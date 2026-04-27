package GUI.Panel;

import BUS.XtBangQuyDoiBUS;
import ENTITY.XtBangQuyDoi;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PaginatedTable;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.XtBangQuyDoiDialog;
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

/**
 *
 * @author Windows
 */
public class XtBangQuyDoiPanel extends JPanel implements ActionListener, ItemListener {

    private PanelBorderRadius pnlMain, functionBar;
    private Main mainFrame;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private XtBangQuyDoiBUS qdBUS;
    private List<XtBangQuyDoi> listQD;
    private Color BackgroundColor = new Color(240, 247, 250);

    public XtBangQuyDoiPanel(Main mainF) {
        this.mainFrame = mainF;
        qdBUS = new XtBangQuyDoiBUS();
        listQD = qdBUS.getAllQuyDoi();
        initComponent();
        loadDataTable(listQD);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        String[] header = new String[]{
            "ID", "Phương thức", "Tổ hợp", "Môn", "Điểm A", "Điểm B", "Điểm C", "Điểm D", "Mã quy đổi", "Phân vị"
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
        comps[0] = TableSorter.INTEGER_COMPARATOR;     // ID
        comps[1] = TableSorter.STRING_COMPARATOR;      // Phương thức
        comps[2] = TableSorter.STRING_COMPARATOR;      // Tổ hợp
        comps[3] = TableSorter.STRING_COMPARATOR;      // Môn
        comps[4] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm A
        comps[5] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm B
        comps[6] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm C
        comps[7] = TableSorter.BIG_DECIMAL_COMPARATOR; // Điểm D
        comps[8] = TableSorter.STRING_COMPARATOR;      // Mã
        comps[9] = TableSorter.STRING_COMPARATOR;      // Phân vị
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
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(1, "bangQuyDoi", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        // Thanh tìm kiếm
        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "Phương thức", "Tổ hợp", "Môn"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadDataTable(qdBUS.getAllQuyDoi());
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

    public void loadDataTable(List<XtBangQuyDoi> list) {
        this.listQD = list;
        java.util.List<Object[]> data = new java.util.ArrayList<>();

        for (XtBangQuyDoi qd : list) {
            data.add(new Object[]{
                qd.getIdqd(),
                qd.getDPhuongthuc(),
                qd.getDTohop(),
                qd.getDMon(),
                qd.getDDiema(),
                qd.getDDiemb(),
                qd.getDDiemc(),
                qd.getDDiemd(),
                qd.getDMaQuyDoi(),
                qd.getDPhanvi()
            });
        }
        paginatedTable.setData(data);
    }

    private void performSearch() {
        String keyword = search.txtSearchForm.getText();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        List<XtBangQuyDoi> result = qdBUS.searchQuyDoi(searchType, keyword);
        loadDataTable(result);
    }

    private XtBangQuyDoi getSelectedQuyDoi() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        int idqd = (int) paginatedTable.getTable().getModel().getValueAt(modelRow, 0);
        for (XtBangQuyDoi qd : listQD) {
            if (qd.getIdqd() == idqd) {
                return qd;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new XtBangQuyDoiDialog(this, mainFrame, "Thêm bảng quy đổi", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            XtBangQuyDoi selected = getSelectedQuyDoi();
            if (selected != null) {
                new XtBangQuyDoiDialog(this, mainFrame, "Chỉnh sửa bảng quy đổi", true, "update", selected);
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            XtBangQuyDoi selected = getSelectedQuyDoi();
            if (selected != null) {
                new XtBangQuyDoiDialog(this, mainFrame, "Chi tiết bảng quy đổi", true, "detail", selected);
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            XtBangQuyDoi selected = getSelectedQuyDoi();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Xóa bảng quy đổi ID=" + selected.getIdqd() + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (qdBUS.deleteQuyDoi(selected.getIdqd())) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        listQD = qdBUS.getAllQuyDoi();
                        loadDataTable(listQD);
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
            performSearch();
        }
    }
}
