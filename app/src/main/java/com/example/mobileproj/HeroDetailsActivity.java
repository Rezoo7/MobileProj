package com.example.mobileproj;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HeroDetailsActivity extends AppCompatActivity {

    private ImageView globalIV, starIV;
    private TextView licenceTV, nameTV, worldTV, genderTV, bdayTV, sizeTV, weightTV;

    Hero hero;
    private boolean isFav;
    private boolean prevFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);


        //region Layout elements assignation
        globalIV = findViewById(R.id.globalIV);
        starIV = findViewById(R.id.starIV);
        licenceTV = findViewById(R.id.licenceTV);
        nameTV = findViewById(R.id.nameTV);
        worldTV = findViewById(R.id.worldTV);
        genderTV = findViewById(R.id.genderTV);
        bdayTV = findViewById(R.id.bdayTV);
        sizeTV = findViewById(R.id.sizeTV);
        weightTV = findViewById(R.id.weightTV);
        //endregion

        hero = (Hero) getIntent().getSerializableExtra("ClickedHero");


        // Retrieve isFav from shared preferences if exists
        SharedPreferences prefs = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        String heroesJSONtoGet = prefs.getString("CreatedHeroes", "");

        // Deserialization
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Hero>>() {
        }.getType();
        Collection<Hero> savedHeroes = gson.fromJson(heroesJSONtoGet, collectionType);
        if (savedHeroes == null) isFav = false;
        else {
            for (Hero h : savedHeroes) {
                if (h.getHeroName().equals(hero.getHeroName())) {
                    isFav = true;
                    break;
                }
            }
        }

        prevFav = isFav;
        setStarIV(getApplicationContext());



        if (hero.getImgPath() != null && hero.getImgPath().length() > 0) {
            Uri imgUri = Uri.parse(hero.getImgPath());
            globalIV.setImageURI(imgUri);
        }

        String world = hero.getHomeWorld();
        String gender = hero.getGender();
        String bday = hero.getBirthDate();
        int size = hero.getSize();
        int weight = hero.getWeight();

        licenceTV.setText((hero.isFromSW() ? "Star Wars" : "Marvel"));
        nameTV.setText(hero.getHeroName());
        worldTV.setText(world.length() <= 0 ? "Monde d'origine\ninconnu" : "Originaire de\n" + world);
        genderTV.setText(gender.length() <= 0 ? "Genre inconnu" : gender);
        bdayTV.setText(bday.length() <= 0 ? "Date de naissance\ninconnue" : "Naissance : \n" + bday);
        sizeTV.setText(size <= 0 ? "Taille inconnue" : size + " cm");
        weightTV.setText(weight <= 0 ? "Poids inconnu" : weight + " kg");


        starIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFav = !isFav;
                setStarIV(view.getContext());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        // If his fav value changed, add or remove from shared preferences
        if (prevFav != isFav) {
            // Retrieve createdHeroes list
            SharedPreferences prefs = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
            String heroesJSONtoGet = prefs.getString("CreatedHeroes", "");

            // Deserialization
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Hero>>() {
            }.getType();
            Collection<Hero> savedHeroes = gson.fromJson(heroesJSONtoGet, collectionType);
            if (savedHeroes == null) savedHeroes = new ArrayList<>();

            // Add the new hero only if he is liked and isn't already in our favorites
            if (isFav && !Utils.contains(savedHeroes, hero)) {
                savedHeroes.add(hero);

            // Remove the hero only if he isn't fav and is in the list
            } else if (!isFav && Utils.contains(savedHeroes, hero)) {
                savedHeroes.remove(hero);
            }

            // Serialization
            String heroesJSONtoSet = gson.toJson(savedHeroes);

            // Save createdHeroes to sharedPreferences
            SharedPreferences.Editor prefsEditor = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE).edit();
            prefsEditor.putString("CreatedHeroes", heroesJSONtoSet);
            prefsEditor.commit();
        }
    }

    public void setStarIV(Context context) {
        String imgName = "star_" + (isFav ? "1" : "0"); // star_0.png = empty star ; star_1.png = checked star

        int resID = getResources().getIdentifier(imgName , "drawable", getPackageName());
        starIV.setImageDrawable(ContextCompat.getDrawable(context, resID));
    }
}
