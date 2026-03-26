package ENTITY;

import jakarta.persistence.*;

@Entity
@Table(name = "taikhoan")
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matk")
    private int matk;

    @Column(name = "tendangnhap")
    private String tendangnhap;

    @Column(name = "matkhau")
    private String matkhau;

    @Column(name = "maphanquyen")
    private int maphanquyen;

    @Column(name = "trangthai")
    private int trangthai;

    // Constructor rỗng
    public TaiKhoan() {
    }

    // Constructor đầy đủ
    public TaiKhoan(int matk, String tendangnhap, String matkhau, int maphanquyen, int trangthai) {
        this.matk = matk;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.maphanquyen = maphanquyen;
        this.trangthai = trangthai;
    }

    // Constructor không có ID
    public TaiKhoan(String tendangnhap, String matkhau, int maphanquyen, int trangthai) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.maphanquyen = maphanquyen;
        this.trangthai = trangthai;
    }

    // Getter và Setter
    public int getMatk() {
        return matk;
    }

    public void setMatk(int matk) {
        this.matk = matk;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getMaphanquyen() {
        return maphanquyen;
    }

    public void setMaphanquyen(int maphanquyen) {
        this.maphanquyen = maphanquyen;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    // toString
    @Override
    public String toString() {
        return "TaiKhoan{" +
                "matk=" + matk +
                ", tendangnhap='" + tendangnhap + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", maphanquyen=" + maphanquyen +
                ", trangthai=" + trangthai +
                '}';
    }
}