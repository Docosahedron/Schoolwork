package FRONT.Controller;

import BACK.Entity.User;
import BACK.Service.SerImpl.UserSerImpl;
import FRONT.MainApp;
import FRONT.View.RegisterView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static FRONT.View.RegisterView.showAlert;

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

    UserSerImpl usi = new UserSerImpl();
    public void handleRegister(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String password_ag = passwordField_ag.getText();
        if (password.equals(password_ag)) {
            System.out.println("注册成功");
            User u = new User(1,username,password);
            if (usi.register(u)) {
                RegisterView.close();
                mainApp.goToLoginStage();
                mainApp.exStage();
            }
        }
        else {
            System.out.println("注册失败");
            showAlert("注册失败", "密码不一致");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        mainApp.goToLoginStage();
        mainApp.exStage();
    }
}
