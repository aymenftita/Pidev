package main.java.com.esprit.models;

public class lecon {
    private int id_leçon;
    private String titre;
    private String description;
    private int id_cours;
    private String contenu;

    public lecon(int id_leçon, String titre, String description, int id_cours, String contenu) {
        this.id_leçon = id_leçon;
        this.titre = titre;
        this.description = description;
        this.id_cours = id_cours;
        this.contenu = contenu;
    }

    public lecon(String titre, String description, String contenu) {
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
    }

    public int getId_leçon() {
        return id_leçon;
    }

    public void setId_leçon(int id_leçon) {
        this.id_leçon = id_leçon;
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

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "lecon{" +
                "id_leçon=" + id_leçon +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", id_cours=" + id_cours +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
