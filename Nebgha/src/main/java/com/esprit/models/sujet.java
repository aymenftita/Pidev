package com.esprit.models;

public class sujet {

    private int id;
    private String titre;
    private String desc;
    private String regles;

    public sujet() {

    }

    public sujet(int id, String titre, String desc, String regles) {
        this.id = id;
        this.titre = titre;
        this.desc = desc;
        this.regles = regles;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "forum{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", desc='" + desc + '\'' +
                ", regles='" + regles + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRegles() {
        return regles;
    }

    public void setRegles(String regles) {
        this.regles = regles;
    }
}