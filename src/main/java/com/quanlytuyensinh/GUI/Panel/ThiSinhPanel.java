/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import javax.swing.*;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Dialog.ThiSinh.ThemThiSinhDialog;
import com.quanlytuyensinh.GUI.Dialog.testDialog;
import com.quanlytuyensinh.GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author DELL
 */
public class ThiSinhPanel extends JPanel implements ActionListener, ItemListener {
    PanelBorderRadius pnlMain, functionBar;
    Main mainFrame;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    MainFunction mainFunction; // Thanh function
    IntegratedSearch search; // Thanh Search
    private PaginatedTable paginatedTable;


    XtThisinhXetTuyen25BUS TSBUS;
    List<XtThisinhXetTuyen25> listTS;
    Color BackgroundColor = new Color(240, 247, 250);

    public ThiSinhPanel(Main mainF) {
        this.mainFrame = mainF;
        TSBUS = new XtThisinhXetTuyen25BUS();
        listTS = TSBUS.getAllThiSinh();
        initComponent();
        loadDataTable(listTS);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Table Header
        String[] header = new String[] { "ID", "CCCD", "SBD", "Họ", "Tên", "Giới tính", "Ngày sinh", "SĐT", "Email",
                "Nơi sinh", "Khu vực", "Đối tượng" };
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader() .getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Table Cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        table.setAutoCreateRowSorter(false);
        Comparator<Object>[] comps = new Comparator[12];
        comps[0] = TableSorter.INTEGER_COMPARATOR;     // ID
        comps[1] = TableSorter.STRING_COMPARATOR;      // CCCD
        comps[2] = TableSorter.STRING_COMPARATOR;      // SBD
        comps[3] = TableSorter.STRING_COMPARATOR;      // Họ
        comps[4] = TableSorter.STRING_COMPARATOR;      // Tên
        comps[5] = TableSorter.STRING_COMPARATOR;      // Giới tính
        comps[6] = TableSorter.DATE_COMPARATOR;      // Ngày sinh
        comps[7] = TableSorter.STRING_COMPARATOR;      // SDT
        comps[8] = TableSorter.STRING_COMPARATOR;      // Email
        comps[9] = TableSorter.STRING_COMPARATOR;      // Nơi sinh
        comps[10] = TableSorter.STRING_COMPARATOR;      // Khu vực
        comps[11] = TableSorter.STRING_COMPARATOR;      // Đối tượng
        
        paginatedTable.enableFullDataSorting(comps);

        // Tạo khung viền
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);
        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);
        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);
        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        // Khu vực chính
        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = { "create", "update", "delete", "detail", "import", "export" };
        mainFunction = new MainFunction(1, "thiSinh", action); // Sửa khi có nhóm quyền
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[] { "Tất cả", "Mã", "Căn cước CD", "SBD", "Họ Tên", "Khu vực" });
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // thucHienTimKiem(); Viết hàm Search
            }
        });
        search.cbxChoose.addItemListener(this);
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            listTS = TSBUS.getAllThiSinh();
            loadDataTable(listTS);
        });

        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);

    }

    private void loadDataTable(List<XtThisinhXetTuyen25> listTS) {
        List<Object[]> data = new ArrayList<>();
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (XtThisinhXetTuyen25 ts : listTS) {
            data.add(new Object[] {
                    ts.getIdthisinh(),
                    ts.getCccd(),
                    ts.getSobaodanh(),
                    ts.getHo(),
                    ts.getTen(),
                    ts.getGioiTinh(),
                    LocalDate.parse(ts.getNgaySinh(), inputFormat).format(outputFormat),
                    ts.getDienThoai(),
                    ts.getEmail(),
                    ts.getNoiSinh(),
                    ts.getKhuVuc(),
                    ts.getDoiTuong()
            });
        }
        paginatedTable.setData(data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); // Lấy Frame cha
        Object source = e.getSource();
        if(source == mainFunction.btn.get("create") ){
            System.err.println("chạy");
            new ThemThiSinhDialog(this, owner, "THÊM THÍ SINH", "create",true, () -> {
                                                                                                                                                    listTS = TSBUS.getAllThiSinh();
                                                                                                                                                    System.out.println(listTS.size());
                                                                                                                                                    loadDataTable(listTS);
                                                                                                                                                }
            );
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public XtThisinhXetTuyen25BUS getBUS() {
        return TSBUS;
    }
}
