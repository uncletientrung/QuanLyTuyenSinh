package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import java.util.ArrayList;

import java.util.List;

public class XtThisinhXetTuyen25BUS {

    private final XtThisinhXetTuyen25DAO TSDAO = XtThisinhXetTuyen25DAO.getInstance();
    private List<XtThisinhXetTuyen25> listThiSinh;

    public XtThisinhXetTuyen25BUS() {
        listThiSinh = TSDAO.getAll();
        if (listThiSinh == null) {
            listThiSinh = new ArrayList<>();
        }

    }

    public List<XtThisinhXetTuyen25> getAllThiSinh() {
        return listThiSinh;
    }
    public boolean insertThiSinh(XtThisinhXetTuyen25 ts) { // Thêm thí sinh
        if (ts == null) return false;
        ts.setSobaodanh(generateSBD());
        XtThisinhXetTuyen25 TS_DaThem = TSDAO.insert(ts); // Trả về thí sinh có id auto 
        if (TS_DaThem != null) {
            listThiSinh.add(TS_DaThem);
            return true;
        }

        return false;
    }
    
    private String generateSBD() {
        int maxId = 0;
        for (XtThisinhXetTuyen25 ts : listThiSinh) {
            if (ts.getIdthisinh() > maxId) {
                maxId = ts.getIdthisinh();
            }
        }
        int newId = maxId + 1;
        return String.format("BD%03d", newId);
    }
    public boolean checkCCCD(String cccd, int idTS){
        for(XtThisinhXetTuyen25 ts : listThiSinh){
            if (ts.getCccd().equals(cccd) && ts.getIdthisinh() != idTS)  return false; // Đã tồn tại
        }
        return true;
    }
    public boolean updateThiSinh(XtThisinhXetTuyen25 tsUpdate){
        if (tsUpdate == null) return false;
        boolean rs = TSDAO.update(tsUpdate);
        if(rs){
            for (XtThisinhXetTuyen25 ts : listThiSinh) {
                if (tsUpdate.getIdthisinh() == ts.getIdthisinh()) {
                    ts.setCccd(tsUpdate.getCccd());
                    ts.setSobaodanh(tsUpdate.getSobaodanh());
                    ts.setHo(tsUpdate.getHo());
                    ts.setTen(tsUpdate.getTen());
                    ts.setNgaySinh(tsUpdate.getNgaySinh());
                    ts.setDienThoai(tsUpdate.getDienThoai());
                    ts.setPassword(tsUpdate.getPassword());
                    ts.setGioiTinh(tsUpdate.getGioiTinh());
                    ts.setEmail(tsUpdate.getEmail());
                    ts.setNoiSinh(tsUpdate.getNoiSinh());
                    ts.setUpdatedAt(tsUpdate.getUpdatedAt());
                    ts.setDoiTuong(tsUpdate.getDoiTuong());
                    ts.setKhuVuc(tsUpdate.getKhuVuc());
                    break;
                }
            }
            return true;
        }
        return false;
    }
    public boolean deleteThiSinh(int IdThiSinh){
        if(IdThiSinh <=0) return false;
        boolean rs = TSDAO.delete(IdThiSinh);
        if (rs) {
            listThiSinh.removeIf(ts -> ts.getIdthisinh() == IdThiSinh);
            return true;
        }
        return false;  
    }
   public List<XtThisinhXetTuyen25> searchThiSinh(String keyword, String searchType) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listThiSinh;
        }
        String key = keyword.trim().toLowerCase();
        List<XtThisinhXetTuyen25> result = new ArrayList<>();

        for (XtThisinhXetTuyen25 ts : listThiSinh) {
            switch (searchType) {

                case "Mã":
                    if (String.valueOf(ts.getIdthisinh()).contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Căn cước CD":
                    if (ts.getCccd() != null && ts.getCccd().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "SBD":
                    if (ts.getSobaodanh() != null && ts.getSobaodanh().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Họ Tên":
                    String fullName = (ts.getHo() + " " + ts.getTen()).toLowerCase();
                    if (fullName.contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Số điện thoại":
                    if (ts.getDienThoai() != null && ts.getDienThoai().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Email":
                    if (ts.getEmail() != null && ts.getEmail().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Nơi sinh":
                    if (ts.getNoiSinh() != null && ts.getNoiSinh().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Khu vực":
                    if (ts.getKhuVuc() != null && ts.getKhuVuc().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                default: // Tất cả
                    if(ts.toString().toLowerCase().contains(key)){
                        result.add(ts);
                    }
                    break;
            }
        }

        return result;
    }

}