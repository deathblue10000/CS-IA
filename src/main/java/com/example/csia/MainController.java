package com.example.csia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {


    private Stage stage;
    private Scene scene;
    @FXML
    private Button recordButton, quoteOfTheDayButton, changeButton;


    public void switchToRecordScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("recordScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();



    }

    public void switchToQODScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("QODScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();



    }

    public void switchToChangeScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("changeScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();



    }
}