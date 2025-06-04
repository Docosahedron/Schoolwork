module org.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires mysql.connector.j;
    requires javafx.media;

    opens front.Views to javafx.fxml;
    exports front.Views;
    exports front.Controller;
    opens front.Controller to javafx.fxml;
}