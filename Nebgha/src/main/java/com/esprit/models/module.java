package com.esprit.models;

public class module {
    private int id_module;
    private String titre;
    private String niveau;
    private String domaine;
    private String createur_id;

    public module(int id_module, String titre, String niveau, String domaine, String createur_id) {
        this.id_module = id_module;
        this.titre = titre;
        this.niveau = niveau;
        this.domaine = domaine;
        this.createur_id = createur_id;
    }

    public module(int id_module, String titre, String domaine, String createur_id){
        this.titre = titre;
        this.domaine = domaine;
        this.createur_id = createur_id;
    }

    public int getId_module() {
        return id_module;
    }

    public void setId_module(int id_module) {
        this.id_module = id_module;
    }

    public  String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getCreateur_id() {
        return createur_id;
    }

    public void setCreateur_id(String createur_id) {
        this.createur_id = createur_id;
    }

    @Override
    public String toString() {
        return "module{" +
                "id_module=" + id_module +
                ", titre='" + titre + '\'' +
                ", niveau=" + niveau +
                ", domaine='" + domaine + '\'' +
                ", uid='" + createur_id + '\'' +
                '}';
    }
}
