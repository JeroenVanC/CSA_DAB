package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.lightcouch.CouchDbClient;

import java.util.Arrays;
import java.util.List;

public class RepositoryCouch {
    public CouchDbClient couchDbClient;

    public RepositoryCouch(CouchDbClient couchDbClient) {

        this.couchDbClient = couchDbClient;
    }

    public void insertTips(String product, String tipValue, String auteurValue) {
        Tips tip = new Tips(product, tipValue, auteurValue);
        couchDbClient.save(tip);
    }

    public ObservableList<Tips> getTips() {
        List<String> keys = Arrays.asList(new String[]{""});
        List<Tips> docs = couchDbClient.view("_all_docs")
                .includeDocs(true)
                .query(Tips.class);
        System.out.println(docs);

        return FXCollections.observableArrayList(docs);
    }

    public void deleteTip(Tips t) {
        couchDbClient.remove(t);

    }


}
