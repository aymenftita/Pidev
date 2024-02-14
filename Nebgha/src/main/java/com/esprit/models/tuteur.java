package com.esprit.models;

import java.util.Date;

public final class tuteur extends utilisateur {
    public int id;
    public String domaine;
    public String experience;

    public tuteur( String nom, String prenom, Date date_naissance, String email, String password, Role role, int id1, String domaine, String experience) {
        super(id1,nom, prenom, date_naissance, email, password, role);
        this.id = id1;
        this.domaine = domaine;
        this.experience = experience;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "tuteur{" +
                "id=" + id +
                ", domaine='" + domaine + '\'' +
                ", experience='" + experience + '\'' +
                "} " + super.toString();
    }
}
