package GUI.Dialog;

import GUI.Component.ButtonCustom;
import GUI.Component.VerticalInputForm; 
import GUI.Component.NumericDocumentFilter;
import GUI.Panel.NganhPanel;
import BUS.XtNganhBUS;
import ENTITY.XtNganh;
import GUI.Component.VerticalComboBoxForm;
import helper.Validation;
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
        setNumericFilter(txtChiTieu, txtSlXtt, txtSlDgnl, txtSlVsat);

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
            txtSlThpt.setText(currentNganh.getSlThpt() != null ? currentNganh.getSlThpt() : "");
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
        nganh.setSlXtt(parseInteger(txtSlXtt.getText()));
        nganh.setSlDgnl(parseInteger(txtSlDgnl.getText()));
        nganh.setSlVsat(parseInteger(txtSlVsat.getText()));
        nganh.setSlThpt(txtSlThpt.getText().trim());

        if (type.equals("create")) {
            String message = bus.insertNganh(nganh);
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            if (message.contains("thành công")) {
                parent.loadDataTable(bus.getAllNganh());
                dispose();
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
        return true;
    }
}