package be.kuleuven.csa.controller;

public class VerkooptSub extends Verkoopt {
    private String boerderij_naam;

    public VerkooptSub() {
        super();
    }

    public VerkooptSub(Integer verkoopt_ID, Integer boerderij_ID, String pakket_naam, Integer prijs, String boerderij_naam) {
        super(verkoopt_ID, boerderij_ID, pakket_naam, prijs);
        this.boerderij_naam = boerderij_naam;
    }

    public String getBoerderij_naam() {
        return boerderij_naam;
    }

    public void setBoerderij_naam(String boerderij_naam) {
        this.boerderij_naam = boerderij_naam;
    }
}
