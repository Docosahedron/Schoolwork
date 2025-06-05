package FRONT.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserView extends Application{
    private static Stage userStage;
    @Override
    public void start(Stage stage) throws Exception {
        userStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(UserView.class.getResource("views/user.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 990, 500);
        stage.setScene(scene);
        stage.show();
    }
}
