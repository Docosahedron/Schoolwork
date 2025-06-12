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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class GameDetailsFrame extends JFrame {
    private final Game curGame;
    private final User curUser;
    boolean flag1;
    boolean flag2;
    UserSerImpl us = new UserSerImpl();
    ReviewSerImpl rs = new ReviewSerImpl();
    WishlistSerImpl ws = new WishlistSerImpl();
    WarehouseSerImpl whs = new WarehouseSerImpl();
    JPanel pGame = new JPanel();
    JPanel pReview = new JPanel();
    private JPanel titleBar;
    private JLabel titleLabel;
    private JButton closeButton;
    private Point initialClick;

    public GameDetailsFrame(User user, Game game) {
        setUndecorated(true);
        Font chineseFont = new Font("Microsoft YaHei", Font.PLAIN, 14);
        UIManager.put("Label.font", chineseFont);
        UIManager.put("Button.font", chineseFont);
        UIManager.put("TextField.font", chineseFont);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(18, 18, 22));
        setContentPane(contentPane);

        // Title bar
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(30, 30, 36));
        titleBar.setPreferredSize(new Dimension(getWidth(), 40));
        titleBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 60, 60)));

        titleLabel = new JLabel(game.getName() + " 游戏详情页", JLabel.LEFT);
        titleLabel.setForeground(new Color(200, 200, 200));
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        closeButton = new JButton("×");
        closeButton.setForeground(new Color(200, 200, 200));
        closeButton.setBackground(new Color(30, 30, 36));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(true);
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(new Color(255, 59, 59));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(30, 30, 36));
            }
        });
        closeButton.addActionListener(e -> System.exit(0));

        titleBar.add(titleLabel, BorderLayout.WEST);
        titleBar.add(closeButton, BorderLayout.EAST);
        contentPane.add(titleBar, BorderLayout.NORTH);

        // Content panel with vertical BoxLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(24, 24, 28));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Gradient image
        String imagePath = "src/main/resources/images/gameDetails/艾尔登法环.jpg";
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            originalImage = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB);
        }

        Image scaledImage = originalImage.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        BufferedImage scaledBufferedImage = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledBufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(24, 24, 28, 0),
                0, 300, new Color(24, 24, 28, 220),
                false
        );
        g2d.setPaint(gradient);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.fillRect(0, 0, 500, 300);
        g2d.dispose();

        JLabel imageLabel = new JLabel(new ImageIcon(scaledBufferedImage));
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imageLabel);
        contentPanel.add(Box.createVerticalStrut(15)); // Spacer

        contentPane.add(contentPanel, BorderLayout.CENTER);

        // Title bar dragging
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });

        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.curGame = game;
        this.curUser = user;
        this.flag1 = ws.queryAdded(curUser, curGame);
        this.flag2 = whs.queryBought(curUser, curGame);

        pGame(curGame);
        pReview(curGame);

        // Add panels with fixed sizes
        pGame.setMaximumSize(new Dimension(500, 250)); // Limit game panel height
        pGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(pGame);
        contentPanel.add(Box.createVerticalStrut(15)); // Spacer
        pReview.setMaximumSize(new Dimension(500, 200)); // Limit review panel height
        pReview.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(pReview);

        setVisible(true);
    }

    public void pGame(Game game) {
        pGame.setLayout(new GridBagLayout());
        pGame.setBackground(new Color(30, 30, 36));
        pGame.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        // Game info
        JLabel typeLabel = new JLabel("类型: " + game.getType());
        typeLabel.setForeground(new Color(200, 200, 200));
        typeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        pGame.add(typeLabel, gbc);

        JLabel scoreLabel = new JLabel("评分: " + game.getScore());
        scoreLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy++;
        pGame.add(scoreLabel, gbc);

        JLabel priceLabel = new JLabel("价格: " + game.getPrice());
        priceLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy++;
        pGame.add(priceLabel, gbc);

        JTextArea overview = new JTextArea("概述:\n" + game.getOverview());
        overview.setEditable(false);
        overview.setLineWrap(true);
        overview.setWrapStyleWord(true);
        overview.setBackground(new Color(30, 30, 36));
        overview.setForeground(new Color(180, 180, 180));
        overview.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        overview.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        overview.setPreferredSize(new Dimension(300, 80));
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pGame.add(overview, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 36));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.EAST;

        JButton addWishlist = new JButton(flag1 ? "移出心愿单" : "加入心愿单");
        styleButton(addWishlist);
        buttonPanel.add(addWishlist);

        JButton buy = new JButton(flag2 ? "已购买!" : "购买");
        styleButton(buy);
        buttonPanel.add(buy);

        pGame.add(buttonPanel, gbc);

        addWishlist.addActionListener(e -> {
            if (flag1) flag1 = !ws.removeSelected(this.curUser, this.curGame);
            else flag1 = ws.addWishlist(this.curUser, this.curGame);
            addWishlist.setText(flag1 ? "移出心愿单" : "加入心愿单");
        });

        buy.addActionListener(e -> {
            if (flag2) JOptionPane.showMessageDialog(null, "已拥有,无法购买!");
            else flag2 = us.buy(curUser, curGame);
            buy.setText(flag2 ? "已购买!" : "购买");
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(63, 81, 181));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 100, 200));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 120, 220), 1),
                        BorderFactory.createEmptyBorder(9, 19, 9, 19)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(63, 81, 181));
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        });
    }

    public void pReview(Game game) {
        pReview.setLayout(new BorderLayout());
        pReview.setBackground(new Color(30, 30, 36));
        pReview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setBackground(new Color(30, 30, 36));
        reviewPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                "游戏评论",
                0,
                0,
                new Font("Microsoft YaHei", Font.BOLD, 14),
                new Color(200, 200, 200)
        ));

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("评论内容");
        tableModel.addColumn("评论用户");
        tableModel.addColumn("评论时间");

        JTable gameTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gameTable.setBackground(new Color(30, 30, 36));
        gameTable.setForeground(new Color(200, 200, 200));
        gameTable.setGridColor(new Color(60, 60, 60));
        gameTable.setSelectionBackground(new Color(50, 50, 56));
        gameTable.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());

        gameTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        gameTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        gameTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        gameTable.setRowHeight(60);

        JScrollPane jp = new JScrollPane(gameTable);
        jp.setBorder(BorderFactory.createEmptyBorder());
        jp.getViewport().setBackground(new Color(30, 30, 36));
        jp.setPreferredSize(new Dimension(460, 150)); // Fixed height for table
        reviewPanel.add(jp, BorderLayout.CENTER);
        pReview.add(reviewPanel, BorderLayout.CENTER);

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

    private static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setBackground(new Color(30, 30, 36));
            setForeground(new Color(200, 200, 200));
            setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(new Color(50, 50, 56));
            } else {
                setForeground(new Color(200, 200, 200));
                setBackground(new Color(30, 30, 36));
            }
            setFont(table.getFont());
            setText((value == null) ? "" : value.toString());

            int lineHeight = getFontMetrics(getFont()).getHeight();
            int textLength = getText().length();
            int rows = (textLength / 40) + 1;
            table.setRowHeight(row, lineHeight * rows);

            return this;
        }
    }

    public static void main(String[] args) {
        Game g = new Game("艾尔登法环", "动作角色扮演", 298);
        g.setOverview("《艾尔登法环》是以正统黑暗奇幻世界为舞台的动作RPG游戏。走进辽阔的场景与地下迷宫探索未知，挑战困难重重的险境，享受克服困境时的成就感吧。");
        User u = new User(111, "test", "123");
        new GameDetailsFrame(u, g);
    }
}