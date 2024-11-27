package id_matrix.id_matrix;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.sql.*;

//class IDMatrixManager {
//    private List<User> users;
//    private static IDMatrixManager ins;
//
//    private IDMatrixManager() {
//        users = new ArrayList<>();
//    }
//
//    public static IDMatrixManager getInstance() {
//        if (ins == null) {
//            ins = new IDMatrixManager();
//        }
//        return ins;
//    }
//
//    public List<Educational> Display_Edu_Documents(int id) {
//        List<Educational> e = new ArrayList<>();
//        for (User user : users) {
//            if (user.getId() == id && user instanceof Customer) {
//                Customer c = (Customer) user;
//                for (Document d : c.getDocuments()) {
//                    if (d instanceof Educational) {  // Check if it's of type Educational
//                        e.add((Educational) d);
//                    }
//                }
//            }
//        }
//        return e;
//    }
//
//    public List<application> ViewPendingApplications() {
//        List<application> a = new ArrayList<>();
//        for (User user : users) {
//            if (user instanceof Customer) {
//                Customer c = (Customer) user;
//                for (application app : c.getApplications()) {
//                    if (app.getStatus().equals("Pending")) { // Use .equals() for string comparison
//                        a.add(app);
//                    }
//                }
//            }
//        }
//        return a;
//    }
//
//    public void addCustomer(String name, String phone, String email, String password, String address, String age) {
//        User u = new Customer(name, phone, email, password, address, age);
//        users.add(u);
//    }
//
//    public void addEmployee(String name, String phone, String email, String password, String address, String age) {
//        User u = new Employee(name, phone, email, password, address, age);
//        users.add(u);
//    }
//
//    public void addAdmin(String name, String phone, String email, String password, String address, String age) {
//        User u = new Admin(name, phone, email, password, address, age);
//        users.add(u);
//    }
//}


// Biometric Class
class Biometric {
    private int id;
    private String fingerprint;

    public Biometric(int id, String fingerprint) {
        this.id = id;
        this.fingerprint = fingerprint;
    }
}

// Abstract User Class
class User {
   // protected int id;
   public String name;
    public String phone;
    public String email;
    public String password;
    public String address;
    public String age;
    public String type;
    public static int idCounter = 0;


    public User(String name, String phone, String email, String password, String address,String age) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.age = age;
     //   this.id = ++idCounter;

    }
    //public int getId() {
      //  return id;
    //}
    public String getName() {

        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    void displayApplications(){};
}

// Employee Class
class Employee extends User {
    public List<Customer> customers;

    public Employee(String name, String phone, String email, String password,String address,String age) {
        super(name, phone, email, password, address, age);
        type="E";
        this.customers = new ArrayList<>();
    }

    public void manageCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
}

// Admin Class
class Admin extends User {
    public List<Employee> employees;

    public Admin(String name, String phone, String email, String password,String address,String age) {
        super(name, phone, email, password, address, age);
        type="A";
        this.employees = new ArrayList<>();
    }

    public void manageEmployee(Employee employee) {
        employees.add(employee);
    }
}

// Customer Class
class Customer extends User {
    public List<application> applications;
    public List<Document> documents;
    public List<Payment> payments;

    public Customer(String name, String phone, String email, String password,String address,String age) {
        super(name, phone, email, password, address, age);
        type="C";
        this.applications = new ArrayList<>();
        this.documents = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public void addApplication(application application) {
        applications.add(application);
    }
    public void displayApplications(){
        for(int i=0;i<applications.size();i++){
            System.out.println(applications.get(i).email+" "+applications.get(i).getStatus()+" "+applications.get(i).type+".  Details: "+applications.get(i).document.email+" "+applications.get(i).document.Name+" "+applications.get(i).document.image);
        }
    }
    public void addDocument(Document document) {
        documents.add(document);
    }

    public void makePayment(Payment payment) {
        payments.add(payment);
    }
    public List <application> getApplications() {
        return applications;
    }
    List<Document> getDocuments() {
        return documents;
    }
    List<Payment> getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
}

// Specialized Document Classes
class Educational extends Document {
    private String cnic;
    public Educational(){};
    public Educational(String id, String firstName, String image, String cnic) {
        super(id, firstName, image);
        type="E";
        this.cnic = cnic;
    }
}

class Passport extends Document {
    public String name;
    public String dob;
    public String gender;
    public String country;
    public String DateOfIssue;
    public String DateOfExpiry;
    public String TypeP;
    public String series;
    public Passport(){}
    // Constructor with parameters
    public Passport(String email, String firstName, String lastName, String dob,
                    String gender, String country, String dateOfIssue, String dateOfExpiry,
                    String typeP, String series, String image, String cnic) {
        // Initialize the parent class properties
        super(email, firstName, image);

        // Initialize the fields specific to the Passport class
        this.name = firstName + " " + lastName;
        this.dob = dob;
        this.gender = gender;
        this.country = country;
        this.DateOfIssue = dateOfIssue;
        this.DateOfExpiry = dateOfExpiry;
        this.TypeP = typeP;
        this.series = series;
    }
    public void setSeries(String series) {
        this.series = series;
    }
    // Getter methods
    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getDateOfIssue() {
        return DateOfIssue;
    }

    public String getDateOfExpiry() {
        return DateOfExpiry;
    }

    public String getTypeP() {
        return TypeP;
    }

    public String getSeries() {
        return series;
    }
}

class Domicile extends Document {

    public String fullname;
    public String fathername;
    public String mothername;
    public String dob;
    public String address;
    public String province;
    public String gender;

    public Domicile(){};
    // Parameterized constructor
    public Domicile(String id, String firstName, String lastName, String email,
                    String jobTitle, String image, String cnic,
                    String fullname, String fathername, String mothername,
                    String dob, String address, String province, String gender) {
        super(email, firstName, image); // Initializes fields in the parent class
        this.type = "D"; // Sets the document type to "D" for Domicile

        // Initialize Domicile-specific fields
        this.fullname = fullname;
        this.fathername = fathername;
        this.mothername = mothername;
        this.dob = dob;
        this.address = address;
        this.province = province;
        this.gender = gender;
    }
    // Getter methods
    public String getFullname() {
        return fullname;
    }

    public String getFathername() {
        return fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getGender() {
        return gender;
    }
}

class BForm extends Document {

    public String fullname;
    public String fathername;
    public String mothername;
    public String dob;
    public String placeofbirth;
    public String nationality;

    public String religion;
    public String gender;
    public BForm(){};
    public BForm(String fullname, String fathername, String mothername, String dob, String placeofbirth,
                 String nationality, String religion, String gender, String email, String firstName,
                 String lastName, String jobTitle, String image, String cnic) {
        super(email, firstName, image);  // Calling the constructor of the Document class
        this.fullname = fullname;
        this.fathername = fathername;
        this.mothername = mothername;
        this.dob = dob;
        this.placeofbirth = placeofbirth;
        this.nationality = nationality;
        this.religion = religion;
        this.gender = gender;
        this.type = "B";  // Assuming 'type' is a field in the Document class, as mentioned earlier

    }
    // Getter methods
    public String getFullname() {
        return fullname;
    }

    public String getFathername() {
        return fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public String getDob() {
        return dob;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getReligion() {
        return religion;
    }

    public String getGender() {
        return gender;
    }
}

class IDCard extends Document {
    public String cnic;
    public String name;
    public String FatherName;
    public String gender;
    public String DOB;
    public String DOI;
    public String DOE;
    public String country;
    public String image;
    public templateCNIC templatecnic;


    public IDCard(){};
    public IDCard(String email, String name, String fatherName, char gender, String country, String cnic, String dob, String doi, String doe,String image) {
        super(email, name, image);
        type="I";
        this.cnic = cnic;
        templatecnic = new templateCNIC();
    }
    // Getter methods for new fields
    public void setCnic(String cnic) {this.cnic = cnic;}
    public String getName() {
        return name;
    }

    public String getFatherName() {
        return FatherName;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return DOB;
    }

    public String getCountry() {
        return country;
    }

    public String getDoi() {
        return DOI;
    }

    public String getDoe() {
        return DOE;
    }

    public String getCnic() {
        return cnic;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

}
class Equivalence extends Document {
    public String studentName;
    public String fatherName;
    public String dob;
    public String gradeLevel;
    public String year;
    public String group;
    public String equivalenceType;
    public String subjectCount;
    public List<String> grades;
    public String percentage;
    public String type; // For equivalence document type


    public Equivalence() {this.grades=new ArrayList<>();}

    // Constructor with parameters
    public Equivalence(String email, String studentName, String fatherName, String dob,
                       String gradeLevel, String year, String group, String equivalenceType,
                       String subjectCount, List<String> series, String image) {
        // Initialize the parent class properties
        super(email, studentName, image);

        // Initialize the fields specific to the Equivalence class
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.dob = dob;
        this.gradeLevel = gradeLevel;
        this.year = year;
        this.group = group;
        this.equivalenceType = equivalenceType;
        this.subjectCount = subjectCount;
        this.grades=new ArrayList<>();
        this.grades = series;
        this.type = "E";  // Equivalence document type
    }

    public void setSeries(List<String> series) {
        this.grades = series;
    }

    // Getter methods
    public String getStudentName() {
        return studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getDob() {
        return dob;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public String getYear() {
        return year;
    }
    public String getPercentage() {
        return percentage;
    }

    public String getGroup() {
        return group;
    }
    public String getSubjectCount() {
        return subjectCount;
    }
    public String getEquivalenceType() {
        return equivalenceType;
    }



    public List<String> getSeries() {
        return grades;
    }

    public String getType() {
        return type;
    }
}

// Application Class

// Payment Abstract Class
 class Payment {
    public String email;
    public Shipment shipment;
    public double price;
    public String type;

    public Payment(String email,double price, String Servicetype,String address,String status) {
        this.shipment = new Shipment();
        this.price = price;
        this.type = Servicetype;
        this.email=email;
        this.shipment=new Shipment(address,status);
    }
}

// Shipment Class
class Shipment {
    public String address;
    public String status;
public Shipment() {}
    public Shipment( String address,String Status) {
       this.status=Status;
        this.address = address;
    }
}




// Database Manager Singleton Class

class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    // Database connection setup
    private DatabaseManager() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=SDA;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Singleton instance
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    public int addShipment(String Phone, String serviceType, String address, String status) {
        String query = "INSERT INTO Shipment (PhoneNo, service_type, address, status, created_at) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, Phone);                   // Set the price
            stmt.setString(2, serviceType);             // Set the service type (e.g., Standard, Express)
            stmt.setString(3, address);                 // Set the address for shipment
            stmt.setString(4, status);                  // Set the status (e.g., Pending, Shipped)
            stmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis())); // Set the current time as created_at

            stmt.executeUpdate(); // Execute the update

            // Retrieve the generated shipment_id
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated shipment ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if the insertion failed
    }

    public boolean updateShipmentIfEmailNull(String email, String documentType, double price) {
        // SQL query to check if any email is NULL in the Shipment table
        String checkQuery = "SELECT shipment_id FROM Shipment WHERE email IS NULL";

        // SQL query to update email, document_type, and price
        String updateQuery = "UPDATE Shipment SET email = ?, document_type = ?, price = ? WHERE email IS NULL";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            // Execute the query to check if there are any rows with NULL email
            ResultSet rs = checkStmt.executeQuery();

            // If there are any rows with NULL email, perform the update
            if (rs.next()) {
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    // Set parameters for the update query
                    updateStmt.setString(1, email);           // Set the new email
                    updateStmt.setString(2, documentType);    // Set the document type
                    updateStmt.setDouble(3, price);           // Set the new price

                    int rowsUpdated = updateStmt.executeUpdate();

                    // Return true if the update was successful
                    return rowsUpdated > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if no rows with NULL email were found or if the update failed
    }

    public List<String> getShipmentDetailsByEmailAndDocType(String email, String documentType) {
        // Prepare the SQL query to match email and document_type, and return the required columns
        String query = "SELECT service_type, address, status FROM Shipment WHERE email = ? AND document_type = ?";
        List<String> shipmentDetails = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters for email and document_type
            stmt.setString(1, email);
            stmt.setString(2, documentType);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // If a matching row is found, fetch the details
            if (rs.next()) {
                String serviceType = rs.getString("service_type");
                String address = rs.getString("address");
                String status = rs.getString("status");

                // Add the values to the list
                shipmentDetails.add(serviceType);
                shipmentDetails.add(address);
                shipmentDetails.add(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list (could be empty if no matching record is found)
        return shipmentDetails;
    }

    // Generalized method to add a user
    public int addUser(String type, String name, String phone, String email, String password, String address, String age) {
        String query = "INSERT INTO users (type, name, phone, email, password, address, age) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, type); // "C" for customer, "E" for employee, "A" for admin
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, address);
            stmt.setString(7, age);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the insertion failed
    }
    public boolean updateUser(String email, String name, String phone, String address, String age) {
        String query = "UPDATE users SET name = ?, phone = ?, address = ?, age = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the values for the update query
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setString(4, age);
            stmt.setString(5, email);

            // Execute the update query
            int rowsAffected = stmt.executeUpdate();

            // Check if any row was updated
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserFromDatabase(String email) {
        String query = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public int addApplication(String customerEmail, String status, String documentType) {
        String query = "INSERT INTO Application (customer_email, status, document_type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customerEmail); // Customer's email (foreign key)
            stmt.setString(2, status);       // Application status ("unverified" or "verified")
            stmt.setString(3, documentType); // Document type ("BForm", "Equivalence", etc.)
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated application_id
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the insertion failed
    }
    public int addIDCard(String cnic, String name, String fatherName, String gender, String dob, String country, String email, String image) {
        String query = "INSERT INTO IDCard (cnic, name, FatherName, gender, DOB, country, email, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cnic);         // CNIC
            stmt.setString(2, name);         // Name of the person
            stmt.setString(3, fatherName);   // Father's name
            stmt.setString(4, gender);       // Gender ('M', 'F', etc.)
            stmt.setString(5,dob); // Date of Birth (assuming `dob` is in `yyyy-MM-dd` format)
            stmt.setString(6, country);      // Country of issuance
            stmt.setString(7, email);        // Associated user email
            stmt.setString(8, image);        // Image URL or file path
                   // Image URL or file path

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the generated primary key (if auto-increment or similar exists)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the insertion failed
    }

    //  ADD PSSPORT
    public boolean addPassport(String name, String dob, String gender, String country,
                               String dateOfIssue, String dateOfExpiry, String type,
                               String series, String email, String imageUrl) {
        String query = "INSERT INTO Passport (name, dob, gender, country, date_of_issue, " +
                "date_of_expiry, type, series, email, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);             // Name of the passport holder
            stmt.setString(2, dob);              // Date of birth (in yyyy-MM-dd format)
            stmt.setString(3, gender);           // Gender (Male/Female/etc.)
            stmt.setString(4, country);          // Country of citizenship
            stmt.setString(5, dateOfIssue);      // Date of issue (in yyyy-MM-dd format)
            stmt.setString(6, dateOfExpiry);     // Date of expiry (in yyyy-MM-dd format)
            stmt.setString(7, type);             // Passport type (e.g., Regular, Diplomatic)
            stmt.setString(8, series);           // Passport series
            stmt.setString(9, email);            // Associated email
            stmt.setString(10, imageUrl);        // URL or file path of the passport image

            int rowsInserted = stmt.executeUpdate(); // Execute the insert query

            return rowsInserted > 0; // Return true if insertion succeeded
        } catch (SQLException e) {
            e.printStackTrace(); // Handle and log SQL exceptions
        }
        return false; // Return false if insertion fails
    }
    //   ADD Domicile
    public boolean addDomicile(String fullname, String fathername, String mothername, String dob,
                               String address, String province, String gender, String email,
                               String imageUrl) {
        // SQL query to insert a new record into the Domicile table
        String query = "INSERT INTO Domicile (fullname, fathername, mothername, dob, address, province, " +
                "gender, email, imageurl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters in the PreparedStatement
            stmt.setString(1, fullname);          // Full name of the person
            stmt.setString(2, fathername);        // Father's name
            stmt.setString(3, mothername);        // Mother's name
            stmt.setString(4, dob);               // Date of birth (in yyyy-MM-dd format)
            stmt.setString(5, address);           // Address
            stmt.setString(6, province);          // Province
            stmt.setString(7, gender);            // Gender (Male/Female/etc.)
            stmt.setString(8, email);             // Email associated with the domicile
            stmt.setString(9, imageUrl);          // URL or file path of the domicile image

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();

            // Return true if insertion was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
        }
        return false;  // Return false if insertion fails
    }

    //  Adding BForm
    public boolean addBForm(String fullname, String fathername, String mothername, String dob,
                            String placeOfBirth, String nationality, String religion,
                            String gender, String email, String imageUrl) {
        // SQL query to insert a new record into the BForm table
        String query = "INSERT INTO BForm (fullname, fathername, mothername, dob, placeofbirth, " +
                "nationality, religion, gender, email, imageurl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters in the PreparedStatement
            stmt.setString(1, fullname);          // Full name of the person
            stmt.setString(2, fathername);        // Father's name
            stmt.setString(3, mothername);        // Mother's name
            stmt.setString(4, dob);               // Date of birth (in yyyy-MM-dd format)
            stmt.setString(5, placeOfBirth);      // Place of birth
            stmt.setString(6, nationality);       // Nationality
            stmt.setString(7, religion);          // Religion
            stmt.setString(8, gender);            // Gender (Male/Female/etc.)
            stmt.setString(9, email);             // Email associated with the BForm
            stmt.setString(10, imageUrl);         // URL or file path of the BForm image

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();

            // Return true if insertion was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
        }
        return false;  // Return false if insertion fails
    }
    public boolean addEquivalence(String studentName, String fatherName, String dob, String gradeLevel,
                                  String year, String studyGroup, String equivalenceType, String subjectCount,
                                  String type, String email, String imageUrl, List<String> grades) {
        String equivalenceQuery = "INSERT INTO Equivalence (student_name, father_name, dob, grade_level, year, " +
                "study_group, equivalence_type, subject_count, type, email, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String gradesQuery = "INSERT INTO Grades (equivalence_email,Equivalence_type, grade) VALUES (?, ?,?)";

        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Insert into Equivalence table
            try (PreparedStatement stmt = connection.prepareStatement(equivalenceQuery)) {
                stmt.setString(1, studentName);  // Student's name
                stmt.setString(2, fatherName);   // Father's name
                stmt.setString(3, dob);          // Date of birth
                stmt.setString(4, gradeLevel);   // Grade level
                stmt.setString(5, year);         // Year of study
                stmt.setString(6, studyGroup);   // Study group
                stmt.setString(7, equivalenceType);  // Equivalence type
                stmt.setString(8, subjectCount);     // Subject count
                stmt.setString(9, type);             // Type of equivalence document
                stmt.setString(10, email);           // Associated email
                stmt.setString(11, imageUrl);        // Image URL for equivalence certificate

                int rowsInserted = stmt.executeUpdate();  // Execute the insert query

                if (rowsInserted == 0) {
                    connection.rollback();  // Rollback if insertion failed
                    return false;
                }
            }
            // Insert grades into Grades table
            try (PreparedStatement stmt = connection.prepareStatement(gradesQuery)) {
                for (String grade : grades) {
                    stmt.setString(1, email);  // Foreign key: equivalence email
                    stmt.setString(2, gradeLevel);
                    stmt.setString(3, grade);  // Grade (e.g., 'A', 'B', 'C', etc.)
                    stmt.addBatch();  // Add to batch
                }
                int[] rowsInserted = stmt.executeBatch();  // Execute the batch insert

                // If none of the grades were inserted, rollback the transaction
                boolean allInserted = Arrays.stream(rowsInserted).allMatch(row -> row > 0);
                if (!allInserted) {
                    connection.rollback();  // Rollback if any grade insertion fails
                    return false;
                }
            }

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();  // Handle and log SQL exceptions
            try {
                connection.rollback();  // Rollback if any error occurs
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public boolean updateIDCardImage(String email, String imagePath) {
        String query = "UPDATE IDCard SET cnicImage = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, imagePath); // The new image path
            stmt.setString(2, email);     // Email to match

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No matching email found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in the database.");
        }

        return false; // Return false if update fails
    }
    public boolean updateBformImage(String email, String imagePath) {
        String query = "UPDATE BForm SET imageurl = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, imagePath); // The new image path
            stmt.setString(2, email);     // Email to match

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No matching email found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in the database.");
        }

        return false; // Return false if update fails
    }
    public boolean updateDomicileImage(String email, String imagePath) {
        String query = "UPDATE Domicile SET imageurl = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, imagePath); // The new image path
            stmt.setString(2, email);     // Email to match

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No matching email found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in the database.");
        }

        return false; // Return false if update fails
    }
    public boolean updatePassportImage(String email, String imagePath) {
        String query = "UPDATE Passport SET image_url = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, imagePath); // The new image path
            stmt.setString(2, email);     // Email to match

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No matching email found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in the database.");
        }

        return false; // Return false if update fails
    }
    public boolean updateEquivalenceImage(String email, String imagePath) {
        String query = "UPDATE Equivalence SET image_url = ? WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, imagePath); // The new image path
            stmt.setString(2, email);     // Email to match

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("No matching email found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in the Equivalence table.");
        }

        return false; // Return false if update fails
    }

    public boolean updateEquivalenceStatusAndDetails(String email, String newStatus, String equivalenceType,
                                                     String series, String year, String gradeLevel) {
        // Step 1: Update the Application status based on the email and document type (Equivalence)
        String updateStatusQuery = "UPDATE Application SET status = ? WHERE customer_email = ? AND document_type = 'Equivalence'";

        try (PreparedStatement stmt = connection.prepareStatement(updateStatusQuery)) {
            stmt.setString(1, newStatus);  // New status for the application
            stmt.setString(2, email);      // Email to identify the application

            int rowsUpdated = stmt.executeUpdate();

            // If no rows are updated, the email/document type combination might not exist
            if (rowsUpdated == 0) {
                System.out.println("No rows updated in Application table. Either no matching email/document type.");
                return false;
            }

            // Step 2: Update the Equivalence table with new details
            String updateEquivalenceQuery = "UPDATE Equivalence SET series = ?, year = ?, grade_level = ? WHERE email = ?";

            try (PreparedStatement stmtEquivalence = connection.prepareStatement(updateEquivalenceQuery)) {
                stmtEquivalence.setString(1, series);         // Equivalence series
                stmtEquivalence.setString(2, year);           // Year of equivalence
                stmtEquivalence.setString(3, gradeLevel);     // Grade level
                stmtEquivalence.setString(4, email);          // Email to identify the corresponding Equivalence

                int rowsUpdatedEquivalence = stmtEquivalence.executeUpdate();

                // If equivalence details are updated successfully
                if (rowsUpdatedEquivalence > 0) {
                    System.out.println("Successfully updated Equivalence details.");
                    return true;
                } else {
                    System.out.println("Failed to update Equivalence details.");
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Log any SQL errors
                return false;         // Return false if equivalence update fails
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL errors
            return false;         // Return false if status update fails
        }

        return false;  // Return false if any part of the update process fails
    }

    // updating applications and document of cnic
    public boolean updateApplicationStatusAndIDCard(String email, String newStatus, String documentType, String cnic, String doi, String doe) {
        // First, update the Application status based on the email and document type
        String updateStatusQuery = "UPDATE Application SET status = ? WHERE customer_email = ? AND document_type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateStatusQuery)) {
            stmt.setString(1, newStatus);       // New status for the application
            stmt.setString(2, email);           // Email to identify the application
            stmt.setString(3, documentType);    // Document type to match

            int rowsUpdated = stmt.executeUpdate();

            // If no rows were updated, return false indicating failure
            if (rowsUpdated == 0) {
                return false;
            }

            // Now update the IDCard with the provided details (CNIC, DOI, DOE) based on the email
            String updateIDCardQuery = "UPDATE IDCard SET cnic = ?, DOI = ?, DOE = ? WHERE email = ?";
            try (PreparedStatement stmtIDCard = connection.prepareStatement(updateIDCardQuery)) {
                stmtIDCard.setString(1, cnic);    // CNIC
                stmtIDCard.setString(2, doi);     // Date of Issue
                stmtIDCard.setString(3, doe);     // Date of Expiry
                stmtIDCard.setString(4, email);   // Email to identify the corresponding IDCard

                int rowsUpdatedIDCard = stmtIDCard.executeUpdate();
                // If IDCard is successfully updated, return true
                return rowsUpdatedIDCard > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if any part of the update process fails
    }

    public boolean updateApplicationStatusAndPassport(String email, String newStatus, String documentType, String series, String doi, String doe) {
        System.out.println("DOCUMENT TYPE: " + documentType);

        // First, update the Application status based on the email and document type
        String updateStatusQuery = "UPDATE Application SET status = ? WHERE customer_email = ? AND document_type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateStatusQuery)) {
            stmt.setString(1, newStatus);  // New status for the application
            stmt.setString(2, email);      // Email to identify the application
            stmt.setString(3, documentType); // Document type for identification

            int rowsUpdated = stmt.executeUpdate();

            // If no rows were updated, it means the application does not exist or document type mismatch
            if (rowsUpdated == 0) {
                System.out.println("No rows updated in Application table. Either no matching email/document type.");
                return false;
            }

            // Now update the Passport table if the document type is "P" (Passport)
            if ("P".equals(documentType)) {
                String updatePassportQuery = "UPDATE Passport SET series = ?, date_of_issue = ?, date_of_expiry = ? WHERE email = ?";

                try (PreparedStatement stmtPassport = connection.prepareStatement(updatePassportQuery)) {
                    stmtPassport.setString(1, series);   // Passport series
                    stmtPassport.setString(2, doi);      // Date of Issue
                    stmtPassport.setString(3, doe);      // Date of Expiry
                    stmtPassport.setString(4, email);    // Email to identify the corresponding Passport

                    int rowsUpdatedPassport = stmtPassport.executeUpdate();

                    // If Passport is successfully updated, return true
                    if (rowsUpdatedPassport > 0) {
                        System.out.println("Successfully updated Passport details.");
                        return true;
                    } else {
                        System.out.println("Failed to update Passport details.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Update process failed.");
        return false;  // Return false if any part of the update process fails
    }
    public boolean updateApplicationStatusOFDandB(String email, String newStatus, String documentType) {
        System.out.println("DOCUMENT TYPE: " + documentType);

        // First, update the Application status based on the email and document type
        String updateStatusQuery = "UPDATE Application SET status = ? WHERE customer_email = ? AND document_type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateStatusQuery)) {
            stmt.setString(1, newStatus);  // New status for the application
            stmt.setString(2, email);      // Email to identify the application
            stmt.setString(3, documentType); // Document type for identification

            int rowsUpdated = stmt.executeUpdate();

            // If no rows were updated, it means the application does not exist or document type mismatch
            if (rowsUpdated == 0) {
                System.out.println("No rows updated in Application table. Either no matching email/document type.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Update process failed.");
        return false;  // Return false if any part of the update process fails
    }
    public boolean updateApplicationStatusOFEquivalence(String email, String newStatus, String documentType) {
        System.out.println("DOCUMENT TYPE: " + documentType);

        // First, update the Application status based on the email and document type
        String updateStatusQuery = "UPDATE Application SET status = ? WHERE customer_email = ? AND document_type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateStatusQuery)) {
            stmt.setString(1, newStatus);  // New status for the application
            stmt.setString(2, email);      // Email to identify the application
            stmt.setString(3, documentType); // Document type for identification

            int rowsUpdated = stmt.executeUpdate();

            // If no rows were updated, it means the application does not exist or document type mismatch
            if (rowsUpdated == 0) {
                System.out.println("No rows updated in Application table. Either no matching email/document type.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Update process failed.");
        return false;  // Return false if any part of the update process fails
    }

    public boolean updateEquivalencePercentage(String email, String gradeLevel, String newPercentage) {
        // SQL query to update percentages where email and grade_level match
        String updateQuery = "UPDATE Equivalence SET percentages = ? WHERE email = ? AND grade_level = ?";

        // Input validation
        if (email == null || email.isEmpty() || gradeLevel == null || gradeLevel.isEmpty() || newPercentage == null || newPercentage.isEmpty()) {
            System.out.println("Invalid input: email, grade level, and percentage cannot be empty or null.");
            return false;
        }

        try {
            // Log inputs for debugging
            System.out.println("Updating Equivalence table with:");
            System.out.println("Email: " + email);
            System.out.println("Grade Level: " + gradeLevel);
            System.out.println("New Percentage: " + newPercentage);

            // Check if the connection is valid
            if (connection == null || connection.isClosed()) {
                System.out.println("Database connection is not open.");
                return false;
            }

            // Prepare the SQL statement
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setString(1, newPercentage); // Set the new percentage
                stmt.setString(2, email);        // Match email
                stmt.setString(3, gradeLevel);   // Match grade level

                // Execute the update query
                int rowsUpdated = stmt.executeUpdate();
                System.out.println("Rows updated: " + rowsUpdated);

                if (rowsUpdated > 0) {
                    System.out.println("Equivalence table updated successfully.");
                    return true;
                } else {
                    System.out.println("No rows matched the provided email and grade level.");
                    return false;
                }
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.out.println("Error executing update query: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private void saveImagePathToDatabase(String email, String imagePath) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabaseName", "username", "password");
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE idCard SET cnicImage = ? WHERE email = ?")) {
            pstmt.setString(1, imagePath);
            pstmt.setString(2, email);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Image path updated successfully for user: " + email);
            } else {
                System.out.println("No matching user found. Could not update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating image path in database.");
        }
    }
    public List<String> getImagePathsByEmail(String email) {
        List<String> imagePaths = new ArrayList<>();

        // Queries to retrieve image paths from different tables
        String[] queries = {
                "SELECT cnicImage AS imagePath FROM IDCard WHERE email = ?",
                "SELECT image_url AS imagePath FROM Passport WHERE email = ?",
                "SELECT imageurl AS imagePath FROM Domicile WHERE email = ?",
                "SELECT imageurl AS imagePath FROM BForm WHERE email = ?",
                "SELECT image_url AS imagePath FROM Equivalence WHERE email = ?"
        };

        // Iterate over the queries and collect results
        for (String query : queries) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email); // Set the email parameter
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String imagePath = rs.getString("imagePath");
                        if (imagePath != null) {
                            imagePaths.add(imagePath); // Add non-null image path
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception appropriately in production
            }
        }

        return imagePaths; // Return the consolidated list of image paths
    }


    // Separate methods for specific user types (optional)
    public int addCustomer(String name, String phone, String email, String password, String address, String age) {
        return addUser("C", name, phone, email, password, address, age);
    }

    public int addEmployee(String name, String phone, String email, String password, String address, String age) {
        return addUser("E", name, phone, email, password, address, age);
    }

    public int addAdmin(String name, String phone, String email, String password, String address, String age) {
        return addUser("A", name, phone, email, password, address, age);
    }

    // Validate credentials
    public String validateCredentials(String username, String password) {
        String query = "SELECT type FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type"); // "A" (Admin), "C" (Customer), or "E" (Employee)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no match is found
    }

    // Method to load all users from the database
    public List<User> loadUsersFromDatabase() {
        IDMatrix idm=IDMatrix.getInstance();

        String query = "SELECT type, name, phone, email, password, address, age FROM users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String age = rs.getString("age");

                // Create the appropriate type of user based on the "type" field
                User user;
                switch (type) {
                    case "C": // Customer
                        System.out.println("ccccccccc");
                        user = new Customer(name, phone, email, password, address, age);

                        break;
                    case "E": // Employee
                        System.out.println("eeeeeeeeee");
                        user = new Employee(name, phone, email, password, address, age);

                        break;
                    case "A": // Admin
                        user = new Admin(name, phone, email, password, address, age);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown user type: " + type);
                }

                idm.users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idm.users;
    }
    // Method to load all applications from the database
    public void loadApplicationsFromDatabase() {
        IDMatrix idm = IDMatrix.getInstance();

        String query = "SELECT application_id, customer_email, status, document_type, submission_date FROM Application";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // First, load all the applications
            while (rs.next()) {
                int applicationId = rs.getInt("application_id");
                String customerEmail = rs.getString("customer_email");
                String status = rs.getString("status");
                String documentType = rs.getString("document_type");
                Timestamp submissionDate = rs.getTimestamp("submission_date");
                    System.out.println("applicationId: " + applicationId);
                //if(status.equals("unverified"))
                {
                    // Create an Application object
                    application applications = new application(documentType, customerEmail, status);
                    System.out.println("applications.toString()");
                    //applications.email=customerEmail;
                    // Find the matching customer by email
                    for (int i = 0; i < idm.users.size(); i++) {
                        if (idm.users.get(i).email.equals(customerEmail)) {
                            System.out.println("user email: "+idm.users.get(i).email);
                            if (idm.users.get(i) instanceof Customer) { // Check if the user is an instance of Customer

                                // Cast user to Customer
                                // Add the application to the customer's application list

                                Customer c = (Customer) idm.users.get(i);

                                c.applications.add(applications);

                               // System.out.println(c.applications.get(i).Status);
                               // System.out.println("Customer email: "+idm.users.get(i).email);
                               // System.out.println(idm.users.get(i).email + "  userrrrrrrrrrr ");
                                // Now, load the corresponding document based on the document type and customer email
                                if (documentType.equals("I")) { // Assuming "I" stands for CNIC
                                    System.out.println(documentType + "   loading doccccccc");
                                    loadCNICForCustomer(customerEmail, applications);
                                }
                                else if (documentType.equals("B")) { // Assuming "I" stands for CNIC
                                    System.out.println(documentType + "   loading doccccccc");
                                    loadBFormForCustomer(customerEmail, applications);
                                }
                                else if(documentType.equals("P")) {
                                    loadPassportForCustomer(customerEmail, applications);
                                    System.out.println(documentType + "   loading doccccccc");
                                }
                                else if(documentType.equals("D")) {
                                    loadDomicileForCustomer(customerEmail, applications);
                                    System.out.println(documentType + "   loading doccccccc");
                                }
                                else if(documentType.equals("E")) {
                                    loadEquivalenceForCustomer(customerEmail, applications);
                                    System.out.println(documentType + "   loading doccccccc");
                                }

                               // break; // Stop searching once the match is found
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load CNIC
    private void loadCNICForCustomer(String customerEmail, application applications) {
        IDMatrix idm = IDMatrix.getInstance(); // Singleton instance
        String query = "SELECT cnic, name, FatherName, gender, DOB, DOI, DOE, country, image " +
                "FROM IDCard WHERE email = ?"; // Query to fetch customer document

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerEmail); // Bind the email to the query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve data from ResultSet
                    String cnic = rs.getString("cnic");
                    String name = rs.getString("name");
                    String fatherName = rs.getString("FatherName");
                    String gender = rs.getString("gender");
                    String dob = rs.getString("DOB");
                    String doi = rs.getString("DOI");
                    String doe = rs.getString("DOE");
                    String country = rs.getString("country");
                    String image = rs.getString("image");

                    // Locate the customer in the users list
                    boolean customerFound = false;
                    for (int i = 0; i < idm.users.size(); i++) {
                        if (idm.users.get(i).email.equals(customerEmail)) {
                            customerFound = true;

                            // Log before filling
                            System.out.println("Before filling IDCard: " + applications.document);

                            // Call the fill_IDcard method to populate application data
                            applications.fill_IDcard(customerEmail, name, fatherName, gender, country, dob,cnic,doi,doe);

                            // Log after filling
                            System.out.println("After filling IDCard: " + applications.document);

                            System.out.println("CNIC: " + ((IDCard) applications.document).cnic);
                            System.out.println("Customer Email: " + customerEmail);
                            break;
                        }
                    }

                    // If the customer was not found in the users list
                    if (!customerFound) {
                        System.out.println("Customer with email " + customerEmail + " not found in IDMatrix users list.");
                    }
                } else {
                    System.out.println("No document found in the database for email: " + customerEmail);
                }
            }
        } catch (SQLException e) {
            // Log the error and stack trace for debugging
            System.err.println("Error loading document for customer with email: " + customerEmail);
            e.printStackTrace();
        } catch (ClassCastException e) {
            // Handle casting issues if document type is mismatched
            System.err.println("Document type mismatch while loading data for email: " + customerEmail);
            e.printStackTrace();
        }
    }

//LOad Passport
private void loadPassportForCustomer(String customerEmail, application applications) {
    IDMatrix idm = IDMatrix.getInstance();
    System.out.println("Starting to load Passport for email: " + customerEmail);
    String query = "SELECT name, dob AS DOB, gender, country, date_of_issue AS DOI, date_of_expiry AS DOE, type, series, image_url AS image FROM Passport WHERE email = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, customerEmail);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String name = rs.getString("name");
                String dob = rs.getString("DOB");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String doi = rs.getString("DOI");
                String doe = rs.getString("DOE");
                String type = rs.getString("type");
                String series = rs.getString("series");
                String image = rs.getString("image");

                boolean customerFound = false;
                for (int i = 0; i < idm.users.size(); i++) {
                    if (idm.users.get(i).email.equals(customerEmail)) {
                        customerFound = true;

                        System.out.println("Before filling Passport: " + applications.document);
                        applications.fill_Passport(customerEmail, name, gender, country, dob, doi, doe, series, type);
                        System.out.println("After filling Passport: " + applications.document);

                        break;
                    }
                }

                if (!customerFound) {
                    System.out.println("Customer with email " + customerEmail + " not found in IDMatrix users list.");
                }
            } else {
                System.out.println("No Passport found in the database for email: " + customerEmail);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error loading Passport for email: " + customerEmail);
        e.printStackTrace();
    }
}

//Load Domicile
private void loadDomicileForCustomer(String customerEmail, application applications) {
    IDMatrix idm = IDMatrix.getInstance();
    System.out.println("Starting to load Domicile for email: " + customerEmail);
    String query = "SELECT fullname AS name, fathername AS FatherName, mothername, gender, dob AS DOB, address AS country, province, imageurl AS image FROM Domicile WHERE email = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, customerEmail);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String name = rs.getString("name");
                String fatherName = rs.getString("FatherName");
                String motherName = rs.getString("mothername");
                String gender = rs.getString("gender");
                String dob = rs.getString("DOB");
                String country = rs.getString("country");
                String province = rs.getString("province");
                String image = rs.getString("image");

                boolean customerFound = false;
                for (int i = 0; i < idm.users.size(); i++) {
                    if (idm.users.get(i).email.equals(customerEmail)) {
                        customerFound = true;
                        System.out.println("Before filling Domicile: " + applications.document);
                        applications.fill_Domicile(customerEmail, name, fatherName, motherName, dob, country,province, gender);
                        System.out.println("After filling Domicile: " + applications.document);

                        break;
                    }
                }

                if (!customerFound) {
                    System.out.println("Customer with email " + customerEmail + " not found in IDMatrix users list.");
                }
            } else {
                System.out.println("No Domicile found in the database for email: " + customerEmail);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error loading Domicile for email: " + customerEmail);
        e.printStackTrace();
    }
}
// LOAD Equivalence
public void loadEquivalenceForCustomer(String customerEmail, application applications) {
    IDMatrix idm = IDMatrix.getInstance(); // Singleton instance

    // Query to fetch equivalence data
    String equivalenceQuery = "SELECT student_name, father_name, dob, grade_level, year, study_group, equivalence_type, subject_count,percentages " +
            "FROM Equivalence WHERE email = ?";

    // Query to fetch grades using grade_level
    String gradesQuery = "SELECT grade FROM Grades WHERE equivalence_email = ? AND Equivalence_type = ?";

    try (PreparedStatement stmtEquivalence = connection.prepareStatement(equivalenceQuery);
         PreparedStatement stmtGrades = connection.prepareStatement(gradesQuery)) {

        // Set parameters for equivalence query
        stmtEquivalence.setString(1, customerEmail);

        // Execute equivalence query
        try (ResultSet rsEquivalence = stmtEquivalence.executeQuery()) {
            if (rsEquivalence.next()) {
                // Retrieve equivalence data
                String studentName = rsEquivalence.getString("student_name");
                String fatherName = rsEquivalence.getString("father_name");
                String dob = rsEquivalence.getString("dob");
                String gradeLevel = rsEquivalence.getString("grade_level");
                String year = rsEquivalence.getString("year");
                String studyGroup = rsEquivalence.getString("study_group");
                String equivalenceType = rsEquivalence.getString("equivalence_type");
                String subjectCount = rsEquivalence.getString("subject_count");
                String percentage = rsEquivalence.getString("percentages");

                // Set parameters for grades query
                stmtGrades.setString(1, customerEmail);
                stmtGrades.setString(2, gradeLevel); // Use grade_level instead of passing equivalenceType

                // Fetch grades
                List<String> grades = new ArrayList<>();
                try (ResultSet rsGrades = stmtGrades.executeQuery()) {
                    while (rsGrades.next()) {
                        grades.add(rsGrades.getString("grade"));
                    }
                }

                // Log before filling
                System.out.println("Before filling Equivalence: " + applications.document);

                // Call the fill_Equivalence method to populate application data
                applications.fill_Equivalence(customerEmail, studentName, fatherName, dob, gradeLevel, year, studyGroup, equivalenceType, subjectCount, grades,percentage);

                // Log after filling
                System.out.println("After filling Equivalence: " + applications.document);
                System.out.println("Grades: " + grades);
            } else {
                System.out.println("No equivalence record found for email: " + customerEmail);
            }
        }
    } catch (SQLException e) {
        // Log SQL exceptions
        System.err.println("Error loading equivalence for customer with email: " + customerEmail);
        e.printStackTrace();
    } catch (ClassCastException e) {
        // Handle casting issues if document type is mismatched
        System.err.println("Document type mismatch while loading equivalence data for email: " + customerEmail);
        e.printStackTrace();
    }
}

    //Load BForm
    private void loadBFormForCustomer(String customerEmail, application applications) {
        IDMatrix idm = IDMatrix.getInstance(); // Singleton instance
        System.out.println("Starting to load BForm for email: " + customerEmail);
        String query = "SELECT fullname AS name, fathername AS FatherName, mothername, dob AS DOB, placeofbirth, nationality, religion, gender, imageurl AS image " +
                "FROM BForm WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerEmail); // Bind the email to the query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve data from ResultSet
                    String name = rs.getString("name");
                    String fatherName = rs.getString("FatherName");
                    String motherName = rs.getString("mothername");
                    String dob = rs.getString("DOB");
                    String placeOfBirth = rs.getString("placeofbirth");
                    String nationality = rs.getString("nationality");
                    String religion = rs.getString("religion");
                    String gender = rs.getString("gender");
                    String image = rs.getString("image");

                    // Locate the customer in the users list
                    boolean customerFound = false;
                    for (int i = 0; i < idm.users.size(); i++) {
                        if (idm.users.get(i).email.equals(customerEmail)) {
                            customerFound = true;

                            // Log before filling
                            System.out.println("Before filling BForm: " + applications.document);

                            // Call the fill_BForm method to populate application data
                            applications.fill_BForm(customerEmail, name, fatherName, motherName, dob, placeOfBirth, nationality, religion, gender);

                            // Log after filling
                            System.out.println("After filling BForm: " + applications.document);
                            break;
                        }
                    }

                    // If the customer was not found in the users list
                    if (!customerFound) {
                        System.out.println("Customer with email " + customerEmail + " not found in IDMatrix users list.");
                    }
                } else {
                    System.out.println("No BForm found in the database for email: " + customerEmail);
                }
            }
        } catch (SQLException e) {
            // Log the error and stack trace for debugging
            System.err.println("Error loading BForm for email: " + customerEmail);
            e.printStackTrace();
        }
    }


    // Close connection when application ends
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
