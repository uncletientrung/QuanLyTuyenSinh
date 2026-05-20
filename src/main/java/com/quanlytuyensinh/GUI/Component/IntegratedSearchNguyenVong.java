package com.quanlytuyensinh.GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Phiên bản compact của IntegratedSearch dành riêng cho NguyenVongPanel.
 * Thu nhỏ ComboBox và TextField để không che nút BỘ LỌC trên functionBar.
 */
public class IntegratedSearchNguyenVong extends JPanel {

    public JComboBox<String> cbxChoose;
    public JButton btnReset;
    public JTextField txtSearchForm;

    public IntegratedSearchNguyenVong(String[] str) {
        initComponent(str);
    }

    private void initComponent(String[] str) {
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel jpSearch = new JPanel(new BorderLayout(4, 0));
        jpSearch.setBorder(new EmptyBorder(18, 8, 18, 8));  // Giảm padding ngang
        jpSearch.setBackground(Color.WHITE);

        // ComboBox thu nhỏ lại
        cbxChoose = new JComboBox<>();
        cbxChoose.setModel(new DefaultComboBoxModel<>(str));
        cbxChoose.setPreferredSize(new Dimension(110, 0));   // Giảm từ 140 → 110
        cbxChoose.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 12));
        cbxChoose.setFocusable(false);
        jpSearch.add(cbxChoose, BorderLayout.WEST);

        // TextField giữ nguyên, tự co giãn
        txtSearchForm = new JTextField();
        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 12));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        jpSearch.add(txtSearchForm, BorderLayout.CENTER);

        // Nút Làm mới thu nhỏ lại
        btnReset = new JButton("Làm mới");
        btnReset.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 12));
        btnReset.setIcon(new FlatSVGIcon(getClass().getResource("/static/icons/refresh.svg")));
        btnReset.setPreferredSize(new Dimension(105, 0));    // Giảm từ 125 → 105
        btnReset.addActionListener(this::btnResetActionPerformed);
        jpSearch.add(btnReset, BorderLayout.EAST);

        this.add(jpSearch);
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent e) {
        txtSearchForm.setText("");
        cbxChoose.setSelectedIndex(0);
    }
}