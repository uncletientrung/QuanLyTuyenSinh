/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Windows
 */
@Controller
public class TraCuuController {
    // Running on http://localhost:8080/tracuu
    @RequestMapping("/tracuu")
    public String tracuu() {
        return "tracuu";
    }
}
