package com.quanlytuyensinh.controller;

import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;


    // http://localhost:8080/login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    
    @PostMapping("/login")
    public String login(String cccd,
                        String password,
                        Model model,
                        HttpSession session) {

        XtThisinhXetTuyen25 thiSinh = loginService.login(cccd, password);

        if (thiSinh == null) {
            model.addAttribute("error", "CCCD hoặc ngày sinh không đúng.");
            return "login";
        }

        session.setAttribute("user", thiSinh);
        return "redirect:/tracuu";   // sẽ tự động load kết quả
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }
}