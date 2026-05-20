package com.quanlytuyensinh.GUI.Dialog;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import com.quanlytuyensinh.ENTITY.XtNganh;
import java.awt.*;
import javax.swing.*;

public class FilterDialog extends JDialog {

    private final NguyenVongPanel parent;
    private JComboBox<String> cbMaNganh;
    private JComboBox<String> cbPhuongThuc;
    private JComboBox<String> cbKetQua;

    public FilterDialog(NguyenVongPanel parent, JFrame owner, String title) {
        super(owner, title, true);
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Bộ Lọc Nguyện Vọng");
        setSize(560, 340);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font labelFont = new Font(FlatRobotoFont.FAMILY, Font.BOLD, 14);
        Font comboFont = new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // ===== Mã ngành =====
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblMaNganh = new JLabel("Mã ngành:");
        lblMaNganh.setFont(labelFont);
        lblMaNganh.setPreferredSize(new Dimension(130, 32));
        lblMaNganh.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add(lblMaNganh, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbMaNganh = new JComboBox<>();
        cbMaNganh.setFont(comboFont);
        cbMaNganh.setPreferredSize(new Dimension(330, 38));
        loadMaNganh();
        mainPanel.add(cbMaNganh, gbc);

        // ===== Phương thức =====
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPhuongThuc = new JLabel("Phương thức:");
        lblPhuongThuc.setFont(labelFont);
        lblPhuongThuc.setPreferredSize(new Dimension(130, 32));
        lblPhuongThuc.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add(lblPhuongThuc, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] phuongThuc = {"Tất cả", "Xét tuyển thẳng", "DGNL", "VSAT", "THPT"};
        cbPhuongThuc = new JComboBox<>(phuongThuc);
        cbPhuongThuc.setFont(comboFont);
        cbPhuongThuc.setPreferredSize(new Dimension(330, 38));
        mainPanel.add(cbPhuongThuc, gbc);

        // ===== Kết quả =====
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblKetQua = new JLabel("Kết quả:");
        lblKetQua.setFont(labelFont);
        lblKetQua.setPreferredSize(new Dimension(130, 32));
        lblKetQua.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add(lblKetQua, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] ketQuaOptions = {"Tất cả", "Trúng tuyển", "Không trúng tuyển", "Đang xét", "Rớt điểm sàn", "Chưa có điểm"};
        cbKetQua = new JComboBox<>(ketQuaOptions);
        cbKetQua.setSelectedItem("Trúng tuyển");  // Mặc định hiển thị trúng tuyển
        cbKetQua.setFont(comboFont);
        cbKetQua.setPreferredSize(new Dimension(330, 38));
        mainPanel.add(cbKetQua, gbc);

        // ===== Buttons =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(Color.WHITE);

        Font btnFont = new Font(FlatRobotoFont.FAMILY, Font.BOLD, 14);

        JButton btnApply = new JButton("Áp dụng");
        JButton btnReset = new JButton("Đặt lại");
        JButton btnClose = new JButton("Đóng");

        btnApply.setFont(btnFont);
        btnReset.setFont(btnFont);
        btnClose.setFont(btnFont);

        btnApply.setPreferredSize(new Dimension(130, 40));
        btnReset.setPreferredSize(new Dimension(130, 40));
        btnClose.setPreferredSize(new Dimension(130, 40));

        btnApply.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e -> resetFilter());
        btnClose.addActionListener(e -> dispose());

        btnPanel.add(btnApply);
        btnPanel.add(btnReset);
        btnPanel.add(btnClose);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnPanel, gbc);

        add(mainPanel);
    }

    private void loadMaNganh() {
        cbMaNganh.addItem("Tất cả");
        if (parent.getListNganh() != null) {
            for (XtNganh nganh : parent.getListNganh()) {
                if (nganh.getManganh() != null) {
                    String display = nganh.getManganh();
                    if (nganh.getTennganh() != null && !nganh.getTennganh().trim().isEmpty()) {
                        display += " - " + nganh.getTennganh();
                    }
                    cbMaNganh.addItem(display);
                }
            }
        }
    }

    private void applyFilter() {
        String selected = (String) cbMaNganh.getSelectedItem();
        String maNganh = "Tất cả".equals(selected) ? "Tất cả" : selected.split(" - ")[0].trim();

        String phuongThuc = (String) cbPhuongThuc.getSelectedItem();
        String ketQua = (String) cbKetQua.getSelectedItem();

        parent.applyFilter(maNganh, phuongThuc, ketQua);
        dispose();
    }

    private void resetFilter() {
        cbMaNganh.setSelectedIndex(0);
        cbPhuongThuc.setSelectedIndex(0);
        cbKetQua.setSelectedIndex(1);
        parent.applyFilter("Tất cả", "Tất cả", "Trúng tuyển");
    }
}