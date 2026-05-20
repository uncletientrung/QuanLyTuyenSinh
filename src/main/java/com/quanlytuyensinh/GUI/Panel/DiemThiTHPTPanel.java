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
import com.quanlytuyensinh.GUI.Dialog.THPTDialog;

public class DiemThiTHPTPanel extends JPanel implements ActionListener {
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

    public DiemThiTHPTPanel(Main main, XtDiemThiXetTuyenBUS bus, List<XtDiemThiXetTuyen> list) {
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
                "Toán", "Văn", "Lý", "Hóa", "Sinh", "Sử", "Địa",
                "GDCD", "Anh (thi)", "Anh (CC)",
                "CNCN", "CNNN", "Tin", "KTPL",
                "NK1", "NK2", "NK3", "NK4", "NK5", "NK6"
        };
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        table.setRowSorter(null);
        table.setAutoCreateRowSorter(false);
        Comparator<Object>[] comps = new Comparator[22];
        comps[0] = TableSorter.INTEGER_COMPARATOR;
        comps[1] = TableSorter.STRING_COMPARATOR;
        for (int i = 2; i <= 21; i++) {
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

        String[] action = { "create", "update", "delete", "import", "import_cert" };
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
            loadDataTable(diemBUS.getListTHPT());
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
                    isNull(m.getGdcd()),
                    isNull(m.getN1Thi()),
                    isNull(m.getN1Cc()),
                    isNull(m.getCncn()),
                    isNull(m.getCnnn()),
                    isNull(m.getTi()),
                    isNull(m.getKtpl()),
                    isNull(m.getNk1()),
                    isNull(m.getNk2()),
                    isNull(m.getNk3()),
                    isNull(m.getNk4()),
                    isNull(m.getNk5()),
                    isNull(m.getNk6()),
            });
        }
        paginatedTable.setData(data);
    }

    private String isNull(BigDecimal val) {
        if (val == null)
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
        } else if (e.getSource() == mainFunction.btn.get("import_cert")) {
            importChungChi();
        } else if (e.getSource() == mainFunction.btn.get("create")) {
            new THPTDialog(this, diemBUS, mainFrame, null, "Thêm điểm thí sinh mới").setVisible(true);
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            XtDiemThiXetTuyen selected = getSelectedRow();
            new THPTDialog(this, diemBUS, mainFrame, selected, "Cập nhật thông tin/điểm số").setVisible(true);
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            XtDiemThiXetTuyen selected = getSelectedRow();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Xóa dữ liệu điểm của căn cước " + selected.getCccd() + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (diemBUS.delete(selected)) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadDataTable(diemBUS.getListTHPT());
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void importChungChi() {
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
                    Map<String, String> diemMap = new LinkedHashMap<>();

                    try (FileInputStream fis = new FileInputStream(file);
                            Workbook workbook = file.getName().toLowerCase().endsWith(".xlsx")
                                    ? new XSSFWorkbook(fis)
                                    : new HSSFWorkbook(fis)) {

                        Sheet sheet = workbook.getSheetAt(0);
                        boolean headerSkipped = false;
                        String[] requiredColumns = {
                            "TT",
                            "CCCD",
                            "Chứng chỉ ngoại ngữ",
                            "Điểm/ Bậc chứng chỉ",
                            "Điểm Quy đổi",
                            "Điểm cộng"
                        };
                        for (Row row : sheet) {
                            if (!headerSkipped) {
                                Set<String> headers = new HashSet<>();

                                for (Cell cell : row) {
                                    headers.add(cell.getStringCellValue().trim());
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

                            String cccd = getCellString(row, 1);
                            if (cccd.isEmpty() || diemMap.containsKey(cccd))
                                continue;
                            String diem = getCellString(row, 4);

                            diemMap.put(cccd, diem);
                        }
                    }

                    diemMap.forEach((cccd, diem) -> {
                        diemBUS.updateCert(cccd, diem);
                    });
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
                if (isCancelled() == false) {
                    dialog.dispose();
                    listDiem = diemBUS.getListTHPT();
                    loadDataTable(listDiem);
                    JOptionPane.showMessageDialog(mainFrame, "Nhập chứng chỉ thành công!");
                }
            }
        };

        worker.execute();
        dialog.setVisible(true);
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
                                "STT", "CCCD", "Họ Tên", "Ngày sinh", "Giới tính",
                                "ĐTƯT", "KVƯT", "TO", "VA", "LI", "HO", "SI",
                                "SU", "DI", "GDCD", "NN", "Mã môn NN", "KTPL",
                                "TI", "CNCN", "CNNN", "Chương trình học",
                                "NK1", "NK2", "NK3", "NK4", "NK5", "NK6",
                                "NK7", "NK8", "NK9", "NK10",
                                "Điểm xét tốt nghiệp",
                                "Dân tộc", "Mã dân tộc", "Nơi sinh"
                        };

                        int i = 0;
                        for (Row row : sheet) {
                            if (!headerSkipped) {
                                Set<String> headers = new HashSet<>();

                                for (Cell cell : row) {
                                    headers.add(cell.getStringCellValue().trim());
                                }

                                for (String required : requiredColumns) {

                                    if (!headers.contains(required)) {
                                        System.out.println(required);
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
                            if (cccd.isEmpty() || diemMap.containsKey(cccd))
                                continue;

                            String cellToan = getCellString(row, 7);
                            BigDecimal Toan = cellToan.isEmpty() ? null : new BigDecimal(cellToan);
                            String cellVan = getCellString(row, 8);
                            BigDecimal Van = cellVan.isEmpty() ? null : new BigDecimal(cellVan);
                            String cellLi = getCellString(row, 9);
                            BigDecimal Li = cellLi.isEmpty() ? null : new BigDecimal(cellLi);
                            String cellHoa = getCellString(row, 10);
                            BigDecimal Hoa = cellHoa.isEmpty() ? null : new BigDecimal(cellHoa);
                            String cellSinh = getCellString(row, 11);
                            BigDecimal Sinh = cellSinh.isEmpty() ? null : new BigDecimal(cellSinh);
                            String cellSu = getCellString(row, 12);
                            BigDecimal Su = cellSu.isEmpty() ? null : new BigDecimal(cellSu);
                            String cellDia = getCellString(row, 13);
                            BigDecimal Dia = cellDia.isEmpty() ? null : new BigDecimal(cellDia);
                            String cellGDCD = getCellString(row, 14);
                            BigDecimal GDCD = cellGDCD.isEmpty() ? null : new BigDecimal(cellGDCD);
                            String cellThiN1 = getCellString(row, 15);
                            BigDecimal ThiN1 = cellThiN1.isEmpty() ? null : new BigDecimal(cellThiN1);
                            String cellKTPL = getCellString(row, 17);
                            BigDecimal KTPL = cellKTPL.isEmpty() ? null : new BigDecimal(cellKTPL);
                            String cellTin = getCellString(row, 18);
                            BigDecimal Tin = cellTin.isEmpty() ? null : new BigDecimal(cellTin);
                            String cellCNCN = getCellString(row, 19);
                            BigDecimal CNCN = cellCNCN.isEmpty() ? null : new BigDecimal(cellCNCN);
                            String cellCNNN = getCellString(row, 20);
                            BigDecimal CNNN = cellCNNN.isEmpty() ? null : new BigDecimal(cellCNNN);

                            String cellNK1 = getCellString(row, 22);
                            BigDecimal NK1 = cellNK1.isEmpty() ? null : new BigDecimal(cellNK1);
                            String cellNK2 = getCellString(row, 23);
                            BigDecimal NK2 = cellNK2.isEmpty() ? null : new BigDecimal(cellNK2);
                            String cellNK3 = getCellString(row, 24);
                            BigDecimal NK3 = cellNK3.isEmpty() ? null : new BigDecimal(cellNK3);
                            String cellNK4 = getCellString(row, 25);
                            BigDecimal NK4 = cellNK4.isEmpty() ? null : new BigDecimal(cellNK4);
                            String cellNK5 = getCellString(row, 26);
                            BigDecimal NK5 = cellNK5.isEmpty() ? null : new BigDecimal(cellNK5);
                            String cellNK6 = getCellString(row, 27);
                            BigDecimal NK6 = cellNK6.isEmpty() ? null : new BigDecimal(cellNK6);

                            XtDiemThiXetTuyen diem = new XtDiemThiXetTuyen(cccd, "THPT", Toan, Li, Hoa, Sinh, Su, Dia,
                                    Van, GDCD, ThiN1, null, CNCN, CNNN, Tin, KTPL, NK1, NK2, NK3, NK4, NK5, NK6);
                            diemMap.put(cccd, diem);
                        }
                    }

                    List<XtDiemThiXetTuyen> toImport = new ArrayList<>();
                    List<XtDiemThiXetTuyen> toUpdate = new ArrayList<>();

                    for (XtDiemThiXetTuyen d : diemMap.values()) {
                        XtDiemThiXetTuyen existing = diemBUS.findByCCCDAndPT(d.getCccd(), "THPT");
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
                if (isCancelled() == false) {
                    dialog.dispose();
                    listDiem = diemBUS.getListTHPT();
                    loadDataTable(listDiem);
                    JOptionPane.showMessageDialog(mainFrame, "Hoàn tất Import!");
                }
            }
        };

        worker.execute();
        dialog.setVisible(true);
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

    private XtDiemThiXetTuyen getSelectedRow() {
        int row = paginatedTable.getTable().getSelectedRow();
        int modelRow = paginatedTable.getTable().convertRowIndexToModel(row);
        int id = (int) paginatedTable.getTable().getModel().getValueAt(modelRow, 0);
        return diemBUS.findById(id);
    }
}
