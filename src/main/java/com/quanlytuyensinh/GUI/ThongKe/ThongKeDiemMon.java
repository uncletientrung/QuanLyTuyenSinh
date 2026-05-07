package com.quanlytuyensinh.GUI.ThongKe;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

// Panel cha chứa tab các môn
public class ThongKeDiemMon extends JPanel {

    JTabbedPane tabbedPane;

    // Danh sách môn trong db
    private static final String[][] MONHOC = {
        {"Toán", "TO"},
        {"Lý", "LI"},
        {"Hóa", "HO"},
        {"Sinh", "SI"},
        {"Sử", "SU"},
        {"Địa", "DI"},
        {"Văn", "VA"},
        {"Ngoại ngữ", "N1_THI"}
    };

    public ThongKeDiemMon() {
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(new Color(240, 247, 250));

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        for (String[] mon : MONHOC) {
            String tenMon = mon[0];
            String colName = mon[1];
            ThongKeMonPanel panel = new ThongKeMonPanel(tenMon, colName);
            tabbedPane.addTab(tenMon, panel);
        }

        // Khi chuyển tab môn, load lại dữ liệu tab đang chọn
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            if (idx >= 0) {
                ThongKeMonPanel panel = (ThongKeMonPanel) tabbedPane.getComponentAt(idx);
                panel.refresh();
            }
        });

        this.add(tabbedPane);
    }

    public void refreshFirstTab() {
        tabbedPane.setSelectedIndex(0);
        ThongKeMonPanel first = (ThongKeMonPanel) tabbedPane.getComponentAt(0);
        first.refresh();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
