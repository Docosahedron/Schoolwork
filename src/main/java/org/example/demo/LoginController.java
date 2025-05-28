package org.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class  LoginController {

    // 注入 FXML 组件
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private Button loginButton;

    // 初始化方法（可选）
    @FXML
    public void initialize() {
        // 可以在这里进行初始化操作
    }

    // 登录按钮点击事件
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 简单示例：用户名和密码都不为空即视为登录成功
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("提示", "请输入用户名和密码！");
        } else if ("ad".equals(username) && "ad".equals(password)) {
            showAlert("登录成功","管理员账号");
        } else {
            showAlert("登录失败", "用户名或密码错误！");
        }
    }

    // 弹出提示框方法
    private void showAlert(String title, String message) {
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
}

