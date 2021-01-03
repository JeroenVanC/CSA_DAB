package be.kuleuven.csa.controller;

public class AfhalingSub extends Afhaling {
    private String klant_naam;
    private String boerderij_naam;
    private String pakket_naam;

    public AfhalingSub() {
        super();
    }

    public AfhalingSub(Integer klant_ID, Integer verkoopt_ID, Integer haalt_af_week) {
        super(klant_ID, verkoopt_ID, haalt_af_week);
    }

    public String getKlant_naam() {
        return klant_naam;
    }

    public void setKlant_naam(String klant_naam) {
        this.klant_naam = klant_naam;
    }

    public String getBoerderij_naam() {
        return boerderij_naam;
    }

    public void setBoerderij_naam(String boerderij_naam) {
        this.boerderij_naam = boerderij_naam;
    }

    public String getPakket_naam() {
        return pakket_naam;
    }

    public void setPakket_naam(String pakket_naam) {
        this.pakket_naam = pakket_naam;
    }
}
