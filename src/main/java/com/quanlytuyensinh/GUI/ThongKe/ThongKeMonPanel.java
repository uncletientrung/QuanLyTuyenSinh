package com.quanlytuyensinh.GUI.ThongKe;

import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.GUI.ThongKe.Support.Chart;
import com.quanlytuyensinh.GUI.ThongKe.Support.ModelChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ThongKeMonPanel extends JPanel {

    private final String tenMon;
    private final String colName;
    private final XtDiemThiXetTuyenBUS bus = new XtDiemThiXetTuyenBUS();
    private JTabbedPane innerTabs;

    public ThongKeMonPanel(String tenMon, String colName) {
        this.tenMon = tenMon;
        this.colName = colName;
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        innerTabs = new JTabbedPane();
        innerTabs.addTab("VSAT", buildSubPanel("VSAT"));
        innerTabs.addTab("THPT", buildSubPanel("THPT"));

        innerTabs.addChangeListener(e -> refresh());
        this.add(innerTabs);
    }

    public void refresh() {
        SubPanel sp = (SubPanel) innerTabs.getSelectedComponent();
        if (sp != null) {
            sp.loadData();
        }
    }

    class SubPanel extends JPanel {

        private final String phuongThuc;
        private Chart chart; // Sử dụng component Chart hỗ trợ
        private DefaultTableModel tblModel;

        SubPanel(String phuongThuc) {
            this.phuongThuc = phuongThuc;
            initUI();
        }

        private void initUI() {
            this.setLayout(new GridBagLayout());
            this.setBackground(Color.WHITE);
            this.setBorder(new EmptyBorder(10, 10, 10, 10));

            GridBagConstraints gbc = new GridBagConstraints();

            // Chart
            chart = new Chart();
            chart.addLegend(tenMon, new Color(12, 84, 175));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.85; // Cao chart
            gbc.fill = GridBagConstraints.BOTH;
            this.add(chart, gbc);

            // Table
            tblModel = new DefaultTableModel(new String[]{"Số lượng", "Điểm TB", "Thấp nhất", "Cao nhất"}, 0);
            JTable table = new JTable(tblModel);
            table.setRowHeight(40); // Cao dòng
            table.setFont(new Font("Arial", Font.PLAIN, 15));
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

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

            JScrollPane scrollPane = new JScrollPane(table);
            gbc.gridy = 1;
            gbc.weighty = 0.15; // Cao table
            this.add(scrollPane, gbc);
        }

        void loadData() {
            List<XtDiemThiXetTuyen> allData = bus.getList();

            // Bỏ điểm 0
            List<Double> scores = allData.stream()
                    .filter(dt -> dt.getDPhuongthuc() != null && dt.getDPhuongthuc().equalsIgnoreCase(phuongThuc))
                    .map(dt -> getFieldValue(dt, colName))
                    .filter(val -> val != null && val.doubleValue() > 0)
                    .map(BigDecimal::doubleValue)
                    .collect(Collectors.toList());

            chart.clear();

            boolean isVsat = phuongThuc.equalsIgnoreCase("VSAT");
            double step = isVsat ? 5.0 : 0.25;
            double maxRange = isVsat ? 150.0 : 10.0;
            double labelStep = isVsat ? 10.0 : 0.5;

            int numBins = (int) (maxRange / step) + 1;
            int[] counts = new int[numBins];

            for (double s : scores) {
                int binIdx = (int) Math.round(s / step);
                if (binIdx >= 0 && binIdx < numBins) {
                    counts[binIdx]++;
                }
            }

            // Đổ dữ liệu vào Chart
            for (int i = 0; i < numBins; i++) {
                double currentVal = i * step;

                String label = "";
                if (Math.abs(currentVal % labelStep) < 0.001) {
                    label = isVsat ? String.valueOf((int) currentVal) : String.format("%.1f", currentVal);
                }
                chart.addData(new ModelChart(label, new double[]{counts[i]}));
            }

            updateTable(scores);
        }

        private void updateTable(List<Double> scores) {
            tblModel.setRowCount(0);
            if (scores.isEmpty()) {
                tblModel.addRow(new Object[]{0, "-", "-", "-"});
                return;
            }
            double avg = scores.stream().mapToDouble(d -> d).average().orElse(0);
            double min = scores.stream().mapToDouble(d -> d).min().orElse(0);
            double max = scores.stream().mapToDouble(d -> d).max().orElse(0);
            tblModel.addRow(new Object[]{scores.size(), String.format("%.2f", avg), min, max});
        }

        private BigDecimal getFieldValue(XtDiemThiXetTuyen dt, String col) {
            return switch (col) {
                case "TO" ->
                    dt.getTo();
                case "LI" ->
                    dt.getLi();
                case "HO" ->
                    dt.getHo();
                case "SI" ->
                    dt.getSi();
                case "SU" ->
                    dt.getSu();
                case "DI" ->
                    dt.getDi();
                case "VA" ->
                    dt.getVa();
                case "N1_THI" ->
                    dt.getN1Thi();
                default ->
                    null;
            };
        }
    }

    private SubPanel buildSubPanel(String phuongThuc) {
        return new SubPanel(phuongThuc);
    }
}
