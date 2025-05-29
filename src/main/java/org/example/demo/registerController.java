package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        RegisterView.changeView("login.fxml");
    }
}
