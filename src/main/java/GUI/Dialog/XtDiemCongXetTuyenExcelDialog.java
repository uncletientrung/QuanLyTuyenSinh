package GUI.Dialog;

import BUS.XtDiemCongXetTuyenBUS;
import ENTITY.XtDiemCongXetTuyen;
import GUI.Component.ButtonCustom;
import GUI.Panel.XtDiemCongXetTuyenPanel;
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
            // TODO: Gọi hàm import điểm ưu tiên xét tuyển
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
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            fis = new FileInputStream(selectedFile);
            workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() <= 1) {
                JOptionPane.showMessageDialog(mainFrame, "File Excel không có dữ liệu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Đọc header để xác định vị trí các cột
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

            // Kiểm tra xem đã tìm thấy đủ các cột cần thiết chưa
            if (cccdCol == -1 || maNganhCol == -1 || maToHopCol == -1 || phuongThucCol == -1) {
                JOptionPane.showMessageDialog(mainFrame,
                        "File Excel phải có các cột: CCCD, Mã ngành, Mã tổ hợp, Phương thức",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Import dữ liệu
            List<XtDiemCongXetTuyen> importedList = new ArrayList<>();
            List<String> errorRows = new ArrayList<>();
            int successCount = 0;
            int skipCount = 0;

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

                    // Bỏ qua dòng trống
                    if (cccd.isEmpty() && maNganh.isEmpty() && maToHop.isEmpty()) {
                        continue;
                    }

                    // Kiểm tra dữ liệu bắt buộc
                    if (cccd.isEmpty() || maNganh.isEmpty() || maToHop.isEmpty()) {
                        errorRows.add("Dòng " + (i + 1) + ": Thiếu thông tin CCCD, Mã ngành hoặc Mã tổ hợp");
                        skipCount++;
                        continue;
                    }

                    // Tạo đối tượng XtDiemCongXetTuyen
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

                    // Validate và thêm vào list tạm
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

            // Thực hiện insert vào database
            int insertedCount = 0;
            for (XtDiemCongXetTuyen dc : importedList) {
                try {
                    if (diemCongBUS.addDiemCong(dc)) {
                        insertedCount++;
                    }
                } catch (Exception ex) {
                    errorRows.add(dc.getTsCccd() + ": Lỗi khi thêm vào DB - " + ex.getMessage());
                }
            }

            // Hiển thị kết quả
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

            // Refresh bảng dữ liệu
            if (insertedCount > 0) {
                parentPanel.loadDataTable(diemCongBUS.getAllDiemCong());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Lỗi xử lý file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                // Bỏ qua lỗi khi đóng file
            }
        }
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
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            fis = new FileInputStream(selectedFile);
            workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() <= 1) {
                JOptionPane.showMessageDialog(mainFrame, "File Excel không có dữ liệu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Đọc header để xác định vị trí các cột
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

            // Kiểm tra xem đã tìm thấy đủ các cột cần thiết chưa
            if (cccdCol == -1 || diemCongCol == -1) {
                JOptionPane.showMessageDialog(mainFrame,
                        "File Excel phải có các cột: CCCD và Điểm cộng",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Đọc dữ liệu từ Excel vào Map (CCCD -> Điểm cộng)
            // Nếu có nhiều dòng cùng CCCD, lấy dòng có điểm cộng cao nhất
            Map<String, BigDecimal> diemCongMap = new HashMap<>();
            List<String> errorRows = new ArrayList<>();

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                try {
                    String cccd = getCellStringValue(row.getCell(cccdCol));
                    String diemCongStr = getCellStringValue(row.getCell(diemCongCol));

                    // Bỏ qua dòng trống
                    if (cccd.isEmpty() && diemCongStr.isEmpty()) {
                        continue;
                    }

                    // Kiểm tra dữ liệu bắt buộc
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

                    // Nếu CCCD đã tồn tại, lấy điểm cao hơn
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
                JOptionPane.showMessageDialog(mainFrame, "Không có dữ liệu hợp lệ trong file Excel!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cập nhật điểm chứng chỉ vào database
            List<XtDiemCongXetTuyen> allDiemCong = diemCongBUS.getAllDiemCong();
            int updatedCount = 0;
            int notFoundCount = 0;
            List<String> notFoundCCCDs = new ArrayList<>();
            List<String> updateErrors = new ArrayList<>();

            for (Map.Entry<String, BigDecimal> entry : diemCongMap.entrySet()) {
                String cccd = entry.getKey();
                BigDecimal diemCong = entry.getValue();

                // Tìm tất cả bản ghi có CCCD này
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

                // Cập nhật từng bản ghi
                for (XtDiemCongXetTuyen dc : matchedRecords) {
                    try {
                        dc.setDiemCC(diemCong);

                        // Tính lại điểm tổng = diemCC + diemUtxt
                        BigDecimal diemUtxt = dc.getDiemUtxt() != null ? dc.getDiemUtxt() : BigDecimal.ZERO;
                        dc.setDiemTong(diemCong.add(diemUtxt));

                        // Validate trước khi update
                        try {
                            diemCongBUS.validateDiemCong(dc);
                            if (diemCongBUS.updateDiemCong(dc)) {
                                updatedCount++;
                            } else {
                                updateErrors.add("CCCD " + cccd + ": Không thể cập nhật vào DB");
                            }
                        } catch (IllegalArgumentException ex) {
                            updateErrors.add("CCCD " + cccd + ": " + ex.getMessage());
                        }
                    } catch (Exception ex) {
                        updateErrors.add("CCCD " + cccd + ": Lỗi - " + ex.getMessage());
                    }
                }
            }

            // Hiển thị kết quả
            StringBuilder message = new StringBuilder();
            message.append("Kết quả import:\n");
            message.append("Tổng số CCCD trong file Excel: ").append(diemCongMap.size()).append("\n");
            message.append("Số bản ghi đã cập nhật: ").append(updatedCount).append("\n");
            message.append("Số CCCD không tìm thấy trong hệ thống: ").append(notFoundCount).append("\n");

            if (!notFoundCCCDs.isEmpty()) {
                message.append("\nCác CCCD không tìm thấy:\n");
                for (String cccd : notFoundCCCDs) {
                    message.append("  • ").append(cccd).append("\n");
                }
                if (notFoundCount > 10) {
                    message.append("  • ... và ").append(notFoundCount - 10).append(" CCCD khác\n");
                }
            }

            if (!updateErrors.isEmpty()) {
                message.append("\nLỗi cập nhật:\n");
                int maxErrors = Math.min(updateErrors.size(), 10);
                for (int i = 0; i < maxErrors; i++) {
                    message.append("  • ").append(updateErrors.get(i)).append("\n");
                }
                if (updateErrors.size() > 10) {
                    message.append("  • ... và ").append(updateErrors.size() - 10).append(" lỗi khác\n");
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

            // Hiển thị kết quả trong dialog
            JOptionPane.showMessageDialog(mainFrame, message.toString(), "Kết quả Import Điểm Chứng Chỉ", JOptionPane.INFORMATION_MESSAGE);

            // Refresh bảng dữ liệu
            if (updatedCount > 0) {
                parentPanel.loadDataTable(diemCongBUS.getAllDiemCong());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Lỗi đọc file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Lỗi xử lý file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                // Bỏ qua lỗi khi đóng file
            }
        }
    }

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
}
