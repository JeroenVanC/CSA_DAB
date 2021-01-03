package be.kuleuven.csa.controller;

public class Klant {

    private Integer klant_ID;
    private String klant_naam;
    private String klant_adress;
    private Integer klant_leeftijd;

    public Klant(){

    }

    @Override
    public String toString() {
        return "Klant{" +
                "klant_ID='" + klant_ID + '\'' +
                ", klant_naam='" + klant_naam + '\'' +
                ", klant_adress=" + klant_adress +
                ", klant_leeftijd=" + klant_leeftijd +
                '}';
    }

    public Klant(Integer klant_ID, String klant_naam, String klant_adress, Integer klant_leeftijd) {
        this.klant_ID = klant_ID;
        this.klant_naam = klant_naam;
        this.klant_adress = klant_adress;
        this.klant_leeftijd = klant_leeftijd;
    }

    public Integer getKlant_ID() {
        return klant_ID;
    }

    public String getKlant_naam() {
        return klant_naam;
    }

    public String getKlant_adress() {
        return klant_adress;
    }

    public Integer getKlant_leeftijd() {
        return klant_leeftijd;
    }

    public void setKlant_ID(Integer klant_ID) {
        this.klant_ID = klant_ID;
    }

    public void setKlant_naam(String klant_naam) {
        this.klant_naam = klant_naam;
    }

    public void setKlant_adress(String klant_adress) {
        this.klant_adress = klant_adress;
    }

    public void setKlant_leeftijd(Integer klant_leeftijd) {
        this.klant_leeftijd = klant_leeftijd;
    }
}
