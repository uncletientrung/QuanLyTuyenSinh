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
public class TinhDiemVSATController {
    // Running on http://localhost:8080/tinhdiemVSAT-view
    @RequestMapping(value = "/tinhdiemVSAT-view", method = RequestMethod.GET)
    public String viewForm() {
        return "tinhdiemVSAT-view";
    }
    
    // Running on http://localhost:8080/tinhdiemVSAT
    @RequestMapping(value = "/tinhdiemVSAT", method = RequestMethod.POST)
    public String tinhdiemVSAT() {
        return "tinhdiemVSAT";
    }
}
