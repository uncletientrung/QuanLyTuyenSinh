package ENTITY;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ctquyen")
@IdClass(CTQuyenId.class) // dùng class phụ cho khóa chính kép
public class CTQuyen {

    @Id
    @Column(name = "maphanquyen")
    private int maPhanQuyen;

    @Id
    @Column(name = "machucnang")
    private int maChucNang;

    @Column(name = "hanhdong")
    private String hanhDong;

    @Column(name = "trangthai")
    private int trangThai;

    // Constructor rỗng
    public CTQuyen() {
    }

    // Constructor đầy đủ
    public CTQuyen(int maPhanQuyen, int maChucNang, String hanhDong, int trangThai) {
        this.maPhanQuyen = maPhanQuyen;
        this.maChucNang = maChucNang;
        this.hanhDong = hanhDong;
        this.trangThai = trangThai;
    }

    // Getter & Setter
    public int getMaPhanQuyen() {
        return maPhanQuyen;
    }

    public void setMaPhanQuyen(int maPhanQuyen) {
        this.maPhanQuyen = maPhanQuyen;
    }

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    // toString
    @Override
    public String toString() {
        return "CTQuyen{" +
                "maPhanQuyen=" + maPhanQuyen +
                ", maChucNang=" + maChucNang +
                ", hanhDong='" + hanhDong + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}