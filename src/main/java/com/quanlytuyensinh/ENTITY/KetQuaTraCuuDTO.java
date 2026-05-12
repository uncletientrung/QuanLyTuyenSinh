package com.quanlytuyensinh.ENTITY;

import java.math.BigDecimal;

public class KetQuaTraCuuDTO {

    private String cccd;
    private String ho;
    private String ten;
    private String ngaySinh;
    private String doiTuong;
    private String khuVuc;

    private String maNganhTrungTuyen;
    private String tenNganh;           // ← lấy từ bảng XtNganh
    private int thuTuNguyenVong;
    private String toHopTrungTuyen;
    private String ketQuaTrungTuyen;
    private String phuongThuc;
    private BigDecimal diemXettuyen;

    public KetQuaTraCuuDTO(String cccd, String ho, String ten, String ngaySinh,
                           String doiTuong, String khuVuc,
                           String maNganhTrungTuyen, String tenNganh,
                           int thuTuNguyenVong, String toHopTrungTuyen,
                           String ketQuaTrungTuyen, String phuongThuc,
                           BigDecimal diemXettuyen,BigDecimal diemchuan) {
        this.cccd = cccd;
        this.ho = ho;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.doiTuong = doiTuong;
        this.khuVuc = khuVuc;
        this.maNganhTrungTuyen = maNganhTrungTuyen;
        this.tenNganh = tenNganh;
        this.thuTuNguyenVong = thuTuNguyenVong;
        this.toHopTrungTuyen = toHopTrungTuyen;
        this.ketQuaTrungTuyen = ketQuaTrungTuyen;
        this.phuongThuc = phuongThuc;
        this.diemXettuyen = diemXettuyen;
    }


    public String getHoTen() {
        return (ho != null ? ho.trim() : "") + " " + (ten != null ? ten.trim() : "");
    }


    public String getToHop() { return toHopTrungTuyen; }


    public String getKetQua() { return ketQuaTrungTuyen; }


    public int getNguyenVong() { return thuTuNguyenVong; }


    public BigDecimal getDiemXetTuyen() { return diemXettuyen; }

  
    public String getTenNganh() { return tenNganh; }

  

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getDoiTuong() { return doiTuong; }
    public void setDoiTuong(String doiTuong) { this.doiTuong = doiTuong; }

    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }

    public String getMaNganhTrungTuyen() { return maNganhTrungTuyen; }
    public void setMaNganhTrungTuyen(String maNganhTrungTuyen) { this.maNganhTrungTuyen = maNganhTrungTuyen; }

    public void setTenNganh(String tenNganh) { this.tenNganh = tenNganh; }

    public int getThuTuNguyenVong() { return thuTuNguyenVong; }
    public void setThuTuNguyenVong(int thuTuNguyenVong) { this.thuTuNguyenVong = thuTuNguyenVong; }

    public String getToHopTrungTuyen() { return toHopTrungTuyen; }
    public void setToHopTrungTuyen(String toHopTrungTuyen) { this.toHopTrungTuyen = toHopTrungTuyen; }

    public String getKetQuaTrungTuyen() { return ketQuaTrungTuyen; }
    public void setKetQuaTrungTuyen(String ketQuaTrungTuyen) { this.ketQuaTrungTuyen = ketQuaTrungTuyen; }

    public String getPhuongThuc() { return phuongThuc; }
    public void setPhuongThuc(String phuongThuc) { this.phuongThuc = phuongThuc; }

    public BigDecimal getDiemXettuyen() { return diemXettuyen; }
    public void setDiemXettuyen(BigDecimal diemXettuyen) { this.diemXettuyen = diemXettuyen; }

}