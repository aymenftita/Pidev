package com.esprit.models;

public class Recompenses {
    private int rewardId;
    private String nom;
    private String description;
    private int scoreRequis;

    public Recompenses(int recompenseId, String nom, String description, int scoreRequis) {
        this.rewardId = recompenseId;
        this.nom = nom;
        this.description = description;
        this.scoreRequis = scoreRequis;
    }

    public Recompenses(String nom, String description, int scoreRequis) {
        this.nom = nom;
        this.description = description;
        this.scoreRequis = scoreRequis;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int recompenseId) {
        this.rewardId = recompenseId;
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

    public int getScoreRequis() {
        return scoreRequis;
    }

    public void setScoreRequis(int scoreRequis) {
        this.scoreRequis = scoreRequis;
    }

    @Override
    public String toString() {
        return "Recompenses{" +
                "recompenseId=" + rewardId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", scoreRequis=" + scoreRequis +
                '}';
    }
}
