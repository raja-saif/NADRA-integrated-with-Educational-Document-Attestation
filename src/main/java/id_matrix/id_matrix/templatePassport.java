package id_matrix.id_matrix;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class templatePassport {

    // Define the TextFields with corresponding IDs from the FXML file
    @FXML
    private TextField nameField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField sexField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField dateOfIssueField;
    @FXML
    private TextField validUntilField;
    @FXML
    private TextField typeField;

    // Canvas and ImageView objects for displaying passport background and uploaded images
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView uploadedImageView;

    private GraphicsContext gc;
    private Image uploadedImage;
    public CustFunc custfunc;
    public void setCustFunc(CustFunc custFunc) {
        // Example: Interact with CustFunc instance
        this.custfunc = custFunc;
    }
    public void setValsAndSavePassport(String name, String dob, String sex, String country, String dateOfIssue, String validUntil, String type) {
        // Populate text fields
        this.nameField.setText(name);
        this.dobField.setText(dob);
        this.sexField.setText(sex);
        this.countryField.setText(country);
        this.dateOfIssueField.setText(dateOfIssue);
        this.validUntilField.setText(validUntil);
        this.typeField.setText(type);

        // Render the Passport on the canvas
        renderPassport();

        // Save the rendered image
        saveCanvasAsImage();
    }

    public templatePassport() {
        this.nameField = new TextField(); // Initialize TextField manually
        this.dobField = new TextField();
        this.sexField = new TextField();
        this.countryField = new TextField();
        this.dateOfIssueField = new TextField();
        this.validUntilField = new TextField();
        this.typeField = new TextField();
        this.canvas = new Canvas();
        this.backgroundImageView = new ImageView();
        this.uploadedImageView = new ImageView();
    }

    @FXML
    public void initialize() {
        canvas.setWidth(800);  // Set the desired width
        canvas.setHeight(600); // Set the desired height

        gc = canvas.getGraphicsContext2D();
        loadPassportBackground();
    }

    private void loadPassportBackground() {
        // Load the passport template image
        Image passportTemplate = new Image("Passportt.png");
        backgroundImageView.setImage(passportTemplate);
    }

    @FXML
    public void onSubmit(ActionEvent event)throws IOException {
        String name = nameField.getText();
        String dob = dobField.getText();
        String sex = sexField.getText();
        String country = countryField.getText();
        String dateOfIssue = dateOfIssueField.getText();
        String validUntil = validUntilField.getText();
        String type = typeField.getText();
        String series = "";
        // Print all the values
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Sex: " + sex);
        System.out.println("Country: " + country);
        System.out.println("Date of Issue: " + dateOfIssue);
        System.out.println("Valid Until: " + validUntil);
        System.out.println("Type: " + type);
        System.out.println("In onSubmit method");
        // Call method to render CNIC and save it

        //RENDER BAKCHODI KAR GEYA THA IS LIYE

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

                a.fill_Passport(idm.id, name, sex,country,dob,dateOfIssue,validUntil,series,type);

                customer.applications.add(a); // Add the application to the customer's list
                // Proceed with adding customer to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int customerId = dbManager.addApplication(idm.id, "unverified","P");
                String cnicc = "";
                boolean b= dbManager.addPassport(name,  dob, sex,country,dateOfIssue,validUntil,  type,series, idm.id,"Empty");

                System.out.println("PAssport Saved in DB "+b);
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
        renderPassport(name, dob, sex, country, dateOfIssue, validUntil, type, series);
    }

    private void renderPassport(String name, String dob, String sex, String country, String dateOfIssue, String validUntil, String type, String series ) {
        System.out.println("In renderPassport method");
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the background template
        Image passportTemplate = backgroundImageView.getImage();
        if (passportTemplate == null) {
            System.out.println("Passport template image is null.");
            return;
        }
        gc.drawImage(passportTemplate, 0, 0, canvas.getWidth(), canvas.getHeight());
        System.out.println("In RENDER method");

        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Sex: " + sex);
        System.out.println("Country: " + country);
        System.out.println("Date of Issue: " + dateOfIssue);
        System.out.println("Valid Until: " + validUntil);
        System.out.println("Type: " + type);

        // Set font and text color
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 30));

        // Draw passport fields on the canvas
        gc.fillText(name, 477, 186);           // Name field
        gc.fillText(dob, 477, 238);            // Date of Birth field
        gc.fillText(sex, 477, 280);           // Sex field
        gc.fillText(country, 477, 320);       // Country field
        gc.fillText(dateOfIssue, 450, 390);   // Date of Issue field
        gc.fillText(validUntil, 450 ,440);    // Valid Until field
        gc.fillText(type, 620, 390);          // Type field

        // Save canvas as image
        //saveCanvasAsImage();
    }
    private void renderPassport () {
        System.out.println("In renderPassport method");
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the background template
        Image passportTemplate = backgroundImageView.getImage();
        if (passportTemplate == null) {
            System.out.println("Passport template image is null.");
            return;
        }
        gc.drawImage(passportTemplate, 0, 0, canvas.getWidth(), canvas.getHeight());
        System.out.println("In RENDER method");

        System.out.println("Name: " + nameField.getText());
        System.out.println("Date of Birth: " + dobField.getText());
        System.out.println("Sex: " + sexField.getText());
        System.out.println("Country: " + countryField.getText());
        System.out.println("Date of Issue: " + dateOfIssueField.getText());
        System.out.println("Valid Until: " + validUntilField.getText());
        System.out.println("Type: " + typeField.getText());

        // Set font and text color
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 30));

        // Draw passport fields on the canvas
        gc.fillText(nameField.getText(), 477, 186);           // Name field
        gc.fillText(dobField.getText(), 477, 238);            // Date of Birth field
        gc.fillText(sexField.getText(), 477, 280);           // Sex field
        gc.fillText(countryField.getText(), 477, 320);       // Country field
        gc.fillText(dateOfIssueField.getText(), 450, 390);   // Date of Issue field
        gc.fillText(validUntilField.getText(), 450 ,440);    // Valid Until field
        gc.fillText(typeField.getText(), 620, 390);          // Type field

        // Save canvas as image
        //saveCanvasAsImage();
    }

    private void saveCanvasAsImage() {
        System.out.println("In saveCanvasAsImage method");
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // Ensure canvas has valid dimensions
        if (canvasWidth <= 0 || canvasHeight <= 0) {
            System.out.println("Canvas has invalid dimensions: " + canvasWidth + " x " + canvasHeight);
            return;
        }

        // Create WritableImage
        WritableImage writableImage = new WritableImage((int) canvasWidth, (int) canvasHeight);

        // Capture content into WritableImage
        canvas.snapshot(null, writableImage);

        // Save image
        // Create the root "DB" directory
        File dbDir = new File("DB");
        if (!dbDir.exists()) {
            boolean dbCreated = dbDir.mkdir();
            if (!dbCreated) {
                System.out.println("Failed to create root DB directory.");
                return;
            }
        }
        IDMatrix idm = IDMatrix.getInstance();
        String email = idm.id; // Assume `idm.id` contains the email
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
        File file = new File(userDir, "passport.png");
        String url="DB\\"+ idm.id+"\\"+"passport.png";
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean b= dbManager.updatePassportImage(idm.id,  url);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("Passport image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving passport image.");
        }
    }

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

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        // Navigate to the previous screen (e.g., Login page)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("LOGIN");
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }
}
