package FRONT.GUI;

import BACK.Entity.*;
import BACK.Service.SerImpl.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class WareFrame extends JFrame implements ActionListener {
    private final User curUser;
    private final Banana curBanana;
    private JPanel mainPanel;
    private JPanel wareShow;
    private JPanel bananaPanel;
    private DefaultTableModel tableModel;
    UserSerImpl us = new UserSerImpl();
    GameSerImpl gs = new GameSerImpl();
    WarehouseSerImpl ws = new WarehouseSerImpl();
    
    public WareFrame(User user,Banana banana) {
        super(user.getName() + "的库存界面");
        this.curUser = user;
        this.curBanana = banana;
        
        // 设置主面板布局
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        setMenuBar();
        showBananaInfo();
        showAllWares();
        
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    // 设置菜单栏
    public void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu operationMenu = new JMenu("操作");
        JMenuItem refreshItem = new JMenuItem("刷新");
        JMenuItem marketItem = new JMenuItem("前往市场");
        JMenuItem closeItem = new JMenuItem("关闭");
        
        operationMenu.add(refreshItem);
        operationMenu.add(marketItem);
        operationMenu.addSeparator();
        operationMenu.add(closeItem);
        
        menuBar.add(operationMenu);
        
        // 设置交互
        refreshItem.addActionListener(e -> {
            refreshInfo();
            refreshTable();
        });
        marketItem.addActionListener(e -> new MarketFrame(curUser));
        closeItem.addActionListener(e -> dispose());
        
        setJMenuBar(menuBar);
    }
    
    // 显示香蕉和包裹信息
    private void showBananaInfo() {
        bananaPanel = new JPanel();
        bananaPanel.setLayout(new BorderLayout());
        
        // 创建包裹和香蕉信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1, 5, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 包裹信息面板
        JPanel packagePanel = new JPanel();
        packagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        packagePanel.setBorder(BorderFactory.createTitledBorder("香蕉包数量"));
        JLabel packageLabel = new JLabel("当前拥有: " + curUser.getPackages() + " 个");
        packageLabel.setFont(new Font("宋体", Font.BOLD, 14));
        packagePanel.add(packageLabel);
        
        // 香蕉信息面板
        JPanel bananaInfoPanel = new JPanel();
        bananaInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        bananaInfoPanel.setBorder(BorderFactory.createTitledBorder("香蕉收藏"));
        
        // 获取香蕉数据
        if (curBanana != null) {
            // 创建不同稀有度的标签
            addBananaTypeLabel(bananaInfoPanel, "N", curBanana.getN(), new Color(150, 150, 150));
            addBananaTypeLabel(bananaInfoPanel, "R", curBanana.getR(), new Color(0, 112, 221));
            addBananaTypeLabel(bananaInfoPanel, "SR", curBanana.getSR(), new Color(163, 53, 238));
            addBananaTypeLabel(bananaInfoPanel, "SSR", curBanana.getSSR(), new Color(255, 128, 0));
            addBananaTypeLabel(bananaInfoPanel, "UR", curBanana.getUR(), new Color(255, 215, 0));
        } else {
            JLabel noBananaLabel = new JLabel("暂无香蕉数据");
            bananaInfoPanel.add(noBananaLabel);
        }
        
        infoPanel.add(packagePanel);
        infoPanel.add(bananaInfoPanel);
        
        bananaPanel.add(infoPanel, BorderLayout.CENTER);
        
        // 添加前往市场按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton marketButton = new JButton("前往市场");
        marketButton.addActionListener(e -> new MarketFrame(curUser));
        buttonPanel.add(marketButton);
        bananaPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(bananaPanel, BorderLayout.NORTH);
    }
    
    // 添加香蕉类型标签
    private void addBananaTypeLabel(JPanel panel, String type, int count, Color color) {
        JLabel typeLabel = new JLabel(type + ": " + count);
        typeLabel.setFont(new Font("宋体", Font.BOLD, 14));
        typeLabel.setForeground(color);
        panel.add(typeLabel);
    }
    
    // 刷新香蕉和包裹信息
    private void refreshInfo() {
        // 重新获取最新的用户数据
        User updatedUser = us.getUser(curUser.getName());
        if (updatedUser != null) {
            curUser.setPackages(updatedUser.getPackages());
        }
        // 移除旧面板
        mainPanel.remove(bananaPanel);
        // 重新显示香蕉信息
        showBananaInfo();
        // 重新布局
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // 显示所有库存游戏
    public void showAllWares() {
        wareShow = new JPanel(new BorderLayout());
        // 添加标题
        JLabel titleLabel = new JLabel("我的游戏库", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        wareShow.add(titleLabel, BorderLayout.NORTH);
        
        // 创建表格模型，设置为只读
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格都不可编辑
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                // 第一列是图片列
                return column == 0 ? ImageIcon.class : Object.class;
            }
        };
        
        // 添加列
        tableModel.addColumn("图片");
        tableModel.addColumn("名称");
        tableModel.addColumn("类型");
        tableModel.addColumn("评分");
        tableModel.addColumn("价格");
        
        // 创建表格和滚动面板
        JTable wareTable = new JTable(tableModel);
        
        // 设置行高以适应图片
        wareTable.setRowHeight(60);
        
        // 设置图片列宽
        TableColumn imageColumn = wareTable.getColumnModel().getColumn(0);
        imageColumn.setPreferredWidth(80);
        imageColumn.setMaxWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(wareTable);
        wareShow.add(scrollPane, BorderLayout.CENTER);
        
        // 加载初始数据
        refreshTable();
        
        mainPanel.add(wareShow, BorderLayout.CENTER);
    }
    
    // 刷新表格数据
    private void refreshTable() {
        tableModel.setRowCount(0); // 清空表格
        
        List<Game> games = ws.getGames(curUser.getName());
        if (games.isEmpty()) {
            JLabel emptyLabel = new JLabel("您的库存中还没有游戏，快去商店购买吧！", JLabel.CENTER);
            emptyLabel.setFont(new Font("宋体", Font.BOLD, 14));
            wareShow.add(emptyLabel, BorderLayout.SOUTH);
        } else {
            // 移除可能存在的空库存提示
            Component[] components = wareShow.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && 
                    ((JLabel)component).getText().contains("您的库存中还没有游戏")) {
                    wareShow.remove(component);
                }
            }
            
            for (Game game : games) {
                // 尝试加载游戏图片
                ImageIcon gameIcon = loadGameImage(game.getName());
                
                tableModel.addRow(new Object[]{
                    gameIcon,
                    game.getName(),
                    game.getType(),
                    game.getScore(),
                    game.getPrice()
                });
            }
        }
        
        wareShow.revalidate();
        wareShow.repaint();
    }
    
    // 加载游戏图片
    private ImageIcon loadGameImage(String gameName) {
        try {
            // 尝试从游戏图片目录加载图片
            String imagePath = "src/main/resources/images/games/" + gameName + ".jpg";
            ImageIcon originalIcon = new ImageIcon(imagePath);
            
            // 检查图片是否成功加载
            if (originalIcon.getIconWidth() <= 0) {
                // 如果加载失败，创建一个默认的图片图标
                return createDefaultGameIcon(gameName);
            }
            
            // 调整图片大小
            Image image = originalIcon.getImage();
            Image scaledImage = image.getScaledInstance(60, 50, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
            
        } catch (Exception e) {
            System.out.println("加载游戏图片失败: " + gameName + ", 错误: " + e.getMessage());
            // 创建默认图片
            return createDefaultGameIcon(gameName);
        }
    }
    
    // 创建默认游戏图标
    private ImageIcon createDefaultGameIcon(String gameName) {
        // 创建一个空白图片
        BufferedImage image = new BufferedImage(60, 50, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // 设置图片背景
        g.setColor(new Color(70, 70, 70));
        g.fillRect(0, 0, 60, 50);
        
        // 添加游戏名称的首字母
        g.setColor(Color.WHITE);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        
        String initial = gameName.length() > 0 ? gameName.substring(0, 1).toUpperCase() : "G";
        FontMetrics metrics = g.getFontMetrics();
        int x = (60 - metrics.stringWidth(initial)) / 2;
        int y = ((50 - metrics.getHeight()) / 2) + metrics.getAscent();
        
        g.drawString(initial, x, y);
        g.dispose();
        
        return new ImageIcon(image);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // 处理事件
    }
}
