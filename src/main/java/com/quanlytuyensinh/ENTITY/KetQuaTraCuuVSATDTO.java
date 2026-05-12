/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.ENTITY;

import java.math.BigDecimal;

/**
 *
 * @author admn
 */
public class KetQuaTraCuuVSATDTO {
    private String Manganh;
    private String Tennganh;
    private String Matohop;
    private String Tentohop;
    private BigDecimal Diemmon1;
    private BigDecimal Diemmon2;
    private BigDecimal Diemmon3;
    private BigDecimal diemUT;
    private BigDecimal tongdiem;
    public KetQuaTraCuuVSATDTO() {
    }

    // 2. Constructor đầy đủ tham số
    public KetQuaTraCuuVSATDTO(String Manganh, String Tennganh, String Matohop, String Tentohop, 
                           BigDecimal Diemmon1, BigDecimal Diemmon2, BigDecimal Diemmon3, 
                           BigDecimal diemUT, BigDecimal tongdiem) {
        this.Manganh = Manganh;
        this.Tennganh = Tennganh;
        this.Matohop = Matohop;
        this.Tentohop = Tentohop;
        this.Diemmon1 = Diemmon1;
        this.Diemmon2 = Diemmon2;
        this.Diemmon3 = Diemmon3;
        this.diemUT = diemUT;
        this.tongdiem = tongdiem;
    }

    // 3. Các phương thức Getter và Setter
    public String getManganh() {
        return Manganh;
    }

    public void setManganh(String Manganh) {
        this.Manganh = Manganh;
    }

    public String getTennganh() {
        return Tennganh;
    }

    public void setTennganh(String Tennganh) {
        this.Tennganh = Tennganh;
    }

    public String getMatohop() {
        return Matohop;
    }

    public void setMatohop(String Matohop) {
        this.Matohop = Matohop;
    }

    public String getTentohop() {
        return Tentohop;
    }

    public void setTentohop(String Tentohop) {
        this.Tentohop = Tentohop;
    }

    public BigDecimal getDiemmon1() {
        return Diemmon1;
    }

    public void setDiemmon1(BigDecimal Diemmon1) {
        this.Diemmon1 = Diemmon1;
    }

    public BigDecimal getDiemmon2() {
        return Diemmon2;
    }

    public void setDiemmon2(BigDecimal Diemmon2) {
        this.Diemmon2 = Diemmon2;
    }

    public BigDecimal getDiemmon3() {
        return Diemmon3;
    }

    public void setDiemmon3(BigDecimal Diemmon3) {
        this.Diemmon3 = Diemmon3;
    }

    public BigDecimal getDiemUT() {
        return diemUT;
    }

    public void setDiemUT(BigDecimal diemUT) {
        this.diemUT = diemUT;
    }

    public BigDecimal getTongdiem() {
        return tongdiem;
    }

    public void setTongdiem(BigDecimal tongdiem) {
        this.tongdiem = tongdiem;
    }
}
