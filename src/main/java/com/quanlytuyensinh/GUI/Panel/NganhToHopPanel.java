package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Main;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class NganhToHopPanel extends JPanel implements ActionListener, ItemListener {

    private final XtNganhToHopBUS nganhToHopBUS;
    private List<XtNganhToHop> listNganhToHop;

    private PanelBorderRadius pnlMain, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private Color BackgroundColor = new Color(240, 247, 250);

    public NganhToHopPanel(Main mainF) {
        this.nganhToHopBUS = new XtNganhToHopBUS();
        initComponent();                                    
        this.listNganhToHop = nganhToHopBUS.getAll();    
        loadDataTable(listNganhToHop);                     
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khai báo header cho bảng
        String[] header = {
            "ID", "Mã Ngành", "Mã Tổ Hợp",
            "Môn 1", "HS 1", "Môn 2", "HS 2", "Môn 3", "HS 3",
            "TB Keys", "N1", "TO", "LI", "HO", "SI", "VA", "SU", "DI", "TI", "KHAC", "KTPL",
            "Độ Lệch"
        };

        paginatedTable = new PaginatedTable(header);
        JTable table = paginatedTable.getTable();

        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setRowHeight(35);

        // can giua header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // can giua nd
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        table.setAutoCreateRowSorter(false);

 
        Comparator<Object>[] comps = new Comparator[22];
        comps[0]  = TableSorter.INTEGER_COMPARATOR;        // ID
        comps[1]  = TableSorter.STRING_COMPARATOR;         // Mã Ngành
        comps[2]  = TableSorter.STRING_COMPARATOR;         // Mã Tổ Hợp
        comps[3]  = TableSorter.STRING_COMPARATOR;         // Môn 1
        comps[4]  = TableSorter.INTEGER_COMPARATOR;        // HS 1
        comps[5]  = TableSorter.STRING_COMPARATOR;         // Môn 2
        comps[6]  = TableSorter.INTEGER_COMPARATOR;        // HS 2
        comps[7]  = TableSorter.STRING_COMPARATOR;         // Môn 3
        comps[8]  = TableSorter.INTEGER_COMPARATOR;        // HS 3
        comps[9]  = TableSorter.STRING_COMPARATOR;         // TB Keys
        comps[10] = TableSorter.STRING_COMPARATOR;         // N1
        comps[11] = TableSorter.STRING_COMPARATOR;         // TO
        comps[12] = TableSorter.STRING_COMPARATOR;         // LI
        comps[13] = TableSorter.STRING_COMPARATOR;         // HO
        comps[14] = TableSorter.STRING_COMPARATOR;         // SI
        comps[15] = TableSorter.STRING_COMPARATOR;         // VA
        comps[16] = TableSorter.STRING_COMPARATOR;         // SU
        comps[17] = TableSorter.STRING_COMPARATOR;         // DI
        comps[18] = TableSorter.STRING_COMPARATOR;         // TI
        comps[19] = TableSorter.STRING_COMPARATOR;         // KHAC
        comps[20] = TableSorter.STRING_COMPARATOR;         // KTPL
        comps[21] = TableSorter.BIG_DECIMAL_COMPARATOR;   // Độ Lệch

        paginatedTable.enableFullDataSorting(comps);

 
        pnlBorder1 = new JPanel(); pnlBorder1.setPreferredSize(new Dimension(0, 10)); pnlBorder1.setBackground(BackgroundColor);
        pnlBorder2 = new JPanel(); pnlBorder2.setPreferredSize(new Dimension(0, 10)); pnlBorder2.setBackground(BackgroundColor);
        pnlBorder3 = new JPanel(); pnlBorder3.setPreferredSize(new Dimension(10, 0)); pnlBorder3.setBackground(BackgroundColor);
        pnlBorder4 = new JPanel(); pnlBorder4.setPreferredSize(new Dimension(10, 0)); pnlBorder4.setBackground(BackgroundColor);

        this.add(pnlBorder1, BorderLayout.NORTH);
        this.add(pnlBorder2, BorderLayout.SOUTH);
        this.add(pnlBorder3, BorderLayout.EAST);
        this.add(pnlBorder4, BorderLayout.WEST);


        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

      
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = {"create", "update", "delete", "detail", "import"};
        mainFunction = new MainFunction(1, "nganhtohop", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã ngành", "Mã tổ hợp", "Môn 1", "Môn 2", "Môn 3"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        search.btnReset.addActionListener(e -> resetSearch());

        functionBar.add(mainFunction);
        functionBar.add(search);

        // panel bang
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        contentCenter.add(pnlMain, BorderLayout.CENTER);

        setColumnWidths();
    }

    private void setColumnWidths() {
        JTable table = paginatedTable.getTable();
        table.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(120);  // Mã Ngành
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Mã Tổ Hợp
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Môn 1
        table.getColumnModel().getColumn(4).setPreferredWidth(55);   // HS 1
        table.getColumnModel().getColumn(5).setPreferredWidth(80);   // Môn 2
        table.getColumnModel().getColumn(6).setPreferredWidth(55);   // HS 2
        table.getColumnModel().getColumn(7).setPreferredWidth(80);   // Môn 3
        table.getColumnModel().getColumn(8).setPreferredWidth(55);   // HS 3
        table.getColumnModel().getColumn(9).setPreferredWidth(100);  // TB Keys
        table.getColumnModel().getColumn(10).setPreferredWidth(50);  // N1
        table.getColumnModel().getColumn(11).setPreferredWidth(50);  // TO
        table.getColumnModel().getColumn(12).setPreferredWidth(50);  // LI
        table.getColumnModel().getColumn(13).setPreferredWidth(50);  // HO
        table.getColumnModel().getColumn(14).setPreferredWidth(50);  // SI
        table.getColumnModel().getColumn(15).setPreferredWidth(50);  // VA
        table.getColumnModel().getColumn(16).setPreferredWidth(50);  // SU
        table.getColumnModel().getColumn(17).setPreferredWidth(50);  // DI
        table.getColumnModel().getColumn(18).setPreferredWidth(50);  // TI
        table.getColumnModel().getColumn(19).setPreferredWidth(60);  // KHAC
        table.getColumnModel().getColumn(20).setPreferredWidth(60);  // KTPL
        table.getColumnModel().getColumn(21).setPreferredWidth(80);  // Độ Lệch
    }

 

    public void loadDataTable(List<XtNganhToHop> list) {
        this.listNganhToHop = list;
        paginatedTable.setData(buildRows(list));
    }

    private Object[] buildRow(XtNganhToHop nth) {
        return new Object[]{
            "NTH-" + nth.getId(),
            nth.getManganh(),
            nth.getMatohop(),
            nth.getThMon1(),
            nth.getHsMon1(),
            nth.getThMon2(),
            nth.getHsMon2(),
            nth.getThMon3(),
            nth.getHsMon3(),
            nth.getTbKeys(),
            boolToText(nth.getN1()),
            boolToText(nth.getTo()),
            boolToText(nth.getLi()),
            boolToText(nth.getHo()),
            boolToText(nth.getSi()),
            boolToText(nth.getVa()),
            boolToText(nth.getSu()),
            boolToText(nth.getDi()),
            boolToText(nth.getTi()),
            boolToText(nth.getKhac()),
            boolToText(nth.getKtpl()),
            nth.getDolech()
        };
    }

    private List<Object[]> buildRows(List<XtNganhToHop> list) {
        List<Object[]> rows = new java.util.ArrayList<>();
        for (XtNganhToHop nth : list) rows.add(buildRow(nth));
        return rows;
    }

    private String boolToText(Boolean value) {
        if (value == null) return "";
        return value ? "Có" : "Không";
    }



    private void performSearch() {
        if (listNganhToHop == null) return;
        String keyword    = search.txtSearchForm.getText().trim().toLowerCase();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        if (keyword.isEmpty()) {
            paginatedTable.setData(buildRows(listNganhToHop));
            return;
        }

        List<Object[]> filtered = new java.util.ArrayList<>();
        for (XtNganhToHop nth : listNganhToHop) {
            boolean match = false;
            switch (searchType) {
                case "Tất cả":
                    match = nvl(nth.getManganh()).toLowerCase().contains(keyword)
                         || nvl(nth.getMatohop()).toLowerCase().contains(keyword)
                         || nvl(nth.getThMon1()).toLowerCase().contains(keyword)
                         || nvl(nth.getThMon2()).toLowerCase().contains(keyword)
                         || nvl(nth.getThMon3()).toLowerCase().contains(keyword)
                         || nvl(nth.getTbKeys()).toLowerCase().contains(keyword);
                    break;
                case "Mã ngành":   match = nvl(nth.getManganh()).toLowerCase().contains(keyword);  break;
                case "Mã tổ hợp":  match = nvl(nth.getMatohop()).toLowerCase().contains(keyword);  break;
                case "Môn 1":      match = nvl(nth.getThMon1()).toLowerCase().contains(keyword);   break;
                case "Môn 2":      match = nvl(nth.getThMon2()).toLowerCase().contains(keyword);   break;
                case "Môn 3":      match = nvl(nth.getThMon3()).toLowerCase().contains(keyword);   break;
            }
            if (match) filtered.add(buildRow(nth));
        }
        paginatedTable.setData(filtered);
    }

    private void resetSearch() {
        search.txtSearchForm.setText("");
        search.cbxChoose.setSelectedIndex(0);
        listNganhToHop = nganhToHopBUS.getAll();
        loadDataTable(listNganhToHop);
    }

    private String nvl(String s) { return s == null ? "" : s; }



    private XtNganhToHop getSelectedRecord() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bản ghi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        String idStr  = paginatedTable.getTable().getModel().getValueAt(modelRow, 0).toString();
        int id = Integer.parseInt(idStr.replace("NTH-", ""));
        for (XtNganhToHop nth : listNganhToHop) {
            if (nth.getId() == id) return nth;
        }
        return null;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == mainFunction.btn.get("create")) {
            JOptionPane.showMessageDialog(this,
                "Chức năng Thêm ngành – tổ hợp chưa được thực hiện.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (source == mainFunction.btn.get("import")) {
            JOptionPane.showMessageDialog(this,
                "Chức năng Import chưa được thực hiện.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (source == mainFunction.btn.get("update") ||
                 source == mainFunction.btn.get("delete") ||
                 source == mainFunction.btn.get("detail")) {

            XtNganhToHop selected = getSelectedRecord();
            if (selected == null) return;

            if (source == mainFunction.btn.get("update")) {
                JOptionPane.showMessageDialog(this,
                    "Chức năng Sửa chưa được thực hiện.",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (source == mainFunction.btn.get("detail")) {
                JOptionPane.showMessageDialog(this,
                    "Chức năng Xem chi tiết chưa được thực hiện.",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (source == mainFunction.btn.get("delete")) {
                JOptionPane.showMessageDialog(this,
                    "Chức năng Xóa chưa được thực hiện.",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            performSearch();
        }
    }
}