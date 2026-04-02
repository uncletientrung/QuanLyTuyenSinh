/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author DELL
 */
public class TrangChuPanel extends  JPanel{
    public TrangChuPanel(){
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                java.net.URL imgURL = TrangChuPanel.class.getResource("/img/dashboard.jpg");
                if (imgURL != null) {
                    ImageIcon imageIcon = new ImageIcon(imgURL);
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(245, 245, 245));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        centerPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("CHÀO MỪNG ĐẾN VỚI HỆ THỐNG", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBackground(new Color(60, 63, 65, 200));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setPreferredSize(new Dimension(0, 80));
        centerPanel.add(welcomeLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
