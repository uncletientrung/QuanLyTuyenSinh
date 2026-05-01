package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.MonBUS;
import com.quanlytuyensinh.ENTITY.Mon;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class MonPanel extends JPanel implements ActionListener, ItemListener{
    private PanelBorderRadius pnlMain, functionBar;
    private Main mainFrame;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;
    
    private MonBUS monBUS; 
    private List<Mon> listMon;
    private Color BackgroundColor = new Color(240, 247, 250);
    
    public MonPanel(Main main) {
        this.mainFrame = main;
        monBUS = new MonBUS();
        listMon = monBUS.getList();
        initComponent();
        loadDataTable(listMon);
    }
    
    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        
        String[] header = new String[]{"#", "Mã tổ hợp", "Môn 1", "Môn 2", "Môn 3", "Tên tổ hợp"};
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        table.setAutoCreateRowSorter(false);
        Comparator<Object>[] comps = new Comparator[10];
        comps[0] = TableSorter.INTEGER_COMPARATOR;     // ID
        comps[1] = TableSorter.STRING_COMPARATOR;      // Mã tổ hợp
        comps[2] = TableSorter.STRING_COMPARATOR;      // Môn 1
        comps[3] = TableSorter.STRING_COMPARATOR;      // Môn 2
        comps[4] = TableSorter.STRING_COMPARATOR;      // Môn 3
        comps[5] = TableSorter.STRING_COMPARATOR;      // Tên tổ hợp
       
        paginatedTable.enableFullDataSorting(comps);
        
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
        
        String[] action = {"create", "update", "delete", "detail", "import"};
        mainFunction = new MainFunction(1, "nguoiDung", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        
        search = new IntegratedSearch(new String[]{"Tất cả", "Mã", "Môn 1", "Môn 2", "Môn 3", "Tên tổ hợp"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadDataTable(monBUS.refreshList());
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(paginatedTable, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);
    }
    
    public void loadDataTable(List<Mon> list) {
        java.util.List<Object[]> data = new java.util.ArrayList<>();

        for (Mon m : list) {
            data.add(new Object[]{
                m.getId(),
                m.getMaToHop(),
                m.getMon1(),
                m.getMon2(),
                m.getMon3(),
                m.getTenToHop()
            });
        }
        paginatedTable.setData(data);
    }
    
    private void performSearch() {
        String keyword = search.txtSearchForm.getText();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        List<Mon> result = monBUS.search(searchType, keyword);
        loadDataTable(result);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == mainFunction.btn.get("create")) {
//            new XtBangQuyDoiDialog(this, mainFrame, "Thêm bảng quy đổi", true, "create");
//        } else if (e.getSource() == mainFunction.btn.get("update")) {
//            XtBangQuyDoi selected = getSelectedQuyDoi();
//            if (selected != null) {
//                new XtBangQuyDoiDialog(this, mainFrame, "Chỉnh sửa bảng quy đổi", true, "update", selected);
//            }
//        } else if (e.getSource() == mainFunction.btn.get("detail")) {
//            XtBangQuyDoi selected = getSelectedQuyDoi();
//            if (selected != null) {
//                new XtBangQuyDoiDialog(this, mainFrame, "Chi tiết bảng quy đổi", true, "detail", selected);
//            }
//        } else if (e.getSource() == mainFunction.btn.get("delete")) {
//            XtBangQuyDoi selected = getSelectedQuyDoi();
//            if (selected != null) {
//                int confirm = JOptionPane.showConfirmDialog(this,
//                        "Xóa bảng quy đổi ID=" + selected.getIdqd() + "?",
//                        "Xác nhận", JOptionPane.YES_NO_OPTION);
//                if (confirm == JOptionPane.YES_OPTION) {
//                    if (qdBUS.deleteQuyDoi(selected.getIdqd())) {
//                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
//                        listQD = qdBUS.getAllQuyDoi();
//                        loadDataTable(listQD);
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        } else 
        if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        }
    }

    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        java.io.File file = fileChooser.getSelectedFile();
        try {
            Map<String, Mon> monMap = new LinkedHashMap<>();
            Pattern pattern = Pattern.compile("(\\w+)\\((\\w+)-(\\d+),(\\w+)-(\\d+),(\\w+)-(\\d+)\\)");

            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = file.getName().toLowerCase().endsWith(".xlsx")
                         ? new XSSFWorkbook(fis) : new HSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                boolean headerSkipped = false;

                for (Row row : sheet) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    // Cột 3: MA_TO_HOP dạng "B03(TO-3,VA-3,SI-1)"
                    String maToHopRaw = getCellString(row, 3);
                    if (maToHopRaw.isEmpty()) continue;

                    Matcher matcher = pattern.matcher(maToHopRaw);
                    if (!matcher.find()) continue;

                    String maTohop = matcher.group(1);
                    if (monMap.containsKey(maTohop)) continue;

                    String mon1 = matcher.group(2);
                    String mon2 = matcher.group(4);
                    String mon3 = matcher.group(6);

                    // Cột 5: TEN_TO_HOP
                    String tenTohop = getCellString(row, 5);
                    if (tenTohop.isEmpty()) tenTohop = maTohop;

                    Mon th = new Mon(maTohop, mon1.toUpperCase(), mon2.toUpperCase(), mon3.toUpperCase(), tenTohop);
                    monMap.put(maTohop, th);
                }
            }

            List<Mon> toImport = new ArrayList<>();
            for (Mon th : monMap.values()) {
                boolean existing = monBUS.existMaToHop(th.getMaToHop());
                if (existing == false) {
                    toImport.add(th);
                }
            }

            System.out.println("Done checking existing");
            System.out.println("Starting import");
            System.out.println(toImport.isEmpty());
           
            if (!toImport.isEmpty()) {
                SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        System.out.println("Import started");
                        monBUS.importToDB(toImport); 
                        return null;
                    }   

                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Import hoàn tất!", "Kết quả Import", JOptionPane.INFORMATION_MESSAGE);
                        listMon = monBUS.refreshList();
                        loadDataTable(listMon);
                    }
                };
                
                worker.execute();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi đọc file Excel: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val)) yield String.valueOf((long) val);
                else yield String.valueOf(val);
            }
            default -> "";
        };
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            performSearch();
        }
    }
}
