/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.KetQuaTraCuuDTO;
import com.quanlytuyensinh.service.TraCuuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TraCuuController {

    @Autowired
    private TraCuuService traCuuService;
    
    // Running on http://localhost:8080/tracuu
    @GetMapping("/tracuu")
    public String showForm() {
        return "tracuu";
    }

    @PostMapping("/tracuu")
    public String traKetQua(
            @RequestParam String cccd,
            @RequestParam String ngaySinh,
            Model model) {

        List<KetQuaTraCuuDTO> dsKetQua = traCuuService.traCuu(cccd, ngaySinh);

        model.addAttribute("daTraCuu", true);
        model.addAttribute("dsKetQua", dsKetQua);
        model.addAttribute("cccd", cccd);
        model.addAttribute("ngaySinh", ngaySinh);

        return "tracuu";
    }
}