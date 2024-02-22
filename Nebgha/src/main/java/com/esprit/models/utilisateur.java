package com.esprit.models;
import com.esprit.models.Role;
public  class utilisateur {
    public int id;
    public String nom;
    public String prenom;
    public String email;
    public String password;
    public Role role;


    public utilisateur(int id, String nom, String prenom,
                         String email,
                       String password, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;

        this.email = email;
        this.password = password;
        this.role = role;
    }

    public utilisateur( String nom, String prenom,
                       String email,
                       String password, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", date_naissance=" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}