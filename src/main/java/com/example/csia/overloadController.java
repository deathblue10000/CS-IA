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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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
    String jdbcUrl = "jdbc:mysql://localhost:3306/calendardb";
    String username = "root";
    String password = "password";

    DatabaseOperations databaseOperations = new DatabaseOperations(jdbcUrl, username, password);

    List<Integer> randno = new ArrayList<Integer>();

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

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static List<Integer> generateDistinctiveRandomNumbers(int min, int max, int count) {
        Set<Integer> randomNumbersSet = new HashSet<>();

        // Generate random numbers until we have 'count' distinct ones
        while (randomNumbersSet.size() < count) {
            int randomNumber = getRandomNumber(min, max);
            randomNumbersSet.add(randomNumber);
        }

        // Convert the set to a list for easy access
        List<Integer> randomNumbersList = new ArrayList<>(randomNumbersSet);
        return randomNumbersList;
    }

    public int gettype(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY) {
            return 1;
        }else if (date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY) {
            return 2;
        }else if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return 3;
        }else {
            return 4;
        }
    }

    public void generateWorkout() {

        //startdate
        int dayfromstart = 0;
        int intensity = Arrays.asList(intensityOptions).indexOf(getIntensity());
        int overloadPeriod =  (Arrays.asList(overloadPeriodOptions).indexOf(getOverloadPeriod()) + 1) * 7;
        int deloadPeriod = (Arrays.asList(deloadPeriodOptions).indexOf(getDeloadPeriod()) + 2) * 7;
        int workoutRotation = (Arrays.asList(workoutRotationPeriod).indexOf(getWorkoutRotation()) + 2) * 7;

        System.out.println( intensity + " " + overloadPeriod + " " + deloadPeriod + " " + workoutRotation);



        randno = generateDistinctiveRandomNumbers(1, 10, 4);




//        String ex1 = databaseOperations.searchRunEx(randno.get(0));
//        String ex2 = databaseOperations.searchRunEx(randno.get(1));
//        String ex3 = databaseOperations.searchRunEx(randno.get(2));
//        String ex4 = databaseOperations.searchRunEx(randno.get(3));
//
//        databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

            // insertIntoTable(dayfromstart); it works


        for (int i = 0; i < overloadPeriod; i ++) {    // this is not done at all

            insertIntoTable(dayfromstart);
            dayfromstart++;


        }
            //databaseOperations.insertDataIntoDisplayTable(startdate.plusDays(dayfromstart), gettype(startdate.plusDays(dayfromstart)), );

    }
    public void insertIntoTable(int dayfromstart) {

        if ( gettype(startdate.plusDays(dayfromstart)) == 1 ) {

            String ex1 = databaseOperations.searchRunEx(randno.get(0));
            String ex2 = databaseOperations.searchRunEx(randno.get(1));
            String ex3 = databaseOperations.searchRunEx(randno.get(2));
            String ex4 = databaseOperations.searchRunEx(randno.get(3));

            databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

        }else if ( gettype(startdate.plusDays(dayfromstart)) == 2 ) {

            String ex1 = databaseOperations.searchSwimEx(randno.get(0));
            String ex2 = databaseOperations.searchSwimEx(randno.get(1));
            String ex3 = databaseOperations.searchSwimEx(randno.get(2));
            String ex4 = databaseOperations.searchSwimEx(randno.get(3));

            databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

        }else if ( gettype(startdate.plusDays(dayfromstart)) == 3 ) {

            String ex1 = databaseOperations.searchCycleEx(randno.get(0));
            String ex2 = databaseOperations.searchCycleEx(randno.get(1));
            String ex3 = databaseOperations.searchCycleEx(randno.get(2));
            String ex4 = databaseOperations.searchCycleEx(randno.get(3));

            databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

        }else if ( gettype(startdate.plusDays(dayfromstart)) == 4 ){

            String ex1 = databaseOperations.searchCoreEx(randno.get(0));
            String ex2 = databaseOperations.searchCoreEx(randno.get(1));
            String ex3 = databaseOperations.searchCoreEx(randno.get(2));
            String ex4 = databaseOperations.searchCoreEx(randno.get(3));

            databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

        }else {
            String ex1 = null;
            String ex2 = null;
            String ex3 = null;
            String ex4 = null;

            databaseOperations.insertDataIntoDisplayTable(java.sql.Date.valueOf(startdate.plusDays(dayfromstart)), gettype(startdate.plusDays(dayfromstart)), ex1, ex2, ex3, ex4);

        }


    }

}
