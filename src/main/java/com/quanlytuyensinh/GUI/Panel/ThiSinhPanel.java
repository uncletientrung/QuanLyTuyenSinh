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
import com.quanlytuyensinh.GUI.Dialog.ThiSinh.ThiSinhDialog;
import com.quanlytuyensinh.GUI.Dialog.testDialog;
import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.UTIL.ExcelImportUtil;
import com.quanlytuyensinh.helper.Validation;
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

        String[] action = { "create", "update", "delete", "detail", "import" };
        mainFunction = new MainFunction(1, "thiSinh", action); // Sửa khi có nhóm quyền
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[] { "Tất cả", "Mã", "Căn cước CD", "SBD", "Họ Tên", "Số điện thoại", "Email", "Nơi sinh","Khu vực" });
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                 Search(); //Viết hàm Search
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
        listTS = this.TSBUS.searchThiSinh(keyword, searchType);
        loadDataTable(listTS);
    }
    private void resetSearch() {
        search.txtSearchForm.setText("");
        search.cbxChoose.setSelectedIndex(0);
        listTS = this.TSBUS.getAllThiSinh();
        loadDataTable(listTS);
    }

    private void loadDataTable(List<XtThisinhXetTuyen25> listTS) {
        List<Object[]> data = new ArrayList<>();
        for (XtThisinhXetTuyen25 ts : listTS) {
            data.add(new Object[] {
                    "TS-"+ts.getIdthisinh(),
                    ts.getCccd(),
                    ts.getSobaodanh(),
                    ts.getHo(),
                    ts.getTen(),
                    ts.getGioiTinh(),
                    ts.getNgaySinh(),
                    ts.getDienThoai(),
                    ts.getEmail(),
                    ts.getNoiSinh(),
                    ts.getKhuVuc(),
                    ts.getDoiTuong()
            });
        }
        paginatedTable.setData(data);
    }

    private XtThisinhXetTuyen25 getSelectedThiSinh() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một thí sinh!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);// Mục đích là để lấy model lấy dữ liệu gốc để nếu chọn hàng đó mà sort thì nó thay đổi theo
        String idStr  = paginatedTable.getTable().getModel().getValueAt(modelRow, 0).toString();
        int id = Integer.parseInt(idStr.replace("TS-", ""));
        for (XtThisinhXetTuyen25 ts : listTS) {
            if (ts.getIdthisinh() == id) return ts;
        }
        return null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); // Lấy Frame cha
        Object source = e.getSource();
        
        if(source == mainFunction.btn.get("create") ){
            new ThiSinhDialog(this, owner, "THÊM THÍ SINH", "create",true, () -> {
                                                                                                                                                    listTS = TSBUS.getAllThiSinh();
                                                                                                                                                    loadDataTable(listTS);
                                                                                                                                                }, null
            );
        }
        else if (source == mainFunction.btn.get("import")) {
            importExcel();
        }else if (source == mainFunction.btn.get("detail") || source == mainFunction.btn.get("update") || 
                            source == mainFunction.btn.get("delete")){
            XtThisinhXetTuyen25 thiSinhDuocChon = getSelectedThiSinh();
            if (thiSinhDuocChon == null) return;
                
            if(source == mainFunction.btn.get("detail") ){
                new ThiSinhDialog(this, owner, "XEM CHI TIẾT THÍ SINH " + "TS-" +thiSinhDuocChon.getIdthisinh(), "detail",true, () -> {
                                                                                                                                                    listTS = TSBUS.getAllThiSinh();
                                                                                                                                                    loadDataTable(listTS);
                                                                                                                                                }, thiSinhDuocChon
                );
            }else if(source == mainFunction.btn.get("update") ){
                new ThiSinhDialog(this, owner, "SỬA THÍ SINH " + "TS-" +thiSinhDuocChon.getIdthisinh(), "update",true, () -> {
                                                                                                                                                    listTS = TSBUS.getAllThiSinh();
                                                                                                                                                    loadDataTable(listTS);
                                                                                                                                                }, thiSinhDuocChon
                );
            }else if(source == mainFunction.btn.get("delete") ){
               int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "<html>"
                    + "<h3 style='color:red;'>Xác nhận xóa thí sinh</h3>"
                    + "<hr>"
                    + "<b>Họ tên:</b> " + thiSinhDuocChon.getHo() + " " + thiSinhDuocChon.getTen() + "<br>"
                    + "<b>CCCD:</b> " + thiSinhDuocChon.getCccd() + "<br>"
                    + "<b>Số báo danh:</b> " + thiSinhDuocChon.getSobaodanh()
                    + "</html>",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
                if (confirm == JOptionPane.YES_OPTION) {
                        if (TSBUS.deleteThiSinh(thiSinhDuocChon.getIdthisinh())) {
                            JOptionPane.showMessageDialog(this, "Xóa thành công!");
                            listTS = TSBUS.getAllThiSinh();
                            loadDataTable(listTS);
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            }
            
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Search();
        }
    }
    public XtThisinhXetTuyen25BUS getBUS() {
        return TSBUS;
    }
    
           // ===================== IMPORT EXCEL =====================
    private void importExcel() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Excel Files",
                        "xlsx",
                        "xls"
                )
        );

        int result = fileChooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

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
                "Import Excel",
                true
        );

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(this);

        // ================= WORKER =================

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {

            int success = 0;
            int skipped = 0;

            StringBuilder errors = new StringBuilder();

            @Override
            protected Void doInBackground() {

                try {

                    List<XtThisinhXetTuyen25> listImport =
                            ExcelImportUtil.readThiSinhFromExcel(
                                    selectedFile.getAbsolutePath()
                            );

                    if (listImport.isEmpty()) {

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    ThiSinhPanel.this,
                                    "Không có dữ liệu hợp lệ!",
                                    "Thông báo",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        });

                        return null;
                    }

                    int total = listImport.size();
                    int current = 0;

                    for (XtThisinhXetTuyen25 ts : listImport) {

                        current++;

                        // ===== VALIDATE =====

                        String errorMsg = validateThiSinh(ts);

                        if (errorMsg != null) {

                            errors.append("Dòng ")
                                    .append(current + 1)
                                    .append(" - CCCD: ")
                                    .append(ts.getCccd())
                                    .append(" -> ")
                                    .append(errorMsg)
                                    .append("\n");

                            skipped++;

                        } else {

                            try {

                                if (TSBUS.insertThiSinh(ts)) {
                                    success++;
                                } else {

                                    skipped++;

                                    errors.append("Dòng ")
                                            .append(current + 1)
                                            .append(" insert thất bại\n");
                                }

                            } catch (Exception ex) {

                                skipped++;

                                errors.append("Dòng ")
                                        .append(current + 1)
                                        .append(": ")
                                        .append(ex.getMessage())
                                        .append("\n");
                            }
                        }

                        // ===== UPDATE PROGRESS =====

                        int percent = (current * 100) / total;

                        publish(percent);
                    }

                } catch (Exception ex) {

                    SwingUtilities.invokeLater(() -> {

                        JOptionPane.showMessageDialog(
                                ThiSinhPanel.this,
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

                lblStatus.setText("Đang import... " + value + "%");
            }

            @Override
            protected void done() {

                dialog.dispose();

                listTS = TSBUS.getAllThiSinh();

                loadDataTable(listTS);

                String message =
                        "Import hoàn tất!\n\n"
                        + "Thành công: " + success + "\n"
                        + "Bỏ qua: " + skipped;

                if (errors.length() > 0) {

                    JTextArea textArea = new JTextArea(errors.toString());

                    textArea.setEditable(false);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    JScrollPane scroll = new JScrollPane(textArea);

                    scroll.setPreferredSize(new Dimension(600, 300));

                    JPanel panel = new JPanel(new BorderLayout(10, 10));

                    JLabel lbl = new JLabel(
                            "<html>"
                            + "Import hoàn tất!<br>"
                            + "Thành công: " + success + "<br>"
                            + "Bỏ qua: " + skipped
                            + "</html>"
                    );

                    panel.add(lbl, BorderLayout.NORTH);
                    panel.add(scroll, BorderLayout.CENTER);

                    JOptionPane.showMessageDialog(
                            ThiSinhPanel.this,
                            panel,
                            "Kết quả Import",
                            JOptionPane.WARNING_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            ThiSinhPanel.this,
                            message,
                            "Kết quả Import",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        };

        worker.execute();

        dialog.setVisible(true);
    }
    private String validateThiSinh(XtThisinhXetTuyen25 ts) {
        // CCCD
        if (Validation.isEmpty(ts.getCccd())) {
            return "CCCD không được để trống!";
        }
//        if (!ts.getCccd().matches("\\d{12}")) {
//            return "CCCD phải gồm đúng 12 chữ số!";
//        }
        if (!TSBUS.checkCCCD(ts.getCccd(), 0)) {
            return "CCCD đã tồn tại trong hệ thống!";
        }

        // Họ Tên (ít nhất phải có họ hoặc tên)
        if (Validation.isEmpty(ts.getHo()) && Validation.isEmpty(ts.getTen())) {
            return "Họ Tên không được để trống!";
        }

        // Giới tính
        if (Validation.isEmpty(ts.getGioiTinh())) {
            return "Giới tính không được để trống!";
        }
        String gt = ts.getGioiTinh().trim().toLowerCase();
        if (!gt.equals("nam") && !gt.equals("nữ") && !gt.equals("male") && !gt.equals("female")) {
            return "Giới tính chỉ được là Nam hoặc Nữ!";
        }

        // Ngày sinh
        if (Validation.isEmpty(ts.getNgaySinh())) {
            return "Ngày sinh không được để trống!";
        }

        return null; // Valid
    }
}
