package FRONT;

import FRONT.Controller.LoginController;
import FRONT.Controller.registerController;
import FRONT.Controller.userController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    private Stage loginStage;
    private Stage userStage;
    private Stage registerStage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        loginStage = stage;
        goToLoginStage();  // 显示无边框登录界面
    }

    public void goToLoginStage() throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loginLoader.load();

        LoginController controller = loginLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 700, 420);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/views/Login.css")).toExternalForm());

        loginStage.setResizable(false);
        loginStage.setScene(scene);
        loginStage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            loginStage.setX(event.getScreenX() - xOffset);
            loginStage.setY(event.getScreenY() - yOffset);
        });
    }
    public void exStage() throws IOException {
        if (userStage != null){
            userStage.close();
        }
        if (registerStage != null){
            registerStage.close();
        }
    }
    public void goToUserStage() throws IOException {
        FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/views/user.fxml"));
        Parent root = userLoader.load();

        userController controller = userLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 990, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/views/user.css")).toExternalForm());

        userStage = new Stage();
        userStage.setTitle("用户界面");
        userStage.setScene(scene);
        userStage.show();

        loginStage.close();
    }
    public void goToResiterStage() throws IOException {
        FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
        Parent root = registerLoader.load();

        registerController controller = registerLoader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 600, 505);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/views/register.css")).toExternalForm());

        registerStage = new Stage();
        registerStage.setResizable(false);
        registerStage.setScene(scene);
        registerStage.show();

        loginStage.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
