package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
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
    
    public boolean checkExistsNV(String cccd, String maNganh) {
        return nvDAO.kiemTraNVTonTai(cccd, maNganh);// tùy theo cách bạn implement
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
    public boolean approveNguyenVong(XtNganhBUS nganhBUS, XtNguyenVongXetTuyen nvDuocChon){
        if(nganhBUS == null ) return false;
        this.NganhBUS = nganhBUS;
        boolean rs= false;
        rs = nvDAO.approve(nvDuocChon);
        if(rs){
            if(nvDuocChon.getNvKetqua().equals("Trúng tuyển")){
                this.NganhBUS.TruSoLuongPhuongThucNganh(nvDuocChon.getTtPhuongthuc(), nvDuocChon.getNvManganh());
            }
            
            if(nvDuocChon.getTtPhuongthuc().equals("Tuyển thẳng")){
               nvDuocChon.setNvKetqua("Trúng tuyển");
               this.NganhBUS.TangSoLuongPhuongThucNganh("Tuyển thẳng", nvDuocChon.getNvManganh());
            }           
            BigDecimal diemTT = NganhBUS.getDiemTTByMaNganhBUS(nvDuocChon.getNvManganh()); 
            BigDecimal diemSan = NganhBUS.getDiemSanByMaNganhBUS(nvDuocChon.getNvManganh());
            if (nvDuocChon.getDiemXettuyen() == null) {
                nvDuocChon.setNvKetqua("Chưa có điểm");
            } else if (diemSan != null  && nvDuocChon.getDiemXettuyen().compareTo(diemSan) < 0) {
                nvDuocChon.setNvKetqua("Rớt điểm sàn");
            } else if (diemTT == null) {
                nvDuocChon.setNvKetqua("Đang xét");
            } else if (nvDuocChon.getDiemXettuyen().compareTo(diemTT) >= 0) {
                nvDuocChon.setNvKetqua("Trúng tuyển");
                this.NganhBUS.TangSoLuongPhuongThucNganh(nvDuocChon.getTtPhuongthuc(), nvDuocChon.getNvManganh());
            } else {
                nvDuocChon.setNvKetqua("Không trúng tuyển");
            }      
            
            return true;
        }
        return false;
    }
    public boolean approveAllNguyenVong(XtNganhBUS nganhBUS){
        if(nganhBUS == null ) return false;
        this.NganhBUS = nganhBUS;
        boolean rs= false;
        
        rs = nvDAO.approveAll();
        if(rs){
            this.NganhBUS.resetSoLuongPhuongThucNganh(); // Reset số lượng phương thức
            Map<String, BigDecimal> MapDiemTT = this.NganhBUS.getListHaveDiemTT(listNV);
            for(XtNguyenVongXetTuyen nv : listNV){
                if(nv.getTtPhuongthuc().equals("Tuyển thẳng")){
                    nv.setNvKetqua("Trúng tuyển");
                    this.NganhBUS.TangSoLuongPhuongThucNganh("Tuyển thẳng", nv.getNvManganh());
                }
                BigDecimal diemTT = MapDiemTT.get(nv.getNvManganh());
                BigDecimal diemSan = NganhBUS.getDiemSanByMaNganhBUS(nv.getNvManganh());
                if (nv.getDiemXettuyen() == null) {
                    nv.setNvKetqua("Chưa có điểm");
                } else if (diemSan != null  && nv.getDiemXettuyen().compareTo(diemSan) < 0) {
                    nv.setNvKetqua("Rớt điểm sàn");
                } else if (diemTT == null) {
                    nv.setNvKetqua("Đang xét");
                } else if (nv.getDiemXettuyen().compareTo(diemTT) >= 0) {
                    nv.setNvKetqua("Trúng tuyển");
                    this.NganhBUS.TangSoLuongPhuongThucNganh(nv.getTtPhuongthuc(), nv.getNvManganh());
                } else {
                    nv.setNvKetqua("Không trúng tuyển");
                }                   
            }
            return true;
        }
            return false;
        }
    public boolean undoAllNguyenVong(XtNganhBUS nganhBUS){
        this.NganhBUS = nganhBUS;
        boolean rs= false;
        rs = nvDAO.undoAll();
        if(rs){
            this.NganhBUS.resetSoLuongPhuongThucNganh();
            for(XtNguyenVongXetTuyen nv : listNV){
                    nv.setNvKetqua("Đang xét");
                }
            return true;
        }
        return false;
    }
    public boolean undoNguyenVong(XtNganhBUS nganhBUS, XtNguyenVongXetTuyen nvDuocChon){
        this.NganhBUS = nganhBUS;
        boolean rs= false;
        rs = nvDAO.undo(nvDuocChon.getIdnv());
        
        if(rs){
            if(nvDuocChon.getNvKetqua().equals("Trúng tuyển")){
                this.NganhBUS.TruSoLuongPhuongThucNganh(nvDuocChon.getTtPhuongthuc(), nvDuocChon.getNvManganh());
            }
            for (XtNguyenVongXetTuyen nv : listNV) {
                if (nv.getIdnv() == nvDuocChon.getIdnv()) {
                    nv.setNvKetqua("Đang xét");
                }
            }
            return true;
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