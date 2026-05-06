package com.quanlytuyensinh.GUI.Panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;

public class DiemThiDGNLPanel extends JPanel implements ActionListener{
    private JScrollPane pnlMain;
    private PanelBorderRadius functionBar;
    private Main mainFrame;
    private JPanel contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;

    private XtDiemThiXetTuyenBUS diemBUS; 
    private List<XtDiemThiXetTuyen> listDiem;
    private Color BackgroundColor = new Color(240, 247, 250);
    

    public DiemThiDGNLPanel(Main main, XtDiemThiXetTuyenBUS bus, List<XtDiemThiXetTuyen> list) {
        this.mainFrame = main;
        diemBUS = bus;
        listDiem = list;
        initComponent();
        loadDataTable(listDiem);
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        String[] header = {
            "ID", "CCCD", "Điểm"
        };
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        
        Comparator<Object>[] comps = new Comparator[4];
        comps[0] = TableSorter.INTEGER_COMPARATOR;
        comps[1] = TableSorter.STRING_COMPARATOR;
        comps[2] = TableSorter.BIG_DECIMAL_COMPARATOR;
       
        paginatedTable.enableFullDataSorting(comps);

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = {"create", "update", "delete", "import"};
        mainFunction = new MainFunction(1, "nguoiDung", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[]{"CCCD"});
        search.cbxChoose.setEnabled(false);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadDataTable(diemBUS.getListDGNL());
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        pnlMain = new JScrollPane(paginatedTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlMain.setBorder(new EmptyBorder(0, 0, 10, 0));
        contentCenter.add(pnlMain, BorderLayout.CENTER);
    }

    private void loadDataTable(List<XtDiemThiXetTuyen> list) {
        java.util.List<Object[]> data = new java.util.ArrayList<>();
            for (XtDiemThiXetTuyen m : list) {
                data.add(new Object[]{
                    m.getIddiemthi(),
                    m.getCccd(),
                    isNull(m.getNl1()),
                });
            }
            paginatedTable.setData(data);
    }

    private String isNull(BigDecimal val) {
        if (val == null)
            return "---";
        else if (val.signum() == 0)
            return "---";
        return val.toString();
    }

    private void performSearch() {
        String keyword = search.txtSearchForm.getText();

        if (keyword == null || keyword.trim().isEmpty())
            loadDataTable(listDiem);
        
        String lowerKeyword = keyword.trim().toLowerCase();
        loadDataTable(listDiem.stream()
            .filter(qd -> String.valueOf(qd.getCccd()).contains(lowerKeyword))
            .collect(Collectors.toList()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
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

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        JOptionPane pane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(mainFrame, "Importing");
        dialog.setModal(true);
        dialog.pack();
    
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                java.io.File file = fileChooser.getSelectedFile();
                try {
                    System.out.println("Please run");
                    Map<String, XtDiemThiXetTuyen> diemMap = new LinkedHashMap<>();

                    try (FileInputStream fis = new FileInputStream(file);
                        Workbook workbook = file.getName().toLowerCase().endsWith(".xlsx")
                                ? new XSSFWorkbook(fis) : new HSSFWorkbook(fis)) {

                        Sheet sheet = workbook.getSheetAt(1);
                        boolean headerSkipped = false;
                        int i = 0;
                        System.out.println("Still running");
                        for (Row row : sheet) {
                            System.out.println("I found this row");
                            if (!headerSkipped) {
                                headerSkipped = true;
                                continue;
                            }
                            
                            String cccd = getCellString(row, 1);
                            String cellDiem = getCellString(row, 8);
                            BigDecimal valDiem = cellDiem.isEmpty() ? BigDecimal.ZERO : new BigDecimal(cellDiem);

                            if (diemMap.get(cccd) == null) {
                                XtDiemThiXetTuyen diem = new XtDiemThiXetTuyen();
                                diem.setCccd(cccd);
                                diem.setDPhuongthuc("DGNL");
                                diemMap.put(cccd, diem);
                            }

                            XtDiemThiXetTuyen d = diemMap.get(cccd);
                            BigDecimal compare = d.getNl1() == null ? BigDecimal.ZERO : d.getNl1();
                            d.setNl1(compare.max(valDiem));
                        }
                    }

                    List<XtDiemThiXetTuyen> toImport = new ArrayList<>();
                    List<XtDiemThiXetTuyen> toUpdate = new ArrayList<>();

                    for (XtDiemThiXetTuyen d : diemMap.values()) {
                        XtDiemThiXetTuyen existing = diemBUS.findByCCCDAndPT(d.getCccd(), "DGNL");
                        if (existing == null) {
                            toImport.add(d);
                        }
                        else {
                            // BeanUtils.copyProperties(d, existing, "iddiemthi", "cccd", "dPhuongthuc");
                            toUpdate.add(existing);
                        }
                    }
                
                    if (!toImport.isEmpty()) 
                        diemBUS.importToDB(toImport);
                    if(!toUpdate.isEmpty())
                        diemBUS.updateToDB(toUpdate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                return null;
            }   

            @Override
            protected void done() {
                try {
                    get();
                } catch (Exception e) {
                     JOptionPane.showMessageDialog(mainFrame,
                                "Lỗi đọc file Excel: " + e.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                dialog.dispose();
                listDiem = diemBUS.getListDGNL();
                loadDataTable(listDiem);
                JOptionPane.showMessageDialog(mainFrame, "Hoàn tất Import!");
            }
        };

        System.out.println("Starting import");
        worker.execute();
        dialog.setVisible(true);
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
}
