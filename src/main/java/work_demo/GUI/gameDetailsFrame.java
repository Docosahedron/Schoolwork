package work_demo.GUI;

import work_demo.ENTITY.*;
import work_demo.DaoImpl.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class gameDetailsFrame extends JFrame {
    // ... 其他成员变量保持不变 ...
    private final Game curGame;
    private final User curUser;
    GameDaoImpl gdi =new GameDaoImpl();
    ReviewDaoImpl rdi =new ReviewDaoImpl();
    JPanel pGame = new JPanel();
    JPanel pReview = new JPanel();
    public gameDetailsFrame(Game game, User user) {
        super(game.getName());
        this.curGame = game;
        this.curUser = user;

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

        gbc.gridy++;
        pGame.add(new JLabel("库存: " + game.getNum()), gbc);

        // 概述 - 使用JTextArea支持多行
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
        JButton addWishlist = new JButton("加入心愿单");
        pGame.add(addWishlist, gbc);

        gbc.gridy++;
        JButton buy = new JButton("购买");
        pGame.add(buy, gbc);

        // 按钮事件监听保持不变
        addWishlist.addActionListener(e->{
            boolean flag= gdi.addWishlist(this.curGame,this.curUser);
            if(flag)JOptionPane.showMessageDialog(null,"添加成功!");
            else JOptionPane.showMessageDialog(null,"添加失败!");
        });

        buy.addActionListener(e->{
            int flag=JOptionPane.showConfirmDialog(null,"是否确认购买?");
            if(flag==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null,"购买成功!");
            }
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
        List<Review> reviews = rdi.getReviews(game.getName());
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
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
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
        Game g = new Game("艾尔登法环", "动作角色扮演", 298, 1);
        g.setOverview("《艾尔登法环》是以正统黑暗奇幻世界为舞台的动作RPG游戏。走进辽阔的场景与地下迷宫探索未知，挑战困难重重的险境，享受克服困境时的成就感吧。");
        User u = new User(111, "test", "123");
        new gameDetailsFrame(g, u);
    }
}