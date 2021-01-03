package be.kuleuven.csa.controller;

public class InschrijvingSub extends Inschrijving{

    private String boerderij_naam;
    private String klant_naam;

    public InschrijvingSub() {
        super();
    }

    public InschrijvingSub(int contract_nr, String boerderij_ID, String pakket_naam, String klant_ID, String contract_jaar, String boerderij_naam, String klant_naam) {
        super(contract_nr, boerderij_ID, pakket_naam, klant_ID, contract_jaar);
        this.boerderij_naam = boerderij_naam;
        this.klant_naam = klant_naam;

    }

    public String getBoerderij_naam() {
        return boerderij_naam;
    }

    public void setBoerderij_naam(String boerderij_naam) {
        this.boerderij_naam = boerderij_naam;
    }

    public String getKlant_naam() {
        return klant_naam;
    }

    public void setKlant_naam(String klant_naam) {
        this.klant_naam = klant_naam;
    }

//    @Override
//    public int getContract_nr() {
//        return super.getContract_nr();
//    }
//
//    @Override
//    public void setContract_nr(int contract_nr) {
//        super.setContract_nr(contract_nr);
//    }
//
//    @Override
//    public String getBoerderij_ID() {
//        return super.getBoerderij_ID();
//    }
//
//    @Override
//    public void setBoerderij_ID(String boerderij_ID) {
//        super.setBoerderij_ID(boerderij_ID);
//    }
//
//    @Override
//    public String getPakket_naam() {
//        return super.getPakket_naam();
//    }
//
//    @Override
//    public void setPakket_naam(String pakket_naam) {
//        super.setPakket_naam(pakket_naam);
//    }
//
//    @Override
//    public String getKlant_ID() {
//        return super.getKlant_ID();
//    }
//
//    @Override
//    public void setKlant_ID(String klant_ID) {
//        super.setKlant_ID(klant_ID);
//    }
//
//    @Override
//    public String getContract_jaar() {
//        return super.getContract_jaar();
//    }
//
//    @Override
//    public void setContract_jaar(String contract_jaar) {
//        super.setContract_jaar(contract_jaar);
//    }
}
