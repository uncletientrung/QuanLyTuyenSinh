package GUI.Panel;

import BUS.XtNganhBUS;
import ENTITY.XtNganh;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PaginatedTable;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.NganhDialog;
import GUI.Main;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class NganhPanel extends JPanel implements ActionListener, ItemListener {

    private final XtNganhBUS nganhBUS;
    private List<XtNganh> listNganh;

    private PanelBorderRadius pnlMain, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private Color BackgroundColor = new Color(240, 247, 250);

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

        // Khai báo header cho bảng
        String[] header = {
            "ID", "Mã Ngành", "Tên Ngành", "Tổ Hợp Gốc",
            "Chỉ Tiêu", "Điểm Sàn", "Điểm TT", "Tuyển Thẳng",
            "DGNL", "THPT", "VSAT", "SL XTT", "SL DGNL", "SL VSAT", "SL THPT"
        };

        paginatedTable = new PaginatedTable(header);
        JTable table = paginatedTable.getTable();

        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setRowHeight(35);

        // Căn giữa header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Tắt AutoCreateRowSorter vì PaginatedTable sẽ xử lý sorting
        table.setAutoCreateRowSorter(false);

        // Cấu hình comparator cho từng cột (rất quan trọng để sort đúng)
        Comparator<Object>[] comps = new Comparator[15];
        comps[0] = TableSorter.INTEGER_COMPARATOR;        // ID
        comps[1] = TableSorter.STRING_COMPARATOR;         // Mã Ngành
        comps[2] = TableSorter.STRING_COMPARATOR;         // Tên Ngành
        comps[3] = TableSorter.STRING_COMPARATOR;         // Tổ Hợp Gốc
        comps[4] = TableSorter.INTEGER_COMPARATOR;        // Chỉ Tiêu
        comps[5] = TableSorter.BIG_DECIMAL_COMPARATOR;    // Điểm Sàn
        comps[6] = TableSorter.BIG_DECIMAL_COMPARATOR;    // Điểm TT
        comps[7] = TableSorter.STRING_COMPARATOR;         // Tuyển Thẳng
        comps[8] = TableSorter.BIG_DECIMAL_COMPARATOR;    // DGNL
        comps[9] = TableSorter.BIG_DECIMAL_COMPARATOR;    // THPT
        comps[10] = TableSorter.BIG_DECIMAL_COMPARATOR;   // VSAT
        comps[11] = TableSorter.INTEGER_COMPARATOR;       // SL XTT
        comps[12] = TableSorter.INTEGER_COMPARATOR;       // SL DGNL
        comps[13] = TableSorter.INTEGER_COMPARATOR;       // SL VSAT
        comps[14] = TableSorter.INTEGER_COMPARATOR;       // SL THPT

        paginatedTable.enableFullDataSorting(comps);

        // === Padding ===
        pnlBorder1 = new JPanel(); pnlBorder1.setPreferredSize(new Dimension(0, 10)); pnlBorder1.setBackground(BackgroundColor);
        pnlBorder2 = new JPanel(); pnlBorder2.setPreferredSize(new Dimension(0, 10)); pnlBorder2.setBackground(BackgroundColor);
        pnlBorder3 = new JPanel(); pnlBorder3.setPreferredSize(new Dimension(10, 0)); pnlBorder3.setBackground(BackgroundColor);
        pnlBorder4 = new JPanel(); pnlBorder4.setPreferredSize(new Dimension(10, 0)); pnlBorder4.setBackground(BackgroundColor);

        this.add(pnlBorder1, BorderLayout.NORTH);
        this.add(pnlBorder2, BorderLayout.SOUTH);
        this.add(pnlBorder3, BorderLayout.EAST);
        this.add(pnlBorder4, BorderLayout.WEST);

        // === Content Center ===
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

        String[] action = {"create", "update", "delete", "detail", "import"};
        mainFunction = new MainFunction(1, "nganh", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "Tên ngành", "Tổ hợp gốc"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        search.btnReset.addActionListener(e -> resetSearch());

        functionBar.add(mainFunction);
        functionBar.add(search);

        // Panel chứa bảng
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        contentCenter.add(pnlMain, BorderLayout.CENTER);

        // Đặt độ rộng cột (nếu PaginatedTable hỗ trợ)
        setColumnWidths();
    }

    private void setColumnWidths() {
        JTable table = paginatedTable.getTable();
        table.getColumnModel().getColumn(0).setPreferredWidth(70);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(110);  // Mã ngành
        table.getColumnModel().getColumn(2).setPreferredWidth(380);  // Tên ngành
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  // Tổ hợp gốc
        table.getColumnModel().getColumn(4).setPreferredWidth(80);   // Chỉ tiêu
        table.getColumnModel().getColumn(5).setPreferredWidth(90);   // Điểm sàn
        table.getColumnModel().getColumn(6).setPreferredWidth(90);   // Điểm TT
        table.getColumnModel().getColumn(7).setPreferredWidth(90);   // Tuyển thẳng
        table.getColumnModel().getColumn(8).setPreferredWidth(70);
        table.getColumnModel().getColumn(9).setPreferredWidth(70);
        table.getColumnModel().getColumn(10).setPreferredWidth(70);
        table.getColumnModel().getColumn(11).setPreferredWidth(70);  // SL XTT
        table.getColumnModel().getColumn(12).setPreferredWidth(70);
        table.getColumnModel().getColumn(13).setPreferredWidth(70);
        table.getColumnModel().getColumn(14).setPreferredWidth(80);  // SL THPT
    }

    public void loadDataTable(List<XtNganh> list) {
        this.listNganh = list;
        List<Object[]> data = new java.util.ArrayList<>();

        for (XtNganh ng : list) {
            data.add(new Object[]{
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
        paginatedTable.setData(data);
    }

    private void performSearch() {
        String keyword    = search.txtSearchForm.getText().trim();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        listNganh = nganhBUS.searchNganh(keyword, searchType);
        loadDataTable(listNganh);
    }

    private void resetSearch() {
        search.txtSearchForm.setText("");
        search.cbxChoose.setSelectedIndex(0);
        listNganh = nganhBUS.getAllNganh();
        loadDataTable(listNganh);
    }

    // Lấy ngành đang được chọn
    private XtNganh getSelectedNganh() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngành!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        String idStr = paginatedTable.getTable().getModel().getValueAt(modelRow, 0).toString();
        int idnganh = Integer.parseInt(idStr.replace("NG-", ""));

        for (XtNganh ng : listNganh) {
            if (ng.getIdnganh() == idnganh) {
                return ng;
            }
        }
        return null;
    }

    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fileChooser.getSelectedFile();
        int successCount = 0;
        java.util.List<String> errorLines = new java.util.ArrayList<>();

        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook
                = new org.apache.poi.xssf.usermodel.XSSFWorkbook(new java.io.FileInputStream(file))) {

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            // Row 0 = tiêu đề bảng, Row 1 = header cột -> dữ liệu từ row 2
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null) continue;

                int excelRow = i + 1; // số dòng hiển thị cho user (Excel đếm từ 1)
                try {
                    // Cột 0: STT - bỏ qua | Cột 1+: dữ liệu
                    String maNganh = getCellString(row.getCell(1)).trim();
                    if (maNganh.isEmpty()) continue;

                    XtNganh ng = new XtNganh();
                    ng.setManganh(maNganh);
                    ng.setTennganh(getCellString(row.getCell(2)).trim());
                    ng.setNTohopgoc(getCellString(row.getCell(3)).trim());
                    ng.setNChitieu((int) row.getCell(4).getNumericCellValue());
                    ng.setNDiemsan(getCellDecimal(row.getCell(5)));
                    ng.setNDiemtrungtuyen(getCellDecimal(row.getCell(6)));
                    ng.setNTuyenthang(parseFlag(getCellString(row.getCell(7))));
                    ng.setNDgnl(parseFlag(getCellString(row.getCell(8))));
                    ng.setNThpt(parseFlag(getCellString(row.getCell(9))));
                    ng.setNVsat(parseFlag(getCellString(row.getCell(10))));
                    ng.setSlXtt((int) row.getCell(11).getNumericCellValue());
                    ng.setSlDgnl((int) row.getCell(12).getNumericCellValue());
                    ng.setSlVsat((int) row.getCell(13).getNumericCellValue());
                    ng.setSlThpt((int) row.getCell(14).getNumericCellValue());

                    String msg = nganhBUS.insertNganh(ng);
                    if (msg.contains("thành công")) {
                        successCount++;
                    } else {
                        errorLines.add("  • Dòng " + excelRow + " [" + maNganh + "]: " + msg);
                    }

                } catch (Exception ex) {
                    errorLines.add("  • Dòng " + excelRow + ": Lỗi đọc dữ liệu - " + ex.getMessage());
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi đọc file Excel: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hiển thị kết quả
        StringBuilder sb = new StringBuilder();
        sb.append("Import hoàn tất!\n");
        sb.append("Thành công: ").append(successCount).append(" dòng\n");
        sb.append("Bỏ qua: ").append(errorLines.size()).append(" dòng");

        if (!errorLines.isEmpty()) {
            sb.append("\n\nChi tiết các dòng bỏ qua:\n");
            for (String err : errorLines) {
                sb.append(err).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, sb.toString(),
                "Kết quả Import",
                errorLines.isEmpty() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

        listNganh = nganhBUS.getAllNganh();
        loadDataTable(listNganh);
    }

    // "Có" -> "1", "Không" -> "0"
    private String parseFlag(String value) {
        if (value == null) return "0";
        return value.trim().equalsIgnoreCase("Có") ? "1" : "0";
    }

    private String getCellString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue();
            case NUMERIC: double d = cell.getNumericCellValue();
                          return (d == Math.floor(d)) ? String.valueOf((long) d) : String.valueOf(d);
            default:      return "";
        }
    }

    private java.math.BigDecimal getCellDecimal(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC: return java.math.BigDecimal.valueOf(cell.getNumericCellValue());
                case STRING:  String s = cell.getStringCellValue().trim();
                              return s.isEmpty() ? null : new java.math.BigDecimal(s);
                default:      return null;
            }
        } catch (Exception e) { return null; }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object source = e.getSource();

        if (source == mainFunction.btn.get("create")) {
            new NganhDialog(this, owner, "Thêm ngành mới", true, "create", null);
        }
        else if (source == mainFunction.btn.get("import")) {
            importExcel();
        }
        else if (source == mainFunction.btn.get("update") ||
                 source == mainFunction.btn.get("delete") ||
                 source == mainFunction.btn.get("detail")) {

            XtNganh selected = getSelectedNganh();
            if (selected == null) return;

            if (source == mainFunction.btn.get("update")) {
                new NganhDialog(this, owner, "Chỉnh sửa ngành", true, "update", selected);
            }
            else if (source == mainFunction.btn.get("detail")) {
                new NganhDialog(this, owner, "Thông tin chi tiết ngành", true, "view", selected);
            }
            else if (source == mainFunction.btn.get("delete")) {
                if (JOptionPane.showConfirmDialog(this,
                        "Xóa ngành " + selected.getTennganh() + " ?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

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
            performSearch();
        }
    }
}