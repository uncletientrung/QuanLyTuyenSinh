/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.KetQuaTraCuuVSATDTO;
import com.quanlytuyensinh.ENTITY.TinhDiemVSAT;
import com.quanlytuyensinh.service.tinhDiemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private tinhDiemService tinhDiemService;
    // Running on http://localhost:8080/tinhdiemVSAT-view
    @RequestMapping(value = "/tinhdiemVSAT-view", method = RequestMethod.GET)
    public String viewForm() {
        return "tinhdiemVSAT-view";
    }
    
    // Running on http://localhost:8080/tinhdiemVSAT
    @RequestMapping(value = "/tinhdiemVSAT", method = RequestMethod.POST)
    public ResponseEntity<?> tinhdiemVSAT(@RequestBody TinhDiemVSAT input, @RequestParam String Manganh) {
        try { 
            List<KetQuaTraCuuVSATDTO> kq = tinhDiemService.tinhdiemxettuyenVSAT(input,Manganh);
        
        if (kq.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy tổ hợp môn cho ngành: " + Manganh);
            }
        return ResponseEntity.ok(kq);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi hệ thống: " + e.getMessage());
        }
        
    }
    
}
