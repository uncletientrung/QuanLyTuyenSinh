
package GUI.Panel;

import BUS.XtNganhBUS;
import ENTITY.XtNganh;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
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

    private PanelBorderRadius pnlMain, functionBar;
    private JPanel contentCenter;
    private JTable tableNganh;
    private JScrollPane scrollTableNganh;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;

    private final Color BackgroundColor = new Color(240, 247, 250);

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
    scrollTableNganh = new JScrollPane(tableNganh);

    tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Đặt tiêu đề cột
    String[] header = {"ID", "Mã Ngành", "Tên Ngành", "Tổ Hợp Gốc",
                       "Chỉ Tiêu", "Điểm Sàn", "Điểm TT", "Tuyển Thẳng",
                       "DGNL", "THPT", "VSAT", "SL XTT", "SL DGNL", "SL VSAT", "SL THPT"};

    tblModel.setColumnIdentifiers(header);
    tableNganh.setModel(tblModel);

    // Thiết kế giao diện bảng
    tableNganh.setFocusable(false);
    tableNganh.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    tableNganh.getTableHeader().setPreferredSize(new Dimension(0, 40));
    tableNganh.setRowHeight(35);
    tableNganh.setAutoCreateRowSorter(true);

    // Căn giữa nội dung các cột
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int i = 0; i < tableNganh.getColumnCount(); i++) {
        tableNganh.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    //do rong cot
    setColumnWidths();


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

    // layout
    contentCenter = new JPanel(new BorderLayout(10, 10));
    contentCenter.setBackground(BackgroundColor);

    pnlMain = new PanelBorderRadius();
    pnlMain.setLayout(new BorderLayout());
    pnlMain.setBackground(Color.WHITE);
    pnlMain.add(scrollTableNganh, BorderLayout.CENTER);

    contentCenter.add(functionBar, BorderLayout.NORTH);
    contentCenter.add(pnlMain, BorderLayout.CENTER);

    this.add(createSpacer(10), BorderLayout.NORTH);
    this.add(createSpacer(10), BorderLayout.SOUTH);
    this.add(createSpacer(10), BorderLayout.EAST);
    this.add(createSpacer(10), BorderLayout.WEST);
    this.add(contentCenter, BorderLayout.CENTER);
}

// tuy chinh do rong cot
private void setColumnWidths() {
    // Cột ID
    tableNganh.getColumnModel().getColumn(0).setPreferredWidth(60);  
    tableNganh.getColumnModel().getColumn(0).setMaxWidth(80);

    // Cột Mã Ngành
    tableNganh.getColumnModel().getColumn(1).setPreferredWidth(100);

    // Cột Tên Ngành 
    tableNganh.getColumnModel().getColumn(2).setPreferredWidth(350); 

    // Tổ hợp gốc
    tableNganh.getColumnModel().getColumn(3).setPreferredWidth(90);

    // Chỉ tiêu
    tableNganh.getColumnModel().getColumn(4).setPreferredWidth(80);

    // Điểm sàn & Điểm TT
    tableNganh.getColumnModel().getColumn(5).setPreferredWidth(90);
    tableNganh.getColumnModel().getColumn(6).setPreferredWidth(90);

    // Tuyển thẳng, DGNL, THPT, VSAT
    tableNganh.getColumnModel().getColumn(7).setPreferredWidth(85);
    tableNganh.getColumnModel().getColumn(8).setPreferredWidth(70);
    tableNganh.getColumnModel().getColumn(9).setPreferredWidth(70);
    tableNganh.getColumnModel().getColumn(10).setPreferredWidth(70);

    // Các cột số lượng 
    tableNganh.getColumnModel().getColumn(11).setPreferredWidth(65);  // SL XTT
    tableNganh.getColumnModel().getColumn(12).setPreferredWidth(65);  // SL DGNL
    tableNganh.getColumnModel().getColumn(13).setPreferredWidth(65);  // SL VSAT
    tableNganh.getColumnModel().getColumn(14).setPreferredWidth(75);  // SL THPT
}

    // Tạo khoảng cách viền
    private JPanel createSpacer(int size) {
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, size));
        spacer.setBackground(BackgroundColor);
        return spacer;
    }

    // load du lieu lên bảng
    private void loadDataTable(List<XtNganh> list) {
        tblModel.setRowCount(0);  

        for (XtNganh ng : list) {
            tblModel.addRow(new Object[]{
                "NG-"+ng.getIdnganh(),
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

    // tim kiem
    private void timKiemTheoTuKhoa() {
        String keyword = search.txtSearchForm.getText().trim().toLowerCase();
        String loaiTimKiem = (String) search.cbxChoose.getSelectedItem();

        if (keyword.isEmpty()) {
            listNganh = nganhBUS.getAllNganh();
        } else {
            listNganh = nganhBUS.getAllNganh().stream()
                    .filter(ng -> {
                        switch (loaiTimKiem) {
                            case "Mã":
                                return ng.getManganh() != null && ng.getManganh().toLowerCase().contains(keyword);
                            case "Tên ngành":
                                return ng.getTennganh() != null && ng.getTennganh().toLowerCase().contains(keyword);
                            case "Tổ hợp gốc":
                                return ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(keyword);
                            default: // Tất cả
                                return (ng.getManganh() != null && ng.getManganh().toLowerCase().contains(keyword)) ||
                                       (ng.getTennganh() != null && ng.getTennganh().toLowerCase().contains(keyword)) ||
                                       (ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(keyword));
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

    // các nut thêm sủa xoa chua lam
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == mainFunction.btn.get("create")) {
            JOptionPane.showMessageDialog(this, "chua lam", 
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (source == mainFunction.btn.get("update")) {
            JOptionPane.showMessageDialog(this, "chua lam", 
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (source == mainFunction.btn.get("delete")) {
            JOptionPane.showMessageDialog(this, "chua lam", 
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            timKiemTheoTuKhoa();
        }
    }
}