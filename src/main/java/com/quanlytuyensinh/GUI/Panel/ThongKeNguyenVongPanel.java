/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.GUI.ThongKe.Support.Chart;
import com.quanlytuyensinh.GUI.ThongKe.Support.ModelChart;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class ThongKeNguyenVongPanel extends JPanel{
     XtNguyenVongXetTuyenBUS NVBUS;
     private HashMap<String, Integer> thongKeNguyenVong;
     private HashMap<String, Integer> thongKeNguyenVongTheoNganh;
     private HashMap<String, Integer> thongKeNguyenVongTheoPhuongThuc;
     int TongNV = 0;
     int NVConLai = 0;
    // ==================== COLORS ====================
    private final Color BG_MAIN = new Color(245, 247, 250);
    private final Color STAT_BLUE = new Color(52, 152, 219);
    private final Color STAT_PURPLE = new Color(231, 76, 140);
    private final Color STAT_GREEN = new Color(41, 128, 185);
    private final Color STAT_GREEN2 = new Color(76, 187, 23);

    private final Color TEXT_PRIMARY = new Color(45, 45, 45);
    private final Color TEXT_SECONDARY = new Color(120, 120, 120);
    private final Color TABLE_HEADER = new Color(52, 73, 94);

    // ==================== FONTS ====================
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_STAT = new Font("Segoe UI", Font.BOLD, 28);
    
    public ThongKeNguyenVongPanel() {
        NVBUS = new XtNguyenVongXetTuyenBUS();
        thongKeNguyenVong = NVBUS.thongKeSoLuongNguyenVong();
        thongKeNguyenVongTheoPhuongThuc = NVBUS.thongKeSoLuongNguyenVongPhuongThuc();
//        thongKeNguyenVongTheoNganh = NVBUS.thongKeSoLuongNguyenVongNganh();
        for (Map.Entry<String, Integer> entry : thongKeNguyenVong.entrySet()) {
            if(!entry.getKey().equals("Trúng tuyển")){
                NVConLai += entry.getValue();
            }
            TongNV += entry.getValue();
        }
        initUI();
    }
        private void initUI() {
        setLayout(new BorderLayout(0, 16));
        setBackground(BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Stats
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 130));

        statsRow.add(createStatCard("📝", "Tổng nguyện vọng", String.valueOf(this.TongNV), STAT_BLUE));
//         statsRow.add(new JLabel(""));
//         statsRow.add(new JLabel(""));
        statsRow.add(createStatCard("📝", "Nguyện vọng trúng tuyển", String.valueOf(this.thongKeNguyenVong.get("Trúng tuyển")), STAT_BLUE));
        statsRow.add(createStatCard("📌", "Nguyện vọng còn lại", String.valueOf(this.NVConLai), STAT_BLUE));

        add(statsRow, BorderLayout.NORTH);

        // Bottom Content
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomRow.setOpaque(false);

        bottomRow.add(createThongKeCard("Thống kê theo KẾT QUẢ", thongKeNguyenVong, STAT_BLUE));
        bottomRow.add(createThongKeCard("Thống kê theo PHƯƠNG THỨC", thongKeNguyenVongTheoPhuongThuc, STAT_BLUE));
//        bottomRow.add(createThongKeCard("Thống kê theo MÃ NGÀNH", this.thongKeNguyenVongTheoNganh, STAT_BLUE));

        add(bottomRow, BorderLayout.CENTER);
    }
            // ==================== STAT CARD ====================
    private JPanel createStatCard(String icon, String label, String value, Color accent) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();

                g2.setColor(new Color(0, 0, 0, 12));
                g2.fillRoundRect(3, 3, w - 6, h - 6, 24, 24);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, w - 3, h - 3, 24, 24);

                g2.setColor(accent);
                g2.setStroke(new BasicStroke(3.5f));
                g2.drawRoundRect(2, 2, w - 5, h - 5, 22, 22);
                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setLayout(new BorderLayout(16, 0));
        card.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        iconLbl.setForeground(accent);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(FONT_STAT);
        valueLbl.setForeground(accent);

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(FONT_REGULAR);
        labelLbl.setForeground(TEXT_SECONDARY);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(valueLbl);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(labelLbl);

        card.add(iconLbl, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }
    private JPanel createThongKeCard(String title, HashMap<String, Integer> data, Color accent) {
        JPanel card = createCardPanel(title);

        JPanel container = new JPanel(new BorderLayout(0, 10));
        container.setOpaque(false);

        // Chart
        Chart chart = new Chart();
        chart.addLegend("Số lượng nguyện vọng", accent);

        int total = data.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            chart.addData(new ModelChart(entry.getKey(), new double[]{entry.getValue()}));
        }
        container.add(chart, BorderLayout.CENTER);

        // Table
        String[] columns;
        if(title.equals("Thống kê theo KẾT QUẢ")){
            columns = new String[]{"Kết quả", "Số lượng", "Tỷ lệ"};
        }else{
            columns = new String[]{"Phương thức", "Số lượng", "Tỷ lệ"};
        }
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int count = entry.getValue();
            double percent = total == 0 ? 0 : count * 100.0 / total;
            model.addRow(new Object[]{
                    entry.getKey(),
                    count,
                    String.format("%.3f%%", percent)
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(FONT_REGULAR);
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new Dimension(0, 160));

        container.add(scroll, BorderLayout.SOUTH);
        card.add(container, BorderLayout.CENTER);

        return card;
    }
            // ==================== COMMON CARD PANEL ====================
    private JPanel createCardPanel(String title) {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 12));
                g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 16, 16);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 16, 16);
                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(FONT_TITLE);
        titleLbl.setForeground(TEXT_PRIMARY);
        card.add(titleLbl, BorderLayout.NORTH);

        return card;
    }
}
