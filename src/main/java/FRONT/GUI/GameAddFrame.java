package FRONT.GUI;
import BACK.Entity.*;
import BACK.Service.SerImpl.GameSerImpl;

import javax.swing.*;

public class GameAddFrame extends JFrame {
    GameSerImpl gs = new GameSerImpl();
    AdminFrame parent; // 添加对 AdminFrame 的引用
    JLabel name = new JLabel("名称:");
    JLabel type = new JLabel("类型:");
    JLabel price = new JLabel("价格:");
    JLabel num = new JLabel("数量:");
    JTextField nameIn = new JTextField();
    JTextField priceIn = new JTextField();
    JTextField typeIn = new JTextField();
    JTextField numIn = new JTextField();
    JButton confirm = new JButton("确认");
    JPanel p = new JPanel();

    public GameAddFrame(AdminFrame parent) {
        super("添加商品");
        this.parent = parent; // 保存父窗口引用
        init();
    }

    public boolean init() {
        p.setLayout(null);
        name.setBounds(10, 10, 50, 30);
        nameIn.setBounds(100, 10, 100, 30);
        type.setBounds(10, 90, 50, 30);
        typeIn.setBounds(100, 90, 100, 30);
        price.setBounds(10, 50, 50, 30);
        priceIn.setBounds(100, 50, 100, 30);
        num.setBounds(10, 130, 50, 30);
        numIn.setBounds(100, 130, 100, 30);
        confirm.setBounds(50, 180, 100, 30);

        p.add(name);
        p.add(price);
        p.add(type);
        p.add(num);
        p.add(nameIn);
        p.add(priceIn);
        p.add(typeIn);
        p.add(numIn);
        p.add(confirm);

        confirm.addActionListener(e -> saveGame());

        this.add(p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 改为 DISPOSE_ON_CLOSE
        this.setBounds(300, 300, 250, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        return true; // 初始化成功
    }

    private void saveGame() {
        // 获取并清理输入
        String name = nameIn.getText().trim();
        String type = typeIn.getText().trim();
        String priceStr = priceIn.getText().trim();
        String numStr = numIn.getText().trim();

        // 验证输入
        if (name.isEmpty() || type.isEmpty() || priceStr.isEmpty() || numStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int num = Integer.parseInt(numStr);

            // 创建 Game 对象
            Game game = new Game(name, type, price);
            if (gs.addGame(game)) {
                JOptionPane.showMessageDialog(this, "添加成功！");
                parent.loadData(); // 刷新父窗口表格
                dispose(); // 关闭窗口
            } else {
                JOptionPane.showMessageDialog(this, "添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "价格或数量格式错误，请输入有效数字！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new GameAddFrame(null); // 测试用，实际使用时传递 AdminFrame
    }
}