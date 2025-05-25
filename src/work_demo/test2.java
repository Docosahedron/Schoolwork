package work_demo;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    private JPanel searchPanel;
    private JPanel dataPanel;

    public MainFrame() {
        // 设置窗口标题和大小
        setTitle("综合应用程序");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建菜单栏
        createMenuBar();

        // 创建搜索面板
        createSearchPanel();

        // 创建数据显示面板
        createDataPanel();

        // 设置布局并添加组件
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        JMenuItem openItem = new JMenuItem("打开");
        JMenuItem saveItem = new JMenuItem("保存");
        JMenuItem exitItem = new JMenuItem("退出");

        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // 编辑菜单
        JMenu editMenu = new JMenu("编辑");
        JMenuItem cutItem = new JMenuItem("剪切");
        JMenuItem copyItem = new JMenuItem("复制");
        JMenuItem pasteItem = new JMenuItem("粘贴");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutItem = new JMenuItem("关于");

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "这是一个示例应用程序", "关于", JOptionPane.INFORMATION_MESSAGE));

        helpMenu.add(aboutItem);

        // 将菜单添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        // 设置菜单栏
        setJMenuBar(menuBar);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("搜索栏"));
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // 添加搜索组件
        JLabel searchLabel = new JLabel("搜索:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");

        // 搜索按钮事件处理
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            JOptionPane.showMessageDialog(this,
                    "正在搜索: " + searchText,
                    "搜索",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
    }

    private void createDataPanel() {
        dataPanel = new JPanel();
        dataPanel.setBorder(BorderFactory.createTitledBorder("数据展示"));
        dataPanel.setLayout(new BorderLayout());

        // 创建表格数据
        String[] columnNames = {"ID", "名称", "描述", "状态"};
        Object[][] data = {
                {1, "项目1", "这是第一个项目", "进行中"},
                {2, "项目2", "第二个项目描述", "已完成"},
                {3, "项目3", "第三个项目的详细信息", "待开始"}
        };

        // 创建表格
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // 添加表格到数据面板
        dataPanel.add(scrollPane, BorderLayout.CENTER);

        // 添加底部按钮面板
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("刷新");
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");

        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        dataPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}