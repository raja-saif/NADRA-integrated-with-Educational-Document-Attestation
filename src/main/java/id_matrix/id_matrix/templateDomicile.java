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

public class templateDomicile {

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
    private TextField permanentAddress;
    @FXML
    private TextField province;
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView backgroundImageView;
    public CustFunc custfunc;
    public void setCustFunc(CustFunc custFunc) {
        // Example: Interact with CustFunc instance
        this.custfunc = custFunc;
    }
    public void setValsAndSaveDomicile(String fullName, String fatherName, String motherName, String dob, String gender, String address, String province) {
        // Populate text fields
        this.fullName.setText(fullName);
        this.fatherName.setText(fatherName);
        this.motherName.setText(motherName);
        this.dob.setText(dob);
        this.gender.setText(gender);
        this.permanentAddress.setText(address);
        this.province.setText(province);

        // Render the Domicile on the canvas
        renderDomicile();

        // Save the rendered image
        saveCanvasAsImage();
    }

    public void onSubmit(ActionEvent event)throws IOException {
        String name = fullName.getText();
        String father = fatherName.getText();
        String mother = motherName.getText();
        String dateOfBirth = dob.getText();
        String genderr = gender.getText();
        String address = permanentAddress.getText();
        String prov = province.getText();
        // Call method to render CNIC and save it



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
                a.fill_Domicile(idm.id, fullName.getText(), fatherName.getText(),motherName.getText(),  dob.getText(),permanentAddress.getText(),province.getText(),gender.getText());

                customer.applications.add(a); // Add the application to the customer's list
                // Proceed with adding customer to the database
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int customerId = dbManager.addApplication(idm.id, "unverified","D");

                dbManager.addDomicile(name,father,mother,dob.getText(),address,province.getText(),gender.getText(),idm.id,"Empty");
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
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        renderDomicile(name, father, mother, dateOfBirth, genderr, address, prov);
    }
    private void renderDomicile(String name, String father, String mother, String dob, String gender, String address, String province) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw background image on the canvas
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());


        // Set font for text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setFill(Color.BLACK);


        gc.fillText("Name: " + name, 150, 260);
        gc.fillText("Father's Name: " + father, 150, 300);
        gc.fillText("Mother's Name: " + mother, 150, 340);
        gc.fillText("Date of Birth: " + dob, 150, 380);
        gc.fillText("Gender: " + gender, 150, 420);
        gc.fillText("Permanent Address: " + address, 150, 460);
        gc.fillText("Province: " + province, 150, 500);

        // Save the canvas as a PNG file
        //saveCanvasAsImage();
    }
    private void renderDomicile() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw background image on the canvas
        Image background = backgroundImageView.getImage();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());


        // Set font for text
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.setFill(Color.BLACK);


        gc.fillText("Name: " + fullName.getText(), 150, 260);
        gc.fillText("Father's Name: " + fatherName.getText(), 150, 300);
        gc.fillText("Mother's Name: " + motherName.getText(), 150, 340);
        gc.fillText("Date of Birth: " + dob.getText(), 150, 380);
        gc.fillText("Gender: " + gender.getText(), 150, 420);
        gc.fillText("Permanent Address: " + permanentAddress.getText(), 150, 460);
        gc.fillText("Province: " + province.getText(), 150, 500);

        // Save the canvas as a PNG file
        //saveCanvasAsImage();
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
        File file = new File(userDir, "domicile.png");
        String url="DB\\"+ idm.id+"\\"+"domicile.png";
        DatabaseManager dbManager = DatabaseManager.getInstance();
        boolean b= dbManager.updateDomicileImage(idm.id,  url);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            System.out.println("DOMICILE image saved successfully in: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving CNIC image.");
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
