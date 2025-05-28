package work_demo.GUI;
import work_demo.DAO.DBUtils;
import work_demo.DAO.GameDao;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFrame extends JFrame implements ActionListener {
    GameSer gs = new GameSer();
    JMenuBar menuBar =new JMenuBar();
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
    JPanel p =new JPanel();

    private String primaryKeyColumn = "name";
    private String[] columnNames;

    public AdminFrame() {
        super("管理员界面");
        init();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,618);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initDBTable();
    }

    private JTable table;
    private DefaultTableModel tableModel;

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
                // 当表格中内容被更改后，更新到数据库
                // 获取行列索引
                int y = e.getFirstRow();
                int x = e.getColumn();
                // 使用DBUpdate更新数据库
                DBUpdate(y, x);
            }
        });
    }

    /*更新数据库数据*/
    private void DBUpdate(int y, int x) {
        // 获取更改的单元格值
        Object newValue = tableModel.getValueAt(y, x);
        List<String> list = getGameName();
        Object primaryKey = list.get(y);//获取主键
        System.out.println("primaryKey是：" + primaryKey);
        String  columnName = columnNames[x];    // 取出更改列列名

        // 构建SQL语句
        String sql = "update games set " + columnName + "= ? where " + primaryKeyColumn + " = ?";
        Connection conn;
        try {
            conn = DBUtils.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, newValue);
            ps.setObject(2, primaryKey);
            System.out.println("sql语句为：" + sql);
            System.out.println("第一个？:" + newValue);
            System.out.println("第二个？:" + primaryKey);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "数据库更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "数据库更新失败，未找到匹配的记录！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        List<String> gameName = new ArrayList<> ();
        while(true) {
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
        if(e.getSource()==home){

        } else if (e.getSource()==add) {
            new GameAddFrame();
        }else if (e.getSource()==del) {

        }else if (e.getSource()==update) {

        }else if (e.getSource()==query) {

        }else if (e.getSource()==lock) {
            dispose();
            new loginFrame();
        }
        else if (e.getSource()==exit) {
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
