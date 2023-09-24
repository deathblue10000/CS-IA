package com.example.csia;

import javafx.beans.NamedArg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class overloadController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button backButton, confirmButton, generateButton;

    @FXML
    private ChoiceBox<String> intensityBox, overloadPeriodBox, deloadPeriodBox, workoutRotationBox, dayOfWeekBox;
    @FXML
    private Label currentLoadLabel, followingLoadLabel, deloadWeekLabel, hoursLabel;
    @FXML
    private Slider hourSlider;
    @FXML
    private DatePicker datepick;


    private String[] intensityOptions = { "Low","Medium", "High", "Intense" }; // options for the check boxes
    private String[] overloadPeriodOptions = { "1 Week", "2 weeks", "3 Weeks", "4 Weeks"};
    private String[] deloadPeriodOptions = { "2 weeks", "3 Weeks", "4 Weeks"};
    private String[] workoutRotationPeriod = { "2 weeks", "3 Weeks", "4 Weeks"};
    private String[] dayOfWeekOptions = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private Double[] hoursPerDay = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};


    private int dow; // day of week variable for hoursPerDay

    private double workoutHours;

    private LocalDate startdate;

    public void setdaypick() {
        startdate = datepick.getValue();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        intensityBox.getItems().addAll(intensityOptions);
        overloadPeriodBox.getItems().addAll(overloadPeriodOptions);    // adding all the options into the box
        deloadPeriodBox.getItems().addAll(deloadPeriodOptions);
        workoutRotationBox.getItems().addAll(workoutRotationPeriod);
        dayOfWeekBox.getItems().addAll(dayOfWeekOptions);


        //hourSlider.setValue(4.00);
        hourSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                workoutHours = roundToHalf(hourSlider.getValue());
                hoursLabel.setText(Double.toString(workoutHours) + " Hours");
            }
        });

    }

    public void commitHoursToDayOfWeek() {

        try {
            hoursPerDay[Arrays.asList(dayOfWeekOptions).indexOf(getDayOfWeek())] = workoutHours; // the method chanining is esseitiallu getting the index of the day of week chosen in the box and use it as a no.
        } catch (Exception e ){
            System.out.println("please select a box before comfirming");

        }
        //float something = Arrays.asList(dayOfWeekOptions).indexOf(getDayOfWeek());
        //System.out.print(hoursPerDay);

        double followingLoad = 0.00;

        for ( double n : hoursPerDay) {
            followingLoad = followingLoad + n;
        }

        String fllb = Double.toString(roundToHalf(followingLoad /7)) + "hours per day"; // following load hours for label
        String dllb = Double.toString(roundToHalf(followingLoad /7 / 2))+ "hours per day"; // deload load hours for label


        followingLoadLabel.setText(fllb);
        deloadWeekLabel.setText(dllb);


    }



    public void switchToMainScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show(); // returning to main scene


    }
    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    public String getIntensity() {

        return intensityBox.getValue(); // method to get the intensity

    }
    public String getOverloadPeriod() {

        return overloadPeriodBox.getValue(); // method to get the overload period

    }
    public String getDeloadPeriod() {

        return deloadPeriodBox.getValue(); // method to get the deload period

    }
    public String getWorkoutRotation() {

        return workoutRotationBox.getValue(); // method to get the workout rotation

    }
    public String getDayOfWeek() {

        return dayOfWeekBox.getValue(); // method to get the day of week to change the hours

    }

    public void generateWorkout() {

        //startdate
        int intensity = Arrays.asList(intensityOptions).indexOf(getIntensity());
        int overloadPeriod =  (Arrays.asList(overloadPeriodOptions).indexOf(getOverloadPeriod()) + 1) * 7;
        int deloadPeriod = (Arrays.asList(deloadPeriodOptions).indexOf(getDeloadPeriod()) + 2) * 7;
        int workoutRotation = (Arrays.asList(workoutRotationPeriod).indexOf(getWorkoutRotation()) + 2) * 7;

        System.out.println( intensity + " " + overloadPeriod + " " + deloadPeriod + " " + workoutRotation);






    }


}
