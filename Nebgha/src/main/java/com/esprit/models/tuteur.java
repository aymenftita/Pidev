package com.esprit.models;

import java.util.Date;

public final class tuteur extends utilisateur {
    public int id;
    public String domaine;
    public double experience;

    public tuteur( int id1,String nom, String prenom,
                   String email,
                   String password, Role role,
                   String domaine, double experience) {
        super(id1,nom, prenom, email, password, role);
        this.id = id1;
        this.domaine = domaine;
        this.experience = experience;
    }
    public tuteur( String nom, String prenom,
                   String email,
                   String password, Role role,
                   String domaine, double experience) {
        super(nom, prenom, email, password, role);

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

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
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
