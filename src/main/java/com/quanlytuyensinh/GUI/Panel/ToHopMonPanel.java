package com.quanlytuyensinh.GUI.Panel;

import com.quanlytuyensinh.BUS.XtToHopMonThiBUS;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;
import com.quanlytuyensinh.GUI.Component.TableSorter;
import com.quanlytuyensinh.GUI.Dialog.ToHopMonDialog;
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

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class ToHopMonPanel extends JPanel implements ActionListener, ItemListener{
    private PanelBorderRadius pnlMain, functionBar;
    private Main mainFrame;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable paginatedTable;
    
    private XtToHopMonThiBUS monBUS; 
    private List<XtToHopMonThi> listToHop;
    private Color BackgroundColor = new Color(240, 247, 250);

    public static final LinkedHashMap<String, String> tenMap = new LinkedHashMap<String, String>() {{
    put("TO", "Toán"); put("VA", "Văn"); put("LI", "Vật lý"); put("HO", "Hóa học");
    put("SI", "Sinh học"); put("SU", "Lịch sử"); put("DI", "Địa lí"); 
    put("GDCD", "Giáo dục công dân"); put("N1", "Tiếng Anh");
    put("KTPL", "Giáo dục Kinh tế và pháp luật"); put("TI", "Tin học");
    put("CNCN", "Công nghệ công nghiệp"); put("CNNN", "Công nghệ nông nghiệp");
    put("NK1", "Kể chuyện - Đọc diễn cảm"); put("NK2", "Hát – Nhạc");
    put("NK3", "Hình họa"); put("NK4", "Trang trí");
    put("NK5", "Hát – Nhạc cụ"); put("NK6", "Xướng âm - Thẩm âm - Tiết tấu");
    }};
    
    public ToHopMonPanel(Main main) {
        this.mainFrame = main;
        monBUS = new XtToHopMonThiBUS();
        listToHop = monBUS.getList();
        initComponent();
        loadDataTable(listToHop);
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
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setPreferredWidth(300);
        
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
        
        String[] action = {"create", "update", "delete", "import"};
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
    
    public void loadDataTable(List<XtToHopMonThi> list) {
        java.util.List<Object[]> data = new java.util.ArrayList<>();

        for (XtToHopMonThi m : list) {
            data.add(new Object[]{
                m.getIdtohop(),
                m.getMatohop(),
                m.getMon1(),
                m.getMon2(),
                m.getMon3(),
                m.getTentohop()
            });
        }
        paginatedTable.setData(data);
    }
    
    private void performSearch() {
        String keyword = search.txtSearchForm.getText();
        String searchType = (String) search.cbxChoose.getSelectedItem();

        List<XtToHopMonThi> result = monBUS.search(searchType, keyword);
        loadDataTable(result);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("create")) {
            new ToHopMonDialog(this, monBUS, null, mainFrame, "Tạo tổ hợp mới").setVisible(true);
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            XtToHopMonThi selected = getSelectedToHop();
            if (selected != null) {
                new ToHopMonDialog(this, monBUS, selected, mainFrame, "Tạo tổ hợp mới").setVisible(true);
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
           XtToHopMonThi selected = getSelectedToHop();
           if (selected != null) {
               int confirm = JOptionPane.showConfirmDialog(this,
                       "Xóa tổ hợp mã " + selected.getMatohop() + "?",
                       "Xác nhận", JOptionPane.YES_NO_OPTION);
               if (confirm == JOptionPane.YES_OPTION) {
                   if (monBUS.deleteToHop(selected)) {
                       JOptionPane.showMessageDialog(this, "Xóa thành công!");
                       loadDataTable(monBUS.refreshList());
                   } else {
                       JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                   }
               }
           }
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
                    Map<String, XtToHopMonThi> monMap = new LinkedHashMap<>();
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
                            
                            String tenTohop = tenMap.get(mon1) + ", " + tenMap.get(mon2) + ", " + tenMap.get(mon3);

                            XtToHopMonThi th = new XtToHopMonThi(maTohop, mon1.toUpperCase(), mon2.toUpperCase(), mon3.toUpperCase(), tenTohop);
                            monMap.put(maTohop, th);
                        }
                    }

                    List<XtToHopMonThi> toImport = new ArrayList<>();
                    List<XtToHopMonThi> toUpdate = new ArrayList<>();

                    for (XtToHopMonThi th : monMap.values()) {
                        XtToHopMonThi existing = monBUS.findByMa(th.getMatohop());
                        if (existing == null) {
                            toImport.add(th);
                        }
                         else {
                            BeanUtils.copyProperties(th, existing, "idtohop", "matohop");
                            toUpdate.add(existing);
                        }
                    }
                
                    if (!toImport.isEmpty())
                        monBUS.importToDB(toImport);
                    
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
                listToHop = monBUS.refreshList();
                loadDataTable(listToHop);
                JOptionPane.showMessageDialog(mainFrame, "Hoàn tất Import!");
            }
        };

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

    private XtToHopMonThi getSelectedToHop() {
        int row = paginatedTable.getTable().getSelectedRow();
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        int id = (int) paginatedTable.getTable().getModel().getValueAt(modelRow, 0);
        return monBUS.findById(id);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        return;
    }
}
