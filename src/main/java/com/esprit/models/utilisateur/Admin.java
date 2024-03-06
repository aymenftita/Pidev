package com.esprit.models.utilisateur;

public final class Admin extends Utilisateur {

    public Admin(int id, String nom,
                 String prenom,
                 String email, String password, Role role)
    {
        super( id,nom, prenom, email, password, role);
    }
    public Admin(String nom,
                 String prenom,
                 String email, String password, Role role)
    {
        super( nom, prenom, email, password, role);
    }

    @Override
    public String toString() {
        return "admin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                "} " ;
    }
}
