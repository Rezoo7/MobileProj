package com.example.mobileproj;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    //region LAYOUT ELEMENTS
    private EditText heroInput;
    private Button clearBtn;
    private TabLayout herosTab;
    private ListView herosListView;
    //endregion

    private int openedTab = 0;
    private ArrayAdapter<Hero> heroesAdapter;
    private List<Hero>[] heroes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //region Layout elements assignation
        heroInput = findViewById(R.id.heroInput);
        clearBtn = findViewById(R.id.clearBtn);
        herosTab = findViewById(R.id.herosTab);
        herosListView = findViewById(R.id.herosListView);
        //endregion


        heroes = new ArrayList[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};


        Ion.with(getApplicationContext())
                .load("https://swapi.co/api/people/?format=json")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            JsonArray list = result.getAsJsonArray("results").getAsJsonArray();
                            Iterator<JsonElement> it = list.iterator();
                            while(it.hasNext()) {

                                //Hero(isFromSW, isFavorite, heroName, homeWorld, gender, birthDate, int size, int weight, imgPath, equipments, films)

                                JsonObject jhero = it.next().getAsJsonObject();
                                String heroName = jhero.getAsJsonPrimitive("name").getAsString();
                                String homeWorldURL = jhero.getAsJsonPrimitive("homeworld").getAsString();
                                String genderStr = jhero.getAsJsonPrimitive("gender").getAsString();
                                String gender = "Gender : " + genderStr.substring(0, 1).toUpperCase() + genderStr.substring(1).toLowerCase();
                                String birthDate = jhero.getAsJsonPrimitive("birth_year").getAsString();
                                int size = jhero.getAsJsonPrimitive("height").getAsInt();
                                int weight = jhero.getAsJsonPrimitive("mass").getAsInt();

                                List<Equipment> equips = new ArrayList<>();
                                List<Film> films = new ArrayList<>();
                                Hero hero = new Hero(true, false, heroName, "fuckURLs", gender, birthDate, size, weight, "", equips, films);

                                heroes[0].add(hero); // Add to all
                                heroes[1].add(hero); // Add to Star Wars

                            }

                            List<Hero> currentHeroesDisplayed = new ArrayList<>(); // The list passed in the constructor of the adapter is modified, so we don't use allHeroes in the constructor to keep them safe from change
                            heroesAdapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, currentHeroesDisplayed);

                            // An adapter filterable
                            herosListView.setTextFilterEnabled(true);
                            filterAndPopulateAdapter(); // equivalent to : heroesAdapter.addAll(heroes[openedTab]); // but with filter
                            herosListView.setAdapter(heroesAdapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "Empty result data", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // Clear text input when clear button pressed
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heroInput.setText("");
            }
        });

        // On text input value change, filter the list of result (NOTE : maybe call the API elsewhere to avoid slowness / But copy all heroes on a local list ?)
        heroInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterAndPopulateAdapter();
            }
        });

        // Opening a tab will display the right list (all || star wars || marvel)
        herosTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                openedTab = herosTab.getSelectedTabPosition();

                // Clear and repopulate with new list
                heroesAdapter.clear();
                filterAndPopulateAdapter();
            }
        });

        herosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(ListActivity.this, HeroDetailsActivity.class);
                intent.putExtra("ClickedHero", heroesAdapter.getItem(position));
                startActivity(intent);
            }
        });

    }

    /**
     * Filters heroes with text input value in their names, then put these heroes in the array adapter
     */
    private void filterAndPopulateAdapter() {
        List<Hero> temp = new ArrayList<>();

        // Browse heroes of the current opened tab
        for (Hero hero : heroes[openedTab]) {
            // Get rid of accents and the case
            String inputStr = Utils.stripAcnt(heroInput.getText().toString().toLowerCase());

            // Separate all chars of the text input value with ".*" to make it more flexible
            String patrnStr = ".*";

            for (char c : inputStr.toCharArray()) {
                patrnStr += c + ".*";
            }
            Pattern pattern = Pattern.compile(patrnStr);
            // Find the pattern in the first and last name of the hero (and is SW bool too, this bool is used only for debugging so np)
            Matcher matcher = pattern.matcher(Utils.stripAcnt(hero.toString().toLowerCase()));
            if (matcher.find())
                temp.add(hero);
        }
        // Temp list with matching heroes replace previous the list
        heroesAdapter.clear();
        Collections.reverse(temp);
        heroesAdapter.addAll(temp);
        heroesAdapter.sort(Hero.COMPARATEUR_HEROS);
    }
}
