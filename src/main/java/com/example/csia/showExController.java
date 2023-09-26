package com.example.csia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class showExController implements Initializable {

    @FXML
    Label ex1label, ex2label, ex3label, ex4label;
    @FXML
    Button closeButton, feedbackButton;

    private Stage stage;
    private Scene scene;
    private String jdbcUrl = "jdbc:mysql://localhost:3306/calendardb";
    private String username = "root";
    private String password = "password";
    LocalDate date = LocalDate.now();
    DatabaseOperations databaseOperations = new DatabaseOperations(jdbcUrl, username, password);

    public void closeStage(ActionEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }
    public void switchToFeedbackScene(ActionEvent event) throws IOException {

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("feedbackScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();


    }
    public void displayExercises(LocalDate passindate) {
        date = passindate;
        String exercise1 = databaseOperations.returndateex(java.sql.Date.valueOf(passindate),"ex1");
        String exercise2 = databaseOperations.returndateex(java.sql.Date.valueOf(passindate),"ex2");
        String exercise3 = databaseOperations.returndateex(java.sql.Date.valueOf(passindate),"ex3");
        String exercise4 = databaseOperations.returndateex(java.sql.Date.valueOf(passindate),"ex4");
        System.out.println(passindate + " In showexcontroller");
        System.out.println(exercise1 + " " + exercise2 + " " + exercise3 + " " + exercise4);

//        String temp = databaseOperations.searchForSetAndRep(exercise1, convertDateToDayNumber(passindate));
//        System.out.println( " in showex : "  + temp);

        ex1label.setText(databaseOperations.searchForSetAndRep(exercise1, convertDateToDayNumber(passindate)));
        ex2label.setText(databaseOperations.searchForSetAndRep(exercise2, convertDateToDayNumber(passindate)));
        ex3label.setText(databaseOperations.searchForSetAndRep(exercise3, convertDateToDayNumber(passindate)));
        ex4label.setText(databaseOperations.searchForSetAndRep(exercise4, convertDateToDayNumber(passindate)));
    }
    public int convertDateToDayNumber(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayNumber = dayOfWeek.getValue();

        // Convert to 1-7 range (Sunday to Saturday)
        return (dayNumber == 7) ? 1 : dayNumber;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

//       String temp = databaseOperations.returndateex(java.sql.Date.valueOf(date),1);
//       System.out.println(temp + " In showex");

    }
}
