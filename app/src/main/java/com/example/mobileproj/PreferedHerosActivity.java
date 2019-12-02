package com.example.mobileproj;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class PreferedHerosActivity extends AppCompatActivity {

    private EditText heroInput;
    private Button clearBtn;
    private TabLayout herosTab;
    private TextView srchTextView;
    private TextView tabTextView;
    private ListView herosListView;

    private int openedTabItem = 0;
    private ArrayAdapter<Hero> heroesAdapter;
    private List<Hero>[] heroes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefered_heros);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreferedHerosActivity.this, HeroCreationActivity.class));
            }
        });

        heroInput = findViewById(R.id.heroInput);
        clearBtn = findViewById(R.id.clearBtn);
        herosTab = findViewById(R.id.herosTab);
        srchTextView = findViewById(R.id.srchTextView);
        tabTextView = findViewById(R.id.tabTextView);
        herosListView = findViewById(R.id.herosListView);

        // 0 = all ; 1 = star wars ; 2 = marvel
        heroes = new ArrayList[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};

        heroes[0].add(new Hero(true, "SW_Stephanie", "Malherbe"));
        heroes[0].add(new Hero(true, "SW_Remi", "Thomas"));
        heroes[0].add(new Hero(true, "SW_Jason", "Giffard"));
        heroes[0].add(new Hero(false, "M_Louis", "Lebrun"));
        heroes[0].add(new Hero(false, "M_Alexis", "Leleu"));
        heroes[0].add(new Hero(false, "M_Erwan", "Dufourt"));

        for (Hero hero : heroes[0]) {
            if (hero.isFromSW()) heroes[1].add(hero);
            else heroes[2].add(hero);
        }

        List<Hero> currentHeroesDisplayed = new ArrayList<>(); // The list passed in the constructor of the adapter is modified, so we don't use allHeroes in the constructor to keep them safe
        heroesAdapter = new ArrayAdapter<>(PreferedHerosActivity.this, android.R.layout.simple_list_item_1, currentHeroesDisplayed);

        herosListView.setTextFilterEnabled(true);
        filterAndPopulateAdapter(); // equivalent to : heroesAdapter.addAll(heroes[openedTabItem]); // but with filter
        herosListView.setAdapter(heroesAdapter);
        heroesAdapter.sort(Hero.COMPARATEUR_HEROS);


        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heroInput.setText("");
            }
        });

        heroInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                srchTextView.setText(charSequence);
                filterAndPopulateAdapter();
                // TODO rechercher dans les API
            }
        });

        herosTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                openedTabItem = herosTab.getSelectedTabPosition();
                tabTextView.setText("Opened tab : " + openedTabItem);

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
        for (Hero hero : heroes[openedTabItem]) {
            String patrnStr = ".*";
            for (char c : heroInput.getText().toString().toLowerCase().toCharArray()) {
                patrnStr += c + ".*";
            }
            Pattern pattern = Pattern.compile(patrnStr);
            Matcher matcher = pattern.matcher(hero.toString().toLowerCase());
            if (matcher.find())
                temp.add(hero);
        }
        heroesAdapter.clear();
        heroesAdapter.addAll(temp);
    }

}
