/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtDiemCongXetTuyenDAO;
import ENTITY.XtDiemCongXetTuyen;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Windows
 */
public class XtDiemCongXetTuyenBUS {

    private final XtDiemCongXetTuyenDAO xtdiemcongxettuyenDAO = XtDiemCongXetTuyenDAO.getInstance();
    private List<XtDiemCongXetTuyen> listDiemCong;

    public XtDiemCongXetTuyenBUS() {
        listDiemCong = xtdiemcongxettuyenDAO.getAll();
        if (listDiemCong == null) {
            listDiemCong = new ArrayList<>();
        }
    }

    public List<XtDiemCongXetTuyen> getAllDiemCong() {
        return listDiemCong;
    }

    public List<XtDiemCongXetTuyen> searchDiemCong(String type, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listDiemCong;
        }

        String lowerKeyword = keyword.trim().toLowerCase();

        switch (type) {
            case "Mã":
                return listDiemCong.stream()
                        .filter(dc -> String.valueOf(dc.getIdDiemCong()).contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "CCCD":
                return listDiemCong.stream()
                        .filter(dc -> dc.getTsCccd() != null && dc.getTsCccd().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Mã ngành":
                return listDiemCong.stream()
                        .filter(dc -> dc.getMaNganh() != null && dc.getMaNganh().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Mã tổ hợp":
                return listDiemCong.stream()
                        .filter(dc -> dc.getMaToHop() != null && dc.getMaToHop().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Phương thức":
                return listDiemCong.stream()
                        .filter(dc -> dc.getPhuongThuc() != null && dc.getPhuongThuc().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tất cả":
            default:
                return listDiemCong.stream()
                        .filter(dc
                                -> (dc.getTsCccd() != null && dc.getTsCccd().toLowerCase().contains(lowerKeyword))
                        || (dc.getMaNganh() != null && dc.getMaNganh().toLowerCase().contains(lowerKeyword))
                        || (dc.getMaToHop() != null && dc.getMaToHop().toLowerCase().contains(lowerKeyword))
                        || (dc.getPhuongThuc() != null && dc.getPhuongThuc().toLowerCase().contains(lowerKeyword))
                        || (dc.getGhiChu() != null && dc.getGhiChu().toLowerCase().contains(lowerKeyword))
                        || String.valueOf(dc.getIdDiemCong()).contains(lowerKeyword)
                        )
                        .collect(Collectors.toList());
        }
    }

    public boolean addDiemCong(XtDiemCongXetTuyen dc) {
        validateDiemCong(dc);
        if (xtdiemcongxettuyenDAO.insert(dc)) {
            listDiemCong.add(dc);
            return true;
        }
        return false;
    }

    public boolean updateDiemCong(XtDiemCongXetTuyen dc) {
        validateDiemCong(dc);
        if (xtdiemcongxettuyenDAO.update(dc)) {
            for (int i = 0; i < listDiemCong.size(); i++) {
                if (listDiemCong.get(i).getIdDiemCong() == dc.getIdDiemCong()) {
                    listDiemCong.set(i, dc);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean deleteDiemCong(int idDiemCong) {
        if (xtdiemcongxettuyenDAO.delete(idDiemCong)) {
            listDiemCong.removeIf(dc -> dc.getIdDiemCong() == idDiemCong);
            return true;
        }
        return false;
    }

    public void validateDiemCong(XtDiemCongXetTuyen dc) throws IllegalArgumentException {
        if (dc.getTsCccd() == null || dc.getTsCccd().trim().isEmpty()) {
            throw new IllegalArgumentException("CCCD thí sinh không được để trống.");
        }
        if (dc.getMaNganh() == null || dc.getMaNganh().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã ngành không được để trống.");
        }
        if (dc.getMaToHop() == null || dc.getMaToHop().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tổ hợp không được để trống.");
        }
        if (dc.getPhuongThuc() == null || dc.getPhuongThuc().trim().isEmpty()) {
            throw new IllegalArgumentException("Phương thức xét tuyển không được để trống.");
        }

        // Điểm không âm
        if (dc.getDiemCC() != null && dc.getDiemCC().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Điểm CC không được là số âm.");
        }
        if (dc.getDiemUtxt() != null && dc.getDiemUtxt().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Điểm UTXT không được là số âm.");
        }

        // Tổng điểm cộng kh vượt quá 3
        BigDecimal diemCC = dc.getDiemCC() != null ? dc.getDiemCC() : BigDecimal.ZERO;
        BigDecimal diemUtxt = dc.getDiemUtxt() != null ? dc.getDiemUtxt() : BigDecimal.ZERO;
        BigDecimal tongDiem = diemCC.add(diemUtxt);

        if (tongDiem.compareTo(new BigDecimal("3")) > 0) {
            throw new IllegalArgumentException("Tổng điểm cộng không được vượt quá 3.0");
        }

        // Check Key trùng lặp
        String currentKey = (dc.getTsCccd() + "_" + dc.getMaNganh() + "_" + dc.getMaToHop()).toLowerCase();

        boolean isDuplicate = listDiemCong.stream().anyMatch(existing -> {
            String existingKey = (existing.getTsCccd() + "_" + existing.getMaNganh() + "_" + existing.getMaToHop()).toLowerCase();
            return existingKey.equals(currentKey) && existing.getIdDiemCong() != dc.getIdDiemCong();
        });

        if (isDuplicate) {
            throw new IllegalArgumentException("Dữ liệu xét tuyển cho thí sinh này với ngành và tổ hợp này đã tồn tại.");
        }
    }
}
