package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import com.quanlytuyensinh.GUI.Component.NumericDocumentFilter;
import com.quanlytuyensinh.GUI.Panel.NganhPanel;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.GUI.Component.VerticalComboBoxForm;
import com.quanlytuyensinh.helper.Validation;
import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class NganhDialog extends JDialog {

    private VerticalInputForm txtMaNganh, txtTenNganh, txtToHopGoc, txtChiTieu,
            txtDiemSan, txtDiemTrungTuyen,
            txtSlXtt, txtSlDgnl, txtSlVsat, txtSlThpt;
    private VerticalComboBoxForm cbbTuyenThang, cbbDgnl, cbbThpt, cbbVsat;
    private ButtonCustom btnLuu, btnHuy;
    private XtNganhBUS bus = new XtNganhBUS();
    private NganhPanel parent;
    private XtNganh currentNganh;
    private JPanel pnlMain, pnlButtons;

    public NganhDialog(NganhPanel parent, JFrame owner, String title, boolean modal, String type, XtNganh nganh) {
        super(owner, title, modal);
        this.parent = parent;
        this.currentNganh = nganh;
        this.setTitle(title);
        init(type);
    }

    private void init(String type) {
        this.setSize(800, 750); 
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initPnlMain();
        initPnlButtons(type);

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

        if (type.equals("view")) {
            setAllFieldsDisable();
        }

        this.setVisible(true);
    }

    private void initPnlMain() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));  
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

  
        JPanel pnlLeft = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlLeft.setBackground(Color.WHITE);
        
        JPanel pnlRight = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlRight.setBackground(Color.WHITE);

        //tao input
        txtMaNganh = new VerticalInputForm("Mã ngành");
        txtTenNganh = new VerticalInputForm("Tên ngành");
        txtToHopGoc = new VerticalInputForm("Tổ hợp gốc");
        txtChiTieu = new VerticalInputForm("Chỉ tiêu");
        txtDiemSan = new VerticalInputForm("Điểm sàn");
        txtDiemTrungTuyen = new VerticalInputForm("Điểm trúng tuyển");
        cbbTuyenThang = new VerticalComboBoxForm("Tuyển thẳng");
        cbbDgnl = new VerticalComboBoxForm("ĐGNL");
        cbbThpt = new VerticalComboBoxForm("THPT");
        cbbVsat = new VerticalComboBoxForm("VSAT");
        txtSlXtt = new VerticalInputForm("SL XTT");
        txtSlDgnl = new VerticalInputForm("SL ĐGNL");
        txtSlVsat = new VerticalInputForm("SL VSAT");
        txtSlThpt = new VerticalInputForm("SL THPT");

        // Filter số
        setNumericFilter(txtChiTieu, txtSlXtt, txtSlDgnl, txtSlVsat,txtSlThpt);
        bindToggle(cbbTuyenThang, txtSlXtt);
        bindToggle(cbbDgnl, txtSlDgnl);
        bindToggle(cbbThpt, txtSlThpt);
        bindToggle(cbbVsat, txtSlVsat);

        // Đổ dữ liệu nếu có
        if (currentNganh != null) {
            txtMaNganh.setText(currentNganh.getManganh());
            txtTenNganh.setText(currentNganh.getTennganh());
            txtToHopGoc.setText(currentNganh.getNTohopgoc() != null ? currentNganh.getNTohopgoc() : "");
            txtChiTieu.setText(currentNganh.getNChitieu() > 0 ? String.valueOf(currentNganh.getNChitieu()) : "");
            txtDiemSan.setText(currentNganh.getNDiemsan() != null ? currentNganh.getNDiemsan().toString() : "");
            txtDiemTrungTuyen.setText(currentNganh.getNDiemtrungtuyen() != null ? currentNganh.getNDiemtrungtuyen().toString() : "");
            cbbTuyenThang.setSelectedValue(currentNganh.getNTuyenthang());
            cbbDgnl.setSelectedValue(currentNganh.getNDgnl());
            cbbThpt.setSelectedValue(currentNganh.getNThpt());
            cbbVsat.setSelectedValue(currentNganh.getNVsat());
            txtSlXtt.setText(currentNganh.getSlXtt() != null ? String.valueOf(currentNganh.getSlXtt()) : "");
            txtSlDgnl.setText(currentNganh.getSlDgnl() != null ? String.valueOf(currentNganh.getSlDgnl()) : "");
            txtSlVsat.setText(currentNganh.getSlVsat() != null ? String.valueOf(currentNganh.getSlVsat()) : "");
            txtSlThpt.setText(currentNganh.getSlThpt() != null ? String.valueOf(currentNganh.getSlVsat()) : "");
        }

        // cot trai
        pnlLeft.add(txtMaNganh);
        pnlLeft.add(txtTenNganh);
        pnlLeft.add(txtToHopGoc);
        pnlLeft.add(txtChiTieu);
        pnlLeft.add(txtDiemSan);
        pnlLeft.add(txtDiemTrungTuyen);
        pnlLeft.add(cbbTuyenThang);

        // cot phai
        pnlRight.add(cbbDgnl);
        pnlRight.add(cbbThpt);
        pnlRight.add(cbbVsat);
        pnlRight.add(txtSlXtt);
        pnlRight.add(txtSlDgnl);
        pnlRight.add(txtSlVsat);
        pnlRight.add(txtSlThpt);

        
        pnlMain.add(pnlLeft);
        pnlMain.add(pnlRight);
    }

    private void setNumericFilter(VerticalInputForm... fields) {
        for (VerticalInputForm f : fields) {
            PlainDocument doc = (PlainDocument) f.getTxtForm().getDocument();
            doc.setDocumentFilter(new NumericDocumentFilter());
        }
    }

    private void setAllFieldsDisable() {
        VerticalInputForm[] fields = {txtMaNganh, txtTenNganh, txtToHopGoc, txtChiTieu,
                txtDiemSan, txtDiemTrungTuyen, 
                txtSlXtt, txtSlDgnl, txtSlVsat, txtSlThpt};
        for (VerticalInputForm f : fields) {
            f.setDisable();
        }
        cbbTuyenThang.setDisable();
        cbbDgnl.setDisable();
        cbbThpt.setDisable();
        cbbVsat.setDisable();
    }
 private void bindToggle(VerticalComboBoxForm cbb, VerticalInputForm txt) {
    cbb.getComboBox().addActionListener(e -> {
        boolean enable = cbb.isSelectedYes();

        txt.getTxtForm().setEnabled(enable); 

        if (!enable) {
            txt.setText("0");
        }
    });
}

    private void initPnlButtons(String type) {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(10, 0, 20, 0));

        String btnText = type.equals("create") ? "Thêm mới" : "Lưu thay đổi";
        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnHuy = new ButtonCustom("Huỷ bỏ", "danger", 15);

        btnLuu.setPreferredSize(new Dimension(150, 48));
        btnHuy.setPreferredSize(new Dimension(150, 48));

        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                luuNganh(type);
            }
        });

        btnHuy.addActionListener(e -> dispose());

        if (!type.equals("view")) {
            pnlButtons.add(btnLuu);
        }
        pnlButtons.add(btnHuy);
    }
    

    private void luuNganh(String type) {
        XtNganh nganh = new XtNganh();

        nganh.setManganh(txtMaNganh.getText().trim());
        nganh.setTennganh(txtTenNganh.getText().trim());
        nganh.setNTohopgoc(txtToHopGoc.getText().trim());
        nganh.setNChitieu(Integer.parseInt(txtChiTieu.getText().trim()));
        nganh.setNDiemsan(parseBigDecimal(txtDiemSan.getText()));
        nganh.setNDiemtrungtuyen(parseBigDecimal(txtDiemTrungTuyen.getText()));

        nganh.setNTuyenthang(cbbTuyenThang.getSelectedValue());
        nganh.setNDgnl(cbbDgnl.getSelectedValue());
        nganh.setNThpt(cbbThpt.getSelectedValue());
        nganh.setNVsat(cbbVsat.getSelectedValue());

        // cho ve 0 neu khong chon phuong thuc do
        nganh.setSlXtt(cbbTuyenThang.isSelectedYes() 
            ? parseInteger(txtSlXtt.getText()) : 0);

        nganh.setSlDgnl(cbbDgnl.isSelectedYes() 
            ? parseInteger(txtSlDgnl.getText()) : 0);

        nganh.setSlThpt(cbbThpt.isSelectedYes() 
            ? parseInteger(txtSlThpt.getText()) : 0);

        nganh.setSlVsat(cbbVsat.isSelectedYes() 
            ? parseInteger(txtSlVsat.getText()) : 0);

        // bus
        if (type.equals("create")) {
            String message = bus.insertNganh(nganh);
            JOptionPane.showMessageDialog(this, message);

            if (message.contains("thành công")) {
                parent.loadDataTable(bus.getAllNganh());
                dispose();
            }
        } else {
            try {
                nganh.setIdnganh(currentNganh.getIdnganh());

                if (bus.updateNganh(nganh)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    parent.loadDataTable(bus.getAllNganh());
                    dispose();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
}

    private BigDecimal parseBigDecimal(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            return new BigDecimal(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInteger(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private boolean validateInput() {
        if (Validation.isEmpty(txtMaNganh.getText())) {
            JOptionPane.showMessageDialog(this, "Mã ngành không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtMaNganh.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtTenNganh.getText())) {
            JOptionPane.showMessageDialog(this, "Tên ngành không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtTenNganh.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtChiTieu.getText())) {
            JOptionPane.showMessageDialog(this, "Chỉ tiêu không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtChiTieu.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtToHopGoc.getText())) {
            JOptionPane.showMessageDialog(this, "Tổ hợp gốc không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtToHopGoc.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtSlDgnl.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng DGNL không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSlDgnl.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtSlThpt.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng THPT không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSlThpt.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtSlVsat.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng VSAT không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSlVsat.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtSlXtt.getText())) {
            JOptionPane.showMessageDialog(this, "Số lượng xét tuyển sớm không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSlXtt.getTxtForm().requestFocus();
            return false;
        }
        
        return true;
    }
}