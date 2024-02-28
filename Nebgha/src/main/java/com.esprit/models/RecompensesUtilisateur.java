package com.esprit.models;

import java.sql.Date;

public class RecompensesUtilisateur {
    private int userRewardId;
    private int userId;
    private Recompenses reward;
    private Date date;
    private boolean statut;
    private Date dateUtilisation;

    public RecompensesUtilisateur(int userRewardId, int userId, Recompenses reward, Date date, boolean statut, Date dateUtilisation) {
        this.userRewardId = userRewardId;
        this.userId = userId;
        this.reward = reward;
        this.date = date;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }

    public RecompensesUtilisateur(int userId, Recompenses reward, Date date, boolean statut, Date dateUtilisation) {
        this.userId = userId;
        this.reward = reward;
        this.date = date;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }
    public RecompensesUtilisateur(int userId, Recompenses reward, boolean statut, Date dateUtilisation) {
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                ", userId=" + userId +
                ", reward=" + reward +
                ", date=" + date +
                ", statut=" + statut +
                ", dateUtilisation=" + dateUtilisation +
                '}';
    }
}
