package com.esprit.models.ReclamationGroupModels;

public class Reclamation {

    private int idReclamation;
    private Utilisateur userId;
    private String dateCreation;
    private String sujet;
    private String description;
    private String status;
    private int priorite;
    private String responsable;

    public Reclamation() {
    }

    public Reclamation(int idReclamation, Utilisateur userId, String dateCreation, String sujet, String description, String status, int priorite, String responsable) {
        this.idReclamation = idReclamation;
        this.userId = userId;
        this.dateCreation = dateCreation;
        this.sujet = sujet;
        this.description = description;
        this.status = status;
        this.priorite = priorite;
        this.responsable = responsable;
    }

    public Reclamation(Utilisateur userId, String dateCreation, String sujet, String description, String status, int priorite, String responsablle) {
        this.userId = userId;
        this.dateCreation = dateCreation;
        this.sujet = sujet;
        this.description = description;
        this.status = status;
        this.priorite = priorite;
        this.responsable = responsablle;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public Utilisateur getUserId() {
        return userId;
    }

    public void setUserId(Utilisateur userId) {
        this.userId = userId;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
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

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsablle(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idReclamation=" + idReclamation +
                ", userId=" + userId +
                ", dateCreation='" + dateCreation + '\'' +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priorité=" + priorite +
                ", responsablle='" + responsable + '\'' +
                '}';
    }
}
