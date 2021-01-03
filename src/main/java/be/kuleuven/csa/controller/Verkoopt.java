package be.kuleuven.csa.controller;

public class Verkoopt {
    private Integer verkoopt_ID;
    private Integer boerderij_ID;
    private String pakket_naam;
    private Integer prijs;

    public Verkoopt() {

    }

    public Verkoopt(Integer verkoopt_ID, Integer boerderij_ID, String pakket_naam, Integer prijs) {
        this.verkoopt_ID = verkoopt_ID;
        this.boerderij_ID = boerderij_ID;
        this.pakket_naam = pakket_naam;
        this.prijs = prijs;
    }

    public Integer getVerkoopt_ID() {
        return verkoopt_ID;
    }

    public void setVerkoopt_ID(Integer verkoopt_ID) {
        this.verkoopt_ID = verkoopt_ID;
    }

    public Integer getBoerderij_ID() {
        return boerderij_ID;
    }

    public void setBoerderij_ID(Integer boerderij_ID) {
        this.boerderij_ID = boerderij_ID;
    }

    public String getPakket_naam() {
        return pakket_naam;
    }

    public void setPakket_naam(String pakket_naam) {
        this.pakket_naam = pakket_naam;
    }

    public Integer getPrijs() {
        return prijs;
    }

    public void setPrijs(Integer prijs) {
        this.prijs = prijs;
    }
}
