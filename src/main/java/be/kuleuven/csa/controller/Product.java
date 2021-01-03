package be.kuleuven.csa.controller;

public class Product {

    private Integer product_id;
    private String product_naam;
    private Integer product_normaal;
    private String product_tip;
    private String product_tip_auteur;

    public  Product(){

    }

    public Product(Integer product_id, String product_naam, Integer product_normaal, String product_tip, String product_tip_auteur) {
        this.product_id = product_id;
        this.product_naam = product_naam;
        this.product_normaal = product_normaal;
        this.product_tip = product_tip;
        this.product_tip_auteur = product_tip_auteur;
    }

    @Override
    public String toString() {
        return "Klant{" +
                "product_id='" + product_id + '\'' +
                ", product_naam='" + product_naam + '\'' +
                ", product_normaal=" + product_normaal + '\'' +
                ", product_tip=" + product_tip + '\'' +
                ", product_tip_auteur=" + product_tip_auteur+ '\'' +
                '}';
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_naam() {
        return product_naam;
    }

    public void setProduct_naam(String product_naam) {
        this.product_naam = product_naam;
    }

    public Integer getProduct_normaal() {
        return product_normaal;
    }

    public void setProduct_normaal(Integer product_normaal) {
        this.product_normaal = product_normaal;
    }

    public String getProduct_tip() {
        return product_tip;
    }

    public void setProduct_tip(String product_tip) {
        this.product_tip = product_tip;
    }

    public String getProduct_tip_auteur() {
        return product_tip_auteur;
    }

    public void setProduct_tip_auteur(String product_tip_auteur) {
        this.product_tip_auteur = product_tip_auteur;
    }
}
