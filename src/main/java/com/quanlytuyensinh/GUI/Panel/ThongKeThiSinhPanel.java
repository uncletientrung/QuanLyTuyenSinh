package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.ThongKe.Support.Chart;
import com.quanlytuyensinh.GUI.ThongKe.Support.ModelChart;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeThiSinhPanel extends JPanel {
    XtThisinhXetTuyen25BUS TSBUS;
    java.util.List<XtThisinhXetTuyen25> listTS;
    private int SoLuongThiSinh;
    private HashMap<String, Integer> ThongKeTheoKV;
    private HashMap<String, Integer> ThongKeTheoDT;
    private List<Map.Entry<String, Integer>> ThongKeTheoTP;
    private HashMap<String, Integer> ThongKeTheoNamNu;
    
    // ==================== MÀU SẮC ====================
    private final Color BG_MAIN = new Color(245, 247, 250);
    private final Color STAT_BLUE = new Color(52, 152, 219);
    private final Color STAT_PURPLE = new Color(231, 76, 140);
    private final Color STAT_ORANGE = new Color(230, 126, 34);
    private final Color STAT_GREEN = new Color(41, 128, 185);

    private final Color TEXT_PRIMARY = new Color(45, 45, 45);
    private final Color TEXT_SECONDARY = new Color(120, 120, 120);
    private final Color TABLE_HEADER = new Color(52, 73, 94);
    private final Color TABLE_GRID = new Color(230, 230, 230);

    // ==================== FONT ====================
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_STAT_NUM = new Font("Segoe UI", Font.BOLD, 28);

    public ThongKeThiSinhPanel(XtThisinhXetTuyen25BUS tsBUS,   java.util.List<XtThisinhXetTuyen25> listThiSinh) {
        TSBUS = tsBUS;
        listTS = listThiSinh;
        this.SoLuongThiSinh = this.listTS.size();
        this.ThongKeTheoKV = TSBUS.thongKeThiSinhKhuVuc();
        this.ThongKeTheoDT = this.TSBUS.thongKeThiSinhDoiTuong();
        this.ThongKeTheoTP = this.TSBUS.thongKeThiSinhTinhThanh();
        this.ThongKeTheoNamNu = this.TSBUS.thongKeThiSinhNamNu();
        
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 16));
        setBackground(BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==================== STAT CARDS ====================
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 130));
        statsRow.add(createStatCard("👥", "Tổng thí sinh", String.valueOf(this.SoLuongThiSinh), STAT_BLUE));
        statsRow.add(createStatCard("♂", "Tổng thí sinh Nam", String.valueOf(this.ThongKeTheoNamNu.get("Nam")), STAT_GREEN));
        statsRow.add(createStatCard("♀", "Điểm cao nhất Nữ", String.valueOf(this.ThongKeTheoNamNu.get("Nữ")), STAT_PURPLE));
        add(statsRow, BorderLayout.NORTH);

        // ==================== BOTTOM ====================
        JPanel bottomRow = new JPanel(new GridLayout(1, 3, 16, 0));
        bottomRow.setOpaque(false);
//        bottomRow.add(createTopTruongCard());
        bottomRow.add(createThongKeTheoDoiTuongCard());
        bottomRow.add(createThongKeTheoKhuVucCard());
        add(bottomRow, BorderLayout.CENTER);
    }

    // STAT CARD
    private JPanel createStatCard(String icon, String label, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                // Shadow
                g2.setColor(new Color(0, 0, 0, 12));
                g2.fill(new RoundRectangle2D.Float(3, 3, w - 6, h - 6, 24, 24));

                // Nền
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, w - 3, h - 3, 24, 24));

                // Viền màu
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(3.5f));
                g2.draw(new RoundRectangle2D.Float(2, 2, w - 5, h - 5, 22, 22));

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setLayout(new BorderLayout(16, 0));
        card.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        iconLbl.setForeground(accentColor);
        card.add(iconLbl, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(FONT_STAT_NUM);
        valLbl.setForeground(accentColor);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(FONT_REGULAR);
        lblLabel.setForeground(TEXT_SECONDARY);

        textPanel.add(valLbl);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(lblLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    // =========================================================
    // CARD CHUNG
    // =========================================================
    private JPanel createCardPanel(String title) {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 12));
                g2.fill(new RoundRectangle2D.Float(2, 2, getWidth() - 2, getHeight() - 2, 16, 16));

                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 2, getHeight() - 2, 16, 16));

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(FONT_SUBTITLE);
        titleLbl.setForeground(TEXT_PRIMARY);
        card.add(titleLbl, BorderLayout.NORTH);

        return card;
    }

private JPanel createTopTruongCard() {

    JPanel card =
            createCardPanel("Số lượng thí sinh theo tỉnh thành");

    String[] header = {
            "STT",
            "Tỉnh thành",
            "Số lượng thí sinh"
    };

    PaginatedTable paginatedTable =
            new PaginatedTable(header);

    JTable table = paginatedTable.getTable();

    // ================= STYLE =================

    table.setFocusable(false);

    table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 13)
    );

    table.getTableHeader().setPreferredSize(
            new Dimension(0, 40)
    );

    table.setRowHeight(32);

    table.setGridColor(TABLE_GRID);

    // Center header
    DefaultTableCellRenderer headerRenderer =
            (DefaultTableCellRenderer)
                    table.getTableHeader()
                            .getDefaultRenderer();

    headerRenderer.setHorizontalAlignment(
            JLabel.CENTER
    );

    // Center cell
    DefaultTableCellRenderer centerRenderer =
            new DefaultTableCellRenderer();

    centerRenderer.setHorizontalAlignment(
            JLabel.CENTER
    );

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel()
                .getColumn(i)
                .setCellRenderer(centerRenderer);
    }

    // ================= DATA =================

    List<Object[]> data = new ArrayList<>();

    int stt = 1;

    for(Map.Entry<String, Integer> entry
            : this.ThongKeTheoTP){

        data.add(new Object[]{
                stt++,
                entry.getKey(),
                entry.getValue()
        });
    }
    paginatedTable.setData(data);

    card.add(paginatedTable, BorderLayout.CENTER);

    return card;
}

    // =========================================================
    // THỐNG KÊ THEO KHỐI
    // =========================================================
private JPanel createThongKeTheoDoiTuongCard() {

    JPanel card =
            createCardPanel("Thống kê thí sinh theo ĐỐI TƯỢNG");

    JPanel container =
            new JPanel(new BorderLayout(0, 10));

    container.setOpaque(false);

    // ================= CHART =================

    Chart chart = new Chart();

    chart.addLegend(
            "Số lượng thí sinh",
            STAT_BLUE
    );

    int total = 0;

    // Tính tổng
    for(Integer value : this.ThongKeTheoDT.values()){
        total += value;
    }

    // Đổ dữ liệu chart
    for(Map.Entry<String, Integer> entry
            : this.ThongKeTheoDT.entrySet()){

        chart.addData(
                new ModelChart(
                        entry.getKey(),
                        new double[]{entry.getValue()}
                )
        );
    }

    container.add(chart, BorderLayout.CENTER);

    // ================= TABLE =================

    String[] cols = {
            "Đối tượng",
            "Số lượng",
            "Tỷ lệ"
    };

    DefaultTableModel model =
            new DefaultTableModel(cols, 0){

        @Override
        public boolean isCellEditable(
                int row,
                int column
        ){
            return false;
        }
    };

    JTable table = new JTable(model);

    table.setRowHeight(32);

    table.setFont(FONT_REGULAR);

    table.getTableHeader()
            .setFont(FONT_BOLD);

    table.getTableHeader()
            .setBackground(TABLE_HEADER);

    table.getTableHeader()
            .setForeground(Color.WHITE);

    // Center renderer
    DefaultTableCellRenderer center =
            new DefaultTableCellRenderer();

    center.setHorizontalAlignment(
            JLabel.CENTER
    );

    table.setDefaultRenderer(
            Object.class,
            center
    );

    // Đổ dữ liệu table
    for(Map.Entry<String, Integer> entry
            : this.ThongKeTheoDT.entrySet()){

        int soLuong = entry.getValue();

        double percent =
                soLuong * 100.0 / total;

        model.addRow(new Object[]{
                entry.getKey(),
                soLuong,
                String.format("%.3f%%", percent)
        });
    }

    JScrollPane scroll =
            new JScrollPane(table);

    scroll.setBorder(
            BorderFactory.createEmptyBorder()
    );

    scroll.setPreferredSize(
            new Dimension(0, 160)
    );

    container.add(scroll, BorderLayout.SOUTH);

    card.add(container, BorderLayout.CENTER);

    return card;
}
private JPanel createThongKeTheoKhuVucCard() {

    JPanel card =
            createCardPanel("Thống kê thí sinh theo KHU VỰC");

    JPanel container =
            new JPanel(new BorderLayout(0, 10));

    container.setOpaque(false);

    // ================= CHART =================

    Chart chart = new Chart();

    chart.addLegend(
            "Số lượng thí sinh",
            STAT_BLUE
    );

    int total = 0;

    // Tính tổng
    for(Integer value : this.ThongKeTheoKV.values()){
        total += value;
    }

    // Đổ dữ liệu chart
    for(Map.Entry<String, Integer> entry
            : this.ThongKeTheoKV.entrySet()){

        chart.addData(
                new ModelChart(
                        entry.getKey(),
                        new double[]{entry.getValue()}
                )
        );
    }

    container.add(chart, BorderLayout.CENTER);

    // ================= TABLE =================

    String[] cols = {
            "Khu vực",
            "Số lượng",
            "Tỷ lệ"
    };

    DefaultTableModel model =
            new DefaultTableModel(cols, 0){

        @Override
        public boolean isCellEditable(
                int row,
                int column
        ){
            return false;
        }
    };

    JTable table = new JTable(model);

    table.setRowHeight(32);

    table.setFont(FONT_REGULAR);

    table.getTableHeader()
            .setFont(FONT_BOLD);

    table.getTableHeader()
            .setBackground(TABLE_HEADER);

    table.getTableHeader()
            .setForeground(Color.WHITE);

    // Center renderer
    DefaultTableCellRenderer center =
            new DefaultTableCellRenderer();

    center.setHorizontalAlignment(
            JLabel.CENTER
    );

    table.setDefaultRenderer(
            Object.class,
            center
    );

    // Đổ dữ liệu table
    for(Map.Entry<String, Integer> entry
            : this.ThongKeTheoKV.entrySet()){

        int soLuong = entry.getValue();

        double percent =
                soLuong * 100.0 / total;

        model.addRow(new Object[]{
                entry.getKey(),
                soLuong,
                String.format("%.3f%%", percent)
        });
    }

    JScrollPane scroll =
            new JScrollPane(table);

    scroll.setBorder(
            BorderFactory.createEmptyBorder()
    );

    scroll.setPreferredSize(
            new Dimension(0, 160)
    );

    container.add(scroll, BorderLayout.SOUTH);

    card.add(container, BorderLayout.CENTER);

    return card;
}
}