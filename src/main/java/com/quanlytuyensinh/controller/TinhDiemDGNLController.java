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
 * @author Windows
 */
@Controller
public class TinhDiemDGNLController {
    // Running on http://localhost:8080/tinhdiemDGNL-view
    @RequestMapping(value = "/tinhdiemDGNL-view", method = RequestMethod.GET)
    public String viewForm() {
        return "tinhdiemDGNL-view";
    }
    
    // Running on http://localhost:8080/tinhdiemDGNL
    @RequestMapping(value = "/tinhdiemDGNL", method = RequestMethod.POST)
    public String tinhdiemDGNL() {
        return "tinhdiemDGNL";
    }
}
