/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Component;

import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import com.quanlytuyensinh.GUI.Panel.ThiSinhPanel;
import com.quanlytuyensinh.GUI.Panel.DiemThiPanel;  
import com.quanlytuyensinh.GUI.Panel.NganhPanel;
import com.quanlytuyensinh.GUI.Panel.TaiKhoanPanel;
import com.quanlytuyensinh.GUI.Panel.TrangChuPanel;
import com.quanlytuyensinh.GUI.Panel.XtDiemCongXetTuyenPanel;
import com.quanlytuyensinh.GUI.Panel.XtBangQuyDoiPanel;
import com.quanlytuyensinh.GUI.Panel.NganhToHopPanel;
import com.quanlytuyensinh.GUI.Panel.ToHopMonPanel;
import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.GUI.Panel.MainThiSinhPanel;
import com.quanlytuyensinh.GUI.ThongKe.ThongKe;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
/**
 *
 * @author DELL
 */
public class MenuTaskbar extends JPanel {
    private XtNguyenVongXetTuyenBUS NVBUS;
    private java.util.List<XtNguyenVongXetTuyen> listNV;

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
    String[][] ArrMenu = {
        {"Trang chủ", "home.svg", "trangChu"},
        {"Thí sinh", "students.svg", "thiSinh"},
        {"Điểm thi", "diemthi.svg", "diemThi"},
        {"Nguyện vọng", "nguyenvong.svg", "nguyenVong"},
        {"Môn", "subject.svg", "mon"},
        {"Ngành", "nganh.svg", "nganh"},
        {"Ngành - Tổ hợp", "tohop.svg", "nganhToHop"},
        {"Bảng quy đổi", "quydoi.svg", "bangQuyDoi"},
        {"Điểm cộng", "add.svg", "diemCong"},
        {"Người dùng", "nguoidung.svg", "nguoiDung"},
        {"Thống kê", "thongke.svg", "thongKe"},
        {"Đăng xuất", "logout.svg", "dangXuat"}
    };
    private Main main;
    private itemTaskbar[] listItem;
    JScrollPane scrollPane;
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;
    Color DefaultColor = new Color(255, 255, 255);
    Color LineColor = new Color(204, 214, 219); // Màu xanh trắng

    public MenuTaskbar(Main m,
                       XtNguyenVongXetTuyenBUS nvBUS, java.util.List<XtNguyenVongXetTuyen> listNV,
                       XtNganhBUS nganhBUS, java.util.List<XtNganh> listNganh,
                       XtNganhToHopBUS nganhTHBUS, java.util.List<XtNganhToHop> listNganhTH,
                       XtDiemCongXetTuyenBUS diemCongBUS, java.util.List<XtDiemCongXetTuyen> listDiemCong,
                       XtThisinhXetTuyen25BUS tsBUS, java.util.List<XtThisinhXetTuyen25> listTS,
                       XtDiemThiXetTuyenBUS dtBUS, java.util.List<XtDiemThiXetTuyen> listDT,
                       XtBangQuyDoiBUS bqdBUS, java.util.List<XtBangQuyDoi> listBQD) {
        this.main = m;
        // Gán các BUS và List
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
                            main.setPanel(new MainThiSinhPanel(main, TSBUS, listTS));
                            break;
                        case 2:
                            main.setPanel(new DiemThiPanel(main));
                            break;
                        case 3:
                            main.setPanel(new NguyenVongPanel(main,
                                    NVBUS, listNV,
                                    NganhBUS, listNganh,
                                    NganhTHBUS, listNganhTH,
                                    DiemCongBUS, listDiemCong,
                                    TSBUS, listTS,
                                    DTBUS, listDT,
                                    BQDBUS, listBQD));
                            break;
                        case 4:
                            main.setPanel(new ToHopMonPanel(main));
                            break;
                        case 5:
                            main.setPanel(new NganhPanel(main));
                            break;
                        case 6:
                            main.setPanel(new NganhToHopPanel(main));
                            break;
                        case 7:
                            main.setPanel(new XtBangQuyDoiPanel(main));
                            break;
                        case 8:
                            main.setPanel(new XtDiemCongXetTuyenPanel(main));
                            break;
                        case 9:
                            main.setPanel(new TaiKhoanPanel(main));
                            break;
                        case 10:
                            main.setPanel(new ThongKe());
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
