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
    public void handleRegister(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String password_ag = passwordField_ag.getText();

        if (!password.equals(password_ag)) {
            System.out.println("注册失败");
            showAlert("注册失败", "密码不一致");
            return;
        }

        System.out.println("开始注册");

        // 创建 User 实体
        User u = new User(1, username, password);

        // 使用 Task 在线程中执行注册逻辑
        javafx.concurrent.Task<Boolean> task = new javafx.concurrent.Task<>() {
            @Override
            protected Boolean call() {
                return usi.register(u);  // 后台执行
            }
        };

        task.setOnSucceeded(e -> {
            boolean success = task.getValue();
            if (success) {
                System.out.println("注册成功");
                try {
                    RegisterView.close();
                    mainApp.goToLoginStage();
                    mainApp.exStage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("注册失败", "页面跳转失败");
                }
            } else {
                showAlert("注册失败", "用户名已存在或其他错误");
            }
        });

        task.setOnFailed(e -> {
            System.out.println("注册线程失败：" + task.getException());
            showAlert("注册失败", "系统错误，请重试");
        });

        // 启动后台线程
        new Thread(task).start();
    }


    @FXML
    private void handleBack() throws IOException {
        mainApp.goToLoginStage();
        mainApp.exStage();
    }
}
