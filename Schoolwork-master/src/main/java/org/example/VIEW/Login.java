package org.example.VIEW;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 420);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    public static void close() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
}
