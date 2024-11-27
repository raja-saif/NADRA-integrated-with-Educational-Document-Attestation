package id_matrix.id_matrix;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class templateCNIC {

    @FXML
    public TextField country;
    @FXML
    public TextField fullName;
    @FXML
    public TextField fatherName;
    @FXML
    public TextField gender;
    @FXML
    public TextField dob;
    @FXML
    public TextField doi;
    @FXML
    public TextField doe;
    @FXML
    public TextField cnic;
    @FXML
    public Canvas canvas;
    @FXML
    public ImageView backgroundImageView;
    @FXML
    public ImageView uploadedImageView;  // New ImageView for the uploaded image

    public Image uploadedImage;
    private GraphicsContext gc;
    public CustFunc custfunc;

    public templateCNIC() {
        this.fullName = new TextField(); // Manually initialize TextField
        this.fatherName = new TextField();
        this.gender = new TextField();
        this.dob = new TextField();
        this.doi = new TextField();
        this.doe = new TextField();
        this.cnic = new TextField();
        this.country = new TextField();
        this.canvas = new Canvas();
        this.backgroundImageView = new ImageView();
        this.uploadedImageView = new ImageView();


    }
    public void setCustFunc(CustFunc custFunc) {
        // Example: Interact with CustFunc instance
        this.custfunc = custFunc;
    }
    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        loadCNICBackground();
    }

    private void loadCNICBackground() {
        // Load the CNIC template image
        Image cnicTemplate = new Image("cnic.png");
        backgroundImageView.setImage(cnicTemplate);
    }
//    public void setVals( String name, String fatherName, String gender, String country, String dob,String cnic,String doi,String doe)
//    {
//        // Setting values for the TextFields
//        this.fullName.setText(name);
//        this.fatherName.setText(fatherName);  // Setting fatherName field
//        this.gender.setText(gender);          // Setting gender field
//        this.dob.setText(dob);                // Setting dob field
//        this.cnic.setText(cnic);              // Setting cnic field
//        this.doi.setText(doi);                // Setting doi field
//        this.doe.setText(doe);                // Setting doe field
//        this.country.setText(country);        // Setting country field
//
//        //CREATE CNIC
//        renderCNIC(country, name, dob, fatherName, gender, cnic, doi, doe);
//    }
public void setValsAndSave(String name, String fatherName, String gender, String country, String dob, String cnic, String doi, String doe) {
    // Populate text fields
    this.fullName.setText(name);
    this.fatherName.setText(fatherName);
    this.gender.setText(gender);
    this.country.setText(country);
    this.dob.setText(dob);
    this.cnic.setText(cnic);
    this.doi.setText(doi);
    this.doe.setText(doe);

    // Render the CNIC on the canvas
    renderCNIC();

    // Save the rendered image
    saveCanvasAsImage();
}
    private void renderCNIC() {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the background template
        Image cnicTemplate = backgroundImageView.getImage();
        gc.drawImage(cnicTemplate, 0, 0, canvas.getWidth(), canvas.getHeight());

        // Set font and text color
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 14));

        // Draw CNIC fields aligned with template
        gc.fillText( fullName.getText(), 160, 120); // Adjust X and Y for alignment
        gc.fillText(  fatherName.getText(), 160, 210);
        gc.fillText( gender.getText(), 160, 275);
        gc.fillText( country.getText(), 250, 275);
        gc.fillText( dob.getText(), 320, 325);
        gc.fillText( cnic.getText(), 160, 325);
        gc.fillText( doi.getText(), 150, 380);
        gc.fillText( doe.getText(), 320, 380);


    }
    // Save canvas as image
    private void saveCanvasAsImage() {
        // Take a snapshot of the canvas
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        // Get user's email
        IDMatrix idm = IDMatrix.getInstance();
        String email = idm.id; // Assume `idm.id` contains the email

        // Create the root "DB" directory
        File dbDir = new File("DB");
        if (!dbDir.exists()) {
            boolean dbCreated = dbDir.mkdir();
            if (!dbCreated) {
                System.out.println("Failed to create root DB directory.");
                return;
            }
                   }

        // Create a subdirectory inside "DB" named after the user's email
        File userDir = new File(dbDir, email);
        if (!userDir.exists()) {
            boolean userDirCreated = userDir.mkdir();
            if (!userDirCreated) {
                System.out.println("Failed to create directory for user: " + email);
                return;
            }
        }

        // Save the image inside the user's directory
        File file = new File(userDir, "cnic.png");
        String url="DB\\"+ idm.id+"\\"+"cnic.png";
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean b= dbManager.updateIDCardImage(idm.id,  url);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("CNIC image saved successfully in: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving CNIC image.");
        }
    }


    // Upload image method (optional)
    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            uploadedImage = new Image(file.toURI().toString());
            uploadedImageView.setImage(uploadedImage);
        }
    }
    public void onSubmit(ActionEvent event) throws IOException {
        String Country = country.getText();
        String name = fullName.getText();
        String dateOfBirth = dob.getText();
        String father = fatherName.getText();
        String genderr = gender.getText();

        // Call method to render CNIC and save it
        String CNIC="";
        String DOI="";
        String DOE="";
        renderCNIC(Country, name, dateOfBirth, father, genderr, CNIC, DOI, DOE);


        //rEATING APPLICATION
        IDMatrix idm = IDMatrix.getInstance();
        System.out.println("In"+idm.users.size());
        for(int i=0;i<idm.users.size();++i)
        {
            System.out.println(idm.users.get(i).email+"Fun");
        }
        for (User user : idm.users) {
            System.out.println("Fun");
            if (user instanceof Customer && user.email.equals(idm.id)) {

                System.out.println("In");
                Customer customer = (Customer) user; // Cast to Customer
                System.out.println("CUSTOMER ID "+ customer.email +" ");
                application a = new application("I",idm.id,"unverified");
                String cnic = "";
                String doi = "";
                String doe = "";
                a.fill_IDcard(idm.id, fullName.getText(), fatherName.getText(), gender.getText(), country.getText(), dob.getText(),cnic,doi,doe);

                customer.applications.add(a); // Add the application to the customer's list
                // Proceed with adding customer to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int customerId = dbManager.addApplication(idm.id, "unverified","I");
                String cnicc = "";
                dbManager.addIDCard(cnicc,fullName.getText(),fatherName.getText(),gender.getText(),dob.getText(),country.getText(),idm.id,"uploadedImageView");
                if (customerId != -1) {
                    System.out.println("Customer added with ID: " + customerId);
                } else {
                    System.out.println("Failed to add customer to the database.");
                }
                System.out.println("Application added successfully for customer: " + customer.name);

                //After adding application to customer, add templatecnic to application aswell
                //customer.app
            }
        }
        //HEAD BACK
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
        Parent root = loader.load();

        // Get the controller for CustFunc to modify UI components
        CustFunc custfunc = loader.getController();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("CUST FUNC");
    }
    public void renderCNIC(String cnic, String name, String dob, String father, String gender, String CNIC, String doi, String doe) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw background image on the canvas
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        // If an uploaded image exists, draw it on the canvas (optional)
        if (uploadedImage != null) {
            gc.drawImage(uploadedImage, 450, 120, 100, 100); // Example position and size for the uploaded image
        }

        // Set font for text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setFill(Color.BLACK);


        // Draw the data on top of the image
        gc.fillText(cnic, 250, 275);
        gc.fillText(name, 160, 120);
        gc.fillText(dob, 320, 325);
        gc.fillText(father, 160, 210);
        gc.fillText(gender, 160, 275);

        // Save the canvas as a PNG file
        //saveCanvasAsImage();
    }

    @FXML
    // Sidebar Button Click Functions
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

    @FXML
    private void onDocumentsButtonClick(ActionEvent event) throws IOException{
        System.out.println("My Documents Opening My Documents section.");
        // Logic to display documents
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
        Parent root = loader.load();

        // Get the controller for CustFunc to modify UI components
        CustFunc custfunc = loader.getController();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Documents");

        // Adjust the visibility of elements in CustFunc
        // Set applicationTable to not visible (hide it)
        custfunc.applicationTable.setVisible(false);

        // Make the hyperlinkVBox and btnAddLinks visible
        custfunc.hyperlinkVBox.setVisible(true);
        custfunc.btnAddLinks.setVisible(true);

        // Optionally, call methods from CustFunc to perform additional actions
        custfunc.onAddLinksButtonClick(); // This triggers the action if needed

        // Show the new scene
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();

    }

    @FXML
    public void onApplicationsButtonClick(ActionEvent event)throws IOException {
        System.out.println("Opening My Applications section.");

        // Load the CustFunc FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
        Parent root = loader.load();

        // Get the controller for CustFunc to modify UI components
        CustFunc custfunc = loader.getController();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("My Applications");

        // Call the onApplicationsButtonClick method of CustFunc
        custfunc.onApplicationsButtonClick(event); // Pass the ActionEvent if necessary

        // Set visibility for necessary elements
        custfunc.applicationTable.setVisible(true); // Ensure application table is visible
        custfunc.btnAddLinks.setVisible(false);    // Hide the add links button
        custfunc.hyperlinkVBox.setVisible(false);  // Hide the hyperlink section

        // Show the updated scene
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();


    }

    @FXML
    public void onIDCardButtonClick() {
        System.out.println("Create ID-Card button clicked.");
        // Add logic to navigate to the ID Card creation screen
    }

    @FXML
    public void onBFormButtonClick() {
        System.out.println("Create B-Form button clicked.");
        // Add logic to navigate to the B-Form creation screen
    }

    @FXML
    public void onPassportButtonClick() {
        System.out.println("Create Passport button clicked.");
        // Add logic to navigate to the passport creation screen
    }

    @FXML
    public void onDomicileButtonClick() {
        System.out.println("Create Domicile button clicked.");
        // Add logic to navigate to the domicile creation screen
    }

    @FXML
    public void onVerifyEduDocButtonClick() {
        System.out.println("Verify Educational Document button clicked.");
        // Add logic to navigate to the document verification screen
    }

    @FXML
    public void onGenerateEquivButtonClick() {
        System.out.println("Generate Equivalence button clicked.");
        // Add logic to navigate to the equivalence generation screen
    }

    @FXML
    public void onDuplicateDocButtonClick() {
        System.out.println("Duplicate Document button clicked.");
        // Add logic to navigate to the duplicate document screen
    }
}
