package GUI.Dialog;

import BUS.XtBangQuyDoiBUS;
import ENTITY.XtBangQuyDoi;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.XtBangQuyDoiPanel;
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
import javax.swing.text.PlainDocument;

public class XtBangQuyDoiDialog extends JDialog implements ActionListener {

    private XtBangQuyDoiPanel jpQD;
    private JPanel pnmain, pnbottom;
    private ButtonCustom btnThem, btnHuyBo;
    private InputForm txtToHopMon, txtPhanVi, txtMaQuyDoi;
    private InputForm txtDiemA, txtDiemB, txtDiemC, txtDiemD;
    private SelectForm cbxPhuongThuc;
    private XtBangQuyDoiBUS xtbangquydoiBUS;

    public XtBangQuyDoiDialog(XtBangQuyDoiPanel jpQD, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpQD = jpQD;
        this.xtbangquydoiBUS = new XtBangQuyDoiBUS();
        initComponents(type);
    }

    public void initComponents(String type) {
        this.setSize(new Dimension(1000, 480));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);

        pnmain = new JPanel(new GridLayout(3, 3, 0, 0));
        pnmain.setBackground(Color.WHITE);
        pnmain.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cột 1        
        txtMaQuyDoi = new InputForm("Mã quy đổi");
        txtMaQuyDoi.setEditable(false);
        txtPhanVi = new InputForm("Phân vị");
        txtToHopMon = new InputForm("Tổ hợp");

        // Cột 2
        String[] phuongthucArr = {"DGNL", "VSAT"};
        cbxPhuongThuc = new SelectForm("Phương thức", phuongthucArr);
        cbxPhuongThuc.getCbb().addActionListener(e -> {
            String selected = cbxPhuongThuc.getValue();
            if ("VSAT".equals(selected)) {
                txtToHopMon.getLblTitle().setText("Môn");
            } else {
                txtToHopMon.getLblTitle().setText("Tổ hợp");
            }
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

        setupNumericFilter(txtDiemA);
        setupNumericFilter(txtDiemB);
        setupNumericFilter(txtDiemC);
        setupNumericFilter(txtDiemD);

        pnmain.add(txtMaQuyDoi);
        pnmain.add(cbxPhuongThuc);
        pnmain.add(txtPhuongThucPhu);
        pnmain.add(txtToHopMon);
        pnmain.add(txtDiemA);
        pnmain.add(txtDiemC);
        pnmain.add(txtPhanVi);
        pnmain.add(txtDiemB);
        pnmain.add(txtDiemD);

        addAutoGenerateListener(txtToHopMon);
        addAutoGenerateListener(txtPhanVi);

        // Button
        pnbottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnbottom.setBackground(Color.WHITE);
        pnbottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnThem = new ButtonCustom("Tạo mới", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);

        btnThem.addActionListener(this);
        btnHuyBo.addActionListener(this);

        pnbottom.add(btnThem);
        pnbottom.add(btnHuyBo);

        this.add(pnmain, BorderLayout.CENTER);
        this.add(pnbottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setupNumericFilter(InputForm input) {
        PlainDocument doc = (PlainDocument) input.getTxtForm().getDocument();
        doc.setDocumentFilter(new NumericDocumentFilter());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnThem) {

            // Tạo entity
            XtBangQuyDoi qd = new XtBangQuyDoi();
            qd.setDPhuongthuc(cbxPhuongThuc.getValue());
            qd.setDTohop("VSAT".equals(cbxPhuongThuc.getValue()) ? null : txtToHopMon.getText().trim());
            qd.setDMon("VSAT".equals(cbxPhuongThuc.getValue()) ? txtToHopMon.getText().trim() : null);
            qd.setDDiema(new BigDecimal(txtDiemA.getText().trim()));
            qd.setDDiemb(new BigDecimal(txtDiemB.getText().trim()));
            qd.setDDiemc(new BigDecimal(txtDiemC.getText().trim()));
            qd.setDDiemd(new BigDecimal(txtDiemD.getText().trim()));
            qd.setDMaQuyDoi(txtMaQuyDoi.getText().trim());
            qd.setDPhanvi(txtPhanVi.getText().trim());

            // Lưu
            if (xtbangquydoiBUS.addQuyDoi(qd)) {
                JOptionPane.showMessageDialog(this, "Thêm bảng quy đổi thành công!");
                jpQD.loadDataTable(xtbangquydoiBUS.getAllQuyDoi());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateMaQuyDoi() {
        String phuongThuc = cbxPhuongThuc.getValue();
        String toHopMon = txtToHopMon.getTxtForm().getText().trim().toUpperCase();
        String phanVi = txtPhanVi.getTxtForm().getText().trim();

        if (toHopMon.isEmpty() || phanVi.isEmpty()) {
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
}
