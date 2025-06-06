package FRONT.View;

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
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Loginstage = stage;

        // 加载FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("/views/Login.fxml"));
        Parent root = fxmlLoader.load();

        // 创建一个透明背景的场景
        Scene scene = new Scene(root, 700, 420);
        scene.getStylesheets().add(LoginView.class.getResource("/views/Login.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT); // 设置透明背景

        // 设置窗口为无边框模式
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        // 监听鼠标按下事件，以便拖动窗口
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // 监听鼠标拖动事件
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
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
