/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.TaiKhoanBUS;
import com.quanlytuyensinh.ENTITY.TaiKhoan;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import javax.swing.*;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import com.quanlytuyensinh.GUI.Dialog.TaiKhoanDialog;
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
public class TaiKhoanPanel extends JPanel implements ActionListener, ItemListener {
    PanelBorderRadius pnlMain, functionBar;
    Main mainFrame;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableTaiKhoan;
    JScrollPane scrollTableTaiKhoan;
    MainFunction mainFunction; // Thanh function
    IntegratedSearch search; // Thanh Search
    DefaultTableModel tblModel;
    private PaginatedTable paginatedTable;
    
    TaiKhoanBUS tkBUS= new TaiKhoanBUS();
    List<TaiKhoan> listTK =  tkBUS.getAllTaiKhoan();
    Color BackgroundColor = new Color(240, 247, 250);
   
    public TaiKhoanPanel(Main mainF ){
        this.mainFrame = mainF;
        
        initComponent();
        loadDataTable(listTK);
    }
    
    // Hàm khởi tạo
    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
       
        
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Table Header
        String[] header = new String[]{"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Nhóm quyền", "Trạng thái"};
        paginatedTable = new PaginatedTable(header);
        tableTaiKhoan = paginatedTable.getTable();
        tblModel.setColumnIdentifiers(header);
        tableTaiKhoan.setFocusable(false);
        tableTaiKhoan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableTaiKhoan.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableTaiKhoan.setAutoCreateRowSorter(true);
      TableSorter.configureTableColumnSorter(tableTaiKhoan, 0, TableSorter.INTEGER_COMPARATOR);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableTaiKhoan.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
   
        // Table Cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableTaiKhoan.getColumnCount(); i++) {
            tableTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Table Sorter
        tableTaiKhoan.setAutoCreateRowSorter(true);
        Comparator<Object>[] comps = new Comparator[12];
        comps[0] = TableSorter.INTEGER_COMPARATOR;     // ID
        comps[1] = TableSorter.STRING_COMPARATOR;      // Tên đăng nhập
        comps[2] = TableSorter.STRING_COMPARATOR;      // Mật khẩu
        comps[3] = TableSorter.INTEGER_COMPARATOR;      // Phân quyền
        comps[4] = TableSorter.INTEGER_COMPARATOR;      // Trạng thái
       
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

        String[] action = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(1, "nguoiDung", action); // Sửa khi có nhóm quyền
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã tài khoản", "Tên đăng nhập", "Mật khẩu"});
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Search();
            }
        });
        search.cbxChoose.addItemListener(this);
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            listTK = tkBUS.getAllTaiKhoan();
            loadDataTable(listTK);
        });

        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);
        
    }
    
    // Hàm load DataTable
    private void loadDataTable(List<TaiKhoan> listTK ) {
       List<Object[]> data = new ArrayList<>();
        for(TaiKhoan tk : listTK){
            String trangThaiText = "";
            int tt = tk.getTrangthai();
            if (tt == 1) {
                trangThaiText = "Hoạt động";
            } else if (tt == 0) {
                trangThaiText = "Ngưng hoạt động";
            }
            data.add(new Object[] {
                      "TK-"+tk.getMatk(),
                tk.getTendangnhap(),
                tk.getMatkhau(),
                tk.getMaphanquyen() == 1 ? "Quản lý " : "Học sinh",
                trangThaiText
            });
  
        }
         
        paginatedTable.setData(data);
    }
    private void Search(){
        String keyword = this.search.txtSearchForm.getText().trim();
        String searchType = (String) this.search.cbxChoose.getSelectedItem(); // Trả về value luôn
        listTK = this.tkBUS.searchTaiKhoan(keyword, searchType);
        loadDataTable(listTK);
    }
private TaiKhoan getSelectedTaiKhoan() {
        int row = tableTaiKhoan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
       int id = Integer.parseInt(tableTaiKhoan.getValueAt(row, 0).toString().replace("TK-", ""));
        for (TaiKhoan ts : listTK) {
            if (ts.getMatk()== id) return ts;
        }
        return null;
    }
    // Event Nhấn nút ToolBar
    @Override
    public void actionPerformed(ActionEvent e) { 
  JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this); // Lấy Frame cha
        Object source = e.getSource();
        
        if(source == mainFunction.btn.get("create") ){
            new TaiKhoanDialog(this,tkBUS ,owner, "THÊM TÀI KHOẢN", "create",true, () -> {
                tkBUS = new TaiKhoanBUS();
                listTK=tkBUS.getAllTaiKhoan();
                   loadDataTable(listTK);
       }, null
            );
            
        }
        if(source == mainFunction.btn.get("update") ){
            TaiKhoan tk = getSelectedTaiKhoan();
            new TaiKhoanDialog(this,tkBUS ,owner, "SỬA TÀI KHOẢN", "update",true, () -> {
               tkBUS = new TaiKhoanBUS();
                listTK=tkBUS.getAllTaiKhoan();
                   loadDataTable(listTK);
       },tk
            );}
            if(source == mainFunction.btn.get("detail") ){
            TaiKhoan tk = getSelectedTaiKhoan();
            new TaiKhoanDialog(this,tkBUS ,owner, "CHI TIẾT TÀI KHOẢN", "detail",true, () -> {
              tkBUS = new TaiKhoanBUS();
                listTK=tkBUS.getAllTaiKhoan();
                   loadDataTable(listTK);
       },tk
            );
            
        }
            else if(source == mainFunction.btn.get("delete") ){
             TaiKhoan tk = getSelectedTaiKhoan();

               int confirm = JOptionPane.showConfirmDialog(
    this,
    "Bạn có chắc chắn muốn xóa tài khoản này không?",
    "Xác nhận xóa",
    JOptionPane.YES_NO_OPTION
);
    
            if (confirm == JOptionPane.YES_OPTION) {
                        if (tkBUS.deleteTaiKhoan(tk.getMatk())) {
                            JOptionPane.showMessageDialog(this, "Xóa thành công!");
                            tkBUS = new TaiKhoanBUS();
                listTK=tkBUS.getAllTaiKhoan();
                            loadDataTable(listTK);
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            
    }
            else if(source == mainFunction.btn.get("import")){
            importExcel();
            }
    }
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

                    List<TaiKhoan> listImport =
                            ExcelImportUtil.readTaiKhoanFromExcel(
                                    selectedFile.getAbsolutePath()
                            );

                    if (listImport.isEmpty()) {

                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    TaiKhoanPanel.this,
                                    "Không có dữ liệu hợp lệ!",
                                    "Thông báo",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        });

                        return null;
                    }

                    int total = listImport.size();
                    int current = 0;

                    for (TaiKhoan ts : listImport) {

                        current++;

                        // ===== VALIDATE =====

                        String errorMsg = validateInput(ts);

                        if (errorMsg != null) {

                            errors.append("Dòng ")
                                    .append(current + 1)
                                    .append(" - Tên đăng nhập: ")
                                    .append(ts.getTendangnhap())
                                    .append(" -> ")
                                    .append(errorMsg)
                                    .append("\n");

                            skipped++;

                        } else {

                            try {

                                if (tkBUS.addTaiKhoan(ts.getTendangnhap(),ts.getMatkhau(),ts.getMaphanquyen(),1)) {
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
                                TaiKhoanPanel.this,
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

                listTK = tkBUS.getAllTaiKhoan();

                loadDataTable(listTK);

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
                            TaiKhoanPanel.this,
                            panel,
                            "Kết quả Import",
                            JOptionPane.WARNING_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            TaiKhoanPanel.this,
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
    private String validateInput(TaiKhoan tk) {

        // ===== CCCD =====
       
        if (Validation.isEmpty(tk.getTendangnhap())) {
            return "Tên đăng nhập không được để trống!";
        }
       
        if (!tkBUS.checktdn(tk.getTendangnhap(),0)) {
            return "Tên đăng nhập đã tồn tại trong hệ thống";
        }
        // ===== Password =====


        if (Validation.isEmpty(tk.getMatkhau())) {
            return "Mật khẩu không được để trống!";
        }
        if (tk.getMatkhau().length() < 5) {
            return "Mật khẩu phải >= 5 ký tự!";
        }
      return null;
    }
   
    // Event nhấn thay đổi kiểu Search
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Search();
        }
    }
}
