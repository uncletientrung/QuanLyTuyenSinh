/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtBangQuyDoiBUS;
import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.GUI.Component.CustomRowRenderer;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import javax.swing.*;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Dialog.NguyenVongDialog;
import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.UTIL.ExcelImportUtil;
import com.quanlytuyensinh.UTIL.NguyenVongImportHelper;
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
import java.io.File;
import java.math.BigDecimal;
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
    private XtNganhBUS NganhBUS;
    private List<XtNganh> listNganh;
    private XtNganhToHopBUS NganhTHBUS;
    private List<XtNganhToHop> listNganhTH;
    private XtDiemCongXetTuyenBUS DiemCongBUS;
    private List<XtDiemCongXetTuyen> listDiemCong;
    private XtThisinhXetTuyen25BUS TSBUS;
    private List<XtThisinhXetTuyen25> listTS;
    private XtDiemThiXetTuyenBUS DTBUS;
    private List<XtDiemThiXetTuyen> listDT;
    private XtBangQuyDoiBUS BQDBUS;
    private List<XtBangQuyDoi> listBQD;
    
    public NguyenVongPanel(Main mainF,
                               XtNguyenVongXetTuyenBUS nvBUS, List<XtNguyenVongXetTuyen> listNV,
                               XtNganhBUS nganhBUS, List<XtNganh> listNganh,
                               XtNganhToHopBUS nganhTHBUS, List<XtNganhToHop> listNganhTH,
                               XtDiemCongXetTuyenBUS diemCongBUS, List<XtDiemCongXetTuyen> listDiemCong,
                               XtThisinhXetTuyen25BUS tsBUS, List<XtThisinhXetTuyen25> listTS,
                               XtDiemThiXetTuyenBUS dtBUS, List<XtDiemThiXetTuyen> listDT,
                               XtBangQuyDoiBUS bqdBUS, List<XtBangQuyDoi> listBQD) {

            this.mainFrame = mainF;

            // Gán các BUS và List được truyền vào
            this.NVBUS = nvBUS;
            this.listNV = listNV;

            this.NganhBUS = nganhBUS;
            this.listNganh = listNganh;

            this.NganhTHBUS = nganhTHBUS;
            this.listNganhTH = listNganhTH;

            this.DiemCongBUS = diemCongBUS;
            this.listDiemCong = listDiemCong;

            this.TSBUS = tsBUS;
            this.listTS = listTS;

            this.DTBUS = dtBUS;
            this.listDT = listDT;

            this.BQDBUS = bqdBUS;
            this.listBQD = listBQD;

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
//           DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//           centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//           for (int i = 0; i < table.getColumnCount(); i++) {
//               table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//           }
           
           CustomRowRenderer rowRenderer = new CustomRowRenderer();
            paginatedTable.setCustomRowRenderer(rowRenderer);
           
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

           String[] action = { "create", "approve", "undo","update", "delete", "detail", "import" };
           mainFunction = new MainFunction(1, "nguyenVong", action); // Sửa khi có nhóm quyền
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
    
    private void showApproveProgress() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                "Đang xét tuyển tất cả nguyện vọng...", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(420, 140);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JLabel lblStatus = new JLabel("Đang xử lý...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);           // ← Thanh chạy qua chạy lại
        progressBar.setStringPainted(true);
        progressBar.setString("Đang xét tuyển...");

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(lblStatus, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        dialog.add(panel);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return NVBUS.approveAllNguyenVong(NganhBUS);
            }

            @Override
            protected void done() {
                dialog.dispose(); 

                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(NguyenVongPanel.this,
                                "Xét tuyển tất cả nguyện vọng thành công!",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(NguyenVongPanel.this,
                                "Xét tuyển thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(NguyenVongPanel.this,
                            "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                listNV = NVBUS.getAllNguyenVong();
                loadDataTable(listNV);
            }
        };

        worker.execute();
        dialog.setVisible(true);
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
        }else if (source == mainFunction.btn.get("import")) {
            importExcel();
        }
        else if (source == mainFunction.btn.get("approve")) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>" +
                    "<h3 style='color:blue;'>XÁC NHẬN XÉT TUYỂN TẤT CẢ NGUYỆN VỌNG</h3>" +
                    "<hr>" +
                    "<b>Số lượng nguyện vọng:</b> " + listNV.size() + "<br>" +
                    "</html>",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;
            showApproveProgress();
        }else if(source == mainFunction.btn.get("undo") ){
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>"
                        + "<h3 style='color:blue;'>XÁC NHẬN HOÀN XÉT TUYỂN TẤT CẢ NGUYỆN VỌNG</h3>"
                        + "<hr>"
                        + "<b>Số lượng nguyện vọng nguyện vọng hoàn:</b> " + listNV.size()+ "<br>"
                    + "</html>",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                // Xử lý xét duyệt tất cả ngành
                if (NVBUS.undoAllNguyenVong(this.NganhBUS)) {
                    JOptionPane.showMessageDialog(this, "Hoàn xét tuyển tất cả nguyện vọng thành công!");
                    listNV = NVBUS.getAllNguyenVong();
                    loadDataTable(listNV);
                } else {
                    JOptionPane.showMessageDialog(this, "Hoàn xét xét tuyển tất cả nguyện vọng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (source == mainFunction.btn.get("detail") || source == mainFunction.btn.get("update") || 
                            source == mainFunction.btn.get("delete") || source == mainFunction.btn.get("approve") || source == mainFunction.btn.get("undo")){
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

    public List<XtBangQuyDoi> getListBQD() {
        return listBQD;
    }

    public void setListBQD(List<XtBangQuyDoi> listBQD) {
        this.listBQD = listBQD;
    }
 private void importExcel() {

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Chọn file Excel nguyện vọng");

    fileChooser.setFileFilter(
            new javax.swing.filechooser.FileNameExtensionFilter(
                    "Excel Files (*.xlsx, *.xls)",
                    "xlsx",
                    "xls"
            )
    );

    int result = fileChooser.showOpenDialog(this);

    if (result != JFileChooser.APPROVE_OPTION) {
        return;
    }

    File file = fileChooser.getSelectedFile();

    // ================= PROGRESS UI =================

    JProgressBar progressBar = new JProgressBar(0, 100);
    progressBar.setStringPainted(true);
    progressBar.setValue(0);

    JLabel lblStatus = new JLabel("Đang import dữ liệu...");

    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.add(lblStatus, BorderLayout.NORTH);
    panel.add(progressBar, BorderLayout.CENTER);

    JDialog dialog = new JDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            "Import Excel Nguyện Vọng",
            true
    );

    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    dialog.getContentPane().add(panel);
    dialog.setSize(400, 120);
    dialog.setLocationRelativeTo(this);

    // ================= WORKER =================

    SwingWorker<Void, Integer> worker = new SwingWorker<>() {

        int successCount = 0;
        int failCount = 0;

        List<String> errorMessages = new ArrayList<>();

        @Override
        protected Void doInBackground() {

            try {

                // ===== ĐỌC EXCEL =====

                List<String[]> rows = ExcelImportUtil.readNguyenVongExcel(file);

                if (rows == null || rows.isEmpty()) {

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                                NguyenVongPanel.this,
                                "File Excel không có dữ liệu!",
                                "Thông báo",
                                JOptionPane.WARNING_MESSAGE
                        );
                    });

                    return null;
                }

                int total = rows.size();
                int current = 0;

                // ===== IMPORT =====

                for (String[] row : rows) {

                    current++;

                    try {

                        String cccd = row[0].trim();
                        int thuTu = Integer.parseInt(row[1].trim());
                        String maNganh = row[2].trim();

                        String tuyenThang =
                                row.length > 3 ? row[3].trim() : "";

                        // ===== TÍNH ĐIỂM =====

                        NguyenVongImportHelper helper =
                                new NguyenVongImportHelper(
                                        cccd,
                                        maNganh,
                                        NganhBUS,
                                        NganhTHBUS,
                                        DiemCongBUS,
                                        TSBUS,
                                        DTBUS,
                                        BQDBUS,
                                        listNganhTH,
                                        listDiemCong,
                                        listDT,
                                        listBQD
                                );

                        helper.tinhDiem();

                        // ===== BUILD ENTITY =====

                        XtNguyenVongXetTuyen nv =
                                new XtNguyenVongXetTuyen();

                        nv.setNnCccd(cccd);
                        nv.setNvManganh(maNganh);
                        nv.setNvTt(thuTu);

                        nv.setDiemThxt(helper.getBestDiemTH());
                        nv.setDiemUtqd(helper.getBestDiemUT());
                        nv.setDiemCong(helper.getBestDiemCong());

                        nv.setDiemXettuyen(helper.getMaxDiemXT());  

                        if (tuyenThang.equalsIgnoreCase("x")) {

                            nv.setTtPhuongthuc("Tuyển thẳng");
                            nv.setNvKetqua("Trúng tuyển");

                        } else {

                            nv.setTtPhuongthuc(
                                    helper.getBestPhuongThuc()
                            );

                            nv.setNvKetqua("Đang xét");
                        }

                        nv.setTtThm(helper.getBestToHop());

                        nv.setNvKeys(
                                cccd + "_" + maNganh + "_" + thuTu
                        );

                        // ===== INSERT =====

                        if (NVBUS.insertNguyenVong(nv)) {

                            successCount++;

                        } else {

                            errorMessages.add(
                                    "Lỗi insert DB - CCCD: "
                                    + cccd
                                    + ", Ngành: "
                                    + maNganh
                            );

                            failCount++;
                        }

                    } catch (NumberFormatException ex) {

                        errorMessages.add(
                                "Thứ tự NV không hợp lệ: "
                                + java.util.Arrays.toString(row)
                        );

                        failCount++;

                    } catch (Exception ex) {

                        errorMessages.add(
                                "Lỗi dòng ["
                                + row[0]
                                + "]: "
                                + ex.getMessage()
                        );

                        failCount++;
                    }

                    // ===== UPDATE PROGRESS =====

                    int percent = (current * 100) / total;

                    publish(percent);
                }

            } catch (Exception ex) {

                SwingUtilities.invokeLater(() -> {

                    JOptionPane.showMessageDialog(
                            NguyenVongPanel.this,
                            "Lỗi import:\n" + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                });
            }

            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {

            int value = chunks.get(chunks.size() - 1);

            progressBar.setValue(value);

            lblStatus.setText(
                    "Đang import... " + value + "%"
            );
        }

        @Override
        protected void done() {

            dialog.dispose();

            // ===== REFRESH TABLE =====

            listNV = NVBUS.getAllNguyenVong();

            loadDataTable(listNV);

            // ===== THÔNG BÁO =====

            if (!errorMessages.isEmpty()) {

                JTextArea textArea =
                        new JTextArea();

                StringBuilder sb = new StringBuilder();

                sb.append("Import hoàn tất!\n\n");
                sb.append("✔ Thành công: ")
                        .append(successCount)
                        .append("\n");

                sb.append("✘ Thất bại: ")
                        .append(failCount)
                        .append("\n\n");

                for (String err : errorMessages) {
                    sb.append("- ")
                            .append(err)
                            .append("\n");
                }

                textArea.setText(sb.toString());

                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scroll =
                        new JScrollPane(textArea);

                scroll.setPreferredSize(
                        new Dimension(650, 300)
                );

                JOptionPane.showMessageDialog(
                        NguyenVongPanel.this,
                        scroll,
                        "Kết quả Import",
                        JOptionPane.WARNING_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        NguyenVongPanel.this,
                        "Import hoàn tất!\n\n"
                        + "✔ Thành công: "
                        + successCount
                        + "\n"
                        + "✘ Thất bại: "
                        + failCount,
                        "Import thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    };

    worker.execute();

    dialog.setVisible(true);
}

}
