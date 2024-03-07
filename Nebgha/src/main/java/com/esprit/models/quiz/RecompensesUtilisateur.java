package com.esprit.models.quiz;

import com.esprit.models.utilisateur.Utilisateur;

import java.sql.Date;

public class RecompensesUtilisateur {
    private int userRewardId;
    private Utilisateur user;
    private Recompenses reward;
    private Date date;
    private boolean statut;
    private Date dateUtilisation;

    public RecompensesUtilisateur(int userRewardId, Utilisateur user, Recompenses reward, Date date, boolean statut, Date dateUtilisation) {
        this.userRewardId = userRewardId;
        this.user = user;
        this.reward = reward;
        this.date = date;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }

    public RecompensesUtilisateur(Utilisateur user, Recompenses reward, Date date, boolean statut, Date dateUtilisation) {
        this.user = user;
        this.reward = reward;
        this.date = date;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }
    public RecompensesUtilisateur(Utilisateur user, Recompenses reward, boolean statut, Date dateUtilisation) {
        this.user = user;
        this.reward = reward;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }

    public int getUserRewardId() {
        return userRewardId;
    }

    public void setUserRewardId(int userRewardId) {
        this.userRewardId = userRewardId;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Recompenses getReward() {
        return reward;
    }

    public void setReward(Recompenses reward) {
        this.reward = reward;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Date getDateUtilisation() {
        return dateUtilisation;
    }

    public void setDateUtilisation(Date dateUtilisation) {
        this.dateUtilisation = dateUtilisation;
    }

    @Override
    public String toString() {
        return "RecompensesUtilisateur{" +
                "userRewardId=" + userRewardId +
                ", user=" + user +
                ", reward=" + reward +
                ", date=" + date +
                ", statut=" + statut +
                ", dateUtilisation=" + dateUtilisation +
                '}';
    }
}
