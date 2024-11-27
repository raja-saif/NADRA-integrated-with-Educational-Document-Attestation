package id_matrix.id_matrix;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;

public class CustFunc {

    //=======================VIEW MY APPLICATIONS ===========================
    //MY APPLICATION
    @FXML
    public TableView<application> applicationTable;

    @FXML
    public TableColumn<application, String> statusColumn;

    @FXML
    public TableColumn<application, String> emailColumn;

    @FXML
    public TableColumn<application, String> typeColumn;
    @FXML
    public TableColumn<application, String> nameColumn;
    @FXML
    public TableColumn<application, String> fatherNameColumn;
    @FXML
    public TableColumn<application, String> genderColumn;
    @FXML
    public TableColumn<application, String> dobColumn;
    @FXML
    public TableColumn<application, String> countryColumn;
    @FXML
    public TableColumn<application, String> doiColumn;
    @FXML
    public TableColumn<application, String> doeColumn;
    @FXML
    public TableColumn<application, String> cnicColumn;
    @FXML
    public TableColumn<application, String> payColumn;
    //===================PASSPORT
    @FXML
    public TableColumn<application, String> country_P_Column;
    @FXML
    public TableColumn<application, String> typePassportColumn;
    @FXML
    public TableColumn<application, String> SeriesColumn;
    //===================DoMICILE
    @FXML
    public TableColumn<application, String> motherColumn;
    @FXML
    public TableColumn<application, String> addressColumn;
    @FXML
    public TableColumn<application, String> provinceColumn;
    //===================BFORM
    @FXML
    public TableColumn<application, String> birthplaceColumn;
    @FXML
    public TableColumn<application, String> religionColumn;


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

    private boolean duplicate=false;

    @FXML
    public TableView<Document> DocumentTable;

    //public  Hyperlink clickable;
    @FXML
    public VBox hyperlinkVBox; // VBox to hold the hyperlinks
    @FXML
    public Button btnAddLinks;
    // List of image paths (these can be loaded dynamically from a database or file system)
    public List<String> imagePaths = new ArrayList<>();

    // Method to add hyperlinks dynamically
    @FXML
    public void onAddLinksButtonClick() {
        // Clear existing hyperlinks (if needed)
        IDMatrix idm=IDMatrix.getInstance();
        DatabaseManager dbManager = DatabaseManager.getInstance();
        imagePaths= dbManager.getImagePathsByEmail(idm.id);


        hyperlinkVBox.getChildren().clear();

        // Add hyperlinks for each image path
        for (String path : imagePaths) {
            String fileName = path.substring(path.lastIndexOf("\\") + 1); // For Windows-style paths
            if ("cnic.png".equals(fileName) || "bform.png".equals(fileName) || "domicile.png".equals(fileName) || "passport.png".equals(fileName) || "equivalence_certificate.png".equals(fileName)) {
                Hyperlink hyperlink = new Hyperlink("Click to view image: " + path);
                hyperlink.setOnAction(event -> clickableImage(path));  // Open the image on click
                hyperlinkVBox.getChildren().add(hyperlink);  // Add the hyperlink to the VBox
            }
         //   Hyperlink hyperlink = new Hyperlink("Click to view image: " + path);
        //    hyperlink.setOnAction(event -> clickableImage(path));  // Open the image on click
         //   hyperlinkVBox.getChildren().add(hyperlink);  // Add the hyperlink to the VBox
        }
    }

    // Method to open the image when the hyperlink is clicked
    public void clickableImage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);  // Open the image using the default viewer
                System.out.println("Image opened successfully.");
            } else {
                System.out.println("File not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error opening image.");
        }
    }
    @FXML
    private void onDocumentsButtonClick(ActionEvent event) throws IOException{
        System.out.println("My Documents Opening My Documents section.");
        // Logic to display documents
        onAddLinksButtonClick();
        applicationTable.setVisible(false);
        hyperlinkVBox.setVisible(true);
        btnAddLinks.setVisible(true);

    }
    //==============================================================================
    private void addButtonToColumns() {
        payColumn.setCellFactory(new Callback<TableColumn<application, String>, TableCell<application, String>>() {
            @Override
            public TableCell<application, String> call(TableColumn<application, String> param) {
                return new TableCell<application, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setGraphic(null); // No content for empty cells or rows
                            return;
                        }

                        // Get the application object for the current row
                        application app = getTableRow().getItem();

                        // Debugging: Log the status to check if it's as expected
                        System.out.println("Row Status: " + app.getStatus());

                        if ("Verified".equalsIgnoreCase(app.getStatus())) { // Ignore case for comparison
                            Button payButton = new Button("Pay");
                            payButton.setOnAction(event -> onpayButtonClick(app));
                            setGraphic(new HBox(payButton)); // Add the button to the cell
                        } else {
                            setGraphic(null); // No button for non-verified rows
                        }
                    }
                };
            }
        });
    }


//    private void onpayButtonClick(application app) {
//        //pay functionality
//        System.out.println("PAY PAY... PAID");
//       IDMatrix idm=IDMatrix.getInstance();
//       Customer c;
//       for(int i=0;i<idm.users.size();++i) {
//
//           if(idm.users.get(i).email.equals(idm.id))
//           {
//               System.out.println("EMAIL"+idm.users.get(i).email);
//               c=((Customer)(idm.users.get(i)));
//               for(int j=0;j<c.applications.size();++j)
//               {
//                   if(c.applications.get(j).type.equals("I")) {
//                       System.out.println("APP" + c.applications.get(i).type);
//                       IDCard idCard = (IDCard) c.applications.get(i).document;
//                       if (idCard.templatecnic == null)
//                       {
//                           idCard.templatecnic = new templateCNIC();
//                       }
//                       // Ensure image is loaded before rendering CNIC
//                       idCard.templatecnic.setVals(idCard.name,idCard.FatherName,idCard.gender,idCard.country,idCard.DOB,idCard.cnic,idCard.DOI,idCard.DOE);
//                   }
//               }
//           }
//
//       }



    private void onpayButtonClick(application app) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaymentForm.fxml"));
            Parent root = loader.load();

            // Get the controller for the payment form
            PaymentForm controller = loader.getController();

            // Create the modal stage for the payment form
            Stage paymentStage = new Stage();
            paymentStage.initModality(Modality.APPLICATION_MODAL);
            paymentStage.setScene(new Scene(root));
            paymentStage.setTitle("Payment Form");
            paymentStage.setMaximized(true); // Make the window maximized
            paymentStage.setFullScreen(true); // Optional: Make the application full screen
            // Set the callback for form submission
            controller.setOnSubmitCallback(() -> {
                System.out.println("Returning to main flow after payment form.");
                paymentStage.close(); // Close the modal window
            });

            // Show the modal form and wait for it to close
            paymentStage.showAndWait();

            // After the modal is closed, the code below will execute
            System.out.println("Continuing with remaining actions...");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Generating CNIC image...");
        IDMatrix idm = IDMatrix.getInstance();
        DatabaseManager db=DatabaseManager.getInstance();
        // Find the current customer
        for (int i = 0; i < idm.users.size(); ++i) {
            if (idm.users.get(i).email.equals(idm.id)) {
                Customer c = (Customer) idm.users.get(i);

                // Process applications of type "I"
                for (int j = 0; j < c.applications.size(); ++j) {

                     if (c.applications.get(j).type.equals("P")) {
                         System.out.println("Application Found!");
                         boolean b = db.updateShipmentIfEmailNull(idm.users.get(i).email, c.applications.get(j).type, 7000);
                         List<String> s = db.getShipmentDetailsByEmailAndDocType(idm.users.get(i).email, c.applications.get(j).type);
                         c.applications.get(j).document.payment = new Payment(idm.users.get(i).email, 7000, s.get(0), s.get(1), s.get(2));
                         // Retrieve IDCard data from the application
                         if (duplicate == false) {
                             Passport passport = (Passport) c.applications.get(j).document;

                             // Load FXML for templateCNIC
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("templatePassport.fxml"));
                             try {
                                 Parent root = loader.load();
                                 templatePassport controller = loader.getController();

                                 // Set values including CNIC-specific attributes
                                 controller.setValsAndSavePassport(
                                         passport.name, passport.dob, passport.gender, passport.country, passport.DateOfIssue, passport.DateOfExpiry, passport.TypeP
                                 );

                                 System.out.println("Passport image generated and saved successfully!");
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                   else if (c.applications.get(j).type.equals("I")) {
                         System.out.println("Application Found!");
                         boolean b = db.updateShipmentIfEmailNull(idm.users.get(i).email, c.applications.get(j).type, 1500);
                         List<String> s = db.getShipmentDetailsByEmailAndDocType(idm.users.get(i).email, c.applications.get(j).type);
                         c.applications.get(j).document.payment = new Payment(idm.users.get(i).email, 1500, s.get(0), s.get(1), s.get(2));
                         // Retrieve IDCard data from the application
                         if (duplicate == false) {
                             IDCard idCard = (IDCard) c.applications.get(j).document;

                             // Load FXML for templateCNIC
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("templateCNIC.fxml"));
                             try {
                                 Parent root = loader.load();
                                 templateCNIC controller = loader.getController();


                                 // Set values including CNIC-specific attributes
                                 controller.setValsAndSave(
                                         idCard.name,
                                         idCard.FatherName,
                                         idCard.gender,
                                         idCard.country,
                                         idCard.DOB,
                                         idCard.cnic,    // Add CNIC
                                         idCard.DOI,     // Add DOI
                                         idCard.DOE      // Add DOE
                                 );

                                 System.out.println("CNIC image generated and saved successfully!");
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                    else if (c.applications.get(j).type.equals("D")) {
                         System.out.println("Application Found!");
                         boolean b = db.updateShipmentIfEmailNull(idm.users.get(i).email, c.applications.get(j).type, 2000);
                         List<String> s = db.getShipmentDetailsByEmailAndDocType(idm.users.get(i).email, c.applications.get(j).type);
                         c.applications.get(j).document.payment = new Payment(idm.users.get(i).email, 2000, s.get(0), s.get(1), s.get(2));
                         // Retrieve IDCard data from the application
                         if (duplicate == false) {
                             Domicile domicile = (Domicile) c.applications.get(j).document;

                             // Load FXML for templateCNIC
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("templateDomicile.fxml"));
                             try {
                                 Parent root = loader.load();
                                 templateDomicile controller = loader.getController();

                                 // Set values including CNIC-specific attributes
                                 controller.setValsAndSaveDomicile(
                                         domicile.fullname, domicile.fathername, domicile.mothername, domicile.dob, domicile.gender, domicile.address, domicile.province // Add DOE
                                 );

                                 System.out.println("Passport image generated and saved successfully!");
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                    else if (c.applications.get(j).type.equals("B")) {
                         System.out.println("Application Found!");
                         boolean b = db.updateShipmentIfEmailNull(idm.users.get(i).email, c.applications.get(j).type, 1000);
                         List<String> s = db.getShipmentDetailsByEmailAndDocType(idm.users.get(i).email, c.applications.get(j).type);
                         c.applications.get(j).document.payment = new Payment(idm.users.get(i).email, 1000, s.get(0), s.get(1), s.get(2));
                         // Retrieve IDCard data from the application
                         if (duplicate == false) {
                             BForm bform = (BForm) c.applications.get(j).document;

                             // Load FXML for templateCNIC
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("templateBform.fxml"));
                             try {
                                 Parent root = loader.load();
                                 templateBform controller = loader.getController();

                                 // Set values including CNIC-specific attributes
                                 controller.setValsAndSaveBForm(
                                         bform.fullname, bform.fathername, bform.mothername, bform.dob, bform.gender, bform.placeofbirth, bform.nationality, bform.religion
                                 );

                                 System.out.println("Passport image generated and saved successfully!");
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
                    else if (c.applications.get(j).type.equals("E")) {
                         System.out.println("Application Found!");
                         boolean b = db.updateShipmentIfEmailNull(idm.users.get(i).email, c.applications.get(j).type, 2500);
                         List<String> s = db.getShipmentDetailsByEmailAndDocType(idm.users.get(i).email, c.applications.get(j).type);
                         c.applications.get(j).document.payment = new Payment(idm.users.get(i).email, 2500, s.get(0), s.get(1), s.get(2));
                         // Retrieve IDCard data from the application
                         if (duplicate == false) {
                             Equivalence equivalence = (Equivalence) c.applications.get(j).document;

                             // Load FXML for templateCNIC
                             FXMLLoader loader = new FXMLLoader(getClass().getResource("templateEquivalence.fxml"));
                             try {
                                 Parent root = loader.load();
                                 templateEquivalence controller = loader.getController();

                                 // Set values including CNIC-specific attributes
                                 controller.setValsAndSaveEquivalence(
                                         equivalence.studentName, equivalence.fatherName, equivalence.dob, equivalence.gradeLevel, equivalence.year, equivalence.group, equivalence.percentage// Add DOE
                                 );

                                 System.out.println("Passport image generated and saved successfully!");
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }


                }
            }
        }

    }
    @FXML
    public void initialize() {
        addButtonToColumns();

    }

    private ObservableList<application> getApplications() {
        System.out.println("HelloPAY");
        ObservableList<application> applications = FXCollections.observableArrayList();

        // Loop through users and collect applications
        IDMatrix idm=IDMatrix.getInstance();
        String e=idm.id;
        for(int i=0;i<idm.users.size();i++) {
            if(idm.users.get(i).email.equals(e)) {
                Customer customer = (Customer) idm.users.get(i);
                // Using index-based for loop
                List<application> customerApplications = customer.getApplications();
                System.out.println(customerApplications.size());
                for (int j = 0; j < customerApplications.size(); j++) {
                    application customerApp = customerApplications.get(j);
                    System.out.println(customerApp.email+customerApp.type+customerApp.document.type);
                    // Assuming application has 'getStatus' and 'email' properties
                    // application app = new application(customerApp.type, customerApp.email);
                        applications.add(customerApp);

                }
            }
        }

        return applications;
    }




    //=====================BACK BUTTON ===============

    @FXML
    private void onBackButtonClick(ActionEvent event) throws IOException {
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



    @FXML
    public void onApplicationsButtonClick(ActionEvent event) throws IOException {
        duplicate=false;
        System.out.println("My Applications Opening My Applications section.");
        // Logic to display applications

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        doiColumn.setCellValueFactory(new PropertyValueFactory<>("doi"));
        doeColumn.setCellValueFactory(new PropertyValueFactory<>("doe"));

        country_P_Column.setCellValueFactory(new PropertyValueFactory<>("country"));
        typePassportColumn.setCellValueFactory(new PropertyValueFactory<>("typePassport"));
        SeriesColumn.setCellValueFactory(new PropertyValueFactory<>("series"));

        motherColumn.setCellValueFactory(new PropertyValueFactory<>("motherName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        birthplaceColumn.setCellValueFactory(new PropertyValueFactory<>("birthplace"));
        religionColumn.setCellValueFactory(new PropertyValueFactory<>("religion"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        subjectCountColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCount"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Load data into the table
        applicationTable.setItems(getApplications());

        // Get the target email (could be from a logged-in user or session)
        IDMatrix idm = IDMatrix.getInstance();
        String targetEmail = idm.id;  // Replace with the actual email you're searching for

        // Loop through each Customer object in the table and check for verification status
        for (application customerApp : applicationTable.getItems()) {
            if (targetEmail.equals(customerApp.getEmail())) {
                //if ("verified".equals(customerApp.getStatus()))
                // {
                   // System.out.println("Customer with email " + targetEmail + " is verified.");
                    customerApp.setPayButtonVisible(true);  // Make button visible for this row
                //} else {
                  //  customerApp.setPayButtonVisible(false);  // Hide button for this row
                //}
            }
        }

        // Hide other sections if needed
        btnAddLinks.setVisible(false);
        hyperlinkVBox.setVisible(false);
        applicationTable.setVisible(true);  // Show the TableView
    }

    @FXML
    private void onIDCardButtonClick(ActionEvent event) throws IOException {
        //showAlert("Create ID-Card", "Navigating to ID-Card creation.");
        // Logic to create an ID card
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateCNIC.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create ID Card");
        // Get the controller of templateCNIC and set CustFunc
        templateCNIC controller = loader.getController();
        controller.setCustFunc(this);  // Pass the current CustFunc instance
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();

    }

    @FXML
    private void onBFormButtonClick(ActionEvent event) throws IOException {
        //showAlert("Create B-Form", "Navigating to B-Form creation.");
        // Logic to create a B-Form
        //showAlert("Create ID-Card", "Navigating to ID-Card creation.");
        // Logic to create an ID card
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateBform.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create BFORM");
        // Get the controller of templateCNIC and set CustFunc
        templateBform controller = loader.getController();
        controller.setCustFunc(this);  // Pass the current CustFunc instance
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }

    @FXML
    private void onPassportButtonClick(ActionEvent event)throws IOException {
        //showAlert("Create ID-Card", "Navigating to ID-Card creation.");
        // Logic to create an ID card
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templatePassport.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create PASSPORT");
        // Get the controller of templateCNIC and set CustFunc
        templatePassport controller = loader.getController();
        controller.setCustFunc(this);  // Pass the current CustFunc instance
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }

    @FXML
    private void onDomicileButtonClick(ActionEvent event) throws IOException{
        //showAlert("Create ID-Card", "Navigating to ID-Card creation.");
        // Logic to create an ID card
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateDomicile.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create DOmicile");
        // Get the controller of templateCNIC and set CustFunc
        templateDomicile controller = loader.getController();
        controller.setCustFunc(this);  // Pass the current CustFunc instance
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }


    @FXML
    private void onGenerateEquivButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateEquivalence.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Equivalence");
        // Get the controller of templateCNIC and set CustFunc
        templateEquivalence controller = loader.getController();
        controller.setCustFunc(this);  // Pass the current CustFunc instance
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }

    @FXML
    private void onDuplicateDocButtonClick(ActionEvent event) {
        System.out.println("My Applications Opening My Applications section.");
        // Logic to display applications

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        doiColumn.setCellValueFactory(new PropertyValueFactory<>("doi"));
        doeColumn.setCellValueFactory(new PropertyValueFactory<>("doe"));

        country_P_Column.setCellValueFactory(new PropertyValueFactory<>("country"));
        typePassportColumn.setCellValueFactory(new PropertyValueFactory<>("typePassport"));
        SeriesColumn.setCellValueFactory(new PropertyValueFactory<>("series"));

        motherColumn.setCellValueFactory(new PropertyValueFactory<>("motherName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        birthplaceColumn.setCellValueFactory(new PropertyValueFactory<>("birthplace"));
        religionColumn.setCellValueFactory(new PropertyValueFactory<>("religion"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        subjectCountColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCount"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        // Load data into the table
        applicationTable.setItems(getApplications());

        // Get the target email (could be from a logged-in user or session)
        IDMatrix idm = IDMatrix.getInstance();
        String targetEmail = idm.id;  // Replace with the actual email you're searching for
        ObservableList<application> verifiedApplications = FXCollections.observableArrayList();

// Loop through each Customer object in the table, check for verification status, and add to the filtered list if verified
        for (application customerApp : getApplications()) {
            if ("verified".equals(customerApp.getStatus()) && targetEmail.equals(customerApp.getEmail())) {
                // Make the button visible for the verified application row
                customerApp.setPayButtonVisible(true);  // Make button visible for this row
                verifiedApplications.add(customerApp); // Add verified application to the filtered list
            }
        }

// Load only verified applications into the table
        applicationTable.setItems(verifiedApplications);
// Hide other sections if needed
        btnAddLinks.setVisible(false);
        hyperlinkVBox.setVisible(false);
        applicationTable.setVisible(true);  // Show the TableView
        duplicate=true;
    }

    // Utility method to show alerts for testing
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
