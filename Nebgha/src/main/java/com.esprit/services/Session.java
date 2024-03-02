package com.esprit.services;

import com.esprit.models.Role;
import com.esprit.models.Utilisateur;

public class Session {
    private static Utilisateur currentUser;
    private static Role currentRole;

    public static void setCurrentUser(Utilisateur user) {
        currentUser = user;
    }

    public static Utilisateur getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentRole(Role role) {
        currentRole = role;
    }

    public static Role getCurrentRole() {
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