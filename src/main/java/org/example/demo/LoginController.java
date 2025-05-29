package org.example.demo;

import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import work_demo.ENTITY.*;
import work_demo.GUI.*;
import work_demo.DAO.*;
import work_demo.SERVICE.*;

import javax.swing.*;

public class  LoginController {

    UserSer us = new UserSer();
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
        if(u.getName().equals("ad")&&u.getPassword().equals("ad")){
            new AdminFrame();
            Login.close();
        } else if (us.query(u)) {
            new UserFrame(u);
            Login.close();
        }else {
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
    @FXML
    private void handleRegister() {
        Login.changeView("register.fxml");
    }
}

