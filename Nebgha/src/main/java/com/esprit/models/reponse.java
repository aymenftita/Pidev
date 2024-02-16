package com.esprit.models;


import java.sql.Date;

public class reponse{
    private int id;
    private int auteur_id;
    private int question_id;
    private String contenu;
    private Date date;
    private int sujet_id;

    public reponse() {
    }



    @Override
    public String toString() {
        return "reponse{" +
                "id=" + id +
                ", auteur_id=" + auteur_id +
                ", question_id=" + question_id +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                ", sujet_id=" + sujet_id +
                '}';
    }

    public reponse(int id, int auteur_id, int question_id, String contenu, Date date, int sujet_id) {
        this.id = id;
        this.auteur_id = auteur_id;
        this.question_id = question_id;
        this.contenu = contenu;
        this.date = date;
        this.sujet_id = sujet_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuteur_id() {
        return auteur_id;
    }

    public void setAuteur_id(int auteur_id) {
        this.auteur_id = auteur_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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
}