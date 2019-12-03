package com.example.mobileproj;

import java.io.Serializable;

public class Equipment implements Serializable {

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
