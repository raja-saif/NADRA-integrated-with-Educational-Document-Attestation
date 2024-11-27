package id_matrix.id_matrix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Signup {
    private String caller;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField ageField;
    @FXML
    private Label feedbackLabel; // Label to show feedback messages
    @FXML
    private RadioButton faceIDRadioButton;
    @FXML
    private Button otpRadioButton;
    @FXML
    private TextField otpval;

    public String myotp;
    public Boolean o = false;

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void initialize() {
        // This will not change the sign-up functionality, and you can check if `caller` is set
        if ("E".equals(caller)) {
            // Handle the case where the page is loaded from the admin page with the "E" parameter
            // For example, disable certain fields or pre-fill some values
            usernameField.setDisable(true);  // Example: disable the username field for admin case
            feedbackLabel.setText("This page is loaded from the admin side.");
        }
    }


    public void onOtpButtonClick(ActionEvent actionEvent) {
        o = true;
        Random random = new Random();
        int sixDigitNumber = 100000 + random.nextInt(900000); // Generates number between 100000 and 999999
        //  System.out.println("Random 6-digit number: " + sixDigitNumber);
        myotp = sixDigitNumber + "";
        OtpSender otpSender = new OtpSender();
        o = otpSender.otp(emailField.getText(), myotp);
        System.out.println("Email sent successfully!");
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

    @FXML
    public void onSignupButtonClick() throws IOException {
        String name = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String age = ageField.getText();
        String otp = otpval.getText();
        IDMatrix matrix = IDMatrix.getInstance();

        // Check if the passwords match
        if (password.equals(confirmPassword)) {
            if (!o || !Objects.equals(otp, myotp)) {
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                feedbackLabel.setText("Sign Up Failed! Otp not sent or Does not match.");
                return;
            }

            // Check if the email is unique
            if (email.isEmpty()) {
                // Set error feedback for duplicate email
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                feedbackLabel.setText("Sign Up Failed! Email is empty.");
                return;
            }
            boolean isEmailUnique = matrix.isEmailUnique(email);
            if (!isEmailUnique) {
                // Set error feedback for duplicate email
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                feedbackLabel.setText("Sign Up Failed! Email already exists. Please use a different email.");
                return;
            }

            // If all validations pass, proceed with signup
            feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            feedbackLabel.setText("Sign Up Successful! Welcome, " + name + "!");

            if (caller != null && caller.equals("E")) {
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int c = dbManager.addEmployee(name, phoneNumber, email, password, address, age);
                // Redirect to Admin page after successful sign-up
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFunc.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Dashboard");
                stage.setMaximized(true); // Optional: Make the window maximized
                stage.setFullScreen(true); // Optional: Make the application full screen
                stage.show();
            } else {
                matrix.addCustomer(name, phoneNumber, email, password, address, age);

                // Proceed with adding customer to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int customerId = dbManager.addCustomer(name, phoneNumber, email, password, address, age);
                if (customerId != -1) {
                    System.out.println("Customer added with ID: " + customerId);
                } else {
                    System.out.println("Failed to add customer to the database.");
                }

                // Optionally, navigate to another page or perform further actions after successful signup


                //REDIRECT TO LOGIN
                // Load the Sign Up page FXML
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("LOGIN");
                stage.setMaximized(true); // Make the window maximized
                stage.setFullScreen(true); // Optional: Make the application full screen
                stage.show();
            }

        }

    }
}
