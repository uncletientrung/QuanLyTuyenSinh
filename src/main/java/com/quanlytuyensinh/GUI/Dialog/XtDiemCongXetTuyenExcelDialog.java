package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtToHopMonThiBUS;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Panel.XtDiemCongXetTuyenPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XtDiemCongXetTuyenExcelDialog extends JDialog implements ActionListener {

    private XtDiemCongXetTuyenPanel parentPanel;
    private JFrame mainFrame;

    private ButtonCustom btnThiSinh;
    private ButtonCustom btnDiemChungChi;
    private ButtonCustom btnDiemUuTien;

    private XtDiemCongXetTuyenBUS diemCongBUS;

    public XtDiemCongXetTuyenExcelDialog(XtDiemCongXetTuyenPanel parent, JFrame mainFrame) {
        super(mainFrame, "Chọn loại dữ liệu import", true);
        this.parentPanel = parent;
        this.mainFrame = mainFrame;
        this.diemCongBUS = new XtDiemCongXetTuyenBUS();
        initComponents();
    }

    private void initComponents() {
        this.setSize(new Dimension(600, 150));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Nút Thí sinh
        btnThiSinh = new ButtonCustom("Thí sinh", "success", 16, 160, 60);
        btnThiSinh.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(btnThiSinh, gbc);

        // Nút Điểm chứng chỉ
        btnDiemChungChi = new ButtonCustom("Điểm chứng chỉ", "warning", 16, 160, 60);
        btnDiemChungChi.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(btnDiemChungChi, gbc);

        // Nút Điểm ưu tiên xét tuyển
        btnDiemUuTien = new ButtonCustom("Điểm ưu tiên xét tuyển", "excel", 15, 180, 60);
        btnDiemUuTien.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(btnDiemUuTien, gbc);

        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThiSinh) {
            dispose();
            importThiSinhFromExcel();
        } else if (e.getSource() == btnDiemChungChi) {
            dispose();
            importDiemChungChiFromExcel();
        } else if (e.getSource() == btnDiemUuTien) {
            dispose();
            importDiemUuTienFromExcel();
        }
    }

    // Hàm import thí sinh, import các cột: CCCD, Mã ngành, Mã
    private void importThiSinhFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel import thí sinh");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx, *.xls)", "xlsx", "xls"));

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        JOptionPane pane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE);
        JDialog loadingDialog = pane.createDialog(mainFrame, "Đang import thí sinh...");
        loadingDialog.setModal(true);
        loadingDialog.pack();

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            private int successCount = 0, skipCount = 0, insertedCount = 0;
            private List<String> errorRows = new ArrayList<>();
            private String errorMessage = null;

            @Override
            protected Void doInBackground() throws Exception {
                FileInputStream fis = null;
                Workbook workbook = null;
                try {
                    fis = new FileInputStream(selectedFile);
                    workbook = WorkbookFactory.create(fis);
                    Sheet sheet = workbook.getSheetAt(0);

                    if (sheet.getPhysicalNumberOfRows() <= 1) {
                        errorMessage = "File Excel không có dữ liệu!";
                        return null;
                    }

                    Row headerRow = sheet.getRow(0);
                    int cccdCol = -1;
                    int maNganhCol = -1;
                    int maToHopCol = -1;
                    int phuongThucCol = -1;

                    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                        Cell cell = headerRow.getCell(i);
                        if (cell != null) {
                            String header = cell.getStringCellValue().trim();
                            if (header.equalsIgnoreCase("CCCD")) {
                                cccdCol = i;
                            } else if (header.equalsIgnoreCase("Mã ngành")) {
                                maNganhCol = i;
                            } else if (header.equalsIgnoreCase("Mã tổ hợp")) {
                                maToHopCol = i;
                            } else if (header.equalsIgnoreCase("Phương thức")) {
                                phuongThucCol = i;
                            }
                        }
                    }

                    if (cccdCol == -1 || maNganhCol == -1 || maToHopCol == -1 || phuongThucCol == -1) {
                        errorMessage = "File Excel phải có các cột: CCCD, Mã ngành, Mã tổ hợp, Phương thức";
                        return null;
                    }

                    List<XtDiemCongXetTuyen> importedList = new ArrayList<>();

                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }

                        try {
                            String cccd = getCellStringValue(row.getCell(cccdCol));
                            String maNganh = getCellStringValue(row.getCell(maNganhCol));
                            String maToHop = getCellStringValue(row.getCell(maToHopCol));
                            String phuongThuc = getCellStringValue(row.getCell(phuongThucCol));

                            if (cccd.isEmpty() && maNganh.isEmpty() && maToHop.isEmpty()) {
                                continue;
                            }

                            if (cccd.isEmpty() || maNganh.isEmpty() || maToHop.isEmpty()) {
                                errorRows.add("Dòng " + (i + 1) + ": Thiếu thông tin CCCD, Mã ngành hoặc Mã tổ hợp");
                                skipCount++;
                                continue;
                            }

                            XtDiemCongXetTuyen dc = new XtDiemCongXetTuyen();
                            dc.setTsCccd(cccd.trim());
                            dc.setMaNganh(maNganh.trim());
                            dc.setMaToHop(maToHop.trim());
                            dc.setPhuongThuc(phuongThuc.isEmpty() ? "THPT" : phuongThuc.trim());
                            dc.setDiemCC(BigDecimal.ZERO);
                            dc.setDiemUtxt(BigDecimal.ZERO);
                            dc.setDiemTong(BigDecimal.ZERO);
                            dc.setGhiChu("");
                            dc.setDcKeys(cccd.trim() + "_" + maNganh.trim() + "_" + maToHop.trim());

                            try {
                                diemCongBUS.validateDiemCong(dc);
                                importedList.add(dc);
                                successCount++;
                            } catch (IllegalArgumentException ex) {
                                errorRows.add("Dòng " + (i + 1) + ": " + ex.getMessage());
                                skipCount++;
                            }

                        } catch (Exception ex) {
                            errorRows.add("Dòng " + (i + 1) + ": Lỗi đọc dữ liệu - " + ex.getMessage());
                            skipCount++;
                        }
                    }

                    for (XtDiemCongXetTuyen dc : importedList) {
                        try {
                            if (diemCongBUS.addDiemCong(dc)) {
                                insertedCount++;
                            }
                        } catch (Exception ex) {
                            errorRows.add(dc.getTsCccd() + ": Lỗi khi thêm vào DB - " + ex.getMessage());
                        }
                    }

                } catch (IOException ex) {
                    errorMessage = "Lỗi đọc file: " + ex.getMessage();
                } catch (Exception ex) {
                    errorMessage = "Lỗi xử lý file: " + ex.getMessage();
                } finally {
                    try {
                        if (workbook != null) {
                            workbook.close();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException ex) {
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();

                if (errorMessage != null) {
                    JOptionPane.showMessageDialog(mainFrame, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder message = new StringBuilder();
                message.append("Kết quả import:\n");
                message.append("- Thành công: ").append(successCount).append(" dòng\n");
                message.append("- Đã thêm vào DB: ").append(insertedCount).append(" dòng\n");
                message.append("- Bỏ qua: ").append(skipCount).append(" dòng\n");

                if (!errorRows.isEmpty()) {
                    message.append("\nChi tiết lỗi:\n");
                    int maxErrors = Math.min(errorRows.size(), 10);
                    for (int i = 0; i < maxErrors; i++) {
                        message.append("  • ").append(errorRows.get(i)).append("\n");
                    }
                    if (errorRows.size() > 10) {
                        message.append("  • ... và ").append(errorRows.size() - 10).append(" lỗi khác\n");
                    }
                }

                JOptionPane.showMessageDialog(mainFrame, message.toString(), "Kết quả Import", JOptionPane.INFORMATION_MESSAGE);

                if (insertedCount > 0) {
                    parentPanel.loadDataTable(diemCongBUS.getAllDiemCong());
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

    // Import điểm chứng chỉ từ file Excel
    private void importDiemChungChiFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel import điểm chứng chỉ");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx, *.xls)", "xlsx", "xls"));

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        JOptionPane pane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE);
        JDialog loadingDialog = pane.createDialog(mainFrame, "Đang import điểm chứng chỉ...");
        loadingDialog.setModal(true);
        loadingDialog.pack();

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            private Map<String, BigDecimal> diemCongMap = new HashMap<>();
            private List<String> errorRows = new ArrayList<>();
            private List<String> notFoundCCCDs = new ArrayList<>();
            private List<String> updateErrors = new ArrayList<>();
            private List<String> englishSubjectErrors = new ArrayList<>();
            private int updatedCount = 0, notFoundCount = 0, englishErrorCount = 0;
            private String errorMessage = null;

            @Override
            protected Void doInBackground() throws Exception {
                FileInputStream fis = null;
                Workbook workbook = null;

                try {
                    fis = new FileInputStream(selectedFile);
                    workbook = WorkbookFactory.create(fis);
                    Sheet sheet = workbook.getSheetAt(0);

                    if (sheet.getPhysicalNumberOfRows() <= 1) {
                        errorMessage = "File Excel không có dữ liệu!";
                        return null;
                    }

                    Row headerRow = sheet.getRow(0);
                    int cccdCol = -1;
                    int diemCongCol = -1;

                    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                        Cell cell = headerRow.getCell(i);
                        if (cell != null) {
                            String header = cell.getStringCellValue().trim().toLowerCase();
                            if (header.contains("cccd")) {
                                cccdCol = i;
                            } else if (header.contains("điểm cộng") || header.contains("diem cong")) {
                                diemCongCol = i;
                            }
                        }
                    }

                    if (cccdCol == -1 || diemCongCol == -1) {
                        errorMessage = "File Excel phải có các cột: CCCD và Điểm cộng";
                        return null;
                    }

                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }

                        try {
                            String cccd = getCellStringValue(row.getCell(cccdCol));
                            String diemCongStr = getCellStringValue(row.getCell(diemCongCol));

                            if (cccd.isEmpty() && diemCongStr.isEmpty()) {
                                continue;
                            }

                            if (cccd.isEmpty()) {
                                errorRows.add("Dòng " + (i + 1) + ": Thiếu CCCD");
                                continue;
                            }

                            BigDecimal diemCong;
                            try {
                                diemCong = parseBigDecimal(diemCongStr);
                            } catch (NumberFormatException e) {
                                errorRows.add("Dòng " + (i + 1) + " (CCCD: " + cccd + "): Điểm cộng không hợp lệ - '" + diemCongStr + "'");
                                continue;
                            }

                            if (diemCongMap.containsKey(cccd.trim())) {
                                BigDecimal existingDiem = diemCongMap.get(cccd.trim());
                                if (diemCong.compareTo(existingDiem) > 0) {
                                    diemCongMap.put(cccd.trim(), diemCong);
                                }
                            } else {
                                diemCongMap.put(cccd.trim(), diemCong);
                            }

                        } catch (Exception ex) {
                            errorRows.add("Dòng " + (i + 1) + ": Lỗi đọc dữ liệu - " + ex.getMessage());
                        }
                    }

                    if (diemCongMap.isEmpty()) {
                        errorMessage = "Không có dữ liệu hợp lệ trong file Excel!";
                        return null;
                    }

                    List<XtDiemCongXetTuyen> allDiemCong = diemCongBUS.getAllDiemCong();

                    for (Map.Entry<String, BigDecimal> entry : diemCongMap.entrySet()) {
                        String cccd = entry.getKey();
                        BigDecimal diemCong = entry.getValue();

                        List<XtDiemCongXetTuyen> matchedRecords = new ArrayList<>();
                        for (XtDiemCongXetTuyen dc : allDiemCong) {
                            if (dc.getTsCccd() != null && dc.getTsCccd().equals(cccd)) {
                                matchedRecords.add(dc);
                            }
                        }

                        if (matchedRecords.isEmpty()) {
                            notFoundCount++;
                            if (notFoundCCCDs.size() < 10) {
                                notFoundCCCDs.add(cccd);
                            }
                            continue;
                        }

                        for (XtDiemCongXetTuyen dc : matchedRecords) {
                            try {
                                dc.setDiemCC(diemCong);
                                BigDecimal diemUtxt = dc.getDiemUtxt() != null ? dc.getDiemUtxt() : BigDecimal.ZERO;
                                dc.setDiemTong(diemCong.add(diemUtxt));

                                try {
                                    diemCongBUS.validateDiemCong(dc);
                                    if (diemCongBUS.updateDiemCong(dc)) {
                                        updatedCount++;
                                    } else {
                                        updateErrors.add("CCCD " + cccd + ": Không thể cập nhật vào DB");
                                    }
                                } catch (IllegalArgumentException ex) {
                                    String errorMsg = ex.getMessage();
                                    updateErrors.add("CCCD " + cccd + " (Mã tổ hợp: " + dc.getMaToHop() + "): " + errorMsg);

                                    if (errorMsg.contains("có chứa môn tiếng Anh") || errorMsg.contains("N1")) {
                                        englishErrorCount++;
                                        englishSubjectErrors.add("CCCD " + cccd + " - Tổ hợp: " + dc.getMaToHop());
                                    }
                                }
                            } catch (Exception ex) {
                                updateErrors.add("CCCD " + cccd + ": Lỗi - " + ex.getMessage());
                            }
                        }
                    }

                } catch (IOException ex) {
                    errorMessage = "Lỗi đọc file: " + ex.getMessage();
                } catch (Exception ex) {
                    errorMessage = "Lỗi xử lý file: " + ex.getMessage();
                    ex.printStackTrace();
                } finally {
                    try {
                        if (workbook != null) {
                            workbook.close();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException ex) {
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();

                if (errorMessage != null) {
                    JOptionPane.showMessageDialog(mainFrame, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder message = new StringBuilder();
                message.append("Kết quả import:\n");
                message.append("Tổng số CCCD trong file Excel: ").append(diemCongMap.size()).append("\n");
                message.append("Số bản ghi đã cập nhật: ").append(updatedCount).append("\n");
                message.append("Số CCCD không tìm thấy trong hệ thống: ").append(notFoundCount).append("\n");

                if (englishErrorCount > 0) {
                    message.append("\nKHÔNG THỂ CẬP NHẬT do tổ hợp có môn tiếng Anh: ").append(englishErrorCount).append(" bản ghi\n");
                    for (int i = 0; i < Math.min(englishSubjectErrors.size(), 10); i++) {
                        message.append("  • ").append(englishSubjectErrors.get(i)).append("\n");
                    }
                    if (englishSubjectErrors.size() > 10) {
                        message.append("  • ... và ").append(englishSubjectErrors.size() - 10).append(" lỗi khác\n");
                    }
                    message.append("Tổ hợp có môn N1 không được cộng điểm chứng chỉ\n");
                }

                if (!notFoundCCCDs.isEmpty()) {
                    message.append("\nCác CCCD không tìm thấy:\n");
                    for (String cccd : notFoundCCCDs) {
                        message.append("  • ").append(cccd).append("\n");
                    }
                    if (notFoundCount > 10) {
                        message.append("  • ... và ").append(notFoundCount - 10).append(" CCCD khác\n");
                    }
                }

                if (!errorRows.isEmpty()) {
                    message.append("\nLỗi đọc dữ liệu Excel:\n");
                    int maxErrors = Math.min(errorRows.size(), 10);
                    for (int i = 0; i < maxErrors; i++) {
                        message.append("  • ").append(errorRows.get(i)).append("\n");
                    }
                    if (errorRows.size() > 10) {
                        message.append("  • ... và ").append(errorRows.size() - 10).append(" lỗi khác\n");
                    }
                }

                message.append("\nHoàn thành import!");

                JOptionPane.showMessageDialog(mainFrame, message.toString(), "Kết quả Import Điểm Chứng Chỉ", JOptionPane.INFORMATION_MESSAGE);

                if (updatedCount > 0) {
                    parentPanel.loadDataTable(diemCongBUS.getAllDiemCong());
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

    // Excel điểm ưu tiên
    private void importDiemUuTienFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel import điểm ưu tiên xét tuyển");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx, *.xls)", "xlsx", "xls"));

        int result = fileChooser.showOpenDialog(mainFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        JOptionPane pane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE);
        JDialog loadingDialog = pane.createDialog(mainFrame, "Đang import điểm ưu tiên xét tuyển...");
        loadingDialog.setModal(true);
        loadingDialog.pack();

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            private int successCount = 0, errorCount = 0, notFoundCount = 0;
            private List<String> errorRows = new ArrayList<>();
            private String errorMessage = null;
            private XtToHopMonThiBUS toHopBUS = new XtToHopMonThiBUS();

            @Override
            protected Void doInBackground() throws Exception {
                FileInputStream fis = null;
                Workbook workbook = null;

                try {
                    fis = new FileInputStream(selectedFile);
                    workbook = WorkbookFactory.create(fis);

                    Map<String, BigDecimal> diemUuTienMap = new HashMap<>();
                    List<XtToHopMonThi> listToHop = toHopBUS.getList();
                    List<XtDiemCongXetTuyen> allDiemCong = diemCongBUS.getAllDiemCong();

                    // SHEET 1
                    Sheet sheetThiSinh = null;
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        String name = workbook.getSheetName(i).toLowerCase();
                        if (name.contains("thi sinh") || name.contains("thisinh")) {
                            sheetThiSinh = workbook.getSheetAt(i);
                            break;
                        }
                    }
                    if (sheetThiSinh == null && workbook.getNumberOfSheets() > 0) {
                        sheetThiSinh = workbook.getSheetAt(0);
                    }

                    if (sheetThiSinh != null) {
                        // Đọc header xác định vị trí cột
                        Row headerRow = sheetThiSinh.getRow(0);
                        int cccdCol = -1, maMonCol = -1, diemCoMonCol = -1, diemKoMonCol = -1;

                        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                            Cell cell = headerRow.getCell(i);
                            if (cell == null) {
                                continue;
                            }
                            String header = cell.getStringCellValue().trim();
                            if (header.equalsIgnoreCase("CCCD")) {
                                cccdCol = i;
                            } else if (header.equalsIgnoreCase("Mã môn")) {
                                maMonCol = i;
                            } else if (header.contains("Điểm cộng cho môn đạt giải")) {
                                diemCoMonCol = i;
                            } else if (header.contains("Điểm cộng cho THXT")) {
                                diemKoMonCol = i;
                            }
                        }

                        if (cccdCol >= 0 && maMonCol >= 0 && diemCoMonCol >= 0 && diemKoMonCol >= 0) {
                            for (int i = 1; i <= sheetThiSinh.getLastRowNum(); i++) {
                                Row row = sheetThiSinh.getRow(i);
                                if (row == null) {
                                    continue;
                                }

                                String cccd = getCellStringValue(row.getCell(cccdCol));
                                String maMon = getCellStringValue(row.getCell(maMonCol));
                                BigDecimal diemCoMon = parseBigDecimal(getCellStringValue(row.getCell(diemCoMonCol)));
                                BigDecimal diemKoMon = parseBigDecimal(getCellStringValue(row.getCell(diemKoMonCol)));

                                if (cccd.isEmpty()) {
                                    continue;
                                }

                                // Tìm tất cả bản ghi của thí sinh này
                                for (XtDiemCongXetTuyen dc : allDiemCong) {
                                    if (cccd.equals(dc.getTsCccd())) {
                                        XtToHopMonThi toHop = findToHopByMa(listToHop, dc.getMaToHop());
                                        BigDecimal diemCong;

                                        if (toHop != null && monTrungToHop(maMon, toHop)) {
                                            // Môn đạt giải CÓ trong tổ hợp
                                            diemCong = diemCoMon;
                                        } else {
                                            // Môn đạt giải KHÔNG trong tổ hợp
                                            diemCong = diemKoMon;
                                        }

                                        String key = cccd + "||" + dc.getMaNganh() + "||" + dc.getMaToHop();
                                        diemUuTienMap.merge(key, diemCong,
                                                (oldVal, newVal) -> oldVal.compareTo(newVal) > 0 ? oldVal : newVal);
                                    }
                                }
                            }
                        } else {
                            errorRows.add("Sheet 'ds thi sinh': Không tìm thấy đủ cột CCCD, Mã môn, Điểm cộng");
                        }
                    }

                    // SHEET 2
                    Sheet sheetNguyenVong = null;
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        String name = workbook.getSheetName(i).toLowerCase();
                        if (name.contains("nguyen vong") || name.contains("nguyenvong")) {
                            sheetNguyenVong = workbook.getSheetAt(i);
                            break;
                        }
                    }
                    if (sheetNguyenVong == null && workbook.getNumberOfSheets() > 1) {
                        sheetNguyenVong = workbook.getSheetAt(1);
                    }

                    if (sheetNguyenVong != null) {
                        Row headerRow = sheetNguyenVong.getRow(0);
                        int cccdCol = -1, monDatGiaiCol = -1, diemCoMonCol = -1, diemKoMonCol = -1;

                        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                            Cell cell = headerRow.getCell(i);
                            if (cell == null) {
                                continue;
                            }
                            String header = cell.getStringCellValue().trim();
                            if (header.equalsIgnoreCase("CCCD")) {
                                cccdCol = i;
                            } else if (header.equalsIgnoreCase("Môn đạt giải")) {
                                monDatGiaiCol = i;
                            } else if (header.contains("Điểm cộng cho môn đạt giải")) {
                                diemCoMonCol = i;
                            } else if (header.contains("Điểm cộng cho THXT")) {
                                diemKoMonCol = i;
                            }
                        }

                        if (cccdCol >= 0 && monDatGiaiCol >= 0 && diemCoMonCol >= 0 && diemKoMonCol >= 0) {
                            for (int i = 1; i <= sheetNguyenVong.getLastRowNum(); i++) {
                                Row row = sheetNguyenVong.getRow(i);
                                if (row == null) {
                                    continue;
                                }

                                String cccd = getCellStringValue(row.getCell(cccdCol));
                                String tenMonDatGiai = getCellStringValue(row.getCell(monDatGiaiCol));
                                BigDecimal diemCoMon = parseBigDecimal(getCellStringValue(row.getCell(diemCoMonCol)));
                                BigDecimal diemKoMon = parseBigDecimal(getCellStringValue(row.getCell(diemKoMonCol)));

                                if (cccd.isEmpty()) {
                                    continue;
                                }

                                // Tìm tất cả bản ghi của thí sinh này
                                for (XtDiemCongXetTuyen dc : allDiemCong) {
                                    if (cccd.equals(dc.getTsCccd())) {
                                        XtToHopMonThi toHop = findToHopByMa(listToHop, dc.getMaToHop());
                                        BigDecimal diemCong;

                                        if (toHop != null && tenMonTrungTenToHop(tenMonDatGiai, toHop.getTentohop())) {
                                            // Tên môn đạt giải CÓ trong tên tổ hợp
                                            diemCong = diemCoMon;
                                        } else {
                                            // Tên môn đạt giải KHÔNG trong tên tổ hợp
                                            diemCong = diemKoMon;
                                        }

                                        String key = cccd + "||" + dc.getMaNganh() + "||" + dc.getMaToHop();
                                        diemUuTienMap.merge(key, diemCong,
                                                (oldVal, newVal) -> oldVal.compareTo(newVal) > 0 ? oldVal : newVal);
                                    }
                                }
                            }
                        } else {
                            errorRows.add("Sheet 'ds nguyen vong': Không tìm thấy đủ cột CCCD, Môn đạt giải, Điểm cộng");
                        }
                    }

                    if (diemUuTienMap.isEmpty()) {
                        errorMessage = "Không có dữ liệu hợp lệ trong file Excel!";
                        return null;
                    }

                    //Vô db
                    for (Map.Entry<String, BigDecimal> entry : diemUuTienMap.entrySet()) {
                        String[] parts = entry.getKey().split("\\|\\|", 3);
                        if (parts.length < 3) {
                            continue;
                        }
                        String cccd = parts[0];
                        String maNganh = parts[1];
                        String maToHop = parts[2];
                        BigDecimal diemUuTien = entry.getValue();

                        boolean found = false;
                        for (XtDiemCongXetTuyen dc : allDiemCong) {
                            if (cccd.equals(dc.getTsCccd())
                                    && maNganh.equals(dc.getMaNganh())
                                    && maToHop.equals(dc.getMaToHop())) {

                                dc.setDiemUtxt(diemUuTien);
                                BigDecimal diemCC = dc.getDiemCC() != null ? dc.getDiemCC() : BigDecimal.ZERO;
                                dc.setDiemTong(diemCC.add(diemUuTien));

                                try {
                                    diemCongBUS.validateDiemCong(dc);
                                    if (diemCongBUS.updateDiemCong(dc)) {
                                        successCount++;
                                    } else {
                                        errorRows.add("CCCD " + cccd + ": Không thể cập nhật");
                                        errorCount++;
                                    }
                                } catch (IllegalArgumentException ex) {
                                    errorRows.add("CCCD " + cccd + ": " + ex.getMessage());
                                    errorCount++;
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            notFoundCount++;
                        }
                    }

                } catch (IOException ex) {
                    errorMessage = "Lỗi đọc file: " + ex.getMessage();
                } catch (Exception ex) {
                    errorMessage = "Lỗi xử lý file: " + ex.getMessage();
                    ex.printStackTrace();
                } finally {
                    try {
                        if (workbook != null) {
                            workbook.close();
                        }
                    } catch (IOException ex) {
                    }
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException ex) {
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();

                if (errorMessage != null) {
                    JOptionPane.showMessageDialog(mainFrame, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder message = new StringBuilder();
                message.append("Kết quả import điểm ưu tiên xét tuyển:\n\n");
                message.append("Cập nhật thành công: ").append(successCount).append(" bản ghi\n");
                message.append("Lỗi: ").append(errorCount).append(" bản ghi\n");
                message.append("Không tìm thấy trong DB: ").append(notFoundCount).append(" bản ghi\n");

                if (!errorRows.isEmpty()) {
                    message.append("\nChi tiết:\n");
                    for (int i = 0; i < Math.min(errorRows.size(), 15); i++) {
                        message.append("  • ").append(errorRows.get(i)).append("\n");
                    }
                    if (errorRows.size() > 15) {
                        message.append("  • ... và ").append(errorRows.size() - 15).append(" lỗi khác\n");
                    }
                }

                JOptionPane.showMessageDialog(mainFrame, message.toString(),
                        "Kết quả Import", JOptionPane.INFORMATION_MESSAGE);

                if (successCount > 0) {
                    parentPanel.loadDataTable(diemCongBUS.getAllDiemCong());
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }
    // END Excel điểm ưu tiên

    // Lấy giá trị chuỗi từ cell Excel
    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Nếu là số nguyên, chuyển về số nguyên
                double numValue = cell.getNumericCellValue();
                if (numValue == Math.floor(numValue) && !Double.isInfinite(numValue)) {
                    return String.valueOf((long) numValue);
                }
                return String.valueOf(numValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue().trim();
                } catch (Exception e) {
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (Exception e2) {
                        return cell.getCellFormula();
                    }
                }
            default:
                return "";
        }
    }

    // Chuyển chuỗi thành BigDecimal 
    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        // Thay , = .
        String normalized = value.trim().replace(",", ".");
        return new BigDecimal(normalized);
    }

    // Mã Môn == mon1 || mon2 || mon3
    private boolean monTrungToHop(String maMon, XtToHopMonThi toHop) {
        if (maMon == null || toHop == null) {
            return false;
        }
        String mon = maMon.trim().toUpperCase();
        return mon.equalsIgnoreCase(toHop.getMon1())
                || mon.equalsIgnoreCase(toHop.getMon2())
                || mon.equalsIgnoreCase(toHop.getMon3());
    }

    // Tên Môn == Tên Tổ hợp (Môn1, Môn2, Môn3)
    private boolean tenMonTrungTenToHop(String tenMon, String tenToHop) {
        if (tenMon == null || tenMon.trim().isEmpty() || tenToHop == null) {
            return false;
        }
        return tenToHop.toLowerCase().contains(tenMon.trim().toLowerCase());
    }

    // Tìm tổ hợp bởi Mã
    private XtToHopMonThi findToHopByMa(List<XtToHopMonThi> listToHop, String maToHop) {
        if (maToHop == null) {
            return null;
        }
        for (XtToHopMonThi th : listToHop) {
            if (maToHop.equalsIgnoreCase(th.getMatohop())) {
                return th;
            }
        }
        return null;
    }
}
