package work_demo.GUI;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;

public class GameAddFrame extends JFrame {
    private final GameSer gs = new GameSer();
    private final AdminFrame parent; // 添加对 AdminFrame 的引用
    private final JLabel name = new JLabel("名称:");
    private final JLabel type = new JLabel("类型:");
    private final JLabel price = new JLabel("价格:");
    private final JLabel num = new JLabel("数量:");
    private final JTextField nameIn = new JTextField();
    private final JTextField priceIn = new JTextField();
    private final JTextField typeIn = new JTextField();
    private final JTextField numIn = new JTextField();
    private final JButton confirm = new JButton("确认");
    private final JPanel p = new JPanel();

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
            Game game = new Game(name, type, price, num);
            if (gs.add(game)) {
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