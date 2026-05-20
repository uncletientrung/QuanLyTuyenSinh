/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

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
public class MainNguyenVongPanel extends JPanel{
    private Main mainFrame;
    private JTabbedPane tabbedPane;
    XtNguyenVongXetTuyenBUS NVBUS;
    List<XtNguyenVongXetTuyen> listNV;
    Color BackgroundColor = new Color(240, 247, 250);
    
     // Tác vụ thêm để xử lý
    private XtNganhBUS NganhBUS;
    private List<XtNganh> listNganh;
    private XtNganhToHopBUS NganhTHBUS;
    private List<XtNganhToHop> listNganhTH;
    private XtDiemCongXetTuyenBUS DiemCongBUS;
    private List<XtDiemCongXetTuyen> listDiemCong;
    private XtThisinhXetTuyen25BUS TSBUS;
    private List<XtThisinhXetTuyen25> listTS;
    private XtDiemThiXetTuyenBUS DTBUS;
    private List<XtDiemThiXetTuyen> listDT;
    private XtBangQuyDoiBUS BQDBUS;
    private List<XtBangQuyDoi> listBQD;
    
        public MainNguyenVongPanel(Main mainF,
                               XtNguyenVongXetTuyenBUS nvBUS, List<XtNguyenVongXetTuyen> listNV,
                               XtNganhBUS nganhBUS, List<XtNganh> listNganh,
                               XtNganhToHopBUS nganhTHBUS, List<XtNganhToHop> listNganhTH,
                               XtDiemCongXetTuyenBUS diemCongBUS, List<XtDiemCongXetTuyen> listDiemCong,
                               XtThisinhXetTuyen25BUS tsBUS, List<XtThisinhXetTuyen25> listTS,
                               XtDiemThiXetTuyenBUS dtBUS, List<XtDiemThiXetTuyen> listDT,
                               XtBangQuyDoiBUS bqdBUS, List<XtBangQuyDoi> listBQD) {

            this.mainFrame = mainF;

            // Gán các BUS và List được truyền vào
            this.NVBUS = nvBUS;
            this.listNV = listNV;

            this.NganhBUS = nganhBUS;
            this.listNganh = listNganh;

            this.NganhTHBUS = nganhTHBUS;
            this.listNganhTH = listNganhTH;

            this.DiemCongBUS = diemCongBUS;
            this.listDiemCong = listDiemCong;

            this.TSBUS = tsBUS;
            this.listTS = listTS;

            this.DTBUS = dtBUS;
            this.listDT = listDT;

            this.BQDBUS = bqdBUS;
            this.listBQD = listBQD;

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
            tabbedPane.addTab("Danh sách nguyện vọng", new NguyenVongPanel(mainFrame,
                                    NVBUS, listNV,
                                    NganhBUS, listNganh,
                                    NganhTHBUS, listNganhTH,
                                    DiemCongBUS, listDiemCong,
                                    TSBUS, listTS,
                                    DTBUS, listDT,
                                    BQDBUS, listBQD));

            tabbedPane.addTab("Thống kê nguyện vọng", new ThongKeNguyenVongPanel());
            tabbedPane.addChangeListener(e -> {
                int index = tabbedPane.getSelectedIndex();

                // Chỉ reload tab thống kê
                if(index == 1){
                    listTS = TSBUS.getAllThiSinh();
                    tabbedPane.setComponentAt( 1,new ThongKeNguyenVongPanel() );
                }
            });


            this.add(tabbedPane);
        }
}
