/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
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
    
     // Tác vụ thêm để xử lý
    private XtNganhBUS NganhBUS =new XtNganhBUS();
    private List<XtNganh> listNganh;
    private XtNganhToHopBUS NganhTHBUS = new XtNganhToHopBUS() ;
    private List<XtNganhToHop> listNganhTH;
    private XtDiemCongXetTuyenBUS DiemCongBUS = new XtDiemCongXetTuyenBUS();
    private List<XtDiemCongXetTuyen> listDiemCong;
    private XtThisinhXetTuyen25BUS TSBUS = new XtThisinhXetTuyen25BUS();
    private List<XtThisinhXetTuyen25> listTS;
    private XtDiemThiXetTuyenBUS DTBUS = new XtDiemThiXetTuyenBUS();
     private List<XtDiemThiXetTuyen> listDT;
    
    public NguyenVongPanel(Main mainF) {
        this.mainFrame = mainF;
        NVBUS = new XtNguyenVongXetTuyenBUS();
        listNV = NVBUS.getAllNguyenVong();
        
        this.listDiemCong = DiemCongBUS.getAllDiemCong();
        this.listNganhTH = NganhTHBUS.getAll();
        this.listNganh = NganhBUS.getAllNganh();
        this.listTS = this.TSBUS.getAllThiSinh();
        this.listDT = DTBUS.getList();
           
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
    private XtNguyenVongXetTuyen getSelectedNguyenVong() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyện vọng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);// Mục đích là để lấy model lấy dữ liệu gốc để nếu chọn hàng đó mà sort thì nó thay đổi theo
        String idStr  = paginatedTable.getTable().getModel().getValueAt(modelRow, 0).toString();
        int id = Integer.parseInt(idStr.replace("NV-", ""));
        for (XtNguyenVongXetTuyen nv : listNV) {
            if (nv.getIdnv()== id) return nv;
        }
        return null;        
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
        }else if (source == mainFunction.btn.get("detail") || source == mainFunction.btn.get("update") || 
                            source == mainFunction.btn.get("delete")){
            XtNguyenVongXetTuyen NguyenVongDuocChon = getSelectedNguyenVong();
             if (NguyenVongDuocChon == null) return;
                
            if(source == mainFunction.btn.get("detail") ){
                new NguyenVongDialog(this, owner, "XEM CHI TIẾT NGUYỆN VỌNG " + "NV-" +NguyenVongDuocChon.getIdnv(), "detail",true, () -> {
                                                                                                                                                    listNV = NVBUS.getAllNguyenVong();
                                                                                                                                                    loadDataTable(listNV);
                                                                                                                                                }, NguyenVongDuocChon
                );
            } else if(source == mainFunction.btn.get("update") ){
                if(NguyenVongDuocChon.getNvKetqua() == null  || NguyenVongDuocChon.getNvKetqua().trim().equals("Đang xét") ){
                    new NguyenVongDialog(this, owner, "SỬA NGUYỆN VỌNG " + "NV-" +NguyenVongDuocChon.getIdnv(), "update",true, () -> {
                                                                                                                                                    listNV = NVBUS.getAllNguyenVong();
                                                                                                                                                    loadDataTable(listNV);
                                                                                                                                                }, NguyenVongDuocChon
                    );
                }else{
                    JOptionPane.showMessageDialog(this, "Kết quả nguyện vọng đã công bố không thể sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }else if(source == mainFunction.btn.get("delete") ){
                int confirm = JOptionPane.showConfirmDialog(this,
                        "<html>"
                            + "<h3 style='color:red;'>Xác nhận xóa nguyện vọng</h3>"
                            + "<hr>"
                            + "<b>Thứ tự nguyện vọng:</b> " + NguyenVongDuocChon.getNvTt() + "<br>"
                            + "<b>CCCD:</b> " + NguyenVongDuocChon.getNnCccd() + "<br>"
                            + "<b>Mã ngành:</b> " + NguyenVongDuocChon.getNvManganh()
                        + "</html>",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                        if (NVBUS.deleteNguyenVong(NguyenVongDuocChon.getIdnv())) {
                            JOptionPane.showMessageDialog(this, "Xóa nguyện vọng thành công!");
                            listNV = NVBUS.getAllNguyenVong();
                            loadDataTable(listNV);
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
         Search();
    }
    public XtNguyenVongXetTuyenBUS getBUS(){
        return NVBUS;
    }
    // XtNganhBUS
    public XtNganhBUS getNganhBUS() {
        return NganhBUS;
    }

    public void setNganhBUS(XtNganhBUS NganhBUS) {
        this.NganhBUS = NganhBUS;
    }

    // List<XtNganh>
    public List<XtNganh> getListNganh() {
        return listNganh;
    }

    public void setListNganh(List<XtNganh> listNganh) {
        this.listNganh = listNganh;
    }

    // XtNganhToHopBUS
    public XtNganhToHopBUS getNganhTHBUS() {
        return NganhTHBUS;
    }

    public void setNganhTHBUS(XtNganhToHopBUS NganhTHBUS) {
        this.NganhTHBUS = NganhTHBUS;
    }

    // List<XtNganhToHop>
    public List<XtNganhToHop> getListNganhTH() {
        return listNganhTH;
    }

    public void setListNganhTH(List<XtNganhToHop> listNganhTH) {
        this.listNganhTH = listNganhTH;
    }

    // XtDiemCongXetTuyenBUS
    public XtDiemCongXetTuyenBUS getDiemCongBUS() {
        return DiemCongBUS;
    }

    public void setDiemCongBUS(XtDiemCongXetTuyenBUS DiemCongBUS) {
        this.DiemCongBUS = DiemCongBUS;
    }

    // List<XtDiemCongXetTuyen>
    public List<XtDiemCongXetTuyen> getListDiemCong() {
        return listDiemCong;
    }

    public void setListDiemCong(List<XtDiemCongXetTuyen> listDiemCong) {
        this.listDiemCong = listDiemCong;
    }

    // XtThisinhXetTuyen25BUS
    public XtThisinhXetTuyen25BUS getTSBUS() {
        return TSBUS;
    }

    public void setTSBUS(XtThisinhXetTuyen25BUS TSBUS) {
        this.TSBUS = TSBUS;
    }

    // List<XtThisinhXetTuyen25>
    public List<XtThisinhXetTuyen25> getListTS() {
        return listTS;
    }

    public void setListTS(List<XtThisinhXetTuyen25> listTS) {
        this.listTS = listTS;
    }
    // XtDiemThiXetTuyenBUS
    public XtDiemThiXetTuyenBUS getDTBUS() {
        return DTBUS;
    }

    public void setDTBUS(XtDiemThiXetTuyenBUS DTBUS) {
        this.DTBUS = DTBUS;
    }

    // List<XtDiemThiXetTuyen>
    public List<XtDiemThiXetTuyen> getListDT() {
        return listDT;
    }

    public void setListDT(List<XtDiemThiXetTuyen> listDT) {
        this.listDT = listDT;
    }
    public List<XtNguyenVongXetTuyen> getListNV() {
        return listNV;
    }

    public void setListNV(List<XtNguyenVongXetTuyen> listNV) {
        this.listNV = listNV;
    }


}
