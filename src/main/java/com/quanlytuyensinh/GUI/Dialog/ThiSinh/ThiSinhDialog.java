/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog.ThiSinh;

import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.InputForm;
import com.quanlytuyensinh.GUI.Component.SelectForm;
import com.quanlytuyensinh.GUI.Panel.ThiSinhPanel;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Component.InputDate;
import com.quanlytuyensinh.helper.convertDateFormat;
import com.quanlytuyensinh.helper.Validation;

import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThiSinhDialog extends JDialog {

    private ThiSinhPanel parent;
    private XtThisinhXetTuyen25BUS bus;
    private XtThisinhXetTuyen25 currentThiSinh;
    private String type; // "create" | "update" | "detail"
    private Runnable onSuccess;

    private InputForm txtCCCD, txtSBD, txtHo, txtTen,
            txtSDT, txtEmail, txtNoiSinh, txtPassword;
    private InputDate txtNgaySinh;
    private SelectForm cbbGioiTinh, cbbKhuVuc, cbbDoiTuong;

    // Chỉ dùng khi detail
    private InputForm txtDiemTHPT, txtDiemDGNL, txtDiemVSAT;

    private ButtonCustom btnLuu, btnHuy;
    private JPanel pnlMain, pnlButtons;

    public ThiSinhDialog(ThiSinhPanel parent, JFrame owner, String title,
            String type, boolean modal, Runnable onSuccess, XtThisinhXetTuyen25 ts) {
        super(owner, title, modal);
        this.parent     = parent;
        this.bus        = parent.getBUS();
        this.type       = type;
        this.onSuccess  = onSuccess;
        this.currentThiSinh = ts;
        this.setTitle(title);
        initComponents();
    }

    private void initComponents() {
        boolean isDetail = "detail".equals(type);
        int width  = isDetail ? 1200 : 1000;
        int height = 480;

        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initFields();
        initMainPanel();
        initButtonPanel();

        if ("detail".equals(type)) {
            setAllFieldsDisable();
            setThiSinhData(currentThiSinh);
        } else if ("update".equals(type)) {
            setThiSinhData(currentThiSinh);
        }

        this.setVisible(true);
    }

    private void initFields() {
        txtCCCD     = new InputForm("CCCD *");
        txtSBD      = new InputForm("Số báo danh");
        txtSBD.setText("Hệ thống tự sinh");
        txtSBD.setEditable(false);
        txtHo       = new InputForm("Họ *");
        txtTen      = new InputForm("Tên *");
        txtNgaySinh = new InputDate("Ngày sinh (dd/MM/yyyy) *", 300, 40);
        txtNoiSinh  = new InputForm("Nơi sinh *");
        txtSDT      = new InputForm("Số điện thoại");
        txtEmail    = new InputForm("Email");
        txtPassword = new InputForm("Mật khẩu *");

        cbbGioiTinh = new SelectForm("Giới tính *",  new String[]{"Nam", "Nữ"});
        cbbKhuVuc   = new SelectForm("Khu vực *",    new String[]{"1", "2", "2NT", "3"});
        cbbDoiTuong = new SelectForm("Đối tượng ưu tiên",
                new String[]{"Không ưu tiên","01","02","03","04","05","06a","06b","07a","07b"});

        if ("detail".equals(type)) {
            txtDiemTHPT = new InputForm("Điểm THPT");
            txtDiemDGNL = new InputForm("Điểm ĐGNL");
            txtDiemVSAT = new InputForm("Điểm VSAT");
        }
    }

    private void initMainPanel() {
        boolean isDetail = "detail".equals(type);
        int cols = isDetail ? 5 : 4;

        pnlMain = new JPanel(new GridLayout(3, cols, 20, 0));
        pnlMain.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlMain.setBackground(Color.WHITE);

        // Hàng 1
        pnlMain.add(txtCCCD);
        pnlMain.add(txtHo);
        pnlMain.add(txtTen);
        pnlMain.add(cbbGioiTinh);
        if (isDetail) pnlMain.add(txtDiemTHPT);

        // Hàng 2
        pnlMain.add(txtSBD);
        pnlMain.add(wrapDate(txtNgaySinh));
        pnlMain.add(txtNoiSinh);
        pnlMain.add(cbbKhuVuc);
        if (isDetail) pnlMain.add(txtDiemDGNL);

        // Hàng 3
        pnlMain.add(txtSDT);
        pnlMain.add(txtEmail);
        pnlMain.add(txtPassword);
        pnlMain.add(cbbDoiTuong);
        if (isDetail) pnlMain.add(txtDiemVSAT);

        this.add(pnlMain, BorderLayout.CENTER);
    }

    /** Bọc InputDate vào JPanel để hoà hợp với GridLayout */
    private JPanel wrapDate(InputDate d) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(d, BorderLayout.CENTER);
        return p;
    }

    private void initButtonPanel() {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 0, 20, 0));

        int idTS = currentThiSinh != null ? currentThiSinh.getIdthisinh() : -1;

        if ("detail".equals(type)) {
            btnHuy = new ButtonCustom("Đóng", "danger", 14);
            btnHuy.addActionListener(e -> dispose());
            pnlButtons.add(btnHuy);
        } else {
            String btnText = "create".equals(type) ? "Thêm thí sinh" : "Lưu chỉnh sửa";
            btnLuu = new ButtonCustom(btnText, "success", 14);
            btnHuy = new ButtonCustom("Huỷ bỏ",   "danger",  14);
            btnLuu.addActionListener(e -> { if (validateInput(idTS)) saveThiSinh(); });
            btnHuy.addActionListener(e -> dispose());
            pnlButtons.add(btnLuu);
            pnlButtons.add(btnHuy);
        }

        this.add(pnlButtons, BorderLayout.SOUTH);
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
        ts.setGioiTinh((String) cbbGioiTinh.getCbb().getSelectedItem());
        ts.setKhuVuc((String) cbbKhuVuc.getCbb().getSelectedItem());
        String doiTuong = (String) cbbDoiTuong.getCbb().getSelectedItem();
        ts.setDoiTuong("Không ưu tiên".equals(doiTuong) ? null : doiTuong);
        ts.setPassword(txtPassword.getText().trim());
        ts.setUpdatedAt(LocalDate.now());

        try {
            if ("create".equals(type)) {
                if (bus.insertThiSinh(ts)) {
                    JOptionPane.showMessageDialog(this, "Thêm thí sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    if (onSuccess != null) onSuccess.run();
                    dispose();
                }
            } else if ("update".equals(type)) {
                ts.setIdthisinh(currentThiSinh.getIdthisinh());
                if (bus.updateThiSinh(ts)) {
                    JOptionPane.showMessageDialog(this, "Sửa thí sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    if (onSuccess != null) onSuccess.run();
                    dispose();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // validate
    private boolean validateInput(int idTS) {
        String cccd = txtCCCD.getText().trim();
        if (Validation.isEmpty(cccd)) {
            JOptionPane.showMessageDialog(this, "CCCD không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }
        if (!bus.checkCCCD(cccd, idTS)) {
            JOptionPane.showMessageDialog(this, "CCCD đã tồn tại trong hệ thống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtCCCD.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtHo.getText()) && Validation.isEmpty(txtTen.getText())) {
            JOptionPane.showMessageDialog(this, "Họ Tên không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHo.getTxtForm().requestFocus();
            return false;
        }

        Date ngaySinh = txtNgaySinh.getDateChooser().getDate();
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getDateChooser().requestFocus();
            return false;
        }
        if (ngaySinh.after(new Date())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được là ngày tương lai!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.getDateChooser().requestFocus();
            return false;
        }

        if (cbbGioiTinh.getCbb().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(txtNoiSinh.getText())) {
            JOptionPane.showMessageDialog(this, "Nơi sinh không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNoiSinh.getTxtForm().requestFocus();
            return false;
        }
        if (cbbKhuVuc.getCbb().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String pass = txtPassword.getText().trim();
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
        return true;
    }

    private void setAllFieldsDisable() {
        txtCCCD.setDisable();   txtSBD.setDisable();
        txtHo.setDisable();     txtTen.setDisable();
        txtSDT.setDisable();    txtEmail.setDisable();
        txtNoiSinh.setDisable(); txtPassword.setDisable();
        txtNgaySinh.setDisable();
        cbbGioiTinh.setDisable(); cbbKhuVuc.setDisable(); cbbDoiTuong.setDisable();
        if (txtDiemTHPT != null) {
            txtDiemTHPT.setDisable();
            txtDiemDGNL.setDisable();
            txtDiemVSAT.setDisable();
        }
    }

    private void setThiSinhData(XtThisinhXetTuyen25 ts) {
        txtCCCD.setText(ts.getCccd());
        txtHo.setText(ts.getHo());
        txtTen.setText(ts.getTen());
        txtSBD.setText(ts.getSobaodanh());
        try {
            DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(ts.getNgaySinh(), fmt1);
            Date date = java.sql.Date.valueOf(localDate);
            txtNgaySinh.getDateChooser().setDate(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        txtNoiSinh.setText(ts.getNoiSinh());
        txtSDT.setText(ts.getDienThoai());
        txtEmail.setText(ts.getEmail());
        txtPassword.setText(ts.getPassword());

        cbbGioiTinh.getCbb().setSelectedItem(ts.getGioiTinh());
        cbbKhuVuc.getCbb().setSelectedItem(ts.getKhuVuc());
        String doiTuong = ts.getDoiTuong() == null ? "Không ưu tiên" : ts.getDoiTuong();
        cbbDoiTuong.getCbb().setSelectedItem(doiTuong);
        // txtDiemTHPT / ĐGNL / VSAT
    }
}