package com.esprit.models.cours;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Cours {
    private int id_cours;
    private String titre;
    private String description;
    private String contenu;
    private int id_module;
    private URI video;


    public Cours() {
    }

    public Cours(int id_cours, String titre, String description, String contenu, int id_module) {
        this.id_cours = id_cours;
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.id_module = id_module;
    }

    public Cours(String titre, String description, String contenu, int id_module) {
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.id_module = id_module;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

   public int getId_module() {
        return id_module ;
   }
    public void setId_module(int id_module) {
        this.id_module = id_module;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id_cours=" + id_cours +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", contenu='" + contenu + '\'' +
                ", id_module=" + id_module +
                '}';
    }
    private List<Utilisateur> participants = new ArrayList<>();


    public void addParticipant(Utilisateur student) {
        participants.add(student);
    }

    public List<Utilisateur> getParticipants() {
        return participants;
    }


    public URI getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = URI.create(video);
    }
}
