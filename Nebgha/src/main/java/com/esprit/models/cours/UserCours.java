package com.esprit.models.cours;

public class UserCours {
    private int uid;
    private int cid;

    public UserCours(int uid, int cid) {
        this.uid = uid;
        this.cid = cid;
    }

    public UserCours() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "UserCours{" +
                "uid=" + uid +
                ", cid=" + cid +
                '}';
    }
}
