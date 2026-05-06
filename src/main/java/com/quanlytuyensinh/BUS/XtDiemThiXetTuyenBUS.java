package com.quanlytuyensinh.BUS;

import java.util.List;
import java.util.stream.Collectors;

import com.quanlytuyensinh.DAO.XtDiemThiXetTuyenDAO;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;

public class XtDiemThiXetTuyenBUS {
    private final XtDiemThiXetTuyenDAO diemDAO = XtDiemThiXetTuyenDAO.getInstance();
    private List<XtDiemThiXetTuyen> listDiem;

    public XtDiemThiXetTuyenBUS() {
        listDiem = diemDAO.getAll();
    }

    public List<XtDiemThiXetTuyen> getList() {
        return listDiem;
    }

    public List<XtDiemThiXetTuyen> getListTHPT() {
        return diemDAO.getAllTHPT();
    }

    public List<XtDiemThiXetTuyen> getListDGNL() {
        return diemDAO.getAllDGNL();
    }

    public List<XtDiemThiXetTuyen> getListVSAT() {
        return diemDAO.getAllVSAT();
    }
    
    public List<XtDiemThiXetTuyen> refreshList() {
        return diemDAO.getAll();
    }

    public XtDiemThiXetTuyen findById(int id) {
        return diemDAO.findById(id);
    }

    public XtDiemThiXetTuyen getDiemThiByCCCD(String cccd){
        XtDiemThiXetTuyen rs = null;
        for(XtDiemThiXetTuyen dt : listDiem){
            if(dt.getCccd().equals(cccd)){
                rs = dt;
                break;
            }
        }
        return rs;
    }

    public void updateCert(String cccd, String diem) {
        diemDAO.updateCert(cccd, diem);
    }

    public XtDiemThiXetTuyen findByCCCDAndPT(String cccd, String pt) {
        return diemDAO.findByCCCDAndPT(cccd, pt);
    }
    public boolean existCCCD(String cccd) {
        return diemDAO.existCCCD(cccd);
    }

    public void importToDB(List<XtDiemThiXetTuyen> list) {
        diemDAO.importToDB(list);
    }

    public void updateToDB(List<XtDiemThiXetTuyen> list) {
        diemDAO.updateToDB(list);
    }
    public XtDiemThiXetTuyen getDiemThiTHPTByCCCD(String cccd){
        XtDiemThiXetTuyen rs = null;
        for(XtDiemThiXetTuyen dt : listDiem){
            if(dt.getCccd().equals(cccd) && dt.getDPhuongthuc().equals("THPT")){
                rs = dt;
                break;
            }
        }
        return rs;
    }
    public XtDiemThiXetTuyen getDiemThiVSATByCCCD(String cccd){
        XtDiemThiXetTuyen rs = null;
        for(XtDiemThiXetTuyen dt : listDiem){
            if(dt.getCccd().equals(cccd) && dt.getDPhuongthuc().equals("VSAT")){
                rs = dt;
                break;
            }
        }
        return rs;
    }
}