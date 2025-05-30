package front.GUI;
import back.SERVICE.SerImpl.GameSerImpl;
import front.Views.LoginView;
import back.ENTITY.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserFrame extends JFrame {
    private final User curUser;//指定用户
    GameSerImpl gs =new GameSerImpl();
    DefaultTableModel tableModel;
    JPanel mainPanel; // 主面板，使用BorderLayout
    JPanel searchArea;//筛选
    JPanel gameShow;//显示数据库
    public UserFrame(User user) {
        super(user.getName()+"的用户界面");
        this.curUser = user;
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
        JMenu warehouse = new JMenu("社 区");//
        JMenuItem collection = new JMenuItem("市场");
        JMenuItem download = new JMenuItem("下载");
        JMenu accountOp = new JMenu("账 户");//
        JMenuItem wallet = new JMenuItem("钱包");
        JMenuItem banana = new JMenuItem("库存");
        JMenuItem lock = new JMenuItem("锁定");
        JMenuItem exit = new JMenuItem("退出");
        //添加组件
        menuBar.add(homePage);//
        menuBar.add(store);
        menuBar.add(warehouse);
        menuBar.add(accountOp);
        homePage.add(home);//
        store.add(features);//
        store.add(discovery);
        store.add(wishList);
        warehouse.add(collection);//
        warehouse.add(download);
        accountOp.add(wallet);//
        accountOp.add(banana);
        accountOp.addSeparator(); // 添加分割线
        accountOp.add(lock);
        accountOp.add(exit);
        setJMenuBar(menuBar);
        this.add(menuBar);
        menuBar.setVisible(true);
        //设置交互
        home.addActionListener(e-> gameShow.setVisible(true));
        features.addActionListener(e->{
            showFeatures(80);
        });
        discovery.addActionListener(e->{});
        wishList.addActionListener(e-> new wishlistFrame(curUser));
        collection.addActionListener(e->{});
        download.addActionListener(e->{});
        wallet.addActionListener(e->{
            new WalletFrame(curUser);
        });
        banana.addActionListener(e->{
            new BananaFrame(curUser);
        });
        lock.addActionListener(e->{
            dispose();
            new LoginView();
        });
        exit.addActionListener(e->System.exit(0));
        setJMenuBar(menuBar); // 注意这里使用setJMenuBar而不是add
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
        };
        tableModel.addColumn("名称");
        tableModel.addColumn("类型");
        tableModel.addColumn("评分");
        tableModel.addColumn("价格");
        //创设滚轮和展示界面
        JTable gameTable = new JTable(tableModel);
        JScrollPane jp = new JScrollPane(gameTable);
        gameShow.add(jp, BorderLayout.CENTER);
        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击情况下才会跳转
                    int row = gameTable.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        Game curGame = gs.getWholeInfo((String) tableModel.getValueAt(row, 0));
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
            tableModel.addRow(new Object[]{
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
            tableModel.addRow(new Object[]{
                    g.getName(),
                    g.getType(),
                    g.getScore(),
                    g.getPrice(),
            });
        }
        gameShow.revalidate();
        gameShow.repaint();
    }
    public static void main(String[] args) {
        new UserFrame(new User(0, "test", "123"));
    }
}