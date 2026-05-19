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
    private BigDecimal diemGocToan;
    private BigDecimal diemGocVan;
    private BigDecimal diemGocLy;
    private BigDecimal diemGocHoa;
    private BigDecimal diemGocSinh;
    private BigDecimal diemGocAnh;
    private BigDecimal diemGocSu;
    private BigDecimal diemGocDia;
      private String  congthucdiemToan;
    private String  congthucdiemVan;
    private String  congthucdiemLy;
    private String  congthucdiemHoa;
    private String  congthucdiemSinh;
    private String  congthucdiemAnh;
    private String  congthucdiemSu;
    private String   congthucdiemDia;
     private BigDecimal diemTHXT;      
    private BigDecimal diemUuTien;
    private BigDecimal diemCong;
    private BigDecimal doLech;   
    private BigDecimal diemXT;    
     private boolean isToHopGoc;  
    
    public TinhDiemVSAT(){
    
}
    public TinhDiemVSAT(String maToHop, String tenToHop,String congThuc,
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
    public String getcongthucDiemToan() {
        return congthucdiemToan;
    }

    public void setcongthucDiemToan(String ctdiemToan) {
        this.congthucdiemToan = ctdiemToan;
    }

    public String getcongthucDiemVan() {
        return congthucdiemVan;
    }

    public void setcongthucDiemVan(String ctdiemVan) {
        this.congthucdiemVan = ctdiemVan;
    }

    public String getcongthucDiemLy() {
        return congthucdiemLy;
    }

    public void setcongthucDiemLy(String ctdiemLy) {
        this.congthucdiemLy = ctdiemLy;
    }

    public String getcongthucDiemHoa() {
        return congthucdiemHoa;
    }

    public void setcongthucDiemHoa(String ctdiemHoa) {
        this.congthucdiemHoa = ctdiemHoa;
    }

    public String getcongthucDiemSinh() {
        return congthucdiemSinh;
    }

    public void setcongthucDiemSinh(String ctdiemSinh) {
        this.congthucdiemSinh = ctdiemSinh;
    }

    public String getcongthucDiemAnh() {
        return congthucdiemAnh;
    }

    public void setcongthucDiemAnh(String ctdiemAnh) {
        this.congthucdiemAnh = ctdiemAnh;
    }

    public String getcongthucDiemSu() {
        return congthucdiemSu;
    }

    public void setcongthucDiemSu(String ctdiemSu) {
        this.congthucdiemSu = ctdiemSu;
    }

    public String getcongthucDiemDia() {
        return congthucdiemDia;
    }

    public void setcongthucDiemDia(String ctdiemDia) {
        this.congthucdiemDia = ctdiemDia;
    }
  
public BigDecimal getDiemGocToan() {
        return diemGocToan;
    }

    public void setDiemGocToan(BigDecimal diemGocToan) {
        this.diemGocToan = diemGocToan;
    }

    public BigDecimal getDiemGocVan() {
        return diemGocVan;
    }

    public void setDiemGocVan(BigDecimal diemGocVan) {
        this.diemGocVan = diemGocVan;
    }

    public BigDecimal getDiemGocLy() {
        return diemGocLy;
    }

    public void setDiemGocLy(BigDecimal diemGocLy) {
        this.diemGocLy = diemGocLy;
    }

    public BigDecimal getDiemGocHoa() {
        return diemGocHoa;
    }

    public void setDiemGocHoa(BigDecimal diemGocHoa) {
        this.diemGocHoa = diemGocHoa;
    }

    public BigDecimal getDiemGocSinh() {
        return diemGocSinh;
    }

    public void setDiemGocSinh(BigDecimal diemGocSinh) {
        this.diemGocSinh = diemGocSinh;
    }

    public BigDecimal getDiemGocAnh() {
        return diemGocAnh;
    }

    public void setDiemGocAnh(BigDecimal diemGocAnh) {
        this.diemGocAnh = diemGocAnh;
    }

    public BigDecimal getDiemGocSu() {
        return diemGocSu;
    }

    public void setDiemGocSu(BigDecimal diemGocSu) {
        this.diemGocSu = diemGocSu;
    }

    public BigDecimal getDiemGocDia() {
        return diemGocDia;
    }

    public void setDiemGocDia(BigDecimal diemGocDia) {
        this.diemGocDia = diemGocDia;
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
            case "N1": return getDiemAnh();
            default: return BigDecimal.ZERO;
        }
    }
     public String getctDiemTheoMaMon(String maMon) {
        if (maMon == null) return "";
        switch (maMon) {
            case "TO": return getcongthucDiemToan();
            case "VA": return getcongthucDiemVan();
            case "LI": return getcongthucDiemLy();
            case "HO": return getcongthucDiemHoa();
            case "SI": return getcongthucDiemSinh();
            case "SU": return getcongthucDiemSu();
            case "DI": return getcongthucDiemDia();
            case "N1": return getcongthucDiemAnh();
            default: return "";
        }
    }
     public BigDecimal getDiemGocTheoMaMon(String maMon) {
        if (maMon == null) return BigDecimal.ZERO;
        
        switch (maMon) {
            case "TO": return getDiemGocToan();
            case "VA": return getDiemGocVan();
            case "LI": return getDiemGocLy();
            case "HO": return getDiemGocHoa();
            case "SI": return getDiemGocSinh();
            case "SU": return getDiemGocSu();
            case "DI": return getDiemGocDia();
            case "N1": return getDiemGocAnh();
            default: return BigDecimal.ZERO;
        }
    }

}
