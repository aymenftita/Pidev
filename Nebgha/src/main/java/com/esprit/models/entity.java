package com.esprit.models;

public class entity {

    private int attribut1;
    private String attribut2;
    private String attribut3;

    public entity(int attribut1, String attribut2, String attribut3) {
        this.attribut1 = attribut1;
        this.attribut2 = attribut2;
        this.attribut3 = attribut3;
    }

    public entity(String attribut2, String attribut3) {
        this.attribut2 = attribut2;
        this.attribut3 = attribut3;
    }


    public int getAttribut1() {
        return attribut1;
    }

    public void setAttribut1(int attribut1) {
        this.attribut1 = attribut1;
    }

    public String getAttribut2() {
        return attribut2;
    }

    public void setAttribut2(String attribut2) {
        this.attribut2 = attribut2;
    }

    public String getAttribut3() {
        return attribut3;
    }

    public void setAttribut3(String attribut3) {
        this.attribut3 = attribut3;
    }

    @Override
    public String toString() {
        return "entity{" +
                "attribut1=" + attribut1 +
                ", attribut2='" + attribut2 + '\'' +
                ", attribut3='" + attribut3 + '\'' +
                '}';
    }

}
