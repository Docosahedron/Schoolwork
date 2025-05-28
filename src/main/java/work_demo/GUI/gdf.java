package work_demo.GUI;
import work_demo.ENTITY.*;
import work_demo.SERVICE.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class gdf extends JFrame{
    private final Game curGame;
    private final User curUser;
    GameSer gs =new GameSer();
    ReviewSer rs =new ReviewSer();
    JPanel pGame = new JPanel();
    JPanel pReview = new JPanel();

    public gdf(Game curGame, User user) {
        super(curGame.getName());
        this.curGame = curGame;
        this.curUser = user;
        pGame(curGame);
        pReview(curGame);
        this.setLayout(new BorderLayout());
        this.add(pGame, BorderLayout.CENTER);
        this.add(pReview, BorderLayout.SOUTH);
        this.setBounds(100, 100, 450, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void pGame(Game game) {
        pGame.setLayout(null);
        JLabel type = new JLabel("类型: " + game.getType());
        JLabel score = new JLabel("评分: " + game.getScore());
        JLabel price = new JLabel("价格: " + game.getPrice());
        JLabel num = new JLabel("库存: " + game.getNum());
        JTextArea overview = new JTextArea("概述:\n " + game.getOverview());
        JButton addWishlist = new JButton("加入心愿单");
        JButton buy = new JButton("购买");
        type.setBounds(10, 10, 100, 30);
        score.setBounds(10, 40, 100, 30);
        price.setBounds(10, 70, 100, 30);
        overview.setBounds(10, 140, 200, 50);
        num.setBounds(10, 100, 100, 30);
        addWishlist.setBounds(200, 10, 100, 40);
        buy.setBounds(200, 70, 100, 40);
        pGame.add(type);
        pGame.add(score);
        pGame.add(price);
        pGame.add(num);
        pGame.add(overview);
        pGame.add(addWishlist);
        pGame.add(buy);
        addWishlist.addActionListener(e->{
            boolean flag=gs.addWishlist(this.curGame,this.curUser);
            if(flag)JOptionPane.showMessageDialog(null,"添加成功!");
            else JOptionPane.showMessageDialog(null,"添加失败!");
        });
        buy.addActionListener(e->{
            int flag=JOptionPane.showConfirmDialog( null,"是否确认购买?");
            if(flag==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null,"购买成功!");
            }
        });
        pGame.setVisible(true);
    }
    public void pReview(Game game) {
        pReview.setLayout(new BorderLayout());
        pReview.setPreferredSize(new Dimension(450, 250));
        JTable gameTable;
        DefaultTableModel tableModel;
        //商品数据显示
        tableModel = new DefaultTableModel();
        tableModel.addColumn("评论内容");
        tableModel.addColumn("评论用户");
        tableModel.addColumn("评论时间");
        gameTable=new JTable(tableModel);
        JScrollPane jp = new JScrollPane(gameTable);
        pReview.add(jp,BorderLayout.CENTER);
        tableModel.setRowCount(0);
        List<Review> reviews = rs.getReviews(game.getName());
        for (Review r:reviews){
            tableModel.addRow(new Object[]{
                    r.getContent(),
                    r.getAuthor(),
                    r.getTime(),
            });
        }
        pReview.setVisible(true);
    }

    public static void main(String[] args) {
        Game g = new Game("艾尔登法环", "1",1, 1);
        User u =new User(111, "test", "123");
        new gameDetailsFrame(g,u);
    }
}
