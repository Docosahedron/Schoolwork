package FRONT.GUI;
import BACK.Entity.*;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {
    UserSerImpl us = new UserSerImpl();
    JLabel nameLabel=new JLabel("名字");
    JTextField nameInput=new JTextField(10);

    JLabel passwordLabel =new JLabel("密码");
    JPasswordField passwordInput=new JPasswordField(10);

    JButton login =new JButton("登录");
    JButton register =new JButton("注册");
    JButton exit =new JButton("退出");
    JPanel  p=new JPanel();
    public LoginFrame() {
        super("登录界面");
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==login) {
            User u = new User(0,nameInput.getText(),passwordInput.getText());
            if (us.login(u)){
                new UserFrame(us.getUserInfo(u.getName()));
                dispose();
            }
        }else if (e.getSource()==register) {
            dispose();
            new RegisterFrame();
        }else if (e.getSource()==exit) {
            System.exit(0);
        }
    }
    public void init(){
        p.setLayout(null);
        //账号输入组件设置
        nameLabel.setBounds(140, 50, 40, 30);
        nameInput.setBounds(180, 50, 150, 30);
        //密码输入组件设置
        passwordLabel.setBounds(140, 100, 40, 30);
        passwordInput.setBounds(180, 100, 150, 30);
        //账号操作组件设置
        login.setBounds(100, 150, 80, 30);
        register.setBounds(200, 150, 80, 30);
        exit.setBounds(300, 150, 80, 30);
        login.addActionListener(this);
        register.addActionListener(this);
        exit.addActionListener(this);
        //添加组件
        p.add(nameLabel);
        p.add(nameInput);
        p.add(passwordLabel);
        p.add(passwordInput);
        p.add(login);
        p.add(register);
        p.add(exit);

        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new LoginFrame();
    }
}
