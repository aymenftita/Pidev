package com.esprit.models;

import java.util.*;

public class RecompensesUtilisateur {
    private int userRewardId;
    private int userId;
    private int rewardId;
    private Date date;
    private boolean statut;
    private Date dateUtilisation;

    public RecompensesUtilisateur(int userRewardId, int userId, int rewardId, Date date, boolean statut, Date dateUtilisation) {
        this.userRewardId = userRewardId;
        this.userId = userId;
        this.rewardId = rewardId;
        this.date = date;
        this.statut = statut;
        this.dateUtilisation = dateUtilisation;
    }

    public RecompensesUtilisateur(int userId, int rewardId, Date date, boolean statut, Date dateUtilisation) {
        this.userId = userId;
        this.rewardId = rewardId;
        this.date = date;
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

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
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
                ", rewardId=" + rewardId +
                ", date=" + date +
                ", statut=" + statut +
                ", dateUtilisation=" + dateUtilisation +
                '}';
    }
}
