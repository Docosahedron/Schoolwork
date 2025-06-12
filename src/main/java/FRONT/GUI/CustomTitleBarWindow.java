package FRONT.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomTitleBarWindow extends JFrame {
    private JPanel titleBar;
    private JLabel titleLabel;
    private JButton closeButton;
    private Point initialClick;

    public CustomTitleBarWindow(String title) {
        // 移除默认窗口装饰
        setUndecorated(true);

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
        titleBar.setBackground(new Color(33, 60, 85)); // 设置标题栏背景色
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // 标题标签
        titleLabel = new JLabel(title, JLabel.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // 关闭按钮
        closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(33, 59, 84));
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
                closeButton.setBackground(new Color(34, 59, 83));
            }
        });

        closeButton.addActionListener(e -> System.exit(0));

        // 添加组件到标题栏
        titleBar.add(titleLabel, BorderLayout.WEST);
        titleBar.add(closeButton, BorderLayout.EAST);

        // 添加标题栏到窗口
        contentPane.add(titleBar, BorderLayout.NORTH);

        // 添加内容区域213C55FF
        JPanel contentPanel = new JPanel();
//        ImageIcon icon = new ImageIcon("src/main/resources/images/background2.png");
//        JLabel backgroundLabel = new JLabel(icon);
//        backgroundLabel.setBounds(0, 0, 300, 500);
//        contentPanel.add(backgroundLabel);
        contentPanel.setBackground(new Color(33, 60, 85)); // 设置内容区域背景色为深蓝色
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 使用居中的FlowLayout

        // 创建内容标签，设置文本颜色为白色
        JLabel contentLabel = new JLabel("这是窗口内容区域");
        contentLabel.setForeground(Color.WHITE); // 设置文本颜色为白色
        contentLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14)); // 设置字体

        contentPanel.add(contentLabel);
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
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // 设置系统属性
        System.setProperty("file.encoding", "UTF-8");

        // 使用SwingUtilities确保在EDT上运行
        SwingUtilities.invokeLater(() -> {
            CustomTitleBarWindow window = new CustomTitleBarWindow("自定义标题栏窗口");
            window.setVisible(true);
        });
    }
}
