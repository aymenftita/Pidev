package com.esprit.services;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static Utilisateur currentUser;
    private static Role currentRole;
    private static String resetCode;

    private static Map<String, String> resetCodeMap = new HashMap<>();

    public static String getResetCode(String email) {
        return resetCodeMap.get(email);
    }

    public static void setResetCode(String email, String resetCode) {
        resetCodeMap.put(email, resetCode);
    }

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
