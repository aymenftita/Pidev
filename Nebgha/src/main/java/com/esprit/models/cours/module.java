package com.esprit.models.cours;

public class module {
    private int id;
    private String titre;
    private String niveau;
    private String domaine;
    private String createur_id;

    public module(int id, String titre, String niveau, String domaine, String createur_id) {
        this.id = id;
        this.titre = titre;
        this.niveau = niveau;
        this.domaine = domaine;
        this.createur_id = createur_id;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", niveau=" + niveau +
                ", domaine='" + domaine + '\'' +
                ", createur_id='" + createur_id + '\'' +
                '}';
    }


}
