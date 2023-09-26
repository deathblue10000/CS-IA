package com.example.csia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordSceneController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button backToMainButton;
    @FXML
    private LineChart<?, ?> lineChart;





    public void switchToMainScene(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScene.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //NumberAxis xAxis = new NumberAxis(1, 5, 1); // Weeks from 1 to 5
        //NumberAxis yAxis = new NumberAxis(0, 100, 10); // Percentages from 0% to 100%
        // Create a line chart
        //LineChart lineChart= new LineChart<Number, Number>(xAxis, yAxis);
        //lineChart.setTitle("Weekly Progress");




        // Create a data series for the line chart
        XYChart.Series series = new XYChart.Series();
        //series.setName("Progress");

        // Add data points (week, percentage) to the series
        series.getData().add(new XYChart.Data("1", 60));
        series.getData().add(new XYChart.Data("2", 45));
        series.getData().add(new XYChart.Data("3", 55));
        series.getData().add(new XYChart.Data("4", 70));
        series.getData().add(new XYChart.Data("5", 75));

        // Add the series to the line chart

            lineChart.getData().add(series);

    }
}
