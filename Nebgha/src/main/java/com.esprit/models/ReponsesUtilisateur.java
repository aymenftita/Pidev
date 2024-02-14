package com.esprit.models;

import java.util.*;

public class ReponsesUtilisateur {
    private int userResponseId;
    private int userId;
    private int reponseId;
    private int quizId;
    private Date date;
    private int tempsPris;
    private boolean isCorrect;


    public ReponsesUtilisateur(int userResponseId, int userId, int reponseId, int quizId, Date date, int tempsPris, boolean isCorrect) {
        this.userResponseId = userResponseId;
        this.userId = userId;
        this.reponseId = reponseId;
        this.quizId = quizId;
        this.date = date;
        this.tempsPris = tempsPris;
        this.isCorrect = isCorrect;
    }

    public ReponsesUtilisateur(int userId, int reponseId, int quizId, Date date, int tempsPris, boolean isCorrect) {
        this.userId = userId;
        this.reponseId = reponseId;
        this.quizId = quizId;
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

    public int getReponseId() {
        return reponseId;
    }

    public void setReponseId(int reponseId) {
        this.reponseId = reponseId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
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
                ", reponseId=" + reponseId +
                ", quizId=" + quizId +
                ", date=" + date +
                ", tempsPris=" + tempsPris +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
