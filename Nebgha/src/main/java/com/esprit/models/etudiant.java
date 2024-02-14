package com.esprit.models;

import java.util.Date;

public final class etudiant extends utilisateur {
public int niveau;
public String specialite;
public float moyenne;

    public etudiant(int id, String nom, Date date_naissance, String email, String password, int niveau, String specialite, float moyenne) {
        super(id, nom, date_naissance, email, password);
        this.niveau = niveau;
        this.specialite = specialite;
        this.moyenne = moyenne;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return "etudiant{" +
                "niveau=" + niveau +
                ", specialite='" + specialite + '\'' +
                ", moyenne=" + moyenne +
                "} " + super.toString();
    }
}
