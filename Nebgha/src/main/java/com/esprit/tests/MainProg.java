package com.esprit.tests;

import com.esprit.models.*;
//import com.esprit.services.EntityService;
import com.esprit.services.*;

import java.sql.Date;
import java.util.List;

public class MainProg {

    public static void main(String[] args) {

       /* etudiant e1 = new etudiant ("emna","bouzidi",
                Date.valueOf("2000-09-10"),"emnabouzidi@gmail.com",
                "258hy",Role.etudiant,3,"informatique");
        ServiceEtudiant se = new ServiceEtudiant();
        se.ajouter(e1);
        se.modifier(e1);
       se.supprimer(e1);
        System.out.println(se.afficher());

        */



     /*  admin a1 = new admin(2,"naoufel","dridi",
                "naoufeldridi@gmail.com",
                "123456", Role.Administrateur);
        ServiceAdmin sa = new ServiceAdmin();

        System.out.println(sa.afficher());
        sa.modifier(new admin(2,"nour","dridi",
               "naoufeldridi@gmail.com",
                "123456", Role.Administrateur));

        sa.ajouter(a1);
System.out.println(sa.afficher());
        sa.modifier(new admin(2,"nour","dridi",
                Date.valueOf("2000-05-02"),"naoufeldridi@gmail.com",
                "123456", Role.administrateur));
        System.out.println(sa.afficher());
        sa.supprimer(a1);

         */






        /*ServiceTuteur sa = new ServiceTuteur();

       tuteur t1 = new tuteur("emna","test",
                "bouchibaasma@gmail.com","1235hyt",Role.Tuteur,
                "Math","5");
       // sa.ajouter(t1);
        /*sa.modifier(new tuteur(10,"emna","test",
                "bouchibaasma@gmail.com","1235hyt",Role.Tuteur,
                "Math","5"));*/
       /* sa.supprimer(new tuteur(10,"emna","test",
                "bouchibaasma@gmail.com","1235hyt",Role.Tuteur,
                "Math","5"));
        System.out.println(sa.afficher());

        */




        ServiceUtilisateur sa = new ServiceUtilisateur();
       utilisateur u1 = new utilisateur(3,"ayda","talbi",
               "talbiayda@gmail.com","12389657hyt",Role.utilisateur);

        sa.ajouter(u1);
/*
        sa.modifier(u1);

        sa.afficher();

 */



        /*ServiceEtudiant se = new ServiceEtudiant();
        etudiant e = new etudiant("emna","bouzidi","gmail","12345678",Role.etudiant,1,"info");
        se.ajouter(e);*/

    }



     }






