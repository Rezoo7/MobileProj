package com.example.mobileproj;

import java.text.Normalizer;
import java.util.Collection;

public class Utils {

    public static String stripAcnt(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static boolean contains(Collection<Hero> list, Hero hero) {
        for (Hero h : list) {
            if (h.getHeroName().equals(hero.getHeroName())) {
                return true;
            }
        }
        return false;
    }
}
