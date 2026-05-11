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
import com.quanlytuyensinh.GUI.Panel.DiemThiVSATPanel;

public class VSATDialog extends JDialog implements ActionListener{
    private DiemThiVSATPanel parentPanel;
    private XtDiemThiXetTuyenBUS bus;
    private JFrame main;
    private JPanel pmain, pbottom;
    private InputForm cccdField, toanField, vanField, lyField, hoaField, sinhField,
                    suField, diaField, n1ThiField;
    private XtDiemThiXetTuyen selectedRow;
    private XtDiemThiXetTuyen newRow;
    private ButtonCustom btnConfirm, btnCancel;


    public VSATDialog(DiemThiVSATPanel parentPanel, XtDiemThiXetTuyenBUS bus, JFrame main,
            XtDiemThiXetTuyen selectedRow, String title) {
        super(main, title, true);
        this.parentPanel = parentPanel;
        this.bus = bus;
        this.main = main;
        this.selectedRow = selectedRow;
        initComponent();
    }

    public VSATDialog() {}

    private void initComponent() {
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(main);

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

        n1ThiField = new InputForm("Tiếng Anh", true);
        pmain.add(n1ThiField, gbc);
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
            set(n1ThiField, selectedRow.getN1Thi());
        }

        JScrollPane container = new JScrollPane(pmain);
        container.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(container, BorderLayout.CENTER);
        this.add(pbottom, BorderLayout.SOUTH);
        this.pack();
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

                if (value >= 0 && value <= 150) {
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
            else if (bus.existCCCD(cccd, sourceCCCD, "VSAT")) { 
                JOptionPane.showMessageDialog(this, "Dữ liệu điểm số của căn cước " + cccd + " đã tồn tại!", "Kiểm tra lại thông tin điểm số", JOptionPane.ERROR_MESSAGE);
            } else {
                newRow = new XtDiemThiXetTuyen(
                    cccdField.getText(),
                    "VSAT",

                    bdNull(toanField),
                    bdNull(lyField),
                    bdNull(hoaField),
                    bdNull(sinhField),
                    bdNull(suField),
                    bdNull(diaField),
                    bdNull(vanField),

                    bdNull(n1ThiField)
                );
                if (selectedRow == null) {
                    if (bus.add(newRow)) {
                        parentPanel.loadDataTable(bus.getListVSAT());
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
                        parentPanel.loadDataTable(bus.getListVSAT());
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
