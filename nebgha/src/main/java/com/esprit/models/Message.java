package com.esprit.models;

public class Message {
    private int idMessage;
    private int idGroupe;
    private String dateCreation;
    private String text;


    public Message(int idMessage, int idGroupe, String dateCreation, String text) {
        this.idMessage = idMessage;
        this.idGroupe = idGroupe;
        this.dateCreation = dateCreation;
        this.text = text;
    }

    public Message(int idGroupe, String dateCreation, String text) {
        this.idGroupe = idGroupe;
        this.dateCreation = dateCreation;
        this.text = text;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return
                  idMessage +
                "                   " + idGroupe +
                "                   " + dateCreation + "                    " +
                "                   " + text ;

    }
}
