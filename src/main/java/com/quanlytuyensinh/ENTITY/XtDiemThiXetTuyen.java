package com.quanlytuyensinh.ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemthixettuyen")
public class XtDiemThiXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemthi")
    private int iddiemthi;

    @Column(name = "cccd", nullable = false, unique = true, length = 20)
    private String cccd;

    @Column(name = "d_phuongthuc", length = 10)
    private String dPhuongthuc;

    @Column(name = "[TO]", precision = 8, scale = 2)
    private BigDecimal to;

    @Column(name = "LI", precision = 8, scale = 2)
    private BigDecimal li;

    @Column(name = "HO", precision = 8, scale = 2)
    private BigDecimal ho;

    @Column(name = "SI", precision = 8, scale = 2)
    private BigDecimal si;

    @Column(name = "SU", precision = 8, scale = 2)
    private BigDecimal su;

    @Column(name = "DI", precision = 8, scale = 2)
    private BigDecimal di;

    @Column(name = "VA", precision = 8, scale = 2)
    private BigDecimal va;

    @Column(name = "GDCD", precision = 8, scale = 2)
    private BigDecimal gdcd;

    @Column(name = "N1_THI", precision = 8, scale = 2)
    private BigDecimal n1Thi;

    @Column(name = "N1_CC", precision = 8, scale = 2)
    private BigDecimal n1Cc;

    @Column(name = "CNCN", precision = 8, scale = 2)
    private BigDecimal cncn;

    @Column(name = "CNNN", precision = 8, scale = 2)
    private BigDecimal cnnn;

    @Column(name = "TI", precision = 8, scale = 2)
    private BigDecimal ti;

    @Column(name = "KTPL", precision = 8, scale = 2)
    private BigDecimal ktpl;

    @Column(name = "NL1", precision = 8, scale = 2)
    private BigDecimal nl1;

    @Column(name = "NK1", precision = 8, scale = 2)
    private BigDecimal nk1;

    @Column(name = "NK2", precision = 8, scale = 2)
    private BigDecimal nk2;

    @Column(name = "NK3", precision = 8, scale = 2)
    private BigDecimal nk3;

    @Column(name = "NK4", precision = 8, scale = 2)
    private BigDecimal nk4;

    @Column(name = "NK5", precision = 8, scale = 2)
    private BigDecimal nk5;

    @Column(name = "NK6", precision = 8, scale = 2)
    private BigDecimal nk6;

    //Constructor cho THPT
    public XtDiemThiXetTuyen(String cccd, String dPhuongthuc, BigDecimal to, BigDecimal li, BigDecimal ho,
            BigDecimal si, BigDecimal su, BigDecimal di, BigDecimal va, BigDecimal gdcd, BigDecimal n1Thi,
            BigDecimal cncn, BigDecimal cnnn, BigDecimal ti, BigDecimal ktpl, BigDecimal nk1, BigDecimal nk2,
            BigDecimal nk3, BigDecimal nk4, BigDecimal nk5, BigDecimal nk6) {
        this.cccd = cccd;
        this.dPhuongthuc = dPhuongthuc;
        this.to = to;
        this.li = li;
        this.ho = ho;
        this.si = si;
        this.su = su;
        this.di = di;
        this.va = va;
        this.gdcd = gdcd;
        this.n1Thi = n1Thi;
        this.cncn = cncn;
        this.cnnn = cnnn;
        this.ti = ti;
        this.ktpl = ktpl;
        this.nk1 = nk1;
        this.nk2 = nk2;
        this.nk3 = nk3;
        this.nk4 = nk4;
        this.nk5 = nk5;
        this.nk6 = nk6;
    }

    public XtDiemThiXetTuyen() {}

    // ==================== GETTER & SETTER ====================

    public int getIddiemthi() {
        return iddiemthi;
    }

    public void setIddiemthi(int iddiemthi) {
        this.iddiemthi = iddiemthi;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getDPhuongthuc() {
        return dPhuongthuc;
    }

    public void setDPhuongthuc(String dPhuongthuc) {
        this.dPhuongthuc = dPhuongthuc;
    }

    public BigDecimal getTo() {
        return to;
    }

    public void setTo(BigDecimal to) {
        this.to = to;
    }

    public BigDecimal getLi() {
        return li;
    }

    public void setLi(BigDecimal li) {
        this.li = li;
    }

    public BigDecimal getHo() {
        return ho;
    }

    public void setHo(BigDecimal ho) {
        this.ho = ho;
    }

    public BigDecimal getSi() {
        return si;
    }

    public void setSi(BigDecimal si) {
        this.si = si;
    }

    public BigDecimal getSu() {
        return su;
    }

    public void setSu(BigDecimal su) {
        this.su = su;
    }

    public BigDecimal getDi() {
        return di;
    }

    public void setDi(BigDecimal di) {
        this.di = di;
    }

    public BigDecimal getVa() {
        return va;
    }

    public BigDecimal getGdcd() {
        return gdcd;
    }

    public void setGdcd(BigDecimal gdcd) {
        this.gdcd = gdcd;
    }

    public void setVa(BigDecimal va) {
        this.va = va;
    }

    public BigDecimal getN1Thi() {
        return n1Thi;
    }

    public void setN1Thi(BigDecimal n1Thi) {
        this.n1Thi = n1Thi;
    }

    public BigDecimal getN1Cc() {
        return n1Cc;
    }

    public void setN1Cc(BigDecimal n1Cc) {
        this.n1Cc = n1Cc;
    }

    public BigDecimal getCncn() {
        return cncn;
    }

    public void setCncn(BigDecimal cncn) {
        this.cncn = cncn;
    }

    public BigDecimal getCnnn() {
        return cnnn;
    }

    public void setCnnn(BigDecimal cnnn) {
        this.cnnn = cnnn;
    }

    public BigDecimal getTi() {
        return ti;
    }

    public void setTi(BigDecimal ti) {
        this.ti = ti;
    }

    public BigDecimal getKtpl() {
        return ktpl;
    }

    public void setKtpl(BigDecimal ktpl) {
        this.ktpl = ktpl;
    }

    public BigDecimal getNl1() {
        return nl1;
    }

    public void setNl1(BigDecimal nl1) {
        this.nl1 = nl1;
    }

    public BigDecimal getNk1() {
        return nk1;
    }

    public void setNk1(BigDecimal nk1) {
        this.nk1 = nk1;
    }

    public BigDecimal getNk2() {
        return nk2;
    }

    public void setNk2(BigDecimal nk2) {
        this.nk2 = nk2;
    }

    public BigDecimal getNk3() {
        return nk3;
    }

    public void setNk3(BigDecimal nk3) {
        this.nk3 = nk3;
    }

    public BigDecimal getNk4() {
        return nk4;
    }

    public void setNk4(BigDecimal nk4) {
        this.nk4 = nk4;
    }

    public BigDecimal getNk5() {
        return nk5;
    }

    public void setNk5(BigDecimal nk5) {
        this.nk5 = nk5;
    }

    public BigDecimal getNk6() {
        return nk6;
    }

    public void setNk6(BigDecimal nk6) {
        this.nk6 = nk6;
    }

    @Override
    public String toString() {
        return "XtDiemThiXetTuyen{" +
                "iddiemthi=" + iddiemthi +
                ", cccd='" + cccd + '\'' +
                ", dPhuongthuc='" + dPhuongthuc + '\'' +
                ", to=" + to +
                ", li=" + li +
                ", ho=" + ho +
                ", si=" + si +
                ", su=" + su +
                ", di=" + di +
                ", va=" + va +
                ", n1Thi=" + n1Thi +
                ", n1Cc=" + n1Cc +
                ", cncn=" + cncn +
                ", cnnn=" + cnnn +
                ", ti=" + ti +
                ", ktpl=" + ktpl +
                ", nl1=" + nl1 +
                ", nk1=" + nk1 +
                ", nk2=" + nk2 +
                '}';
    }
}