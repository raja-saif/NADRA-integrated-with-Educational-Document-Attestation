package id_matrix.id_matrix;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class templateEquivalence {

    // Initial Section
    @FXML
    private ComboBox<String> equivalenceType;
    @FXML
    private ComboBox<Integer> subjectCount;
    @FXML
    private Button submitEquivalence;
    @FXML
    private VBox initialSection;

    // Dynamic Input Section
    @FXML
    private VBox dynamicInputSection;
    @FXML
    private VBox dynamicFields;

    // Certificate Rendering Section
    @FXML
    private TextField studentName, fatherName, Dateofbirth, gradeLevel, Year, group;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private AnchorPane certificateSection;

    @FXML
    public TextField Percent;
    public CustFunc custfunc;

    public List<String> g;
    public void setCustFunc(CustFunc custFunc) {
        // Example: Interact with CustFunc instance
        this.custfunc = custFunc;
    }
    public templateEquivalence() {
        // Initialize ComboBoxes
        this.g = new ArrayList<>();
        this.equivalenceType = new ComboBox<>();
        this.subjectCount = new ComboBox<>();

        // Initialize Buttons
        this.submitEquivalence = new Button();

        // Initialize VBoxes
        this.initialSection = new VBox();
        this.dynamicInputSection = new VBox();
        this.dynamicFields = new VBox();

        // Initialize TextFields
        this.studentName = new TextField();
        this.fatherName = new TextField();
        this.Dateofbirth = new TextField();
        this.gradeLevel = new TextField();
        this.Year = new TextField();
        this.group = new TextField();

        // Initialize Canvas and ImageView for rendering
        this.canvas = new Canvas();
        this.backgroundImageView = new ImageView();
        this.certificateSection = new AnchorPane();
        this.Percent = new TextField();
    }

    public void setValsAndSaveEquivalence(String name, String father, String dob, String grade, String year, String group,String percentage) {
        // Populate text fields
        this.studentName.setText(name);
        this.fatherName.setText(father);
        this.Dateofbirth.setText(dob);
        this.gradeLevel.setText(grade);
        this.Year.setText(year);
        this.group.setText(group);
        this.Percent.setText(percentage);


        // Render the Equivalence certificate on the canvas
        renderEquivalenceCertificate();

        // Save the rendered image
        saveCanvasAsImageEquiv();
    }

    @FXML
    public void initialize() {
        // Populate equivalence type dropdown
        g=new ArrayList<>();
        equivalenceType.getItems().addAll("A Levels", "O Levels");

        // Populate subject count dropdown
        for (int i = 3; i <= 10; i++) {
            subjectCount.getItems().add(i);
        }
    }

    @FXML
    public void onEquivalenceSubmit(javafx.event.ActionEvent event) throws IOException{

        System.out.println("Subjects submitted:");
        for (int i = 0; i < dynamicFields.getChildren().size(); i++) {
            VBox fieldContainer = (VBox) dynamicFields.getChildren().get(i);
            TextField textField = (TextField) fieldContainer.getChildren().get(1);
            System.out.println("Subject " + (i + 1) + ": " + textField.getText());
            g.add(textField.getText());
            System.out.println(">>>");
            System.out.println("g->"+g);
        }

        // Hide dynamic input section and show certificate section
        dynamicInputSection.setVisible(false);
        certificateSection.setVisible(true);

//        String selectedType = equivalenceType.getValue();
//        Integer selectedSubjectCount = subjectCount.getValue();
//
//        if (selectedType == null || selectedSubjectCount == null) {
//            System.out.println("Please select both equivalence type and number of subjects.");
//            return;
//        }
//
//        // Hide initial section and show dynamic input section
//        initialSection.setVisible(false);
//        dynamicInputSection.setVisible(true);
//
//        // Create input fields dynamically
//        dynamicFields.getChildren().clear();
//        for (int i = 1; i <= subjectCount.getValue(); i++) {
//            Label label = new Label("Subject " + i + ":");
//            TextField textField = new TextField();
//            textField.setPromptText("Enter Subject " + i);
//            VBox fieldContainer = new VBox(5, label, textField);
//            dynamicFields.getChildren().add(fieldContainer);
//        }

        //FUNCTIONALITY

            // Get values from the input fields
            String studentname = studentName.getText();
            String fathername = fatherName.getText();
            String dob = Dateofbirth.getText();
            String gradelevel = gradeLevel.getText();
            String year = Year.getText();
            String Group = group.getText();
            String equivalencetype = equivalenceType.getValue();
            String SubjectCount = subjectCount.getValue().toString();


            // Print all the values
            System.out.println("Student Name: " + studentName);
            System.out.println("Father's Name: " + fatherName);
            System.out.println("Date of Birth: " + dob);
            System.out.println("Grade Level: " + gradeLevel);
            System.out.println("Year: " + year);
            System.out.println("Group: " + group);
            System.out.println("Equivalence Type: " + equivalenceType);
            System.out.println("Subject Count: " + subjectCount);
            System.out.println("In onSubmitEquivalence method");

            // Handle the equivalence application
            IDMatrix idm = IDMatrix.getInstance();
            System.out.println("In " + idm.users.size());

            for (int i = 0; i < idm.users.size(); ++i) {
                System.out.println(idm.users.get(i).email + " Fun");
            }

//            for (User user : idm.users) {
//                if (user instanceof Customer && user.email.equals(idm.id)) {
//                    System.out.println("In equivalence submission");
//                    Customer customer = (Customer) user; // Cast to Customer
//                    System.out.println("CUSTOMER ID: " + customer.email);
//
//                    // Create equivalence application
//                    application eqApp = new application("E", idm.id, "unverified");
//
//                    // Fill the equivalence application
//                    String series="";
//                    eqApp.fill_Equivalence(idm.id, studentName.getText(), fatherName.getText(), dob, gradeLevel.getText(), year, group.getText(),equivalencetype, SubjectCount, series);
//
//                    customer.applications.add(eqApp); // Add the equivalence application to the customer's list
//
//                    // Proceed with adding customer application to the database
//                    DatabaseManager dbManager = DatabaseManager.getInstance();
//                    int customerId = dbManager.addApplication(idm.id, "unverified", "E"); // "E" for equivalence
//                    boolean saved = dbManager.addEquivalence(studentName.getText(), fatherName.getText(), dob, gradeLevel.getText(), year, group.getText(), equivalencetype, subjectCount.getValue().toString(), series, equivalencetype,idm.id,"Empty");
//
//                    System.out.println("Equivalence Saved in DB: " + saved);
//                    if (customerId != -1) {
//                        System.out.println("Customer added with ID: " + customerId);
//                    } else {
//                        System.out.println("Failed to add customer to the database.");
//                    }
//                    System.out.println("Equivalence application added successfully for customer: " + customer.name);
//                }
//            }

//            // After processing, navigate to the equivalence functions screen
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller for CustFunc to modify UI components
//            CustFunc custfunc = loader.getController();
//
//            // Get the current stage (window) and set the new scene
//            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.setTitle("CUST FUNC");
//
//            // Optionally, render equivalence output (if needed)
//            renderEquivalenceCertificate(studentName.getText(),fathername, dob, gradeLevel.getText(), year,Group);


    }
    @FXML
    public void onfirstSubmit() {
        System.out.println("On first submit");
        initialSection.setVisible(false);
        // Hide initial section and show dynamic input section
        initialSection.setVisible(false);
        dynamicInputSection.setVisible(true);

        // Clear existing fields if any
        dynamicFields.getChildren().clear();

        // Create input fields dynamically
        for (int i = 1; i <= subjectCount.getValue(); i++) {
            Label label = new Label("Subject " + i + ":");
            TextField textField = new TextField();
            textField.setPromptText("Enter Subject " + i);
            VBox fieldContainer = new VBox(5, label, textField);
            dynamicFields.getChildren().add(fieldContainer);
        }
        dynamicInputSection.setVisible(true);
    }
    @FXML
    public void onsecondsubmit() {
        System.out.println("Subjects submitted:");
        for (int i = 0; i < dynamicFields.getChildren().size(); i++) {
            VBox fieldContainer = (VBox) dynamicFields.getChildren().get(i);
            TextField textField = (TextField) fieldContainer.getChildren().get(1);
            System.out.println("Subject " + (i + 1) + ": " + textField.getText());
            g.add(textField.getText());
            System.out.println(">>>");
            System.out.println("g->"+g);
        }

        // Hide dynamic input section and show certificate section
        dynamicInputSection.setVisible(false);
        certificateSection.setVisible(true);
    }

    @FXML
    public void onGenerateCertificate(javafx.event.ActionEvent event) throws IOException {

        IDMatrix idm = IDMatrix.getInstance();
        for (User user : idm.users) {
            if (user instanceof Customer && user.email.equals(idm.id)) {
                System.out.println("In equivalence submission");
                Customer customer = (Customer) user; // Cast to Customer
                System.out.println("CUSTOMER ID: " + customer.email);

                // Create equivalence application
                application eqApp = new application("E", idm.id, "unverified");

                // Fill the equivalence application
                //List<String> series;

                eqApp.fill_Equivalence(idm.id, studentName.getText(), fatherName.getText(),Dateofbirth.getText() , gradeLevel.getText(), Year.getText(), group.getText(),equivalenceType.getValue(), subjectCount.getValue().toString(), g,"null");

                customer.applications.add(eqApp); // Add the equivalence application to the customer's list

                // Proceed with adding customer application to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                System.out.println("Displaying GRADES "+ g);
                int customerId = dbManager.addApplication(idm.id, "unverified", "E"); // "E" for equivalence
                boolean saved = dbManager.addEquivalence(studentName.getText(), fatherName.getText(), Dateofbirth.getText(), gradeLevel.getText(),  Year.getText(), group.getText(), equivalenceType.getValue(), subjectCount.getValue().toString(), equivalenceType.getValue(),idm.id,"Empty",g);

                System.out.println("Equivalence Saved in DB: " + saved);
                if (customerId != -1) {
                    System.out.println("Customer added with ID: " + customerId);
                } else {
                    System.out.println("Failed to add customer to the database.");
                }
                System.out.println("Equivalence application added successfully for customer: " + customer.name);
            }
        }

    // After processing, navigate to the equivalence functions screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
            Parent root = loader.load();

            // Get the controller for CustFunc to modify UI components
            CustFunc custfunc = loader.getController();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("CUST FUNC");

            // Optionally, render equivalence output (if needed)
            renderEquivalenceCertificate(studentName.getText(),fatherName.getText(),Dateofbirth.getText(), gradeLevel.getText(),Year.getText(),group.getText());

//
//        String name = studentName.getText();
//        String father = fatherName.getText();
//        String dob = Dateofbirth.getText();
//        String grade = gradeLevel.getText();
//        String year = Year.getText();
//        String Group =group.getText();
//        renderEquivalenceCertificate(name, father, dob, grade, year, Group);
    }

    private void renderEquivalenceCertificate(String name, String father, String dob, String grade, String year, String group) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        // Set font and draw text
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 14));
        gc.setFill(javafx.scene.paint.Color.BLACK);

        gc.fillText(name, 190, 180);
        gc.fillText(father, 400, 180);
        gc.fillText(dob, 130, 200);
        gc.fillText(grade, 340, 200);
        gc.fillText(year, 470, 215);
        gc.fillText(group, 210, 255);

        //saveCanvasAsImage();
    }
    private void renderEquivalenceCertificate() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        // Set font and draw text
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 14));
        gc.setFill(javafx.scene.paint.Color.BLACK);

        gc.fillText(studentName.getText(), 190, 180);
        gc.fillText(fatherName.getText(), 400, 180);
        gc.fillText(Dateofbirth.getText(), 130, 200);
        gc.fillText(gradeLevel.getText(), 340, 200);
        gc.fillText(Year.getText(), 470, 215);
        gc.fillText(group.getText(), 210, 255);

        //saveCanvasAsImage();
    }

    public void saveCanvasAsImageEquiv() {
        System.out.println("In saveCanvasAsImage EQUIV method");

        // Capture the content of the canvas
        javafx.scene.image.WritableImage image = canvas.snapshot(null, null);

        // Get the width and height of the canvas
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // Ensure canvas has valid dimensions
        if (canvasWidth <= 0 || canvasHeight <= 0) {
            System.out.println("Canvas has invalid dimensions: " + canvasWidth + " x " + canvasHeight);
            return;
        }

        // Create the root "DB" directory if it doesn't exist
        File dbDir = new File("DB");
        if (!dbDir.exists()) {
            boolean dbCreated = dbDir.mkdir();
            if (!dbCreated) {
                System.out.println("Failed to create root DB directory.");
                return;
            }
        }

        // Get user email from IDMatrix instance
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

        // Save the image inside the user's directory with a specific filename
        File file = new File(userDir, "equivalence_certificate.png");
        String url = "DB\\" + idm.id + "\\" + "equivalence_certificate.png";

        // Update the URL in the database if needed
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean updateSuccess = dbManager.updateEquivalenceImage(idm.id, url);

        try {
            // Write the image to file
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
            System.out.println("Equivalence certificate image saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving equivalence certificate image.");
        }
    }


}
