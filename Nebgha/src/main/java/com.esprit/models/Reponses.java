package com.esprit.models;

public class Reponses {
    private int reponseId;
    private int questionId;
    private String texte;
    private boolean estCorrecte;
    private int ordre;
    private String explication;

    public Reponses(int questionId, String texte, boolean estCorrecte, int ordre, String explication) {
        this.questionId = questionId;
        this.texte = texte;
        this.estCorrecte = estCorrecte;
        this.ordre = ordre;
        this.explication = explication;
    }

    public Reponses(int reponseId, int questionId, String texte, boolean estCorrecte, int ordre, String explication) {
        this.reponseId = reponseId;
        this.questionId = questionId;
        this.texte = texte;
        this.estCorrecte = estCorrecte;
        this.ordre = ordre;
        this.explication = explication;
    }

    public int getReponseId() {
        return reponseId;
    }

    public void setReponseId(int reponseId) {
        this.reponseId = reponseId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public boolean isEstCorrecte() {
        return estCorrecte;
    }

    public void setEstCorrecte(boolean estCorrecte) {
        this.estCorrecte = estCorrecte;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    @Override
    public String toString() {
        return "Reponses{" +
                "reponseId=" + reponseId +
                ", questionId=" + questionId +
                ", texte='" + texte + '\'' +
                ", estCorrecte=" + estCorrecte +
                ", ordre=" + ordre +
                ", explication='" + explication + '\'' +
                '}';
    }
}
