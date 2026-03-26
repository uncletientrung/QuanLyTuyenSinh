package ENTITY;

import java.io.Serializable;
import java.util.Objects;

// Đây là class phụ cho khóa chính kép
public class CTQuyenId implements Serializable {
    private int maPhanQuyen;
    private int maChucNang;

    // Constructor rỗng
    public CTQuyenId() {}

    // Constructor đầy đủ
    public CTQuyenId(int maPhanQuyen, int maChucNang) {
        this.maPhanQuyen = maPhanQuyen;
        this.maChucNang = maChucNang;
    }

    // getters & setters
    public int getMaPhanQuyen() { return maPhanQuyen; }
    public void setMaPhanQuyen(int maPhanQuyen) { this.maPhanQuyen = maPhanQuyen; }
    public int getMaChucNang() { return maChucNang; }
    public void setMaChucNang(int maChucNang) { this.maChucNang = maChucNang; }

    // hashCode và equals bắt buộc
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CTQuyenId)) return false;
        CTQuyenId that = (CTQuyenId) o;
        return maPhanQuyen == that.maPhanQuyen &&
               maChucNang == that.maChucNang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPhanQuyen, maChucNang);
    }
}