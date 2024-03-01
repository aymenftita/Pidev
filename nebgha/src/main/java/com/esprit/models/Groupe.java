package com.esprit.models;

public class Groupe {

    private int id_groupe;
    public Utilisateur uid;
    private String titre;
    private String description;

    public Groupe() {
    }

    public Groupe(int id_groupe, Utilisateur uid, String titre, String description) {
        this.id_groupe = id_groupe;
        this.uid = uid;
        this.titre = titre;
        this.description = description;
    }

    public Groupe(Utilisateur uid, String titre, String description) {
        this.uid = uid;
        this.titre = titre;
        this.description = description;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public Utilisateur getUid() {
        return uid;
    }

    public void setUid(Utilisateur uid) {
        this.uid = uid;
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
        return "Groupe{" +
                "id_groupe= " + id_groupe +
                ", uid= " + uid +
                ", titre= '" + titre +
                ", description= '" + description +
                '}';
    }
}
