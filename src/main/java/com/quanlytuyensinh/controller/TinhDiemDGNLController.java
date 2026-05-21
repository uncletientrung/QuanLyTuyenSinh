/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanlytuyensinh.ENTITY.TinhDiemDGNL;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.service.tinhDiemService;

/**
 *
 * @author Windows
 */
@Controller
public class TinhDiemDGNLController {
    // Running on http://localhost:8080/tinhdiemDGNL-view
    @Autowired
    tinhDiemService service  = new tinhDiemService();
    @RequestMapping(value = "/tinhdiemDGNL-view", method = RequestMethod.GET)
    public String viewForm(Model model) {
        List<XtNganh> listNganh = service.getListNganhKhongSuPham();
        model.addAttribute("listNganh", listNganh);
        return "tinhdiemDGNL-view";
    }
    
    // Running on http://localhost:8080/tinhdiemDGNL
    @RequestMapping(value = "/tinhdiemDGNL", method = RequestMethod.POST)
    public String tinhdiemDGNL(
        @RequestParam("nganh-select") String nganh,
        @RequestParam("diem-cong-input") String diemCong,
        @RequestParam("khuvuc-select") String khuVuc,
        @RequestParam("doituong-select") String doiTuong,
        @RequestParam("diem-thi-input") String diemThi,
        Model model
    ) {
        
        TinhDiemDGNL diemDGNL = service.tinhDiemDGNL(nganh, diemCong, khuVuc, doiTuong, diemThi);
        BigDecimal diemTrungTuyen = service.getDiemTT(nganh) == null ? new BigDecimal("0.00") : service.getDiemTT(nganh);
        BigDecimal diemSan = service.getDiemSan(nganh) == null ? new BigDecimal("0.00") : service.getDiemSan(nganh);
        model.addAttribute("diemSan", diemSan);
        model.addAttribute("diemTrungTuyen", diemTrungTuyen);
        model.addAttribute("ketqua", diemDGNL);
        model.addAttribute("nganh", nganh);
        
        String kqDiemSan = "", kqDiemTT = "";

        if (diemSan.compareTo(BigDecimal.ZERO) == 0) {
            kqDiemSan = "Ngành chưa có điểm sàn được công bố";
        } else if (diemDGNL.getDiemXetTuyen().compareTo(diemSan) > 0) {
            kqDiemSan = "ĐẠT";
        }
        else if (diemDGNL.getDiemXetTuyen().compareTo(diemSan) < 0) {
            kqDiemSan = "KHÔNG ĐẠT";
        }

        if (diemTrungTuyen.compareTo(BigDecimal.ZERO) == 0) {
            kqDiemTT = "Ngành chưa có điểm sàn được công bố";
        } else if (diemDGNL.getDiemXetTuyen().compareTo(diemTrungTuyen) > 0) {
            kqDiemTT = "ĐẠT";
        }
        else if (diemDGNL.getDiemXetTuyen().compareTo(diemTrungTuyen) < 0) {
            kqDiemTT = "KHÔNG ĐẠT";
        }

        model.addAttribute("kqDiemSan", kqDiemSan);
        model.addAttribute("kqDiemTT", kqDiemTT);
        return "tinhdiemDGNL";
    }
}
