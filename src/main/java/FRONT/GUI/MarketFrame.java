package FRONT.GUI;

import BACK.Entity.*;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketFrame extends JFrame{
    private final User curUser;
    private Banana banana;
    private final UserSerImpl us = new UserSerImpl();

    private JPanel mainPanel;
    private JPanel bananaInfoPanel;
    private JLabel packageCountLabel;
    private JLabel balanceLabel;
    private JLabel[] bananaCountLabels;

    private JPanel titleBar;
    private JLabel titleLabel;
    private JButton closeButton;
    private Point initialClick;

    private final String[] bananaTypes = {"N", "R", "SR", "SSR", "UR"};
    private final Color[] bananaColors = {
            new Color(150, 150, 150),  // N - 灰色
            new Color(0, 112, 221),    // R - 蓝色
            new Color(163, 53, 238),   // SR - 紫色
            new Color(255, 128, 0),    // SSR - 橙色
            new Color(255, 215, 0)     // UR - 金色
    };

    public MarketFrame(User user) {
        // 移除默认窗口装饰
        setUndecorated(true);

        // 设置支持中文的字体
        Font chineseFont = new Font("Microsoft YaHei", Font.PLAIN, 12);
        UIManager.put("Label.font", chineseFont);
        UIManager.put("Button.font", chineseFont);
        UIManager.put("TextField.font", chineseFont);

        // 创建内容面板
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(255, 255, 255)); // 设置主面板背景色
        setContentPane(contentPane);

        // 创建自定义标题
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(23, 29, 37)); // 设置标题栏背景色
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // 标题标签
        titleLabel = new JLabel(user.getName() + "的市场", JLabel.LEFT);
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
        closeButton.addActionListener(e -> dispose()); // 关闭窗口
        // 添加组件到标题栏
        titleBar.add(titleLabel, BorderLayout.WEST);
        titleBar.add(closeButton, BorderLayout.EAST);
        // 添加标题栏到窗口
        contentPane.add(titleBar, BorderLayout.NORTH);

        this.curUser = user;
        this.banana = us.getBanana(user);

        System.out.println("初始化市场界面 - 用户: " + user.getName() + ", 余额: " + user.getBalance() + ", 香蕉包: " + user.getPackages());

        initUI();
        refreshData(); // 初始化时刷新数据

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

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void initUI() {
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(40, 71, 102)); // 设置主面板背景色

        // 设置主面板布局
        mainPanel.setLayout(new BorderLayout());
        // 顶部信息面板
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 中间功能区域
        JTabbedPane tabbedPane = new JTabbedPane();
        // 设置中间功能区域边框颜色
        tabbedPane.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 200), 0)); // 设置边框颜色和宽度
        // 设置选项卡背景色和字体
        tabbedPane.setBackground(new Color(61, 67, 79)); // 设置选项卡背景色
        tabbedPane.setForeground(Color.WHITE); // 设置选项卡文字颜色
        tabbedPane.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14)); // 设置选项卡字体
        // 自定义 TabbedPane UI，不绘制边框
        class NoBorderTabbedPaneUI extends BasicTabbedPaneUI {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                // 绘制边框
                g.setColor(new Color(61, 67, 79)); // 设置边框颜色
                // 设置边框宽度
                g.fillRect(0, 24, tabbedPane.getWidth(), 3); // 绘制上边框
//                g.drawLine(0, 30, tabbedPane.getWidth(), 30); // 绘制上边框
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            }
        }
        tabbedPane.setUI(new NoBorderTabbedPaneUI());
        tabbedPane.addTab("购买香蕉包", createBuyPackagePanel());
        tabbedPane.addTab("开启香蕉包", createOpenPackagePanel());
        tabbedPane.addTab("出售香蕉", createSellBananaPanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(40, 71, 102)); // 设置背景色


        // 用户信息面板
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("用户信息");
        titledBorder.setTitleColor(new Color(219, 223, 227)); // 设置标题颜色为白色
        userInfoPanel.setBorder(new TitledBorder(titledBorder));
        userInfoPanel.setBackground(new Color(31, 47, 65));

        balanceLabel = new JLabel("余额: ¥" + curUser.getBalance());
        balanceLabel.setForeground(new Color(219, 223, 227)); // 设置文字颜色为白色
        balanceLabel.setFont(new Font("宋体", Font.BOLD, 14));

        packageCountLabel = new JLabel("香蕉包: " + curUser.getPackages() + "个");
        packageCountLabel.setForeground(new Color(219, 223, 227)); // 设置文字颜色为白色
        packageCountLabel.setFont(new Font("宋体", Font.BOLD, 14));

        userInfoPanel.add(balanceLabel);
        userInfoPanel.add(packageCountLabel);

        // 香蕉信息面板
        bananaInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        bananaInfoPanel.setBackground(new Color(31, 47, 65)); // 设置背景色
        TitledBorder bananaBorder = BorderFactory.createTitledBorder("香蕉信息");
        bananaBorder.setTitleColor(new Color(219, 223, 227)); // 设置标题颜色为白色
        bananaInfoPanel.setBorder(new TitledBorder(bananaBorder));

        bananaCountLabels = new JLabel[bananaTypes.length];
        for (int i = 0; i < bananaTypes.length; i++) {
            bananaCountLabels[i] = new JLabel(bananaTypes[i] + ": 0");
            bananaCountLabels[i].setFont(new Font("宋体", Font.BOLD, 14));
            bananaCountLabels[i].setForeground(bananaColors[i]);
            bananaInfoPanel.add(bananaCountLabels[i]);
        }

        // 刷新按钮
        JButton refresh = new JButton("刷新");
        refresh.setBackground(new Color(62, 82, 112)); // 设置按钮背景色
        refresh.setForeground(Color.WHITE); // 设置按钮文字颜色
        // 删除按钮边框
        refresh.setBorderPainted(false);
        refresh.setFocusPainted(false);
        refresh.setOpaque(true);
        refresh.setFont(new Font("宋体", Font.BOLD, 14));
        // 设置按钮悬停效果
        refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refresh.setBackground(new Color(82, 102, 132));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                refresh.setBackground(new Color(62, 82, 112));
            }
        });
        refresh.addActionListener(e -> refreshData());

        JPanel topInfoPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        topInfoPanel.setBackground(new Color(40, 71, 102)); // 设置背景色
        topInfoPanel.add(userInfoPanel);
        topInfoPanel.add(bananaInfoPanel);

        panel.add(topInfoPanel, BorderLayout.CENTER);
        panel.add(refresh, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBuyPackagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(40, 71, 101)); // 设置背景色

        // 标题
        JLabel titleLabel = new JLabel("购买香蕉包");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // 设置标题颜色为白色
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // 描述
        JLabel descLabel = new JLabel("香蕉包价格: 2元/个");
        descLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        descLabel.setForeground(Color.WHITE); // 设置描述颜色为白色
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 数量选择
        JPanel amountPanel = new JPanel();
        amountPanel.setBackground(new Color(40, 71, 101)); // 设置背景色
        JLabel amountLabel = new JLabel("购买数量:");
        String[] amounts = {"1", "5", "10", "20", "50"};
        amountLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        amountLabel.setForeground(Color.WHITE); // 设置标签颜色为白色
        JComboBox<String> amountComboBox = new JComboBox<>(amounts);
        amountComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        amountComboBox.setBackground(new Color(61, 67, 79)); // 设置下拉框背景色
        amountComboBox.setForeground(Color.WHITE); // 设置下拉框文字颜色
        amountPanel.add(amountLabel);
        amountPanel.add(amountComboBox);
        amountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 购买按钮
        ImageIcon buyIcon = new ImageIcon("src/main/resources/images/market/buy.png");
        buyIcon.setImage(buyIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH)); // 调整图标大小
        JButton buyButton = new JButton(buyIcon);
        // 将按钮边框设置为无边框
        buyButton.setBorderPainted(false);
        buyButton.setContentAreaFilled(false);
        buyButton.setFocusPainted(false);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // 设置按钮悬停效果
        buyButton.addMouseListener(new MouseAdapter( ){
            @Override
            public void mouseEntered(MouseEvent e) {
                buyButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 鼠标悬停时变为手型
                // 鼠标悬停时更换图片
                ImageIcon hoverIcon = new ImageIcon("src/main/resources/images/market/buy_push.png");
                hoverIcon.setImage(hoverIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH));
                buyButton.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 鼠标离开时恢复默认形状
                // 鼠标离开时恢复原图片
                ImageIcon normalIcon = new ImageIcon("src/main/resources/images/market/buy.png");
                normalIcon.setImage(normalIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH));
                buyButton.setIcon(normalIcon);
            }
        });
        buyButton.addActionListener(e -> {
            int amount = Integer.parseInt((String) amountComboBox.getSelectedItem());
            if (us.buyPackage(curUser, amount)) {
                // 购买成功后立即刷新数据
                refreshData();
                JOptionPane.showMessageDialog(this, "购买成功！您现在有 " + curUser.getPackages() + " 个香蕉包");
            } else {
                JOptionPane.showMessageDialog(this, "购买失败，请检查余额是否充足", "购买失败", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 添加组件
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(amountPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(buyButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createOpenPackagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(40, 71, 101)); // 设置背景色

        // 标题
        JLabel titleLabel = new JLabel("开启香蕉包");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setForeground(new Color(255, 255, 255)); // 设置标题颜色为白色
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 描述
        JTextArea descArea = new JTextArea(
                "开启香蕉包可获得随机香蕉:\n" +
                        "N级香蕉: 60%概率\n" +
                        "R级香蕉: 25%概率\n" +
                        "SR级香蕉: 10%概率\n" +
                        "SSR级香蕉: 4.5%概率\n" +
                        "UR级香蕉: 0.5%概率"
        );
        descArea.setEditable(false);
        descArea.setForeground(Color.WHITE); // 设置文字颜色为白色
        descArea.setBackground(new Color(39, 59, 81)); // 设置背景色
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 开包按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBackground(new Color(40, 71, 101)); // 设置背景色

        JButton open1Button = new JButton("开启1个");
        JButton open5Button = new JButton("开启5个");
        JButton open10Button = new JButton("开启10个");

        // 设置按钮样式
        // 1.字体
        open1Button.setFont(new Font("黑体", Font.PLAIN, 14));
        open5Button.setFont(new Font("黑体", Font.PLAIN, 14));
        open10Button.setFont(new Font("黑体", Font.PLAIN, 14));
        // 2.背景色和前景色
        open1Button.setBackground(new Color(91, 120, 163));
        open5Button.setBackground(new Color(91, 120, 163));
        open10Button.setBackground(new Color(91, 120, 163));
        open1Button.setForeground(Color.WHITE);
        open5Button.setForeground(Color.WHITE);
        open10Button.setForeground(Color.WHITE);
        // 3.删除边框
        open1Button.setBorderPainted(false);
        open5Button.setBorderPainted(false);
        open10Button.setBorderPainted(false);
        // 4.鼠标悬停效果
        open1Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                open1Button.setBackground(new Color(111, 140, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                open1Button.setBackground(new Color(91, 120, 163));
            }
        });
        open5Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                open5Button.setBackground(new Color(111, 140, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                open5Button.setBackground(new Color(91, 120, 163));
            }
        });
        open10Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                open10Button.setBackground(new Color(111, 140, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                open10Button.setBackground(new Color(91, 120, 163));
            }
        });

        open1Button.addActionListener(e -> openPackage(1));
        open5Button.addActionListener(e -> openPackage(5));
        open10Button.addActionListener(e -> openPackage(10));

        buttonPanel.add(open1Button);
        buttonPanel.add(open5Button);
        buttonPanel.add(open10Button);

        // 添加组件
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(descArea);
        panel.add(Box.createVerticalStrut(30));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createSellBananaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(40, 71, 101)); // 设置背景色

        // 标题
        JLabel titleLabel = new JLabel("出售香蕉");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // 设置标题颜色为白色
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 描述
        JTextArea priceArea = new JTextArea(
                "香蕉价格:\n" +
                        "N级香蕉: 0.1元/个\n" +
                        "R级香蕉: 1元/个\n" +
                        "SR级香蕉: 5元/个\n" +
                        "SSR级香蕉: 20元/个\n" +
                        "UR级香蕉: 100元/个"
        );
        priceArea.setEditable(false);
        priceArea.setForeground(Color.WHITE); // 设置文字颜色为白色
        priceArea.setBackground(new Color(39, 59, 81)); // 设置背景色
        priceArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 选择面板
        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        selectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectionPanel.setMaximumSize(new Dimension(300, 100));
        selectionPanel.setBackground(new Color(40, 71, 101)); // 设置背景色

        JLabel typeLabel = new JLabel("香蕉类型:");
        typeLabel.setForeground(Color.WHITE); // 设置标签颜色为白色
        JComboBox<String> typeComboBox = new JComboBox<>(bananaTypes);
        typeComboBox.setFont(new Font("宋体", Font.PLAIN, 14));
        typeComboBox.setBackground(new Color(61, 67, 79)); // 设置下拉框背景色
        typeComboBox.setForeground(Color.WHITE); // 设置下拉框文字颜色

        JLabel amountLabel = new JLabel("出售数量:");
        amountLabel.setForeground(Color.WHITE); // 设置标签颜色为白色
        
        
        
        // 创建Spinner
        JSpinner numSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        numSpinner.setFont(new Font("宋体", Font.PLAIN, 14));
        numSpinner.setPreferredSize(new Dimension(80, 30));

        // 获取Spinner内部的文本框并设置背景色
        JComponent editor = numSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(new Color(61, 67, 79));
            textField.setForeground(Color.WHITE);
            textField.setHorizontalAlignment(JTextField.CENTER);
        }

        // 自定义箭头按钮颜色的方法
        customizeSpinnerArrows(numSpinner);

        add(numSpinner);
        setLocationRelativeTo(null);
        setVisible(true);
        JLabel totalLabel = new JLabel("预计获得:");
        totalLabel.setForeground(Color.WHITE); // 设置标签颜色为白色
        JLabel totalValueLabel = new JLabel("¥1.0");
        totalValueLabel.setForeground(Color.WHITE); // 设置标签颜色为白色

        selectionPanel.add(typeLabel);
        selectionPanel.add(typeComboBox);
        selectionPanel.add(amountLabel);
        selectionPanel.add(numSpinner);
        selectionPanel.add(totalLabel);
        selectionPanel.add(totalValueLabel);

        // 更新预计获得金额
        typeComboBox.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();assert selectedType != null;
            int num = (int) numSpinner.getValue();
            double price = us.getBananaPrice(selectedType);
            totalValueLabel.setText("¥" + (price * num));
        });

        numSpinner.addChangeListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();assert selectedType != null;
            int num = (int) numSpinner.getValue();
            double price = us.getBananaPrice(selectedType);
            totalValueLabel.setText("¥" + (price * num));
        });

        // 出售按钮
        JButton sellButton = new JButton("出售");
        // 设置按钮样式
        // 1.字体
        sellButton.setFont(new Font("黑体", Font.BOLD, 14));
        // 2.背景色和前景色
        sellButton.setBackground(new Color(91, 120, 163));
        sellButton.setForeground(Color.WHITE);
        // 3.删除边框
        sellButton.setBorderPainted(false);
        sellButton.setFocusPainted(false);
        sellButton.setOpaque(true);
        // 4.鼠标悬停效果
        sellButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sellButton.setBackground(new Color(111, 140, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sellButton.setBackground(new Color(91, 120, 163));
            }
        });
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellButton.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();assert selectedType != null;
            //获取数量
            int num = (int) numSpinner.getValue();
            Banana temp= new Banana(curUser.getName(),selectedType,num);
            if (us.sellBanana(curUser, temp)) {
                refreshData();
            }
        });

        // 添加组件
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(priceArea);
        panel.add(Box.createVerticalStrut(30));
        panel.add(selectionPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sellButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void customizeSpinnerArrows(JSpinner spinner) {
        // 获取Spinner的组件
        Component[] components = spinner.getComponents();
        for (Component comp : components) {
            // 检查是否为箭头按钮
            if (comp instanceof BasicArrowButton) {
                BasicArrowButton arrowButton = (BasicArrowButton) comp;

                // 设置按钮背景色
                arrowButton.setBackground(new Color(80, 80, 80));

                // 对于不同方向的箭头，设置不同的前景色
                if (arrowButton.getDirection() == BasicArrowButton.NORTH) {
                    arrowButton.setForeground(Color.WHITE);
                } else if (arrowButton.getDirection() == BasicArrowButton.SOUTH) {
                    arrowButton.setForeground(Color.WHITE);
                }

                // 重绘按钮
                arrowButton.repaint();
            }
        }
    }

    private void refreshData() {
        try {
            // 更新用户数据 - 使用仅需用户名的方法
            User updatedUser = us.getUserInfo(curUser.getName());
            if (updatedUser != null) {
                curUser.setBalance(updatedUser.getBalance());
                curUser.setPackages(updatedUser.getPackages());

                // 输出调试信息
                System.out.println("刷新数据 - 用户: " + curUser.getName() + ", 余额: " + curUser.getBalance() + ", 香蕉包: " + curUser.getPackages());
            } else {
                System.out.println("警告: 无法获取用户数据! 用户名: " + curUser.getName());
                JOptionPane.showMessageDialog(this, "获取用户数据失败，请重新登录", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 更新界面显示
            balanceLabel.setText("余额: ¥" + curUser.getBalance());
            packageCountLabel.setText("香蕉包: " + curUser.getPackages() + "个");

            // 获取香蕉数据
            banana = us.getBanana(curUser);
            if (banana != null) {
                bananaCountLabels[0].setText("N: " + banana.getN());
                bananaCountLabels[1].setText("R: " + banana.getR());
                bananaCountLabels[2].setText("SR: " + banana.getSR());
                bananaCountLabels[3].setText("SSR: " + banana.getSSR());
                bananaCountLabels[4].setText("UR: " + banana.getUR());

                // 输出调试信息
                System.out.println("香蕉数据 - N: " + banana.getN() + ", R: " + banana.getR() +
                        ", SR: " + banana.getSR() + ", SSR: " + banana.getSSR() + ", UR: " + banana.getUR());
            } else {
                System.out.println("警告: 无法获取香蕉数据! 用户名: " + curUser.getName());
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (Exception e) {
            System.out.println("刷新数据出错: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "刷新数据出错: " + e.getMessage(), "系统错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPackage(int amount) {
        try {
            // 先刷新数据，确保使用最新的用户数据
            refreshData();

            System.out.println("尝试开启 " + amount + " 个香蕉包，当前拥有: " + curUser.getPackages() + " 个");

            if (curUser.getPackages() < amount) {
                JOptionPane.showMessageDialog(this,
                        "您只有 " + curUser.getPackages() + " 个香蕉包，不足以开启 " + amount + " 个！",
                        "开包失败",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (us.openPackage(curUser, amount)) {
                System.out.println("开包成功，刷新数据...");
                refreshData();
            } else {
                System.out.println("开包失败，可能是数据库操作问题");
                JOptionPane.showMessageDialog(this,
                        "开包失败，请稍后再试！",
                        "操作失败",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("开包过程中出错: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "开包过程中出现错误: " + e.getMessage(),
                    "系统错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // 测试用
        User test = new User(1, "测试用户", "password", 1000, 10);
        new MarketFrame(test);
    }
}