package work_demo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class WindowInitial extends JFrame implements ActionListener
{
    JPanel panel;
    JTextField text;
    JMenuBar bar;
    JMenu menu;
    JMenuItem itemInitial , itemChange;
    FlowLayout layout = new FlowLayout();
    WindowChange change;
    WindowInitial(){
        init();
        setBounds(100,100,200,200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void init(){
        bar = new JMenuBar();
        menu = new JMenu("菜单");
        itemInitial = new JMenuItem("初始");
        itemChange = new JMenuItem("后来");
        panel = new JPanel();
        text = new JTextField(10);
        change = new WindowChange();
        text.setText("切换前的界面");
        text.setEditable(false);
        itemInitial.addActionListener(this);
        itemChange.addActionListener(this);
        bar.add(menu);
        menu.add(itemInitial);
        menu.add(itemChange);
        panel.setLayout(layout);
        add(panel);
        panel.add(text);
        setJMenuBar(bar);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == itemChange){//改变图形化界面为“后来”的界面。
            panel.removeAll();
            panel.add("后来" , change);//切换代码。
            panel.validate();
            repaint();
        }
        else if(e.getSource() == itemInitial){//改变图形化界面为“初始”界面。
            panel.removeAll();
            panel.add(text);
            panel.validate();
            repaint();
        }
    }
}

class WindowChange extends JPanel
{
    JTextField text;
    WindowChange(){
        text = new JTextField(10);
        text.setText("切换后的界面");
        text.setEditable(false);
        add(text);
    }
}
class WindowTest
{
    public static void main(String args[]){
        WindowInitial wi = new WindowInitial();
        wi.setTitle("界面切换");
    }
}