package com.esprit.models.ReclamationGroupModels;

import com.esprit.models.utilisateur.Utilisateur;

public class UserGroupe {

    private Utilisateur idUser;
    private Groupe idGroupe;


    public UserGroupe() {
    }

    public UserGroupe(Utilisateur idUser, Groupe idGroupe) {
        this.idUser = idUser;
        this.idGroupe = idGroupe;
    }

    public Utilisateur getIdUser() {
        return idUser;
    }

    public void setIdUser(Utilisateur idUser) {
        this.idUser = idUser;
    }

    public Groupe getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Groupe idGroupe) {
        this.idGroupe = idGroupe;
    }

    @Override
    public String toString() {
        return "UserGroupe{" +
                "idUser=" + idUser +
                ", idGroupe=" + idGroupe +
                '}';
    }
}
