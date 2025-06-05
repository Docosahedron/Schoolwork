package FRONT.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterView extends Application{
    private static Stage Registerstage;

    public static void main(String[] args) {
        launch(args);
    }

    public static void showAlert(String 注册失败, String 密码不一致) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        Registerstage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginView.class.getResource("views/register.fxml"));
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
