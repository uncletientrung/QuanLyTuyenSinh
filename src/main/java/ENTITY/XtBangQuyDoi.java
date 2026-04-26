package ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_bangquydoi")
public class XtBangQuyDoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idqd")
    private int idqd;

    @Column(name = "d_phuongthuc", length = 45)
    private String dPhuongthuc;

    @Column(name = "d_tohop", length = 45)
    private String dTohop;

    @Column(name = "d_mon", length = 45)
    private String dMon;

    @Column(name = "d_diema", precision = 6, scale = 2)
    private BigDecimal dDiema;

    @Column(name = "d_diemb", precision = 6, scale = 2)
    private BigDecimal dDiemb;

    @Column(name = "d_diemc", precision = 6, scale = 2)
    private BigDecimal dDiemc;

    @Column(name = "d_diemd", precision = 6, scale = 2)
    private BigDecimal dDiemd;

    @Column(name = "d_maquydoi", length = 45)
    private String dMaQuyDoi;

    @Column(name = "d_phanvi", length = 45)
    private String dPhanvi;

    public XtBangQuyDoi() {}

    // Getter & Setter
    public int getIdqd() {
        return idqd;
    }

    public void setIdqd(int idqd) {
        this.idqd = idqd;
    }

    public String getDPhuongthuc() {
        return dPhuongthuc;
    }

    public void setDPhuongthuc(String dPhuongthuc) {
        this.dPhuongthuc = dPhuongthuc;
    }

    public String getDTohop() {
        return dTohop;
    }

    public void setDTohop(String dTohop) {
        this.dTohop = dTohop;
    }

    public String getDMon() {
        return dMon;
    }

    public void setDMon(String dMon) {
        this.dMon = dMon;
    }

    public BigDecimal getDDiema() {
        return dDiema;
    }

    public void setDDiema(BigDecimal dDiema) {
        this.dDiema = dDiema;
    }

    public BigDecimal getDDiemb() {
        return dDiemb;
    }

    public void setDDiemb(BigDecimal dDiemb) {
        this.dDiemb = dDiemb;
    }

    public BigDecimal getDDiemc() {
        return dDiemc;
    }

    public void setDDiemc(BigDecimal dDiemc) {
        this.dDiemc = dDiemc;
    }

    public BigDecimal getDDiemd() {
        return dDiemd;
    }

    public void setDDiemd(BigDecimal dDiemd) {
        this.dDiemd = dDiemd;
    }

    public String getDMaQuyDoi() {
        return dMaQuyDoi;
    }

    public void setDMaQuyDoi(String dMaQuyDoi) {
        this.dMaQuyDoi = dMaQuyDoi;
    }

    public String getDPhanvi() {
        return dPhanvi;
    }

    public void setDPhanvi(String dPhanvi) {
        this.dPhanvi = dPhanvi;
    }

    // toString()
    @Override
    public String toString() {
        return "XtBangQuyDoi{" +
                "idqd=" + idqd +
                ", dPhuongthuc='" + dPhuongthuc + '\'' +
                ", dTohop='" + dTohop + '\'' +
                ", dMon='" + dMon + '\'' +
                ", dDiema=" + dDiema +
                ", dDiemb=" + dDiemb +
                ", dDiemc=" + dDiemc +
                ", dDiemd=" + dDiemd +
                ", dMaQuyDoi='" + dMaQuyDoi + '\'' +
                ", dPhanvi='" + dPhanvi + '\'' +
                '}';
    }
}