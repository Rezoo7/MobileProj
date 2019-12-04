package com.example.mobileproj;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    //region LAYOUT ELEMENTS
    private EditText heroInput;
    private Button clearBtn;
    private TabLayout herosTab;
    private ListView herosListView;
    //endregion


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

        final String url = "https://swapi.co/api/";

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String perso = "people/";

                for(int i=0; i<= 10; i++) {

                    Ion.with(view.getContext())
                            .load(url+perso+i)
                            .asJsonArray()
                            .setCallback(new FutureCallback<JsonArray>() {
                                @Override
                                public void onCompleted(Exception e, JsonArray result) {
                                    Iterator<JsonElement> ite = result.iterator();
                                    while (ite.hasNext()) {
                                        JsonObject item = ite.next().getAsJsonObject();
                                        String name = item.getAsJsonPrimitive("nom").getAsString();
                                        String homeW = item.getAsJsonPrimitive("homeworld").getAsString();
                                        String gender = item.getAsJsonPrimitive("gender").getAsString();
                                        String birth = item.getAsJsonPrimitive("birth_year").getAsString();
                                        int size = item.getAsJsonPrimitive("height").getAsInt();
                                        int weight = item.getAsJsonPrimitive("mass").getAsInt();
                                        String imgPath = item.getAsJsonPrimitive("nom").getAsString();
                                        String equip = item.getAsJsonPrimitive("nom").getAsString();

                                        ArrayList<Film> listef = new ArrayList<>();


                                        //Hero h = new Hero(true, false, name);
                                        //monAdapter.add(install);
                                    }
                                }

                            });
                }
            }
        });

    }
}
