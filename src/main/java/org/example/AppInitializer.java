package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.dto.MemberDTO;

import java.io.IOException;

public class AppInitializer extends Application {

    // Main method that launches the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene = new Scene(load);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to load the form - Contact Developer").show();
            e.printStackTrace();
        }
    }
}
