package com.example.mobileproj;

import java.io.Serializable;

public class Film implements Serializable {

    private String filmName;
    private String imgURL;

    public Film(String filmName, String imgURL) {
        this.filmName = filmName;
        this.imgURL = imgURL;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getImgURL() {
        return imgURL;
    }
}
