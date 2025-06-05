package front.Controller;

import front.MainApp;
import front.Views.RegisterView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class registerController {
    private MainApp mainApp;  // 保存主应用引用

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
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

    @FXML
    private void handleBack() throws IOException {
        mainApp.goToLoginStage();
    }
}
