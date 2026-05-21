package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.ThongKe.Support.Chart;
import com.quanlytuyensinh.GUI.ThongKe.Support.ModelChart;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeThiSinhPanel extends JPanel {

    // ==================== DATA ====================
    private final int tongThiSinh;
    private final HashMap<String, Integer> thongKeDoiTuong;
    private final HashMap<String, Integer> thongKeKhuVuc;
    private final List<Map.Entry<String, Integer>> thongKeTinhThanh;
    private HashMap<String, Integer> ThongKeTheoNamNu;
    XtThisinhXetTuyen25BUS TSBUS;
    List<XtThisinhXetTuyen25> listThiSinh;

    // ==================== COLORS ====================
    private final Color BG_MAIN = new Color(245, 247, 250);
    private final Color STAT_BLUE = new Color(52, 152, 219);
    private final Color STAT_PURPLE = new Color(231, 76, 140);
    private final Color STAT_GREEN = new Color(41, 128, 185);

    private final Color TEXT_PRIMARY = new Color(45, 45, 45);
    private final Color TEXT_SECONDARY = new Color(120, 120, 120);
    private final Color TABLE_HEADER = new Color(52, 73, 94);

    // ==================== FONTS ====================
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_STAT = new Font("Segoe UI", Font.BOLD, 28);

    public ThongKeThiSinhPanel() {
        TSBUS = new XtThisinhXetTuyen25BUS();
        listThiSinh = TSBUS.getAllThiSinh();
        this.tongThiSinh = listThiSinh.size();
        this.thongKeDoiTuong = TSBUS.thongKeThiSinhDoiTuong();
        this.thongKeKhuVuc = TSBUS.thongKeThiSinhKhuVuc();
        this.thongKeTinhThanh = TSBUS.thongKeThiSinhTinhThanh();
        this.ThongKeTheoNamNu = this.TSBUS.thongKeThiSinhNamNu();
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

        statsRow.add(createStatCard("👥", "Tổng thí sinh", String.valueOf(tongThiSinh), STAT_BLUE));
        statsRow.add(createStatCard("♂", "Thí sinh Nam", String.valueOf(this.ThongKeTheoNamNu.get("Nam")), STAT_GREEN));
        statsRow.add(createStatCard("♀", "Thí sinh Nữ", String.valueOf(this.ThongKeTheoNamNu.get("Nữ")), STAT_PURPLE));

        add(statsRow, BorderLayout.NORTH);

        // Bottom Content
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomRow.setOpaque(false);

        bottomRow.add(createThongKeCard("Thống kê theo ĐỐI TƯỢNG", thongKeDoiTuong, STAT_BLUE));
        bottomRow.add(createThongKeCard("Thống kê theo KHU VỰC", thongKeKhuVuc, STAT_BLUE));

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
        chart.addLegend("Số lượng thí sinh", accent);

        int total = data.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            chart.addData(new ModelChart(entry.getKey(), new double[]{entry.getValue()}));
        }
        container.add(chart, BorderLayout.CENTER);

        // Table
        String[] columns;
        if (title.equals("Thống kê theo ĐỐI TƯỢNG")) {
            columns = new String[]{"Đối tượng", "Số lượng", "Tỷ lệ"};
        } else {
            columns = new String[]{"Khu vực", "Số lượng", "Tỷ lệ"};
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(220, 220, 220));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    setForeground(Color.BLACK);
                }
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        };
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
