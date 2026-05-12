/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.TaiKhoanBUS;
import com.quanlytuyensinh.ENTITY.TaiKhoan;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalComboBoxForm;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import com.quanlytuyensinh.GUI.Panel.TaiKhoanPanel;
import com.quanlytuyensinh.helper.Validation;
import com.quanlytuyensinh.helper.convertDateFormat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author admn
 */
public class TaiKhoanDialog extends JDialog{
     private TaiKhoanPanel parent;
    private TaiKhoanBUS bus;
    private TaiKhoan tk;
    private String type; // "create" hoặc "edit"
    private Runnable onSuccess; // Lưu hàm chạy sau khi xong

    // Form fields
    private VerticalInputForm txttdn, txtPassword, txtPasswordConfirm;
    private VerticalComboBoxForm cbbPhanQuyen, cbbTrangthai;
    private ButtonCustom btnLuu, btnHuy;
    JPanel centerPanel;

    public TaiKhoanDialog(TaiKhoanPanel parent, TaiKhoanBUS bus,JFrame owner, String title, String type, boolean modal, Runnable onSuccess, TaiKhoan tk) {
        super(owner, title, modal);
        this.parent = parent;
        this.bus = bus; // Dùng chung 1 BUS với cha
        this.type = type;
        this.onSuccess = onSuccess;
        this.tk = tk;
        initComponents();
    }

    private void initComponents() {
        this.setSize(600, 550);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initMainPanel();
        initButtonPanel();
      
       if(this.type.equals("detail")){
            setAllFieldsDisable(); // Chặn chỉnh sửa
            centerPanel.remove(txtPasswordConfirm);
                           centerPanel.add(cbbTrangthai);
            setTaiKhoanData(tk);
        }
       else if(this.type.equals("update")){
               this.setSize(600,630);
              
               centerPanel.add(cbbTrangthai);
               
            setTaiKhoanData(tk);
        }

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private JPanel pnlMain, pnlButtons;

    private void initMainPanel() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

            centerPanel = new JPanel(new GridLayout(0, 1, 0, 15));
        
        centerPanel.setBackground(Color.WHITE);
        
        // ==================== ĐỊNH NGHĨA CÁC THUỘC TÍNH ====================
        txttdn= new VerticalInputForm("Tên đăng nhập");
        txtPassword = new VerticalInputForm("Mật khẩu");
        txtPasswordConfirm = new VerticalInputForm("Xác nhận mật khẩu");
        cbbPhanQuyen = new VerticalComboBoxForm("Quyền", 
            new String[]{"Quản lý", "Học sinh"});
        cbbTrangthai = new VerticalComboBoxForm("Trạng thái",
                new String[]{"Hoạt động", "Ngừng hoạt động"});
        Label lbHide = new Label();
       centerPanel.add(txttdn);
       centerPanel.add(txtPassword);
       centerPanel.add(txtPasswordConfirm);
       centerPanel.add(cbbPhanQuyen);
        
        
        
        pnlMain.add(centerPanel);
    }

    // Khởi tạo nút
    private void initButtonPanel() {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(15, 0, 25, 0));

        String btnText = "create".equals(type) ? "Thêm thí sinh" : "Lưu chỉnh sửa";

        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnHuy = new ButtonCustom("Hủy bỏ", "danger", 15);

        btnLuu.setPreferredSize(new Dimension(160, 48));
        btnHuy.setPreferredSize(new Dimension(160, 48));

        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                 savetaikhoan ();
            }
        });

        btnHuy.addActionListener(e -> dispose());

        if (!this.type.equals("detail")) {
            pnlButtons.add(btnLuu);
        }
        pnlButtons.add(btnHuy);
    }

    private void savetaikhoan() {
        
        TaiKhoan ts = new TaiKhoan();
        ts.setTendangnhap(txttdn.getText().trim());
        ts.setMatkhau(txtPassword.getText().trim());
       ts.setTrangthai(1);
        String PhanQuyen = (String) cbbPhanQuyen.getSelectedValue();
        int pq;
        if(PhanQuyen.equals("Quản lý"))
        {
            pq=1;
        }
        else {pq=2;}
        ts.setMaphanquyen(pq);
       
        try {
            if ("create".equals(type)) {
                if (bus.addTaiKhoan(ts.getTendangnhap(),ts.getMatkhau(),ts.getMaphanquyen(),ts.getTrangthai())) {
                    System.out.println(onSuccess);
                    JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                    dispose();
                }
            }
             else if (this.type.equals("update")) {
                ts.setMatk(tk.getMatk());
                String Trangthai = (String) cbbTrangthai.getSelectedValue();
                int trangthai;
                  if(Trangthai.equals("Hoạt động"))
                {
                        trangthai=1;
                }
        else {trangthai=0;}
                if(bus.updateTaiKhoan(ts.getMatk(),ts.getTendangnhap(),ts.getMatkhau(),ts.getMaphanquyen(),trangthai)){
                    JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                        dispose();
                }
//            }
        }
        }
            catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    
    }
    
    // Kiểm tra dữ liệu nhập
    private boolean validateInput() {

        // ===== CCCD =====
        String tdn = txttdn.getText().trim();
        if (Validation.isEmpty(tdn)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txttdn.getTxtForm().requestFocus();
            return false;
        }
       
        int currentId = (tk != null) ? tk.getMatk() : -1;
        
        if (!bus.checktdn(tdn,currentId)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại trong hệ thống", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txttdn.getTxtForm().requestFocus();
            return false;
        }

   
       
        if (cbbPhanQuyen.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // ===== Password =====
        String pass = txtPassword.getText().trim();
        String confirm = txtPasswordConfirm.getText().trim();

        if (Validation.isEmpty(pass)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtPassword.getTxtForm().requestFocus();
            return false;
        }
        if (pass.length() < 5) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải >= 5 ký tự!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtPassword.getTxtForm().requestFocus();
            return false;
        }
        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtPasswordConfirm.getTxtForm().requestFocus();
            return false;
        }

        return true;
    }
   
    private void setAllFieldsDisable(){
        VerticalInputForm[] listInput = {txttdn, txtPassword,
                txtPasswordConfirm};
        for (VerticalInputForm f : listInput) {
            f.setDisable();
        }
        cbbPhanQuyen.setDisable();
        cbbTrangthai.setDisable();
    }
    
    private void setTaiKhoanData(TaiKhoan tk){
             txttdn.setText(tk.getTendangnhap());
         txtPassword.setText(tk.getMatkhau());
      
       if(this.type.equals("detail")){
        
           if(tk.getMaphanquyen()==1)
           {
               cbbPhanQuyen.setSelectedValue("Quản lý");
           }
           else{
            cbbPhanQuyen.setSelectedValue("Học sinh");

           }
           if(tk.getTrangthai()==1)
           {
               cbbTrangthai.setSelectedValue("Hoạt động");
           }
           else{
               cbbTrangthai.setSelectedValue("Ngừng hoạt động");
           }
       }
       if(this.type.equals("update")){
           txtPasswordConfirm.setText(tk.getMatkhau());
           if(tk.getMaphanquyen()==1)
           {
               cbbPhanQuyen.setSelectedValue("Quản lý");
           }
           else{
            cbbPhanQuyen.setSelectedValue("Học sinh");

           }
           if(tk.getTrangthai()==1)
           {
               cbbTrangthai.setSelectedValue("Hoạt động");
           }
           else{
               cbbTrangthai.setSelectedValue("Ngừng hoạt động");
           }
       }
        // ComboBox
    }
    
//    // Làm dữ liệu giả 
//    private void setFakeData() {
//        txtCCCD.setText("012345678901");
//        txtHo.setText("Nguyen");
//        txtTen.setText("An");
//
//
//        try {
//            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate localDate = LocalDate.parse("2004-05-15", fmt);
//            Date date = java.sql.Date.valueOf(localDate);
//            txtNgaySinh.getDateChooser().setDate(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        txtNoiSinh.setText("TP.HCM");
//        txtSDT.setText("0912345678");
//        txtEmail.setText("test@gmail.com");
//
//        txtPassword.setText("123456");
//        txtPasswordConfirm.setText("123456");
//
//        // ComboBox
//        cbbGioiTinh.getComboBox().setSelectedItem("Nam");
//        cbbKhuVuc.getComboBox().setSelectedItem("1");
//        cbbDoiTuong.getComboBox().setSelectedItem("Không ưu tiên");
//    }

}
