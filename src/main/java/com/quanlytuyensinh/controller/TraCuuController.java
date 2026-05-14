package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.KetQuaTraCuuDTO;
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

    @Autowired
    private TraCuuService traCuuService;

    @GetMapping("/tracuu")
    public String showForm(HttpSession session, 
                           Model model,
                           @RequestParam(required = false) String cccd,
                           @RequestParam(required = false) String ngaySinh) {

        // Nếu chưa có cccd (tức là mới redirect từ login) → tự động tra cứu cho user đang đăng nhập
        if (cccd == null || cccd.trim().isEmpty()) {
            XtThisinhXetTuyen25 user = (XtThisinhXetTuyen25) session.getAttribute("user");
            if (user != null) {
                cccd = user.getCccd();
                
                List<KetQuaTraCuuDTO> dsKetQua = traCuuService.traCuu(cccd, ""); // không cần password
                model.addAttribute("daTraCuu", true);
                model.addAttribute("dsKetQua", dsKetQua);
                model.addAttribute("cccd", cccd);
                // Không cần set ngaySinh vì là auto load
                return "tracuu";
            }
        }

        // Trường hợp bình thường (truy cập trực tiếp hoặc refresh)
        model.addAttribute("daTraCuu", false);
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