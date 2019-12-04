package com.example.mobileproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HeroCreationActivity extends AppCompatActivity {

    final int PICK_IMAGE_FLAG = 222;

    //region LAYOUT ELEMENTS
    private Button createBtn;
    private ImageView globalIV, starIV, addEquipIV, addFilmIV;
    private EditText nameInput, homeworldInput, bdayInput, sizeInput, weightInput;
    private Spinner genderSpinner;
    private Switch licenceSwitch;
    //endregion

    //region DATA
    private boolean isFav = true;
    private boolean isSW = true;
    private String mainImgPath;
    private List<Equipment> equipments;
    private List<Film> films;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_creation);

        //region Layout elements assignation
        createBtn = findViewById(R.id.createBtn);
        globalIV = findViewById(R.id.globalIV);
        starIV = findViewById(R.id.starIV);
        addEquipIV = findViewById(R.id.addEquipIV);
        addFilmIV = findViewById(R.id.addFilmIV);
        nameInput = findViewById(R.id.nameInput);
        homeworldInput = findViewById(R.id.homeworldInput);
        bdayInput = findViewById(R.id.bdayInput);
        sizeInput = findViewById(R.id.sizeInput);
        weightInput = findViewById(R.id.weightInput);
        genderSpinner = findViewById(R.id.genderSpinner);
        licenceSwitch = findViewById(R.id.licenceSwitch);
        //endregion

        equipments = new ArrayList<>();
        films = new ArrayList<>();


        globalIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "Selectionner une image"), PICK_IMAGE_FLAG);
            }
        });


        starIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFav = !isFav;
                String imgName = "star_" + (isFav ? "1" : "0"); // star_0.png = empty star ; star_1.png = checked star

                int resID = getResources().getIdentifier(imgName , "drawable", getPackageName());
                starIV.setImageDrawable(ContextCompat.getDrawable(view.getContext(), resID));
            }
        });


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //region Get infos
                final String NO_DATA = "";

                String heroName = nameInput.getText().toString();
                if(TextUtils.isEmpty(nameInput.getText())) {
                    nameInput.setError("Le nom du héros doit être précisé !");
                    return;
                }

                String homeworld = homeworldInput.getText().toString();
                if(TextUtils.isEmpty(homeworldInput.getText())) homeworld = NO_DATA;

                String gender;
                if (genderSpinner.getSelectedItem() != genderSpinner.getItemAtPosition(0) &&
                        genderSpinner.getSelectedItem() != genderSpinner.getItemAtPosition(3)) {
                    gender = genderSpinner.getSelectedItem().toString();
                } else gender = NO_DATA;

                String bday = bdayInput.getText().toString();
                if(TextUtils.isEmpty(bdayInput.getText())) bday = NO_DATA;

                int size = 5;
                if(!TextUtils.isDigitsOnly(sizeInput.getText()))
                    size = Integer.parseInt(sizeInput.getText().toString());

                int weight = 5;
                if(!TextUtils.isDigitsOnly(weightInput.getText()))
                    weight = Integer.parseInt(weightInput.getText().toString());
                //endregion

                Hero hero = new Hero(isSW, isFav, heroName, homeworld, gender, bday, size, weight, mainImgPath, equipments, films);


                //region Add the new hero to the list of the created

                // Retrieve createdHeroes list
                SharedPreferences prefs = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
                String heroesJSONtoGet = prefs.getString("CreatedHeroes", "");
                Toast.makeText(getApplicationContext(), "'" + heroesJSONtoGet + "'", Toast.LENGTH_LONG).show();

                // Deserialization
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Hero>>(){}.getType();
                Collection<Hero> savedHeroes = gson.fromJson(heroesJSONtoGet, collectionType);


                // Add the new hero
                if (savedHeroes == null) savedHeroes = new ArrayList<>();
                savedHeroes.add(hero);


                // Serialization
                String heroesJSONtoSet = gson.toJson(savedHeroes);
                //Toast.makeText(getApplicationContext(), "'" + heroesJSONtoSet + "'", Toast.LENGTH_LONG).show();

                // Save createdHeroes to sharedPreferences
                SharedPreferences.Editor prefsEditor = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE).edit();
                prefsEditor.putString("CreatedHeroes", heroesJSONtoSet);
                prefsEditor.commit();
                //endregion


                //region Send infos
                Intent intent = new Intent();
                intent.putExtra("CreatedHero", hero);
                setResult(Activity.RESULT_OK, intent);
                finish();
                //endregion
            }
        });

        licenceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW = !isChecked;
                licenceSwitch.setText((isSW ? "Star Wars" : "Marvel"));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_FLAG && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            globalIV.setImageURI(selectedImage);
            mainImgPath = selectedImage.toString();
        }
    }
}
