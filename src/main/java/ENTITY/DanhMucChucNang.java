package ENTITY;

import jakarta.persistence.*;

@Entity
@Table(name = "danhmuchucnang")
public class DanhMucChucNang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machucnang")
    private int maChucNang;

    @Column(name = "tenchucnang")
    private String tenChucNang;

    @Column(name = "mota")
    private String moTa;

    // Constructor rỗng
    public DanhMucChucNang() {
    }

    // Constructor đầy đủ
    public DanhMucChucNang(int maChucNang, String tenChucNang, String moTa) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
        this.moTa = moTa;
    }

    // Constructor không có ID
    public DanhMucChucNang(String tenChucNang, String moTa) {
        this.tenChucNang = tenChucNang;
        this.moTa = moTa;
    }

    // Getter & Setter
    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    // toString
    @Override
    public String toString() {
        return "DanhMucChucNang{" +
                "maChucNang=" + maChucNang +
                ", tenChucNang='" + tenChucNang + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}