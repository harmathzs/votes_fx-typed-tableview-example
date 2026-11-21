package org.example.votes_fxtypedtableviewexample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class VotesController implements Initializable {
    @FXML
    public TableView<Vote> votesTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
