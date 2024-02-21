package com.esprit.models;

import java.sql.Date;
public class ReponsesUtilisateur {
    private int userResponseId;
    private int userId;
    private Reponses reponse;
    private Quiz quiz;
    private Date date;
    private int tempsPris;
    private boolean isCorrect;


    public ReponsesUtilisateur(int userResponseId, int userId, Reponses reponse, Quiz quiz, Date date, int tempsPris, boolean isCorrect) {
        this.userResponseId = userResponseId;
        this.userId = userId;
        this.reponse = reponse;
        this.quiz = quiz;
        this.date = date;
        this.tempsPris = tempsPris;
        this.isCorrect = isCorrect;
    }

    public ReponsesUtilisateur(int userId, Reponses reponse, Quiz quiz, Date date, int tempsPris, boolean isCorrect) {
        this.userId = userId;
        this.reponse = reponse;
        this.quiz = quiz;
        this.date = date;
        this.tempsPris = tempsPris;
        this.isCorrect = isCorrect;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                ", userId=" + userId +
                ", reponse=" + reponse+
                ", quiz=" + quiz +
                ", date=" + date +
                ", tempsPris=" + tempsPris +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
