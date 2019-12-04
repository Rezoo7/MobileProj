package com.example.mobileproj;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hero implements Serializable {

    private static int id = 0;
    private boolean isFavorite;
    private boolean isFromSW;
    private String heroName;
    private String birthDate;
    private String homeWorld;
    private String gender;
    private int size;
    private int weight;
    private String imgPath;

    private ArrayList<Equipment> equipments;
    private ArrayList<Film> films;

    /**
     * Create a new hero with parameters
     */
    public Hero(boolean isFromSW, boolean isFavorite, String heroName, String homeWorld, String gender, String birthDate, int size, int weight, String imgPath, List<Equipment> equipments, List<Film> films) {
        this.isFromSW = isFromSW;
        this.isFavorite = isFavorite;
        this.heroName = heroName;
        this.birthDate = birthDate;
        this.homeWorld = homeWorld;
        this.gender = gender;
        this.size = size;
        this.weight = weight;
        this.imgPath = imgPath;
        this.equipments = new ArrayList<>(equipments != null ? equipments : null);
        this.films = new ArrayList<>(films);
    }

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

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String toString() {
        String str = heroName
                + " " + (isFromSW ? "<SW>" : "<M>");
        return str;
    }

    public static final Comparator<Hero> COMPARATEUR_HEROS = new Comparator<Hero>() {
        @Override
        public int compare(Hero o1, Hero o2) {
            int compNom = String.CASE_INSENSITIVE_ORDER.compare(o1.heroName, o2.heroName);

            return compNom;
        }
    };

    public int getId() {
        return id;
    }

    public boolean isFromSW() {
        return isFromSW;
    }

    public boolean isFav() {
        return isFavorite;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getHomeWorld() {
        return homeWorld;
    }

    public String getGender() {
        return gender;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public String getImgPath() {
        return imgPath;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }
}
