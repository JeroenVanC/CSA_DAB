package be.kuleuven.csa.controller;

public class Tips {
    private String _id;
    private String _rev;
    private String product_naam;
    private String tip;
    private String auteur;

    public Tips(){

    }

    public Tips( String product_naam, String tip, String auteur) {
        this.product_naam = product_naam;
        this.tip = tip;
        this.auteur = auteur;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getProduct_naam() {
        return product_naam;
    }

    public void setProduct_naam(String product_naam) {
        this.product_naam = product_naam;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String get_id() {
        return _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }
}
