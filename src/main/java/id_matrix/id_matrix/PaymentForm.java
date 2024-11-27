package id_matrix.id_matrix;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PaymentForm {

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblDocumentType;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtAddress;

    @FXML
    private ComboBox<String> cmbServiceType;
    private Runnable onSubmitCallback;
    @FXML
    private Button submitButton;
    @FXML
    public void initialize() {
        // Initialize the ComboBox with default values (if not set in FXML)
        cmbServiceType.getItems().addAll("EasyPaisa", "JazzCash", "SadaPay");
    }

    public void setOnSubmitCallback(Runnable callback) {
        this.onSubmitCallback = callback;
    }

    @FXML
    public void handleSubmitButtonClick() {
        System.out.println("Payment submitted.");

        // Invoke the callback to notify submission is complete
        if (onSubmitCallback != null) {
                    String phoneNumber = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        String serviceType = cmbServiceType.getValue();

        // Validation
        if (phoneNumber.isEmpty() || address.isEmpty() || serviceType == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled!");
            return;
        }

        // Handle form submission
        System.out.println("Payment Details:");
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("Service Type: " + serviceType);
        DatabaseManager db=DatabaseManager.getInstance();
        int r=db.addShipment(phoneNumber,serviceType,address,"Pending");
        System.out.println("SHIPMENT ADDED "+r);
        // Display success alert
        showAlert(Alert.AlertType.INFORMATION, "Success", "Payment submitted successfully!");
            onSubmitCallback.run();
        }
    }


    public void setTotalAmount(double amount) {
        lblTotalAmount.setText("Total Amount: $" + String.format("%.2f", amount));
    }

    public void setDocumentType(String documentType) {
        lblDocumentType.setText("Document Type: " + documentType);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


   
