package com.example.mobileproj;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeroDetailsActivity extends AppCompatActivity {

    private ImageView globalIV;
    private TextView licenceTV, nameTV, worldTV, genderTV, bdayTV, sizeTV, weightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);


        //region Layout elements assignation
        globalIV = findViewById(R.id.globalIV);
        licenceTV = findViewById(R.id.licenceTV);
        nameTV = findViewById(R.id.nameTV);
        worldTV = findViewById(R.id.worldTV);
        genderTV = findViewById(R.id.genderTV);
        bdayTV = findViewById(R.id.bdayTV);
        sizeTV = findViewById(R.id.sizeTV);
        weightTV = findViewById(R.id.weightTV);
        //endregion


        Hero hero = (Hero) getIntent().getSerializableExtra("ClickedHero");

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
    }
}
