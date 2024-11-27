module id_matrix.id_matrix {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    //requires javafx.base;

    requires javafx.swing;
    requires java.sql;
    requires jakarta.mail;

    opens id_matrix.id_matrix to javafx.fxml;
    exports id_matrix.id_matrix;
}

