package org.example.demo;

import work_demo.ENTITY.*;
import work_demo.GUI.AdminFrame;
import work_demo.GUI.UserFrame;
import work_demo.SERVICE.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class registerController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField_ag;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    public void handleRegister(ActionEvent actionEvent) {

    }

    public void handleBack(ActionEvent actionEvent) {
        register.changeView("login.fxml");
    }
}
