///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.quanlytuyensinh.service;
//import com.quanlytuyensinh.service.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns(
//                        "/tracuu",
//                        "/tinhdiemDGNL-view",
//                        "/tinhdiemTHPT-view",
//                        "/tinhdiemVSAT-view"
//                );
//    }
//}

// 2 file LoginInterceptor va Webconfig dung de chan khong cho truy cap link tinhdiem va tracuu khi chua login