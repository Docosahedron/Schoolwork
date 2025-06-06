package FRONT.GUI;
import BACK.Service.SerImpl.*;
import BACK.Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class UserFrame extends JFrame {
    private final User curUser;//指定用户
    private final Banana curBanana;
    GameSerImpl gs =new GameSerImpl();
    UserSerImpl us = new UserSerImpl();
    DefaultTableModel tableModel;
    JPanel mainPanel; // 主面板，使用BorderLayout
    JPanel searchArea;//筛选
    JPanel gameShow;//显示数据库
    public UserFrame(User user) {
        super(user.getName()+"的用户界面");
        this.curUser = user;
        this.curBanana = us.getBanana(curUser);
        // 设置主面板布局
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setMenuBar();
        setSearch();
        showAll();
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭退出程序
        this.setSize(800,495);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //设置菜单栏,是JFrame的特别组件
    public void setMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu homePage = new JMenu("首页");
        JMenuItem home = new JMenuItem("首页");
        JMenu store = new JMenu("商 店");//
        JMenuItem features = new JMenuItem("精选");
        JMenuItem discovery = new JMenuItem("探索队列");
        JMenuItem wishList = new JMenuItem("心愿单");
        JMenu community = new JMenu("社 区");//
        JMenuItem market = new JMenuItem("市场");
        JMenuItem download = new JMenuItem("下载");
        JMenu accountOp = new JMenu("账 户");//
        JMenuItem wallet = new JMenuItem("钱包");
        JMenuItem ware = new JMenuItem("库存");
        JMenuItem lock = new JMenuItem("锁定");
        JMenuItem exit = new JMenuItem("退出");
        //添加组件
        menuBar.add(homePage);//
        menuBar.add(store);
        menuBar.add(community);
        menuBar.add(accountOp);
        homePage.add(home);//
        store.add(features);//
        store.add(discovery);
        store.add(wishList);
        community.add(market);//
        community.add(download);
        accountOp.add(wallet);//
        accountOp.add(ware);
        accountOp.addSeparator(); // 添加分割线
        accountOp.add(lock);
        accountOp.add(exit);
        setJMenuBar(menuBar);
        this.add(menuBar);
        menuBar.setVisible(true);
        //设置交互
        home.addActionListener(e-> gameShow.setVisible(true));
        //检索精选游戏
        features.addActionListener(e->{
            showFeatures(80);
        });
        discovery.addActionListener(e->{});
        //打开心愿单
        wishList.addActionListener(e-> new WishlistFrame(curUser));
        //打开市场
        market.addActionListener(e-> new MarketFrame(curUser));
        //
        download.addActionListener(e->{});
        //打开钱包
        wallet.addActionListener(e->new WalletFrame(curUser));
        //打开库存
        ware.addActionListener(e->new WareFrame(curUser,curBanana));
        //锁定
        lock.addActionListener(e->{
            dispose();
            new LoginFrame();
        });
        //退出程序
        exit.addActionListener(e->System.exit(0));
        setJMenuBar(menuBar);
    }

    //初始化检索框
    public void setSearch(){
        searchArea = new JPanel();
        searchArea.setLayout(new FlowLayout(FlowLayout.LEFT)); // 左对齐
        JLabel type = new JLabel("类型:");
        JTextField typeIn = new JTextField(8);
        JLabel minPrice = new JLabel("最低价:");
        JTextField minPriceIn = new JTextField(8);
        JLabel maxPrice = new JLabel("最高价:");
        JTextField maxPriceIn = new JTextField(8);
        JButton searchButton = new JButton("检索");
        JButton refresh = new JButton("重置");
        searchArea.add(type);
        searchArea.add(typeIn);
        searchArea.add(minPrice);
        searchArea.add(minPriceIn);
        searchArea.add(maxPrice);
        searchArea.add(maxPriceIn);
        searchArea.add(searchButton);
        searchArea.add(refresh);
        searchButton.addActionListener(e->{
            double min;double max;
            if (minPriceIn.getText().isEmpty()) min = 0;
            else min = Double.parseDouble(minPriceIn.getText());
            if (maxPriceIn.getText().isEmpty()) max = 1e9;
            else max = Double.parseDouble(maxPriceIn.getText());
            showSearched(typeIn.getText(),min,max);
        });
        refresh.addActionListener(e-> refresh("",0,1000));
        mainPanel.add(searchArea, BorderLayout.NORTH);
    }


    // 商品表格初始化
    public void showAll() {
        gameShow = new JPanel(new BorderLayout());
        // 创建表格模型,重写表格类型,使其只读
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
        
        tableModel.addColumn("图片");
        tableModel.addColumn("名称");
        tableModel.addColumn("类型");
        tableModel.addColumn("评分");
        tableModel.addColumn("价格");
        
        //创设滚轮和展示界面
        JTable gameTable = new JTable(tableModel);
        
        // 设置行高以适应图片
        gameTable.setRowHeight(60);
        
        // 设置图片列宽
        TableColumn imageColumn = gameTable.getColumnModel().getColumn(0);
        imageColumn.setPreferredWidth(80);
        imageColumn.setMaxWidth(80);
        
        JScrollPane jp = new JScrollPane(gameTable);
        gameShow.add(jp, BorderLayout.CENTER);
        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击情况下才会跳转
                    int row = gameTable.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        Game curGame = gs.getGameInfo((String) tableModel.getValueAt(row, 1));
                        new GameDetailsFrame(curUser,curGame);
                    }
                }
            }
        });
        // 加载初始数据
        refresh("",0,1000);
        mainPanel.add(gameShow, BorderLayout.CENTER);//组件添加到主组件
    }
    public void showSearched(String type,double min,double max) {
        // 获取当前表格模型,固定搭配,先记着
        JScrollPane scrollPane = (JScrollPane) gameShow.getComponent(0);
        JTable gameTable = (JTable)scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel)gameTable.getModel();
        // 清空现有数据
        tableModel.setRowCount(0);
        //刷新数据
        refresh(type,min,max);
    }
    public void showFeatures(int score) {
        // 获取当前表格模型,固定搭配,先记着
        JScrollPane scrollPane = (JScrollPane) gameShow.getComponent(0);
        JTable gameTable = (JTable)scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel)gameTable.getModel();
        // 清空现有数据
        tableModel.setRowCount(0);
        //刷新数据
        refresh(score);
    }

    // 提取刷新数据的公共方法
    private void refresh(String type,double min,double max) {
        tableModel.setRowCount(0);
        List<Game> games = gs.getGameBySearch(type, min,max);
        for (Game g : games) {
            // 加载游戏图片
            ImageIcon gameIcon = loadImage(g.getName());
            
            tableModel.addRow(new Object[]{
                    gameIcon,
                    g.getName(),
                    g.getType(),
                    g.getScore(),
                    g.getPrice(),
            });
        }
        gameShow.revalidate();
        gameShow.repaint();
    }
    private void refresh(int score) {
        tableModel.setRowCount(0);
        List<Game> games = gs.getGameByScore(score);
        for (Game g : games) {
            // 加载游戏图片
            ImageIcon gameIcon = loadImage(g.getName());
            
            tableModel.addRow(new Object[]{
                    gameIcon,
                    g.getName(),
                    g.getType(),
                    g.getScore(),
                    g.getPrice(),
            });
        }
        gameShow.revalidate();
        gameShow.repaint();
    }
    
    // 加载游戏图片
    private ImageIcon loadImage(String gameName) {
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
    
    public static void main(String[] args) {
        new UserFrame(new User(4, "a", "a"));
    }
}