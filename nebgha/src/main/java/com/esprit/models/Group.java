package com.esprit.models;

public class Group {

    private int idGroup;

    private Utilisateur idUser;

    private String titre;

    private String description;

    public Group() {
    }

    public Group(int idGroupe, Utilisateur idUser, String titre, String description) {
        this.idGroup = idGroupe;
        this.idUser = idUser;
        this.titre = titre;
        this.description = description;
    }

    public Group(Utilisateur idUser, String titre, String description) {
        this.idUser = idUser;
        this.titre = titre;
        this.description = description;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public Utilisateur getIdUser() {
        return idUser;
    }

    public void setIdUser(Utilisateur idUser) {
        this.idUser = idUser;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idGroupe=" + idGroup +
                ", idUser=" + idUser +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
