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

        initPnlMain(type);
        initPnlButtons(type);

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

        if (type.equals("view")) {
            setAllFieldsDisable();
        }

        this.setVisible(true);
    }

    private void initPnlMain(String type) {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

        JPanel pnlLeft = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlLeft.setBackground(Color.WHITE);

        JPanel pnlRight = new JPanel(new GridLayout(7, 1, 0, 15));
        pnlRight.setBackground(Color.WHITE);

        // Tạo components
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
        setNumericFilter(txtChiTieu, txtSlXtt, txtSlDgnl, txtSlVsat, txtSlThpt);

        if (type.equals("create")) {
            
            for (VerticalInputForm sl : new VerticalInputForm[]{txtSlXtt, txtSlDgnl, txtSlVsat, txtSlThpt}) {
                sl.setText("0");
                sl.getTxtForm().setEnabled(false);
            }
        } else {
            
            bindToggle(cbbTuyenThang, txtSlXtt);
            bindToggle(cbbDgnl, txtSlDgnl);
            bindToggle(cbbThpt, txtSlThpt);
            bindToggle(cbbVsat, txtSlVsat);
        }

        // Đổ dữ liệu nếu edit/view
        if (currentNganh != null) {
            txtMaNganh.setText(currentNganh.getManganh());
            txtTenNganh.setText(currentNganh.getTennganh());
            txtToHopGoc.setText(currentNganh.getNTohopgoc() != null ? currentNganh.getNTohopgoc().trim() : "");
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
            txtSlThpt.setText(currentNganh.getSlThpt() != null ? String.valueOf(currentNganh.getSlThpt()) : "");
        }

        pnlLeft.add(txtMaNganh);
        pnlLeft.add(txtTenNganh);
        pnlLeft.add(txtToHopGoc);
        pnlLeft.add(cbbTuyenThang);
        pnlLeft.add(cbbDgnl);
        pnlLeft.add(cbbVsat);
        pnlLeft.add(cbbThpt);

        pnlRight.add(txtChiTieu);
        pnlRight.add(txtDiemSan);
        pnlRight.add(txtDiemTrungTuyen);
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
        try {
            XtNganh nganh = new XtNganh();

            nganh.setManganh(txtMaNganh.getText().trim());
            nganh.setTennganh(txtTenNganh.getText().trim());
            String toHopGoc = txtToHopGoc.getText().trim();
            nganh.setNTohopgoc(toHopGoc.isEmpty() ? null : toHopGoc);
            nganh.setNChitieu(Integer.parseInt(txtChiTieu.getText().trim()));
            nganh.setNDiemsan(parseBigDecimal(txtDiemSan.getText()));
            nganh.setNDiemtrungtuyen(parseBigDecimal(txtDiemTrungTuyen.getText()));

            nganh.setNTuyenthang(cbbTuyenThang.getSelectedValue());
            nganh.setNDgnl(cbbDgnl.getSelectedValue());
            nganh.setNThpt(cbbThpt.getSelectedValue());
            nganh.setNVsat(cbbVsat.getSelectedValue());

            if (type.equals("create")) {
                // khi add
                nganh.setSlXtt(parseInteger(txtSlXtt.getText()) != null ? parseInteger(txtSlXtt.getText()) : 0);
                nganh.setSlDgnl(parseInteger(txtSlDgnl.getText()) != null ? parseInteger(txtSlDgnl.getText()) : 0);
                nganh.setSlThpt(parseInteger(txtSlThpt.getText()) != null ? parseInteger(txtSlThpt.getText()) : 0);
                nganh.setSlVsat(parseInteger(txtSlVsat.getText()) != null ? parseInteger(txtSlVsat.getText()) : 0);
            } else {
                // khi sua
                nganh.setSlXtt(cbbTuyenThang.isSelectedYes() ? parseInteger(txtSlXtt.getText()) : 0);
                nganh.setSlDgnl(cbbDgnl.isSelectedYes() ? parseInteger(txtSlDgnl.getText()) : 0);
                nganh.setSlThpt(cbbThpt.isSelectedYes() ? parseInteger(txtSlThpt.getText()) : 0);
                nganh.setSlVsat(cbbVsat.isSelectedYes() ? parseInteger(txtSlVsat.getText()) : 0);
            }

            if (type.equals("create")) {
                if (bus.insertNganh(nganh)) {
                    JOptionPane.showMessageDialog(this, "Thêm mới ngành thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    parent.loadDataTable(bus.getAllNganh());
                    dispose();
                }
            } else {
                nganh.setIdnganh(currentNganh.getIdnganh());
                if (bus.updateNganh(nganh)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật ngành thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    parent.loadDataTable(bus.getAllNganh());
                    dispose();
                }
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        return true;
    }
}