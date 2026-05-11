package com.quanlytuyensinh.GUI.Panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.GUI.Main;

public class DiemThiPanel extends JPanel{
    private Main mainFrame;
    private JTabbedPane tabbedPane;

    private XtDiemThiXetTuyenBUS diemBUS;
    private Color BackgroundColor = new Color(240, 247, 250);

    public DiemThiPanel(Main main) {
        this.mainFrame = main;
        diemBUS = new XtDiemThiXetTuyenBUS();
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

        tabbedPane.addTab("THPT", new DiemThiTHPTPanel(mainFrame, diemBUS, diemBUS.getListTHPT()));
        tabbedPane.addTab("VSAT", new DiemThiVSATPanel(mainFrame, diemBUS, diemBUS.getListVSAT()));
        tabbedPane.addTab("DGNL", new DiemThiDGNLPanel(mainFrame, diemBUS, diemBUS.getListDGNL()));
        
        this.add(tabbedPane);
    }
}
