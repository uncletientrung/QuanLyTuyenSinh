package ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_khuvuc")
public class XtKhuvuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhuvuc")
    private int idkhuvuc;

    @Column(name = "makv", nullable = false, length = 45)
    private String makv;

    @Column(name = "tenkhuvuc", nullable = false, length = 100)
    private String tenkhuvuc;

    @Column(name = "diemuutien", nullable = false, precision = 8, scale = 2)
    private BigDecimal diemuutien;

    @Column(name = "trangthai", nullable = false)
    private int trangthai = 1;

    public XtKhuvuc() {}

    // ==================== GETTER & SETTER ====================

    public int getIdkhuvuc() {
        return idkhuvuc;
    }

    public void setIdkhuvuc(int idkhuvuc) {
        this.idkhuvuc = idkhuvuc;
    }

    public String getMakv() {
        return makv;
    }

    public void setMakv(String makv) {
        this.makv = makv;
    }

    public String getTenkhuvuc() {
        return tenkhuvuc;
    }

    public void setTenkhuvuc(String tenkhuvuc) {
        this.tenkhuvuc = tenkhuvuc;
    }

    public BigDecimal getDiemuutien() {
        return diemuutien;
    }

    public void setDiemuutien(BigDecimal diemuutien) {
        this.diemuutien = diemuutien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return "XtKhuvuc{" +
                "idkhuvuc=" + idkhuvuc +
                ", makv='" + makv + '\'' +
                ", tenkhuvuc='" + tenkhuvuc + '\'' +
                ", diemuutien=" + diemuutien +
                ", trangthai=" + trangthai +
                '}';
    }
}