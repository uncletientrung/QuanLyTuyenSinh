/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI;
import com.quanlytuyensinh.BUS.TaiKhoanBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import java.util.List;
import com.quanlytuyensinh.ENTITY.TaiKhoan;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;

/**
 *
 * @author DELL
 */
public class testGUI {
    public static void main(String[] args) {

        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        XtThisinhXetTuyen25BUS TSBUS =  new XtThisinhXetTuyen25BUS();
        XtNguyenVongXetTuyenBUS NVBUS = new XtNguyenVongXetTuyenBUS();
        List<XtThisinhXetTuyen25> list = TSBUS.getAllThiSinh();
        List<XtNguyenVongXetTuyen> list2 = NVBUS.getAllNguyenVong();
        XtDiemThiXetTuyenBUS DTBUS = new XtDiemThiXetTuyenBUS();
         List<XtDiemThiXetTuyen> listDT = DTBUS.getList();
        System.out.println("===== DANH SACH TAI KHOAN =====");

        for (XtDiemThiXetTuyen nv : listDT) {

            System.out.println(
                    nv.toString()
            );
        }

    }
}
