package org.example.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane mainSubAnchorPane;
    public AnchorPane subAnchorPane1;
    public AnchorPane subAnchorPane2;
    public TextField txtUserName;
    public TextField txtPassword;
    public PasswordField pwfPassword;

    public void btnViewAndHideOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        Stage window =(Stage) txtPassword.getScene().getWindow();
        window.close();
        Stage stage = new Stage();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/main_dashboard_form.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the form - Contact Developer").show();
            e.printStackTrace();
        }


    }

    public void btnCreateAnAcountOnAction(ActionEvent actionEvent) {
        subAnchorPane2.getChildren().clear();

        try {
            Node load = FXMLLoader.load(getClass().getResource("/view/register_form_sub.fxml"));
            ObservableList<Node> children = subAnchorPane2.getChildren();
            children.add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the form - Contact Developer").show();
            e.printStackTrace();
        }

    }
}
