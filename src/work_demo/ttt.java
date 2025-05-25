//package work_demo;
//
//import work_demo.ENTITY.Game;
//import work_demo.SERVICE.GameSer;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionListener;
//
//class AdminFrame extends JFrame implements ActionListener {
//    private JTable gameTable;
//    private DefaultTableModel tableModel;
//
//    public void init() {
//        p.setLayout(new BorderLayout());
//
//        // 初始化表格
//        tableModel = new DefaultTableModel();
//        tableModel.addColumn("ID");
//        tableModel.addColumn("游戏名称");
//        tableModel.addColumn("价格");
//        tableModel.addColumn("分类");
//
//        gameTable = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(gameTable);
//        p.add(scrollPane, BorderLayout.CENTER);
//
//        loadGameData();
//
//        // 菜单栏代码...
//    }
//
//    private void loadGameData() {
//        GameSer gs=new GameSer();
//        List games = gs.getAllGames();
//        tableModel.setRowCount(0);
//
//        for (Game game : games) {
//            tableModel.addRow(new Object[]{
//                    game.getName(),
//                    game.getPrice(),
//                    game.getType(),
//                    game.getNum()
//            });
//        }
//    }
//}