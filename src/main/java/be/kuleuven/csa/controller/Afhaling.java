package be.kuleuven.csa.controller;

// klasse voor haalt_af tbl
public class Afhaling {
       private Integer klant_ID;
       private Integer verkoopt_ID;
       private Integer haalt_af_week;

       public Afhaling(){

       }

    public Afhaling(Integer klant_ID, Integer verkoopt_ID, Integer haalt_af_week) {
        this.klant_ID = klant_ID;
        this.verkoopt_ID = verkoopt_ID;
        this.haalt_af_week = haalt_af_week;
    }

    public Integer getKlant_ID() {
        return klant_ID;
    }

    public void setKlant_ID(Integer klant_ID) {
        this.klant_ID = klant_ID;
    }

    public Integer getVerkoopt_ID() {
        return verkoopt_ID;
    }

    public void setVerkoopt_ID(Integer verkoopt_ID) {
        this.verkoopt_ID = verkoopt_ID;
    }

    public Integer getHaalt_af_week() {
        return haalt_af_week;
    }

    public void setHaalt_af_week(Integer haalt_af_week) {
        this.haalt_af_week = haalt_af_week;
    }
}
