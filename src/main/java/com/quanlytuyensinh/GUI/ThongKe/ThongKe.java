package com.quanlytuyensinh.GUI.ThongKe;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ThongKe extends JPanel {

    JTabbedPane tabbedPane;
    ThongKeDiemMon diemMon;

    public ThongKe() {
        initComponent();
    }

    public void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(new Color(240, 247, 250));

        diemMon = new ThongKeDiemMon();

//        tabbedPane = new JTabbedPane();
//        tabbedPane.setOpaque(false);
//        tabbedPane.addTab("Điểm thi", diemMon);
//
//        tabbedPane.addChangeListener(e -> refreshCurrentTab());
        diemMon.refreshFirstTab();
        this.add(diemMon);
    }

    private void refreshCurrentTab() {
        int index = tabbedPane.getSelectedIndex();
        if (index == 0) {
            diemMon.refreshFirstTab();
        }
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public ThongKeDiemMon getDiemMon() {
        return diemMon;
    }
}
