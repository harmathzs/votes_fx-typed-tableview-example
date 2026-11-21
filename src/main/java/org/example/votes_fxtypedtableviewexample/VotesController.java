package org.example.votes_fxtypedtableviewexample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class VotesController implements Initializable {
    @FXML
    public TableView<Vote> votesTableView;

    @FXML
    private TableColumn<Vote, String> idColumn;

    @FXML
    private TableColumn<Vote, String> firstNameColumn;

    @FXML
    private TableColumn<Vote, String> lastNameColumn;

    @FXML
    private TableColumn<Vote, String> birthdayColumn;

    @FXML
    private TableColumn<Vote, String> addressColumn;

    @FXML
    private TableColumn<Vote, String> partyColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().id));

        firstNameColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().firstName));

        lastNameColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().lastName));

        birthdayColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().birthday.toString()));

        addressColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().address));

        partyColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().votedParty));


        votesTableView.getItems().addAll(VotesData.votes);
    }
}
