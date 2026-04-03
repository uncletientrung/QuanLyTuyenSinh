/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.TaiKhoanBUS;
import ENTITY.TaiKhoan;
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
public class TaiKhoanPanel extends JPanel implements ActionListener, ItemListener {
    PanelBorderRadius pnlMain, functionBar;
    Main mainFrame;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableTaiKhoan;
    JScrollPane scrollTableTaiKhoan;
    MainFunction mainFunction; // Thanh function
    IntegratedSearch search; // Thanh Search
    DefaultTableModel tblModel;
    
    TaiKhoanBUS tkBUS= new TaiKhoanBUS();
    List<TaiKhoan> listTK =  tkBUS.getAllTaiKhoan();
    Color BackgroundColor = new Color(240, 247, 250);
   
    public TaiKhoanPanel(Main mainF){
        this.mainFrame = mainF;
        initComponent();
        loadDataTable(listTK);
    }
    
    // Hàm khởi tạo
    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
        tableTaiKhoan = new JTable();
        scrollTableTaiKhoan = new JScrollPane();
        
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Table Header
        String[] header = new String[]{"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Nhóm quyền", "Trạng thái"};
        tblModel.setColumnIdentifiers(header);
        tableTaiKhoan.setModel(tblModel);
        tableTaiKhoan.setFocusable(false);
        tableTaiKhoan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableTaiKhoan.getTableHeader().setPreferredSize(new Dimension(0, 40));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableTaiKhoan.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scrollTableTaiKhoan.setViewportView(tableTaiKhoan);
        // Table Cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableTaiKhoan.getColumnCount(); i++) {
            tableTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Table Sorter
        tableTaiKhoan.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableTaiKhoan, 0, TableSorter.INTEGER_COMPARATOR);
        
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

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "Tài khoản", "Nhóm quyền"});
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
//                thucHienTimKiem(); Viết hàm Search
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
        pnlMain.add(scrollTableTaiKhoan, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);
        
    }
    
    // Hàm load DataTable
    private void loadDataTable(List<TaiKhoan> listTK ) {
        tblModel.setRowCount(0);
        for(TaiKhoan tk : listTK){
            String trangThaiText = "";
            int tt = tk.getTrangthai();
            if (tt == 1) {
                trangThaiText = "Hoạt động";
            } else if (tt == 0) {
                trangThaiText = "Ngưng hoạt động";
            }
            tblModel.addRow(new Object[]{
                "TK-"+tk.getMatk(),
                tk.getTendangnhap(),
                tk.getMatkhau(),
                tk.getMaphanquyen() == 1 ? "Quản lý " : "Học sinh",
                trangThaiText
            });
        }
    }

    // Event Nhấn nút ToolBar
    @Override
    public void actionPerformed(ActionEvent e) { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Event nhấn thay đổi kiểu Search
    @Override
    public void itemStateChanged(ItemEvent e) { 
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
