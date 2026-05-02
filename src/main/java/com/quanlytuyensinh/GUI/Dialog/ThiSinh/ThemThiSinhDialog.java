/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog.ThiSinh;

import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import com.quanlytuyensinh.GUI.Component.VerticalComboBoxForm;
import com.quanlytuyensinh.GUI.Panel.ThiSinhPanel;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Component.InputDate;
import com.quanlytuyensinh.helper.convertDateFormat;
import com.quanlytuyensinh.GUI.Component.NumericDocumentFilter;
import com.quanlytuyensinh.helper.Validation;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class ThemThiSinhDialog extends JDialog {

    private ThiSinhPanel parent;
    private XtThisinhXetTuyen25BUS bus;
    private XtThisinhXetTuyen25 currentThiSinh;
    private String type; // "create" hoặc "edit"
    private Runnable onSuccess; // Lưu hàm chạy sau khi xong

    // Form fields
    private VerticalInputForm txtCCCD, txtSBD, txtHo, txtTen,
            txtSDT, txtEmail, txtNoiSinh, txtPassword, txtPasswordConfirm;
    private InputDate txtNgaySinh;
    private VerticalComboBoxForm cbbGioiTinh, cbbKhuVuc, cbbDoiTuong;
    private ButtonCustom btnLuu, btnHuy;

    public ThemThiSinhDialog(ThiSinhPanel parent, JFrame owner, String title, String type, boolean modal, Runnable onSuccess) {
        super(owner, title, modal);
        this.parent = parent;
        this.bus = parent.getBUS(); // Dùng chung 1 BUS với cha
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
        setFakeData(); // Set dữ liệu giả
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JPanel pnlMain, pnlButtons;

    private void initMainPanel() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

        JPanel left = new JPanel(new GridLayout(7, 1, 0, 15));
        JPanel right = new JPanel(new GridLayout(7, 1, 0, 15));
        left.setBackground(Color.WHITE);
        right.setBackground(Color.WHITE);
        // ==================== ĐỊNH NGHĨA CÁC THUỘC TÍNH ====================
        
        txtCCCD = new VerticalInputForm("CCCD / CMND");
        txtSBD = new VerticalInputForm("Số báo danh (SBD)");
        txtSBD.setText("Hệ thống tự sinh");
        txtSBD.setDisable();
        txtHo = new VerticalInputForm("Họ");
        txtTen = new VerticalInputForm("Tên");
        txtNgaySinh = new InputDate("Ngày sinh (dd/MM/yyyy)", 300, 40);
        txtSDT = new VerticalInputForm("Số điện thoại");
        txtPassword = new VerticalInputForm("Mật khẩu");
        txtPasswordConfirm = new VerticalInputForm("Xác nhận mật khẩu");
        cbbGioiTinh = new VerticalComboBoxForm("Giới tính",new String[]{"Nam", "Nữ"});
        txtEmail = new VerticalInputForm("Email");
        txtNoiSinh = new VerticalInputForm("Nơi sinh");
        cbbKhuVuc = new VerticalComboBoxForm("Khu vực", 
            new String[]{"KV1", "KV2", "KV2-NT", "KV3"});
        cbbDoiTuong = new VerticalComboBoxForm("Đối tượng ưu tiên", 
            new String[]{"Không ưu tiên", "Ưu tiên 1", "Ưu tiên 2"});
        
        // ==================== LEFT COLUMN ====================
        left.add(txtCCCD);
        left.add(txtHo);
        left.add(txtNgaySinh);
        left.add(txtNoiSinh);
        left.add(txtSDT);
        left.add(txtPassword);
        left.add(cbbDoiTuong);

        // ==================== RIGHT COLUMN ====================
        right.add(txtSBD);
        right.add(txtTen);
        right.add(cbbGioiTinh);
        right.add(cbbKhuVuc);
        right.add(txtEmail);
        right.add(txtPasswordConfirm);
        
        // =======================
        pnlMain.add(left);
        pnlMain.add(right);
    }

    private void initButtonPanel() {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(15, 0, 25, 0));

        String btnText = "create".equals(type) ? "Thêm thí sinh" : "Lưu thay đổi";

        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnHuy = new ButtonCustom("Hủy bỏ", "danger", 15);

        btnLuu.setPreferredSize(new Dimension(160, 48));
        btnHuy.setPreferredSize(new Dimension(160, 48));

        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                 saveThiSinh();
            }
        });

        btnHuy.addActionListener(e -> dispose());

        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
    }

    private void saveThiSinh() {
        String ngaySinh = "";
        try {
            ngaySinh = convertDateFormat.DateToString(txtNgaySinh.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày khi save");
        }
        
        XtThisinhXetTuyen25 ts = new XtThisinhXetTuyen25();
        
        ts.setCccd(txtCCCD.getText().trim());
        ts.setSobaodanh(txtSBD.getText().trim());
        ts.setHo(txtHo.getText().trim());
        ts.setTen(txtTen.getText().trim());
        ts.setNgaySinh(ngaySinh);
        ts.setDienThoai(txtSDT.getText().trim());
        ts.setEmail(txtEmail.getText().trim());
        ts.setNoiSinh(txtNoiSinh.getText().trim());
        ts.setGioiTinh((String) cbbGioiTinh.getSelectedValue());
        ts.setKhuVuc((String) cbbKhuVuc.getSelectedValue());
        ts.setDoiTuong((String) cbbDoiTuong.getSelectedValue());
        ts.setPassword(txtPassword.getText().trim()); 
         ts.setUpdatedAt(LocalDate.now()); // Cập nhật trạng thái chỉnh sửa
       
        try {
            if ("create".equals(type)) {
                if (bus.insertThiSinh(ts)) {
                    JOptionPane.showMessageDialog(this, "Thêm thí sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                    dispose();
                }
            } else {
                // edit logic (nếu cần sau này)
                JOptionPane.showMessageDialog(this, "Chức năng chỉnh sửa đang phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private boolean validateInput() {

        // ===== CCCD =====
        String cccd = txtCCCD.getText().trim();
        if (Validation.isEmpty(cccd)) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }
        if (!cccd.matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "CCCD phải gồm đúng 12 chữ số!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }
        if (!bus.checkCCCD(cccd)) {
            JOptionPane.showMessageDialog(this, "CCCD đã tồn tại trong hệ thống", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }

        // ===== SBD =====
        if (Validation.isEmpty(txtSBD.getText())) {
            JOptionPane.showMessageDialog(this, "Số báo danh không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSBD.getTxtForm().requestFocus();
            return false;
        }

        // ===== Họ =====
        if (Validation.isEmpty(txtHo.getText())) {
            JOptionPane.showMessageDialog(this, "Họ không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHo.getTxtForm().requestFocus();
            return false;
        }

        // ===== Tên =====
        if (Validation.isEmpty(txtTen.getText())) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtTen.getTxtForm().requestFocus();
            return false;
        }

        // ===== Ngày sinh =====
        Date ngaySinh = txtNgaySinh.getDateChooser().getDate();
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getDateChooser().requestFocus();
            return false;
        }

        // Optional: kiểm tra không được chọn ngày tương lai
        if (ngaySinh.after(new Date())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được là ngày tương lai!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getDateChooser().requestFocus();
            return false;
        }

        // ===== Giới tính =====
        if (cbbGioiTinh.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // ===== Nơi sinh =====
        if (Validation.isEmpty(txtNoiSinh.getText())) {
            JOptionPane.showMessageDialog(this, "Nơi sinh không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNoiSinh.getTxtForm().requestFocus();
            return false;
        }

        // ===== Khu vực =====
        if (cbbKhuVuc.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // ===== SĐT =====
        String sdt = txtSDT.getText().trim();
        if (Validation.isEmpty(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSDT.getTxtForm().requestFocus();
            return false;
        }
        if (!sdt.matches("0\\d{9}")) {
            JOptionPane.showMessageDialog(this, "SĐT phải gồm 10 số và bắt đầu bằng 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSDT.getTxtForm().requestFocus();
            return false;
        }

        // ===== Email =====
        String email = txtEmail.getText().trim();
        if (Validation.isEmpty(email)) {
            JOptionPane.showMessageDialog(this, "Email không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtEmail.getTxtForm().requestFocus();
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtEmail.getTxtForm().requestFocus();
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
   
    private void setFakeData() {
        txtCCCD.setText("012345678901");
        txtHo.setText("Nguyen");
        txtTen.setText("An");

        // Ngày sinh: 15/05/2004
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse("15/05/2004", fmt);
            Date date = java.sql.Date.valueOf(localDate);
            txtNgaySinh.getDateChooser().setDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtNoiSinh.setText("TP.HCM");
        txtSDT.setText("0912345678");
        txtEmail.setText("test@gmail.com");

        txtPassword.setText("123456");
        txtPasswordConfirm.setText("123456");

        // ComboBox
        cbbGioiTinh.getComboBox().setSelectedItem("Nam");
        cbbKhuVuc.getComboBox().setSelectedItem("KV1");
        cbbDoiTuong.getComboBox().setSelectedItem("Không ưu tiên");
    }

}