package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class register extends Application{
    private static Stage Registerstage;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Registerstage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 832, 772);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    public static void close() {
        if (Registerstage != null) {
            Registerstage.close();
        }
    }
    public static void changeView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(register.class.getResource(fxml));
            Registerstage.setScene(new Scene(root));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
