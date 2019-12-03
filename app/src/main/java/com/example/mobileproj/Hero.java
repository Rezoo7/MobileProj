package com.example.mobileproj;

import android.media.Image;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Hero {

    private static int id = 0;
    private boolean isFromSW;
    private String heroName;
    private String heroLastName;
    /*private Date birthDate;
    private String homeWorld;
    private boolean isMale;
    private float size;
    private float weight;
    private String imgURL;

    private ArrayList<Equipment> equipments;
    private ArrayList<Film> films;*/

    /**
     * Temp consructor
     */
    public Hero(boolean isFromSW, String heroName, String heroLastName) {
        id++;
        this.isFromSW = isFromSW;
        this.heroName = heroName;
        this.heroLastName = heroLastName;
    }

    /**
     * Create a new hero with parameters
     */
    /*public Hero(boolean isFromSW, String heroName, String heroLastName, Date birthDate, String homeWorld, boolean isMale, float size, float weight, String imgURL) {
        this.isFromSW = isFromSW;
        this.heroName = heroName;
        this.heroLastName = heroLastName;
        this.birthDate = birthDate;
        this.homeWorld = homeWorld;
        this.isMale = isMale;
        this.size = size;
        this.weight = weight;
        this.imgURL = imgURL;
    }*/

    /**
     * Create a new hero with a json array
     */
    public Hero(JSONArray data) {
        // TODO remplir attributs avec tableau json
    }

    /**
     * Add an equipment to the list of equipments
     * @param equipment Equipment to add
     */
    public void addEquipment(Equipment equipment) {
        //this.equipments.add(equipment);
    }

    /**
     * Add a film to the list of films
     * @param film Film to add
     */
    public void addFilm(Film film) {
        //this.films.add(film);
    }

    public String toString() {
        String str = heroName + " " + heroLastName
                + " " + (isFromSW ? "<SW>" : "<M>");
        return str;
    }

    public static final Comparator<Hero> COMPARATEUR_HEROS = new Comparator<Hero>() {
        @Override
        public int compare(Hero o1, Hero o2) {
            int compNom = String.CASE_INSENSITIVE_ORDER.compare(o1.heroName, o2.heroName);
            if (compNom == 0) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.heroLastName, o2.heroLastName);
            }
            else
                return compNom;
        }
    };

    public int getId() {
        return id;
    }

    public boolean isFromSW() {
        return isFromSW;
    }
}
