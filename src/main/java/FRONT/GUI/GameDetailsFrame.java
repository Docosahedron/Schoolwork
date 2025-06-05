package FRONT.GUI;

import BACK.Entity.*;
import BACK.Service.SerImpl.ReviewSerImpl;
import BACK.Service.SerImpl.UserSerImpl;
import BACK.Service.SerImpl.WarehouseSerImpl;
import BACK.Service.SerImpl.WishlistSerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class GameDetailsFrame extends JFrame {
    // ... 其他成员变量保持不变 ...
    private final Game curGame;
    private final User curUser;
    boolean flag1;boolean flag2;
    UserSerImpl us = new UserSerImpl();
    ReviewSerImpl rs = new ReviewSerImpl();
    WishlistSerImpl ws = new WishlistSerImpl();
    WarehouseSerImpl whs = new WarehouseSerImpl();
    JPanel pGame = new JPanel();
    JPanel pReview = new JPanel();
    public GameDetailsFrame(User user, Game game) {
        super(game.getName());
        this.curGame = game;
        this.curUser = user;
        this.flag1 = ws.queryAdded(curUser,curGame);
        this.flag2  = whs.queryBought(curUser,curGame);
        // 设置主窗口使用BorderLayout
        this.setLayout(new BorderLayout());
        this.setBounds(100, 100, 500, 700); // 稍微增大窗口尺寸
        this.setLocationRelativeTo(null);

        pGame(curGame);
        pReview(curGame);

        this.add(pGame, BorderLayout.NORTH);
        this.add(pReview, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void pGame(Game game) {
        // 使用GridBagLayout以获得更灵活的布局
        pGame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距
        gbc.anchor = GridBagConstraints.WEST; // 左对齐

        // 第一列：游戏信息
        gbc.gridx = 0;
        gbc.gridy = 0;
        pGame.add(new JLabel("类型: " + game.getType()), gbc);

        gbc.gridy++;
        pGame.add(new JLabel("评分: " + game.getScore()), gbc);

        gbc.gridy++;
        pGame.add(new JLabel("价格: " + game.getPrice()), gbc);

//        gbc.gridy++;
//        pGame.add(new JLabel("库存: " + game.get), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2; // 跨越两列
        JTextArea overview = new JTextArea("概述:\n" + game.getOverview());
        overview.setEditable(false);
        overview.setLineWrap(true);
        overview.setWrapStyleWord(true);
        overview.setBackground(pGame.getBackground());
        pGame.add(overview, gbc);

        // 第二列：按钮
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 重置列跨度
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton addWishlist = new JButton();
        if (flag1) addWishlist.setText("移出心愿单");
        else addWishlist.setText("加入心愿单");
        pGame.add(addWishlist, gbc);

        gbc.gridy++;
        JButton buy = new JButton();
        if (flag2) buy.setText("已购买!");
        else buy.setText("购买");
        pGame.add(buy, gbc);

        // 按钮事件监听保持不变
        addWishlist.addActionListener(e->{
            if(flag1) flag1=!ws.removeSelected(this.curUser,this.curGame);
            else flag1=ws.addWishlist(this.curUser,this.curGame);
            if (flag1) addWishlist.setText("移出心愿单");
            else addWishlist.setText("加入心愿单");

        });

        buy.addActionListener(e-> {
            if(flag2) JOptionPane.showMessageDialog(null, "已拥有,无法购买!");
            else flag2=us.buy(curUser,curGame);
            if (flag2) buy.setText("已购买!");
            else buy.setText("购买");
        });
    }

    public void pReview(Game game) {
        // 使用BorderLayout作为基础，内部表格使用GridLayout
        pReview.setLayout(new BorderLayout());

        // 创建带标题的面板
        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setBorder(BorderFactory.createTitledBorder("游戏评论"));

        // 商品数据显示
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("评论内容");
        tableModel.addColumn("评论用户");
        tableModel.addColumn("评论时间");

        JTable gameTable = new JTable(tableModel) {
            // 禁止表格编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 设置内容列自动换行
        gameTable.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());

        // 设置列宽
        gameTable.getColumnModel().getColumn(0).setPreferredWidth(300); // 内容列更宽
        gameTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        gameTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        // 设置行高
        gameTable.setRowHeight(60);

        JScrollPane jp = new JScrollPane(gameTable);
        reviewPanel.add(jp, BorderLayout.CENTER);
        pReview.add(reviewPanel, BorderLayout.CENTER);

        // 填充数据
        tableModel.setRowCount(0);
        List<Review> reviews = rs.getReviews(game);
        for (Review r : reviews) {
            tableModel.addRow(new Object[]{
                    r.getContent(),
                    r.getAuthor(),
                    r.getTime(),
            });
        }
    }

    // 多行单元格渲染器
    private static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setFont(table.getFont());
            setText((value == null) ? "" : value.toString());

            // 根据内容调整行高
            int lineHeight = getFontMetrics(getFont()).getHeight();
            int textLength = getText().length();
            int rows = (textLength / 40) + 1; // 每行大约40个字符
            table.setRowHeight(row, lineHeight * rows);

            return this;
        }
    }

    public static void main(String[] args) {
        Game g = new Game("艾尔登法环", "动作角色扮演", BigDecimal.valueOf(298));
        g.setOverview("《艾尔登法环》是以正统黑暗奇幻世界为舞台的动作RPG游戏。走进辽阔的场景与地下迷宫探索未知，挑战困难重重的险境，享受克服困境时的成就感吧。");
        User u = new User(111, "test", "123");
        new GameDetailsFrame(u,g);
    }
}