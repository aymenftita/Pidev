package com.esprit.models;


import java.sql.Date;

public class Reponse {
    //TODO: change auteur_id to a class
    private int id;
    private int auteur_id;
    private Question question;
    private String contenu;
    private Date date;
    private Sujet sujet;

    public Reponse() {
    }



    @Override
    public String toString() {
        return "reponse{" +
                "id=" + id +
                ", auteur_id=" + auteur_id +
                ", question_id=" + question.getId() +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                ", sujet_id=" + sujet.getId() +
                '}';
    }

    public Reponse(int id, int auteur_id, Question question, String contenu, Date date, Sujet sujet) {
        this.id = id;
        this.auteur_id = auteur_id;
        this.question = question;
        this.contenu = contenu;
        this.date = date;
        this.sujet = sujet;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet_id(Sujet sujet) {
        this.sujet = sujet;
    }
}