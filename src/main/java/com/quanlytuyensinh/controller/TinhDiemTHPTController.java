/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.service.tinhDiemService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

/**
 *
 * @author dell
 */
@Controller
public class TinhDiemTHPTController {
        tinhDiemService tinhDiem = new tinhDiemService();
    
    
    // Running on http://localhost:8080/tinhdiemTHPT-view
    @RequestMapping(value = "/tinhdiemTHPT-view", method = RequestMethod.GET)
    public String viewForm(Model model) {// request tới controller -> Spring tạo Model obj 
        List<XtNganh> listNganh = tinhDiem.getListNganh();
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
        @RequestParam("gdcd") BigDecimal gdcd,
        @RequestParam("cnCongNghiep") BigDecimal cnCongNghiep,
        @RequestParam("cnNongNghiep") BigDecimal cnNongNghiep
    ) {

        System.out.println("Ngành: " + nganh);
        System.out.println("Toán: " + toan);
        System.out.println("Điểm cộng: " + diemCong);

        return "tinhdiemTHPT";
    }
}
