package back.GUI;

import javax.swing.*;
import java.awt.*;

public class WalletFrame extends JFrame {
    public WalletFrame() {
        initFrame();    // 创建界面
        initButten();   // 创建按钮
        initText();     // 创建文本
        initImage();    // 创建图片
        initBalance();  // 添加余额模块
    }

    private void initBalance() {
        //添加次级标题
        JLabel balanceTitle = new JLabel("您的账户");
        Font font = new Font("宋体", Font.BOLD, 20);
        balanceTitle.setFont(font);
        balanceTitle.setBounds(560, 160, 240, 25);
        add(balanceTitle);

        //创建余额显示
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(null);
        balancePanel.setBackground(new Color(220, 220, 220, 200));    // 将背景颜色设置为亮灰
        balancePanel.setBounds(560, 185, 220, 125);
        add(balancePanel);

        //创建余额提示
        JLabel balanceTitle2 = new JLabel("当前钱包余额");
        Font font2 = new Font("宋体", Font.BOLD, 15);
        balanceTitle2.setFont(font2);
        balanceTitle2.setForeground(Color.BLUE);   //设置字体颜色为蓝色
        balanceTitle2.setBounds(10, 10, 100, 25);
        balancePanel.add(balanceTitle2);
    }

    private void initImage() {
        ImageIcon imageIcon = new ImageIcon("image/生成渐变背景色.png");
        // 图片1
        JLabel imageLabel1 = new JLabel(imageIcon);
        imageLabel1.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel1);
        imageLabel1.setBounds(50, 160, 500, 70);
        // 图片2
        JLabel imageLabel2 = new JLabel(imageIcon);
        imageLabel2.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel2);
        imageLabel2.setBounds(50, 260, 500, 70);
        // 图片3
        JLabel imageLabel3 = new JLabel(imageIcon);
        imageLabel3.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel3);
        imageLabel3.setBounds(50, 360, 500, 70);
        // 图片4
        JLabel imageLabel4 = new JLabel(imageIcon);
        imageLabel4.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel4);
        imageLabel4.setBounds(50, 460, 500, 70);
//        this.getContentPane().add(imageLabel);
    }

    private void initText() {
        JLabel mainTitle = new JLabel("为您的钱包充值");
        Font fontTitle = new Font("宋体", Font.PLAIN, 50);
        mainTitle.setFont(fontTitle);
        mainTitle.setBounds(50, 30, 600, 50);
        this.getContentPane().add(mainTitle);
    }

    private void initButten() {
        // 充值50元按钮
        JButton c50 = new JButton("充值 ￥ 50");
        c50.setBounds(420,180,120,30);
        this.getContentPane().add(c50);
        // 充值100元按钮
        JButton c100 = new JButton("充值 ￥ 100");
        c100.setBounds(420,280,120,30);
        this.getContentPane().add(c100);
        // 充值200元按钮
        JButton c200 = new JButton("充值 ￥ 200");
        c200.setBounds(420,380,120,30);
        this.getContentPane().add(c200);
        // 充值500元按钮
        JButton c500 = new JButton("充值 ￥ 500");
        c500.setBounds(420,480,120,30);
        this.getContentPane().add(c500);
    }

    private void initFrame() {
        this.setTitle("我的钱包");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(2);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new WalletFrame();
    }
}
