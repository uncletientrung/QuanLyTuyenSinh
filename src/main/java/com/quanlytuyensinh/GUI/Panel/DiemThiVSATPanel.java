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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.quanlytuyensinh.GUI.Dialog.VSATDialog;

public class DiemThiVSATPanel extends JPanel implements ActionListener {
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

    public DiemThiVSATPanel(Main main, XtDiemThiXetTuyenBUS bus, List<XtDiemThiXetTuyen> list) {
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
                "ID", "CCCD",
                "Toán", "Văn", "Lý", "Hóa", "Sinh", "Sử", "Địa", "Anh"
        };
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.INTEGER_COMPARATOR);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader()
                .getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        table.setRowSorter(null);
        table.setAutoCreateRowSorter(false);
        Comparator<Object>[] comps = new Comparator[10];
        comps[0] = TableSorter.INTEGER_COMPARATOR;
        comps[1] = TableSorter.STRING_COMPARATOR;
        for (int i = 2; i <= 9; i++) {
            comps[i] = TableSorter.BIG_DECIMAL_COMPARATOR;
        }

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

        String[] action = { "create", "update", "delete", "import" };
        mainFunction = new MainFunction(1, "nguoiDung", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[] { "CCCD" });
        search.cbxChoose.setEnabled(false);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
            loadDataTable(diemBUS.getListVSAT());
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);
        pnlMain = new JScrollPane(paginatedTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlMain.setBorder(new EmptyBorder(0, 0, 10, 0));
        contentCenter.add(pnlMain, BorderLayout.CENTER);
    }

    public void loadDataTable(List<XtDiemThiXetTuyen> list) {
        java.util.List<Object[]> data = new java.util.ArrayList<>();
        for (XtDiemThiXetTuyen m : list) {
            data.add(new Object[] {
                    m.getIddiemthi(),
                    m.getCccd(),
                    isNull(m.getTo()),
                    isNull(m.getVa()),
                    isNull(m.getLi()),
                    isNull(m.getHo()),
                    isNull(m.getSi()),
                    isNull(m.getSu()),
                    isNull(m.getDi()),
                    isNull(m.getN1Thi())
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
                .filter(qd -> String.valueOf(qd.getCccd().toLowerCase()).contains(lowerKeyword))
                .collect(Collectors.toList()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("create")) {
            new VSATDialog(this, diemBUS, mainFrame, null, "Thêm điểm thí sinh mới").setVisible(true);
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            XtDiemThiXetTuyen selected = getSelectedRow();
            new VSATDialog(this, diemBUS, mainFrame, selected, "Cập nhật thông tin/điểm số").setVisible(true);
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            XtDiemThiXetTuyen selected = getSelectedRow();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Xóa dữ liệu điểm của căn cước " + selected.getCccd() + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (diemBUS.delete(selected)) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadDataTable(diemBUS.getListVSAT());
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private XtDiemThiXetTuyen getSelectedRow() {
        int row = paginatedTable.getTable().getSelectedRow();
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        int id = (int) paginatedTable.getTable().getModel().getValueAt(modelRow, 0);
        return diemBUS.findById(id);
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
                    Map<String, XtDiemThiXetTuyen> diemMap = new LinkedHashMap<>();

                    try (FileInputStream fis = new FileInputStream(file);
                            Workbook workbook = file.getName().toLowerCase().endsWith(".xlsx")
                                    ? new XSSFWorkbook(fis)
                                    : new HSSFWorkbook(fis)) {

                        Sheet sheet = workbook.getSheetAt(0);
                        boolean headerSkipped = false;
                        String[] requiredColumns = {
                                "STT",
                                "CMND",
                                "DOTTHI",
                                "MADOTTHI",
                                "NGAYTHI",
                                "NAMTHI",
                                "MAMONTHI",
                                "TENMONTHI",
                                "DIEM",
                                "THANGDIEM",
                                "MADVTCTDL",
                                "TENDVTCTDL"
                        };
                        int i = 0;
                        for (Row row : sheet) {
                            if (!headerSkipped) {
                                Set<String> headers = new HashSet<>();

                                for (Cell cell : row) {
                                    headers.add(cell.getStringCellValue().trim().toUpperCase());
                                }

                                for (String required : requiredColumns) {

                                    if (!headers.contains(required)) {
                                        JOptionPane.showMessageDialog(mainFrame,
                                                "File excel không đúng định dạng",
                                                "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                                        dialog.dispose();
                                        cancel(true);
                                        return null;
                                    }
                                }

                                headerSkipped = true;
                                continue;
                            }

                            // if (i == 50)
                            //     break;
                            // i++;
                            String cccd = getCellString(row, 1);
                            String mon = getCellString(row, 6);
                            String cellDiem = getCellString(row, 8);
                            BigDecimal diem_mon = cellDiem.isEmpty() ? BigDecimal.ZERO : new BigDecimal(cellDiem);

                            if (diemMap.get(cccd) == null) {
                                XtDiemThiXetTuyen diem = new XtDiemThiXetTuyen();
                                diem.setCccd(cccd);
                                diem.setDPhuongthuc("VSAT");
                                diemMap.put(cccd, diem);
                            }

                            setVSAT(diemMap.get(cccd), mon, diem_mon);
                        }
                    }

                    List<XtDiemThiXetTuyen> toImport = new ArrayList<>();
                    List<XtDiemThiXetTuyen> toUpdate = new ArrayList<>();

                    for (XtDiemThiXetTuyen d : diemMap.values()) {
                        XtDiemThiXetTuyen existing = diemBUS.findByCCCDAndPT(d.getCccd(), "VSAT");
                        if (existing == null) {
                            toImport.add(d);
                        } else {
                            BeanUtils.copyProperties(d, existing, "iddiemthi", "cccd", "dPhuongthuc");
                            toUpdate.add(existing);
                        }
                    }

                    if (!toImport.isEmpty())
                        diemBUS.importToDB(toImport);
                    if (!toUpdate.isEmpty())
                        diemBUS.updateToDB(toUpdate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                if (isCancelled() == false){
                    dialog.dispose();
                    listDiem = diemBUS.getListVSAT();
                    loadDataTable(listDiem);
                    JOptionPane.showMessageDialog(mainFrame, "Hoàn tất Import!");
                }
            }
        };

        worker.execute();
        dialog.setVisible(true);
    }

    private void setVSAT(XtDiemThiXetTuyen d, String mon, BigDecimal diem) {
        switch (mon) {
            case "TO_VS":
            case "M1":
                d.setTo((d.getTo() == null ? BigDecimal.ZERO : d.getTo()).max(diem));
                break;
            case "VA_VS":
                d.setVa((d.getVa() == null ? BigDecimal.ZERO : d.getVa()).max(diem));
                break;
            case "LI_VS":
            case "M2":
                d.setLi((d.getLi() == null ? BigDecimal.ZERO : d.getLi()).max(diem));
                break;
            case "HO_VS":
            case "M3":
                d.setHo((d.getHo() == null ? BigDecimal.ZERO : d.getHo()).max(diem));
                break;
            case "SI_VS":
            case "M4":
                d.setSi((d.getSi() == null ? BigDecimal.ZERO : d.getSi()).max(diem));
                break;
            case "SU_VS":
            case "M6":
                d.setSu((d.getSu() == null ? BigDecimal.ZERO : d.getSu()).max(diem));
                break;
            case "DI_VS":
            case "M7":
                d.setDi((d.getDi() == null ? BigDecimal.ZERO : d.getDi()).max(diem));
                break;
            case "N1_VS":
            case "M8":
                d.setN1Thi((d.getN1Thi() == null ? BigDecimal.ZERO : d.getN1Thi()).max(diem));
                break;
            default:
                break;
        }
    }

    private String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null)
            return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val))
                    yield String.valueOf((long) val);
                else
                    yield String.valueOf(val);
            }
            default -> "";
        };
    }
}
