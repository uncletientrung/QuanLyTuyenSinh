package com.quanlytuyensinh.ENTITY;

import java.math.BigDecimal;

public class TinhDiemDGNL {

    private String tenNganh;
    private String maToHop;
    private boolean toHopGoc;
    private BigDecimal diemThi;
    private String ctQuyDoi;
    private BigDecimal diemThiQuyDoi;
    private BigDecimal diemCong;
    private BigDecimal diemUuTien;
    private BigDecimal diemXetTuyen;
    private BigDecimal doLech;

public TinhDiemDGNL(String tenNganh,
                    String maToHop,
                    boolean toHopGoc,
                    BigDecimal diemThi,
                    String ctQuyDoi,
                    BigDecimal diemThiQuyDoi,
                    BigDecimal diemCong,
                    BigDecimal diemUuTien,
                    BigDecimal diemXetTuyen,
                    BigDecimal doLech) {

    this.tenNganh = tenNganh;
    this.maToHop = maToHop;
    this.toHopGoc = toHopGoc;
    this.diemThi = diemThi;
    this.ctQuyDoi = ctQuyDoi;
    this.diemThiQuyDoi = diemThiQuyDoi;
    this.diemCong = diemCong;
    this.diemUuTien = diemUuTien;
    this.diemXetTuyen = diemXetTuyen;
    this.doLech = doLech;
}

    public TinhDiemDGNL() {
    }

    // ================= GETTER =================

    public String getTenNganh() {
        return tenNganh;
    }

    public String getMaToHop() {
        return maToHop;
    }

    public BigDecimal getDiemThi() {
        return diemThi;
    }

    public String getCtQuyDoi() {
        return ctQuyDoi;
    }

    public BigDecimal getDiemThiQuyDoi() {
        return diemThiQuyDoi;
    }

    public BigDecimal getDiemCong() {
        return diemCong;
    }

    public BigDecimal getDiemUuTien() {
        return diemUuTien;
    }

    public BigDecimal getDiemXetTuyen() {
        return diemXetTuyen;
    }

    // ================= SETTER =================

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public void setMaToHop(String maToHop) {
        this.maToHop = maToHop;
    }

    public void setDiemThi(BigDecimal diemThi) {
        this.diemThi = diemThi;
    }

    public void setCtQuyDoi(String ctQuyDoi) {
        this.ctQuyDoi = ctQuyDoi;
    }

    public void setDiemThiQuyDoi(BigDecimal diemThiQuyDoi) {
        this.diemThiQuyDoi = diemThiQuyDoi;
    }

    public void setDiemCong(BigDecimal diemCong) {
        this.diemCong = diemCong;
    }

    public void setDiemUuTien(BigDecimal diemUuTien) {
        this.diemUuTien = diemUuTien;
    }

    public void setDiemXetTuyen(BigDecimal diemXetTuyen) {
        this.diemXetTuyen = diemXetTuyen;
    }
    
        // GETTER
    public boolean getToHopGoc() {
        return toHopGoc;
    }

    public BigDecimal getDoLech() {
        return doLech;
    }

    // SETTER
    public void setToHopGoc(boolean toHopGoc) {
        this.toHopGoc = toHopGoc;
    }

    public void setDoLech(BigDecimal doLech) {
        this.doLech = doLech;
    }

    @Override
    public String toString() {
        return "TinhDiemDGNL{" +
                "tenNganh='" + tenNganh + '\'' +
                ", maToHop='" + maToHop + '\'' +
                ", diemThi=" + diemThi +
                ", ctQuyDoi='" + ctQuyDoi + '\'' +
                ", diemThiQuyDoi=" + diemThiQuyDoi +
                ", diemCong=" + diemCong +
                ", diemUuTien=" + diemUuTien +
                ", diemXetTuyen=" + diemXetTuyen +
                '}';
    }
}