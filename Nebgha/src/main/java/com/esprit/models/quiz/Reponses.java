package com.esprit.models.quiz;

public class Reponses {
    private int reponseId;
    private Questions question;
    private String texte;
    private boolean estCorrecte;
    private int ordre;
    private String explication;

    public Reponses(Questions question, String texte, boolean estCorrecte, int ordre, String explication) {
        this.question = question;
        this.texte = texte;
        this.estCorrecte = estCorrecte;
        this.ordre = ordre;
        this.explication = explication;
    }

    public Reponses(int reponseId, Questions question, String texte, boolean estCorrecte, int ordre, String explication) {
        this.reponseId = reponseId;
        this.question = question;
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

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
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
                ", question=" + question +
                ", texte='" + texte + '\'' +
                ", estCorrecte=" + estCorrecte +
                ", ordre=" + ordre +
                ", explication='" + explication + '\'' +
                '}';
    }
}
