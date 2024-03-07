package com.esprit.models.quiz;

import com.esprit.models.utilisateur.Utilisateur;

import java.sql.Date;

public class Quiz {
    private int quizId;
    private String nom;
    private String description;
    private Utilisateur creator;
    private Date dateCreation;
    private int duree;
    private int nombreQuestions;
    private Difficulty difficulte;

    public Quiz(int quizId, Utilisateur creator, String nom, String description, Date dateCreation, int duree, int nombreQuestions, Difficulty difficulte) {
        this.quizId = quizId;
        this.nom = nom;
        this.description = description;
        this.creator = creator;
        this.dateCreation = dateCreation;
        this.duree = duree;
        this.nombreQuestions = nombreQuestions;
        this.difficulte = difficulte;
    }

    public Quiz(Utilisateur creator, String nom, String description,  int duree, int nombreQuestions, Difficulty difficulte) {
        this.nom = nom;
        this.description = description;
        this.creator = creator;
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

    public Utilisateur getCreator() {
        return creator;
    }

    public void setCreator(Utilisateur creator) {
        this.creator = creator;
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

    public Difficulty getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulty difficulte) {
        this.difficulte = difficulte;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", dateCreation=" + dateCreation +
                ", duree=" + duree +
                ", nombreQuestions=" + nombreQuestions +
                ", difficulte=" + difficulte +
                '}';
    }


}
