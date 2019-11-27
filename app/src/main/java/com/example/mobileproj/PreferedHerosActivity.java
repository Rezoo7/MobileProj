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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PreferedHerosActivity extends AppCompatActivity {

    private int openedTabItem = 0;

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

        final EditText heroInput = findViewById(R.id.heroInput);
        final Button clearBtn = findViewById(R.id.clearBtn);
        final TabLayout herosTab = findViewById(R.id.herosTab);
        final TextView srchTextView = findViewById(R.id.srchTextView);
        final TextView tabTextView = findViewById(R.id.tabTextView);
        final ListView herosListView = findViewById(R.id.herosListView);

        List<Hero> heros = new ArrayList<>();
        heros.add(new Hero(true, "SW_Stephanie", "Malherbe"));
        heros.add(new Hero(true, "SW_Remi", "Thomas"));
        heros.add(new Hero(false, "M_Louis", "Lebrun"));
        heros.add(new Hero(false, "M_Alexis", "Leleu"));
        heros.add(new Hero(true, "SW_Jason", "Giffard"));
        heros.add(new Hero(false, "M_Erwan", "Dufourt"));

        ArrayAdapter<Hero> herosAdapter = new ArrayAdapter<>(PreferedHerosActivity.this, android.R.layout.simple_list_item_1, heros);
        herosListView.setAdapter(herosAdapter);
        herosAdapter.sort(Hero.COMPARATEUR_HEROS);

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
                // TODO refresh table
            }
        });
    }

}
