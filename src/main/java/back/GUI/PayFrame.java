/*支付界面*/
package back.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayFrame extends JFrame {
        public PayFrame() {
            initFrame();
            initButton();
            initText();
        }

    private void initText() {
        JLabel mainTitle = new JLabel("选择您的支付方式:");
        Font fontTitle = new Font("宋体", Font.PLAIN, 20);
        mainTitle.setFont(fontTitle);
        mainTitle.setBounds(10, 20, 300, 20);
        add(mainTitle);

    }

    private void initButton() {
        JButton zfbBut = new JButton("支付宝");
        JButton wxBut = new JButton("微信");
        zfbBut.setBounds(20,100,150, 80);
        wxBut.setBounds(220, 100, 150, 80);
        add(zfbBut);
        add(wxBut);
        zfbBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel zfb = new JLabel(new ImageIcon("image/wallet/zfb.jpg"));
                JOptionPane.showMessageDialog(PayFrame.this, zfb);
            }
        });
        wxBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel wx = new JLabel(new ImageIcon("image/wallet/wx.png"));
                JOptionPane.showMessageDialog(PayFrame.this, wx);
            }
        });
    }

    private void initFrame() {
        this.setTitle("支付界面");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setLayout(null);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new PayFrame();
    }
}
