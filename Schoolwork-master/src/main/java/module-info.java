module org.example.VIEW {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.example.VIEW to javafx.fxml;
    exports org.example.VIEW;
}