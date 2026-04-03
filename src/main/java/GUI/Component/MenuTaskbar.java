/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import GUI.Main;
import GUI.Panel.TaiKhoanPanel;
import GUI.Panel.ThiSinhPanel;
import GUI.Panel.TrangChuPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
    
/**
 *
 * @author DELL
 */
public class MenuTaskbar extends JPanel{
    String[][] ArrMenu = {
    {"Trang chủ", "home.svg", "trangChu"},
    {"Thí sinh", "home.svg", "thiSinh"},
    {"Điểm thi", "home.svg", "diemThi"},
    {"Nguyện vọng", "home.svg", "nguyenVong"},
    {"Khu vực", "home.svg", "khuvuc"},
    {"Môn", "home.svg", "mon"},
    {"Ngành", "home.svg", "nganh"},
    {"Ngành - Tổ hợp", "home.svg", "nganhToHop"},
    {"Bảng quy đổi", "home.svg", "bangQuyDoi"},
    {"Điểm cộng", "home.svg", "diemCong"},
    {"Người dùng", "home.svg", "nguoiDung"},
    {"Nhóm quyền", "home.svg", "nhomQuyen"},
    {"Thống kê", "home.svg", "thongKe"},
    {"Đăng xuất", "logout.svg", "dangXuat"}
    };
    private Main main;
    private itemTaskbar[] listItem;
    JScrollPane scrollPane;
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;
    Color DefaultColor = new Color(255, 255, 255);
    Color LineColor = new Color(204, 214, 219); // Màu xanh trắng
    
    public MenuTaskbar(Main m){
        this.main = m;
        initComponent();
    }
    private void initComponent(){
        listItem = new itemTaskbar[ArrMenu.length];
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(DefaultColor);
        
        // TOP PANEL
        pnlTop = new JPanel(new BorderLayout());
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(new Color(48, 103, 204)); // Xanh biển
        this.add(pnlTop, BorderLayout.NORTH);
        
        JLabel lblLogo = new JLabel("USER", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Roboto", Font.BOLD, 18));
        lblLogo.setForeground(Color.WHITE);
        pnlTop.add(lblLogo, BorderLayout.CENTER);
        
        bar1 = new JPanel();    // Thanh kẻ trắng ngắn cách bg màu xanh và bên phải
        bar1.setBackground(LineColor);
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);
        
        // CENTER PANEL
        pnlCenter = new JPanel();
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        
        bar3 = new JPanel(); // Thanh kẻ trắng ngắn cách các item Taskbar và bên phải
        bar3.setBackground(LineColor);
        bar3.setPreferredSize(new Dimension(1, 0));
        this.add(bar3, BorderLayout.EAST);
        
        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
        
        // BOTTOM PANEL
        pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setPreferredSize(new Dimension(250, 55));
        pnlBottom.setBackground(DefaultColor);
        this.add(pnlBottom, BorderLayout.SOUTH);
        
        bar4 = new JPanel();
        bar4.setBackground(LineColor);
        bar4.setPreferredSize(new Dimension(1, 0));
        pnlBottom.add(bar4, BorderLayout.EAST);
        
        for (int i = 0; i < ArrMenu.length; i++) {
            listItem[i] = new itemTaskbar(ArrMenu[i][1], ArrMenu[i][0]);
            if (i == ArrMenu.length - 1) {
                pnlBottom.add(listItem[i]);
            } else {
                pnlCenter.add(listItem[i]);
                listItem[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            }
            final int index = i;
            listItem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                    switch (index) {
                        case 0:
                            main.setPanel(new TrangChuPanel());
                            break;
                        case 1:
                            main.setPanel(new ThiSinhPanel());
                            break;
                        case 10:
                            main.setPanel(new TaiKhoanPanel(main));
                            break;
                    }
                }
            });
        }
        listItem[0].isSelected = true;
        listItem[0].setBackground(new Color(187, 222, 251));
        listItem[0].setForeground(new Color(0, 0, 0));
         for (int i = 1; i < ArrMenu.length; i++) {
             listItem[i].setBackground(DefaultColor);
             listItem[i].setForeground(new Color(96, 125, 139));
         }
    }
    
    public void pnlMenuTaskbarMousePress(MouseEvent evt) {
        for (int i = 0; i < ArrMenu.length; i++) {
            if (evt.getSource() == listItem[i]) {
                listItem[i].isSelected = true;
                listItem[i].setBackground(new Color(187, 222, 251));
                listItem[i].setForeground(new Color(0, 0, 0));
            } else {
                listItem[i].isSelected = false;
                listItem[i].setBackground(DefaultColor);
                listItem[i].setForeground(new Color(96, 125, 139));
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    
    
}
