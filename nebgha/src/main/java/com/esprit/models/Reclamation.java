package com.esprit.models;

public class Reclamation {

    private int id_reclamation;
    private int uid;
    private String date_creation;
    private String sujet;
    private String description;
    private String status;
    private int priorité;
    private String responsablle;

    public Reclamation(int id_reclamation, int uid, String date_creation, String sujet, String description, String status, int priorité, String responsablle) {
        this.id_reclamation = id_reclamation;
        this.uid = uid;
        this.date_creation = date_creation;
        this.sujet = sujet;
        this.description = description;
        this.status = status;
        this.priorité = priorité;
        this.responsablle = responsablle;
    }

    public Reclamation(int uid, String date_creation, String sujet, String description, String status, int priorité, String responsablle) {
        this.uid = uid;
        this.date_creation = "01/01/2024";
        this.sujet = sujet;
        this.description = description;
        this.status = status;
        this.priorité = priorité;
        this.responsablle = responsablle;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriorité() {
        return priorité;
    }

    public void setPriorité(int priorité) {
        this.priorité = priorité;
    }

    public String getResponsablle() {
        return responsablle;
    }

    public void setResponsablle(String responsablle) {
        this.responsablle = responsablle;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", utilisateur_id=" + uid +
                ", date_creation='" + date_creation + '\'' +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priorité=" + priorité +
                ", responsablle=" + responsablle +
                '}';
    }
}
