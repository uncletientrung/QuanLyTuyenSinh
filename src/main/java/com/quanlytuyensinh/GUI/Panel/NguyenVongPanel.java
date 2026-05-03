/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import javax.swing.*;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Dialog.NguyenVongDialog;
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
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author DELL
 */
public class NguyenVongPanel extends JPanel implements ActionListener, ItemListener {
    PanelBorderRadius pnlMain, functionBar;
    Main mainFrame;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    MainFunction mainFunction; // Thanh function
    IntegratedSearch search; // Thanh Search
    private PaginatedTable paginatedTable;
    
    XtNguyenVongXetTuyenBUS NVBUS;
    List<XtNguyenVongXetTuyen> listNV;
    Color BackgroundColor = new Color(240, 247, 250);
    
    public NguyenVongPanel(Main mainF) {
        this.mainFrame = mainF;
        NVBUS = new XtNguyenVongXetTuyenBUS();
        listNV = NVBUS.getAllNguyenVong();
        initComponent();
        loadDataTable(listNV);
    }
    
        private void initComponent() {
           this.setBackground(BackgroundColor);
           this.setLayout(new BorderLayout(0, 0));
           this.setOpaque(true);

           // Table Header
           String[] header = new String[] { "ID", "CCCD", "Mã ngành", "Thứ tự NV", "Điểm THXT", "Điểm UT", "Điểm cộng", "Điểm xét tuyển","Kết quả", "Phương thức",
                   "Tổ hợp" };
           paginatedTable = new PaginatedTable(header);
           
           JTable table = paginatedTable.getTable();
           table.setFocusable(false);
           table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
           table.getTableHeader().setPreferredSize(new Dimension(0, 40));
           table.setAutoCreateRowSorter(true);
           TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);
           
           DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
           headerRenderer.setHorizontalAlignment(JLabel.CENTER);
           
           // Table Cell
           DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
           centerRenderer.setHorizontalAlignment(JLabel.CENTER);
           for (int i = 0; i < table.getColumnCount(); i++) {
               table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
           }
           // Table Sorter
           table.setAutoCreateRowSorter(false);
           Comparator<Object>[] comps = new Comparator[11];
            comps[0] = TableSorter.INTEGER_COMPARATOR;     // ID
            comps[1] = TableSorter.STRING_COMPARATOR;      // CCCD
            comps[2] = TableSorter.STRING_COMPARATOR;      // Mã ngành
            comps[3] = TableSorter.INTEGER_COMPARATOR;      // Thứ tự nguyện vọng
            comps[4] = TableSorter.BIG_DECIMAL_COMPARATOR;      // Điểm THXT
            comps[5] = TableSorter.BIG_DECIMAL_COMPARATOR;      // Điểm UT
            comps[6] = TableSorter.BIG_DECIMAL_COMPARATOR;      // Điểm cộng
            comps[7] = TableSorter.BIG_DECIMAL_COMPARATOR;      // Điểm xét tuyển
            comps[8] = TableSorter.STRING_COMPARATOR;      // Kết quả
            comps[9] = TableSorter.STRING_COMPARATOR;      // Phương thức
            comps[10] = TableSorter.STRING_COMPARATOR;      // Tổ hợp
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

           search = new IntegratedSearch(new String[] { "Tất cả", "Mã", "Căn cước CD", "Mã ngành", "Phương thức", "Tổ hợp" });
           search.txtSearchForm.addKeyListener(new KeyAdapter() {
               @Override
               public void keyReleased(KeyEvent e) {
                    Search();
               }
           });
           search.cbxChoose.addItemListener(this);
           search.btnReset.addActionListener(e -> {
              resetSearch();
           });

           functionBar.add(search);
           contentCenter.add(functionBar, BorderLayout.NORTH);

           pnlMain = new PanelBorderRadius();
           pnlMain.setLayout(new BorderLayout());
           pnlMain.setBackground(Color.WHITE);
           pnlMain.add(paginatedTable, BorderLayout.CENTER);
           contentCenter.add(pnlMain, BorderLayout.CENTER);

       }
        
        private void Search(){
            String keyword = this.search.txtSearchForm.getText().trim();
            String searchType = (String) this.search.cbxChoose.getSelectedItem(); // Trả về value luôn
            listNV = this.NVBUS.searchNguyenVong(keyword, searchType);
            loadDataTable(listNV);
        }
        private void resetSearch() {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            listNV = this.NVBUS.getAllNguyenVong();
            loadDataTable(listNV);
        }
        

        private void loadDataTable(List<XtNguyenVongXetTuyen> listNV) {
            List<Object[]> data = new ArrayList<>();
            for (XtNguyenVongXetTuyen nv : listNV) {
                data.add(new Object[] {
                        "NV-" + nv.getIdnv(),
                        nv.getNnCccd(),
                        nv.getNvManganh(),
                        nv.getNvTt(),
                        nv.getDiemThxt(),
                        nv.getDiemUtqd(),
                        nv.getDiemCong(),
                        nv.getDiemXettuyen(),
                        nv.getNvKetqua(),
                        nv.getTtPhuongthuc(),
                        nv.getTtThm(),
                });
            } 
            paginatedTable.setData(data);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); // Lấy Frame cha
        Object source = e.getSource();
        
        if(source == mainFunction.btn.get("create") ){
            new NguyenVongDialog(this, owner, "THÊM NGUYỆN VỌNG", "create",true, () -> {
                                                                                                                                                    listNV = NVBUS.getAllNguyenVong();
                                                                                                                                                    loadDataTable(listNV);
                                                                                                                                                }, null
            );
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
         Search();
    }
    public XtNguyenVongXetTuyenBUS getBUS(){
        return NVBUS;
    }

}
