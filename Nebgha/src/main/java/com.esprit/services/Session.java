package com.esprit.services;

public class Session {
    private static int userId;
    private static String role;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        Session.role = role;
    }
}
