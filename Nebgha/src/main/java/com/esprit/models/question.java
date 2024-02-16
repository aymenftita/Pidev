package com.esprit.models;

import java.sql.Date;

public class question{
    private int id;
    private String titre;
    private int auteur_id;
    private Date date;
    private int sujet_id;
    private String contenu;

    public question() {
    }

    public question(int id,String titre, int auteur_id, Date date, int sujet_id, String contenu) {
        this.id = id;
        this.titre = titre;
        this.auteur_id = auteur_id;
        this.date = date;
        this.sujet_id = sujet_id;
        this.contenu = contenu;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "question{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur_id=" + auteur_id +
                ", date='" + date + '\'' +
                ", sujet_id=" + sujet_id +
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

    public int getAuteur_id() {
        return auteur_id;
    }

    public void setAuteur_id(int auteur_id) {
        this.auteur_id = auteur_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}