package be.kuleuven.csa.controller;

public class Boerderij {

    private int boerderij_ID;
    private String  boerderij_naam;
    private  String  boerderij_locatie;

   public Boerderij(){

    }

    @Override
    public String toString() {
        return "Boerderij{" +
                "boerderij_ID='" + boerderij_ID + '\'' +
                ", boerderij_naam='" + boerderij_naam + '\'' +
                ", boerderij_locatie=" + boerderij_locatie +
                '}';
    }

    public Boerderij(int boerderij_ID, String boerderij_naam, String boerderij_locatie) {
        this.boerderij_ID = boerderij_ID;
        this.boerderij_naam = boerderij_naam;
        this.boerderij_locatie = boerderij_locatie;
    }

    public int getBoerderij_ID() {
        return boerderij_ID;
    }

    public void setBoerderij_ID(int boerderij_ID) {
        this.boerderij_ID = boerderij_ID;
    }

    public String getBoerderij_naam() {
        return boerderij_naam;
    }

    public void setBoerderij_naam(String boerderij_naam) {
        this.boerderij_naam = boerderij_naam;
    }

    public String getBoerderij_locatie() {
        return boerderij_locatie;
    }

    public void setBoerderij_locatie(String boerderij_locatie) {
        this.boerderij_locatie = boerderij_locatie;
    }
}
