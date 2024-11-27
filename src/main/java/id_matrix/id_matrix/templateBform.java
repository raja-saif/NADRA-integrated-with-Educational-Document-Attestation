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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class templateBform{

    @FXML
    private TextField fullName;
    @FXML
    private TextField fatherName;
    @FXML
    private TextField motherName;
    @FXML
    private TextField dob;
    @FXML
    private TextField gender;
    @FXML
    private TextField placeOfBirth;
    @FXML
    private TextField nationality;
    @FXML
    private TextField religion;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView backgroundImageView;
    public CustFunc custfunc;
    public void setCustFunc(CustFunc custFunc) {
        // Example: Interact with CustFunc instance
        this.custfunc = custFunc;
    }
    public void setValsAndSaveBForm(String fullName, String fatherName, String motherName, String dob, String gender, String placeOfBirth, String nationality, String religion) {
        // Populate text fields
        this.fullName.setText(fullName);
        this.fatherName.setText(fatherName);
        this.motherName.setText(motherName);
        this.dob.setText(dob);
        this.gender.setText(gender);
        this.placeOfBirth.setText(placeOfBirth);
        this.nationality.setText(nationality);
        this.religion.setText(religion);

        // Render the BForm on the canvas
        renderBform();

        // Save the rendered image
        saveCanvasAsImage();
    }


    public void onSubmit() {
        String name = fullName.getText();
        String father = fatherName.getText();
        String mother = motherName.getText();
        String dateOfBirth = dob.getText();
        String genderValue = gender.getText();
        String place = placeOfBirth.getText();
        String nationalityValue = nationality.getText();
        String religionValue = religion.getText();




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

                a.fill_BForm(idm.id, fullName.getText(), fatherName.getText(),motherName.getText(), dob.getText(),placeOfBirth.getText(), nationality.getText(),religion.getText(),gender.getText());

                customer.applications.add(a); // Add the application to the customer's list
                // Proceed with adding customer to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int customerId = dbManager.addApplication(idm.id, "unverified","B");

                boolean b=dbManager.addBForm(name,father,mother,dob.getText(),placeOfBirth.getText(),nationalityValue,religion.getText(),genderValue,idm.id,"Empty");
                System.out.println("SAVED BFORM IN DB "+b);
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
        // Call method to render CNIC and save it
        renderBform(name, father, mother, dateOfBirth, genderValue, place, nationalityValue, religionValue);
    }

    private void renderBform(String name, String father, String mother, String dob, String gender, String place, String nationality, String religion) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw background image on the canvas
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        // Set font for text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setFill(Color.BLACK);

        // Draw the data on top of the image

        gc.fillText("Name: " + name, 150, 180);
        gc.fillText("Father's Name: " + father, 150, 230);
        gc.fillText("Mother's Name: " + mother, 150, 280);
        gc.fillText("Date of Birth: " + dob, 150, 330);
        gc.fillText("Gender: " + gender, 150, 380);
        gc.fillText("Place of birth: " + place, 150, 430);
        gc.fillText("Nationality: " + nationality, 150, 480);
        gc.fillText("Religion: " + religion, 150, 530);


        // Save the canvas as a PNG file
        //saveCanvasAsImage();
    }
    private void renderBform() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw background image on the canvas
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        // Set font for text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setFill(Color.BLACK);

        // Draw the data on top of the image

        gc.fillText("Name: " + fullName.getText(), 150, 180);
        gc.fillText("Father's Name: " + fatherName.getText(), 150, 230);
        gc.fillText("Mother's Name: " + motherName.getText(), 150, 280);
        gc.fillText("Date of Birth: " + dob.getText(), 150, 330);
        gc.fillText("Gender: " + gender.getText(), 150, 380);
        gc.fillText("Place of birth: " + placeOfBirth.getText(), 150, 430);
        gc.fillText("Nationality: " + nationality.getText(), 150, 480);
        gc.fillText("Religion: " + religion.getText(), 150, 530);


        // Save the canvas as a PNG file
        //saveCanvasAsImage();
    }

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
        File file = new File(userDir, "bform.png");
        String url="DB\\"+ idm.id+"\\"+"bform.png";
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean b= dbManager.updateBformImage(idm.id,  url);
        System.out.println("Image BFORM SAVED: "+b);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("BFORM image saved successfully in: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving CNIC image.");
        }
    }
    @FXML
    // Sidebar Button Click Functions
    public void onBackButtonClick(ActionEvent event) throws IOException {

        //showAlert("Back Button", "Navigating to the previous page.");
        // Logic to go back to the previous page
        // Load the Sign Up page FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustFunc.fxml"));
        Parent root = loader.load();

        // Get the current stage (window) and set the new scene
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("LOGIN");
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();
    }
}
