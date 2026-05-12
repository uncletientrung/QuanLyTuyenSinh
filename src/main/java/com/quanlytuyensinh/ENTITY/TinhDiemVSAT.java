/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.ENTITY;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author admn
 */
public class TinhDiemVSAT {
    private String Matohop;
    private String Tentohop;
    private List<String> dsMonThi;           // Ví dụ: ["TO", "VA", "SI"]
    private List<String> dsTenMonThi; 
    private String congThuc; 
    private BigDecimal  diemToan;
    private BigDecimal  diemVan;
    private BigDecimal  diemLy;
    private BigDecimal  diemHoa;
    private BigDecimal  diemSinh;
    private BigDecimal  diemAnh;
    private BigDecimal  diemSu;
    private BigDecimal   diemDia;
     private BigDecimal diemTHXT;      
    private BigDecimal diemUuTien;
    private BigDecimal diemCong;
    private BigDecimal doLech;   
    private BigDecimal diemXT;    
     private boolean isToHopGoc;  
    
    public TinhDiemVSAT(){
    
}
    public TinhDiemVSAT(String maToHop, String tenToHop,
            BigDecimal diemToan, BigDecimal diemVan, BigDecimal diemLy,
            BigDecimal diemHoa, BigDecimal diemSinh, BigDecimal diemAnh,
            BigDecimal diemSu, BigDecimal diemDia, BigDecimal diemTHXT,
            BigDecimal diemUuTien, BigDecimal diemCong, BigDecimal doLech,
            BigDecimal diemXT,boolean isToHopGoc) {

        this.Matohop = maToHop;
        this.Tentohop = tenToHop;
        this.diemToan = diemToan;
         this.congThuc = congThuc;
        this.diemVan = diemVan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
        this.diemSinh = diemSinh;
        this.diemAnh = diemAnh;
        this.diemSu = diemSu;
        this.diemDia = diemDia;
   
        this.diemTHXT = diemTHXT;
        this.diemUuTien = diemUuTien;
        this.diemCong = diemCong;
        this.doLech = doLech;
        this.diemXT = diemXT;
      this.isToHopGoc = isToHopGoc;
    }
     public String getMaToHop() {
        return Matohop;
    }

    public void setMaToHop(String maToHop) {
        this.Matohop = maToHop;
    }

    public String getTenToHop() {
        return Tentohop;
    }

    public void setTenToHop(String tenToHop) {
        this.Tentohop = tenToHop;
    }

     public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }

    public BigDecimal getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(BigDecimal diemToan) {
        this.diemToan = diemToan;
    }

    public BigDecimal getDiemVan() {
        return diemVan;
    }

    public void setDiemVan(BigDecimal diemVan) {
        this.diemVan = diemVan;
    }

    public BigDecimal getDiemLy() {
        return diemLy;
    }

    public void setDiemLy(BigDecimal diemLy) {
        this.diemLy = diemLy;
    }

    public BigDecimal getDiemHoa() {
        return diemHoa;
    }

    public void setDiemHoa(BigDecimal diemHoa) {
        this.diemHoa = diemHoa;
    }

    public BigDecimal getDiemSinh() {
        return diemSinh;
    }

    public void setDiemSinh(BigDecimal diemSinh) {
        this.diemSinh = diemSinh;
    }

    public BigDecimal getDiemAnh() {
        return diemAnh;
    }

    public void setDiemAnh(BigDecimal diemAnh) {
        this.diemAnh = diemAnh;
    }

    public BigDecimal getDiemSu() {
        return diemSu;
    }

    public void setDiemSu(BigDecimal diemSu) {
        this.diemSu = diemSu;
    }

    public BigDecimal getDiemDia() {
        return diemDia;
    }

    public void setDiemDia(BigDecimal diemDia) {
        this.diemDia = diemDia;
    }

  


    public BigDecimal getDiemTHXT() {
        return diemTHXT;
    }

    public void setDiemTHXT(BigDecimal diemTHXT) {
        this.diemTHXT = diemTHXT;
    }

    public BigDecimal getDiemUuTien() {
        return diemUuTien;
    }

    public void setDiemUuTien(BigDecimal diemUuTien) {
        this.diemUuTien = diemUuTien;
    }

    public BigDecimal getDiemCong() {
        return diemCong;
    }

    public void setDiemCong(BigDecimal diemCong) {
        this.diemCong = diemCong;
    }

    public BigDecimal getDoLech() {
        return doLech;
    }

    public void setDoLech(BigDecimal doLech) {
        this.doLech = doLech;
    }

    public BigDecimal getDiemXT() {
        return diemXT;
    }

    public void setDiemXT(BigDecimal diemXT) {
        this.diemXT = diemXT;
    }
    public List<String> getDsMonThi() {
        return dsMonThi;
    }
    public void setDsMonThi(List<String> dsMonThi) {
        this.dsMonThi = dsMonThi;
    }

    public List<String> getDsTenMonThi() {
        return dsTenMonThi;
    }
    public void setDsTenMonThi(List<String> dsTenMonThi) {
        this.dsTenMonThi = dsTenMonThi;
    }
    public boolean isToHopGoc() {
        return isToHopGoc;
    }

    public void setToHopGoc(boolean toHopGoc) {
        isToHopGoc = toHopGoc;
    }
    
    public BigDecimal getDiemTheoMaMon(String maMon) {
        if (maMon == null) return BigDecimal.ZERO;
        switch (maMon) {
            case "TO": return getDiemToan();
            case "VA": return getDiemVan();
            case "LI": return getDiemLy();
            case "HO": return getDiemHoa();
            case "SI": return getDiemSinh();
            case "SU": return getDiemSu();
            case "DI": return getDiemDia();
            default: return BigDecimal.ZERO;
        }
    }

}
