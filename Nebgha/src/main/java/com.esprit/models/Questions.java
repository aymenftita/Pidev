package com.esprit.models;

public class Questions {
    private int questionId;
    private Quiz quiz;
    private String texte;
    private String type;
    private int points;
    private int ordre;
    private String categorie;

    public Questions(int questionId, Quiz quiz, String texte, String type, int points, int ordre, String categorie) {
        this.questionId = questionId;
        this.quiz = quiz;
        this.texte = texte;
        this.type = type;
        this.points = points;
        this.ordre = ordre;
        this.categorie = categorie;
    }

    public Questions(Quiz quiz, String texte, String type, int points, int ordre, String categorie) {
        this.quiz = quiz;
        this.texte = texte;
        this.type = type;
        this.points = points;
        this.ordre = ordre;
        this.categorie = categorie;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "questionId=" + questionId +
                ", quiz=" + quiz +
                ", texte='" + texte + '\'' +
                ", type='" + type + '\'' +
                ", points=" + points +
                ", ordre=" + ordre +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
