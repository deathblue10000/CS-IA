package com.example.csia;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class feedbackController implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    Button backButton;

    @FXML
    RadioButton ex1int1, ex1int2, ex1int3, ex1int4,
                ex2int1, ex2int2, ex2int3, ex2int4,
                ex3int1, ex3int2, ex3int3, ex3int4,
                ex4int1, ex4int2, ex4int3, ex4int4;

    @FXML
    RadioButton ex1Diff1, ex1Diff2, ex1Diff3, ex1Diff4,
                ex2Diff1, ex2Diff2, ex2Diff3, ex2Diff4,
                ex3Diff1, ex3Diff2, ex3Diff3, ex3Diff4,
                ex4Diff1, ex4Diff2, ex4Diff3, ex4Diff4;



    @FXML
    CheckBox comp1, comp2, comp3, comp4;

    @FXML
    HBox hBoxEx1Int, hBoxEx2Int, hBoxEx3Int, hBoxEx4Int, hBoxEx1Diff, hBoxEx2Diff, hBoxEx3Diff, hBoxEx4Diff ;

    @FXML
    VBox vBoxInt, vBoxDiff;

    @FXML
    Slider energySlider;
    @FXML
    Button annotation;
    @FXML
    Label currentScoreLabel;


    int energyLevel;


    public void switchToMainScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show(); // returning to main scene


    }

    public void getEx1Intensity() {
        get_rButton(ex1int1, ex1int2, ex1int3, ex1int4);

    }
    public void getEx2Intensity() {
        get_rButton(ex2int1, ex2int2, ex2int3, ex2int4);

    }
    public void getEx3Intensity() {
        get_rButton(ex3int1, ex3int2, ex3int3, ex3int4);

    }
    public void getEx4Intensity() {
        get_rButton(ex4int1, ex4int2, ex4int3, ex4int4);

    }
    public void getEx1Diff() {
        get_rButton(ex1Diff1, ex1Diff2, ex1Diff3, ex1Diff4);

    }
    public void getEx2Diff() {
        get_rButton(ex2Diff1, ex2Diff2, ex2Diff3, ex2Diff4);

    }
    public void getEx3Diff() {
        get_rButton(ex3Diff1, ex3Diff2, ex3Diff3, ex3Diff4);

    }
    public void getEx4Diff() {
        get_rButton(ex4Diff1, ex4Diff2, ex4Diff3, ex4Diff4);

    }

    private void get_rButton(RadioButton one, RadioButton two, RadioButton three, RadioButton four) {
        if (one.isSelected()) {
            System.out.println(one.getText());

        }
        if (two.isSelected()) {
            System.out.println(two.getText());

        }
        if (three.isSelected()) {
            System.out.println(three.getText());

        }
        if (four.isSelected()) {
            System.out.println(four.getText());

        }
    }

    public void comp1Dis(ActionEvent e) {
        if (comp1.isSelected() == false ) {
            disable(hBoxEx1Int, hBoxEx1Diff);
        } else {
            enable(hBoxEx1Int, hBoxEx1Diff);
        }
    }
    public void comp2Dis(ActionEvent e) {
        if (comp2.isSelected() == false ) {
            disable(hBoxEx2Int, hBoxEx2Diff);
        } else {
            enable(hBoxEx2Int, hBoxEx2Diff);
        }
    }
    public void comp3Dis(ActionEvent e) {
        if (comp3.isSelected() == false ) {
            disable(hBoxEx3Int, hBoxEx3Diff);
        } else {
            enable(hBoxEx3Int, hBoxEx3Diff);
        }
    }
    public void comp4Dis(ActionEvent e) {
        if (comp4.isSelected() == false ) {
            disable(hBoxEx4Int, hBoxEx4Diff);
        } else {
            enable(hBoxEx4Int, hBoxEx4Diff);
        }
    }


    private void disable(HBox hbox1, HBox hbox2){
        hbox1.setDisable(true);
        hbox2.setDisable(true);

    }
    private void enable(HBox hbox1, HBox hbox2){
        hbox1.setDisable(false);
        hbox2.setDisable(false);

    }

    public void annotation (ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("annotation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        energySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                energyLevel = (int) energySlider.getValue();
                currentScoreLabel.setText("Current Score: " + energyLevel);

            }
        });

    }
}
