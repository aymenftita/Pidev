package com.esprit.models.Forum;


import com.esprit.models.utilisateur;

import java.sql.Date;

public class Reponse {

    private int id;
    private utilisateur auteur;
    private Question question;
    private String contenu;
    private Date date;
    private Sujet sujet;
    private int score;
    private boolean accepted;
    private boolean reported;

    public Reponse() {
    }



    @Override
    public String toString() {
        return "reponse{" +
                "id=" + id +
                ", auteur_id=" + auteur +
                ", question_id=" + question.getId() +
                ", contenu='" + contenu + '\'' +
                ", date='" + date + '\'' +
                ", sujet_id=" + sujet.getId() +
                '}';
    }

    public Reponse(int id, utilisateur auteur, Question question, String contenu, Date date, Sujet sujet, int score, boolean accepted, boolean reported) {
        this.id = id;
        this.auteur = auteur;
        this.question = question;
        this.contenu = contenu;
        this.date = date;
        this.sujet = sujet;
        this.score = score;
        this.accepted = accepted;
        this.reported = reported;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(utilisateur auteur) {
        this.auteur = auteur;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

}