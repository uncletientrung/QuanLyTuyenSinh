package com.quanlytuyensinh.ENTITY;

import java.math.BigDecimal;

public class TinhDiemDGNL {
    private String tenNganh;
    private String toHopGoc;
    private BigDecimal diemThi;
    private String ctQuyDoi;
    private BigDecimal diemThiQuyDoi;
    private BigDecimal diemCong;
    private BigDecimal diemUuTien;
    private BigDecimal diemXetTuyen;

    public TinhDiemDGNL(String tenNganh, String toHopGoc, BigDecimal diemThi, String ctQuyDoi, BigDecimal diemThiQuyDoi,
            BigDecimal diemCong, BigDecimal diemUuTien, BigDecimal diemXetTuyen) {
        this.tenNganh = tenNganh;
        this.toHopGoc = toHopGoc;
        this.diemThi = diemThi;
        this.ctQuyDoi = ctQuyDoi;
        this.diemThiQuyDoi = diemThiQuyDoi;
        this.diemCong = diemCong;
        this.diemUuTien = diemUuTien;
        this.diemXetTuyen = diemXetTuyen;
    }

    public TinhDiemDGNL() {};

    public String getTenNganh() {
        return tenNganh;
    }

    public String getToHopGoc() {
        return toHopGoc;
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
}