package FRONT.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterView extends Application{
    private static Stage Registerstage;
    public static void main(String[] args) {
        launch(args);
    }
    public static void showAlert(String title, String message) {
        // JavaFX 警告框实现逻辑，例如：
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Registerstage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("views/Register.fxml"));
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
            root = FXMLLoader.load(RegisterView.class.getResource(fxml));
            Registerstage.setScene(new Scene(root));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
