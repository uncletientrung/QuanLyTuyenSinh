package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JOptionPane;

public class XtNguyenVongXetTuyenBUS {
    private final XtNguyenVongXetTuyenDAO nvDAO = XtNguyenVongXetTuyenDAO.getInstance();
    private List<XtNguyenVongXetTuyen> listNV;
    private XtNganhBUS NganhBUS;
    
    public XtNguyenVongXetTuyenBUS() {

        listNV = nvDAO.getAll();
    }
    public List<XtNguyenVongXetTuyen> getAllNguyenVong() {
        return listNV;
    }
    
    public List<XtNguyenVongXetTuyen> getListNVByCCCD(String cccd){
        List<XtNguyenVongXetTuyen> rs = new ArrayList<>();
        rs = nvDAO.findByCCCD(cccd);
        return rs;
    }
    
    public boolean insertNguyenVong(XtNguyenVongXetTuyen nvNew){
        if(nvNew== null) return false;
        boolean rs = nvDAO.insert(nvNew);
        if(rs){
            listNV.add(nvNew);
            return true;
        }
        return false;
    }
    
    public boolean updateNguyenVong(XtNguyenVongXetTuyen nvUpdate){
        if (nvUpdate == null) return false;
        boolean rs = nvDAO.update(nvUpdate);
        if(rs){
            for (XtNguyenVongXetTuyen nv : listNV) {
                if (nvUpdate.getIdnv()== nv.getIdnv()) {
                    nv.setNnCccd(nvUpdate.getNnCccd());
                    nv.setNvManganh(nvUpdate.getNvManganh());
                    nv.setNvTt(nvUpdate.getNvTt());

                    nv.setDiemThxt(nvUpdate.getDiemThxt());
                    nv.setDiemUtqd(nvUpdate.getDiemUtqd());
                    nv.setDiemCong(nvUpdate.getDiemCong());
                    nv.setDiemXettuyen(nvUpdate.getDiemXettuyen());

                    nv.setNvKetqua(nvUpdate.getNvKetqua());
                    nv.setNvKeys(nvUpdate.getNvKeys());
                    nv.setTtPhuongthuc(nvUpdate.getTtPhuongthuc());
                    nv.setTtThm(nvUpdate.getTtThm());
                    break;
                }
            }
            return true;
        }
        return false;
    }
    public boolean deleteNguyenVong(int idNV){
        if(idNV <=0) return false;
        boolean rs = nvDAO.delete(idNV);
        if (rs) {
            listNV.removeIf(nv -> nv.getIdnv()== idNV);
            return true;
        }
        return false;  
    }
    public boolean approveNguyenVong(XtNguyenVongXetTuyen nvXetTuyen, BigDecimal diemTT){
        if(nvXetTuyen == null || diemTT == null) return false;
        boolean rs= false;
        String ketQuaStr = nvXetTuyen.getDiemXettuyen().compareTo(diemTT) >= 0 ? "Trúng tuyển"  : "Rớt trúng tuyển";
        rs = nvDAO.approve(nvXetTuyen.getIdnv(), ketQuaStr);
        if(rs){
            for(XtNguyenVongXetTuyen nv : listNV){
                if(nv.getIdnv() == nvXetTuyen.getIdnv()){
                    nv.setNvKetqua(ketQuaStr);
                    return true;
                }
            }
        }
        return false;
    }
        public boolean approveAllNguyenVong(XtNganhBUS nganhBUS){
            if(nganhBUS == null ) return false;
            this.NganhBUS = nganhBUS;
            boolean rs= false;
            
            rs = nvDAO.approveAll();
            if(rs){
                for(XtNguyenVongXetTuyen nv : listNV){
                    
                    BigDecimal diemTT = NganhBUS.getDiemTTByMaNganhBUS(nv.getNvManganh()); 
                    
                    if (diemTT == null) {
                        nv.setNvKetqua("Đang xét");
                        continue;
                    }
                    if (nv.getDiemXettuyen() == null) {
                        nv.setNvKetqua("Chưa có điểm");
                        continue;
                    }
                    if (nv.getDiemXettuyen().compareTo(diemTT) >= 0) {
                        nv.setNvKetqua("Trúng tuyển");
                    } else {
                        nv.setNvKetqua("Không trúng tuyển");
                    }
                }
                return true;
            }
            return false;
        }
    public boolean undoNguyenVong(XtNguyenVongXetTuyen nvXetTuyen){
        if(nvXetTuyen == null) return false;
        boolean rs= false;
        String ketQuaStr = "Đang xét";
        rs = nvDAO.approve(nvXetTuyen.getIdnv(), ketQuaStr); // Tái sử dụng
        if(rs){
            for(XtNguyenVongXetTuyen nv : listNV){
                if(nv.getIdnv() == nvXetTuyen.getIdnv()){
                    nv.setNvKetqua(ketQuaStr);
                    return true;
                }
            }
        }
        return false;
    }
    
   

    public List<XtNguyenVongXetTuyen> searchNguyenVong(String keyword, String searchType) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listNV;
        }
        String key = keyword.trim().toLowerCase();
        List<XtNguyenVongXetTuyen> result = new ArrayList<>();
        for (XtNguyenVongXetTuyen nv : listNV) {
            switch (searchType) {
                case "Mã":
                    if (String.valueOf(nv.getIdnv()).contains(key)) {
                        result.add(nv);
                    }
                    break;

                case "CCCD":
                    if (nv.getNnCccd() != null &&
                        nv.getNnCccd().toLowerCase().contains(key)) {
                        result.add(nv);
                    }
                    break;

                case "Mã ngành":
                    if (nv.getNvManganh() != null &&
                        nv.getNvManganh().toLowerCase().contains(key)) {
                        result.add(nv);
                    }
                    break;

                case "Phương thức":
                    if (nv.getTtPhuongthuc() != null &&
                        nv.getTtPhuongthuc().toLowerCase().contains(key)) {
                        result.add(nv);
                    }
                    break;

                case "Tổ hợp":
                    if (nv.getTtThm() != null &&
                        nv.getTtThm().toLowerCase().contains(key)) {
                        result.add(nv);
                    }
                    break;

                default: // Tất cả
                    if (nv.toString().toLowerCase().contains(key)) {
                        result.add(nv);
                    }
                    break;
            }
        }

        return result;
    }
}