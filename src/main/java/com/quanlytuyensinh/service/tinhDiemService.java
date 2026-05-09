/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.service;

import com.quanlytuyensinh.DAO.XtBangQuyDoiDAO;
import com.quanlytuyensinh.DAO.XtDiemCongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtDiemThiXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtNganhDAO;
import com.quanlytuyensinh.DAO.XtNganhToHopDAO;
import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.DAO.XtToHopMonThiDAO;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@Service
public class tinhDiemService {
    private final XtDiemThiXetTuyenDAO diemDAO = XtDiemThiXetTuyenDAO.getInstance(); // Điểm thi
    private List<XtDiemThiXetTuyen> listDiem;
    private final XtNganhDAO nganhDAO = XtNganhDAO.getInstance(); // Ngành
    private List<XtNganh> listNganh;
    private final XtNguyenVongXetTuyenDAO nvDAO = XtNguyenVongXetTuyenDAO.getInstance(); // Nguyện vọng
    private List<XtNguyenVongXetTuyen> listNV;
    private final XtNganhToHopDAO nganhToHopDAO = XtNganhToHopDAO.getInstance();  // Ngành tổ hợp
    private List<XtNganhToHop> listNTH;
    private final XtThisinhXetTuyen25DAO TSDAO = XtThisinhXetTuyen25DAO.getInstance(); // Thí sinh
    private List<XtThisinhXetTuyen25> listThiSinh;
    private final XtDiemCongXetTuyenDAO diemCongDAO = XtDiemCongXetTuyenDAO.getInstance(); // Điểm cộng
    private List<XtDiemCongXetTuyen> listDiemCong;
    private final XtBangQuyDoiDAO xtbangquydoiDAO = XtBangQuyDoiDAO.getInstance(); // Bảng quy đổi
    private List<XtBangQuyDoi> listQuyDoi;
    
    public tinhDiemService(){
        this.listDiem = this.diemDAO.getAll();
        this.listDiemCong = this.diemCongDAO.getAll();
        this.listNganh = this.nganhDAO.getAll();
        this.listNTH = this.nganhToHopDAO.getAll();
        this.listQuyDoi = this.xtbangquydoiDAO.getAll();
        this.listNV = this.nvDAO.getAll();
        this.listThiSinh = this.TSDAO.getAll();
    }
    
    public List<XtNganh> getListNganh(){
        return listNganh;
    }
    
    public BigDecimal DiemUuTienDoiTuong(String dt){
        if (dt == null || dt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal diemUT = BigDecimal.ZERO;
        // Tính điểm đối tượng
        if (dt != null) {
            if (dt.equals("01") || dt.equals("02") || dt.equals("03") || dt.equals("04") || dt.equals("05")) { // UT1
                diemUT = diemUT.add(new BigDecimal("2.0"));
            } else if (dt.startsWith("06") || dt.startsWith("07") ) { // UT2
                diemUT = diemUT.add(new BigDecimal("1.0")); 
            }
        }
        return diemUT;
    }
    public BigDecimal DiemUuTienKhuVuc(String KhuVuc){
        if (KhuVuc == null || KhuVuc.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal diemUT = BigDecimal.ZERO;
        // Tính điểm khu vực
        if (KhuVuc != null) {
            switch (KhuVuc) {
                case "1":
                    diemUT = diemUT.add(new BigDecimal("0.75"));
                    break;
                case "2NT":
                    diemUT = diemUT.add(new BigDecimal("0.50"));
                    break;
                case "2":
                    diemUT = diemUT.add(new BigDecimal("0.25"));
                    break;
            }
        }
        return diemUT;
    }
    
    public BigDecimal getMucDiemUuTienTheoQuyDinh(String kv, String dt){ // Trả về mức điểm ưu tiên
        BigDecimal diemUT = BigDecimal.ZERO;
        diemUT = diemUT.add(this.DiemUuTienDoiTuong(dt));
        diemUT = diemUT.add(this.DiemUuTienKhuVuc(kv));
        return diemUT;
   }
    
    
    public BigDecimal getDiemUuTien( String kv, String dt, BigDecimal diemTHXT, BigDecimal diemCong ) { // Tính điểm ưu tiên
        if (kv == null || kv.isEmpty() || dt == null || dt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        if(diemTHXT.add(diemCong).compareTo(new BigDecimal("30")) >= 0) return  BigDecimal.ZERO; // Nếu điểm >= 30 thì điểm ưu tiên là 0
        BigDecimal MucDiemUuTien = this.getMucDiemUuTienTheoQuyDinh(kv, dt);

        // Tính tổng điểm ưu tiên
        BigDecimal tong = diemTHXT.add(diemCong);
        if (tong.compareTo(new BigDecimal("22.5")) < 0){ // Nếu bé hơn 22.5
            return MucDiemUuTien;
        }

        BigDecimal heSo = new BigDecimal("30")
                .subtract(tong)
                .divide(new BigDecimal("7.5"), 5, RoundingMode.HALF_UP);
        return heSo.multiply(MucDiemUuTien).setScale(5, RoundingMode.HALF_UP);
    }
    
    // Tính điểm xét ngưỡng
    
    
    
    
       
       
    
    
}
