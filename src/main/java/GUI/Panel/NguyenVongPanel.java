/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.XtNguyenVongXetTuyenBUS;
import ENTITY.XtNguyenVongXetTuyen;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import javax.swing.*;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Main;
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

/**
 *
 * @author DELL
 */
public class NguyenVongPanel extends JPanel implements ActionListener, ItemListener {
    PanelBorderRadius pnlMain, functionBar;
    Main mainFrame;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableNguyenVong;
    JScrollPane scrollTableNguyenVong;
    MainFunction mainFunction; // Thanh function
    IntegratedSearch search; // Thanh Search
    DefaultTableModel tblModel;

    XtNguyenVongXetTuyenBUS NVBUS = new XtNguyenVongXetTuyenBUS();
    List<XtNguyenVongXetTuyen> listNV = NVBUS.getAllNguyenVong();
    Color BackgroundColor = new Color(240, 247, 250);
    
    public NguyenVongPanel(Main mainF) {
        this.mainFrame = mainF;
        initComponent();
        loadDataTable(listNV);
    }
    
        private void initComponent() {
           this.setBackground(BackgroundColor);
           this.setLayout(new BorderLayout(0, 0));
           this.setOpaque(true);

           tableNguyenVong = new JTable();
           scrollTableNguyenVong = new JScrollPane();

           tblModel = new DefaultTableModel() {
               @Override
               public boolean isCellEditable(int row, int column) {
                   return false;
               }
           };
           // Table Header
           String[] header = new String[] { "ID", "CCCD", "Mã ngành", "Thứ tự NV", "Điểm xét", "Điểm UT", "Điểm cộng", "Kết quả", "Phương thức",
                   "Tổ hợp", "NV_Keys" };
           tblModel.setColumnIdentifiers(header);
           tableNguyenVong.setModel(tblModel);
           tableNguyenVong.setFocusable(false);
           tableNguyenVong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
           tableNguyenVong.getTableHeader().setPreferredSize(new Dimension(0, 40));
           DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableNguyenVong.getTableHeader()
                   .getDefaultRenderer();
           headerRenderer.setHorizontalAlignment(JLabel.CENTER);
           scrollTableNguyenVong.setViewportView(tableNguyenVong);
           // Table Cell
           DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
           centerRenderer.setHorizontalAlignment(JLabel.CENTER);
           for (int i = 0; i < tableNguyenVong.getColumnCount(); i++) {
               tableNguyenVong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
           }
           // Table Sorter
           tableNguyenVong.setAutoCreateRowSorter(true);
           TableSorter.configureTableColumnSorter(tableNguyenVong, 0, TableSorter.INTEGER_COMPARATOR);

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
               listNV = NVBUS.getAllNguyenVong();
               loadDataTable(listNV);
           });

           functionBar.add(search);
           contentCenter.add(functionBar, BorderLayout.NORTH);

           pnlMain = new PanelBorderRadius();
           pnlMain.setLayout(new BorderLayout());
           pnlMain.setBackground(Color.WHITE);
           pnlMain.add(scrollTableNguyenVong, BorderLayout.CENTER);
           contentCenter.add(pnlMain, BorderLayout.CENTER);

       }

        private void loadDataTable(List<XtNguyenVongXetTuyen> listNV) {
            tblModel.setRowCount(0);
            for (XtNguyenVongXetTuyen nv : listNV) {
                tblModel.addRow(new Object[] {
                        "NV-" + nv.getIdnv(),
                        nv.getNnCccd(),
                        nv.getNvManganh(),
                        nv.getNvTt(),
                        nv.getDiemThxt(),
                        nv.getDiemUtqd(),
                        nv.getDiemCong(),
                        nv.getNvKetqua(),
                        nv.getTtPhuongthuc(),
                        nv.getTtThm(),
                        nv.getNvKeys()
                });
            }
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
