package work_demo.GUI;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminFrame extends JFrame implements ActionListener {
    GameSer gs = new GameSer();
    JMenuBar menuBar =new JMenuBar();
    JMenu homePage = new JMenu("首页");
    JMenuItem home = new JMenuItem("首页");
    JMenu goodsOp = new JMenu("商 品 操 作");
    JMenuItem add = new JMenuItem("添加商品");
    JMenuItem del = new JMenuItem("删除商品");
    JMenuItem update = new JMenuItem("修改商品");
    JMenuItem query = new JMenuItem("查询商品");

    JMenu accountOp = new JMenu("账 号 操 作");
    JMenuItem lock = new JMenuItem("锁定");
    JMenuItem exit = new JMenuItem("退出");
    JPanel p =new JPanel();
    public AdminFrame() {
        super("管理员界面");
        init();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,618);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==home){

        } else if (e.getSource()==add) {
            new GameAddFrame();
        }else if (e.getSource()==del) {

        }else if (e.getSource()==update) {

        }else if (e.getSource()==query) {

        }else if (e.getSource()==lock) {
            dispose();
            new loginFrame();
        }
        else if (e.getSource()==exit) {
            dispose();
        }
    }
    public void init() {
        p.setLayout(null);

        //添加组件
        p.add(menuBar);
        menuBar.add(homePage);
        menuBar.add(goodsOp);
        menuBar.add(accountOp);
        //
        homePage.add(home);
        //
        goodsOp.add(add);
        goodsOp.add(del);
        goodsOp.add(update);
        goodsOp.add(query);
        //
        accountOp.add(lock);
        accountOp.add(exit);
        //首页
        home.addActionListener(this);

        add.addActionListener(this);
        del.addActionListener(this);
        update.addActionListener(this);
        query.addActionListener(this);
        //退出管理员界面

        lock.addActionListener(this);
        exit.addActionListener(this);
        setJMenuBar(menuBar);
        menuBar.setVisible(true);
    }
    public static void main(String[] args) {
        new AdminFrame();
    }
}
