package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Login extends Application {
    private static Stage Loginstage;
    @Override
    public void start(Stage stage) throws Exception {
        Loginstage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 420);
        scene.getStylesheets().add(Login.class.getResource("/org/example/demo/Login.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    public static void close() {
        if (Loginstage != null) {
            Loginstage.close();
        }
    }
    public static void open(){
        try {
            new Login().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changeView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(Login.class.getResource(fxml));
            Loginstage.setScene(new Scene(root));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
