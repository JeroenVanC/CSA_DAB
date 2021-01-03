package be.kuleuven.csa.controller;

//klasse voor BEHOORT_TOT tabel
public class InhoudPakket {
    private Integer product_ID;
    private Integer verkoopt_ID;
    private Integer behoort_tot_week;
    private Integer behoort_tot_aantal;

    public InhoudPakket(){}

    public InhoudPakket(Integer product_ID, Integer verkoopt_ID, Integer behoort_tot_week, Integer behoort_tot_aantal) {
        this.product_ID = product_ID;
        this.verkoopt_ID = verkoopt_ID;
        this.behoort_tot_week = behoort_tot_week;
        this.behoort_tot_aantal = behoort_tot_aantal;
    }

    public Integer getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(Integer product_ID) {
        this.product_ID = product_ID;
    }

    public Integer getVerkoopt_ID() {
        return verkoopt_ID;
    }

    public void setVerkoopt_ID(Integer verkoopt_ID) {
        this.verkoopt_ID = verkoopt_ID;
    }

    public Integer getBehoort_tot_week() {
        return behoort_tot_week;
    }

    public void setBehoort_tot_week(Integer behoort_tot_week) {
        this.behoort_tot_week = behoort_tot_week;
    }

    public Integer getBehoort_tot_aantal() {
        return behoort_tot_aantal;
    }

    public void setBehoort_tot_aantal(Integer behoort_tot_aantal) {
        this.behoort_tot_aantal = behoort_tot_aantal;
    }
}
