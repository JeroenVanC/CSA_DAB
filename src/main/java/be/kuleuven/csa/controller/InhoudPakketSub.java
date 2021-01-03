package be.kuleuven.csa.controller;

public class InhoudPakketSub extends InhoudPakket {
    private String product_naam;
    private String pakket_naam;
    private String boerderij_naam;



    public InhoudPakketSub() {
        super();
    }

    public InhoudPakketSub(Integer product_ID, Integer verkoopt_ID, Integer behoort_tot_week, Integer behoort_tot_aantal, String product_naam, String pakket_naam, String boerderij_naam) {
        super(product_ID, verkoopt_ID, behoort_tot_week, behoort_tot_aantal);
        this.product_naam = product_naam;
        this.pakket_naam = pakket_naam;
        this.boerderij_naam = boerderij_naam;
    }

    public String getProduct_naam() {
        return product_naam;
    }

    public void setProduct_naam(String product_naam) {
        this.product_naam = product_naam;
    }

    public String getPakket_naam() {
        return pakket_naam;
    }

    public void setPakket_naam(String pakket_naam) {
        this.pakket_naam = pakket_naam;
    }
    public String getBoerderij_naam() {
        return boerderij_naam;
    }

    public void setBoerderij_naam(String boerderij_naam) {
        this.boerderij_naam = boerderij_naam;
    }
}
