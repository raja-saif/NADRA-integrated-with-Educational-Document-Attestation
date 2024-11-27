package id_matrix.id_matrix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Login {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label feedbackLabel; // Label to show feedback
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);  // Use the provided alertType
        alert.setTitle(title);
        alert.setHeaderText(null);  // You can optionally add a header text.
        alert.setContentText(message);
        alert.showAndWait();  // Wait for the user to dismiss the alert.
    }

    // Handle the login button click event
    @FXML
    public void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        IDMatrix idm=IDMatrix.getInstance();
        idm.id=username;
        System.out.println(idm.id);
        try {
            // Check credentials in the database
            String userType = DatabaseManager.getInstance().validateCredentials(username, password);

            if (userType == null) {
                // No matching user found
                showAlert("Login Failed", "No matching record found. Please sign up first.", Alert.AlertType.ERROR);
                return;
            }

            // Determine the FXML to load based on user type
            String fxmlFile;
            String title;
            switch (userType) {
                case "A": // Admin
                    fxmlFile = "AdminFunc.fxml";
                    title = "Admin Functionalities";
                    break;
                case "C": // Customer
                    fxmlFile = "CustFunc.fxml";
                    title = "Customer Dashboard";
                    break;
                case "E": // Employee
                    fxmlFile = "EmpFunc.fxml";
                    title = "Employee Dashboard";
                    break;
                default:
                    showAlert("Login Failed", "Invalid user type.", Alert.AlertType.ERROR);
                    return;
            }

            // Load the appropriate dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true); // Make the window maximized
            stage.setFullScreen(true); // Optional: Make the application full screen
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while logging in.", Alert.AlertType.ERROR);
        }
    }


    // Handle the Back Button click event (if needed)
    @FXML
    public void onBackButtonClick(ActionEvent event) {
        try {
            // Load the Welcome page scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome - ID Matrix");
            stage.setMaximized(true); // Make the window maximized
            stage.setFullScreen(true); // Optional: Make the application full screen
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }

}
