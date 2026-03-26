package ENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "phanquyen")
public class PhanQuyen {

    @Id
    @Column(name = "maphanquyen")
    private int maPhanQuyen;

    @Column(name = "tenphanquyen", nullable = false)
    private String tenPhanQuyen;

    @Column(name = "trangthai")
    private int trangThai;

    // Constructor không tham số
    public PhanQuyen() {
    }

    // Constructor có tham số
    public PhanQuyen(int maPhanQuyen, String tenPhanQuyen, int trangThai) {
        this.maPhanQuyen = maPhanQuyen;
        this.tenPhanQuyen = tenPhanQuyen;
        this.trangThai = trangThai;
    }

    // Getter & Setter
    public int getMaPhanQuyen() {
        return maPhanQuyen;
    }

    public void setMaPhanQuyen(int maPhanQuyen) {
        this.maPhanQuyen = maPhanQuyen;
    }

    public String getTenPhanQuyen() {
        return tenPhanQuyen;
    }

    public void setTenPhanQuyen(String tenPhanQuyen) {
        this.tenPhanQuyen = tenPhanQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "PhanQuyen{" +
                "maPhanQuyen=" + maPhanQuyen +
                ", tenPhanQuyen='" + tenPhanQuyen + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}