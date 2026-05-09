package com.quanlytuyensinh.GUI.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRowRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
        int ketQuaColumnIndex = -1;
        
        // Tìm cột "Kết quả" theo tên (an toàn hơn)
        for (int i = 0; i < table.getColumnCount(); i++) {
            if ("Kết quả".equals(table.getColumnName(i))) {
                ketQuaColumnIndex = i;
                break;
            }
        }

        String ketQua = "";
        if (ketQuaColumnIndex != -1) {
            try {
                Object obj = table.getModel().getValueAt(table.convertRowIndexToModel(row), ketQuaColumnIndex);
                if (obj != null) ketQua = obj.toString().trim();
            } catch (Exception ignored) {}
        }

        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        } else {
            switch (ketQua) {
                case "Trúng tuyển":
                    c.setBackground(new Color(200, 230, 201));   // Xanh lá
                    c.setForeground(Color.BLACK);
                    break;
                    
                case "Đang xét":
                    c.setBackground(new Color(255, 253, 231));   // Vàng nhạt
                    c.setForeground(Color.BLACK);
                    break;
                    
                case "Không trúng tuyển":
                    c.setBackground(new Color(255, 204, 188));   // Cam đỏ
                    c.setForeground(Color.BLACK);
                    break;
                case "Rớt điểm sàn":
                    c.setBackground(new Color(255, 223, 221));   // Đỏ hồng
                    c.setForeground(Color.BLACK);
                    break;
                default:
                    c.setBackground(new Color(255, 255, 255));   // trắng
                    c.setForeground(Color.BLACK);
                    break;
            }
        }
        return c;
    }
}