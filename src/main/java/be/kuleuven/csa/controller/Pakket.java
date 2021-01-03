package be.kuleuven.csa.controller;

public class Pakket {
    private String pakket_naam;
    private Integer pakket_volwassenen;
    private Integer pakket_kinderen;

    public Pakket(){

    }
    public Pakket(String pakket_naam, Integer pakket_volwassenen, Integer pakket_kinderen) {
        this.pakket_naam = pakket_naam;
        this.pakket_volwassenen = pakket_volwassenen;
        this.pakket_kinderen = pakket_kinderen;
    }

    public String getPakket_naam() {
        return pakket_naam;
    }

    public void setPakket_naam(String pakket_naam) {
        this.pakket_naam = pakket_naam;
    }

    public Integer getPakket_volwassenen() {
        return pakket_volwassenen;
    }

    public void setPakket_volwassenen(Integer pakket_volwassenen) {
        this.pakket_volwassenen = pakket_volwassenen;
    }

    public Integer getPakket_kinderen() {
        return pakket_kinderen;
    }

    public void setPakket_kinderen(Integer pakket_kinderen) {
        this.pakket_kinderen = pakket_kinderen;
    }
}
