/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dell
 */
public class MainThiSinhPanel extends JPanel {
    private Main mainFrame;
    private JTabbedPane tabbedPane;
    XtThisinhXetTuyen25BUS TSBUS;
    List<XtThisinhXetTuyen25> listTS;
    private Color BackgroundColor = new Color(240, 247, 250);

    public MainThiSinhPanel(Main main, XtThisinhXetTuyen25BUS tsBUS,   List<XtThisinhXetTuyen25> listThiSinh) {
        this.mainFrame = main;
        TSBUS = tsBUS;
        listTS = listThiSinh;
        initComponent();
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 14));
        tabbedPane.setBorder(new EmptyBorder(10, 10, 0, 10));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.addTab("Danh sách thí sinh", new ThiSinhPanel(mainFrame, TSBUS, listTS));

        tabbedPane.addTab("Thống kê thí sinh", new ThongKeThiSinhPanel());
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();

            // Chỉ reload tab thống kê
            if(index == 1){
                listTS = TSBUS.getAllThiSinh();
                tabbedPane.setComponentAt( 1,new ThongKeThiSinhPanel() );
            }
        });

        
        this.add(tabbedPane);
    }
}
