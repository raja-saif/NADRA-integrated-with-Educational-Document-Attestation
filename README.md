# ID Matrix: NADRA-Style Identity & Educational Document Attestation System

ID Matrix is a **JavaFX desktop application**. Citizens use it to apply for identity documents (CNIC, B-Form, Passport, Domicile) and for an educational equivalence certificate. Employees verify the applications and admins manage employee accounts. Data is stored in Microsoft SQL Server, and new users confirm their email with a one-time password (OTP).

## ✨ Features

### 🧑 Customer (Citizen)
- **Sign up with email OTP verification.** A random 6-digit OTP is emailed over Gmail SMTP (Jakarta Mail), and the email address must be unique.
- **Role-based login.** The same login screen sends Admin, Customer and Employee users to their own dashboards.
- **Apply for documents:**
  - **ID Card (CNIC):** name, father's name, gender, country, date of birth
  - **B-Form:** name, father's and mother's names, date of birth, place of birth, nationality, religion, gender
  - **Passport:** personal details and passport type
  - **Domicile:** name, father's and mother's names, date of birth, address, province, gender
  - **Equivalence certificate (A Levels / O Levels):** student details, grade level, year, group, number of subjects, and the grade for each subject
- **My Applications:** a table showing each application and its status.
- **My Documents:** links that open the document images generated for the user.
- **Duplicate Document:** lists the user's verified applications and offers a **Pay** option. The payment form collects a phone number, a delivery address and a service (EasyPaisa, JazzCash or SadaPay), then records a shipment with the status *Pending*.

### 🧑‍💼 Employee
- **Verify Applications:** verify or cancel pending applications.
  - When a **CNIC** is verified, the app generates a CNIC number in `#####-#######-#` format, an issue date and an expiry date 5 years later.
  - When a **Passport** is verified, the app generates a passport number from a series (A, D or S) plus 7 digits, with issue and expiry dates.
  - When an **Equivalence** application is verified, the app turns the subject grades into an equivalent percentage (A\*=100, A=90, B=80, C=70, D=60, E=50, U=0) and averages them.
- **Manage customers:** update or delete customer records.

### 🛡️ Admin
- Display, add, update and delete **employees**. New employees are added through the sign-up form.

### 🖼️ Document rendering
- Each document is drawn onto its official-style template image with a JavaFX `Canvas`. The result is exported as a PNG through `SwingFXUtils` and `ImageIO`, saved under `DB/<user-email>/`, and its path is stored in the database.

## 🧰 Tech stack
- **Language:** Java (Java 22 in `pom.xml`)
- **UI:** JavaFX 22 with FXML views and controllers
- **Build:** Maven (`javafx-maven-plugin`, Maven wrapper included)
- **Database:** Microsoft SQL Server via JDBC (`DatabaseManager` singleton)
- **Email:** Jakarta Mail over Gmail SMTP (port 587, STARTTLS)
- **Design:** object-oriented domain model (`User` → `Customer`/`Employee`/`Admin`, plus `Document` → `IDCard`, `Passport`, `BForm`, `Domicile`, `Educational`/`Equivalence`, along with `Payment` and `Shipment`) and Singleton classes (`IDMatrix`, `DatabaseManager`)

## 📁 Project structure
```
.
├── pom.xml, mvnw, mvnw.cmd
├── src/main/java/
│   ├── module-info.java
│   └── id_matrix/id_matrix/
│       ├── IDMatrix.java            # JavaFX Application entry point (loads Welcome.fxml)
│       ├── IDMatrixManager.java     # Domain classes + DatabaseManager (JDBC)
│       ├── Welcome / Login / Signup / OtpSender
│       ├── Admin_func / EmpFunc / CustFunc / UpdateDeleteCus
│       ├── templateCNIC / templateBform / templatePassport / templateDomicile / templateEquivalence
│       └── PaymentForm / application / Document
├── src/main/resources/              # FXML views and document template images
├── DB/                              # Generated document images
└── *.docx                           # Software requirements & design document
```

## 🚀 Setup & run
**Prerequisites:** JDK 22+, Maven (or the bundled `mvnw`), and a local Microsoft SQL Server instance.

1. **Database:** create a SQL Server database named **`SDA`** on `localhost:1433`. The connection string in `IDMatrixManager.java` uses Windows integrated security (`integratedSecurity=true`). The required tables (users, applications and document tables) must be created first. No schema script is included in the repo.
2. **Email:** in `OtpSender.java`, set the sender address and password to your own Gmail account and a Gmail **App Password**. It is better to load these from environment variables than to hard-code them.
3. **Build config:** check `pom.xml` before the first run:
   - Set the `javafx-maven-plugin` `mainClass` to `id_matrix.id_matrix.IDMatrix`.
   - Add the dependencies that `module-info.java` requires but `pom.xml` does not list: Jakarta Mail, the Microsoft SQL Server JDBC driver, `javafx-web` and `javafx-swing`.
4. **Run:**
   ```bash
   mvn clean javafx:run
   ```

## 🖼️ Screenshots
Generated CNIC (test data):

![Generated CNIC](cnic_output.png)

## 👤 Author
Raja Saif Ali — [GitHub](https://github.com/raja-saif) · [LinkedIn](https://www.linkedin.com/in/raja-saif-01702329a/)
