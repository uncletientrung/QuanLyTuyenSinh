/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.KetQuaTraCuuVSATDTO;
import com.quanlytuyensinh.ENTITY.TinhDiemTHPTDTO;
import com.quanlytuyensinh.ENTITY.TinhDiemVSAT;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.service.tinhDiemService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Windows
 */
@Controller
public class TinhDiemVSATController {
     tinhDiemService tinhDiemSV = new tinhDiemService();
    @Autowired
    private tinhDiemService tinhDiemService;
    // Running on http://localhost:8080/tinhdiemVSAT-view
    @RequestMapping(value = "/tinhdiemVSAT-view", method = RequestMethod.GET)
    public String viewForm(Model model) {
       List<XtNganh> listNganh = tinhDiemSV.getListNganh();
        model.addAttribute("listNganh", listNganh);  // truyền vào method   
        return "tinhdiemVSAT-view";
    }
    
    // Running on http://localhost:8080/tinhdiemVSAT
    @RequestMapping(value = "/tinhdiemVSAT", method = RequestMethod.POST)
    public String tinhdiemVSAT(@RequestParam String Manganh,@RequestParam("diemCong") BigDecimal diemCong,
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
         Model model) {
        
        try { 
            
            List<TinhDiemVSAT> listKQVSAT = tinhDiemService.tinhdiemxettuyenVSAT(Manganh, toan, nguVan, vatLy, hoaHoc, sinhHoc, tiengAnh, lichSu, diaLy, 
                   khuVuc, doiTuong, diemCong);
         String THGoc = this.tinhDiemSV.getTHGoc(Manganh);
           TinhDiemVSAT ThCaoNhat = listKQVSAT.get(0);
          BigDecimal NguongDauVao = this.tinhDiemSV.getDiemSan(Manganh);
           String tenNganh = this.tinhDiemSV.getTenNganhByMaNganh(Manganh);
           BigDecimal diemUuTienKhuVuc = this.tinhDiemSV.DiemUuTienKhuVuc(khuVuc);
        BigDecimal diemUuTienDoiTuong = this.tinhDiemSV.DiemUuTienDoiTuong(doiTuong);
        // 2. GỬI NGƯỢC LẠI INPUT CHỨA TOÀN BỘ ĐIỂM THÍ SINH VỪA NHẬP
       
            model.addAttribute("listKQVSAT", listKQVSAT);

        // 3. Gửi ngược lại mã ngành đã chọn (nếu cần hiển thị)
       
         model.addAttribute("doiTuong", doiTuong.equals("0") ? "Không có" : doiTuong);
        model.addAttribute("diemUuTienDoiTuong", diemUuTienDoiTuong);
        model.addAttribute("khuVuc", khuVuc.equals("0") ? "Không có" : khuVuc);
        model.addAttribute("diemUuTienKhuVuc", diemUuTienKhuVuc);
        model.addAttribute("tenNganh", tenNganh + " (" + Manganh + ")"); 
        model.addAttribute("ThCaoNhat", ThCaoNhat);
        model.addAttribute("NguongDauVao", NguongDauVao);
        model.addAttribute("THGoc", THGoc);
        return "tinhdiemVSAT";
        }
        catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "error-page"; 
        }
        
    }
    
}
