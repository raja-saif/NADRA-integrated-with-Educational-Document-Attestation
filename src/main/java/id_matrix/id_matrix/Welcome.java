package id_matrix.id_matrix;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Welcome {

    @FXML
    private Label welcomeText;

    @FXML
    public void onSignupButtonClick() throws IOException {
        // Load the Sign Up page FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the current stage and set the new scene
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign Up - ID Matrix");
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }
    @FXML
    public void onLoginButtonClick() throws IOException {
        // Load the Login page FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the current stage and set the new scene
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.setTitle("Login - ID Matrix");
        stage.show();
    }
}
