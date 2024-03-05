package com.esprit.tests;

import com.esprit.controllers.ChatboxController;
import com.esprit.controllers.NotificationUtil;
import com.esprit.controllers.reclamationControllers.AjouterReclamationController;
import com.esprit.models.*;
import com.esprit.services.*;
import javafx.application.Platform;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainProg {

    public static void main(String[] args) throws SQLException, IOException {
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
        /*UserGroupeService ugs = new UserGroupeService();
        System.out.println(us.rechercheUtilisateur(2));
        System.out.println("********************************************************");
        List<Groupe> g = new ArrayList<>();

        List<UserGroupe> uglist =ugs.ListUserGroupeByIdUser(2);
        System.out.println(uglist);
        System.out.println("********************************************************");
        for(UserGroupe ugl : uglist){
            g.add(gs.afficherById(ugl.getIdGroupe().getId_groupe()));
        }

        System.out.println(g);*/
        //ugs.ajouter(ug);
        //System.out.println(ms.afficher());

        //ms.modifier(new Message(23,23,"2024-02-15","hello everyone im knew here, i need some guidness"));

        //Groupe g2 = new Groupe(0,1,"test Title","test Description");

        //MessageService ms = new MessageService();

        //Message m = new Message(1,1,"dzdzd","hfhff");
        //System.out.println(ms.afficher());

        ChatboxController cbc=new ChatboxController();
        //cbc.AutoCorrectApi();
        AjouterReclamationController arc = new AjouterReclamationController();
        arc.AutoCorrectApi("basterd");

    }
}
