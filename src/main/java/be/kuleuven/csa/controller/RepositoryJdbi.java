package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class RepositoryJdbi {

    private Jdbi jdbi;

    public RepositoryJdbi() {
        this.jdbi = Jdbi.create("jdbc:sqlite:CSA_DAB.db");

    }

    public ObservableList<Boerderij> getBoerderijen() {
        var query = "SELECT * FROM BOERDERIJ";

        List<Boerderij> boerderijen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(Boerderij.class)
                    .list();

        });

        return FXCollections.observableArrayList(boerderijen);
    }

    public ObservableList<Klant> getKlanten() {
        var query = "SELECT * FROM KLANT";

        List<Klant> klanten = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(Klant.class)
                    .list();
        });

        return FXCollections.observableArrayList(klanten);
    }

    public ObservableList<Pakket> getPakket() {
        var query = "SELECT * FROM PAKKET";

        List<Pakket> paketten = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(Pakket.class)
                    .list();
        });

        return FXCollections.observableArrayList(paketten);
    }

    public ObservableList<Product> getProducten() {
        var query = "SELECT * FROM PRODUCT";

        List<Product> producten = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(Product.class)
                    .list();
        });

        return FXCollections.observableArrayList(producten);
    }

    public ObservableList<Inschrijving> getInschrijvingen() {
        var query = "SELECT * FROM ZIT_IN_CONTRACT";

        List<Inschrijving> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(Inschrijving.class)
                    .list();
        });

        return FXCollections.observableArrayList(inschrijvingen);
    }

    public ObservableList<InschrijvingSub> getInschrijvingenSub() {
        var query = "SELECT BOERDERIJ.boerderij_naam, ZIT_IN_CONTRACT.boerderij_ID,  KLANT.klant_naam,ZIT_IN_CONTRACT.klant_ID , ZIT_IN_CONTRACT.contract_jaar, ZIT_IN_CONTRACT.contract_nr, ZIT_IN_CONTRACT.pakket_naam\n" +
                "FROM ((ZIT_IN_CONTRACT\n" +
                "INNER JOIN BOERDERIJ ON BOERDERIJ.boerderij_ID =  ZIT_IN_CONTRACT.boerderij_ID)\n" +
                "INNER JOIN KLANT ON KLANT.klant_ID = ZIT_IN_CONTRACT.klant_ID)";

        List<InschrijvingSub> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(InschrijvingSub.class)
                    .list();
        });

        return FXCollections.observableArrayList(inschrijvingen);
    }

    public ObservableList<VerkooptSub> getVerkooptSub() {
        var query = "SELECT VERKOOPT.verkoopt_ID,  BOERDERIJ.boerderij_naam, VERKOOPT.boerderij_ID, VERKOOPT.pakket_naam, VERKOOPT.prijs\n" +
                "FROM VERKOOPT\n" +
                "INNER JOIN BOERDERIJ ON BOERDERIJ.boerderij_ID = VERKOOPT.boerderij_ID";

        List<VerkooptSub> verkopingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(VerkooptSub.class)
                    .list();
        });

        return FXCollections.observableArrayList(verkopingen);
    }

    public ObservableList<AfhalingSub> getAhalingSub() {
        var query = "SELECT KLANT.klant_naam, HAALT_AF.haalt_af_week,  BOERDERIJ.boerderij_naam, PAKKET.pakket_naam\n" +
                "FROM(( ((HAALT_AF\n" +
                "INNER JOIN KLANT on KLANT.klant_ID = HAALT_AF.klant_ID)\n" +
                "INNER JOIN VERKOOPT on VERKOOPT.verkoopt_ID = HAALT_AF.verkoopt_ID ) \n" +
                "INNER JOIN BOERDERIJ on VERKOOPT.boerderij_ID = BOERDERIJ.boerderij_ID)\n" +
                "INNER JOIN PAKKET on VERKOOPT.pakket_naam = PAKKET.pakket_naam)";

        List<AfhalingSub> afhalingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(AfhalingSub.class)
                    .list();
        });

        return FXCollections.observableArrayList(afhalingen);
    }

    public ObservableList<InhoudPakketSub> getInhoudPakketSub() {
        var query = "SELECT PRODUCT.product_naam, PAKKET.pakket_naam,  BEHOORT_TOT.behoort_tot_aantal, BEHOORT_TOT.behoort_tot_week, BOERDERIJ.boerderij_naam\n" +
                "FROM( (((BEHOORT_TOT\n" +
                "INNER JOIN PRODUCT on PRODUCT.product_ID = BEHOORT_TOT.product_ID)\n" +
                "INNER JOIN VERKOOPT on VERKOOPT.verkoopt_ID = BEHOORT_TOT.verkoopt_ID ) \n" +
                "INNER JOIN PAKKET on VERKOOPT.pakket_naam = PAKKET.pakket_naam) \n" +
                "INNER JOIN BOERDERIJ on VERKOOPT.boerderij_ID = BOERDERIJ.boerderij_ID)";

        List<InhoudPakketSub> inhoudPaketten = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapToBean(InhoudPakketSub.class)
                    .list();
        });

        return FXCollections.observableArrayList(inhoudPaketten);
    }

    public ObservableList<String> getBoerderijNamen() {
        var query = "SELECT boerderij_naam FROM BOERDERIJ";

        List<String> boerNamen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });

        return FXCollections.observableArrayList(boerNamen);
    }

    public ObservableList<String> getPrijzen() {
        var query = "SELECT prijs FROM VERKOOPT";

        List<String> prijzen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });

        return FXCollections.observableArrayList(prijzen);
    }

    public ObservableList<String> getKlantNamen() {
        var query = "SELECT klant_naam FROM KLANT";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });

        return FXCollections.observableArrayList(inschrijvingen);
    }

    public ObservableList<String> getProfuctenNaam() {
        var query = "SELECT product_naam FROM PRODUCT";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });

        return FXCollections.observableArrayList(inschrijvingen);
    }

    public int getKlantID(String klant_naam) {
        var query = "SELECT klant_ID FROM KLANT WHERE klant_naam ='" + klant_naam + "'";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });
        return Integer.parseInt(inschrijvingen.toArray()[0].toString());
    }

    public int getProductID(String productNaam) {
        var query = "SELECT product_ID FROM PRODUCT WHERE product_naam ='" + productNaam + "'";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });
        return Integer.parseInt(inschrijvingen.toArray()[0].toString());
    }

    public int getBoerderijID(String boerNaam) {
        var query = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boerNaam + "'";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });
        return Integer.parseInt(inschrijvingen.toArray()[0].toString());
    }

    public int getVerkooptID(int boerID, String pakketNaam) {
        var query = "SELECT verkoopt_ID FROM VERKOOPT WHERE boerderij_ID ='" + boerID + "' and pakket_naam ='" + pakketNaam + "'";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });
        return Integer.parseInt(inschrijvingen.toArray()[0].toString());
    }

    public ObservableList<String> getSizes() {
        var query = "SELECT pakket_naam FROM PAKKET";

        List<String> inschrijvingen = jdbi.withHandle(handle -> {
            return handle.createQuery(query)
                    .mapTo(String.class)
                    .list();
        });

        return FXCollections.observableArrayList(inschrijvingen);
    }

    public void insertBoerderij(String naam, String locatie) {
        var querry = "INSERT INTO BOERDERIJ(boerderij_naam, boerderij_locatie) VALUES (?, ?)";


        jdbi.withHandle(handle -> {
            handle.createUpdate(querry)
                    .bind(0, naam)
                    .bind(1, locatie)
                    .execute();
            return null;
        });
    }

    public void insertKlant(String naam, String locatie, String leeftijd) {
        var querry = "INSERT INTO KLANT(klant_naam, klant_adress, klant_leeftijd) VALUES (?, ?, ?)";


        jdbi.withHandle(handle -> {
            handle.createUpdate(querry)
                    .bind(0, naam)
                    .bind(1, locatie)
                    .bind(2, leeftijd)
                    .execute();
            return null;
        });
    }

    public void insertProduct(String naam, int normaal) {
        var querry = "INSERT INTO PRODUCT(product_naam, product_normaal) VALUES (?, ?)";


        jdbi.withHandle(handle -> {
            handle.createUpdate(querry)
                    .bind(0, naam)
                    .bind(1, normaal)
                    .execute();
            return null;
        });
    }

    public void insertInschrijving(String boer_naam, String pakket_naam, String klant_naam, String jaar) {

        var query2 = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        var query3 = "SELECT klant_ID FROM KLANT WHERE klant_naam ='" + klant_naam + "'";
        var querry = "INSERT INTO ZIT_IN_CONTRACT(boerderij_ID, pakket_naam, klant_ID, contract_jaar) VALUES (?, ?, ?, ?)";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(query2)
                    .mapTo(String.class)
                    .list();
        });

        List<String> klantID = jdbi.withHandle(handle -> {
            return handle.createQuery(query3)
                    .mapTo(String.class)
                    .list();
        });

        System.out.print(boerID);
        jdbi.withHandle(handle -> {
            handle.createUpdate(querry)
                    .bind(0, boerID.toArray()[0])
                    .bind(1, pakket_naam)
                    .bind(2, klantID.toArray()[0])
                    .bind(3, jaar)
                    .execute();
            return null;
        });
    }

    public void insertPrijzen(String boer_naam, String pakket_naam, String prijs) {

        var query2 = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        var querry = "INSERT INTO VERKOOPT(boerderij_ID, pakket_naam, prijs) VALUES (?, ?, ?)";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(query2)
                    .mapTo(String.class)
                    .list();
        });

        System.out.print(boerID);
        jdbi.withHandle(handle -> {
            handle.createUpdate(querry)
                    .bind(0, boerID.toArray()[0])
                    .bind(1, pakket_naam)
                    .bind(2, prijs)
                    .execute();
            return null;
        });
    }

    public void insertAfhalingen(String klant_naam, String boernaam, String size, Integer week) {

        var queryKID = "SELECT klant_ID FROM KLANT WHERE klant_naam ='" + klant_naam + "'";
        var queryBID = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boernaam + "'";
        var query = "INSERT INTO HAALT_AF(klant_ID, verkoopt_ID, haalt_af_week) VALUES (?, ?, ?)";
        List<String> klantID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryKID)
                    .mapTo(String.class)
                    .list();
        });

        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryBID)
                    .mapTo(String.class)
                    .list();
        });

        var queryVID = "SELECT verkoopt_ID FROM VERKOOPT where\n" +
                " VERKOOPT.boerderij_ID = " + boerID.toArray()[0] + " and VERKOOPT.pakket_naam = '" + size + "'";
        List<String> verkooptID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryVID)
                    .mapTo(String.class)
                    .list();
        });

        System.out.print(klantID);
        jdbi.withHandle(handle -> {
            handle.createUpdate(query)
                    .bind(0, klantID.toArray()[0])
                    .bind(1, verkooptID.toArray()[0])
                    .bind(2, week)
                    .execute();
            return null;
        });
    }

    public void insertInhoudPakket(String productNaam, String size, Integer week, String boerNaam, Integer aantal) {

        var queryPID = "SELECT product_ID FROM PRODUCT WHERE product_naam ='" + productNaam + "'";
        var queryBID = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boerNaam + "'";
        var query = "INSERT INTO BEHOORT_TOT(product_ID, verkoopt_ID, behoort_tot_week, behoort_tot_aantal) VALUES (?, ?, ?, ?)";
        List<String> productID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryPID)
                    .mapTo(String.class)
                    .list();
        });

        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryBID)
                    .mapTo(String.class)
                    .list();
        });
        System.out.println(boerID);

        var queryVID = "SELECT verkoopt_ID FROM VERKOOPT where\n" +
                " VERKOOPT.boerderij_ID = " + boerID.toArray()[0] + " and VERKOOPT.pakket_naam = '" + size + "'";
        List<String> verkooptID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryVID)
                    .mapTo(String.class)
                    .list();
        });
        if (verkooptID.isEmpty()) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("JE MOET NOG PRIJZEN TOEVOEGEN");
            alert.setHeaderText("JE MOET NOG PRIJZEN TOEVOEGEN");
            alert.setContentText("VOOR BOERDERIJ VAN : " + boerNaam);
            alert.showAndWait();
        } else {
            jdbi.withHandle(handle -> {
                handle.createUpdate(query)
                        .bind(0, productID.toArray()[0])
                        .bind(1, verkooptID.toArray()[0])
                        .bind(2, week)
                        .bind(3, aantal)
                        .execute();
                return null;
            });
        }
    }


    public void deleteBoerderij(int boerderij_id, String tableNaam, String IDNaam) {

        var query = "DELETE FROM " + tableNaam + " WHERE " + IDNaam + "='" + boerderij_id + "'";
        System.out.print(query);
        jdbi.withHandle(handle -> {
            handle.execute(query);
            return null;
        });
    }

    public void deleteAfhaling(int deleteID, String tableNaam, String IDNaam, String extraclm, int extraValue) {

        var query = "DELETE FROM " + tableNaam + " WHERE " + IDNaam + "='" + deleteID + "' and " + extraclm + " ='" + extraValue + "'";
        System.out.print(query);
        jdbi.withHandle(handle -> {
            handle.execute(query);
            return null;
        });
    }

    public void deleteInhoudProd(int deleteID, String tableNaam, String IDNaam, String extraclm, int extraValue, int verkooptID) {

        var query = "DELETE FROM " + tableNaam + " WHERE " + IDNaam + "='" + deleteID + "' and " + extraclm + " ='" + extraValue + "' and BEHOORT_TOT.verkoopt_ID ='" + verkooptID + "'";
        System.out.println(query);
        System.out.print(query);
        jdbi.withHandle(handle -> {
            handle.execute(query);
            return null;
        });
    }


    public void updateBoerderij(String naam, String locatie, Integer ID) {
        var querry = "UPDATE BOERDERIJ SET boerderij_naam = '" + naam + "', boerderij_locatie = '" + locatie + "' WHERE boerderij_ID = " + ID + ";";
        jdbi.withHandle(handle -> {
            handle.execute(querry);
            System.out.println("UITGEVOERDE QUERY ::: " + querry);
            return null;
        });
    }

    public void updateKlant(String naam, String locatie, String leeftijd, Integer ID) {
        var querry = "UPDATE KLANT SET klant_naam = '" + naam + "', klant_adress = '" + locatie + "', klant_leeftijd = '" + leeftijd + "' WHERE klant_ID = " + ID + ";";
        jdbi.withHandle(handle -> {
            handle.execute(querry);
            System.out.println("UITGEVOERDE QUERY ::: " + querry);
            return null;
        });
    }

    public void updateProduct(String naam, Integer ID, int normaal) {
        var querry = "UPDATE PRODUCT SET product_naam = '" + naam + "', product_normaal = '" + normaal + "' WHERE product_id = " + ID + ";";
        jdbi.withHandle(handle -> {
            handle.execute(querry);
            System.out.println("UITGEVOERDE QUERY ::: " + querry);
            return null;
        });
    }

    public void updateInschrijving(String boer_naam, String pakket_naam, String klant_naam, String jaar, Integer ID) {

        var query2 = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        var query3 = "SELECT klant_ID FROM KLANT WHERE klant_naam ='" + klant_naam + "'";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(query2)
                    .mapTo(String.class)
                    .list();
        });

        List<String> klantID = jdbi.withHandle(handle -> {
            return handle.createQuery(query3)
                    .mapTo(String.class)
                    .list();
        });


        var querry = "UPDATE ZIT_IN_CONTRACT SET boerderij_ID = '" + boerID.toArray()[0] + "', pakket_naam = '" + pakket_naam + "', klant_ID = '" + klantID.toArray()[0] + "', contract_jaar = '" + jaar + "' WHERE contract_nr = " + ID + ";";
        System.out.println("UITGEVOERDE QUERY ::: " + querry);
        jdbi.withHandle(handle -> {
            handle.execute(querry);

            return null;
        });
    }

    public void updateAfhalingen(String boer_naam, String size, String klant_naam, int week, int prevWeek) {

        var queryKID = "SELECT klant_ID FROM KLANT WHERE klant_naam ='" + klant_naam + "'";
        var queryBID = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryBID)
                    .mapTo(String.class)
                    .list();
        });

        List<String> klantID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryKID)
                    .mapTo(String.class)
                    .list();
        });
        var queryVID = "SELECT verkoopt_ID FROM VERKOOPT where\n" +
                " VERKOOPT.boerderij_ID = " + boerID.toArray()[0] + " and VERKOOPT.pakket_naam = '" + size + "'";
        System.out.print(queryVID);
        List<String> verkooptID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryVID)
                    .mapTo(String.class)
                    .list();
        });

        var querry = "UPDATE HAALT_AF SET haalt_af_week = " + week + " WHERE HAALT_AF.verkoopt_ID = " + verkooptID.toArray()[0] + " and HAALT_AF.klant_ID = " + klantID.toArray()[0] + " and HAALT_AF.haalt_af_week ='" + prevWeek + "';";
        System.out.println("UITGEVOERDE QUERY ::: " + querry);
        jdbi.withHandle(handle -> {
            handle.execute(querry);

            return null;
        });
    }
    public void updatePakket( String pakket_naam, int volw, int kind) {

        var querry = "UPDATE PAKKET SET pakket_volwassenen = " + volw + ", pakket_kinderen ='" + kind + "' WHERE pakket_naam = '" + pakket_naam + "';";
        System.out.println("UITGEVOERDE QUERY ::: " + querry);
        jdbi.withHandle(handle -> {
            handle.execute(querry);

            return null;
        });
    }

    public void updateInhoudPakket(String boer_naam, String size, String productNaam, int week, int prevWeek, int aantal, int PrevAantal) {

        var queryPID = "SELECT product_ID FROM PRODUCT WHERE product_naam ='" + productNaam + "'";
        var queryBID = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryBID)
                    .mapTo(String.class)
                    .list();
        });

        List<String> prodID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryPID)
                    .mapTo(String.class)
                    .list();
        });
        var queryVID = "SELECT verkoopt_ID FROM VERKOOPT where\n" +
                " VERKOOPT.boerderij_ID = " + boerID.toArray()[0] + " and VERKOOPT.pakket_naam = '" + size + "'";
        System.out.print(queryVID);
        List<String> verkooptID = jdbi.withHandle(handle -> {
            return handle.createQuery(queryVID)
                    .mapTo(String.class)
                    .list();
        });

        var querry = "UPDATE BEHOORT_TOT SET behoort_tot_week = " + week + ", behoort_tot_aantal ='" + aantal + "' WHERE BEHOORT_TOT.verkoopt_ID = " + verkooptID.toArray()[0] + " and BEHOORT_TOT.product_ID = " + prodID.toArray()[0] + " and BEHOORT_TOT.behoort_tot_aantal ='" + PrevAantal + "' and BEHOORT_TOT.behoort_tot_week ='" + prevWeek + "';";
        System.out.println("UITGEVOERDE QUERY ::: " + querry);
        jdbi.withHandle(handle -> {
            handle.execute(querry);

            return null;
        });
    }

    public void updatePrijzen(String boer_naam, String pakket_naam, String prijs, Integer ID) {

        var query2 = "SELECT boerderij_ID FROM BOERDERIJ WHERE boerderij_naam ='" + boer_naam + "'";
        List<String> boerID = jdbi.withHandle(handle -> {
            return handle.createQuery(query2)
                    .mapTo(String.class)
                    .list();
        });


        var querry = "UPDATE VERKOOPT SET boerderij_ID = '" + boerID.toArray()[0] + "', pakket_naam = '" + pakket_naam + "', prijs = '" + prijs + "' WHERE verkoopt_ID = " + ID + ";";
        System.out.println("UITGEVOERDE QUERY ::: " + querry);
        jdbi.withHandle(handle -> {
            handle.execute(querry);

            return null;
        });
    }


}
