package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Dialog.NganhDialog;
import com.quanlytuyensinh.GUI.Dialog.NganhToHopDialog;
import com.quanlytuyensinh.GUI.Main;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class NganhToHopPanel extends JPanel implements ActionListener, ItemListener {

    private final XtNganhToHopBUS nganhToHopBUS;
    private final XtNganhBUS nganhBUS;
    private List<XtNganhToHop> listNganhToHop;

    private PanelBorderRadius pnlMain, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private Color BackgroundColor = new Color(240, 247, 250);

    public NganhToHopPanel(Main mainF) {
        this.nganhToHopBUS = new XtNganhToHopBUS();
        this.nganhBUS = new XtNganhBUS();
        initComponent();                                    
        this.listNganhToHop = nganhToHopBUS.getAll();    
        loadDataTable(listNganhToHop);                     
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // Khai báo header cho bảng
        String[] header = {
            "ID", "Mã Ngành", "Mã Tổ Hợp",
            "Môn 1", "HS 1", "Môn 2", "HS 2", "Môn 3", "HS 3",
            "N1", "TO", "LI", "HO", "SI", "VA", "SU", "DI", "TI", "GDCD", "KTPL", "CNCN", "CNNN", "NK",
            "Độ Lệch"
        };

        paginatedTable = new PaginatedTable(header);
        JTable table = paginatedTable.getTable();

        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setRowHeight(35);

        // can giua header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // can giua nd
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        table.setAutoCreateRowSorter(false);

 
        Comparator<Object>[] comps = new Comparator[24];

        comps[0]  = TableSorter.INTEGER_COMPARATOR;
        comps[1]  = TableSorter.STRING_COMPARATOR;
        comps[2]  = TableSorter.STRING_COMPARATOR;
        comps[3]  = TableSorter.STRING_COMPARATOR;
        comps[4]  = TableSorter.INTEGER_COMPARATOR;
        comps[5]  = TableSorter.STRING_COMPARATOR;
        comps[6]  = TableSorter.INTEGER_COMPARATOR;
        comps[7]  = TableSorter.STRING_COMPARATOR;
        comps[8]  = TableSorter.INTEGER_COMPARATOR;

        comps[9]  = TableSorter.STRING_COMPARATOR; // N1
        comps[10] = TableSorter.STRING_COMPARATOR; // TO
        comps[11] = TableSorter.STRING_COMPARATOR; // LI
        comps[12] = TableSorter.STRING_COMPARATOR; // HO
        comps[13] = TableSorter.STRING_COMPARATOR; // SI
        comps[14] = TableSorter.STRING_COMPARATOR; // VA
        comps[15] = TableSorter.STRING_COMPARATOR; // SU
        comps[16] = TableSorter.STRING_COMPARATOR; // DI
        comps[17] = TableSorter.STRING_COMPARATOR; // TI

        comps[18] = TableSorter.STRING_COMPARATOR; // GDCD
        comps[19] = TableSorter.STRING_COMPARATOR; // KTPL
        comps[20] = TableSorter.STRING_COMPARATOR; // CNCN
        comps[21] = TableSorter.STRING_COMPARATOR; // CNNN
        comps[22] = TableSorter.STRING_COMPARATOR; // NK

        comps[23] = TableSorter.BIG_DECIMAL_COMPARATOR; // Độ lệch

        paginatedTable.enableFullDataSorting(comps);

 
        pnlBorder1 = new JPanel(); pnlBorder1.setPreferredSize(new Dimension(0, 10)); pnlBorder1.setBackground(BackgroundColor);
        pnlBorder2 = new JPanel(); pnlBorder2.setPreferredSize(new Dimension(0, 10)); pnlBorder2.setBackground(BackgroundColor);
        pnlBorder3 = new JPanel(); pnlBorder3.setPreferredSize(new Dimension(10, 0)); pnlBorder3.setBackground(BackgroundColor);
        pnlBorder4 = new JPanel(); pnlBorder4.setPreferredSize(new Dimension(10, 0)); pnlBorder4.setBackground(BackgroundColor);

        this.add(pnlBorder1, BorderLayout.NORTH);
        this.add(pnlBorder2, BorderLayout.SOUTH);
        this.add(pnlBorder3, BorderLayout.EAST);
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

        String[] action = {"create", "update", "delete", "detail", "import"};
        mainFunction = new MainFunction(1, "nganhtohop", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã ngành", "Mã tổ hợp", "Môn 1", "Môn 2", "Môn 3"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e)  { performSearch(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        search.btnReset.addActionListener(e -> resetSearch());

        functionBar.add(mainFunction);
        functionBar.add(search);

        // panel bang
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        contentCenter.add(pnlMain, BorderLayout.CENTER);

        setColumnWidths();
    }

    private void setColumnWidths() {
        JTable table = paginatedTable.getTable();
        table.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(120);  // Mã Ngành
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Mã Tổ Hợp
        table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Môn 1
        table.getColumnModel().getColumn(4).setPreferredWidth(55);   // HS 1
        table.getColumnModel().getColumn(5).setPreferredWidth(80);   // Môn 2
        table.getColumnModel().getColumn(6).setPreferredWidth(55);   // HS 2
        table.getColumnModel().getColumn(7).setPreferredWidth(80);   // Môn 3
        table.getColumnModel().getColumn(8).setPreferredWidth(55);
        // HS 3
        for (int i = 9; i <= 22; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(45);//cac mon
        } 
        
        table.getColumnModel().getColumn(21).setPreferredWidth(80);  // Độ Lệch
    }

 

    public void loadDataTable(List<XtNganhToHop> list) {
        this.listNganhToHop = list;
        paginatedTable.setData(buildRows(list));
    }

    private Object[] buildRow(XtNganhToHop nth) {
        // NK = true nếu bất kỳ NK1-NK6 nào là true
        boolean hasNk = Boolean.TRUE.equals(nth.getNk1())
                     || Boolean.TRUE.equals(nth.getNk2())
                     || Boolean.TRUE.equals(nth.getNk3())
                     || Boolean.TRUE.equals(nth.getNk4())
                     || Boolean.TRUE.equals(nth.getNk5())
                     || Boolean.TRUE.equals(nth.getNk6());

        return new Object[]{
            "NTH-" + nth.getId(),
            nth.getManganh(),
            nth.getMatohop(),
            nth.getThMon1(),
            nth.getHsMon1(),
            nth.getThMon2(),
            nth.getHsMon2(),
            nth.getThMon3(),
            nth.getHsMon3(),
            boolToText(nth.getN1()),
            boolToText(nth.getTo()),
            boolToText(nth.getLi()),
            boolToText(nth.getHo()),
            boolToText(nth.getSi()),
            boolToText(nth.getVa()),
            boolToText(nth.getSu()),
            boolToText(nth.getDi()),
            boolToText(nth.getTi()),
            boolToText(nth.getGdcd()),
            boolToText(nth.getKtpl()),
            boolToText(nth.getCncn()),
            boolToText(nth.getCnnn()),
            boolToText(hasNk),
            nth.getDolech()
        };
    }

    private List<Object[]> buildRows(List<XtNganhToHop> list) {
        List<Object[]> rows = new java.util.ArrayList<>();
        for (XtNganhToHop nth : list) rows.add(buildRow(nth));
        return rows;
    }

    private String boolToText(Boolean value) {
        if (value == null) return "";
        //return value ? "Có" : "Không";
        //return value ? "✔" : "✘";
        return value != null && value ? "✔" : "";
    }



    private void performSearch() {
        String keyword    = search.txtSearchForm.getText().trim();
        String searchType = (String) search.cbxChoose.getSelectedItem();
        listNganhToHop = nganhToHopBUS.searchNTH(keyword, searchType);
        loadDataTable(listNganhToHop);    
    }

    private void resetSearch() {
        search.txtSearchForm.setText("");
        search.cbxChoose.setSelectedIndex(0);
        listNganhToHop = nganhToHopBUS.getAll();
        loadDataTable(listNganhToHop);
    }

    private String nvl(String s) { return s == null ? "" : s; }



    private XtNganhToHop getSelectedRecord() {
        int row = paginatedTable.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        String idStr  = paginatedTable.getTable().getModel().getValueAt(modelRow, 0).toString();
        int id = Integer.parseInt(idStr.replace("NTH-", ""));
        for (XtNganhToHop nth : listNganhToHop) {
            if (nth.getId() == id) return nth;
        }
        return null;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        Object source = e.getSource();

        if (source == mainFunction.btn.get("create")) {
             new NganhToHopDialog(this, owner, "Thêm ngành tổ hợp mới", true, "create", null);
        }
        else if (source == mainFunction.btn.get("import")) {
            importExcel();
        }
        else if (source == mainFunction.btn.get("update") ||
                 source == mainFunction.btn.get("delete") ||
                 source == mainFunction.btn.get("detail")) {

            XtNganhToHop selected = getSelectedRecord();
            if (selected == null) return;

            if (source == mainFunction.btn.get("update")) {
                new NganhToHopDialog(this, owner, "Chỉnh sửa ngành tổ hợp", true, "update", selected);
            }
            else if (source == mainFunction.btn.get("detail")) {
                new NganhToHopDialog(this, owner, "Thông tin chi tiết ngành", true, "view", selected);
            }
            else if (source == mainFunction.btn.get("delete")) {
                if(selected != null){
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "Xóa ngành tổ hợp: " + selected.getTbKeys()+ "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (nganhToHopBUS.deleteNTH(selected.getId())) {
                            JOptionPane.showMessageDialog(this, "Xóa thành công!");
                            listNganhToHop = nganhToHopBUS.getAll();
                            loadDataTable(listNganhToHop);
                        } else {
                            JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
          
                }
            }
        }
    }
    
 

    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fileChooser.getSelectedFile();
        int successCount = 0;
        int updateGocCount = 0;
        java.util.List<String> errorLines = new java.util.ArrayList<>();

        // Cache tb_keys đã tồn tại để kiểm tra trùng
        java.util.Set<String> existingKeys = new java.util.HashSet<>();
        for (XtNganhToHop nth : nganhToHopBUS.getAll()) {
            if (nth.getTbKeys() != null) {
                existingKeys.add(nth.getTbKeys().trim().toLowerCase());
            }
        }

        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook
                = new org.apache.poi.xssf.usermodel.XSSFWorkbook(new java.io.FileInputStream(file))) {

            // Cần FormulaEvaluator để đọc giá trị ô công thức (tb_keys = B2&"_"&F2)
            org.apache.poi.ss.usermodel.FormulaEvaluator evaluator =
                    workbook.getCreationHelper().createFormulaEvaluator();

            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            // Dòng 0 = header → dữ liệu bắt đầu từ dòng 1 (index 1)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null) continue;

                int excelRow = i + 1; // số dòng hiển thị theo Excel (1-based)
                try {
                    // Cột 1: MANGANH
                    String maNganh = getCellString(row.getCell(1), evaluator).trim();

                    // Cột 3: MA_TO_HOP dạng "D07(TO-3,HO-5,N1-1)"
                    String maToHopRaw = getCellString(row.getCell(3), evaluator).trim();

                    if (maNganh.isEmpty() || maToHopRaw.isEmpty()) continue;

                    // --- Tách mã tổ hợp và phần môn+hệ số ---
                    // Ví dụ: "D07(TO-3,HO-5,N1-1)"
                    //   → maToHop = "D07"
                    //   → monHsList = ["TO-3", "HO-5", "N1-1"]
                    String maToHop;
                    String[] monHsParts; // mảng "MON-HS"

                    int parenOpen = maToHopRaw.indexOf('(');
                    if (parenOpen > 0 && maToHopRaw.endsWith(")")) {
                        maToHop = maToHopRaw.substring(0, parenOpen).trim().toUpperCase();
                        String inner = maToHopRaw.substring(parenOpen + 1, maToHopRaw.length() - 1);
                        monHsParts = inner.split(",");
                    } else {
                        // Không có dấu ngoặc → chỉ lấy mã, không có môn
                        maToHop = maToHopRaw.toUpperCase();
                        monHsParts = new String[0];
                    }

                    // Parse tối đa 3 môn và hệ số
                    String mon1 = "", mon2 = "", mon3 = "";
                    int    hs1  = 1,  hs2  = 1,  hs3  = 1;

                    for (int k = 0; k < monHsParts.length && k < 3; k++) {
                        String part = monHsParts[k].trim(); // "TO-3"
                        int dash = part.lastIndexOf('-');
                        String tenMon = (dash > 0) ? part.substring(0, dash).trim().toUpperCase() : part.toUpperCase();
                        int    heSo  = 1;
                        if (dash > 0) {
                            try { heSo = Integer.parseInt(part.substring(dash + 1).trim()); }
                            catch (NumberFormatException ignored) {}
                        }
                        if      (k == 0) { mon1 = tenMon; hs1 = heSo; }
                        else if (k == 1) { mon2 = tenMon; hs2 = heSo; }
                        else             { mon3 = tenMon; hs3 = heSo; }
                    }

                    // Cột 4: tb_keys (công thức Excel → FormulaEvaluator đọc được)
                    String tbKeys = getCellString(row.getCell(4), evaluator).trim();
                    // Fallback nếu ô trống: tự ghép
                    if (tbKeys.isEmpty()) tbKeys = maNganh + "_" + maToHop;

                    // Cột 7: Độ lệch
                    java.math.BigDecimal dolech = getCellDecimal(row.getCell(7), evaluator);

                    // Cột 6: Gốc
                    String goc = getCellString(row.getCell(6), evaluator).trim();

                    // Kiểm tra trùng tb_keys
                    if (existingKeys.contains(tbKeys.toLowerCase())) {
                        errorLines.add("  • Dòng " + excelRow + " [" + tbKeys + "]: Đã tồn tại, bỏ qua.");
                        continue;
                    }

                    // Xây dựng entity
                    XtNganhToHop nth = new XtNganhToHop();
                    nth.setManganh(maNganh);
                    nth.setMatohop(maToHop);
                    nth.setTbKeys(tbKeys);
                    nth.setThMon1(mon1.isEmpty() ? null : mon1);
                    nth.setHsMon1(hs1);
                    nth.setThMon2(mon2.isEmpty() ? null : mon2);
                    nth.setHsMon2(hs2);
                    nth.setThMon3(mon3.isEmpty() ? null : mon3);
                    nth.setHsMon3(hs3);
                    nth.setDolech(dolech);

                    // Set boolean: môn nào xuất hiện → true, các môn còn lại → false
                    resetBooleanFields(nth);
                    if (!mon1.isEmpty()) setSubjectBoolean(nth, mon1);
                    if (!mon2.isEmpty()) setSubjectBoolean(nth, mon2);
                    if (!mon3.isEmpty()) setSubjectBoolean(nth, mon3);

                    if (nganhToHopBUS.addNTH(nth)) {
                        successCount++;
                        existingKeys.add(tbKeys.toLowerCase());

                        // Nếu cột Gốc = "Gốc" → update tổ hợp gốc bên bảng ngành
                        if ("Gốc".equalsIgnoreCase(goc)) {
                            XtNganh nganh = nganhBUS.getByMaNganh(maNganh);
                            if (nganh != null) {
                                nganh.setNTohopgoc(maToHop);
                                if (nganhBUS.updateNganh(nganh)) {
                                    updateGocCount++;
                                } else {
                                    errorLines.add("  • Dòng " + excelRow + " [" + maNganh + "]: Cập nhật tổ hợp gốc thất bại.");
                                }
                            }
                            // Nếu không tìm thấy ngành thì bỏ qua (không báo lỗi)
                        }
                    } else {
                        errorLines.add("  • Dòng " + excelRow + " [" + tbKeys + "]: Thêm vào DB thất bại.");
                    }

                } catch (Exception ex) {
                    errorLines.add("  • Dòng " + excelRow + ": Lỗi xử lý - " + ex.getMessage());
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
        if (updateGocCount > 0) {
            sb.append("Cập nhật tổ hợp gốc ngành: ").append(updateGocCount).append(" ngành\n");
        }
        sb.append("Bỏ qua/Lỗi: ").append(errorLines.size()).append(" dòng");
        if (!errorLines.isEmpty()) {
            sb.append("\n\nChi tiết:\n");
            errorLines.forEach(e -> sb.append(e).append("\n"));
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Kết quả Import",
                errorLines.isEmpty() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);

        listNganhToHop = nganhToHopBUS.getAll();
        loadDataTable(listNganhToHop);
    }



    private String getCellString(org.apache.poi.ss.usermodel.Cell cell,
                                  org.apache.poi.ss.usermodel.FormulaEvaluator evaluator) {
        if (cell == null) return "";
        org.apache.poi.ss.usermodel.CellValue cv = evaluator.evaluate(cell);
        if (cv == null) return "";
        switch (cv.getCellType()) {
            case STRING:  return cv.getStringValue();
            case NUMERIC: double d = cv.getNumberValue();
                          return (d == Math.floor(d)) ? String.valueOf((long) d) : String.valueOf(d);
            case BOOLEAN: return String.valueOf(cv.getBooleanValue());
            default:      return "";
        }
    }

    private java.math.BigDecimal getCellDecimal(org.apache.poi.ss.usermodel.Cell cell,
                                                 org.apache.poi.ss.usermodel.FormulaEvaluator evaluator) {
        if (cell == null) return java.math.BigDecimal.ZERO;
        try {
            org.apache.poi.ss.usermodel.CellValue cv = evaluator.evaluate(cell);
            if (cv == null) return java.math.BigDecimal.ZERO;
            switch (cv.getCellType()) {
                case NUMERIC: return java.math.BigDecimal.valueOf(cv.getNumberValue());
                case STRING:  String s = cv.getStringValue().trim();
                              return s.isEmpty() ? java.math.BigDecimal.ZERO : new java.math.BigDecimal(s);
                default:      return java.math.BigDecimal.ZERO;
            }
        } catch (Exception e) { return java.math.BigDecimal.ZERO; }
    }



    private int getCellInt(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) return 0;
        try {
            switch (cell.getCellType()) {
                case NUMERIC: return (int) cell.getNumericCellValue();
                case STRING:  String s = cell.getStringCellValue().trim();
                              return s.isEmpty() ? 0 : Integer.parseInt(s);
                default:      return 0;
            }
        } catch (Exception e) { return 0; }
    }

 
    

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            performSearch();
        }
    }
    
    // Gán giá trị true cho cột môn học tương ứng
    private void setSubjectBoolean(XtNganhToHop nth, String mon) {
        switch (mon) {
            case "N1":   nth.setN1(true); break;
            case "TO":   nth.setTo(true); break;
            case "LI":   nth.setLi(true); break;
            case "HO":   nth.setHo(true); break;
            case "SI":   nth.setSi(true); break;
            case "VA":   nth.setVa(true); break;
            case "SU":   nth.setSu(true); break;
            case "DI":   nth.setDi(true); break;
            case "TI":   nth.setTi(true); break;
            case "GDCD": nth.setGdcd(true); break;
            case "KTPL": nth.setKtpl(true); break;
            case "CNCN": nth.setCncn(true); break;
            case "CNNN": nth.setCnnn(true); break;
            case "NK1":  nth.setNk1(true); break;
            case "NK2":  nth.setNk2(true); break;
            case "NK3":  nth.setNk3(true); break;
            case "NK4":  nth.setNk4(true); break;
            case "NK5":  nth.setNk5(true); break;
            case "NK6":  nth.setNk6(true); break;
        }
    }

    // Reset các trường boolean về false trước khi set (tránh null)
    private void resetBooleanFields(XtNganhToHop nth) {
        nth.setN1(false); nth.setTo(false); nth.setLi(false); nth.setHo(false);
        nth.setSi(false); nth.setVa(false); nth.setSu(false); nth.setDi(false);
        nth.setTi(false); nth.setGdcd(false); nth.setKtpl(false); nth.setCncn(false);
        nth.setCnnn(false); nth.setNk1(false); nth.setNk2(false); nth.setNk3(false);
        nth.setNk4(false); nth.setNk5(false); nth.setNk6(false);
    }
}