package com.quanlytuyensinh.UTIL;

import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ExcelImportUtil {

    public static List<XtThisinhXetTuyen25> readThiSinhFromExcel(String excelFilePath) {
        List<XtThisinhXetTuyen25> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return list;

            // Bắt đầu từ row 1 (row 0 là header)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                XtThisinhXetTuyen25 ts = new XtThisinhXetTuyen25();
                
                try {
                    // Mapping cột theo Excel của bạn
                    ts.setCccd(getStringValue(row.getCell(1)));           // CCCD
                    
                    String hoTen = getStringValue(row.getCell(2));        // Họ Tên
                    splitHoTen(hoTen, ts);                                // Tách Họ - Tên
                    
                    String ngaySinh = getStringValue(row.getCell(3));
                    ts.setNgaySinh(ngaySinh);       // Ngày sinh
                   ts.setGioiTinh(normalizeGioiTinh(getStringValue(row.getCell(4))));       // Giới tính

                    ts.setDoiTuong(getStringValue(row.getCell(5)));       // ĐTƯT (Đối tượng)
                    ts.setKhuVuc(getStringValue(row.getCell(6)));         // KVƯT (Khu vực)

                    ts.setNoiSinh(getStringValue(row.getCell(35)));       // Nơi sinh (cột cuối)

                    // Các trường mặc định
                    ts.setDienThoai(null);
                    ts.setEmail(null);
                    ts.setPassword(ngaySinh.replace("/", ""));          // Mật khẩu mặc định
                    ts.setUpdatedAt(LocalDate.now());

                    list.add(ts);

                } catch (Exception e) {
                    System.out.println("Lỗi dòng " + (i+1) + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String getStringValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new java.text.SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                }
                return String.valueOf((long) cell.getNumericCellValue());
            }
            return cell.getStringCellValue().trim();
        } catch (Exception e) {
            return null;
        }
    }

private static void splitHoTen(String hoTen, XtThisinhXetTuyen25 ts) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            ts.setHo("");
            ts.setTen("");
            return;
        }
        hoTen = hoTen.trim();
        int lastSpace = hoTen.lastIndexOf(" ");
        if (lastSpace > 0) {
            ts.setHo(hoTen.substring(0, lastSpace).trim());
            ts.setTen(hoTen.substring(lastSpace + 1).trim());
        } else {
            ts.setHo(hoTen);
            ts.setTen("");
        }
    }

    // Thêm vào sau khi setGioiTinh trong readThiSinhFromExcel
    private static String normalizeGioiTinh(String gt) {
        if (gt == null) return null;
        gt = gt.trim().toLowerCase();
        if (gt.equals("nữ") || gt.equals("female") || gt.equals("f")) {
            return "Nữ";
        }
        if (gt.equals("nam") || gt.equals("male") || gt.equals("m")) {
            return "Nam";
        }
        return gt; // giữ nguyên nếu không match
    }
    /**
 * Đọc file Excel nguyện vọng.
 * Cột bắt buộc (bỏ qua header dòng đầu):
 *   Col 1 = CCCD, Col 2 = Thứ tự NV, Col 5 = Mã xét tuyển (mã ngành)
 * Trả về List<String[]> với mỗi phần tử: [cccd, thuTu, maNganh]
 */
public static List<String[]> readNguyenVongExcel(File file) throws Exception {
    List<String[]> result = new ArrayList<>();
    try (org.apache.poi.ss.usermodel.Workbook wb =
             org.apache.poi.ss.usermodel.WorkbookFactory.create(file)) {

        org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
        boolean isFirstRow = true;

        for (org.apache.poi.ss.usermodel.Row row : sheet) {
            if (isFirstRow) { isFirstRow = false; continue; } // Bỏ header
            if (row == null) continue;

            String cccd     = getCellString(row.getCell(1)); // Cột B
            String thuTu    = getCellString(row.getCell(2)); // Cột C
            String maNganh  = getCellString(row.getCell(5)); // Cột F (Mã xét tuyển)

            if (cccd.isEmpty() && thuTu.isEmpty() && maNganh.isEmpty()) continue; // dòng trống

            result.add(new String[]{cccd, thuTu, maNganh});
        }
    }
    return result;
}

private static String getCellString(org.apache.poi.ss.usermodel.Cell cell) {
    if (cell == null) return "";
    switch (cell.getCellType()) {
        case STRING:  return cell.getStringCellValue().trim();
        case NUMERIC: return String.valueOf((long) cell.getNumericCellValue());
        default:      return "";
    }
}
}