package FRONT.GUI;

import BACK.Entity.User;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class WalletFrame extends JFrame {
    private final User curUser;
    UserSerImpl us =new UserSerImpl();
    JLabel balanceNum;
    public WalletFrame(User user) {
        super(user.getName()+"的钱包");
        this.curUser = user;
        initFrame();    // 创建界面
        initButten();   // 创建按钮
        initText();     // 创建文本
        initImage();    // 创建图片
        initBalance();  // 添加余额模块
    }
    private void initFrame() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }
    JPanel balancePanel = new JPanel();
    private void initBalance() {
        // 添加次级标题
        JLabel balanceTitle = new JLabel("您的账户");
        Font font = new Font("宋体", Font.BOLD, 20);
        balanceTitle.setFont(font);
        balanceTitle.setBounds(560, 160, 240, 25);
        add(balanceTitle);

        // 创建余额显示容器
        balancePanel.setLayout(null);
        balancePanel.setBackground(new Color(220, 220, 220, 200));    // 将背景颜色设置为亮灰
        balancePanel.setBounds(560, 185, 220, 125);
        add(balancePanel);

        // 创建余额提示
        JLabel balanceTitle2 = new JLabel("当前钱包余额");
        Font font2 = new Font("宋体", Font.BOLD, 15);
        balanceTitle2.setFont(font2);
        balanceTitle2.setForeground(Color.BLUE);   //设置字体颜色为蓝色
        balanceTitle2.setBounds(10, 10, 100, 25);
        balancePanel.add(balanceTitle2);

        balanceNum = new JLabel("¥ " +curUser.getBalance());
        Font font3 = new Font("宋体", Font.BOLD, 24);
        balanceNum.setFont(font3);
        balanceNum.setBounds(10, 40, 200, 30);
        balancePanel.add(balanceNum);

    }

    private void initImage() {
        URL imageUrl = getClass().getResource("/images/wallet/background.png");

        if (imageUrl == null) {
            System.err.println("错误：图片资源未找到！请检查路径：/front/Images/wallet/生成渐变背景色.png");
            // 可以在这里加载一个默认图片或者处理错误
            return; // 无法加载图片，直接返回
        }
        ImageIcon imageIcon = new ImageIcon(imageUrl);
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
        // 自定义充值按钮
        JButton diy = new JButton("自定义充值");
        diy.setBounds(660, 500,100, 25);
        this.getContentPane().add(diy);
        c50.addActionListener(e->{
            if (us.recharge(curUser,50)) dispose();
        });
        c100.addActionListener(e->{
            if (us.recharge(curUser,100)) dispose();
        });
        c200.addActionListener(e->{
            if (us.recharge(curUser,200)) dispose();
        });
        c500.addActionListener(e->{
            if (us.recharge(curUser,500)) dispose();
        });
        diy.addActionListener(e->{
            new PayFrame();
        });
    }

    public static void main(String[] args) {
        User user = new User(4, "a", "a");
        new WalletFrame(user);
    }
}
