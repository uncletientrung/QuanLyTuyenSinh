package GUI.Dialog;

import BUS.XtDiemCongXetTuyenBUS;
import BUS.XtThisinhXetTuyen25BUS;
import ENTITY.XtDiemCongXetTuyen;
import ENTITY.XtThisinhXetTuyen25;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.SelectForm;
import GUI.Panel.XtDiemCongXetTuyenPanel;
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

public class XtDiemCongXetTuyenDialog extends JDialog implements ActionListener {

    private XtDiemCongXetTuyenPanel jpDC;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnLuu, btnHuyBo;

    private SelectForm cbxCccd;
    private InputForm txtMaNganh, txtMaToHop;
    private InputForm txtDiemCC, txtDiemUtxt, txtDiemTong;
    private InputForm txtPhuongThuc, txtGhiChu, txtDcKeys;

    private XtDiemCongXetTuyenBUS diemCongBUS;
    private XtThisinhXetTuyen25BUS thiSinhBUS;
    private String currentType;
    private XtDiemCongXetTuyen currentData;

    public XtDiemCongXetTuyenDialog(XtDiemCongXetTuyenPanel jpDC, JFrame owner, String title, boolean modal, String type) {
        this(jpDC, owner, title, modal, type, null);
    }

    public XtDiemCongXetTuyenDialog(XtDiemCongXetTuyenPanel jpDC, JFrame owner, String title, boolean modal, String type, XtDiemCongXetTuyen data) {
        super(owner, title, modal);
        this.jpDC = jpDC;
        this.diemCongBUS = new XtDiemCongXetTuyenBUS();
        this.thiSinhBUS = new XtThisinhXetTuyen25BUS();
        this.currentType = type;
        this.currentData = data;
        initComponents(type);
    }

    public void initComponents(String type) {
        this.setSize(new Dimension(1000, 480));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);

        pnmain = new JPanel(new GridLayout(3, 3, 0, 0));
        pnmain.setBackground(Color.WHITE);
        pnmain.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cột 1
        String[] cccdArr = thiSinhBUS.getAllThiSinh().stream()
                .map(XtThisinhXetTuyen25::getCccd)
                .toArray(String[]::new);
        cbxCccd = new SelectForm("CCCD", cccdArr);
        cbxCccd.getCbb().addActionListener(e -> updateDcKeys());

        txtMaNganh = new InputForm("Mã ngành");
        txtMaToHop = new InputForm("Mã tổ hợp");

        // Cột 2
        txtDiemCC = new InputForm("Điểm CC");
        txtDiemUtxt = new InputForm("Điểm UTXT");
        txtDiemTong = new InputForm("Điểm tổng");
        txtDiemTong.setEditable(false);
        txtDiemTong.getTxtForm().setText("0.00");

        // Cột 3
        txtPhuongThuc = new InputForm("Phương thức");
        txtGhiChu = new InputForm("Ghi chú");
        txtDcKeys = new InputForm("DC Keys");
        txtDcKeys.setEditable(false);

        pnmain.add(cbxCccd);
        pnmain.add(txtDiemCC);
        pnmain.add(txtPhuongThuc);
        pnmain.add(txtMaNganh);
        pnmain.add(txtDiemUtxt);
        pnmain.add(txtGhiChu);
        pnmain.add(txtMaToHop);
        pnmain.add(txtDiemTong);
        pnmain.add(txtDcKeys);

        boolean isDetail = "detail".equals(type);
        if (isDetail) {
            cbxCccd.setDisable();
            txtMaNganh.setDisable();
            txtMaToHop.setDisable();
            txtDiemCC.setDisable();
            txtDiemUtxt.setDisable();
            txtPhuongThuc.setDisable();
            txtGhiChu.setDisable();
        } else {
            addAutoGenerateListener(txtMaNganh);
            addAutoGenerateListener(txtMaToHop);
            addDiemTongListener(txtDiemCC);
            addDiemTongListener(txtDiemUtxt);
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

    private void loadDataToForm(XtDiemCongXetTuyen dc) {
        if (dc.getTsCccd() != null) {
            javax.swing.ComboBoxModel<String> model = cbxCccd.getCbb().getModel();
            boolean found = false;
            for (int i = 0; i < model.getSize(); i++) {
                if (dc.getTsCccd().equals(model.getElementAt(i))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                cbxCccd.getCbb().addItem(dc.getTsCccd());
            }
            cbxCccd.getCbb().setSelectedItem(dc.getTsCccd());
        }
        txtMaNganh.setText(dc.getMaNganh() != null ? dc.getMaNganh() : "");
        txtMaToHop.setText(dc.getMaToHop() != null ? dc.getMaToHop() : "");
        txtDiemCC.setText(dc.getDiemCC() != null
                ? String.format("%.2f", dc.getDiemCC()) : "");
        txtDiemUtxt.setText(dc.getDiemUtxt() != null
                ? String.format("%.2f", dc.getDiemUtxt()) : "");
        txtDiemTong.setText(dc.getDiemTong() != null
                ? String.format("%.2f", dc.getDiemTong()) : "0.00");
        txtPhuongThuc.setText(dc.getPhuongThuc() != null ? dc.getPhuongThuc() : "");
        txtGhiChu.setText(dc.getGhiChu() != null ? dc.getGhiChu() : "");
        txtDcKeys.setText(dc.getDcKeys() != null ? dc.getDcKeys() : "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (btnLuu != null && e.getSource() == btnLuu) {
            if ("detail".equals(currentType)) {
                return;
            }

            XtDiemCongXetTuyen dc;
            if ("update".equals(currentType) && currentData != null) {
                dc = currentData;
            } else {
                dc = new XtDiemCongXetTuyen();
            }

            dc.setTsCccd((String) cbxCccd.getCbb().getSelectedItem());
            dc.setMaNganh(txtMaNganh.getText().trim());
            dc.setMaToHop(txtMaToHop.getText().trim());
            dc.setPhuongThuc(txtPhuongThuc.getText().trim());
            dc.setGhiChu(txtGhiChu.getText().trim());
            dc.setDcKeys(txtDcKeys.getText().trim());

            try {
                String rawCC = txtDiemCC.getText().trim();
                String rawUtxt = txtDiemUtxt.getText().trim();

                BigDecimal diemCC = (rawCC.isEmpty() ? BigDecimal.ZERO : new BigDecimal(rawCC))
                        .setScale(2, java.math.RoundingMode.HALF_UP);
                BigDecimal diemUtxt = (rawUtxt.isEmpty() ? BigDecimal.ZERO : new BigDecimal(rawUtxt))
                        .setScale(2, java.math.RoundingMode.HALF_UP);

                dc.setDiemCC(diemCC);
                dc.setDiemUtxt(diemUtxt);
                dc.setDiemTong(diemCC.add(diemUtxt));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Điểm số không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success;
            try {
                if ("update".equals(currentType)) {
                    success = diemCongBUS.updateDiemCong(dc);
                } else {
                    success = diemCongBUS.addDiemCong(dc);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi validate", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "update".equals(currentType) ? "Cập nhật thành công!" : "Thêm mới thành công!");
                jpDC.loadDataTable(diemCongBUS.getAllDiemCong());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "update".equals(currentType) ? "Cập nhật thất bại!" : "Thêm thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateDcKeys() {
        String cccd = (String) cbxCccd.getCbb().getSelectedItem();
        String maNganh = txtMaNganh.getText().trim().toUpperCase();
        String maToHop = txtMaToHop.getText().trim().toUpperCase();

        if (cccd == null || cccd.isEmpty() || maNganh.isEmpty() || maToHop.isEmpty()) {
            txtDcKeys.getTxtForm().setText("");
        } else {
            txtDcKeys.getTxtForm().setText(cccd + "_" + maNganh + "_" + maToHop);
        }
    }

    private void updateDiemTong() {
        String rawCC = txtDiemCC.getText().trim();
        String rawUtxt = txtDiemUtxt.getText().trim();

        try {
            BigDecimal diemCC = rawCC.isEmpty() ? BigDecimal.ZERO : new BigDecimal(rawCC);
            BigDecimal diemUtxt = rawUtxt.isEmpty() ? BigDecimal.ZERO : new BigDecimal(rawUtxt);

            BigDecimal tong = diemCC.add(diemUtxt);
            txtDiemTong.getTxtForm().setText(String.format("%.2f", tong));
        } catch (NumberFormatException e) {
            txtDiemTong.getTxtForm().setText("0.00");
        }
    }

    private void addAutoGenerateListener(InputForm input) {
        input.getTxtForm().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateDcKeys();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateDcKeys();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateDcKeys();
            }
        });
    }

    private void addDiemTongListener(InputForm input) {
        input.getTxtForm().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateDiemTong();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateDiemTong();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateDiemTong();
            }
        });
    }
}
