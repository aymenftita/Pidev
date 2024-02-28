package com.esprit.services;

import com.esprit.models.Evenement;
import com.esprit.models.Localisation;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
    public void modifier(T t);

    public  void supprimer(T t) throws SQLException;

    public List<T> afficher();

    List<Evenement> recuperer() throws SQLException;

    List<Localisation> recupererLocalisation() throws SQLException;
}
