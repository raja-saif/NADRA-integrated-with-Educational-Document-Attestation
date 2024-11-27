package id_matrix.id_matrix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class IDMatrix extends Application {
    public String id;
    public List<User> users;
    private static IDMatrix ins;

    public IDMatrix() {
        users = new ArrayList<>();


    }

    public static IDMatrix getInstance() {
        if (ins == null) {
            ins = new IDMatrix();

        }

        return ins;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //LOAD EVERYTING
        loadUsers();
        loadDocuments();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ID Matrix");
        stage.setScene(scene);
        stage.setMaximized(true); // Make the window maximized
        stage.setFullScreen(true); // Optional: Make the application full screen
        stage.show();



    }

    public List<Educational> Display_Edu_Documents(String email) {
        List<Educational> e = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail() == email && user instanceof Customer) {
                Customer c = (Customer) user;
                for (Document d : c.getDocuments()) {
                    if (d instanceof Educational) {
                        e.add((Educational) d);
                    }
                }
            }
        }
        return e;
    }

    public boolean isEmailUnique(String email) {
        if (users == null) {
            users = new ArrayList<>();
        }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }
    public void loadDocuments()
    {
        System.out.println("LOAD DOC");
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dbManager.loadApplicationsFromDatabase();
        System.out.println("APPLICATIONS loaded from database: " + users.size());
//        for(int i=0;i<users.size();i++)
//        {
//            users.get(i).displayApplications();
//        }
    }
    public void loadUsers() {
        System.out.println("CONSTRUCTOR");
        if (users == null) {
            users = new ArrayList<>();
        }
        System.out.println("CONSTRUCTOR");
        DatabaseManager dbManager = DatabaseManager.getInstance();
        users = dbManager.loadUsersFromDatabase();
        System.out.println("Users loaded from database: " + users.size());
    }

    public List<application> ViewPendingApplications() {
        List<application> a = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Customer) {
                Customer c = (Customer) user;
                for (application app : c.getApplications()) {
                    if (app.getStatus().equals("unverified")) {
                        a.add(app);
                    }
                }
            }
        }
        return a;
    }

    public void addCustomer(String name, String phone, String email, String password, String address, String age) {
        User u = new Customer(name, phone, email, password, address, age);
        users.add(u);
    }

    public void addEmployee(String name, String phone, String email, String password, String address, String age) {
        User u = new Employee(name, phone, email, password, address, age);
        users.add(u);
    }

    public void addAdmin(String name, String phone, String email, String password, String address, String age) {
        User u = new Admin(name, phone, email, password, address, age);
        users.add(u);
    }
}
