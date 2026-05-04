package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import com.quanlytuyensinh.GUI.Component.NumericDocumentFilter;
import com.quanlytuyensinh.GUI.Panel.NganhToHopPanel;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtToHopMonThiBUS;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNganh;

import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import com.quanlytuyensinh.helper.Validation;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.PlainDocument;

public class NganhToHopDialog extends JDialog {

    private JComboBox<String> cbbManganh, cbbMatohop;

    private VerticalInputForm txtTbKeys, txtDolech;
    private VerticalInputForm txtMon1, txtHsMon1;
    private VerticalInputForm txtMon2, txtHsMon2;
    private VerticalInputForm txtMon3, txtHsMon3;

    private JPanel pnlBoolMon;
    private JLabel lblBoolMonDisplay;

    // Panel hiển thị chi tiết NK khi xem (view mode)
    private JPanel pnlNkDetail;

    private ButtonCustom btnLuu, btnHuy;

    private XtNganhToHopBUS bus      = new XtNganhToHopBUS();
    private XtNganhBUS      nganhBUS = new XtNganhBUS();
    private XtToHopMonThiBUS          XtToHopMonThiBUS   = new XtToHopMonThiBUS();

    // Cache danh sách để tra cứu khi cần
    private List<XtNganh>       listNganh = new ArrayList<>();
    private List<XtToHopMonThi> listToHop = new ArrayList<>();

    private NganhToHopPanel parent;
    private XtNganhToHop    currentRecord;
    private JPanel pnlMain, pnlButtons;


    public NganhToHopDialog(NganhToHopPanel parent, JFrame owner, String title, boolean modal,
                            String type, XtNganhToHop record) {
        super(owner, title, modal);
        this.parent        = parent;
        this.currentRecord = record;
        setTitle(title);
        init(type);
    }



    private void init(String type) {
        setSize(680, 720);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        loadLists();      
        initPnlMain(type);
        initPnlButtons(type);

        add(pnlMain,    BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        if (type.equals("view")) {
            setAllFieldsDisable();
        }

        setVisible(true);
    }

    
    private void loadLists() {
        listNganh = nganhBUS.getAllNganh();
        listToHop = XtToHopMonThiBUS.getList();  
    }

    // main panel

    private void initPnlMain(String type) {
        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(new EmptyBorder(20, 35, 10, 35));
        pnlMain.setBackground(Color.WHITE);

       
        JPanel pnlInfo = new JPanel(new GridLayout(2, 2, 25, 12));
        pnlInfo.setBackground(Color.WHITE);

        cbbManganh = buildComboNganh();
        cbbMatohop = buildComboToHop();
        txtTbKeys  = new VerticalInputForm("TB Keys");
        txtDolech  = new VerticalInputForm("Độ lệch");

        // TB Keys chỉ đọc , tự cập nhật theo listener
        txtTbKeys.getTxtForm().setEditable(false);
        txtTbKeys.getTxtForm().setBackground(new Color(245, 245, 245));

        pnlInfo.add(wrapCombo("Mã ngành",  cbbManganh));
        pnlInfo.add(wrapCombo("Mã tổ hợp", cbbMatohop));
        pnlInfo.add(txtTbKeys);
        pnlInfo.add(txtDolech);

     
        JPanel pnlMonThi = new JPanel(new GridLayout(3, 1, 0, 12));
        pnlMonThi.setBackground(Color.WHITE);
        pnlMonThi.setBorder(buildTitledBorder("Các môn thi"));

        txtMon1   = new VerticalInputForm("Môn 1");
        txtHsMon1 = new VerticalInputForm("Hệ số môn 1");
        txtMon2   = new VerticalInputForm("Môn 2");
        txtHsMon2 = new VerticalInputForm("Hệ số môn 2");
        txtMon3   = new VerticalInputForm("Môn 3");
        txtHsMon3 = new VerticalInputForm("Hệ số môn 3");

        // Tên môn tự điền từ DB, chỉ đọc
        setReadOnly(txtMon1, txtMon2, txtMon3);
        // Hệ số , chỉ nhập số
        setNumericFilter(txtHsMon1, txtHsMon2, txtHsMon3);

        pnlMonThi.add(buildMonRow(txtMon1, txtHsMon1));
        pnlMonThi.add(buildMonRow(txtMon2, txtHsMon2));
        pnlMonThi.add(buildMonRow(txtMon3, txtHsMon3));

        // --- Nhóm ngành áp dụng (tentohop) ---
        pnlBoolMon = new JPanel(new BorderLayout(0, 4));
        pnlBoolMon.setBackground(Color.WHITE);
        pnlBoolMon.setBorder(buildTitledBorder("Nhóm ngành áp dụng"));

        lblBoolMonDisplay = new JLabel("(Chọn mã tổ hợp để hiển thị)");
        lblBoolMonDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblBoolMonDisplay.setForeground(new Color(80, 80, 80));
        lblBoolMonDisplay.setBorder(new EmptyBorder(6, 10, 6, 10));
        pnlBoolMon.add(lblBoolMonDisplay, BorderLayout.CENTER);

        // --- Panel chi tiết NK (chỉ hiển thị khi xem/sửa bản ghi có NK) ---
        pnlNkDetail = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        pnlNkDetail.setBackground(Color.WHITE);
        pnlNkDetail.setBorder(buildTitledBorder("Chi tiết NK trong DB"));
        pnlNkDetail.setVisible(false); // ẩn mặc định, hiện khi fillData

        // Gắn listener SAU khi tất cả field đã tạo xong
        bindListeners();

        // Đổ dữ liệu nếu đang view/update
        if (currentRecord != null) {
            fillData();
        }

        // Ghép vào pnlMain
        pnlMain.add(pnlInfo);
        pnlMain.add(Box.createVerticalStrut(14));
        pnlMain.add(pnlMonThi);
        pnlMain.add(Box.createVerticalStrut(14));
        pnlMain.add(pnlBoolMon);
        pnlMain.add(Box.createVerticalStrut(8));
        pnlMain.add(pnlNkDetail);
    }



    private JComboBox<String> buildComboNganh() {
        JComboBox<String> cbb = new JComboBox<>();
        cbb.addItem("-- Chọn ngành --");
        for (XtNganh n : listNganh) {
            cbb.addItem(n.getManganh());
        }
        styleCombo(cbb);
        return cbb;
    }

    private JComboBox<String> buildComboToHop() {
        JComboBox<String> cbb = new JComboBox<>();
        cbb.addItem("-- Chọn tổ hợp --");
        for (XtToHopMonThi t : listToHop) {
            cbb.addItem(t.getMatohop());
        }
        styleCombo(cbb);
        return cbb;
    }

    private void styleCombo(JComboBox<String> cbb) {
        cbb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbb.setBackground(Color.WHITE);
        cbb.setPreferredSize(new Dimension(0, 38));
    }

 
    private JPanel wrapCombo(String labelText, JComboBox<String> cbb) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(60, 60, 60));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(cbb, BorderLayout.CENTER);
        return panel;
    }

    

    private void bindListeners() {
        // Cập nhật TB Keys khi đổi ngành
        cbbManganh.addActionListener(e -> updateTbKeys());

        // Cập nhật TB Keys + 3 môn + tentohop khi đổi tổ hợp
        cbbMatohop.addActionListener(e -> {
            updateTbKeys();
            updateMonFromToHop();
        });
    }

   
    private void updateTbKeys() {
        String ma    = getSelectedNganh();
        String tohop = getSelectedToHop();

        if (ma == null || tohop == null) {
            txtTbKeys.setText("");
        } else {
            txtTbKeys.setText(ma + "_" + tohop);
        }
    }

    // tu dien khi chon
    private void updateMonFromToHop() {
        String matohop = getSelectedToHop();
        if (matohop == null) {
            txtMon1.setText("");
            txtMon2.setText("");
            txtMon3.setText("");
            lblBoolMonDisplay.setText("(Chọn mã tổ hợp để hiển thị)");
            return;
        }

        for (XtToHopMonThi t : listToHop) {
            if (matohop.equals(t.getMatohop())) {
                txtMon1.setText(t.getMon1() != null ? t.getMon1() : "");
                txtMon2.setText(t.getMon2() != null ? t.getMon2() : "");
                txtMon3.setText(t.getMon3() != null ? t.getMon3() : "");
                lblBoolMonDisplay.setText(
                    t.getTentohop()!= null && !t.getTentohop().isEmpty()
                        ? t.getTentohop()
                        : "(Không có tên tổ hợp)");
                return;
            }
        }

        // Không tìm thấy trong cache
        txtMon1.setText("");
        txtMon2.setText("");
        txtMon3.setText("");
        lblBoolMonDisplay.setText("(Không tìm thấy tổ hợp)");
    }



    private void fillData() {
        // Chọn đúng item trong combobox (listener sẽ kích hoạt updateMonFromToHop)
        if (currentRecord.getManganh() != null) {
            cbbManganh.setSelectedItem(currentRecord.getManganh());
        }
        if (currentRecord.getMatohop() != null) {
            cbbMatohop.setSelectedItem(currentRecord.getMatohop());
        }

        // TB Keys và độ lệch
        txtTbKeys.setText(currentRecord.getTbKeys() != null ? currentRecord.getTbKeys() : "");
        txtDolech.setText(currentRecord.getDolech() != null ? currentRecord.getDolech().toPlainString() : "");

        // Hệ số 
        txtHsMon1.setText(currentRecord.getHsMon1() != null ? String.valueOf(currentRecord.getHsMon1()) : "");
        txtHsMon2.setText(currentRecord.getHsMon2() != null ? String.valueOf(currentRecord.getHsMon2()) : "");
        txtHsMon3.setText(currentRecord.getHsMon3() != null ? String.valueOf(currentRecord.getHsMon3()) : "");

    }



    private void luuNTH(String type) {
        XtNganhToHop nth = new XtNganhToHop();

        nth.setManganh(getSelectedNganh());
        nth.setMatohop(getSelectedToHop());
        nth.setTbKeys(txtTbKeys.getText().trim());
        nth.setDolech(parseBigDecimal(txtDolech.getText()));
        nth.setThMon1(txtMon1.getText().trim());
        nth.setHsMon1(parseInteger(txtHsMon1.getText()));
        nth.setThMon2(txtMon2.getText().trim());
        nth.setHsMon2(parseInteger(txtHsMon2.getText()));
        nth.setThMon3(txtMon3.getText().trim());
        nth.setHsMon3(parseInteger(txtHsMon3.getText()));

        // Lấy 3 tên môn đang được chọn từ tổ hợp (uppercase để so sánh)
        String mon1 = txtMon1.getText().trim().toUpperCase();
        String mon2 = txtMon2.getText().trim().toUpperCase();
        String mon3 = txtMon3.getText().trim().toUpperCase();

        // Set boolean: môn nào nằm trong 3 môn của tổ hợp thì true, còn lại thì false
        nth.setN1  (mon1.equals("N1")   || mon2.equals("N1")   || mon3.equals("N1"));
        nth.setTo  (mon1.equals("TO")   || mon2.equals("TO")   || mon3.equals("TO"));
        nth.setLi  (mon1.equals("LI")   || mon2.equals("LI")   || mon3.equals("LI"));
        nth.setHo  (mon1.equals("HO")   || mon2.equals("HO")   || mon3.equals("HO"));
        nth.setSi  (mon1.equals("SI")   || mon2.equals("SI")   || mon3.equals("SI"));
        nth.setVa  (mon1.equals("VA")   || mon2.equals("VA")   || mon3.equals("VA"));
        nth.setSu  (mon1.equals("SU")   || mon2.equals("SU")   || mon3.equals("SU"));
        nth.setDi  (mon1.equals("DI")   || mon2.equals("DI")   || mon3.equals("DI"));
        nth.setTi  (mon1.equals("TI")   || mon2.equals("TI")   || mon3.equals("TI"));
        nth.setGdcd(mon1.equals("GDCD") || mon2.equals("GDCD") || mon3.equals("GDCD")); 
        nth.setKtpl(mon1.equals("KTPL") || mon2.equals("KTPL") || mon3.equals("KTPL"));
        nth.setCncn(mon1.equals("CNCN")|| mon2.equals("CNCN") || mon3.equals("CNCN"));
        nth.setCnnn(mon1.equals("CNNN")|| mon2.equals("CNNN") || mon3.equals("CNNN"));
        // NK1-NK6: mỗi cột độc lập theo tên môn
        nth.setNk1 (mon1.equals("NK1")  || mon2.equals("NK1")  || mon3.equals("NK1"));
        nth.setNk2 (mon1.equals("NK2")  || mon2.equals("NK2")  || mon3.equals("NK2"));
        nth.setNk3 (mon1.equals("NK3")  || mon2.equals("NK3")  || mon3.equals("NK3"));
        nth.setNk4 (mon1.equals("NK4")  || mon2.equals("NK4")  || mon3.equals("NK4"));
        nth.setNk5 (mon1.equals("NK5")  || mon2.equals("NK5")  || mon3.equals("NK5"));
        nth.setNk6 (mon1.equals("NK6")  || mon2.equals("NK6")  || mon3.equals("NK6"));

        if (type.equals("create")) {
            if (bus.addNTH(nth)) {
                JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
                parent.loadDataTable(bus.getAll());
                dispose();
            }
        } else {
            nth.setId(currentRecord.getId());

                if (bus.updateNTH(nth)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    parent.loadDataTable(bus.getAll());
                    dispose();
                }
        }
    }



    private boolean validateInput() {
        if (getSelectedNganh() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã ngành!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            cbbManganh.requestFocus();
            return false;
        }
        if (getSelectedToHop() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã tổ hợp!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            cbbMatohop.requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtHsMon1.getText())) {
            JOptionPane.showMessageDialog(this, "Hệ số môn 1 không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHsMon1.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtHsMon2.getText())) {
            JOptionPane.showMessageDialog(this, "Hệ số môn 2 không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHsMon2.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtHsMon3.getText())) {
            JOptionPane.showMessageDialog(this, "Hệ số môn 3 không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHsMon3.getTxtForm().requestFocus();
            return false;
        }
        if (Validation.isEmpty(txtDolech.getText())) {
            JOptionPane.showMessageDialog(this, "Độ lệch không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtDolech.getTxtForm().requestFocus();
            return false;
        }
        return true;
    }

    // button

    private void initPnlButtons(String type) {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(5, 0, 15, 0));

        String btnText = type.equals("create") ? "Thêm mới" : "Lưu thay đổi";
        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnHuy = new ButtonCustom("Huỷ bỏ", "danger",  15);

        btnLuu.setPreferredSize(new Dimension(150, 48));
        btnHuy.setPreferredSize(new Dimension(150, 48));

        btnLuu.addActionListener(e -> {
            if (validateInput()) luuNTH(type);
        });
        btnHuy.addActionListener(e -> dispose());

        if (!type.equals("view")) pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
    }



    // null nếu người dùng chưa chọn 
    private String getSelectedNganh() {
        Object sel = cbbManganh.getSelectedItem();
        if (sel == null || sel.toString().startsWith("--")) return null;
        return sel.toString();
    }

    private String getSelectedToHop() {
        Object sel = cbbMatohop.getSelectedItem();
        if (sel == null || sel.toString().startsWith("--")) return null;
        return sel.toString();
    }

    private JPanel buildMonRow(VerticalInputForm fMon, VerticalInputForm fHs) {
        JPanel row = new JPanel(new GridLayout(1, 2, 20, 0));
        row.setBackground(Color.WHITE);
        row.add(fMon);
        row.add(fHs);
        return row;
    }

    private void setNumericFilter(VerticalInputForm... fields) {
        for (VerticalInputForm f : fields) {
            PlainDocument doc = (PlainDocument) f.getTxtForm().getDocument();
            doc.setDocumentFilter(new NumericDocumentFilter());
        }
    }

    private void setReadOnly(VerticalInputForm... fields) {
        for (VerticalInputForm f : fields) {
            f.getTxtForm().setEditable(false);
            f.getTxtForm().setBackground(new Color(245, 245, 245));
        }
    }

    private void setAllFieldsDisable() {
        cbbManganh.setEnabled(false);
        cbbMatohop.setEnabled(false);
        for (VerticalInputForm f : new VerticalInputForm[]{
                txtTbKeys, txtDolech,
                txtMon1, txtHsMon1, txtMon2, txtHsMon2, txtMon3, txtHsMon3}) {
            f.setDisable();
        }
    }

    private BigDecimal parseBigDecimal(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try { return new BigDecimal(text.trim()); }
        catch (Exception e) { return null; }
    }

    private Integer parseInteger(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try { return Integer.parseInt(text.trim()); }
        catch (Exception e) { return null; }
    }

    private javax.swing.border.Border buildTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 230), 1),
                title,
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(60, 130, 180));
    }
}