package be.kuleuven.csa.controller;

public class Inschrijving {

    private int contract_nr;
    private String boerderij_ID;
    private String pakket_naam;
    private String klant_ID;
    private String contract_jaar;

    public  Inschrijving(){

    }

    public Inschrijving(int contract_nr, String boerderij_ID, String pakket_naam, String klant_ID, String contract_jaar) {
        this.contract_nr = contract_nr;
        this.boerderij_ID = boerderij_ID;
        this.pakket_naam = pakket_naam;
        this.klant_ID = klant_ID;
        this.contract_jaar = contract_jaar;
    }

    public int getContract_nr() {
        return contract_nr;
    }

    public void setContract_nr(int contract_nr) {
        this.contract_nr = contract_nr;
    }

    public String getBoerderij_ID() {
        return boerderij_ID;
    }

    public void setBoerderij_ID(String boerderij_ID) {
        this.boerderij_ID = boerderij_ID;
    }

    public String getPakket_naam() {
        return pakket_naam;
    }

    public void setPakket_naam(String pakket_naam) {
        this.pakket_naam = pakket_naam;
    }

    public String getKlant_ID() {
        return klant_ID;
    }

    public void setKlant_ID(String klant_ID) {
        this.klant_ID = klant_ID;
    }

    public String getContract_jaar() {
        return contract_jaar;
    }

    public void setContract_jaar(String contract_jaar) {
        this.contract_jaar = contract_jaar;
    }
}
