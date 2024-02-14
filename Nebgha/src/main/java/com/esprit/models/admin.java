package com.esprit.models;

import java.util.Date;

public final class admin extends utilisateur{

    public admin(int id, String nom, String prenom, Date date_naissance, String email, String password, Role role) {
        super(id, nom, prenom, date_naissance, email, password, role);
    }



    @Override
    public String toString() {
        return "admin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date_naissance=" + date_naissance +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
