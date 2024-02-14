package com.esprit.models;

public class Questions {
    private int questionId;
    private int quizId;
    private String texte;
    private String type;
    private int points;
    private int ordre;
    private String categorie;

    public Questions(int questionId, int quizId, String texte, String type, int points, int ordre, String categorie) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.texte = texte;
        this.type = type;
        this.points = points;
        this.ordre = ordre;
        this.categorie = categorie;
    }

    public Questions(int quizId, String texte, String type, int points, int ordre, String categorie) {
        this.quizId = quizId;
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

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
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
                ", quizId=" + quizId +
                ", texte='" + texte + '\'' +
                ", type='" + type + '\'' +
                ", points=" + points +
                ", ordre=" + ordre +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
