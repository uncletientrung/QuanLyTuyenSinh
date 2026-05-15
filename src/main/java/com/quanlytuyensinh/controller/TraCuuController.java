package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.KetQuaTraCuuDTO;
import com.quanlytuyensinh.ENTITY.TraCuuResultWrapper;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.service.TraCuuService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TraCuuController {
    // http://localhost:8080/tracuu
    @Autowired
    private TraCuuService traCuuService;

    @GetMapping("/tracuu")
    public String showForm(HttpSession session, Model model,
                           @RequestParam(required = false) String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            XtThisinhXetTuyen25 user = (XtThisinhXetTuyen25) session.getAttribute("user");
            if (user != null) {
                TraCuuResultWrapper wrapper = traCuuService.traCuu(user.getCccd());
                model.addAttribute("daTraCuu", true);
                model.addAttribute("dsKetQua",  wrapper.getDsNguyenVong());
                model.addAttribute("dsDiem",    wrapper.getDsDiem());      // ← thêm
                model.addAttribute("wrapper",   wrapper);                  // ← thêm (để gọi getDiemTheo)
                model.addAttribute("cccd", user.getCccd());
                return "tracuu";
            }
        }
        model.addAttribute("daTraCuu", false);
        return "tracuu";
    }

    @PostMapping("/tracuu")
    public String traKetQua(@RequestParam String cccd, Model model) {
        TraCuuResultWrapper wrapper = traCuuService.traCuu(cccd);
        model.addAttribute("daTraCuu", true);
        model.addAttribute("dsKetQua",  wrapper.getDsNguyenVong());
        model.addAttribute("dsDiem",    wrapper.getDsDiem());
        model.addAttribute("wrapper",   wrapper);
        model.addAttribute("cccd", cccd);
        return "tracuu";
    }
}