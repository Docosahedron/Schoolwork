package FRONT.GUI;
import BACK.Entity.*;
import BACK.Service.SerImpl.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class WishlistFrame extends JFrame {
    private final User curUser;
    GameSerImpl gs = new GameSerImpl();
    WishlistSerImpl ws = new WishlistSerImpl();
    JPanel gameShow;
    DefaultTableModel tableModel;
    
    public WishlistFrame(User user) {
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

        // 创建表格
        JTable gameTable = new JTable(tableModel);
        
        // 设置行高以适应图片
        gameTable.setRowHeight(60);
        
        // 设置图片列宽
        TableColumn imageColumn = gameTable.getColumnModel().getColumn(0);
        imageColumn.setPreferredWidth(80);
        imageColumn.setMaxWidth(80);
        
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
                        Game curGame = gs.getGameInfo((String) tableModel.getValueAt(row, 1));
                        new GameDetailsFrame(curUser,curGame);
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
                Game curGame = gs.getGameInfo((String) tableModel.getValueAt(row, 1));
                int confirm = JOptionPane.showConfirmDialog(
                        WishlistFrame.this,
                        "确定要将 [" + curGame.getName() + "] 从心愿单中移除吗？",
                        "确认移除",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ws.removeSelected(curUser, curGame);
                    refresh();
                }
            }
        });
        removeAllItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    WishlistFrame.this,
                    "确定要清空您的整个心愿单吗？",
                    "确认清空",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ws.removeWishlist(curUser);
                refresh();
            }
        });

        // 加载初始数据
        refresh();
    }
    
    public void refresh() {
        tableModel.setRowCount(0);
        List<Game> games = ws.getAllWishlistGame(curUser);
        for (Game g : games) {
            // 加载游戏图片
            ImageIcon gameIcon = loadGameImage(g.getName());
            
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
}
