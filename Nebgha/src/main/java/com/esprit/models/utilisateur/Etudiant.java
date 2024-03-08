package com.esprit.models.utilisateur;


public final class Etudiant extends Utilisateur {

public int niveau;
public String specialite;


    public Etudiant(int id, String nom, String prenom,
                    String email, String password, Role role,
                    int niveau, String specialite) {
        super(id, nom, prenom, email, password, role);
        this.niveau = niveau;
        this.specialite = specialite;
    }

    public Etudiant(String nom, String prenom, String email, String password,
                    Role role, int niveau, String specialite) {
        super(nom, prenom, email, password, role);
        this.niveau = niveau;
        this.specialite = specialite;
    }


    public int getNiveau() {
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



    @Override
    public String toString() {
        return "etudiant{" +
                "niveau=" + niveau +
                ", specialite='" + specialite + '\'' +
                "} " + super.toString();
    }
}
