package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import java.util.ArrayList;

import java.util.List;

public class XtNguyenVongXetTuyenBUS {
    private final XtNguyenVongXetTuyenDAO nvDAO = XtNguyenVongXetTuyenDAO.getInstance();
    private List<XtNguyenVongXetTuyen> listNV;
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