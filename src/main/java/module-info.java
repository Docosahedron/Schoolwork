module org.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires mysql.connector.j;
    requires javafx.media;

    opens front.Views to javafx.fxml;
    opens back.ENTITY to javafx.base, javafx.fxml;
    exports front.Views;
    exports front.Controller;
    opens front.Controller to javafx.fxml;

    opens front to javafx.fxml, javafx.graphics;
    exports front;
}