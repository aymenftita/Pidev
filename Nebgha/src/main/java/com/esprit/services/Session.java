package com.esprit.services;

import com.esprit.models.utilisateur;

public class Session {
    private static utilisateur currentUser;
    private static String currentRole;

    public static void setCurrentUser(utilisateur user) {
        currentUser = user;
    }

    public static utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
        currentRole = null;
    }
}
