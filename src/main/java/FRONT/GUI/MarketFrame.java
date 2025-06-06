package FRONT.GUI;

import BACK.Entity.Banana;
import BACK.Entity.BananaTemp;
import BACK.Entity.User;
import BACK.Dao.DaoImpl.BananaDaoImpl;
import BACK.Dao.DaoImpl.UserDaoImpl;
import BACK.Service.SerImpl.UserSerImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MarketFrame extends JFrame{
    private final User curUser;
    private Banana banana;
    private final UserSerImpl us = new UserSerImpl();
    private final BananaDaoImpl bananaDao = new BananaDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();
    
    private JPanel mainPanel;
    private JPanel bananaInfoPanel;
    private JLabel packageCountLabel;
    private JLabel balanceLabel;
    private JLabel[] bananaCountLabels;
    
    private final String[] bananaTypes = {"N", "R", "SR", "SSR", "UR"};
    private final Color[] bananaColors = {
        new Color(150, 150, 150),  // N - 灰色
        new Color(0, 112, 221),    // R - 蓝色
        new Color(163, 53, 238),   // SR - 紫色
        new Color(255, 128, 0),    // SSR - 橙色
        new Color(255, 215, 0)     // UR - 金色
    };
    
    public MarketFrame(User user) {
        super(user.getName() + "的市场");
        this.curUser = user;
        this.banana = us.getBanana(user);
        
        System.out.println("初始化市场界面 - 用户: " + user.getName() + ", 余额: " + user.getBalance() + ", 香蕉包: " + user.getPackages());
        
        initUI();
        refreshData(); // 初始化时刷新数据
        
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void initUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // 顶部信息面板
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // 中间功能区域
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("购买香蕉包", createBuyPackagePanel());
        tabbedPane.addTab("开启香蕉包", createOpenPackagePanel());
        tabbedPane.addTab("出售香蕉", createSellBananaPanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        this.add(mainPanel);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 用户信息面板
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        userInfoPanel.setBorder(new TitledBorder("用户信息"));
        
        balanceLabel = new JLabel("余额: ¥" + curUser.getBalance());
        balanceLabel.setFont(new Font("宋体", Font.BOLD, 14));
        
        packageCountLabel = new JLabel("香蕉包: " + curUser.getPackages() + "个");
        packageCountLabel.setFont(new Font("宋体", Font.BOLD, 14));
        
        userInfoPanel.add(balanceLabel);
        userInfoPanel.add(packageCountLabel);
        
        // 香蕉信息面板
        bananaInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        bananaInfoPanel.setBorder(new TitledBorder("香蕉收藏"));
        
        bananaCountLabels = new JLabel[bananaTypes.length];
        for (int i = 0; i < bananaTypes.length; i++) {
            bananaCountLabels[i] = new JLabel(bananaTypes[i] + ": 0");
            bananaCountLabels[i].setFont(new Font("宋体", Font.BOLD, 14));
            bananaCountLabels[i].setForeground(bananaColors[i]);
            bananaInfoPanel.add(bananaCountLabels[i]);
        }
        
        // 刷新按钮
        JButton refresh = new JButton("刷新");
        refresh.addActionListener(e -> refreshData());
        
        JPanel topInfoPanel = new JPanel(new GridLayout(2, 1, 0, 10));
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
        
        // 标题
        JLabel titleLabel = new JLabel("购买香蕉包");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // 描述
        JLabel descLabel = new JLabel("香蕉包价格: 2元/个");
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 数量选择
        JPanel amountPanel = new JPanel();
        JLabel amountLabel = new JLabel("购买数量:");
        String[] amounts = {"1", "5", "10", "20", "50"};
        JComboBox<String> amountComboBox = new JComboBox<>(amounts);
        amountPanel.add(amountLabel);
        amountPanel.add(amountComboBox);
        amountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 购买按钮
        JButton buyButton = new JButton("购买");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        
        // 标题
        JLabel titleLabel = new JLabel("开启香蕉包");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
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
        descArea.setBackground(null);
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 开包按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton open1Button = new JButton("开启1个");
        JButton open5Button = new JButton("开启5个");
        JButton open10Button = new JButton("开启10个");
        
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
        
        // 标题
        JLabel titleLabel = new JLabel("出售香蕉");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
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
        //priceArea.setBackground(null);
        priceArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 选择面板
        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        selectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectionPanel.setMaximumSize(new Dimension(300, 100));
        
        JLabel typeLabel = new JLabel("香蕉类型:");
        JComboBox<String> typeComboBox = new JComboBox<>(bananaTypes);
        
        JLabel amountLabel = new JLabel("出售数量:");
        JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        
        JLabel totalLabel = new JLabel("预计获得:");
        JLabel totalValueLabel = new JLabel("¥1.0");
        
        selectionPanel.add(typeLabel);
        selectionPanel.add(typeComboBox);
        selectionPanel.add(amountLabel);
        selectionPanel.add(amountSpinner);
        selectionPanel.add(totalLabel);
        selectionPanel.add(totalValueLabel);
        
        // 更新预计获得金额
        typeComboBox.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            int amount = (int) amountSpinner.getValue();
            double price = us.getBananaPrice(selectedType);
            totalValueLabel.setText("¥" + (price * amount));
        });
        
        amountSpinner.addChangeListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            int amount = (int) amountSpinner.getValue();
            double price = us.getBananaPrice(selectedType);
            totalValueLabel.setText("¥" + (price * amount));
        });
        
        // 出售按钮
        JButton sellButton = new JButton("出售");
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellButton.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            int amount = (int) amountSpinner.getValue();
            double price = us.getBananaPrice(selectedType);
            
            BananaTemp bananaTemp = new BananaTemp(curUser.getName(), selectedType, price, amount);
            if (us.sellBanana(curUser, bananaTemp)) {
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
    
    private void refreshData() {
        try {
            // 更新用户数据 - 使用仅需用户名的方法
            User updatedUser = userDao.getUserByName(curUser.getName());
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
            banana = bananaDao.getOne(curUser.getName());
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