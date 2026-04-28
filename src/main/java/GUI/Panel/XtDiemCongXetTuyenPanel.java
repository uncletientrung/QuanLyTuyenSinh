package GUI.Panel;

import BUS.XtDiemCongXetTuyenBUS;
import ENTITY.XtDiemCongXetTuyen;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PaginatedTable;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class XtDiemCongXetTuyenPanel extends JPanel implements ActionListener, ItemListener {

    private PanelBorderRadius pnlMain, functionBar;
    private Main mainFrame;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private XtDiemCongXetTuyenBUS diemCongBUS;
    private List<XtDiemCongXetTuyen> listDiemCong;
    private Color BackgroundColor = new Color(240, 247, 250);

    public XtDiemCongXetTuyenPanel(Main mainF) {
        this.mainFrame = mainF;
        diemCongBUS = new XtDiemCongXetTuyenBUS();
        listDiemCong = diemCongBUS.getAllDiemCong();
        initComponent();
        loadDataTable(listDiemCong);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        String[] header = new String[]{
            "ID", "CCCD", "Mã ngành", "Mã tổ hợp", "Phương thức",
            "Điểm CC", "Điểm UTXT", "Điểm tổng", "Ghi chú", "DC Keys"
        };

        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        // Căn giữa nội dung Header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa nội dung các ô trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setAutoCreateRowSorter(false);
        Comparator<Object>[] comps = new Comparator[10];
        comps[0] = TableSorter.INTEGER_COMPARATOR;      // ID
        comps[1] = TableSorter.STRING_COMPARATOR;      // CCCD
        comps[2] = TableSorter.STRING_COMPARATOR;      // Mã ngành
        comps[3] = TableSorter.STRING_COMPARATOR;      // Mã tổ hợp
        comps[4] = TableSorter.STRING_COMPARATOR;      // Phương thức
        comps[5] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm CC
        comps[6] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm UTXT
        comps[7] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm tổng
        comps[8] = TableSorter.STRING_COMPARATOR;      // Ghi chú
        comps[9] = TableSorter.STRING_COMPARATOR;      // DC Keys
        paginatedTable.enableFullDataSorting(comps);

        // padding
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

        // Khu vực trung tâm
        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // Function Bar
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 1, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = {"create", "update", "delete", "detail", "import"};
        mainFunction = new MainFunction(1, "diemCong", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        // Thanh tìm kiếm
        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "CCCD", "Mã ngành", "Mã tổ hợp", "Phương thức"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadDataTable(diemCongBUS.getAllDiemCong());
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // Panel chứa bảng dữ liệu
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);
    }

    public void loadDataTable(List<XtDiemCongXetTuyen> list) {
        this.listDiemCong = list;
        List<Object[]> data = new java.util.ArrayList<>();

        for (XtDiemCongXetTuyen dc : list) {
            data.add(new Object[]{
                dc.getIdDiemCong(),
                dc.getTsCccd(),
                dc.getMaNganh(),
                dc.getMaToHop(),
                dc.getPhuongThuc(),
                dc.getDiemCC(),
                dc.getDiemUtxt(),
                dc.getDiemTong(),
                dc.getGhiChu(),
                dc.getDcKeys()
            });
        }
        paginatedTable.setData(data);
    }

    private void performSearch() {
        String keyword = search.txtSearchForm.getText();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        List<XtDiemCongXetTuyen> result = diemCongBUS.searchDiemCong(searchType, keyword);
        loadDataTable(result);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            performSearch();
        }
    }
}
