package com.quanlytuyensinh.BUS;

import java.util.List;

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
}