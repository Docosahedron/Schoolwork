package work_demo.GUI;
import work_demo.DAO.DBUtils;
import work_demo.SERVICE.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AdminFrame extends JFrame implements ActionListener {
    GameSer gs = new GameSer();
    JMenuBar menuBar = new JMenuBar();
    JMenu homePage = new JMenu("首页");
    JMenuItem home = new JMenuItem("首页");
    JMenu goodsOp = new JMenu("商 品 操 作");
    JMenuItem add = new JMenuItem("添加商品");
    JMenuItem del = new JMenuItem("删除商品");
    JMenuItem update = new JMenuItem("修改商品");
    JMenuItem query = new JMenuItem("查询商品");

    JMenu accountOp = new JMenu("账 号 操 作");
    JMenuItem lock = new JMenuItem("锁定");
    JMenuItem exit = new JMenuItem("退出");
    JPanel p = new JPanel();

    private String primaryKeyColumn = "name";
    private String[] columnNames;
    private JTable table;
    private DefaultTableModel tableModel;

    private JPopupMenu popupMenu;
    private int row2;


    public AdminFrame() {
        super("管理员界面");
        init();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 618);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initDBTable();
    }

    /*将gametable插入界面*/
    private void initDBTable() {
        // 创建表格模型
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

        // 连接数据库并加载数据
        loadfromDB();

        // 添加表格内容监听
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int y = e.getFirstRow();
                int x = e.getColumn();
                System.out.println("当前选中行：" + y + " 列：" + x);
                DBUpdate(y, x);
            }
        });

        createPopupMenu();

        // 鼠标点击内容监听
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {   // 右键
                    System.out.println("右键按下");
                    row2 = selectRowAndShowPopup(e);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    System.out.println("右键松开");
                    row2 = selectRowAndShowPopup(e);
                }
            }
        });
    }

    /*创建右键菜单*/
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("删除");
        popupMenu.add(deleteItem);
        deleteItem.addActionListener(e -> deleteSelectedRow(row2));
    }

    private int selectRowAndShowPopup(MouseEvent e) {
        // 获取当前选中行列位置
        int row = table.rowAtPoint(e.getPoint());
        System.out.println("当前行：" + row);
        if (row >= 0) {
            table.setRowSelectionInterval(row, row);
            popupMenu.show(table, e.getX(), e.getY());
        }
        return row;
    }

    private void deleteSelectedRow(int row2) {
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("row2:" + row2);
        if (row2 >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "确定要删除这条记录吗",
                    "确认删除",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println("在按下yes按钮后，row2：" + row2);
                try {
                    Object name = tableModel.getValueAt(row2, 0);
                    String sql = "DELETE FROM games WHERE `name` = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setObject(1, name);
                    System.out.println("sql: " + sql);
                    System.out.println("name: " + name);
                    ps.executeUpdate();
                    // 刷新表格
                    loadData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("删除失败");
                    JOptionPane.showMessageDialog(this, "删除失败" + e.getMessage());
                }
            }
        }
    }

    /*刷新数据*/
    public void loadData() {
        try {
            // 清空现有数据
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            Connection conn = DBUtils.getConnection();
            // 获取数据
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 添加列名
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(columnNames);

            // 添加数据
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据加载失败: " + e.getMessage());
        }
    }


    /*更新数据库数据*/
    private void DBUpdate(int y, int x) {
        // 忽略无效行或列（例如行删除事件，x = -1）
        if (y < 0 || x < 0 || y >= tableModel.getRowCount() || x >= tableModel.getColumnCount()) {
            System.out.println("忽略无效的表格更改事件：行=" + y + "，列=" + x);
            return;
        }

        // 获取更改的单元格值
        Object newValue = tableModel.getValueAt(y, x);
        List<String> list = getGameName();
        if (y >= list.size()) {
            System.out.println("行索引超出主键列表范围：y=" + y);
            return;
        }
        Object primaryKey = list.get(y); // 获取主键内容
        System.out.println("主键：" + primaryKey);
        String columnName = columnNames[x]; // 取出更改列的列名

        // 构建SQL语句
        String sql = "UPDATE games SET " + columnName + " = ? WHERE " + primaryKeyColumn + " = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, newValue);
            ps.setObject(2, primaryKey);
            System.out.println("SQL语句：" + sql);
            System.out.println("参数1：" + newValue);
            System.out.println("参数2：" + primaryKey);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "数据库更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "数据库更新失败，未找到匹配的记录！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库更新失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*将游戏名放入列表中*/
    private List<String> getGameName() {
        Connection conn;
        Statement stat;
        ResultSet rs;
        try {
            conn = DBUtils.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "select name from games";
        String sql2 = "select count(*) as total from games";
        try {
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> gameName = new ArrayList<>();
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                gameName.add(rs.getString("name"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return gameName;
    }

    /*获取数据库中的数据*/
    private void loadfromDB() {
        // 建立连接
        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
        } catch (SQLException e) {
            System.out.println("连接数据库失败");
            throw new RuntimeException(e);
        }
        String sql = "select * from games";
        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("失败的执行语句");
            throw new RuntimeException(e);
        }

        // 获取元数据
        ResultSetMetaData metaData = null;
        try {
            metaData = rs.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnCount = 0;
        try {
            columnCount = metaData.getColumnCount();
        } catch (SQLException e) {
            System.out.println("查询未返回任何列");
            throw new RuntimeException(e);
        }

        // 记录列名
        columnNames = new String[columnCount];
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        for (int i = 1; i <= columnCount; i++) {
            try {
                columnNames[i - 1] = metaData.getColumnName(i);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tableModel.addColumn(columnNames[i - 1]);
        }

        // 添加数据行到表格模型
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                try {
                    row[i - 1] = rs.getObject(i);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            tableModel.addRow(row);
        }

        // 关闭资源
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stat.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /*按键实现*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {

        } else if (e.getSource() == add) {
            new GameAddFrame(this); // 传递当前 AdminFrame 实例
        } else if (e.getSource() == del) {

        } else if (e.getSource() == update) {

        } else if (e.getSource() == query) {

        } else if (e.getSource() == lock) {
            dispose();
            new loginFrame();
        } else if (e.getSource() == exit) {
            dispose();
        }
    }

    /*初始化菜单*/
    public void init() {
        p.setLayout(null);

        //添加组件
        p.add(menuBar);
        menuBar.add(homePage);
        menuBar.add(goodsOp);
        menuBar.add(accountOp);
        //
        homePage.add(home);
        //
        goodsOp.add(add);
        goodsOp.add(del);
        goodsOp.add(update);
        goodsOp.add(query);
        //
        accountOp.add(lock);
        accountOp.add(exit);
        //首页
        home.addActionListener(this);

        add.addActionListener(this);
        del.addActionListener(this);
        update.addActionListener(this);
        query.addActionListener(this);
        //退出管理员界面

        lock.addActionListener(this);
        exit.addActionListener(this);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);
    }

    /*Text*/
    public static void main(String[] args) {
        new AdminFrame();
    }

}
