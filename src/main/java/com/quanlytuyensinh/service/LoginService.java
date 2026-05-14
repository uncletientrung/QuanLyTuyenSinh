package com.quanlytuyensinh.service;

import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final XtThisinhXetTuyen25DAO thisinhDAO =
            XtThisinhXetTuyen25DAO.getInstance();

    public XtThisinhXetTuyen25 login(String cccd, String password) {
        return thisinhDAO.findByCccdAndPassword2(cccd, password);
    }
}