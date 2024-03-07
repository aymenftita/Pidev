package com.esprit.services.cours;

public class Session {
    private static  int uid;

    public static int getUid() {
        return uid;
    }

    public static void setUid(int uid) {
        Session.uid = uid;
    }
}
