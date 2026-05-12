package com.quanlytuyensinh.GUI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ThongKeThiSinhPanel extends JPanel {

    // ==================== MÀU SẮC ====================
    private final Color BG_MAIN = new Color(245, 247, 250);
    private final Color STAT_BLUE = new Color(52, 152, 219);
    private final Color STAT_PURPLE = new Color(155, 89, 182);
    private final Color STAT_ORANGE = new Color(230, 126, 34);
    private final Color STAT_GREEN = new Color(46, 204, 113);

    private final Color TEXT_PRIMARY = new Color(45, 45, 45);
    private final Color TEXT_SECONDARY = new Color(120, 120, 120);
    private final Color TABLE_HEADER = new Color(52, 73, 94);
    private final Color TABLE_GRID = new Color(230, 230, 230);

    // ==================== FONT ====================
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONT_STAT_NUM = new Font("Segoe UI", Font.BOLD, 28);

    public ThongKeThiSinhPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 16));
        setBackground(BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==================== DATA GIẢ ====================
        int tongThiSinh = 5234;
        int soNam = 2480;
        int soNu = 2754;
        double diemCaoNhat = 29.75;

        // ==================== STAT CARDS ====================
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 130));

        statsRow.add(createStatCard("👤", "Tổng thí sinh", String.valueOf(tongThiSinh), STAT_BLUE));
        statsRow.add(createStatCard("👤", "Tổng thí sinh", String.valueOf(tongThiSinh), STAT_BLUE));
        statsRow.add(createStatCard("🏆", "Điểm cao nhất", String.format("%.2f", diemCaoNhat), STAT_GREEN));

        add(statsRow, BorderLayout.NORTH);

        // ==================== BOTTOM ====================
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomRow.setOpaque(false);
        bottomRow.add(createTopTruongCard());
        bottomRow.add(createThongKeTheoKhoiCard());

        add(bottomRow, BorderLayout.CENTER);
    }

    // =========================================================
    // STAT CARD - VIỀN MÀU
    // =========================================================
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

    // =========================================================
    // TOP 10 TRƯỜNG
    // =========================================================
    private JPanel createTopTruongCard() {
        JPanel card = createCardPanel("Top 10 Trường THPT có nhiều thí sinh nhất");

        String[] cols = {"STT", "Mã trường", "Tên trường", "Số thí sinh"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(FONT_REGULAR);
        table.setRowHeight(28);
        table.setGridColor(TABLE_GRID);
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(FONT_BOLD);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(280);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);

        // Custom renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, selected, focus, row, col);
                if (!selected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                setHorizontalAlignment(col == 2 ? SwingConstants.LEFT : SwingConstants.CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return c;
            }
        });

        // Data mẫu
        model.addRow(new Object[]{1, "TH001", "THPT Nguyễn Du", 512});
        model.addRow(new Object[]{2, "TH002", "THPT Lê Quý Đôn", 486});
        model.addRow(new Object[]{3, "TH003", "THPT Trần Phú", 455});
        model.addRow(new Object[]{4, "TH004", "THPT Gia Định", 438});
        model.addRow(new Object[]{5, "TH005", "THPT Nguyễn Thị Minh Khai", 421});

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        card.add(sp, BorderLayout.CENTER);

        return card;
    }

    // =========================================================
    // THỐNG KÊ THEO KHỐI
    // =========================================================
    private JPanel createThongKeTheoKhoiCard() {
        JPanel card = createCardPanel("Thống kê theo khối xét tuyển");

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        content.add(createSimpleProgressItem("Khối A00", 2100, STAT_BLUE));
        content.add(Box.createVerticalStrut(18));
        content.add(createSimpleProgressItem("Khối D01", 1650, STAT_BLUE));
        content.add(Box.createVerticalStrut(18));
        content.add(createSimpleProgressItem("Khối B00", 980, STAT_BLUE));
        content.add(Box.createVerticalStrut(18));
        content.add(createSimpleProgressItem("Khối C00", 504, STAT_BLUE));

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel createSimpleProgressItem(String label, int count, Color color) {
        int total = 5234;
        int percent = (int) Math.round(count * 100.0 / total);

        JPanel item = new JPanel(new BorderLayout(0, 6));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        // Info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);

        JLabel lblName = new JLabel(label);
        lblName.setFont(FONT_REGULAR);
        lblName.setForeground(TEXT_PRIMARY);

        JLabel lblInfo = new JLabel(count + " thí sinh • " + percent + "%");
        lblInfo.setFont(FONT_BOLD);
        lblInfo.setForeground(color);

        infoPanel.add(lblName, BorderLayout.WEST);
        infoPanel.add(lblInfo, BorderLayout.EAST);

        // Progress Bar
        JPanel progressBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                g2.setColor(new Color(235, 237, 240));
                g2.fillRoundRect(0, 0, w, h, 12, 12);

                int pw = (int) (w * percent / 100.0);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, pw, h, 12, 12);

                g2.dispose();
            }
        };
        progressBar.setPreferredSize(new Dimension(0, 11));
        progressBar.setOpaque(false);

        item.add(infoPanel, BorderLayout.NORTH);
        item.add(progressBar, BorderLayout.CENTER);
        return item;
    }
}