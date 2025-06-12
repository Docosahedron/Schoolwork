package FRONT.GUI;
import BACK.Entity.*;
import BACK.Service.SerImpl.UserSerImpl;
import BACK.Service.Check;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {
    UserSerImpl us = new UserSerImpl();
    Check ch = new Check();
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
        this.setBounds(300, 300, 250, 200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirm) {
            if(ch.checkUserName(nameIn.getText())&&ch.checkPassword(passwordIn.getText())) {
                User u = new User(1,nameIn.getText(),passwordIn.getText());
                if(us.register(u)) dispose();
            }else{
                JOptionPane.showMessageDialog(this, "注册失败!\n用户名只能包含中文,英文或者\"_\",且长度在4-10位\n密码必须同时包含英文和数字,且长度在8-16位");
            }

        }
    }

    public void init() {
        p.setLayout(null);
        name.setBounds(10, 10, 50, 30);
        nameIn.setBounds(70, 10, 130, 30);
        password.setBounds(10, 50, 50, 30);
        passwordIn.setBounds(70, 50, 130, 30);
        confirm.setBounds(10, 100, 100, 30);

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
