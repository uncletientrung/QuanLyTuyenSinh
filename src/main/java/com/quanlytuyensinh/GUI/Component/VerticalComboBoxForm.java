package com.quanlytuyensinh.GUI.Component;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * VerticalComboBoxForm - Hỗ trợ cả combo Có/Không và combo tùy chỉnh
 */
public class VerticalComboBoxForm extends JPanel {

    private JLabel lblTitle;
    private JComboBox<String> cbbForm;

    // Constructor mặc định: Có / Không
    public VerticalComboBoxForm(String title) {
        this(title, new String[]{"-- Chọn --", "Có", "Không"});
    }

    // Constructor mới: Truyền mảng tùy chỉnh
    public VerticalComboBoxForm(String title, String[] options) {
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitle.setForeground(new Color(60, 60, 60));

        cbbForm = new JComboBox<>(options);
        cbbForm.setPreferredSize(new Dimension(300, 40));
        cbbForm.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbbForm.setBackground(Color.WHITE);

        this.add(lblTitle, BorderLayout.NORTH);
        this.add(cbbForm, BorderLayout.CENTER);
    }

    /**
     * Kiểm tra có phải chọn "Có" hay không (dùng cho các trường Có/Không)
     */
    public boolean isSelectedYes() {
        String selected = (String) cbbForm.getSelectedItem();
        return "Có".equals(selected) || "1".equals(selected);
    }

    /**
     * Lấy giá trị đã chọn (trả về giá trị thực tế để lưu vào DB)
     */
    public String getSelectedValue() {
        String selected = (String) cbbForm.getSelectedItem();
        if (selected == null || "-- Chọn --".equals(selected)) {
            return null;
        }
        // Chuyển "Có" → "1", "Không" → "0"
        if ("Có".equals(selected)) return "1";
        if ("Không".equals(selected)) return "0";
        
        return selected; // Trả về nguyên giá trị nếu là combo tùy chỉnh
    }

    /**
     * Set giá trị từ database vào combo
     */
    public void setSelectedValue(String dbValue) {
        if (dbValue == null || dbValue.isEmpty()) {
            cbbForm.setSelectedIndex(0);
            return;
        }

        // Xử lý trường hợp Có/Không
        if ("1".equals(dbValue)) {
            cbbForm.setSelectedItem("Có");
        } else if ("0".equals(dbValue)) {
            cbbForm.setSelectedItem("Không");
        } else {
            // Combo tùy chỉnh: tìm item khớp
            for (int i = 0; i < cbbForm.getItemCount(); i++) {
                if (dbValue.equals(cbbForm.getItemAt(i))) {
                    cbbForm.setSelectedIndex(i);
                    return;
                }
            }
            cbbForm.setSelectedIndex(0); // fallback
        }
    }

    /**
     * Disable toàn bộ combo
     */
    public void setDisable() {
        cbbForm.setEnabled(false);
    }

    /**
     * Enable combo
     */
    public void setEnable() {
        cbbForm.setEnabled(true);
    }

    // Getter
    public JLabel getLblTitle() {
        return lblTitle;
    }

    public JComboBox<String> getComboBox() {
        return cbbForm;
    }

    /**
     * Set items mới cho combo (nếu cần thay đổi động)
     */
    public void setOptions(String[] options) {
        cbbForm.setModel(new DefaultComboBoxModel<>(options));
    }
}