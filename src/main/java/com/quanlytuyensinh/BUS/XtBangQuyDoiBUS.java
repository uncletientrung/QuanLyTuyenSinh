/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtBangQuyDoiDAO;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

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
        listQuyDoi = xtbangquydoiDAO.getAll();
        if (listQuyDoi == null) {
            listQuyDoi = new ArrayList<>();
        }
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
            for (int i = 0; i < listQuyDoi.size(); i++) {
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
        public XtBangQuyDoi getBQDByPhuongThucVaMonVaDiem(String phuongThuc, String mon, BigDecimal Diem){
            return xtbangquydoiDAO.getBQDVSATByPhuongThucVaMonVaDiemDAO(phuongThuc, mon, Diem);
        }
        public BigDecimal CongThucQuyDoiVSAT(String mon, BigDecimal Diem){
            XtBangQuyDoi bangQuyDoi = getBQDByPhuongThucVaMonVaDiem("VSAT", mon, Diem);
            if(bangQuyDoi == null){
                JOptionPane.showMessageDialog(null, "THANG ĐIỂM NẰM NGOÀI QUY ĐỔI (MẶC ĐỊNH = 10 )", "DEBUG DIEM", JOptionPane.INFORMATION_MESSAGE);
                return new BigDecimal("10");
            }
            BigDecimal  a= bangQuyDoi.getDDiema();
            BigDecimal  b= bangQuyDoi.getDDiemb();
            BigDecimal  c= bangQuyDoi.getDDiemc();
            BigDecimal  d= bangQuyDoi.getDDiemd();
            BigDecimal  x= Diem;
            BigDecimal y = c.add(
                (x.subtract(a))
                .divide(b.subtract(a), 5, RoundingMode.HALF_UP)
                .multiply(d.subtract(c))
            );

            return y.setScale(5, RoundingMode.HALF_UP);
        }
        public XtDiemThiXetTuyen getDiemThiVSATQuyDoi(XtDiemThiXetTuyen diemThiVSAT){
            XtDiemThiXetTuyen diemThiVSATQuyDoi = new XtDiemThiXetTuyen();
            if(diemThiVSAT == null) return null;
            diemThiVSATQuyDoi.setCccd(diemThiVSAT.getCccd());
            // Quy đổi Toán
            if(diemThiVSAT.getTo() != null && diemThiVSAT.getTo().compareTo(BigDecimal.ZERO) != 0){

                diemThiVSATQuyDoi.setTo(CongThucQuyDoiVSAT("TO", diemThiVSAT.getTo()));
            }
              // Lý
            if(diemThiVSAT.getLi() != null && diemThiVSAT.getLi().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setLi(CongThucQuyDoiVSAT("LI", diemThiVSAT.getLi()));
            }

            // Hóa
            if(diemThiVSAT.getHo() != null && diemThiVSAT.getHo().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setHo(CongThucQuyDoiVSAT("HO", diemThiVSAT.getHo()));
            }

            // Sinh
            if(diemThiVSAT.getSi() != null && diemThiVSAT.getSi().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setSi(CongThucQuyDoiVSAT("SI", diemThiVSAT.getSi()));
            }

            // Sử
            if(diemThiVSAT.getSu() != null && diemThiVSAT.getSu().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setSu(CongThucQuyDoiVSAT("SU", diemThiVSAT.getSu()));
            }

            // Địa
            if(diemThiVSAT.getDi() != null && diemThiVSAT.getDi().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setDi(CongThucQuyDoiVSAT("DI", diemThiVSAT.getDi()));
            }

            // Văn
            if(diemThiVSAT.getVa()!= null && diemThiVSAT.getVa().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setVa(CongThucQuyDoiVSAT("VA", diemThiVSAT.getVa()));
            }

            // Anh Văn
            if(diemThiVSAT.getN1Thi()!= null && diemThiVSAT.getN1Thi().compareTo(BigDecimal.ZERO) != 0){
                diemThiVSATQuyDoi.setN1Thi(CongThucQuyDoiVSAT("N1", diemThiVSAT.getN1Thi()));
            }
            return diemThiVSATQuyDoi;
        }
        
        
        // ĐÁNH GIÁ NĂNG LỰC
        
        // Lấy công thức DGNL
        public BigDecimal CongThucQuyDoiDGNL(String MaToHop, BigDecimal Diem){
            XtBangQuyDoi bangQuyDoi = this.xtbangquydoiDAO.getBQDDGNLByPhuongThucVaMonVaDiemDAO("DGNL",MaToHop, Diem);
            if(bangQuyDoi == null){
                JOptionPane.showMessageDialog(null, "THANG ĐIỂM NẰM NGOÀI QUY ĐỔI (MẶC ĐỊNH = 30 )", "DEBUG DIEM", JOptionPane.INFORMATION_MESSAGE);
                return new BigDecimal("30");
            }
            BigDecimal  a= bangQuyDoi.getDDiema();
            BigDecimal  b= bangQuyDoi.getDDiemb();
            BigDecimal  c= bangQuyDoi.getDDiemc();
            BigDecimal  d= bangQuyDoi.getDDiemd();
            BigDecimal  x= Diem;
            BigDecimal y = c.add(
                (x.subtract(a))
                .divide(b.subtract(a), 5, RoundingMode.HALF_UP)
                .multiply(d.subtract(c))
            );
            return y.setScale(5, RoundingMode.HALF_UP);
        }
        
        // Lấy điểm thi
        public BigDecimal getDiemThiDGNLQuyDoi(String MaToHop, BigDecimal diemThiDGNL){
            if(diemThiDGNL == null) return null;
            return CongThucQuyDoiDGNL(MaToHop, diemThiDGNL);
            
        }
}
