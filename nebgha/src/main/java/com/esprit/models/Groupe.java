package com.esprit.models;

public class Groupe {

    private int id_groupe;
    private int uid;
    private String titre;
    private String description;

    public Groupe(int id_groupe, int uid, String titre, String description) {
        this.id_groupe = id_groupe;
        this.uid = uid;
        this.titre = titre;
        this.description = description;
    }

    public Groupe(int uid, String titre, String description) {
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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
                "id_groupe=" + id_groupe +
                ", uid=" + uid +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
