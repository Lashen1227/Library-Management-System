package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormSubController {
    public AnchorPane signupMainPane;

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        Stage window =(Stage) signupMainPane.getScene().getWindow();
        window.close();

        Stage stage = new Stage();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the form - Contact Developer").show();
            e.printStackTrace();
        }
    }
}
