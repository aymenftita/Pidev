package com.esprit.models.quiz;

import com.esprit.models.utilisateur.Utilisateur;

import java.sql.Date;
public class ReponsesUtilisateur {
    private int userResponseId;
    private Utilisateur user;
    private Reponses reponse;
    private Quiz quiz;
    private Date date;
    private int tempsPris;
    private boolean isCorrect;


    public ReponsesUtilisateur(int userResponseId, Utilisateur user, Reponses reponse, Quiz quiz, Date date, int tempsPris, boolean isCorrect) {
        this.userResponseId = userResponseId;
        this.user = user;
        this.reponse = reponse;
        this.quiz = quiz;
        this.date = date;
        this.tempsPris = tempsPris;
        this.isCorrect = isCorrect;
    }

    public ReponsesUtilisateur(Utilisateur user, Reponses reponse, Quiz quiz, Date date, int tempsPris, boolean isCorrect) {
        this.user = user;
        this.reponse = reponse;
        this.quiz = quiz;
        this.date = date;
        this.tempsPris = tempsPris;
        this.isCorrect = isCorrect;
    }

    public ReponsesUtilisateur(Utilisateur user, Reponses reponse, Quiz quiz, int tempsPris, boolean isCorrect) {
        this.user=user;
        this.reponse=reponse;
        this.quiz=quiz;
        this.tempsPris=tempsPris;
        this.isCorrect=isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getUserResponseId() {
        return userResponseId;
    }

    public void setUserResponseId(int userResponseId) {
        this.userResponseId = userResponseId;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Reponses getReponse() {
        return reponse;
    }

    public void setReponse(Reponses reponse) {
        this.reponse = reponse;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTempsPris() {
        return tempsPris;
    }

    public void setTempsPris(int tempsPris) {
        this.tempsPris = tempsPris;
    }

    @Override
    public String toString() {
        return "ReponsesUtilisateur{" +
                "userResponseId=" + userResponseId +
                ", user=" + user +
                ", reponse=" + reponse+
                ", quiz=" + quiz +
                ", date=" + date +
                ", tempsPris=" + tempsPris +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
