package com.example.mobileproj;

import java.security.Timestamp;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by lanoix-a on 07/03/2018.
 */

public class Invitation {


    private String nom;
    private String prénom;
    private boolean venue_confirmée;



    /**
     * créer un nouvelle invitation
     * @param n le nom de l'invité
     * @param p le prénom de l'invité
     */
    public Invitation(String n, String p) {
        nom = n;
        prénom = p;
        venue_confirmée = false;
    }


    /**
     * chaine de caractères représentant l'invitation
     * @return une chaine représentant l'invitation
     */
    public String toString() {
        String str = nom.toUpperCase()
                + " "+ prénom.substring(0,1).toUpperCase()
                + prénom.substring(1).toLowerCase();
        if (venue_confirmée) {
            str += "  <OK>";
        }
        return str;
    }

    /**
     * confirme/infirme la venue à l'invitation
     */
    public void confirmer(boolean etat) {
        venue_confirmée = etat;
    }

    /**
     * indique si l'invité vient ou non
     * @return vrai si la venue est confirmée, faux sinon
     */
    public boolean estConfirmee() {
        return venue_confirmée;
    }


    /**
     * indique le nom de l'invité
     * @return le nom de l'invité
     */
    public String nomInvite() {
        return nom;
    }

    /**
     * indique le prénom de l'invité
     * @return le prénom de l'invité
     */
    public String prénomInvite() {
        return prénom;
    }


    /**
     * Operateur de comparaison permettant de trier une liste d'invitation
     */
    public static final Comparator<Invitation> COMPARATEUR_INVITES = new Comparator<Invitation>() {
        @Override
        public int compare(Invitation o1, Invitation o2) {
            int compVenue = -1 * Boolean.compare(o1.venue_confirmée, o2.venue_confirmée);
            if (compVenue == 0) {
                int compNom = String.CASE_INSENSITIVE_ORDER.compare(o1.nom, o2.nom);
               if (compNom == 0) {
                   return String.CASE_INSENSITIVE_ORDER.compare(o1.prénom, o2.prénom);
               }
                else
                   return compNom;
            }
            else
                return compVenue;
        }
    };



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation invitation = (Invitation) o;
        if (nom != null ? !nom.equalsIgnoreCase(invitation.nom) : invitation.nom != null) return false;
        return prénom != null ? prénom.equalsIgnoreCase(invitation.prénom) : invitation.prénom == null;

    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (prénom != null ? prénom.hashCode() : 0);
        return result;
    }
}
