package com.example.csia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QODController {

    private Stage stage;
    private Scene scene;
    @FXML
    ImageView memeImageView;

    @FXML
    Button showButton, backButton;
    @FXML
    Label motivelabel; // you need to fix that label



    public void showMeme(ActionEvent event){
       try {
           Image dogMeme = new Image(getClass().getResourceAsStream("/picuture/dog.png"));
           memeImageView.setImage(dogMeme);
       }catch (Exception e) {

           System.out.println("IT doesn't work D:");


       }
    }



    public void switchToMainScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();


    }


}
