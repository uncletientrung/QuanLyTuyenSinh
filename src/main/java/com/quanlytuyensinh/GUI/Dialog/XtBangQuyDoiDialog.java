package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.XtBangQuyDoiBUS;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.InputForm;
import com.quanlytuyensinh.GUI.Component.SelectForm;
import com.quanlytuyensinh.GUI.Panel.XtBangQuyDoiPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class XtBangQuyDoiDialog extends JDialog implements ActionListener {

    private XtBangQuyDoiPanel jpQD;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnLuu, btnHuyBo;
    private SelectForm cbxToHopMon;
    private InputForm txtPhanVi, txtMaQuyDoi;
    private InputForm txtDiemA, txtDiemB, txtDiemC, txtDiemD;
    private SelectForm cbxPhuongThuc;
    private XtBangQuyDoiBUS xtbangquydoiBUS;
    private String currentType;
    private XtBangQuyDoi currentData;

    public XtBangQuyDoiDialog(XtBangQuyDoiPanel jpQD, JFrame owner, String title, boolean modal, String type) {
        this(jpQD, owner, title, modal, type, null);
    }

    public XtBangQuyDoiDialog(XtBangQuyDoiPanel jpQD, JFrame owner, String title, boolean modal, String type, XtBangQuyDoi data) {
        super(owner, title, modal);
        this.jpQD = jpQD;
        this.xtbangquydoiBUS = new XtBangQuyDoiBUS();
        this.currentType = type;
        this.currentData = data;
        initComponents(type);
    }

    // Hardcode cho Tổ hợp(ĐGNL) & Môn(VSAT)
    private static final String[] TO_HOP_ARR = {"A00", "A01", "B00", "C00", "C01", "D01"};
    private static final String[] MON_ARR = {"TO", "VA", "LI", "HO", "SI", "DI", "N1"};

    public void initComponents(String type) {
        this.setSize(new Dimension(1000, 480));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);

        pnmain = new JPanel(new GridLayout(3, 3, 0, 0));
        pnmain.setBackground(Color.WHITE);
        pnmain.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cột 1        
        txtMaQuyDoi = new InputForm("Mã quy đổi");
        txtMaQuyDoi.setEditable(false);
        txtPhanVi = new InputForm("Phân vị");
        cbxToHopMon = new SelectForm("Tổ hợp", TO_HOP_ARR);

        // Cột 2
        String[] phuongthucArr = {"DGNL", "VSAT"};
        cbxPhuongThuc = new SelectForm("Phương thức", phuongthucArr);
        cbxPhuongThuc.getCbb().addActionListener(e -> {
            switchToHopMonDropdown(cbxPhuongThuc.getValue());
            updateMaQuyDoi();
        });
        txtDiemA = new InputForm("Điểm thấp nhất");
        txtDiemB = new InputForm("Điểm cao nhất");

        // Cột 3
        InputForm txtPhuongThucPhu = new InputForm("Phương thức");
        txtPhuongThucPhu.getTxtForm().setText("THPT");
        txtPhuongThucPhu.setEditable(false);
        txtDiemC = new InputForm("Điểm thấp nhất (THPT)");
        txtDiemD = new InputForm("Điểm cao nhất (THPT)");

        pnmain.add(txtMaQuyDoi);
        pnmain.add(cbxPhuongThuc);
        pnmain.add(txtPhuongThucPhu);
        pnmain.add(cbxToHopMon);
        pnmain.add(txtDiemA);
        pnmain.add(txtDiemC);
        pnmain.add(txtPhanVi);
        pnmain.add(txtDiemB);
        pnmain.add(txtDiemD);

        boolean isDetail = "detail".equals(type);
        if (isDetail) {
            cbxToHopMon.setDisable();
            txtPhanVi.setDisable();
            txtDiemA.setDisable();
            txtDiemB.setDisable();
            txtDiemC.setDisable();
            txtDiemD.setDisable();
            cbxPhuongThuc.setDisable();
        } else {
            cbxToHopMon.getCbb().addActionListener(e -> updateMaQuyDoi());
            addAutoGenerateListener(txtPhanVi);
        }

        if ("update".equals(type) && currentData != null) {
            loadDataToForm(currentData);
        }

        if ("detail".equals(type) && currentData != null) {
            loadDataToForm(currentData);
        }

        // Button
        pnbottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnbottom.setBackground(Color.WHITE);
        pnbottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        if ("detail".equals(type)) {
            btnHuyBo = new ButtonCustom("Đóng", "danger", 14);
            btnHuyBo.addActionListener(this);
            pnbottom.add(btnHuyBo);
        } else {
            String btnText = "create".equals(type) ? "Tạo mới" : "Lưu thông tin";
            btnLuu = new ButtonCustom(btnText, "success", 14);
            btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
            btnLuu.addActionListener(this);
            btnHuyBo.addActionListener(this);
            pnbottom.add(btnLuu);
            pnbottom.add(btnHuyBo);
        }

        this.add(pnmain, BorderLayout.CENTER);
        this.add(pnbottom, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void loadDataToForm(XtBangQuyDoi qd) {
        if ("DGNL".equals(qd.getDPhuongthuc())) {
            cbxPhuongThuc.getCbb().setSelectedIndex(0);
            switchToHopMonDropdown("DGNL");
            cbxToHopMon.getCbb().setSelectedItem(qd.getDTohop());
        } else {
            cbxPhuongThuc.getCbb().setSelectedIndex(1);
            switchToHopMonDropdown("VSAT");
            cbxToHopMon.getCbb().setSelectedItem(qd.getDMon());
        }

        txtPhanVi.setText(qd.getDPhanvi() != null ? qd.getDPhanvi() : "");
        txtDiemA.setText(qd.getDDiema() != null ? String.format("%.2f", qd.getDDiema()) : "0.00");
        txtDiemB.setText(qd.getDDiemb() != null ? String.format("%.2f", qd.getDDiemb()) : "0.00");
        txtDiemC.setText(qd.getDDiemc() != null ? String.format("%.2f", qd.getDDiemc()) : "0.00");
        txtDiemD.setText(qd.getDDiemd() != null ? String.format("%.2f", qd.getDDiemd()) : "0.00");
        txtMaQuyDoi.setText(qd.getDMaQuyDoi() != null ? qd.getDMaQuyDoi() : "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnLuu) {

            if ("detail".equals(currentType)) {
                return;
            }

            XtBangQuyDoi qd;
            if ("update".equals(currentType) && currentData != null) {
                qd = currentData;
            } else {
                qd = new XtBangQuyDoi();
            }

            qd.setDPhuongthuc(cbxPhuongThuc.getValue());
            qd.setDTohop("VSAT".equals(cbxPhuongThuc.getValue()) ? null : cbxToHopMon.getValue());
            qd.setDMon("VSAT".equals(cbxPhuongThuc.getValue()) ? cbxToHopMon.getValue() : null);
            try {
                qd.setDDiema(new BigDecimal(txtDiemA.getText().trim()).setScale(2, java.math.RoundingMode.HALF_UP));
                qd.setDDiemb(new BigDecimal(txtDiemB.getText().trim()).setScale(2, java.math.RoundingMode.HALF_UP));
                qd.setDDiemc(new BigDecimal(txtDiemC.getText().trim()).setScale(2, java.math.RoundingMode.HALF_UP));
                qd.setDDiemd(new BigDecimal(txtDiemD.getText().trim()).setScale(2, java.math.RoundingMode.HALF_UP));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Điểm số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            qd.setDMaQuyDoi(txtMaQuyDoi.getText().trim());
            qd.setDPhanvi(txtPhanVi.getText().trim());

            boolean success;
            try {
                if ("update".equals(currentType)) {
                    success = xtbangquydoiBUS.updateQuyDoi(qd);
                } else {
                    success = xtbangquydoiBUS.addQuyDoi(qd);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi validate", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "update".equals(currentType) ? "Cập nhật bảng quy đổi thành công!" : "Thêm bảng quy đổi thành công!");
                jpQD.loadDataTable(xtbangquydoiBUS.getAllQuyDoi());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "update".equals(currentType) ? "Cập nhật thất bại!" : "Thêm thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMaQuyDoi() {
        String phuongThuc = cbxPhuongThuc.getValue();
        String toHopMon = cbxToHopMon.getValue();
        String phanVi = txtPhanVi.getTxtForm().getText().trim();

        if (toHopMon == null || toHopMon.isEmpty() || phanVi.isEmpty()) {
            txtMaQuyDoi.getTxtForm().setText("");
        } else {
            String result = phuongThuc + "_" + toHopMon + "_" + phanVi;
            txtMaQuyDoi.getTxtForm().setText(result);
        }
    }

    private void addAutoGenerateListener(InputForm input) {
        input.getTxtForm().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateMaQuyDoi();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateMaQuyDoi();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateMaQuyDoi();
            }
        });
    }

    private void switchToHopMonDropdown(String phuongThuc) {
        // Xoá listenr để thêm item vào combobox
        ActionListener[] listeners = cbxToHopMon.getCbb().getActionListeners();
        for (ActionListener al : listeners) {
            cbxToHopMon.getCbb().removeActionListener(al);
        }

        cbxToHopMon.getCbb().removeAllItems();
        if ("VSAT".equals(phuongThuc)) {
            cbxToHopMon.getLblTitle().setText("Môn");
            for (String mon : MON_ARR) {
                cbxToHopMon.getCbb().addItem(mon);
            }
        } else {
            cbxToHopMon.getLblTitle().setText("Tổ hợp");
            for (String tohop : TO_HOP_ARR) {
                cbxToHopMon.getCbb().addItem(tohop);
            }
        }

        // Thêm lại listener
        for (ActionListener al : listeners) { 
            cbxToHopMon.getCbb().addActionListener(al);
        }
    }
}
