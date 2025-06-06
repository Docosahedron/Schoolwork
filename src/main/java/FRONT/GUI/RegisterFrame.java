package FRONT.GUI;
import BACK.Entity.*;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {
    UserSerImpl us = new UserSerImpl();
    JLabel name = new JLabel("用户名:");
    JLabel password = new JLabel("密码:");
    JTextField nameIn = new JTextField();
    JPasswordField passwordIn = new JPasswordField();
    JButton confirm = new JButton("确认");
    JPanel p =new JPanel();
    public RegisterFrame() {
        super("用户注册");
        init();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 300, 250, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirm) {
            User u = new User(1,nameIn.getText(),passwordIn.getText());
            if(us.register(u)) dispose();
        }
    }

    public void init() {
        p.setLayout(null);
        name.setBounds(10, 10, 50, 30);
        nameIn.setBounds(100, 10, 100, 30);
        password.setBounds(10, 50, 50, 30);
        passwordIn.setBounds(100, 50, 100, 30);
        confirm.setBounds(10, 130, 100, 30);

        p.add(name);
        p.add(password);
        p.add(nameIn);
        p.add(passwordIn);
        p.add(confirm);
        confirm.addActionListener(this);
    }
    public static void main(String[] args) {
        new RegisterFrame();
    }
}
