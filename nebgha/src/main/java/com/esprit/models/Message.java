package com.esprit.models;

public class Message {
    private int idMessage;
    private int uid;
    private int idGroupe;
    private String dateCreation;
    private String text;
    private int signal;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Message(int idMessage,  int idGroupe, String dateCreation, String text,int uid ,int signal) {
        this.idMessage = idMessage;
        this.idGroupe = idGroupe;
        this.dateCreation = dateCreation;
        this.text = text;
        this.uid=uid;
        this.signal=signal;
    }

    public Message(int idGroupe, String dateCreation, String text,int uid,int signal) {
        this.idGroupe = idGroupe;
        this.dateCreation = dateCreation;
        this.text = text;
        this.uid=uid;
        this.signal=signal;
    }

    public int isSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
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
        return "Message{" +
                "idMessage=" + idMessage +
                ", uid=" + uid +
                ", idGroupe=" + idGroupe +
                ", dateCreation='" + dateCreation + '\'' +
                ", text='" + text + '\'' +
                ", signal=" + signal +
                '}';
    }
}
