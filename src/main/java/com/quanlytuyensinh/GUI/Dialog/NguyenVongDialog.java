/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalComboBoxForm;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dell
 */
public class NguyenVongDialog extends JDialog{
    private NguyenVongPanel parent;
    private XtNguyenVongXetTuyenBUS NVBUS;
    private String type; // "create" hoặc "edit"
    private Runnable onSuccess; // Lưu hàm chạy sau khi xong
    
    // Form fields
    private VerticalInputForm txtThuTu, txtDiemTHXT, txtDiemUT, txtDiemCong, txtDiemXetTuyen, txtKetQua, txtDanhSachTH, txtTHXet;
    private VerticalComboBoxForm cbbPhuongThuc, cbbCCCD, cbbMaNganh;
    private ButtonCustom btnLuu, btnHuy;
    private JPanel pnlMain, pnlButtons;
    
    // Tác vụ thêm để xử lý
    private XtNganhBUS NganhBUS;
    private XtNganhToHopBUS NganhTHBUS;
    private XtDiemCongXetTuyenBUS DiemCongBUS;
    private XtThisinhXetTuyen25BUS TSBUS;
    
    public NguyenVongDialog(NguyenVongPanel parent, JFrame owner, String title, String type, boolean modal, Runnable onSuccess, XtNguyenVongXetTuyen nv){
         super(owner, title, modal);
        this.parent = parent;
        NVBUS = parent.getBUS();
        this.type = type;
        this.onSuccess = onSuccess;
        this.setTitle(title);

        initComponents();
    }
        private void initComponents() {
        this.setSize(800, 750);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initMainPanel();
        initButtonPanel();

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void initMainPanel() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

        JPanel left = new JPanel(new GridLayout(7, 1, 0, 15));
        JPanel right = new JPanel(new GridLayout(7, 1, 0, 15));
        left.setBackground(Color.WHITE);
        right.setBackground(Color.WHITE);
        // ==================== ĐỊNH NGHĨA CÁC THUỘC TÍNH ====================
        
    }

    private void initButtonPanel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
