package back.GUI;
import back.ENTITY.*;
import back.DaoImpl.*;
import javax.swing.*;

public class GameAddFrame extends JFrame  {
    GameDaoImpl gdi = new GameDaoImpl();
    JLabel name = new JLabel("名称:");
    JLabel type = new JLabel("类型:");
    JLabel price = new JLabel("价格:");
    JLabel num = new JLabel("数量:");
    JTextField nameIn = new JTextField();
    JTextField priceIn = new JTextField();
    JTextField typeIn = new JTextField();
    JTextField numIn = new JTextField();
    JButton confirm = new JButton("确认");
    JPanel p  =new JPanel();
    public GameAddFrame() {
        super("添加商品");
        init();

    }
    public void init() {
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
        confirm.addActionListener(e -> {
            Game game = new Game(nameIn.getText(),
                    typeIn.getText(),
                    Double.parseDouble(priceIn.getText()),
                    Integer.parseInt(numIn.getText()));
            if (gdi.add(game)) {
                JOptionPane.showMessageDialog(null, "添加成功!");
                dispose();
            }else
                JOptionPane.showMessageDialog(null, "添加失败!");
        });

        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 300, 250, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new GameAddFrame();
    }
}
