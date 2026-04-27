/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtBangQuyDoiDAO;
import ENTITY.XtBangQuyDoi;
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
        if (xtbangquydoiDAO.insert(qd)) {
            listQuyDoi.add(qd);
            return true;
        }
        return false;
    }
}
