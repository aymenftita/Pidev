package com.esprit.services.ReclamationGroupServices;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(T t);
    public List<T> afficher();
}