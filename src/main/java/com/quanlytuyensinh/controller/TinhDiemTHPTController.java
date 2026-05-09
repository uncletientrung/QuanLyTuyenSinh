/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author dell
 */
@Controller
public class TinhDiemTHPTController {
    // Running on http://localhost:8080/tinhdiemTHPT-view
    @RequestMapping(value = "/tinhdiemTHPT-view", method = RequestMethod.GET)
    public String viewForm() {
        return "tinhdiemTHPT-view";
    }
}
