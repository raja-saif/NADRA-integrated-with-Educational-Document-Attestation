package id_matrix.id_matrix;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Admin_func {
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private TableColumn<Employee, String> addressColumn;

    @FXML
    private TableColumn<Employee, String> ageColumn;
    @FXML
    private TextField newValueField;

    @FXML
    private ComboBox<String> emailDropdown;
    @FXML
    private ComboBox<String> emailComboBox;
    @FXML
    private ComboBox<String> attributeComboBox;
    @FXML
    private TextField check;
    @FXML
    private AnchorPane deletePane;

    @FXML
    private AnchorPane updatePane;
    @FXML
    private ObservableList<Employee> employees;
    Admin_func controller;
    @FXML
    public void initialize() {


    }
    // Method for the Back button
    public void onBackButtonClick(ActionEvent event) {
        displayPage("Welcome.fxml", event);
    }

    // Method for the Display Employee button
    public void onDisplayEmployeeButtonClick(ActionEvent event) {
        displayPage(".fxml", event);
    }

    // Method for the Add Employee button
    public void onAddEmployeeButtonClick(ActionEvent event) throws IOException {
        // Load the Signup page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        Parent root = loader.load();

        // Get the controller of the Signup page
        Signup signupController = loader.getController();

        // Pass the string parameter "E" to the controller
        signupController.setCaller("E");

        // Set the new scene for the Signup page
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Use the button's scene to get the window
        stage.setScene(scene);
        stage.setTitle("Sign Up - Employee");
        stage.setMaximized(true); // Optional: Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }

    // Method for the Delete Employee button
    public void onDeleteEmployeeButtonClick(ActionEvent event) throws IOException {
        System.out.println("Delete button clicked");


        // Sample employee data
        employees = FXCollections.observableArrayList(getEmployees());

        if (deletePane.isVisible()) {
            System.out.println("Delete pane is visible");
        } else {
            System.out.println("Delete pane is not visible");
        }
        // Set cell value factories for the table columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        ageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAge()));

        // Populate the table
        employeeTable.setItems(employees);

        // Populate the dropdown with employee emails
        emailDropdown.setItems(FXCollections.observableArrayList(
                employees.stream().map(Employee::getEmail).collect(Collectors.toList())
        ));

       // updatePane.setVisible(false);
       // deletePane.setVisible(true);
        System.out.println("Delete Pane Initialized: " + deletePane.isVisible());
       // if (deletePane != null)
        {
            employees = FXCollections.observableArrayList(getEmployees());

            // Load the delete page with the same controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("del.fxml"));
            Parent root = loader.load();

            // Get the current controller
            controller=new Admin_func();
             controller = loader.getController();

            // Set up the employee table in the controller
            controller.employeeTable.setItems(employees);

            // Set the cell value factories for the table columns (if not set in FXML)
            controller.nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            controller.phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
            controller.emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            controller.addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
            controller.ageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAge()));
            controller.emailDropdown.setItems(FXCollections.observableArrayList(
                    employees.stream().map(Employee::getEmail).collect(Collectors.toList())
            ));
            // Show the deletePane and hide the updatePane
            if (controller.updatePane != null) {
                controller.updatePane.setVisible(false);  // Hide updatePane
            }
            if (controller.deletePane != null) {
                controller.deletePane.setVisible(true);  // Ensure deletePane is visible
            }

            // Get the current stage (window)
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene with the loaded FXML content
            Scene scene = new Scene(root);
            stage.setScene(scene);  // Update the scene with the new FXML
            stage.setTitle("Delete Employee - ID Matrix");
            stage.setMaximized(true); // Optional: Maximize the window
            stage.setFullScreen(true);
            stage.show();
        }


    }

     //Handle Delete Employee///////////
     @FXML
     private void handleDeleteEmployee() {
         // Ensure employees list is initialized
         if (employees == null) {
             // Sample employee data
             employees = FXCollections.observableArrayList(getEmployees());
         }

         // Check if employees list is empty
//         if (employees.isEmpty()) {
//             showAlert("Warning", "No employees available to delete.", Alert.AlertType.WARNING);
//             return;
//         }

         employeeTable.setItems(employees);
         String selectedEmail = emailDropdown.getValue();

         if (selectedEmail == null || selectedEmail.isEmpty()) {
            // showAlert("Error", "Please select an email to delete.", Alert.AlertType.ERROR);
             return;
         }

         // Access the singleton instance of IDMatrix
         IDMatrix idm = IDMatrix.getInstance();

         boolean employeeDeleted = false;

         // Remove employee from `users` list in IDMatrix
         for (Iterator<User> iterator = idm.users.iterator(); iterator.hasNext(); ) {
             User user = iterator.next();
             if (user instanceof Employee && user.getEmail().equals(selectedEmail)) {
                 DatabaseManager db = DatabaseManager.getInstance();
                 boolean dbSuccess = db.deleteUserFromDatabase(user.getEmail()); // Ensure a `deleteUser` method exists in `DatabaseManager`

                 if (dbSuccess) {
                     iterator.remove(); // Remove from `IDMatrix.users`
                     employeeDeleted = true;
                     break;
                 } else {
                    // showAlert("Error", "Failed to delete employee from database.", Alert.AlertType.ERROR);
                     return;
                 }
             }
         }

         // Remove employee from the `employees` observable list
         if (employeeDeleted) {
             employees.removeIf(emp -> emp.getEmail().equals(selectedEmail));

             // Update the dropdown with remaining emails
             emailDropdown.setItems(FXCollections.observableArrayList(
                     employees.stream().map(Employee::getEmail).toList()
             ));

             // Refresh the UI components
             employeeTable.setItems(employees);
             employeeTable.refresh();
             emailDropdown.setValue(null);

          //   showAlert("Success", "Employee deleted successfully!", Alert.AlertType.INFORMATION);
         } else {
          //   showAlert("Error", "Employee not found in the system.", Alert.AlertType.ERROR);
         }
     }



    private ObservableList<Employee> getEmployees() {
        System.out.println("Fetching Employees...");
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        // Access the singleton instance of IDMatrix
        IDMatrix idm = IDMatrix.getInstance();

        // Loop through users and collect employees
        for (User user : idm.users) {
            if (user instanceof Employee) {
                Employee employee = (Employee) user;
                System.out.println("Employee Found: " + employee.getName() + " - " + employee.getEmail());
                employees.add(employee);
            }
        }

        return employees;
    }
    @FXML
    private void onUpdateEmployeeButtonClick(ActionEvent event) {
        // Initialize table columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        ageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAge()));

        // Populate combo boxes
        employees = FXCollections.observableArrayList(getEmployees());
        emailComboBox.setItems(FXCollections.observableArrayList(
                employees.stream().map(Employee::getEmail).toList()
        ));
        attributeComboBox.setItems(FXCollections.observableArrayList("Name", "Phone", "Address", "Age"));

        // Ensure panes visibility is managed correctly
        if (updatePane != null) {
            updatePane.setVisible(true);
        }
        deletePane.setVisible(false);

        // Fetch user input
        String selectedEmail = emailComboBox.getValue();
        String selectedAttribute = attributeComboBox.getValue();
        String newValue = newValueField.getText();

        // Validate user input
        if (selectedEmail == null || selectedAttribute == null || newValue.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        // Access the singleton instance of IDMatrix
        IDMatrix idm = IDMatrix.getInstance();

        // Locate and update the employee in both employees and IDMatrix.users
        boolean employeeUpdated = false;

        // Update employee in `users` list
        for (User user : idm.users) {
            if (user instanceof Employee && user.getEmail().equals(selectedEmail)) {
                Employee employee = (Employee) user;
                switch (selectedAttribute) {
                    case "Name" -> employee.name = newValue;
                    case "Phone" -> employee.phone = newValue;
                    case "Address" -> employee.address = newValue;
                    case "Age" -> employee.age = newValue;
                    default -> {
                        showAlert("Error", "Invalid attribute selected.", Alert.AlertType.ERROR);
                        return;
                    }
                }
                DatabaseManager db=DatabaseManager.getInstance();
                employeeUpdated = true;
                System.out.println("Employee Name: " + employee.getName() + " - " + employee.getEmail());
                System.out.println("Employee Address: " + employee.getAddress() + " - " + employee.getEmail());
                System.out.println("Employee Age: " + employee.getAge() + " - " + employee.getEmail());
                System.out.println("Employee Phone: " + employee.getPhone() + " - " + employee.getEmail());
                boolean b=db.updateUser( employee.getEmail(), employee.getName(), employee.getPhone(), employee.getAddress(), employee.getAge());
                break;
            }
        }

        // Update employee in `employees` observable list
        if (employeeUpdated) {
            employees.stream()
                    .filter(emp -> emp.getEmail().equals(selectedEmail))
                    .forEach(emp -> {
                        switch (selectedAttribute) {
                            case "Name" -> emp.name = newValue;
                            case "Phone" -> emp.phone = newValue;
                            case "Address" -> emp.address = newValue;
                            case "Age" -> emp.age = newValue;
                        }
                    });
            employeeTable.refresh();
          //  showAlert("Success", "Employee updated successfully!", Alert.AlertType.INFORMATION);
        } else {
           // showAlert("Error", "Employee not found.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to load FXML files and change the scene
    private void displayPage(String fxmlFile, ActionEvent event) {
        try {
            // Load the new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();  // Load the FXML file

            // Get the current stage (window)
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene with the loaded FXML content
            Scene scene = new Scene(root);
            stage.setScene(scene);  // Update the scene with the new FXML
            stage.setTitle("Admin - ID Matrix");
            stage.setMaximized(true); // Optional: Maximize the window
            stage.setFullScreen(true); // Optional: Make the application full screen
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }


}

