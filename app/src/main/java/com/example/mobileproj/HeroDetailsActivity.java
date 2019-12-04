package com.example.mobileproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HeroDetailsActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);


        tv = findViewById(R.id.tv);

        Hero hero = (Hero) getIntent().getSerializableExtra("ClickedHero");

        tv.setText("Details for " + hero.toString());
    }
}
