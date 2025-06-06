module org.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires mysql.connector.j;
    requires javafx.media;

    opens FRONT.View to javafx.fxml;
    opens BACK.Entity to javafx.base, javafx.fxml;
    exports FRONT.View;
    exports FRONT.Controller;
    opens FRONT.Controller to javafx.fxml;

    opens FRONT to javafx.fxml, javafx.graphics;
    exports FRONT;
}