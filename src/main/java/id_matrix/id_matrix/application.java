package id_matrix.id_matrix;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class application {
    public String email;
    public String Status;
    public Document document;
    public String type;
    private BooleanProperty payButtonVisible = new SimpleBooleanProperty(false);
    // Initialize the list with some paths (or you can load them dynamically)

    public BooleanProperty payButtonVisibleProperty() {
        return payButtonVisible;
    }

    public void setPayButtonVisible(boolean visible) {
        this.payButtonVisible.set(visible);
    }

    public boolean isPayButtonVisible() {
        return payButtonVisible.get();
    }

    public application(String type,String email,String Status) {
        this.email=email;
        this.Status=Status;
        if(type.equals("B"))
        {
            document=new BForm();
            this.type="B";
        }
        else if(type.equals("I"))
        {
            document=new IDCard();
            this.type="I";
        }
        else if(type.equals("D"))
        {
            document=new Domicile();
            this.type="D";
        }
        else if(type.equals("P"))
        {
            document=new Passport();
            this.type="P";
        }
        else if (type.equals("E"))
        {
            document=new Equivalence();
            this.type="E";
        }
    }
    public void fill_IDcard(String email, String name, String fatherName, String gender, String country, String dob,String cnic,String doi,String doe) {
        if (this.document == null || !(this.document instanceof IDCard)) {
            this.document = new IDCard(); // Ensure the document is an IDCard instance
        }
        IDCard idCard = (IDCard) this.document;
        idCard.email = email;
        idCard.name = name;
        idCard.FatherName = fatherName;
        idCard.gender = gender;
        idCard.country = country;
        idCard.DOB = dob;
        idCard.cnic = cnic;
        idCard.DOI = doi;
        idCard.DOE = doe;
        idCard.type = "I";
       // idCard.templatecnic = temp;//saves template
        System.out.println("IDCard filled:");
        System.out.println("Email: " + idCard.email);
        System.out.println("Name: " + idCard.name);
        System.out.println("FatherName: " + idCard.FatherName);
        System.out.println("Gender: " + idCard.gender);
        System.out.println("Country: " + idCard.country);
        System.out.println("DOB: " + idCard.DOB);
        System.out.println("CNIC: " + idCard.cnic);
        System.out.println("DOI: " + idCard.DOI);
        System.out.println("DOE: " + idCard.DOE);
    }
    public void fill_Passport(String email, String name, String gender, String country,
                             String dob, String dateOfIssue, String dateOfExpiry, String series, String typeP) {
        if (this.document == null || !(this.document instanceof Passport)) {
            this.document = new Passport(); // Ensure the document is a Passport instance
        }
        Passport passport = (Passport) this.document;

        // Fill in the Passport fields
        passport.email = email;
        passport.name = name;
        passport.gender = gender;
        passport.country = country;
        passport.dob = dob;
        passport.DateOfIssue = dateOfIssue;
        passport.DateOfExpiry = dateOfExpiry;
        passport.TypeP = typeP;  // Set the type to the passed value
        passport.series = series;
        passport.type = "P";
        // Optional: Print out the details for verification
        System.out.println("Passport filled:");
        System.out.println("Email: " + passport.email);
        System.out.println("Name: " + passport.name);
        System.out.println("Gender: " + passport.gender);
        System.out.println("Country: " + passport.country);
        System.out.println("DOB: " + passport.dob);
        System.out.println("Date of Issue: " + passport.DateOfIssue);
        System.out.println("Date of Expiry: " + passport.DateOfExpiry);
        System.out.println("Series: " + passport.series);
        System.out.println("Type: " + passport.TypeP);
    }
    public void fill_Domicile(String email,String fullname, String fathername, String mothername,
                              String dob, String address, String province, String gender) {

        if (this.document == null || !(this.document instanceof Domicile)) {
            this.document = new Domicile(); // Ensure the document is a Passport instance
        }
        Domicile domicile = (Domicile) this.document;

        // Fill in the Domicile fields
        domicile.email=email;
        domicile.fullname = fullname;
        domicile.fathername = fathername;
        domicile.mothername = mothername;
        domicile.dob = dob;
        domicile.address = address;
        domicile.province = province;
        domicile.gender = gender;
        domicile.type = "D";
        // Print out the details for verification
        System.out.println("Domicile filled:");
        System.out.println("Full Name: " + domicile.fullname);
        System.out.println("Father's Name: " + domicile.fathername);
        System.out.println("Mother's Name: " + domicile.mothername);
        System.out.println("Date of Birth: " + domicile.dob);
        System.out.println("Address: " + domicile.address);
        System.out.println("Province: " + domicile.province);
        System.out.println("Gender: " + domicile.gender);
    }
    public void fill_BForm(String email, String fullname,
    String fathername,
    String mothername,
    String dob,
    String placeofbirth,
    String nationality,
    String religion,
    String gender) {

        // Ensure the document is a BForm instance
        if (this.document == null || !(this.document instanceof BForm)) {
            this.document = new BForm(); // Initialize a new BForm instance
        }

        BForm bForm = (BForm) this.document;  // Cast the document to BForm

        // Fill in the BForm fields based on the provided values
        bForm.email = email;
        bForm.fullname = fullname;
        bForm.fathername = fathername;
        bForm.mothername = mothername;
        bForm.dob = dob;
        bForm.placeofbirth = placeofbirth;
        bForm.nationality = nationality;
        bForm.religion = religion;
        bForm.gender = gender;

        bForm.type = "B";  // Set the type as "B" for BForm

        // Print out the details for verification
        System.out.println("BForm filled:");
        System.out.println("Full Name: " + bForm.fullname);
        System.out.println("Father's Name: " + bForm.fathername);
        System.out.println("Mother's Name: " + bForm.mothername);
        System.out.println("Date of Birth: " + bForm.dob);
        System.out.println("Place of Birth: " + bForm.placeofbirth);
        System.out.println("Nationality: " + bForm.nationality);
        System.out.println("Religion: " + bForm.religion);
        System.out.println("Gender: " + bForm.gender);
    }
    public void fill_Equivalence(String email, String studentName, String fatherName, String dob,
                                 String gradeLevel, String year, String group, String equivalenceType,
                                 String subjectCount, List<String> series,String percentage) {
        if (this.document == null || !(this.document instanceof Equivalence)) {
            this.document = new Equivalence(); // Ensure the document is an instance of Equivalence
        }
        Equivalence equivalence = (Equivalence) this.document;

        // Fill in the Equivalence fields
        equivalence.email = email;
        equivalence.studentName = studentName;
        equivalence.fatherName = fatherName;
        equivalence.dob = dob;
        equivalence.gradeLevel = gradeLevel;
        equivalence.year = year;
        equivalence.group = group;
        equivalence.equivalenceType = equivalenceType;
        equivalence.subjectCount = subjectCount;
        equivalence.grades = series;
        equivalence.type = "E"; // Set the type to "E" for equivalence
        equivalence.percentage = percentage;

        // Optional: Print out the details for verification
        System.out.println("Equivalence filled:");
        System.out.println("Email: " + equivalence.email);
        System.out.println("Student Name: " + equivalence.studentName);
        System.out.println("Father's Name: " + equivalence.fatherName);
        System.out.println("DOB: " + equivalence.dob);
        System.out.println("Grade Level: " + equivalence.gradeLevel);
        System.out.println("Year: " + equivalence.year);
        System.out.println("Group: " + equivalence.group);
        System.out.println("Equivalence Type: " + equivalence.equivalenceType);
        System.out.println("Subject Count: " + equivalence.subjectCount);
        System.out.println("Series: " + equivalence.grades);
        System.out.println("Type: " + equivalence.type);
    }


    public void setStatus(String s) {
        this.Status=s;
    }
    public String getStatus() {
        return Status;
    }
    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        if(this.type.equals("I"))
            return ((IDCard)this.document).name;

        else if(this.type.equals("B"))
            return ((BForm)this.document).getFullname();

        else if(this.type.equals("D"))
            return ((Domicile)this.document).getFullname();
        else if(this.type.equals("P"))
            return ((Passport)this.document).getName();
        else return null;
    }

    public String getFatherName() {
        if(this.type.equals("I"))
            return ((IDCard)this.document).getFatherName();

        else if(this.type.equals("B"))
            return ((BForm)this.document).getFathername();

        else if(this.type.equals("D"))
            return ((Domicile)this.document).getFathername();

        else return null;
    }

    public String getGender() {
        if(this.type.equals("I"))
            return ((IDCard)this.document).getGender();

        else if(this.type.equals("B"))
            return ((BForm)this.document).getGender();

        else if(this.type.equals("D"))
            return ((Domicile)this.document).getGender();
        else if(this.type.equals("P"))
            return ((Passport)this.document).getGender();

        else return null;
    }

    public String getDob() {

        if(this.type.equals("I"))
            return ((IDCard)this.document).getDob();

        else if(this.type.equals("B"))
            return ((BForm)this.document).getDob();

        else if(this.type.equals("D"))
            return ((Domicile)this.document).getDob();
        else if(this.type.equals("P"))
            return ((Passport)this.document).getDob();

        else return null;
    }

    public String getCountry() {

        if(this.type.equals("I"))
            return ((IDCard)this.document).getCountry();

        else if(this.type.equals("P"))
            return ((Passport)this.document).getCountry();

        else return null;
    }

    public String getDoi() {

        if(this.type.equals("I"))
            return ((IDCard)this.document).getDoi();

        else if(this.type.equals("P"))
            return ((Passport)this.document).getDateOfIssue();

        else return null;
    }

    public String getDoe() {

        if(this.type.equals("I"))
            return ((IDCard)this.document).getDoe();

        else if(this.type.equals("P"))
            return ((Passport)this.document).getDateOfExpiry();

        else return null;
    }

    public String getCnic() {
        if(this.type.equals("I"))
            return ((IDCard)this.document).getCnic();
        else return null;
    }
    public String getTypePassport() {
        if (this.type.equals("P")) {
            return ((Passport) this.document).getTypeP(); // This accesses the passport's TypeP property
        }
        return null;
    }

    public String getSeries() {
        if (this.type.equals("P")) {
            return ((Passport) this.document).getSeries(); // This accesses the passport's series property
        }
        return null;
    }

    public String getMotherName() {
        if (this.type.equals("D")) {
            return ((Domicile) this.document).getMothername(); // This accesses the domicile's mothername property
        }
        return null;
    }

    public String getAddress() {
        if (this.type.equals("D")) {
            return ((Domicile) this.document).getAddress(); // This accesses the domicile's address property
        }
        return null;
    }

    public String getProvince() {
        if (this.type.equals("D")) {
            return ((Domicile) this.document).getProvince(); // This accesses the domicile's province property
        }
        return null;
    }

    public String getBirthplace() {
        if (this.type.equals("B")) {
            return ((BForm) this.document).getPlaceofbirth(); // This accesses the BForm's placeofbirth property
        }
        return null;
    }

    public String getReligion() {
        if (this.type.equals("B")) {
            return ((BForm) this.document).getReligion(); // This accesses the BForm's religion property
        }
        return null;
    }
    public String getGradeLevel() {
        if (this.type.equals("E")) {
            return ((Equivalence) this.document).getGradeLevel(); // This accesses the BForm's religion property
        }
        return null;
    }

    public String getYear() {
        if (this.type.equals("E")) {
            return ((Equivalence) this.document).getYear();
        }
        return null;
    }
    public String getPercentage() {
        if (this.type.equals("E")) {
            return ((Equivalence) this.document).getPercentage();
        }
        return null;
    }

    public String getGroup() {
        if (this.type.equals("E")) {
        return ((Equivalence)this.document).getGroup();
        }
        return null;
    }
    public String getSubjectCount() {
        if (this.type.equals("E")) {
            return ((Equivalence) this.document).getSubjectCount();
        }
        return null;
    }


}
