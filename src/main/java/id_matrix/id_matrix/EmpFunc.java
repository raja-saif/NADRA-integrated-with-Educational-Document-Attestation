
package id_matrix.id_matrix;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;

public class EmpFunc {

    //VERIFY APPLICATION
    @FXML
    private TableView<application> applicationTable;

    @FXML
    private TableColumn<application, String> statusColumn;

    @FXML
    private TableColumn<application, String> emailColumn;

    @FXML
    private TableColumn<application, String> typeColumn;
    @FXML
    private TableColumn<application, String> nameColumn;
    @FXML
    private TableColumn<application, String> fatherNameColumn;
    @FXML
    private TableColumn<application, String> genderColumn;
    @FXML
    private TableColumn<application, String> dobColumn;
    @FXML
    private TableColumn<application, String> countryColumn;
    @FXML
    private TableColumn<application, String> doiColumn;
    @FXML
    private TableColumn<application, String> doeColumn;
    @FXML
    private TableColumn<application, String> cnicColumn;
    @FXML
    private TableColumn<application, String> verifyColumn;
    @FXML
    private TableColumn<application, String> cancelColumn;


    @FXML
    private TableColumn<application, String> yearColumn;
    @FXML
    private TableColumn<application, String> groupColumn;
    @FXML
    private TableColumn<application, String> subjectCountColumn;
    @FXML
    private TableColumn<application, String> gradeLevelColumn;
    @FXML
    private TableColumn<application, String> percentageColumn;


    private void addButtonToColumns() {
        // Verify Column: Add button to each cell
        verifyColumn.setCellFactory(new Callback<TableColumn<application, String>, TableCell<application, String>>() {
            @Override
            public TableCell<application, String> call(TableColumn<application, String> param) {
                return new TableCell<application, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // No content for empty cells
                        } else {
                            Button verifyButton = new Button("Verify");
                            verifyButton.setOnAction(event -> onVerifyButtonClick(getTableRow().getItem()));
                            setGraphic(new HBox(verifyButton));
                        }
                    }
                };
            }
        });

        // Cancel Column: Add button to each cell
        cancelColumn.setCellFactory(new Callback<TableColumn<application, String>, TableCell<application, String>>() {
            @Override
            public TableCell<application, String> call(TableColumn<application, String> param) {
                return new TableCell<application, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // No content for empty cells
                        } else {
                            Button cancelButton = new Button("Cancel");
                            cancelButton.setOnAction(event -> onCancelButtonClick(getTableRow().getItem()));
                            setGraphic(new HBox(cancelButton));
                        }
                    }
                };
            }
        });
    }

    private void onVerifyButtonClick(application app) {
        // Handle verify action
        DatabaseManager dbManager = DatabaseManager.getInstance();
        if(app.getStatus()!="verified") {
            System.out.println("Verified application: " + app.getType());
            app.setStatus("verified");
            //CNIC
            if(app.type.equals("I"))
            {
                //CNIC
                Random random = new Random();
                // Generate the first 5 digits (to match the format of CNIC)
                int firstPart = 10000 + random.nextInt(90000); // 5 digits
                // Generate the next 7 digits
                int secondPart = 1000000 + random.nextInt(9000000); // 7 digits
                // Generate the last digit (the check digit, could be a random number)
                int lastDigit = 0 + random.nextInt(9); // 3 digits

                // Combine all parts into the final CNIC
                ((IDCard) (app.document)).setCnic(String.format("%05d-%07d-%01d", firstPart, secondPart, lastDigit));
                System.out.println(((IDCard) (app.document)).cnic);
                Calendar doiCalendar = Calendar.getInstance(); // Current date
                int currentYear = doiCalendar.get(Calendar.YEAR); // Get current year
                int currentMonth = doiCalendar.get(Calendar.MONTH); // Get current month
                // Get the maximum number of days in the current month
                int maxDay = doiCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                // Generate a random day in the current month
                int randomDay = 1 + random.nextInt(maxDay); // Random day (1 to maxDay)
                // Set the day, month, and year
                doiCalendar.set(currentYear, currentMonth, randomDay);
                Date doi = doiCalendar.getTime();
                String formattedDoi = formatDate(doi);
                ((IDCard) (app.document)).DOI = formattedDoi;
                System.out.println( ((IDCard) (app.document)).DOI);
                // Generate Date of Expiry (adding 5 years to the Date of Issue)
                Calendar doeCalendar = Calendar.getInstance();
                doeCalendar.setTime(doi);
                doeCalendar.add(Calendar.YEAR, 5); // Expiry date is 5 years after DOI
                Date doe = doeCalendar.getTime();
                String formattedDoe = formatDate(doe);

                ((IDCard) (app.document)).DOE = formattedDoe;
                System.out.println( ((IDCard) (app.document)).DOE);
                boolean confirmation=dbManager.updateApplicationStatusAndIDCard(app.email,app.Status,app.type, ((IDCard) (app.document)).cnic, formattedDoi, formattedDoe);

            }
            else if(app.type.equals("P"))
            {
                Random random = new Random();

                // Choose one of the series for the passport: Regular ('A'), Diplomatic ('D'), or Special ('S')
                String[] series = {"A", "D", "S"};
                String passportSeries = series[random.nextInt(series.length)];

                // Generate the 7 digits for the passport number
                int passportNumber = 1000000 + random.nextInt(9000000); // Seven digits between 1000000 and 9999999

                // Construct the full passport number (e.g., A1234567)
                String fullPassportNumber = passportSeries + passportNumber;

                // Print the generated passport number
                System.out.println("Generated Passport Number: " + fullPassportNumber);
                // Combine all parts into the final CNIC
                ((Passport) (app.document)).setSeries(fullPassportNumber);

                Calendar doiCalendar = Calendar.getInstance(); // Current date
                int currentYear = doiCalendar.get(Calendar.YEAR); // Get current year
                int currentMonth = doiCalendar.get(Calendar.MONTH); // Get current month
                // Get the maximum number of days in the current month
                int maxDay = doiCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                // Generate a random day in the current month
                int randomDay = 1 + random.nextInt(maxDay); // Random day (1 to maxDay)
                // Set the day, month, and year
                doiCalendar.set(currentYear, currentMonth, randomDay);
                Date doi = doiCalendar.getTime();
                String formattedDoi = formatDate(doi);
                ((Passport) (app.document)).DateOfIssue = formattedDoi;
                System.out.println( ((Passport) (app.document)).DateOfIssue);
                // Generate Date of Expiry (adding 5 years to the Date of Issue)
                Calendar doeCalendar = Calendar.getInstance();
                doeCalendar.setTime(doi);
                doeCalendar.add(Calendar.YEAR, 5); // Expiry date is 5 years after DOI
                Date doe = doeCalendar.getTime();
                String formattedDoe = formatDate(doe);

                ((Passport) (app.document)).DateOfExpiry = formattedDoe;
                System.out.println( ((Passport) (app.document)).DateOfExpiry);
                System.out.println("Passport type "+ app.type);
                boolean confirmation=dbManager.updateApplicationStatusAndPassport(app.email,app.Status,app.type, ((Passport) (app.document)).series, formattedDoi, formattedDoe);

            }
            else if(app.type.equals("B"))
            {
                boolean confirmation=dbManager.updateApplicationStatusOFDandB(app.email,app.Status,app.type);
            }
            else if(app.type.equals("D"))
            {
                boolean confirmation=dbManager.updateApplicationStatusOFDandB(app.email,app.Status,app.type);
            }
            else if(app.type.equals("E"))//EQUIVALENCE
            {

                //WORKING FOR FORMULA
                List<String>gr=((Equivalence) (app.document)).grades;
                Map<String, Integer> gradeToPercentage = new HashMap<>();
                gradeToPercentage.put("A*", 100);
                gradeToPercentage.put("A", 90);
                gradeToPercentage.put("B", 80);
                gradeToPercentage.put("C", 70);
                gradeToPercentage.put("D", 60);
                gradeToPercentage.put("E", 50);
                gradeToPercentage.put("U", 0);  // Ungraded

                // Calculate total percentage
                int totalPercentage = 0;
                for (String grade : gr) {
                    if (gradeToPercentage.containsKey(grade)) {
                        totalPercentage += gradeToPercentage.get(grade);
                    }
                }

                // Calculate equivalent average percentage
                double averagePercentage = (double) totalPercentage / gr.size();
                    System.out.println("PERCENTAGE: " + averagePercentage);
                ((Equivalence) (app.document)).percentage=String.valueOf(averagePercentage);

                System.out.println("Equivalent Percentage: " + ((Equivalence) (app.document)).percentage + "%");


                //((Equivalence) (app.document)).;

                boolean confirmation=dbManager.updateApplicationStatusOFEquivalence(app.email,"verified",app.type);
                System.out.println("EQUIVALANCE SAVED "+confirmation);
                //,((Equivalence) (app.document)).percentage
               boolean c= dbManager.updateEquivalencePercentage(app.email,((Equivalence) (app.document)).gradeLevel,((Equivalence) (app.document)).percentage);
            }


            // Load data into the table

            //db save
            //table updated display
            addButtonToColumns();
            applicationTable.setItems(getApplications());
            applicationTable.setVisible(true); // Show the TableView
        }

    }
    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    private void onCancelButtonClick(application app) {
        // Handle cancel action
        System.out.println("Cancelled application: " + app.getType());
        app.setStatus("cancelled");
        DatabaseManager dbManager = DatabaseManager.getInstance();
            boolean confirmation=dbManager.updateApplicationStatusAndIDCard(app.email,app.Status,app.type,"",null, null);
            addButtonToColumns();
            applicationTable.setItems(getApplications());
            applicationTable.setVisible(true); // Show the TableView

    }


    @FXML
    public void initialize() {
        // Initialize columns
//        System.out.println("Fish");
//        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
//
//        // Load data into the table
//        applicationTable.setItems(getApplications());
//        System.out.println("applicationTable.getItems()");
        addButtonToColumns();

    }

    private ObservableList<application> getApplications() {
        System.out.println("Hello");
        ObservableList<application> applications = FXCollections.observableArrayList();

        // Loop through users and collect applications
        IDMatrix idm=IDMatrix.getInstance();
        for (User user : idm.users) {
            System.out.println("User");
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                System.out.println(customer.email);
                // Using index-based for loop
                List<application> customerApplications = customer.getApplications();
                System.out.println(customerApplications.size());
                for (int i = 0; i < customerApplications.size(); i++) {
                    application customerApp = customerApplications.get(i);
                    System.out.println(customerApp.email+customerApp.type+customerApp.document.type);
                    // Assuming application has 'getStatus' and 'email' properties
                   // application app = new application(customerApp.type, customerApp.email);
                    if(customerApp.Status.equals("unverified"))
                        applications.add(customerApp);

                }
            }
        }

        return applications;
    }
    //BACK BUTTON
    // Method for the Back button
    public void onBackButtonClick(ActionEvent event) throws IOException {

        //showAlert("Back Button", "Navigating to the previous page.");
        // Logic to go back to the previous page
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("LOGIN");
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }

    // Method for the Display Employee button
    public void onManageCustomerButtonClick(ActionEvent event) {
        try {
            // Load the new FXML page for update/delete customer functionality
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDeleteCus.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Update/Delete Customers");
            stage.show();
            stage.setMaximized(true); // Make the window maximized
            stage.setFullScreen(true); // Optional: Make the application full screen
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }

    // Method for the Add Employee button
    public void onVerifyApplicationButtonClick(ActionEvent event) {

         //Initialize columns
        System.out.println("Fish");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        // IDCARD
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        doiColumn.setCellValueFactory(new PropertyValueFactory<>("doi"));
        doeColumn.setCellValueFactory(new PropertyValueFactory<>("doe"));
        cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));

        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        subjectCountColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCount"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Load data into the table
        applicationTable.setItems(getApplications());
        applicationTable.setVisible(true); // Show the TableView
        System.out.println("applicationTable.getItems()");


    }

    // Method for the Delete Employee button
    public void onDeleteCustomerButtonClick(ActionEvent event) {
        displayPage(".fxml", event);
    }

    // Helper method to load FXML files and change the scene
    private void displayPage(String fxmlFile, ActionEvent event) {
        try {
            // Load the new FXML page
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin - ID Matrix");
            stage.show();
            stage.setMaximized(true); // Make the window maximized
            stage.setFullScreen(true); // Optional: Make the application full screen
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }
}

