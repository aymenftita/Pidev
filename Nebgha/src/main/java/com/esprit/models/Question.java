package com.esprit.models;

import java.sql.Date;

public class Question {

    private int id;
    private String titre;
    private utilisateur auteur;
    private Date date;
    private Sujet sujet;
    private String contenu;

    public Question() {
    }

    public Question(int id, String titre, utilisateur auteur, Date date, Sujet sujet, String contenu) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.date = date;
        this.sujet = sujet;
        this.contenu = contenu;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "question{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur=" + auteur +
                ", date='" + date + '\'' +
                ", sujet_id=" + sujet.getId() +
                ", contenu='" + contenu + '\'' +
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

    public utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(utilisateur auteur) {
        this.auteur = auteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}