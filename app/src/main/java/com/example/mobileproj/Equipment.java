package com.example.mobileproj;

import android.media.Image;

public class Equipment {

    private String EqmtName;
    private String imgURL;

    public Equipment(String EqmtName, String imgURL) {
        this.EqmtName = EqmtName;
        this.imgURL = imgURL;
    }

    public String getEquipmentName() {
        return EqmtName;
    }

    public String getImgURL() {
        return imgURL;
    }
}
