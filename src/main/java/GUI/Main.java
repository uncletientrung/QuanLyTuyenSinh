/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import GUI.Component.MenuTaskbar;
import GUI.Panel.TrangChuPanel;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DELL
 */
public class Main extends  JFrame{
    public JPanel MainContent;
    private MenuTaskbar menuTaskbar;
    Color MainColor = new Color(250, 250, 250);
    public Main(){
        setupLAF();
        initComponent();
    }
     // Setup Look And Feel
    private void setupLAF() {
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.setup();
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("Table.selectionBackground", new Color(240, 247, 250));
        UIManager.put("Table.scrollPaneBorder", new EmptyBorder(0, 0, 0, 0));
        UIManager.put("Table.rowHeight", 40);
        UIManager.put("TableHeader.height", 40);
        UIManager.put("TableHeader.background", new Color(242, 242, 242));
    }
    
    private void initComponent(){
        this.setSize(new Dimension(1400, 800));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Hệ thống quản lý tuyển sinh");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // MENU
        menuTaskbar = new MenuTaskbar(this);
        menuTaskbar.setPreferredSize(new Dimension(250, 1400));
        this.add(menuTaskbar, BorderLayout.WEST);
        
        // MAIN CONTENT
        MainContent = new JPanel();
        MainContent.setBackground(MainColor);
        MainContent.setLayout(new BorderLayout(0, 0));
        this.add(MainContent, BorderLayout.CENTER);
        
        // TRANG CHỦ MẶC ĐỊNH
        this.setPanel(new TrangChuPanel());
    }
    public void setPanel(JPanel pn) { // Tạo Panel Trung tâm
        MainContent.removeAll();
        MainContent.add(pn, BorderLayout.CENTER);
        MainContent.repaint();
        MainContent.revalidate();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true); 
        });
    }
    
    
    
}
