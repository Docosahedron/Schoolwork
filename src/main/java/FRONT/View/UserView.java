package FRONT.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class UserView extends Application{
    private static Stage userStage;
    @Override
    public void start(Stage stage) throws Exception {
        userStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(UserView.class.getResource("/views/User.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 990, 500);
        stage.setScene(scene);
        stage.show();
    }
    public static void showAlert(String title, String message) {
        // JavaFX 警告框实现逻辑，例如：
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
