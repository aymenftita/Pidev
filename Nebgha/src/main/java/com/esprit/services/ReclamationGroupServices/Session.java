package com.esprit.services.ReclamationGroupServices;

public class Session  {
    public static int userId ;
    private static String nom;
    private static String prenom;

    private static String email;
    private static String password;
    private static String role;
    private static String specialite;
    private static int niveau;
    private static String domaine;
    private static int experience;


    public Session() {
    }



    public static void login(int userId) {
        Session.userId =userId;
    }

    public static void logout() {
        Session.userId = Integer.parseInt(null);
    }

    public static boolean isLoggedIn() {
        return userId != Integer.parseInt(null);
    }

    public static int getUserId() {
        return userId;
    }
}
