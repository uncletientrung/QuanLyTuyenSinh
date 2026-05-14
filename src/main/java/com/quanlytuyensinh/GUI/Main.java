/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI;

import com.quanlytuyensinh.GUI.Component.MenuTaskbar;
import com.quanlytuyensinh.GUI.Panel.TrangChuPanel;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.BUS.XtBangQuyDoiBUS;
import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DELL
 */
public class Main extends JFrame {

    public JPanel MainContent;
    private MenuTaskbar menuTaskbar;

    Color MainColor = new Color(250, 250, 250);
    
    XtNguyenVongXetTuyenBUS NVBUS;
    java.util.List<XtNguyenVongXetTuyen> listNV;
    private XtNganhBUS NganhBUS;
    private java.util.List<XtNganh> listNganh;
    private XtNganhToHopBUS NganhTHBUS;
    private java.util.List<XtNganhToHop> listNganhTH;
    private XtDiemCongXetTuyenBUS DiemCongBUS;
    private java.util.List<XtDiemCongXetTuyen> listDiemCong;
    private XtThisinhXetTuyen25BUS TSBUS;
    private java.util.List<XtThisinhXetTuyen25> listTS;
    private XtDiemThiXetTuyenBUS DTBUS;
    private java.util.List<XtDiemThiXetTuyen> listDT;
    private XtBangQuyDoiBUS BQDBUS;
    private java.util.List<XtBangQuyDoi> listBQD;
    

    public Main() {
        TSBUS = new XtThisinhXetTuyen25BUS();
        DTBUS = new XtDiemThiXetTuyenBUS();
        NVBUS = new XtNguyenVongXetTuyenBUS();
        NganhBUS =new XtNganhBUS();
        NganhTHBUS = new XtNganhToHopBUS();
        BQDBUS = new XtBangQuyDoiBUS();
        DiemCongBUS = new XtDiemCongXetTuyenBUS();

        listTS = TSBUS.getAllThiSinh();
        this.listDT = DTBUS.getList();
        listNV = NVBUS.getAllNguyenVong();
        this.listNganh = NganhBUS.getAllNganh();
        this.listNganhTH = NganhTHBUS.getAll();
        this.listBQD = BQDBUS.getAllQuyDoi();
        this.listDiemCong = DiemCongBUS.getAllDiemCong();
        
        
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

    private void initComponent() {
        this.setSize(new Dimension(1400, 800));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Hệ thống quản lý tuyển sinh");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MENU
        menuTaskbar = new MenuTaskbar(this,
                        NVBUS, listNV,
                        NganhBUS, listNganh,
                        NganhTHBUS, listNganhTH,
                        DiemCongBUS, listDiemCong,
                        TSBUS, listTS,
                        DTBUS, listDT,
                        BQDBUS, listBQD);
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

}
