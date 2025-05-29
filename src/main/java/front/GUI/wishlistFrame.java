package front.GUI;
import back.ENTITY.*;
import back.DAO.DaoImpl.*;
import back.SERVICE.SerImpl.GameSerImpl;
import back.SERVICE.SerImpl.WishlistSerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class wishlistFrame extends JFrame {
    private final User curUser;
    GameSerImpl gs = new GameSerImpl();
    WishlistSerImpl ws = new WishlistSerImpl();
    WishlistDaoImpl wdi  =new WishlistDaoImpl();
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
                return false;
            }
        };
        tableModel.addColumn("名称");
        tableModel.addColumn("类型");
        tableModel.addColumn("评分");
        tableModel.addColumn("价格");

        // 创建表格
        JTable gameTable = new JTable(tableModel);
        JScrollPane jp = new JScrollPane(gameTable);
        gameShow.add(jp, BorderLayout.CENTER);
        gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 创建右键菜单
        JPopupMenu popupMenu = new JPopupMenu();
        JMenu removeMenu = new JMenu("移除");
        JMenuItem removeOneItem = new JMenuItem("移除当前游戏");
        removeMenu.add(removeOneItem);
        JMenuItem removeAllItem = new JMenuItem("移除全部游戏");
        removeMenu.add(removeAllItem);
        popupMenu.add(removeMenu);
        // 右键点击表格逻辑
        gameTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int row = gameTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        Game curGame = gs.getWholeInfo((String) tableModel.getValueAt(row, 0));
                        new gameDetailsFrame(curUser,curGame);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupIfTriggered(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupIfTriggered(e);
            }
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            private void showPopupIfTriggered(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = gameTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < gameTable.getRowCount()) {
                        gameTable.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        // 右键菜单点击删除逻辑（调用你自己的方法）
        removeOneItem.addActionListener(e -> {
            int row = gameTable.getSelectedRow();
            if (row >= 0) {
                Game curGame = gs.getWholeInfo((String) tableModel.getValueAt(row, 0));
                int confirm = JOptionPane.showConfirmDialog(
                        wishlistFrame.this,
                        "确定要将 [" + curGame.getName() + "] 从心愿单中移除吗？",
                        "确认移除",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ws.removeWishlist(curUser, curGame);
                }
            }
        });
        removeAllItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    wishlistFrame.this,
                    "确定要清空您的整个心愿单吗？",
                    "确认清空",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ws.removeWishlistAll(curUser);
            }
        });

        // 加载初始数据
        refresh(curUser.getName());
    }
    private void refresh(String userName) {
        tableModel.setRowCount(0);
        List<Game> games = wdi.getAllGames(userName);
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
}
