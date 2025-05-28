package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class user extends Application{
    private static Stage userStage;
    @Override
    public void start(Stage stage) throws Exception {
        userStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(user.class.getResource("user.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 420);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    public static void close() {
        if (userStage != null) {
            userStage.close();
        }
    }
    public static void open(){
        try {
            new user().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
