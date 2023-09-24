package com.example.csia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/calendardb";
        String username = "root";
        String password = "password";

        DatabaseOperations databaseOperations = new DatabaseOperations(jdbcUrl, username, password);


        LocalDate date = LocalDate.of(2023,01,02);
        java.sql.Date sqlDate = java.sql.Date.valueOf(date); //java.sql.Date.valueOf()
        databaseOperations.insertDataIntoDisplayTable(sqlDate, 2, "Swim", "Swdddddim","Swggggggim","Swim");

        databaseOperations.retrieveDataFromDisplayTable();



        launch();
    }
}