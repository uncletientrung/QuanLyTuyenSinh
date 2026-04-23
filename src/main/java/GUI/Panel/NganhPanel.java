package GUI.Panel;

import BUS.XtNganhBUS;
import ENTITY.XtNganh;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.NganhDialog;
import GUI.Main;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NganhPanel extends JPanel implements ActionListener, ItemListener {

    private final XtNganhBUS nganhBUS;
    private List<XtNganh> listNganh;

    
    PanelBorderRadius pnlMain, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableNganh;
    JScrollPane scrollTableNganh;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;

    Color BackgroundColor = new Color(240, 247, 250);

    public NganhPanel(Main mainF) {
        this.nganhBUS = new XtNganhBUS();
        this.listNganh = nganhBUS.getAllNganh();

        initComponent();
        loadDataTable(listNganh);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        
        tableNganh = new JTable();
        scrollTableNganh = new JScrollPane();

        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] header = {"ID", "Mã Ngành", "Tên Ngành", "Tổ Hợp Gốc",
                           "Chỉ Tiêu", "Điểm Sàn", "Điểm TT", "Tuyển Thẳng",
                           "DGNL", "THPT", "VSAT", "SL XTT", "SL DGNL", "SL VSAT", "SL THPT"};

        tblModel.setColumnIdentifiers(header);
        tableNganh.setModel(tblModel);

        tableNganh.setFocusable(false);
        tableNganh.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableNganh.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableNganh.setRowHeight(35);
        tableNganh.setAutoCreateRowSorter(true);

        // Căn giữa tất cả cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableNganh.getColumnCount(); i++) {
            tableNganh.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollTableNganh.setViewportView(tableNganh);

        
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
        mainFunction = new MainFunction(1, "nganh", action);

        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "Tên ngành", "Tổ hợp gốc"});
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemTheoTuKhoa();
            }
        });
        search.cbxChoose.addItemListener(this);
        search.btnReset.addActionListener(e -> resetSearch());

        functionBar.add(mainFunction);
        functionBar.add(search);

        
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(scrollTableNganh, BorderLayout.CENTER);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        contentCenter.add(pnlMain, BorderLayout.CENTER);

        // Đặt độ rộng cột (giữ nguyên như bạn đã chỉnh)
        setColumnWidths();
    }

    // do rong cot
    private void setColumnWidths() {
        tableNganh.getColumnModel().getColumn(0).setPreferredWidth(70);   // ID
        tableNganh.getColumnModel().getColumn(1).setPreferredWidth(110);  // Mã ngành
        tableNganh.getColumnModel().getColumn(2).setPreferredWidth(380);  // Tên ngành 
        tableNganh.getColumnModel().getColumn(3).setPreferredWidth(100);  // Tổ hợp gốc
        tableNganh.getColumnModel().getColumn(4).setPreferredWidth(80);   // Chỉ tiêu
        tableNganh.getColumnModel().getColumn(5).setPreferredWidth(90);   // Điểm sàn
        tableNganh.getColumnModel().getColumn(6).setPreferredWidth(90);   // Điểm TT
        tableNganh.getColumnModel().getColumn(7).setPreferredWidth(90);   // Tuyển thẳng
        tableNganh.getColumnModel().getColumn(8).setPreferredWidth(70);
        tableNganh.getColumnModel().getColumn(9).setPreferredWidth(70);
        tableNganh.getColumnModel().getColumn(10).setPreferredWidth(70);
        tableNganh.getColumnModel().getColumn(11).setPreferredWidth(70);  // SL XTT
        tableNganh.getColumnModel().getColumn(12).setPreferredWidth(70);
        tableNganh.getColumnModel().getColumn(13).setPreferredWidth(70);
        tableNganh.getColumnModel().getColumn(14).setPreferredWidth(80);  // SL THPT
    }

    // load dl
    public void loadDataTable(List<XtNganh> list) {
        tblModel.setRowCount(0);
        for (XtNganh ng : list) {
            tblModel.addRow(new Object[]{
                "NG-" + ng.getIdnganh(),
                ng.getManganh(),
                ng.getTennganh(),
                ng.getNTohopgoc(),
                ng.getNChitieu(),
                ng.getNDiemsan(),
                ng.getNDiemtrungtuyen(),
                ng.getNTuyenthang(),
                ng.getNDgnl(),
                ng.getNThpt(),
                ng.getNVsat(),
                ng.getSlXtt(),
                ng.getSlDgnl(),
                ng.getSlVsat(),
                ng.getSlThpt()
            });
        }
    }

    // tim kuem
    private void timKiemTheoTuKhoa() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String loaiTimKiem = (String) search.cbxChoose.getSelectedItem();

        if (keyword.isEmpty()) {
            listNganh = nganhBUS.getAllNganh();
        } else {
            listNganh = nganhBUS.getAllNganh().stream()
                    .filter(ng -> {
                        switch (loaiTimKiem) {
                            case "Mã" -> { return ng.getManganh() != null && ng.getManganh().toLowerCase().contains(keyword); }
                            case "Tên ngành" -> { return ng.getTennganh() != null && ng.getTennganh().toLowerCase().contains(keyword); }
                            case "Tổ hợp gốc" -> { return ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(keyword); }
                            default -> { // Tất cả
                                return (ng.getManganh() != null && ng.getManganh().toLowerCase().contains(keyword)) ||
                                       (ng.getTennganh() != null && ng.getTennganh().toLowerCase().contains(keyword)) ||
                                       (ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(keyword));
                            }
                        }
                    })
                    .toList();
        }
        loadDataTable(listNganh);
    }

    private void resetSearch() {
        search.txtSearchForm.setText("");
        search.cbxChoose.setSelectedIndex(0);
        listNganh = nganhBUS.getAllNganh();
        loadDataTable(listNganh);
    }

    // cac chuc nang crud chua lam 
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object source = e.getSource();
        if (source == mainFunction.btn.get("create")) {
            new NganhDialog(this, owner, "Thêm ngành mới", true, "create", null);
        } 
        else if (source == mainFunction.btn.get("update") ||
                 source == mainFunction.btn.get("delete") || 
                 source == mainFunction.btn.get("detail")) {
            // lay doi tuong dang duoc chon trong bangr ra
            
            int index = tableNganh.getSelectedRow();
            if(index == -1){
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngành để thao tác!");
                return;
            }
            String maStr = tableNganh.getValueAt(index, 0).toString();
            int manganh = Integer.parseInt(maStr.replace("NG-", ""));
            XtNganh selected = nganhBUS.getNganhById(manganh);
            if (source == mainFunction.btn.get("update")) {
                new NganhDialog(this, owner, "Chỉnh sửa ngành", true, "update", selected);
            }else if (source == mainFunction.btn.get("delete")) {
                if (JOptionPane.showConfirmDialog(
                        this,
                        "Xóa ngành " + selected.getTennganh() + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                ) == JOptionPane.YES_OPTION) {

                    String message = nganhBUS.deleteNganh(selected.getIdnganh());
                    JOptionPane.showMessageDialog(this, message);
                    if (message.contains("thành công")) {
                        listNganh = nganhBUS.getAllNganh();
                        loadDataTable(listNganh);
                    }
                }
            }
        } 
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            timKiemTheoTuKhoa();
        }
    }
}