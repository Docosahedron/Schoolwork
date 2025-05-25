package work_demo.GUI;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private final User curUser;//指定用户
    GameSer gs = new GameSer();
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
        JMenu warehouse = new JMenu("仓 库");//
        JMenuItem collection = new JMenuItem("收藏");
        JMenuItem download = new JMenuItem("下载");
        JMenu accountOp = new JMenu("账 号 操 作");//
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
        accountOp.add(lock);//
        accountOp.add(exit);
        setJMenuBar(menuBar);
        this.add(menuBar);
        menuBar.setVisible(true);
        //设置交互
        home.addActionListener(e->{
            gameShow.setVisible(true);
        });
        features.addActionListener(e->{});
        discovery.addActionListener(e->{});
        wishList.addActionListener(e->{
            new wishlistFrame(curUser);
        });
        collection.addActionListener(e->{});
        download.addActionListener(e->{});
        lock.addActionListener(e->{
            new loginFrame();
            dispose();
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
            showSearched(typeIn.getText());
        });
        refresh.addActionListener(e->{
            refresh("");
        });
        mainPanel.add(searchArea, BorderLayout.NORTH);
    }

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
        tableModel.addColumn("价格");
        tableModel.addColumn("库存");
        //创设滚轮和展示界面
        JTable gameTable = new JTable(tableModel);
        JScrollPane jp = new JScrollPane(gameTable);
        gameShow.add(jp, BorderLayout.CENTER);
        gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 只能单选
        gameTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击情况下才会跳转
                    int row = gameTable.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        Game curGame = gs.getByName((String) tableModel.getValueAt(row, 0));
                        new gameDetailsFrame(curGame,curUser);
                    }
                }
            }
        });
        // 加载初始数据
        refresh("");
        mainPanel.add(gameShow, BorderLayout.CENTER);//组件添加到主组件
    }
    public void showSearched(String type) {
        // 获取当前表格模型,固定搭配,先记着
        JScrollPane scrollPane = (JScrollPane) gameShow.getComponent(0);
        JTable gameTable = (JTable)scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel)gameTable.getModel();
        // 清空现有数据
        tableModel.setRowCount(0);
        //刷新数据
        refresh(type);
    }

    // 提取刷新数据的公共方法
    private void refresh(String type) {
        tableModel.setRowCount(0);
        List<Game> games = gs.getByType(type);
        for (Game g : games) {
            tableModel.addRow(new Object[]{
                    g.getName(),
                    g.getType(),
                    g.getPrice(),
                    g.getNum()
            });
        }
        gameShow.revalidate();
        gameShow.repaint();
    }

    public static void main(String[] args) {
        new UserFrame(new User(0, "test", "123"));
    }
}