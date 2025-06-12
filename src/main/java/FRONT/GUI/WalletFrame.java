package FRONT.GUI;

import BACK.Entity.User;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;


public class WalletFrame extends JFrame {
    private final User curUser;
    UserSerImpl us = new UserSerImpl();
    JLabel balanceNum;

    private JPanel titleBar;
    private JLabel titleLabel;
    private JButton closeButton;
    private Point initialClick;
    private JPanel contentPanel;

    public WalletFrame(User user) {
        this.curUser = user;
        initFrame(user.getName());    // 创建界面
        initButten();   // 创建按钮
        initText();     // 创建文本
        initImage();    // 创建图片
        initBalance();  // 添加余额模块
    }

    private void initFrame(String title) {
        // 移除默认的窗口装饰
        this.setUndecorated(true);

        // 设置支持中文的字体
        Font chineseFont = new Font("Microsoft YaHei", Font.PLAIN, 12);
        UIManager.put("Label.font", chineseFont);
        UIManager.put("Button.font", chineseFont);
        UIManager.put("TextField.font", chineseFont);

        // 创建内容面板
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(190, 190, 190)); // 设置主面板背景色
        setContentPane(contentPane);

        // 创建自定义标题栏
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(23, 29, 37)); // 设置标题栏背景色
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // 标题标签
        titleLabel = new JLabel(title + "的钱包", JLabel.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // 关闭按钮
        closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(23, 29, 37));
        // 移除按钮边框
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(true);
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // 添加悬停效果
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(new Color(255, 59, 59));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(23, 29, 37));
            }
        });

        // 只关闭窗口而不退出程序
        closeButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
            window.dispose();
        });

        // 添加组件到标题栏
        titleBar.add(titleLabel, BorderLayout.WEST);
        titleBar.add(closeButton, BorderLayout.EAST);

        // 添加标题栏到窗口
        contentPane.add(titleBar, BorderLayout.NORTH);

        // 创建主内容面板，使用null布局
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(33, 60, 85)); // 设置内容区域背景色
        contentPanel.setLayout(null);

        // 添加主内容面板到窗口
        contentPane.add(contentPanel, BorderLayout.CENTER);

        // 添加标题栏拖动功能
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 获取当前位置
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // 计算移动距离
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // 移动窗口
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });

        // 设置窗口属性
        setBackground(new Color(27, 40, 56)); // 设置窗口背景色
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    JPanel balancePanel = new JPanel();
    private void initBalance() {
        // 添加次级标题
        JLabel balanceTitle = new JLabel("您的账户");
        Font font = new Font("宋体", Font.BOLD, 20);
        balanceTitle.setFont(font);
        balanceTitle.setForeground(new Color(240, 240, 240));   //设置字体颜色为蓝色
        balanceTitle.setBounds(560, 160, 240, 25);
        contentPanel.add(balanceTitle); // 添加到contentPanel而不是直接添加到JFrame

        // 创建余额显示容器
        balancePanel.setLayout(null);
        balancePanel.setBackground(new Color(22, 32, 45, 255));    // 将背景颜色设置为亮灰
        balancePanel.setBounds(560, 185, 220, 125);
        contentPanel.add(balancePanel); // 添加到contentPanel而不是直接添加到JFrame

        // 创建余额提示
        JLabel balanceTitle2 = new JLabel("当前钱包余额");
        Font font2 = new Font("黑体", Font.BOLD, 15);
        balanceTitle2.setFont(font2);
        balanceTitle2.setForeground(new Color(53, 156, 236));   //设置字体颜色为蓝色
        balanceTitle2.setBounds(10, 10, 100, 25);
        balancePanel.add(balanceTitle2);

        balanceNum = new JLabel("¥ " + curUser.getBalance());
        Font font3 = new Font("宋体", Font.BOLD, 24);
        balanceNum.setFont(font3);
        balanceNum.setForeground(new Color(240, 240, 240));   //设置字体颜色为蓝色
        balanceNum.setBounds(10, 40, 200, 30);
        balancePanel.add(balanceNum);
        // 刷新余额显示
        balancePanel.repaint();
    }

    private void initImage() {
        String imageUrl = "src/main/resources/images/wallet/img.png";
        ImageIcon imageIcon = new ImageIcon(imageUrl);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(500, 70, Image.SCALE_SMOOTH)); // 缩放图片

        // 创建图片标签并添加到contentPanel
        JLabel imageLabel1 = new JLabel(imageIcon);
        imageLabel1.setBounds(50, 160, 500, 70);
        contentPanel.add(imageLabel1);

        JLabel imageLabel2 = new JLabel(imageIcon);
        imageLabel2.setBounds(50, 260, 500, 70);
        contentPanel.add(imageLabel2);

        JLabel imageLabel3 = new JLabel(imageIcon);
        imageLabel3.setBounds(50, 360, 500, 70);
        contentPanel.add(imageLabel3);

        JLabel imageLabel4 = new JLabel(imageIcon);
        imageLabel4.setBounds(50, 460, 500, 70);
        contentPanel.add(imageLabel4);
        // 刷新界面
        contentPanel.repaint();
    }

    private void initText() {
        JLabel mainTitle = new JLabel("为您的钱包充值");
        Font fontTitle = new Font("黑体", Font.PLAIN, 50);
        mainTitle.setFont(fontTitle);
        mainTitle.setForeground(new Color(240, 240, 240));   //设置字体颜色为白色
        mainTitle.setBounds(50, 30, 600, 50);
        contentPanel.add(mainTitle); // 添加到contentPanel而不是直接添加到JFrame

        // 充值金额提示
        JLabel C50 = new JLabel("充值 ￥ 50.00");
        JLabel C100 = new JLabel("充值 ￥ 100.00");
        JLabel C200 = new JLabel("充值 ￥ 200.00");
        JLabel C500 = new JLabel("充值 ￥ 500.00");
        JLabel tip = new JLabel("最低资金级别");

        // 设置字体颜色
        C50.setForeground(new Color(240, 240, 240));
        C100.setForeground(new Color(240, 240, 240));
        C200.setForeground(new Color(240, 240, 240));
        C500.setForeground(new Color(240, 240, 240));
        tip.setForeground(new Color(240, 240, 240));
        // 设置字体大小
        Font font = new Font("宋体", Font.BOLD, 20);
        C50.setFont(font);
        C100.setFont(font);
        C200.setFont(font);
        C500.setFont(font);
        tip.setFont(new Font("宋体", Font.PLAIN, 12));
        // 设置位置
        C50.setBounds(65, 180, 200, 30);
        C100.setBounds(65, 280, 200, 30);
        C200.setBounds(65, 380, 200, 30);
        C500.setBounds(65, 480, 200, 30);
        tip.setBounds(65, 202, 200, 30);

        // 添加到contentPanel
        contentPanel.add(C50);
        contentPanel.add(C100);
        contentPanel.add(C200);
        contentPanel.add(C500);
        contentPanel.add(tip);


        // 创建功能提示文本
        JTextArea priceArea = new JTextArea(
                "您的Steam的钱包资金可用于购买Steam上的任何一款游戏，也可在支持Steam交易的游戏内用来\n" +
                        "进行购买。\n" +
                        "\n" +
                        "下订单之前，您有机会查看您的订单。\n"
        );
        priceArea.setEditable(false);
        priceArea.setFont(new Font("宋体", Font.PLAIN, 14));
        priceArea.setForeground(Color.WHITE); // 设置文字颜色为白色
        priceArea.setBackground(new Color(33, 60, 84)); // 设置背景色
        priceArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        // 设置文本位置
        priceArea.setBounds(50, 90, 500, 60);

        // 添加到contentPanel
        contentPanel.add(priceArea);
    }

    private void initButten() {
        ImageIcon buttonIcon = new ImageIcon("src/main/resources/images/wallet/充值.png");
        ImageIcon buttonIcon_push = new ImageIcon("src/main/resources/images/wallet/充值_push.png");
        // 充值50元按钮
        JButton c50 = new JButton(buttonIcon);
        c50.setBounds(420, 180, 120, 30);
        contentPanel.add(c50); // 添加到contentPanel

        // 充值100元按钮
        JButton c100 = new JButton(buttonIcon);
        c100.setBounds(420, 280, 120, 30);
        contentPanel.add(c100);

        // 充值200元按钮
        JButton c200 = new JButton(buttonIcon);
        c200.setBounds(420, 380, 120, 30);
        contentPanel.add(c200);

        // 充值500元按钮
        JButton c500 = new JButton(buttonIcon);
        c500.setBounds(420, 480, 120, 30);
        contentPanel.add(c500);

        // 自定义充值按钮
        JButton diy = new JButton("自定义充值");
        diy.setBounds(660, 500, 100, 25);
        diy.setFont(new Font("宋体", Font.BOLD, 12));
        diy.setForeground(new Color(240, 240, 240)); // 设置字体颜色为白色
        diy.setBackground(new Color(61, 67, 79)); // 设置按钮背景色
        diy.setFocusPainted(false); // 去掉焦点边框
        diy.setBorder(BorderFactory.createLineBorder(new Color(61, 66, 78, 115))); // 设置边框颜色为白色
        diy.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 设置鼠标悬停时的手型
        contentPanel.add(diy);


        // 删除按钮的边框
        c50.setBorderPainted(false);
        c50.setContentAreaFilled(false);
        c50.setFocusPainted(false);
        c100.setBorderPainted(false);
        c100.setContentAreaFilled(false);
        c100.setFocusPainted(false);
        c200.setBorderPainted(false);
        c200.setContentAreaFilled(false);
        c200.setFocusPainted(false);
        c500.setBorderPainted(false);
        c500.setContentAreaFilled(false);
        c500.setFocusPainted(false);

        // 设置按钮悬停效果
        c50.setRolloverIcon(buttonIcon_push);
        c100.setRolloverIcon(buttonIcon_push);
        c200.setRolloverIcon(buttonIcon_push);
        c500.setRolloverIcon(buttonIcon_push);

        c50.addActionListener(e -> {
            if (us.recharge(curUser, 50)) dispose();
        });
        c100.addActionListener(e -> {
            if (us.recharge(curUser, 100)) dispose();
        });
        c200.addActionListener(e -> {
            if (us.recharge(curUser, 200)) dispose();
        });
        c500.addActionListener(e -> {
            if (us.recharge(curUser, 500)) dispose();
        });
        diy.addActionListener(e -> {
            new PayFrame();
        });
    }

    public static void main(String[] args) {
        User user = new User(4, "a", "a");
        new WalletFrame(user);
    }
}
