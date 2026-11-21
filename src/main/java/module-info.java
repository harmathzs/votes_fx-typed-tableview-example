module org.example.votes_fxtypedtableviewexample {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.votes_fxtypedtableviewexample to javafx.fxml;
    exports org.example.votes_fxtypedtableviewexample;
}