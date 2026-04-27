/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtBangQuyDoiDAO;
import ENTITY.XtBangQuyDoi;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Windows
 */
public class XtBangQuyDoiBUS {

    private final XtBangQuyDoiDAO xtbangquydoiDAO = XtBangQuyDoiDAO.getInstance();
    private List<XtBangQuyDoi> listQuyDoi;

    public XtBangQuyDoiBUS() {
        listQuyDoi = xtbangquydoiDAO.getAll();
        if (listQuyDoi == null) {
            listQuyDoi = new ArrayList<>();
        }
    }

    public List<XtBangQuyDoi> getAllQuyDoi() {
        return listQuyDoi;
    }

    public List<XtBangQuyDoi> searchQuyDoi(String type, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listQuyDoi;
        }

        String lowerKeyword = keyword.trim().toLowerCase();

        switch (type) {
            case "Mã":
                return listQuyDoi.stream()
                        .filter(qd -> String.valueOf(qd.getIdqd()).contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Phương thức":
                return listQuyDoi.stream()
                        .filter(qd -> qd.getDPhuongthuc() != null
                        && qd.getDPhuongthuc().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tổ hợp":
                return listQuyDoi.stream()
                        .filter(qd -> qd.getDTohop() != null
                        && qd.getDTohop().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Môn":
                return listQuyDoi.stream()
                        .filter(qd -> qd.getDMon() != null
                        && qd.getDMon().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Mã quy đổi":
                return listQuyDoi.stream()
                        .filter(qd -> qd.getDMaQuyDoi() != null
                        && qd.getDMaQuyDoi().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Phân vị":
                return listQuyDoi.stream()
                        .filter(qd -> qd.getDPhanvi() != null
                        && qd.getDPhanvi().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tất cả":
            default:
                return listQuyDoi.stream()
                        .filter(qd
                                -> (qd.getDPhuongthuc() != null && qd.getDPhuongthuc().toLowerCase().contains(lowerKeyword))
                        || (qd.getDTohop() != null && qd.getDTohop().toLowerCase().contains(lowerKeyword))
                        || (qd.getDMon() != null && qd.getDMon().toLowerCase().contains(lowerKeyword))
                        || (qd.getDMaQuyDoi() != null && qd.getDMaQuyDoi().toLowerCase().contains(lowerKeyword))
                        || (qd.getDPhanvi() != null && qd.getDPhanvi().toLowerCase().contains(lowerKeyword))
                        || String.valueOf(qd.getIdqd()).contains(lowerKeyword)
                        )
                        .collect(Collectors.toList());
        }
    }

    public boolean addQuyDoi(XtBangQuyDoi qd) {
        validateQuyDoi(qd);
        if (xtbangquydoiDAO.insert(qd)) {
            listQuyDoi.add(qd);
            return true;
        }
        return false;
    }

    public boolean updateQuyDoi(XtBangQuyDoi qd) {
        validateQuyDoi(qd);
        if (xtbangquydoiDAO.update(qd)) {
            for (int i = 0; i <= listQuyDoi.size(); i++) {
                if (listQuyDoi.get(i).getIdqd() == qd.getIdqd()) {
                    listQuyDoi.set(i, qd);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean deleteQuyDoi(int idqd) {
        if (xtbangquydoiDAO.delete(idqd)) {
            listQuyDoi.removeIf(qd -> qd.getIdqd() == idqd);
            return true;
        }
        return false;
    }

    // Validate
    public void validateQuyDoi(XtBangQuyDoi qd) throws IllegalArgumentException {
        // Kiểm tra Môn / Tổ hợp dựa theo Phương thức
        if ("VSAT".equals(qd.getDPhuongthuc())) {
            if (qd.getDMon() == null || qd.getDMon().trim().isEmpty()) {
                throw new IllegalArgumentException("Môn không được để trống đối với phương thức VSAT.");
            }
        } else { // DGNL
            if (qd.getDTohop() == null || qd.getDTohop().trim().isEmpty()) {
                throw new IllegalArgumentException("Tổ hợp không được để trống đối với phương thức " + qd.getDPhuongthuc() + ".");
            }
        }

        // Kiểm tra tính hợp lệ của Điểm
        if (qd.getDDiema() == null || qd.getDDiemb() == null || qd.getDDiemc() == null || qd.getDDiemd() == null) {
            throw new IllegalArgumentException("Các trường điểm số không được để trống.");
        }

        if (qd.getDDiema().compareTo(BigDecimal.ZERO) < 0 || qd.getDDiemc().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Điểm số không được là số âm.");
        }

        if (qd.getDDiemb().compareTo(qd.getDDiema()) < 0) {
            throw new IllegalArgumentException("Điểm cao nhất (B) phải lớn hơn hoặc bằng điểm thấp nhất (A).");
        }

        if (qd.getDDiemd().compareTo(qd.getDDiemc()) < 0) {
            throw new IllegalArgumentException("Điểm cao nhất THPT (D) phải lớn hơn hoặc bằng điểm thấp nhất THPT (C).");
        }

        // Kiểm tra phân vị và mã quy đổi
        if (qd.getDPhanvi() == null || qd.getDPhanvi().trim().isEmpty()) {
            throw new IllegalArgumentException("Phân vị không được để trống.");
        }
        if (qd.getDMaQuyDoi() == null || qd.getDMaQuyDoi().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã quy đổi không được để trống.");
        }

        // Kiểm tra trùng lặp Mã quy đổi
        boolean isDuplicate = listQuyDoi.stream().anyMatch(existing
                -> existing.getDMaQuyDoi().equalsIgnoreCase(qd.getDMaQuyDoi()) && existing.getIdqd() != qd.getIdqd()
        );
        if (isDuplicate) {
            throw new IllegalArgumentException("Mã quy đổi '" + qd.getDMaQuyDoi() + "' đã tồn tại trong hệ thống.");
        }
    }
}
