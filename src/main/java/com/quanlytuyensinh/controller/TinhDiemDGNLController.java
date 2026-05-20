/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

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
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Windows
 */
@Controller
public class TinhDiemDGNLController {
    // Running on http://localhost:8080/tinhdiemDGNL-view
    tinhDiemService service  = new tinhDiemService();
    @RequestMapping(value = "/tinhdiemDGNL-view", method = RequestMethod.GET)
    public String viewForm(Model model) {
        List<XtNganh> listNganh = service.getListNganh();
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
        List<TinhDiemDGNL> listKQDGNL= new ArrayList<>();
        listKQDGNL = service.TinhDiemDGNLTatCaToHop(nganh, diemCong, khuVuc, doiTuong, diemThi);
        String THGoc = this.service.getTHGoc(nganh);
        TinhDiemDGNL ThCaoNhat = listKQDGNL.get(0);
        BigDecimal NguongDauVao = this.service.getDiemSan(nganh);
        String tenNganh = this.service.getTenNganhByMaNganh(nganh);
        BigDecimal diemUuTienKhuVuc = this.service.DiemUuTienKhuVuc(khuVuc);
        BigDecimal diemUuTienDoiTuong = this.service.DiemUuTienDoiTuong(doiTuong);
        
        model.addAttribute("doiTuong", doiTuong.equals("0") ? "Không có" : doiTuong);
        model.addAttribute("diemUuTienDoiTuong", diemUuTienDoiTuong);
        model.addAttribute("khuVuc", khuVuc.equals("0") ? "Không có" : khuVuc);
        model.addAttribute("diemUuTienKhuVuc", diemUuTienKhuVuc);
        model.addAttribute("tenNganh", tenNganh + " (" + nganh + ")"); 
        model.addAttribute("listKQDGNL", listKQDGNL);
        model.addAttribute("THGoc", THGoc);
        model.addAttribute("ThCaoNhat", ThCaoNhat);
        model.addAttribute("NguongDauVao", NguongDauVao == null || NguongDauVao.compareTo(BigDecimal.ZERO) == 0 ? "Chưa công bố" : NguongDauVao);
        return "tinhdiemDGNL";
    }
}
