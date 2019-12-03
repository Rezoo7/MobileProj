package com.example.mobileproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class HeroCreationActivity extends AppCompatActivity {

    //region LAYOUT ELEMENTS
    private Button createBtn;
    private ImageView globalIV, starIV, addEquipIV, addFilmIV;
    private EditText nameInput, homeworldInput, bdayInput, sizeInput, weightInput;
    private Spinner genderSpinner;
    //endregion

    //region DATA
    private boolean isFav = true;
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
        //endregion

        equipments = new ArrayList<>();
        films = new ArrayList<>();

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
                String nodata = "Inconnu";

                String heroName = nameInput.getText().toString();
                if( TextUtils.isEmpty(nameInput.getText()))
                    nameInput.setError( "Le nom du héros doit être précisé !");

                String homeworld = homeworldInput.getText().toString();
                if(TextUtils.isEmpty(homeworldInput.getText())) homeworld = nodata;

                String gender = genderSpinner.getSelectedItem().toString();
                if (gender.equals("@arrays/no_more_choice")) gender = nodata;

                String bday = bdayInput.getText().toString();
                if(TextUtils.isEmpty(bdayInput.getText())) bday = nodata;

                String size = sizeInput.getText().toString() + "cm";
                if(TextUtils.isEmpty(sizeInput.getText())) size = nodata + "e";

                String weight = weightInput.getText().toString() + "kg";
                if(TextUtils.isEmpty(weightInput.getText())) weight = nodata;
                //endregion

                // TODO Create Hero and return it
            }
        });
    }
}
