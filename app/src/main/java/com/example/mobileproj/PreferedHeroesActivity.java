package com.example.mobileproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PreferedHeroesActivity extends AppCompatActivity {

    final int FLAG_CREATE_HERO = 777;

    //region LAYOUT ELEMENTS
    private EditText heroInput;
    private Button clearBtn;
    private TabLayout herosTab;
    private TextView srchTextView;
    private TextView tabTextView;
    private ListView herosListView;
    //endregion

    private int openedTabItem = 0;
    private ArrayAdapter<Hero> heroesAdapter;
    private List<Hero>[] heroes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefered_heroes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //region Layout elements assignation
        heroInput = findViewById(R.id.heroInput);
        clearBtn = findViewById(R.id.clearBtn);
        herosTab = findViewById(R.id.herosTab);
        srchTextView = findViewById(R.id.srchTextView);
        tabTextView = findViewById(R.id.tabTextView);
        herosListView = findViewById(R.id.herosListView);
        FloatingActionButton fab = findViewById(R.id.fab);
        //endregion


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreferedHeroesActivity.this, HeroCreationActivity.class);
                startActivityForResult(intent, FLAG_CREATE_HERO);

                //Hero hero = new Hero(true, heroName, homeworld, gender, bday, size, weight, null);
            }
        });


        // Tabs ID : 0 = all ; 1 = star wars ; 2 = marvel
        heroes = new ArrayList[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};

        //region Hard coded data
        heroes[0].add(new Hero(true, "Stephanie Malherbe"));
        heroes[0].add(new Hero(true, "Remi Thomas"));
        heroes[0].add(new Hero(true, "Jason Giffard"));
        heroes[0].add(new Hero(false, "Louis Lebrun"));
        heroes[0].add(new Hero(false, "Alexis Leleu"));
        heroes[0].add(new Hero(false, "Erwan Dufourt"));
        //endregion

        // Browse all heroes to distribute them in star wars or marvel list
        for (Hero hero : heroes[0]) {
            if (hero.isFromSW()) heroes[1].add(hero);
            else heroes[2].add(hero);
        }

        List<Hero> currentHeroesDisplayed = new ArrayList<>(); // The list passed in the constructor of the adapter is modified, so we don't use allHeroes in the constructor to keep them safe from change
        heroesAdapter = new ArrayAdapter<>(PreferedHeroesActivity.this, android.R.layout.simple_list_item_1, currentHeroesDisplayed);

        // An adapter filterable
        herosListView.setTextFilterEnabled(true);
        filterAndPopulateAdapter(); // equivalent to : heroesAdapter.addAll(heroes[openedTabItem]); // but with filter
        herosListView.setAdapter(heroesAdapter);
        heroesAdapter.sort(Hero.COMPARATEUR_HEROS);


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
                // TODO rechercher dans les API (ou pas ici)

               /* Retrofit retro = new Retrofit.Builder()
                        .baseUrl("https://swapi.co/api/people/1")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API api = retro.create(API.class);
                Call<List<Hero>> call = (Call<List<Hero>>) api.getPosts();

                call.enqueue(new Callback<List<Hero>>() {
                    @Override
                    public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {

                        if(!response.isSuccessful()){
                            System.out.println("Code: "+ response.code());
                            return;
                        }

                        List<Hero> heros = response.body();

                        for(Hero hero : heros){

                            String name = hero.getHeroName();

                            Hero h = new Hero(true,name);

                            String content = "";
                            content += "Name : " + name + "\n";
                            System.out.println(content);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hero>> call, Throwable t) {
                        System.out.println("BAH NON");
                    }
                });

                */

                srchTextView.setText(charSequence);
                filterAndPopulateAdapter();
            }
        });

        // Opening a tab will display the right list (all || star wars || marvel)
        herosTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                openedTabItem = herosTab.getSelectedTabPosition();
                tabTextView.setText("Opened tab : " + openedTabItem);

                // Clear and repopulate with new list
                heroesAdapter.clear();
                filterAndPopulateAdapter();
                heroesAdapter.sort(Hero.COMPARATEUR_HEROS);
            }
        });


    }

    /**
     * Filters heroes with text input value in their names, then put these heroes in the array adapter
     */
    private void filterAndPopulateAdapter() {
        List<Hero> temp = new ArrayList<>();

        // Browse heroes of the current opened tab
        for (Hero hero : heroes[openedTabItem]) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == FLAG_CREATE_HERO) { // We've just created a hero

            Hero hero = (Hero) data.getSerializableExtra("CreatedHero");
            if (hero.isFav()) {
                heroes[0].add(hero);
                if (hero.isFromSW()) heroes[1].add(hero);
                else heroes[2].add(hero);
                filterAndPopulateAdapter();
            } else {
                // Creation of heroes which we don't like can be cool to add to a database. Here it's useless.
                Toast.makeText(getApplicationContext(), "Un héro doit être liké pour être ajouté à la liste des favoris.", Toast.LENGTH_LONG).show();
            }
        }
    }

}