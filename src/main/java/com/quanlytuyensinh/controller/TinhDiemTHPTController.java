/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.TinhDiemTHPTDTO;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.service.tinhDiemService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
@Controller
public class TinhDiemTHPTController {
        tinhDiemService tinhDiemSV = new tinhDiemService();
    
    
    // Running on http://localhost:8080/tinhdiemTHPT-view
    @RequestMapping(value = "/tinhdiemTHPT-view", method = RequestMethod.GET)
    public String viewForm(Model model) {// request tới controller -> Spring tạo Model obj 
        List<XtNganh> listNganh = tinhDiemSV.getListNganh();
        model.addAttribute("listNganh", listNganh);  // truyền vào method   
        return "tinhdiemTHPT-view";
    }
    
    // Running on http://localhost:8080/tinhdiemTHPT
    @RequestMapping(value = "/tinhdiemTHPT", method = RequestMethod.POST)
    public String tinhdiemTHPT(
        @RequestParam("nganh") String nganh,
        @RequestParam("diemCong") BigDecimal diemCong,
        @RequestParam("khuVuc") String khuVuc,
        @RequestParam("doiTuong") String doiTuong,
        @RequestParam("toan") BigDecimal toan,
        @RequestParam("nguVan") BigDecimal nguVan,
        @RequestParam("vatLy") BigDecimal vatLy,
        @RequestParam("hoaHoc") BigDecimal hoaHoc,
        @RequestParam("sinhHoc") BigDecimal sinhHoc,
        @RequestParam("tiengAnh") BigDecimal tiengAnh,
        @RequestParam("lichSu") BigDecimal lichSu,
        @RequestParam("diaLy") BigDecimal diaLy,
        @RequestParam("tinHoc") BigDecimal tinHoc,
        @RequestParam("ktpl") BigDecimal ktpl,
        @RequestParam("cnCongNghiep") BigDecimal cnCongNghiep,
        @RequestParam("cnNongNghiep") BigDecimal cnNongNghiep, Model model
    ) {

        List<TinhDiemTHPTDTO> listKQTHPT = new ArrayList<>();
        
        listKQTHPT = this.tinhDiemSV.tinhDiemTHPTTatCaToHop(nganh, toan, nguVan, vatLy, hoaHoc, sinhHoc, tiengAnh, lichSu, diaLy, tinHoc, 
                ktpl, cnCongNghiep, cnNongNghiep, khuVuc, doiTuong, diemCong);
        String THGoc = this.tinhDiemSV.getTHGoc(nganh);
        TinhDiemTHPTDTO ThCaoNhat = listKQTHPT.get(0);
        BigDecimal NguongDauVao = this.tinhDiemSV.getDiemSan(nganh);
        String tenNganh = this.tinhDiemSV.getTenNganhByMaNganh(nganh);
        BigDecimal diemUuTienKhuVuc = this.tinhDiemSV.DiemUuTienKhuVuc(khuVuc);
        BigDecimal diemUuTienDoiTuong = this.tinhDiemSV.DiemUuTienDoiTuong(doiTuong);
        
        model.addAttribute("doiTuong", doiTuong.equals("0") ? "Không có" : doiTuong);
        model.addAttribute("diemUuTienDoiTuong", diemUuTienDoiTuong);
        model.addAttribute("khuVuc", khuVuc.equals("0") ? "Không có" : khuVuc);
        model.addAttribute("diemUuTienKhuVuc", diemUuTienKhuVuc);
        model.addAttribute("tenNganh", tenNganh + " (" + nganh + ")"); 
        model.addAttribute("listKQTHPT", listKQTHPT);
        model.addAttribute("THGoc", THGoc);
        model.addAttribute("ThCaoNhat", ThCaoNhat);
        model.addAttribute("NguongDauVao", NguongDauVao == BigDecimal.ZERO || NguongDauVao == null ? "Chưa công bố" : NguongDauVao);
        return "tinhdiemTHPT";
    }
}
