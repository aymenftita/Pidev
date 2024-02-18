package com.esprit.models;

import java.util.Date;

public class Quiz {
    private int quizId;
    private String nom;
    private String description;
    private int creatorId;
    private Date dateCreation;
    private int duree;
    private int nombreQuestions;
    private Difficulte difficulte;

    public Quiz(int quizId, int creatorId, String nom, String description, Date dateCreation, int duree, int nombreQuestions, Difficulte difficulte) {
        this.quizId = quizId;
        this.nom = nom;
        this.description = description;
        this.creatorId = creatorId;
        this.dateCreation = dateCreation;
        this.duree = duree;
        this.nombreQuestions = nombreQuestions;
        this.difficulte = difficulte;
    }

    public Quiz(int creatorId, String nom, String description, Date dateCreation, int duree, int nombreQuestions, Difficulte difficulte) {
        this.nom = nom;
        this.description = description;
        this.creatorId = creatorId;
        this.dateCreation = dateCreation;
        this.duree = duree;
        this.nombreQuestions = nombreQuestions;
        this.difficulte = difficulte;
    }

    public Quiz() {

    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNombreQuestions() {
        return nombreQuestions;
    }

    public void setNombreQuestions(int nombreQuestions) {
        this.nombreQuestions = nombreQuestions;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", creatorId=" + creatorId +
                ", dateCreation=" + dateCreation +
                ", duree=" + duree +
                ", nombreQuestions=" + nombreQuestions +
                ", difficulte=" + difficulte +
                '}';
    }


}
