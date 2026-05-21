package com.quanlytuyensinh.GUI.Dialog;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import org.springframework.beans.BeanUtils;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.InputForm;
import com.quanlytuyensinh.GUI.Panel.DiemThiTHPTPanel;

public class THPTDialog extends JDialog implements ActionListener{
    private DiemThiTHPTPanel parentPanel;
    private XtDiemThiXetTuyenBUS bus;
    private JFrame main;
    private JPanel pmain, pbottom;
    private InputForm cccdField, toanField, vanField, lyField, hoaField, sinhField,
                    suField, diaField, gdcdField, n1ThiField, n1CcField, cncnField,
                    cnnnField, tinField, ktplField, nk1Field, nk2Field, nk3Field,
                    nk4Field, nk5Field, nk6Field;
    private XtDiemThiXetTuyen selectedRow;
    private XtDiemThiXetTuyen newRow;
    private ButtonCustom btnConfirm, btnCancel;


    public THPTDialog(DiemThiTHPTPanel parentPanel, XtDiemThiXetTuyenBUS bus, JFrame main,
            XtDiemThiXetTuyen selectedRow, String title) {
        super(main, title, true);
        this.parentPanel = parentPanel;
        this.bus = bus;
        this.main = main;
        this.selectedRow = selectedRow;
        initComponent();
        this.setLocationRelativeTo(main);
    }

    public THPTDialog() {}

    private void initComponent() {
        this.setSize(new Dimension(400, 700));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);

        pmain = new JPanel(new GridBagLayout());
        pmain.setBorder(new EmptyBorder(10, 10, 10, 10));
        pmain.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        cccdField = new InputForm("Căn cước công dân", true);
        pmain.add(cccdField, gbc);
        gbc.gridy++;

        
        //Separator
        gbc.insets = new Insets(6, 0, 0, 0);

        JPanel sep1 = new JPanel();
        sep1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        sep1.setBackground(Color.WHITE);

        pmain.add(sep1, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(0, 0, 0, 0);
        //Separator

        toanField = new InputForm("Toán", true);
        pmain.add(toanField, gbc);
        gbc.gridy++;

        vanField = new InputForm("Văn", true);
        pmain.add(vanField, gbc);
        gbc.gridy++;

        lyField = new InputForm("Lý", true);
        pmain.add(lyField, gbc);
        gbc.gridy++;

        hoaField = new InputForm("Hóa", true);
        pmain.add(hoaField, gbc);
        gbc.gridy++;

        sinhField = new InputForm("Sinh", true);
        pmain.add(sinhField, gbc);
        gbc.gridy++;

        suField = new InputForm("Sử", true);
        pmain.add(suField, gbc);
        gbc.gridy++;

        diaField = new InputForm("Địa", true);
        pmain.add(diaField, gbc);
        gbc.gridy++;

        gdcdField = new InputForm("GDCD", true);
        pmain.add(gdcdField, gbc);
        gbc.gridy++;

        //Separator
        gbc.insets = new Insets(6, 0, 0, 0);
        
        JPanel sep2 = new JPanel();
        sep2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        sep2.setBackground(Color.WHITE);

        pmain.add(sep2, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(0, 0, 0, 0);
        //Separator

        n1ThiField = new InputForm("Tiếng Anh (Thi)", true);
        pmain.add(n1ThiField, gbc);
        gbc.gridy++;

        n1CcField = new InputForm("Tiếng Anh (Chứng chỉ)", true);
        pmain.add(n1CcField, gbc);
        gbc.gridy++;

        cncnField = new InputForm("Công nghệ công nghiệp", true);
        pmain.add(cncnField, gbc);
        gbc.gridy++;

        cnnnField = new InputForm("Công nghệ nông nghiệp", true);
        pmain.add(cnnnField, gbc);
        gbc.gridy++;

        tinField = new InputForm("Tin", true);
        pmain.add(tinField, gbc);
        gbc.gridy++;

        ktplField = new InputForm("Kinh tế pháp luật", true);
        pmain.add(ktplField, gbc);
        gbc.gridy++;

        //Separator
        gbc.insets = new Insets(6, 0, 0, 0);
        
        JPanel sep3 = new JPanel();
        sep3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        sep3.setBackground(Color.WHITE);

        pmain.add(sep3, gbc);
        gbc.gridy++;

        gbc.insets = new Insets(0, 0, 0, 0);
        //Separator

        nk1Field = new InputForm("Kể chuyện - Đọc diễn cảm", true);
        pmain.add(nk1Field, gbc);
        gbc.gridy++;

        nk2Field = new InputForm("Hát – Nhạc", true);
        pmain.add(nk2Field, gbc);
        gbc.gridy++;

        nk3Field = new InputForm("Hình họa", true);
        pmain.add(nk3Field, gbc);
        gbc.gridy++;

        nk4Field = new InputForm("Trang trí", true);
        pmain.add(nk4Field, gbc);
        gbc.gridy++;

        nk5Field = new InputForm("Hát – Nhạc cụ", true);
        pmain.add(nk5Field, gbc);
        gbc.gridy++;

        nk6Field = new InputForm("Xướng âm - Thẩm âm - Tiết tấu", true);
        pmain.add(nk6Field, gbc);
        gbc.gridy++;

        applyFilter();

        btnConfirm = new ButtonCustom("Xác nhận", "success", 20);
        btnCancel = new ButtonCustom("Huỷ bỏ", "danger", 20);
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);

        JPanel line1 = new JPanel();
        line1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        
        JPanel line2 = new JPanel();
        line2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        
        JPanel line3 = new JPanel();
        line3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));

        pbottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pbottom.setBackground(Color.WHITE);
        pbottom.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        pbottom.add(btnConfirm);
        pbottom.add(btnCancel);    

        if (selectedRow != null) {
            cccdField.setText(selectedRow.getCccd());

            set(toanField, selectedRow.getTo());
            set(vanField, selectedRow.getVa());
            set(lyField, selectedRow.getLi());
            set(hoaField, selectedRow.getHo());
            set(sinhField, selectedRow.getSi());
            set(suField, selectedRow.getSu());
            set(diaField, selectedRow.getDi());
            set(gdcdField, selectedRow.getGdcd());

            set(n1ThiField, selectedRow.getN1Thi());
            set(n1CcField, selectedRow.getN1Cc());

            set(cncnField, selectedRow.getCncn());
            set(cnnnField, selectedRow.getCnnn());

            set(tinField, selectedRow.getTi());
            set(ktplField, selectedRow.getKtpl());

            set(nk1Field, selectedRow.getNk1());
            set(nk2Field, selectedRow.getNk2());
            set(nk3Field, selectedRow.getNk3());
            set(nk4Field, selectedRow.getNk4());
            set(nk5Field, selectedRow.getNk5());
            set(nk6Field, selectedRow.getNk6());
        }
        JScrollPane container = new JScrollPane(pmain);
        container.getVerticalScrollBar().setUnitIncrement(16);
        container.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(container, BorderLayout.CENTER);
        this.add(pbottom, BorderLayout.SOUTH);
    }

    private void set(InputForm field, BigDecimal value) {
        field.setText(value != null ? value.toString() : "");
    }

    private void applyFilter() { 
        DocumentFilter filter = new DocumentFilter() {

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs)
                throws BadLocationException {

            String fixedText = text.replaceAll("[^0-9.]", "");

            Document doc = fb.getDocument();
            String currentText = doc.getText(0, doc.getLength());

            String newText = currentText.substring(0, offset)
                    + fixedText
                    + currentText.substring(offset + length);

            if (newText.isEmpty()) {
                super.replace(fb, offset, length, fixedText, attrs);
                return;
            }

            if (newText.chars().filter(ch -> ch == '.').count() > 1) {
                return;
            }

            if (newText.contains(".")) {
                String[] parts = newText.split("\\.");
                if (parts.length > 2) return;

                if (parts.length == 2 && parts[1].length() > 2) return;
            }

            try {
                double value = Double.parseDouble(newText);

                if (value >= 0 && value <= 10) {
                    super.replace(fb, offset, length, fixedText, attrs);
                }

            } catch (NumberFormatException e) {
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset,
                                String string, AttributeSet attr)
                throws BadLocationException {
            replace(fb, offset, 0, string, attr);
        }
    };

        // ((AbstractDocument) cccdField.getTxtForm().getDocument()).setDocumentFilter(new DocumentFilter() {
        //     @Override
        //     public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
        //         throws BadLocationException {
                
        //         // Convert input to uppercase and remove anything that isn't A-Z or 0-9
        //         String fixedText = text.toUpperCase().replaceAll("[^0-9]", "");
        //         Document doc = fb.getDocument();
        //         String currentText = doc.getText(0, doc.getLength());

        //         String newText = currentText.substring(0, offset)
        //                 + fixedText
        //                 + currentText.substring(offset + length);

        //         if (newText.length() <= 12) {
        //             super.replace(fb, offset, length, fixedText, attrs);
        //         }
        //     }
        
        //     @Override
        //     public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
        //         throws BadLocationException {
                
        //         String fixedText = string.toUpperCase().replaceAll("[^A-Z0-9]", "");
        //         super.insertString(fb, offset, fixedText, attr);
        //     }
        // });
        ((AbstractDocument) toanField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) vanField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) lyField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) hoaField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) sinhField.getTxtForm().getDocument()).setDocumentFilter(filter);
        
        ((AbstractDocument) suField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) diaField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) gdcdField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) n1ThiField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) n1CcField.getTxtForm().getDocument()).setDocumentFilter(filter);
        
        ((AbstractDocument) cncnField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) cnnnField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) tinField.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) ktplField.getTxtForm().getDocument()).setDocumentFilter(filter);
        
        ((AbstractDocument) nk1Field.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) nk2Field.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) nk3Field.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) nk4Field.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) nk5Field.getTxtForm().getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) nk6Field.getTxtForm().getDocument()).setDocumentFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            dispose();
        } else if (e.getSource() == btnConfirm) {
            String cccd = cccdField.getText();
            String sourceCCCD = (selectedRow != null) ? selectedRow.getCccd() : "";
            if (cccd.isEmpty())
                JOptionPane.showMessageDialog(this, "Ô căn cước công dân trống","Kiểm tra lại thông tin điểm số", JOptionPane.ERROR_MESSAGE);
            // else if (cccd.length() < 12) 
            //     JOptionPane.showMessageDialog(this, "Căn cước công dân không hợp lệ","Kiểm tra lại thông tin điểm số", JOptionPane.ERROR_MESSAGE);
            else if (bus.existCCCD(cccd, sourceCCCD, "THPT")) { 
                JOptionPane.showMessageDialog(this, "Dữ liệu điểm số của căn cước " + cccd + " đã tồn tại!", "Kiểm tra lại thông tin điểm số", JOptionPane.ERROR_MESSAGE);
            } else {
                newRow = new XtDiemThiXetTuyen(
                    cccdField.getText(),
                    "THPT",

                    bdNull(toanField),
                    bdNull(lyField),
                    bdNull(hoaField),
                    bdNull(sinhField),
                    bdNull(suField),
                    bdNull(diaField),
                    bdNull(vanField),
                    bdNull(gdcdField),

                    bdNull(n1ThiField),
                    bdNull(n1CcField),
                    bdNull(cncnField),
                    bdNull(cnnnField),
                    bdNull(tinField),
                    bdNull(ktplField),

                    bdNull(nk1Field),
                    bdNull(nk2Field),
                    bdNull(nk3Field),
                    bdNull(nk4Field),
                    bdNull(nk5Field),
                    bdNull(nk6Field)
                );
                if (selectedRow == null) {
                    if (bus.add(newRow)) {
                        parentPanel.loadDataTable(bus.getListTHPT());
                        JOptionPane.showMessageDialog(this, "Thêm dữ liệu điểm mới thành công!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xảy ra lỗi khi thêm dữ liệu vào database!");
                    }
                } else if (selectedRow.toString().equals(newRow.toString())) {
                    dispose();
                } else {
                    System.out.println(selectedRow.toString());
                    BeanUtils.copyProperties(newRow, selectedRow, "iddiemthi");
                    System.out.println(selectedRow.toString());
                    if (bus.update(selectedRow)) {
                        parentPanel.loadDataTable(bus.getListTHPT());
                        JOptionPane.showMessageDialog(this, "Cập nhật dữ liệu điểm thành công!");
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(this, "Xảy ra lỗi khi thêm dữ liệu vào database!");
                }
            }
        }
    }

    private BigDecimal bdNull(InputForm f) {
        String text = f.getText().trim();
        if (text.isEmpty()) return null;
        return new BigDecimal(text);
    }
}
