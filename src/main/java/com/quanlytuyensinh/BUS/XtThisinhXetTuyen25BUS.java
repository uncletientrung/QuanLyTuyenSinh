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
    public boolean checkCCCD(String cccd){
        for(XtThisinhXetTuyen25 ts : listThiSinh){
            if (ts.getCccd().equals(cccd))  return false; // Đã tồn tại
        }
        return true;
    }

}