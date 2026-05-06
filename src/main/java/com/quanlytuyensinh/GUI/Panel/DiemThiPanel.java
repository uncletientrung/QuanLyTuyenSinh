package com.quanlytuyensinh.GUI.Panel;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.GUI.Main;

public class DiemThiPanel extends JPanel{
    private Main mainFrame;
    private JTabbedPane tabbedPane;

    private XtDiemThiXetTuyenBUS diemBUS;
    private Color BackgroundColor = new Color(240, 247, 250);

    public DiemThiPanel(Main main) {
        this.mainFrame = main;
        diemBUS = new XtDiemThiXetTuyenBUS();
        initComponent();
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 14));
        tabbedPane.setBorder(new EmptyBorder(10, 10, 0, 10));
        tabbedPane.setBackground(Color.WHITE);

        tabbedPane.addTab("THPT", new DiemThiTHPTPanel(mainFrame, diemBUS, diemBUS.getListTHPT()));
        tabbedPane.addTab("VSAT", new DiemThiVSATPanel(mainFrame, diemBUS, diemBUS.getListVSAT()));
        tabbedPane.addTab("DGNL", new DiemThiDGNLPanel(mainFrame, diemBUS, diemBUS.getListDGNL()));
        
        this.add(tabbedPane);
    }









































    // private JPanel initTabPanel(String[] header, PaginatedTable paginatedTable, String pt) {
    //     paginatedTable = new PaginatedTable(header);
    //     JTable table = paginatedTable.getTable();
    //     table.setFocusable(false);
    //     table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    //     table.getTableHeader().setPreferredSize(new Dimension(0, 40));
    //     table.setAutoCreateRowSorter(true);

    //     DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
    //     headerRenderer.setHorizontalAlignment(JLabel.CENTER);

    //     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    //     centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    //     for (int i = 0; i < table.getColumnCount(); i++) {
    //         table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    //     }

    //     JPanel panel = new PanelBorderRadius();
    //     panel.setLayout(new BorderLayout());
    //     panel.setBackground(Color.WHITE);
    //     panel.add(paginatedTable, BorderLayout.CENTER);
    //     return panel;
    //     // loadDataTable(paginatedTable, pt);
    // }

    // private void loadDataTable(PaginatedTable table, String phuongThuc) {
    //     List<Object[]> rows = new ArrayList<>();
    //     int stt = 1;
    //     for (XtDiemThiXetTuyen d : listDiem) {
    //         if (phuongThuc.equals("DGNL")) {
    //             rows.add(new Object[]{
    //                 stt++,
    //                 d.getCccd(),
    //                 convert(d.getNl1())
    //             });
    //         } else if (phuongThuc.equals("VSAT")) {
    //             rows.add(new Object[]{
    //                 stt++,
    //                 d.getCccd(),
    //                 convert(d.getTo()),
    //                 convert(d.getVa()),
    //                 convert(d.getLi()),
    //                 convert(d.getHo()),
    //                 convert(d.getSi()),
    //                 convert(d.getSu()),
    //                 convert(d.getDi()),
    //                 convert(d.getN1Thi())
    //             });
    //         } else {
    //             rows.add(new Object[]{
    //                 stt++,
    //                 d.getCccd(),
    //                 convert(d.getTo()),
    //                 convert(d.getVa()),
    //                 convert(d.getLi()),
    //                 convert(d.getHo()),
    //                 convert(d.getSi()),
    //                 convert(d.getSu()),
    //                 convert(d.getDi()),
    //                 convert(d.getGdcd()),
    //                 convert(d.getN1Thi()),
    //                 convert(d.getN1Cc()),
    //                 convert(d.getCncn()),
    //                 convert(d.getCnnn()),
    //                 convert(d.getTi()),
    //                 convert(d.getKtpl()),
    //                 convert(d.getNk1()),
    //                 convert(d.getNk2()),
    //                 convert(d.getNk3()),
    //                 convert(d.getNk4()),
    //                 convert(d.getNk5()),
    //                 convert(d.getNk6())
    //             });
    //         }
    //     }

    //     table.setData(rows);
    // }

    // private String convert(BigDecimal value) {
    //     return value != null && value.compareTo(BigDecimal.ZERO) != 0
    //         ? value.stripTrailingZeros().toPlainString()
    //         : "---";
    // }
}
