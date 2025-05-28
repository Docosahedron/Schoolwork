package work_demo.GUI;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class wishlistFrame extends JFrame {
    private final User curUser;
    GameSer gs=  new GameSer();
    JPanel gameShow;
    DefaultTableModel tableModel;
    public wishlistFrame(User user) {
        super(user.getName()+"的心愿单");
        curUser = user;
        showWishlist();
        this.add(gameShow);
        this.setSize(800,495);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void showWishlist() {
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
        tableModel.addColumn("数量");
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
                        Game curGame = new Game((String) tableModel.getValueAt(row, 0),
                                (String) tableModel.getValueAt(row, 1),
                                (Double) tableModel.getValueAt(row, 2),
                                (int) tableModel.getValueAt(row, 3));
                        new gameDetailsFrame(curGame,curUser);
                    }
                }
            }
        });
        // 加载初始数据
        refresh(curUser.getName());
    }
    private void refresh(String userName) {
        tableModel.setRowCount(0);
        List<Game> games = gs.getByUser(userName);
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
}
