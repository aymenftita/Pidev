package com.esprit.models;

public class lecon {
    private int id_lecon;
    private String titre;
    private String contenu;
    private String description;
    private int id_cours;

    // Constructor
    public lecon(int id_lecon, String titre, String description, int id_cours, String contenu) {
        this.id_lecon = id_lecon;
        this.titre = titre;
        this.description = description;
        this.id_cours = id_cours;
        this.contenu = contenu;


    }

    // Getters and setters
    public int getId_lecon() {
        return id_lecon;
    }

    public void setId_lecon(int id_lecon) {
        this.id_lecon = id_lecon;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }


    @Override
    public String toString() {
        return "lecon{" +
                "id_leçon=" + id_lecon +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", id_cours=" + id_cours +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
