package com.esprit.tests;

import com.esprit.models.*;
import com.esprit.services.*;

import java.sql.Date;
import java.sql.SQLException;


public class MainProg {

    public static void main(String[] args) throws SQLException {
        GroupeService gs = new GroupeService();
        ReclamationService rs = new ReclamationService();
        UtilisateurService us =new UtilisateurService();
        MessageService ms =new MessageService();
        //test de reclamation valid
        Utilisateur user = new Utilisateur(
                8,
                "nomTest",
                "prenomTest",
                "nom.prenom@esprit.com",
                "12345678",
                "roleTest",
                "specTest",
                100,
                "DomaineTest",
                1000);


        //System.out.println(gs.afficherG(2));
        /*Groupe g3 = new Groupe(user,"testttttttttt","descriptiontesttttttttttttt");

        gs.ajouter(g3);*/
        /*GroupService Gs=new GroupService();

        Gs.modifier(g3);

        System.out.println(us.rechercheUtilisateur(2));*/


        //UserGroupe ug = new UserGroupe(u,g3);
        UserGroupeService ugs = new UserGroupeService();
        System.out.println(ugs.afficherUG(2,20));
        //ugs.ajouter(ug);
        //System.out.println(ms.afficher());

        //ms.modifier(new Message(23,23,"2024-02-15","hello everyone im knew here, i need some guidness"));

        //Groupe g2 = new Groupe(0,1,"test Title","test Description");

        //MessageService ms = new MessageService();

        //Message m = new Message(1,1,"dzdzd","hfhff");



    }
}
