package front.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginView extends Application {
    private static Stage Loginstage;
    @Override
    public void start(Stage stage) throws Exception {
        Loginstage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 420);
        scene.getStylesheets().add(LoginView.class.getResource("/front/Views/Login.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
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
            new LoginView().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changeView(String fxml){
        Parent root = null;
        try{
            root = FXMLLoader.load(LoginView.class.getResource(fxml));
            Loginstage.setScene(new Scene(root));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
