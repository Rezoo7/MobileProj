package com.example.mobileproj;

import java.text.Normalizer;

public class Utils {

    public static String stripAcnt(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
