package org.example.votes_fxtypedtableviewexample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VotesController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
