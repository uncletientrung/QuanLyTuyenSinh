package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;

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


}