module front.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;// Java 标准 SQL 模块
    requires mysql.connector.java;

    opens front.Views to javafx.fxml;
    exports front.Views;
    exports front.Controller;
    opens front.Controller to javafx.fxml;
}

