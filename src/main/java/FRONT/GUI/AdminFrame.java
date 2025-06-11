package FRONT.GUI;

import BACK.Dao.DBUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*; // 导入 AWT 包以使用 BorderLayout
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException; // 用于 SwingWorker.get()

public class AdminFrame extends JFrame implements ActionListener {
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
    JPanel p = new JPanel(); // 这个 p 面板似乎没有被使用，可以考虑删除或正确布局

    private String primaryKeyColumn = "name"; // 假设 "name" 是主键
    private String[] columnNames; // 存储从数据库获取的列名
    private JTable table;
    private DefaultTableModel tableModel;

    private JPopupMenu popupMenu;
    private int selectedRowForPopup = -1; // 记录右键选中的行

    // 构造函数
    public AdminFrame() {
        super("管理员界面");
        initMenuBar(); // 初始化菜单栏
        // 不要在这里直接调用 initDBTable()
        // 而是将框架显示出来，再在 EDT 上安全地加载数据
        // 注意：setDefaultCloseOperation 应该在 setVisible 之前
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 618);
        this.setLocationRelativeTo(null);
        // this.add(p); // 如果 p 面板没有内容，可以考虑删除或设置其布局

        // 将 JScrollPane 直接添加到 JFrame 的内容面板
        // JFrame 默认使用 BorderLayout
        tableModel = new DefaultTableModel(); // 在这里创建模型
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        this.add(scroll, BorderLayout.CENTER); // 将表格放置在中央

        // 确保 UI 组件可见后再开始加载数据
        this.setVisible(true);

        // 初始化表格相关监听器和右键菜单，在 UI 初始化完成后进行
        setupTableListeners();

        // 异步加载数据库数据
        loadData();
    }

    // 将菜单栏初始化方法单独抽离
    private void initMenuBar() {
        //添加组件
        menuBar.add(homePage);
        menuBar.add(goodsOp);
        menuBar.add(accountOp);

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
        // menuBar.setVisible(true); // JMenuBar 默认是可见的
    }

    /* 将表格的监听器和右键菜单设置放在这里，而不是 initDBTable() */
    private void setupTableListeners() {
        // 添加表格内容监听
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 确保事件来自用户编辑，而不是模型被重置
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    System.out.println("当前选中行：" + row + " 列：" + column);
                    DBUpdate(row, column);
                }
            }
        });

        createPopupMenu();

        // 鼠标点击内容监听
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {   // 右键
                    System.out.println("右键按下");
                    selectedRowForPopup = selectRowAndShowPopup(e);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    System.out.println("右键松开");
                    selectedRowForPopup = selectRowAndShowPopup(e);
                }
            }
        });
    }

    /*创建右键菜单*/
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("删除");
        popupMenu.add(deleteItem);
        // 使用 lambda 表达式更简洁
        deleteItem.addActionListener(e -> {
            if (selectedRowForPopup != -1) {
                deleteSelectedRow(selectedRowForPopup);
            }
        });
    }

    private int selectRowAndShowPopup(MouseEvent e) {
        // 获取当前选中行列位置
        int row = table.rowAtPoint(e.getPoint());
        System.out.println("当前行：" + row);
        if (row >= 0) {
            table.setRowSelectionInterval(row, row); // 选中行
            popupMenu.show(table, e.getX(), e.getY()); // 显示菜单
        }
        return row;
    }

    private void deleteSelectedRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= tableModel.getRowCount()) {
            System.out.println("无效的删除行索引: " + rowIndex);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要删除这条记录吗",
                "确认删除",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Object primaryKeyValue = tableModel.getValueAt(rowIndex, 0); // 假设第一列是主键

            // 在后台线程执行删除操作
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Connection conn = null;
                    PreparedStatement ps = null;
                    try {
                        conn = DBUtils.getConnection();
                        String sql = "DELETE FROM games WHERE `" + primaryKeyColumn + "` = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setObject(1, primaryKeyValue);
                        System.out.println("Executing SQL: " + sql + " with PK: " + primaryKeyValue);
                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected > 0) {
                            // 删除成功，在 EDT 上更新 UI (重新加载数据)
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(AdminFrame.this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                                loadData(); // 重新异步加载数据以刷新表格
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(AdminFrame.this, "删除失败，未找到匹配的记录！", "错误", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    } finally {
                        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // 任务完成，可以在这里处理异常，但UI更新已在 doInBackground 内部的 invokeLater 处理
                    try {
                        get(); // 获取并抛出 doInBackground 中可能发生的异常
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(AdminFrame.this, "删除操作异常: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute(); // 启动 SwingWorker
        }
    }


    /*异步加载数据*/
    public void loadData() {
        // 清空现有数据 (在 EDT 上操作)
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        columnNames = null; // 重置列名

        // 显示加载提示（可选）
        // tableModel.addRow(new Object[]{"正在加载数据...", "", "", ""}); // 假设有4列

        new SwingWorker<List<Vector<Object>>, Void>() {
            private Vector<String> fetchedColumnNames; // 存储获取到的列名

            @Override
            protected List<Vector<Object>> doInBackground() throws Exception {
                // 在后台线程执行数据库操作
                List<Vector<Object>> data = new ArrayList<>();
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    conn = DBUtils.getConnection();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM games");
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    // 记录列名 (在后台线程中安全获取)
                    fetchedColumnNames = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        fetchedColumnNames.add(metaData.getColumnName(i));
                    }

                    // 添加数据
                    while (rs.next()) {
                        Vector<Object> row = new Vector<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(rs.getObject(i));
                        }
                        data.add(row);
                    }
                } finally {
                    // 关闭资源
                    if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                return data;
            }

            @Override
            protected void done() {
                // 在 EDT 上更新 UI
                try {
                    List<Vector<Object>> data = get(); // 获取 doInBackground 的结果
                    if (fetchedColumnNames != null) {
                        // 更新表格列名
                        tableModel.setColumnIdentifiers(fetchedColumnNames);
                        // 更新 AdminFrame 类的 columnNames 成员变量
                        AdminFrame.this.columnNames = fetchedColumnNames.toArray(new String[0]);
                    }

                    // 添加数据行
                    for (Vector<Object> row : data) {
                        tableModel.addRow(row);
                    }
                    // 如果有加载提示，清除它
                    // if (tableModel.getRowCount() > 0 && tableModel.getValueAt(0, 0).equals("正在加载数据...")) {
                    //     tableModel.removeRow(0);
                    // }

                    System.out.println("数据加载成功并已显示在表格中。");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AdminFrame.this, "数据加载失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute(); // 启动 SwingWorker
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

        // 获取主键内容
        // 这里不能直接使用 getGameName()，因为它会重新查询数据库，并且可能导致线程问题
        // 正确的做法是从当前 tableModel 中获取主键值
        // 假设 primaryKeyColumn 对应表格的第一列 (索引 0)
        Object primaryKey = tableModel.getValueAt(y, tableModel.findColumn(primaryKeyColumn));

        System.out.println("主键：" + primaryKey);
        // 确保 columnNames 已经加载
        if (columnNames == null || columnNames.length <= x) {
            System.err.println("错误：列名未加载或索引超出范围。");
            JOptionPane.showMessageDialog(this, "内部错误：列名未加载。", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String columnName = columnNames[x]; // 取出更改列的列名

        // 构建SQL语句
        String sql = "UPDATE games SET `" + columnName + "` = ? WHERE `" + primaryKeyColumn + "` = ?";

        // 在后台线程执行数据库更新
        new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                Connection conn = null;
                PreparedStatement ps = null;
                int rowsAffected = 0;
                try {
                    conn = DBUtils.getConnection();
                    ps = conn.prepareStatement(sql);
                    ps.setObject(1, newValue);
                    ps.setObject(2, primaryKey);
                    System.out.println("SQL语句：" + sql);
                    System.out.println("参数1：" + newValue);
                    System.out.println("参数2：" + primaryKey);
                    rowsAffected = ps.executeUpdate();
                } finally {
                    if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                return rowsAffected;
            }

            @Override
            protected void done() {
                // 在 EDT 上处理结果
                try {
                    int rowsAffected = get(); // 获取 doInBackground 的返回值
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "数据库更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AdminFrame.this, "数据库更新失败，未找到匹配的记录！", "错误", JOptionPane.ERROR_MESSAGE);
                        // 更新失败时，可能需要重新加载数据或回滚表格中的值
                        // loadDataAsync(); // 重新加载数据
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AdminFrame.this, "数据库更新失败：" + e.getCause().getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute(); // 启动 SwingWorker
    }

    // 优化 getGameName 方法：不再需要，因为主键值可以直接从表格模型中获取
    // 如果你还需要一个所有游戏名称的列表，可以考虑在 loadDataAsync 中一并获取并存储
    /*将游戏名放入列表中*/
    /*
    private List<String> getGameName() {
        // ... (此方法不再需要，因为 DBUpdate 直接从 tableModel 获取主键)
    }
    */

    /*按键实现*/
    @Override
    public void actionPerformed(ActionEvent e) {
        // ... 其他 ActionPerformed 逻辑不变
        if (e.getSource() == home) {
            // home 按钮逻辑
        } else if (e.getSource() == add) {
            // 传递当前 AdminFrame 实例以便 GameAddFrame 可以调用 loadDataAsync 刷新表格
            new GameAddFrame(this);
        } else if (e.getSource() == del) {
            // 如果 del 按钮是另一个删除逻辑，而不是右键菜单的删除
            // 考虑重用 deleteSelectedRow 方法或添加新逻辑
        } else if (e.getSource() == update) {
            // update 按钮逻辑
        } else if (e.getSource() == query) {
            // query 按钮逻辑
        } else if (e.getSource() == lock) {
            dispose();
            new LoginFrame(); // 假设 LoginFrame 在 EDT 上创建
        } else if (e.getSource() == exit) {
            dispose();
        }
    }

    /*Text*/
    public static void main(String[] args) {
        // 确保 Swing UI 的创建和显示在 EDT 上
        SwingUtilities.invokeLater(() -> {
            new AdminFrame();
        });
    }

}