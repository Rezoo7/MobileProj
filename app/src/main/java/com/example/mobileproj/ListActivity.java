package com.example.mobileproj;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    //region LAYOUT ELEMENTS
    private EditText heroInput;
    private Button clearBtn;
    private TabLayout herosTab;
    private ListView herosListView;
    //endregion

    TextView resultTV;


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

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ion.with(view.getContext())
                        .load("https://swapi.co/api/people/?format=json")
                        //.setLogging("ION_LOGS", Log DEBUG) // ACTIVER LE MODE DEBUG
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
                                        String gender = jhero.getAsJsonPrimitive("gender").getAsString();
                                        String birthDate = jhero.getAsJsonPrimitive("birth_year").getAsString();
                                        int size = jhero.getAsJsonPrimitive("height").getAsInt();
                                        int weight = jhero.getAsJsonPrimitive("mass").getAsInt();

                                        List<Equipment> equips = new ArrayList<>();
                                        List<Film> films = new ArrayList<>();
                                        Hero hero = new Hero(true, false, heroName, "fuckURLs", gender, birthDate, size, weight, "", equips, films);
                                        resultTV.setText(hero.toString());
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Empty result data", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });



        //Debut modif

        herosTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = "dbb2123c9aff04c577a26c08f5d24ce8";

                Ion.with(view.getContext())
                        .load("https://gateway.marvel.com:443/v1/public/characters?apikey="+key)
                        //.setLogging("ION_LOGS", Log DEBUG) // ACTIVER LE MODE DEBUG
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
                                        String hero = jhero.getAsJsonObject("name").getAsString();


                                        //Hero hero = new Hero(true, false, heroName, "fuckURLs", gender, birthDate, size, weight, "", equips, films);
                                        resultTV.setText(hero);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Empty result data", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });



    }
}
