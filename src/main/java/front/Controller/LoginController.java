package front.Controller;

import back.SERVICE.SerImpl.UserSerImpl;
import front.Views.LoginView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import back.ENTITY.*;

public class  LoginController {
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

    // 登录按钮点击事件
    @FXML
    private void handleLogin() {
        String nameInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        User u = new User(0,nameInput,passwordInput);
        if (usi.login(u)) LoginView.close();
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
    private void handleRegister() {
        showAlert("立入禁止!", "前方的区域以后再来探索吧!");
        //LoginView.changeView("register.fxml");
    }
}

