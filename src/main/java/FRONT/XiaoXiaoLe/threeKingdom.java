package FRONT.XiaoXiaoLe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threeKingdom extends JFrame {
    private static final int ROWS = 9;
    private static final int COLS = 9;
    private static final int TYPES = 8; // 元素种类
    private JButton[][] buttons = new JButton[ROWS][COLS];
    private int[][] board = new int[ROWS][COLS];
    private int selectedRow = -1, selectedCol = -1;
    private Random random = new Random();
    private ImageIcon[] icons = new ImageIcon[TYPES];
    private JLayeredPane layeredPane;
    private JPanel gridPanel;
    private boolean animating = false;
    private boolean[][] isPower = new boolean[ROWS][COLS];
    
    // 计时系统相关变量
    private Timer gameTimer;
    private int timeRemaining = 60; // 默认60秒
    private JLabel timeLabel;
    private JPanel statusPanel;
    
    // 分数系统相关变量
    private int score = 0;
    private JLabel scoreLabel;
    
    // 性能优化相关
    private ExecutorService animationExecutor = Executors.newSingleThreadExecutor();
    private final int ANIMATION_SPEED = 15; // 动画速度，值越小越快
    private ImageIcon[][] cachedIcons = new ImageIcon[TYPES][2]; // 缓存图标 [类型][是否强化]

    public threeKingdom() {
        setTitle("三国消消乐");
        setSize(630, 680); // 增加高度以容纳状态栏
        
        // 创建状态面板
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setPreferredSize(new Dimension(600, 30));
        
        // 时间标签放在左侧
        timeLabel = new JLabel("剩余时间: 60秒", JLabel.LEFT);
        timeLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        // 分数标签放在右侧
        scoreLabel = new JLabel("分数: 0", JLabel.RIGHT);
        scoreLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(timeLabel);
        infoPanel.add(scoreLabel);
        statusPanel.add(infoPanel, BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(statusPanel, BorderLayout.NORTH);
        
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        mainPanel.add(layeredPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        
        loadIcons();
        gridPanel = new JPanel(new GridLayout(ROWS, COLS));
        gridPanel.setBounds(0, 0, 600, 600);
        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        initBoard();
        initTimer();
        setVisible(true);
        
        // 关闭时清理线程池
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animationExecutor.shutdown();
            }
        });
    }
    
    private void initTimer() {
        gameTimer = new Timer(1000, e -> {
            timeRemaining--;
            timeLabel.setText("剩余时间: " + timeRemaining + "秒");
            
            if (timeRemaining <= 0) {
                gameTimer.stop();
                setButtonsEnabled(false);
                JOptionPane.showMessageDialog(this, "游戏结束！\n最终得分: " + score, "时间到", JOptionPane.INFORMATION_MESSAGE);
                int option = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？", "游戏结束", 
                                                         JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            }
        });
        gameTimer.start();
    }
    
    private void resetGame() {
        timeRemaining = 60;
        timeLabel.setText("剩余时间: " + timeRemaining + "秒");
        score = 0;
        scoreLabel.setText("分数: " + score);
        initBoard();
        gameTimer.start();
    }

    private void loadIcons() {
        for (int i = 0; i < TYPES; i++) {
            String path = "src/main/java/FRONT/XiaoXiaoLe/images/" + (i + 1) + ".png";
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("图片不存在: " + path);
                BufferedImage img = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(0, 0, 60, 60);
                g2.setColor(Color.BLACK);
                g2.drawString("?", 25, 35);
                g2.dispose();
                icons[i] = new ImageIcon(img);
            } else {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                icons[i] = new ImageIcon(img);
            }
            
            // 预先生成并缓存普通和强化图标
            cachedIcons[i][0] = icons[i];
            cachedIcons[i][1] = createPowerIcon(icons[i]);
        }
    }
    
    private ImageIcon createPowerIcon(ImageIcon baseIcon) {
        Image base = baseIcon.getImage();
        int w = base.getWidth(null), h = base.getHeight(null);
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.drawImage(base, 0, 0, null);
        // 发光圈
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int r = 0; r < 6; r++) {
            float alpha = 0.15f * (6 - r);
            g2.setColor(new Color(255, 215, 0, (int)(255 * alpha)));
            g2.setStroke(new BasicStroke(6 - r));
            g2.drawOval(3 - r, 3 - r, w - 6 + 2*r, h - 6 + 2*r);
        }
        g2.dispose();
        return new ImageIcon(img);
    }

    private void initBoard() {
        while (true) {
            gridPanel.removeAll();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    int type;
                    do {
                        type = random.nextInt(TYPES);
                    } while ((j >= 2 && type == board[i][j-1] && type == board[i][j-2]) ||
                             (i >= 2 && type == board[i-1][j] && type == board[i-2][j]));
                    board[i][j] = type;
                    isPower[i][j] = random.nextDouble() < 0.1; // 10%概率
                    JButton btn = new JButton();
                    btn.setIcon(getIcon(board[i][j], isPower[i][j]));
                    btn.setBorder(null);
                    btn.setContentAreaFilled(false);
                    btn.addActionListener(new ButtonListener(i, j));
                    buttons[i][j] = btn;
                    gridPanel.add(btn);
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
            // 检查是否有可消除
            if (!hasAnyClear()) break;
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buttons[i][j].setEnabled(enabled);
            }
        }
    }

    // 动画交换
    private void animateSwap(int r1, int c1, int r2, int c2, Runnable after) {
        // 动画前清理所有动画层label
        for (Component comp : layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)) {
            if (comp instanceof JLabel) layeredPane.remove(comp);
        }
        layeredPane.repaint();
        animating = true;
        setButtonsEnabled(false);
        int size = 600 / ROWS;
        // 动画开始时将两个按钮设为null
        buttons[r1][c1].setIcon(null);
        buttons[r2][c2].setIcon(null);
        JLabel label1 = new JLabel(getIcon(board[r1][c1], isPower[r1][c1]));
        JLabel label2 = new JLabel(getIcon(board[r2][c2], isPower[r2][c2]));
        int x1 = c1 * size, y1 = r1 * size;
        int x2 = c2 * size, y2 = r2 * size;
        label1.setBounds(x1, y1, size, size);
        label2.setBounds(x2, y2, size, size);
        layeredPane.add(label1, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(label2, JLayeredPane.PALETTE_LAYER);
        
        animationExecutor.execute(() -> {
            int steps = 15;
            for (int count = 0; count <= steps; count++) {
                final int currentCount = count;
                double t = currentCount / (double)steps;
                int nx1 = (int)(x1 + (x2 - x1) * t);
                int ny1 = (int)(y1 + (y2 - y1) * t);
                int nx2 = (int)(x2 + (x1 - x2) * t);
                int ny2 = (int)(y2 + (y1 - y2) * t);
                
                SwingUtilities.invokeLater(() -> {
                    label1.setLocation(nx1, ny1);
                    label2.setLocation(nx2, ny2);
                });
                
                try {
                    Thread.sleep(ANIMATION_SPEED);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                layeredPane.remove(label1);
                layeredPane.remove(label2);
                layeredPane.repaint();
                animating = false;
                setButtonsEnabled(true);
                after.run();
                updateBoard();
            });
        });
    }

    // 下落动画
    private void animateDrop(int[][] fromTo, Runnable after) {
        animating = true;
        setButtonsEnabled(false);
        int size = 600 / ROWS;
        JLabel[] labels = new JLabel[fromTo.length];
        int[] startY = new int[fromTo.length];
        int[] endY = new int[fromTo.length];
        int[] x = new int[fromTo.length];
        // 动画开始时将所有目标格设为null
        for (int i = 0; i < fromTo.length; i++) {
            int fromRow = fromTo[i][0], col = fromTo[i][1], toRow = fromTo[i][2];
            buttons[toRow][col].setIcon(null);
        }
        
        animationExecutor.execute(() -> {
            // 动画层用源格子的图片
            SwingUtilities.invokeLater(() -> {
                for (Component comp : layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)) {
                    if (comp instanceof JLabel) layeredPane.remove(comp);
                }
                layeredPane.repaint();
                
                for (int i = 0; i < fromTo.length; i++) {
                    int fromRow = fromTo[i][0], col = fromTo[i][1], toRow = fromTo[i][2];
                    labels[i] = new JLabel(getIcon(board[toRow][col], isPower[toRow][col]));
                    x[i] = col * size;
                    startY[i] = (fromRow == -1 ? -size : fromRow * size);
                    endY[i] = toRow * size;
                    labels[i].setBounds(x[i], startY[i], size, size);
                    layeredPane.add(labels[i], JLayeredPane.PALETTE_LAYER);
                }
            });
            
            int steps = 15;
            for (int count = 0; count <= steps; count++) {
                final int currentCount = count;
                double t = currentCount / (double)steps;
                
                SwingUtilities.invokeLater(() -> {
                    for (int i = 0; i < fromTo.length; i++) {
                        int ny = (int)(startY[i] + (endY[i] - startY[i]) * t);
                        labels[i].setLocation(x[i], ny);
                    }
                });
                
                try {
                    Thread.sleep(ANIMATION_SPEED);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                for (JLabel label : labels) layeredPane.remove(label);
                layeredPane.repaint();
                animating = false;
                setButtonsEnabled(true);
                after.run();
                updateBoard();
            });
        });
    }

    private void swap(int r1, int c1, int r2, int c2) {
        int temp = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = temp;
        boolean p = isPower[r1][c1];
        isPower[r1][c1] = isPower[r2][c2];
        isPower[r2][c2] = p;
    }

    private void updateBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == -1) {
                    buttons[i][j].setIcon(null);
                } else {
                    buttons[i][j].setIcon(getIcon(board[i][j], isPower[i][j]));
                }
            }
        }
    }

    private boolean checkAndClear() {
        boolean[][] toClear = new boolean[ROWS][COLS];
        boolean found = false;
        // 横向
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 2; j++) {
                int t = board[i][j];
                if (t != -1 && t == board[i][j+1] && t == board[i][j+2] && board[i][j+1] != -1 && board[i][j+2] != -1) {
                    markPowerClear(i, j, toClear);
                    markPowerClear(i, j+1, toClear);
                    markPowerClear(i, j+2, toClear);
                    found = true;
                }
            }
        }
        // 纵向
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS - 2; i++) {
                int t = board[i][j];
                if (t != -1 && t == board[i+1][j] && t == board[i+2][j] && board[i+1][j] != -1 && board[i+2][j] != -1) {
                    markPowerClear(i, j, toClear);
                    markPowerClear(i+1, j, toClear);
                    markPowerClear(i+2, j, toClear);
                    found = true;
                }
            }
        }
        // 清除
        int clearCount = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (toClear[i][j]) {
                    board[i][j] = -1;
                    isPower[i][j] = false;
                    clearCount++;
                }
            }
        }
        
        // 计算得分和奖励时间
        if (clearCount > 0) {
            // 基础分：每个方块10分
            int baseScore = clearCount * 10;
            // 连锁反应奖励：每次消除超过5个方块，额外奖励
            int comboBonus = clearCount > 5 ? (clearCount - 5) * 5 : 0;
            int totalScore = baseScore + comboBonus;
            
            score += totalScore;
            scoreLabel.setText("分数: " + score);
            
            // 每消除一组方块奖励1秒
            timeRemaining += clearCount / 3;
            timeLabel.setText("剩余时间: " + timeRemaining + "秒");
        }
        
        updateBoard();
        return found;
    }

    private void dropAndFillWithAnimation(Runnable after) {
        // 动画前清理所有动画层label
        for (Component comp : layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)) {
            if (comp instanceof JLabel) layeredPane.remove(comp);
        }
        layeredPane.repaint();
        java.util.List<int[]> drops = new java.util.ArrayList<>();
        // 记录每个目标格的源格（fromRow, col, toRow）
        for (int j = 0; j < COLS; j++) {
            int empty = ROWS - 1;
            for (int i = ROWS - 1; i >= 0; i--) {
                if (board[i][j] != -1) {
                    if (empty != i) {
                        drops.add(new int[]{i, j, empty});
                        // 移动方块
                        board[empty][j] = board[i][j];
                        isPower[empty][j] = isPower[i][j];
                        board[i][j] = -1;
                        isPower[i][j] = false;
                    }
                    empty--;
                }
            }
            while (empty >= 0) {
                int type = random.nextInt(TYPES);
                boolean power = random.nextDouble() < 0.1;
                drops.add(new int[]{-1, j, empty});
                board[empty][j] = type;
                isPower[empty][j] = power;
                empty--;
            }
        }
        
        if (drops.isEmpty()) {
            after.run();
        } else {
            animateDrop(drops.toArray(new int[0][0]), after);
        }
    }

    private class ButtonListener implements ActionListener {
        int row, col;
        public ButtonListener(int r, int c) {
            row = r; col = c;
        }
        public void actionPerformed(ActionEvent e) {
            if (animating) return;
            if (selectedRow == -1) {
                selectedRow = row;
                selectedCol = col;
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            } else {
                if ((Math.abs(selectedRow - row) + Math.abs(selectedCol - col)) == 1) {
                    int r1 = selectedRow, c1 = selectedCol, r2 = row, c2 = col;
                    animateSwap(r1, c1, r2, c2, () -> {
                        swap(r1, c1, r2, c2);
                        if (checkAndClear()) {
                            doDropAndClear();
                        } else {
                            animateSwap(r1, c1, r2, c2, () -> {
                                swap(r1, c1, r2, c2);
                            }); // 无法消除则换回
                        }
                    });
                }
                buttons[selectedRow][selectedCol].setBorder(null);
                selectedRow = selectedCol = -1;
            }
        }
    }

    private void doDropAndClear() {
        dropAndFillWithAnimation(() -> {
            if (checkAndClear()) {
                doDropAndClear();
            }
        });
    }

    private ImageIcon getIcon(int type, boolean power) {
        if (type < 0 || type >= TYPES) return null;
        return cachedIcons[type][power ? 1 : 0];
    }

    // 检查当前棋盘是否有可消除（包括强化方块）
    private boolean hasAnyClear() {
        boolean[][] toClear = new boolean[ROWS][COLS];
        // 横向
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 2; j++) {
                int t = board[i][j];
                if (t != -1 && t == board[i][j+1] && t == board[i][j+2] && board[i][j+1] != -1 && board[i][j+2] != -1) {
                    boolean power = isPower[i][j] || isPower[i][j+1] || isPower[i][j+2];
                    if (power) return true;
                    else return true;
                }
            }
        }
        // 纵向
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS - 2; i++) {
                int t = board[i][j];
                if (t != -1 && t == board[i+1][j] && t == board[i+2][j] && board[i+1][j] != -1 && board[i+2][j] != -1) {
                    boolean power = isPower[i][j] || isPower[i+1][j] || isPower[i+2][j];
                    if (power) return true;
                    else return true;
                }
            }
        }
        return false;
    }

    // 递归标记强化方块全行全列
    private void markPowerClear(int i, int j, boolean[][] toClear) {
        if (i < 0 || i >= ROWS || j < 0 || j >= COLS) return;
        if (toClear[i][j]) return;
        toClear[i][j] = true;
        if (isPower[i][j]) {
            for (int k = 0; k < ROWS; k++) markPowerClear(k, j, toClear);
            for (int k = 0; k < COLS; k++) markPowerClear(i, k, toClear);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(threeKingdom::new);
    }
} 