package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNganhDAO;
import com.quanlytuyensinh.ENTITY.XtNganh;
import java.math.BigDecimal;
import java.util.List;

public class XtNganhBUS {

    private final XtNganhDAO nganhDAO;
    private List<XtNganh> listNganh;

    public XtNganhBUS() {
        this.nganhDAO  = XtNganhDAO.getInstance();
        this.listNganh = nganhDAO.getAll();
    }



    public List<XtNganh> getAllNganh() {
        listNganh = nganhDAO.getAll();
        return listNganh;
    }

    public XtNganh getNganhById(int id) {
        return nganhDAO.getNganhById(id);
    }



    public boolean insertNganh(XtNganh nganh) {
        try {
            validateNganh(nganh, true);
        } catch (IllegalArgumentException ex) {
            throw ex; 
        }

        if (nganhDAO.insert(nganh)) {
            listNganh.add(nganh);
            return true;
        }
        return false;
    }


    public boolean updateNganh(XtNganh nganh) {
        try {
            validateNganh(nganh, false);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }

        if (nganhDAO.update(nganh)) {
            for (int i = 0; i < listNganh.size(); i++) {
                if (listNganh.get(i).getIdnganh() == nganh.getIdnganh()) {
                    listNganh.set(i, nganh);
                    break;
                }
            }
            return true;
        }
        return false;
    }

   

    public boolean deleteNganh(int idnganh) {
        if (idnganh <= 0) return false;

        if (nganhDAO.delete(idnganh)) {
            listNganh.removeIf(ng -> ng.getIdnganh() == idnganh);
            return true;
        }
        return false;
    }



    public List<XtNganh> searchNganh(String keyword, String searchType) {
        List<XtNganh> all = nganhDAO.getAll();

        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }

        String kw = keyword.trim().toLowerCase();

        return all.stream()
            .filter(ng -> switch (searchType) {
                case "Mã"          -> ng.getManganh()   != null && ng.getManganh().toLowerCase().contains(kw);
                case "Tên ngành"   -> ng.getTennganh()  != null && ng.getTennganh().toLowerCase().contains(kw);
                case "Tổ hợp gốc" -> ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(kw);
                default ->
                    (ng.getManganh()   != null && ng.getManganh().toLowerCase().contains(kw))   ||
                    (ng.getTennganh()  != null && ng.getTennganh().toLowerCase().contains(kw))  ||
                    (ng.getNTohopgoc() != null && ng.getNTohopgoc().toLowerCase().contains(kw));
            })
            .toList();
    }

  
    private void validateNganh(XtNganh nganh, boolean isInsert) throws IllegalArgumentException {
        if (nganh == null) {
            throw new IllegalArgumentException("Dữ liệu ngành không được để trống!");
        }
        if (!isInsert && nganh.getIdnganh() <= 0) {
            throw new IllegalArgumentException("ID ngành không hợp lệ!");
        }

        if (nganh.getManganh() == null || nganh.getManganh().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã ngành không được để trống!");
        }
        if (nganh.getManganh().length() > 45) {
            throw new IllegalArgumentException("Mã ngành không được vượt quá 45 ký tự!");
        }
        if (nganhDAO.checkTrungMaNganh(nganh.getManganh(), isInsert ? 0 : nganh.getIdnganh())) {
            throw new IllegalArgumentException("Mã ngành '" + nganh.getManganh() + "' đã tồn tại!");
        }

        if (nganh.getTennganh() == null || nganh.getTennganh().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên ngành không được để trống!");
        }
        if (nganh.getTennganh().length() > 100) {
            throw new IllegalArgumentException("Tên ngành không được vượt quá 100 ký tự!");
        }

        if (nganh.getNTohopgoc() != null && nganh.getNTohopgoc().length() > 3) {
            throw new IllegalArgumentException("Tổ hợp gốc không hợp lệ (VD: A00, D01...)!");
        }

        if (nganh.getNChitieu() <= 0) {
            throw new IllegalArgumentException("Chỉ tiêu phải lớn hơn 0!");
        }

        if (nganh.getNDiemsan() != null && nganh.getNDiemsan().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Điểm sàn không được là số âm!");
        }
        if (nganh.getNDiemtrungtuyen() != null && nganh.getNDiemtrungtuyen().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Điểm trúng tuyển không được là số âm!");
        }
        if (nganh.getNDiemsan() != null && nganh.getNDiemtrungtuyen() != null &&
            nganh.getNDiemtrungtuyen().compareTo(nganh.getNDiemsan()) < 0) {
            throw new IllegalArgumentException("Điểm trúng tuyển không được nhỏ hơn điểm sàn!");
        }

        if (!isValidFlag(nganh.getNTuyenthang())) {
            throw new IllegalArgumentException("Vui lòng chọn trạng thái 'Tuyển thẳng' (Có/Không)!");
        }
        if (!isValidFlag(nganh.getNDgnl())) {
            throw new IllegalArgumentException("Vui lòng chọn trạng thái 'ĐGNL' (Có/Không)!");
        }
        if (!isValidFlag(nganh.getNThpt())) {
            throw new IllegalArgumentException("Vui lòng chọn trạng thái 'THPT' (Có/Không)!");
        }
        if (!isValidFlag(nganh.getNVsat())) {
            throw new IllegalArgumentException("Vui lòng chọn trạng thái 'VSAT' (Có/Không)!");
        }

        if (nganh.getSlXtt() != null && nganh.getSlXtt() < 0) {
            throw new IllegalArgumentException("Số lượng Xét tuyển thẳng phải >= 0!");
        }
        if (nganh.getSlDgnl() != null && nganh.getSlDgnl() < 0) {
            throw new IllegalArgumentException("Số lượng ĐGNL phải >= 0!");
        }
        if (nganh.getSlVsat() != null && nganh.getSlVsat() < 0) {
            throw new IllegalArgumentException("Số lượng VSAT phải >= 0!");
        }
        if (nganh.getSlThpt() != null && nganh.getSlThpt() < 0) {
            throw new IllegalArgumentException("Số lượng THPT phải >= 0!");
        }
    }

    private boolean isValidFlag(String flag) {
        if (flag == null || flag.trim().isEmpty()) return false;
        return flag.trim().equals("1") || flag.trim().equals("0");
    }
}