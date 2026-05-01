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
import com.quanlytuyensinh.GUI.Component.NumericDocumentFilter;
import com.quanlytuyensinh.helper.Validation;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class ThemThiSinhDialog extends JDialog {

    private ThiSinhPanel parent;
    private XtThisinhXetTuyen25BUS bus = new XtThisinhXetTuyen25BUS();
    private XtThisinhXetTuyen25 currentThiSinh;
    private String type; // "create" hoặc "edit"

    // Form fields
    private VerticalInputForm txtCCCD, txtSBD, txtHo, txtTen, txtNgaySinh,
            txtSDT, txtEmail, txtNoiSinh, txtPassword, txtPasswordConfirm;

    private VerticalComboBoxForm cbbGioiTinh, cbbKhuVuc, cbbDoiTuong;

    private ButtonCustom btnLuu, btnHuy;

    public ThemThiSinhDialog(ThiSinhPanel parent, JFrame owner, String title, String type, boolean modal) {
        super(owner, title, modal);
        this.parent = parent;
        this.type = type;
        this.setTitle(title);

        initComponents();
    }

    private void initComponents() {
        this.setSize(950, 720);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initMainPanel();
        initButtonPanel();

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

        if ("edit".equals(type) && currentThiSinh != null) {
            loadData();
        }

        this.setVisible(true);
    }

    private JPanel pnlMain, pnlButtons;

    private void initMainPanel() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(30, 40, 30, 40));
        pnlMain.setBackground(Color.WHITE);

        JPanel left = new JPanel(new GridLayout(7, 1, 0, 18));
        JPanel right = new JPanel(new GridLayout(7, 1, 0, 18));
        left.setBackground(Color.WHITE);
        right.setBackground(Color.WHITE);

        // ==================== LEFT COLUMN ====================
        txtCCCD = new VerticalInputForm("CCCD / CMND");
        txtSBD = new VerticalInputForm("Số báo danh (SBD)");
        txtHo = new VerticalInputForm("Họ");
        txtTen = new VerticalInputForm("Tên");
        txtNgaySinh = new VerticalInputForm("Ngày sinh (dd/MM/yyyy)");
        txtSDT = new VerticalInputForm("Số điện thoại");
        txtPassword = new VerticalInputForm("Mật khẩu");
        txtPasswordConfirm = new VerticalInputForm("Xác nhận mật khẩu");

        cbbGioiTinh = new VerticalComboBoxForm("Giới tính",new String[]{"Nam", "Nữ"});

        left.add(txtCCCD);
        left.add(txtSBD);
        left.add(txtHo);
        left.add(txtTen);
        left.add(txtNgaySinh);
        left.add(txtSDT);
        left.add(cbbGioiTinh);

        // ==================== RIGHT COLUMN ====================
        txtEmail = new VerticalInputForm("Email");
        txtNoiSinh = new VerticalInputForm("Nơi sinh");
        
        cbbKhuVuc = new VerticalComboBoxForm("Khu vực", 
            new String[]{"KV1", "KV2", "KV2-NT", "KV3"});
        
        cbbDoiTuong = new VerticalComboBoxForm("Đối tượng ưu tiên", 
            new String[]{"Không ưu tiên", "Ưu tiên 1", "Ưu tiên 2"});

        // Thêm các trường còn lại (có thể để trống hoặc có giá trị mặc định)
        
        right.add(txtEmail);
        right.add(txtNoiSinh);
        right.add(cbbKhuVuc);
        right.add(cbbDoiTuong);
        right.add(txtPassword);   // di chuyển xuống nếu cần cân bằng layout
        right.add(txtPasswordConfirm);   // di chuyển xuống nếu cần cân bằng layout

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

//        btnLuu.addActionListener(e -> {
//            if (validateInput()) {
//                saveThiSinh();
//            }
//        });

        btnHuy.addActionListener(e -> dispose());

        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
    }

//    private void saveThiSinh() {
//        XtThisinhXetTuyen25 ts = new XtThisinhXetTuyen25();
//
//        ts.setCccd(txtCCCD.getText().trim());
//        ts.setSobaodanh(txtSBD.getText().trim());
//        ts.setHo(txtHo.getText().trim());
//        ts.setTen(txtTen.getText().trim());
//        ts.setNgaySinh(convertDateFormat(txtNgaySinh.getText().trim()));
//        ts.setDienThoai(txtSDT.getText().trim());
//        ts.setEmail(txtEmail.getText().trim());
//        ts.setNoiSinh(txtNoiSinh.getText().trim());
//        ts.setGioiTinh((String) cbbGioiTinh.getSelectedValue());
//        ts.setKhuVuc((String) cbbKhuVuc.getSelectedValue());
//        ts.setDoiTuong((String) cbbDoiTuong.getSelectedValue());
//        ts.setPassword(txtPassword.getText().trim());   // quan trọng: mật khẩu
//
//        // Các trường tự động
//        ts.setUpdatedAt(java.sql.Date.valueOf(LocalDate.now()));
//
//        try {
//            if ("create".equals(type)) {
//                if (bus.insertThiSinh(ts)) {   // Bạn cần có phương thức này trong BUS
//                    JOptionPane.showMessageDialog(this, "Thêm thí sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                    parent.listTS = bus.getAllThiSinh();   // refresh list
//                    parent.loadDataTable(parent.listTS);
//                    dispose();
//                }
//            } else {
//                // edit logic (nếu cần sau này)
//                JOptionPane.showMessageDialog(this, "Chức năng chỉnh sửa đang phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }

    private String convertDateFormat(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        
        // Chuyển từ dd/MM/yyyy sang yyyy-MM-dd (phù hợp với DB)
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dbFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        try {
            LocalDate date = LocalDate.parse(input, inputFmt);
            return date.format(dbFmt);
        } catch (DateTimeParseException e) {
            return input; // giữ nguyên nếu đã đúng format
        }
    }

    private boolean validateInput() {
        if (Validation.isEmpty(txtCCCD.getText())) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtHo.getText())) {
            JOptionPane.showMessageDialog(this, "Họ không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHo.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtTen.getText())) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtTen.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtNgaySinh.getText())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getTxtForm().requestFocus();
            return false;
        }

        // Kiểm tra định dạng ngày sinh
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(txtNgaySinh.getText().trim(), fmt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng (dd/MM/yyyy)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getTxtForm().requestFocus();
            return false;
        }

        return true;
    }

    private void loadData() {
        // TODO: Implement khi cần chức năng Edit
        if (currentThiSinh != null) {
            txtCCCD.setText(currentThiSinh.getCccd());
            txtSBD.setText(currentThiSinh.getSobaodanh());
            txtHo.setText(currentThiSinh.getHo());
            txtTen.setText(currentThiSinh.getTen());
            // ... load các trường khác
        }
    }

    // Getter nếu cần
    public void setCurrentThiSinh(XtThisinhXetTuyen25 ts) {
        this.currentThiSinh = ts;
    }
}