package FRONT.Controller;

import BACK.Service.SerImpl.UserSerImpl;
import FRONT.MainApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import BACK.Entity.*;

import java.io.IOException;

public class  LoginController {
    private MainApp mainApp;  // 保存主应用引用

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() throws IOException {
        String nameInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        User u = new User(0,nameInput,passwordInput);
        if (usi.login(u)) mainApp.goToUserStage();  // 调用主应用中的方法
    }

    UserSerImpl usi = new UserSerImpl();
    // 注入 FXML 组件
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;
    // 初始化方法（可选）
    @FXML
    public void initialize() {
        // 可以在这里进行初始化操作
    }


    // 弹出提示框方法
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
    @FXML
    private void handleRegister() throws IOException {
        mainApp.goToResiterStage();
    }
}

