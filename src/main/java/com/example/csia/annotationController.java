package com.example.csia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class annotationController {
    @FXML
    TextArea annotationText;
    @FXML
    Button saveAndClose;

    public void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

}
