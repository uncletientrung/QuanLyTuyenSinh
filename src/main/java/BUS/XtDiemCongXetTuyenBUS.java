/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtDiemCongXetTuyenDAO;
import ENTITY.XtDiemCongXetTuyen;
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
        if (xtdiemcongxettuyenDAO.insert(dc)) {
            listDiemCong.add(dc);
            return true;
        }
        return false;
    }

    public boolean updateDiemCong(XtDiemCongXetTuyen dc) {
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
}
