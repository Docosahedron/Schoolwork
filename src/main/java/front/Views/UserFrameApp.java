package front.Views;

import back.ENTITY.User;
import front.Controller.UserFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserFrameApp extends Application {
    private final User user = new User(0, "test", "123");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        Scene scene = new Scene(loader.load());
        UserFrameController controller = loader.getController();
        controller.setCurUser(user);
        primaryStage.setScene(scene);
        primaryStage.setTitle(user.getName() + "的用户界面");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
