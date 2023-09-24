package com.example.csia;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private Stage stage;
    private Scene scene;

    private LocalDate today = LocalDate.now();
    @FXML
    private Button recordButton, quoteOfTheDayButton, changeButton, overloadButton, workoutFeedBack, previousWeekButton, nextWeekButton;
    @FXML
    private GridPane calendar = new GridPane();
    @FXML
    private BorderPane bpcalendar;
    @FXML
    private Label monLabel, tueLabel, wedLabel, thurLabel, friLabel, satLabel, sunLabel, weekOfLabel;
    @FXML
    private VBox monVBox, tueVBox, wedVBox, thurVBox, friVBox, satVBox, sunVBox;
    @FXML
    private DatePicker datepicker;

    @FXML
    private Button monex1, monex2, monex3, monex4,
                    tueex1, tueex2, tueex3, tueex4,
                    wedex1, wedex2,wedex3, wedex4,
                    thurex1, thurex2, thurex3, thurex4,
                    friex1, friex2, friex3, friex4,
                    satex1, satex2, satex3, satex4,
                    sunex1, sunex2, sunex3, sunex4;


    String jdbcUrl = "jdbc:mysql://localhost:3306/calendardb";
    String username = "root";
    String password = "password";

    DatabaseOperations databaseOperations = new DatabaseOperations(jdbcUrl, username, password);


       LocalDate nextweekdate;
       LocalDate previousweekdate;
       LocalDate firstDayOfDisplay;
       LocalDate lastDayOfDisplay;
       LocalDate pickeddate = LocalDate.now();
       LocalDate currentdate;


    public void start() {
        monLabel.setText("Monday");
        tueLabel.setText("Tuesday");
        wedLabel.setText("Wednesday");
        thurLabel.setText("Thursday");
        friLabel.setText("Friday");
        satLabel.setText("Saturday");
        sunLabel.setText("Sunday");
    }
    public void getDate(ActionEvent event) {
        LocalDate pickDate = datepicker.getValue();

        LocalDate firstDayOfDisplay;
        LocalDate lastDayOfDisplay;
        System.out.println(pickDate);

        if (pickDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDayOfDisplay = pickDate.minusDays(pickDate.getDayOfWeek().getValue() -1);
            lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
        }else {
            firstDayOfDisplay = pickDate;
            lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
        }
        initializeLabel(firstDayOfDisplay, lastDayOfDisplay);
        setWeekOfLabel(firstDayOfDisplay,lastDayOfDisplay);

        pickeddate = datepicker.getValue();
        currentdate = pickeddate;
    }
    public void initializeLabel(LocalDate firstday, LocalDate lastday) {
        monLabel.setText("Monday " + firstday);
        tueLabel.setText("Tuesday " + firstday.plusDays(1));
        wedLabel.setText("Wednesday " + firstday.plusDays(2));
        thurLabel.setText("Thursday "+ firstday.plusDays(3));
        friLabel.setText("Friday "+ firstday.plusDays(4));
        satLabel.setText("Saturday "+ firstday.plusDays(5));
        sunLabel.setText("Sunday " + lastday);

        updateButton( firstday, 1);
    }
    public void setWeekOfLabel(LocalDate firstday, LocalDate lastday) {
        weekOfLabel.setText("Week Of " + firstday + " To " + lastday);

    }

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

    public void switchToOverloadScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("overloadScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToWorkoutFeedback(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("feedbackScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void changeWeek() {
        nextWeekButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nextweekdate = currentdate.plusDays(7);
                if (nextweekdate.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstDayOfDisplay = nextweekdate.minusDays(nextweekdate.getDayOfWeek().getValue() -1);
            lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
        }else {
            firstDayOfDisplay = nextweekdate;
            lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
        }
                currentdate = nextweekdate;
        initializeLabel(firstDayOfDisplay, lastDayOfDisplay);
        setWeekOfLabel(firstDayOfDisplay,lastDayOfDisplay);

            }
        });
        previousWeekButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previousweekdate = currentdate.minusDays(7);
                if (previousweekdate.getDayOfWeek() != DayOfWeek.MONDAY) {
                    firstDayOfDisplay = previousweekdate.minusDays(previousweekdate.getDayOfWeek().getValue() - 1);
                    lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
                } else {
                    firstDayOfDisplay = previousweekdate;
                    lastDayOfDisplay = firstDayOfDisplay.plusDays(6);
                }
                initializeLabel(firstDayOfDisplay, lastDayOfDisplay);
                setWeekOfLabel(firstDayOfDisplay, lastDayOfDisplay);
                currentdate = previousweekdate;
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }


    public void updateButton(LocalDate day, int dow){
            String searchQuery = "SELECT * From Display Where date = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(day));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and output the results
                Date column1 = resultSet.getDate("date");
                int column2 = resultSet.getInt("type");
                String column3 = resultSet.getString("ex1");
                String column4 = resultSet.getString("ex2");
                String column5 = resultSet.getString("ex3");
                String column6 = resultSet.getString("ex4");

                // Output or process the retrieved data as needed
                changeButtonLabel(column2, column3, column4, column5, column6, dow);

            } resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeButtonLabel(int type, String ex1, String ex2, String ex3, String ex4, int dow) {
        if (dow == 1) {
            monex1.setText(ex1);
            monex2.setText(ex2);
            monex3.setText(ex3);
            monex4.setText(ex4);
        }else if (dow == 2) {
            tueex1.setText(ex1);
            tueex2.setText(ex2);
            tueex3.setText(ex3);
            tueex4.setText(ex4);
        }else if (dow == 3 ) {
            wedex1.setText(ex1);
            wedex2.setText(ex2);
            wedex3.setText(ex3);
            wedex4.setText(ex4);
        }else if (dow == 4 ) {
            thurex1.setText(ex1);
            thurex2.setText(ex2);
            thurex3.setText(ex3);
            thurex4.setText(ex4);
        }else if (dow == 5) {
            friex1.setText(ex1);
            friex2.setText(ex2);
            friex3.setText(ex3);
            friex4.setText(ex4);
        }else if (dow == 6) {
            satex1.setText(ex1);
            satex2.setText(ex2);
            satex3.setText(ex3);
            satex4.setText(ex4);
        }else if (dow == 7) {
            sunex1.setText(ex1);
            sunex2.setText(ex2);
            sunex3.setText(ex3);
            sunex4.setText(ex4);
        }
    }

}

