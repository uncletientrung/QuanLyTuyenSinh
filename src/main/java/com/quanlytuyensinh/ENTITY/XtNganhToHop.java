package com.quanlytuyensinh.ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh_tohop")
public class XtNganhToHop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "manganh", nullable = false, length = 45)
    private String manganh;

    @Column(name = "matohop", nullable = false, length = 45)
    private String matohop;

    @Column(name = "th_mon1", length = 10)
    private String thMon1;

    @Column(name = "hsmon1")
    private Integer hsMon1;

    @Column(name = "th_mon2", length = 10)
    private String thMon2;

    @Column(name = "hsmon2")
    private Integer hsMon2;

    @Column(name = "th_mon3", length = 10)
    private String thMon3;

    @Column(name = "hsmon3")
    private Integer hsMon3;

    @Column(name = "tb_keys", length = 45)
    private String tbKeys;


    @Column(name = "N1")
    private Boolean n1;

    @Column(name = "`TO`")
    private Boolean to;

    @Column(name = "LI")
    private Boolean li;

    @Column(name = "HO")
    private Boolean ho;

    @Column(name = "SI")
    private Boolean si;

    @Column(name = "VA")
    private Boolean va;

    @Column(name = "SU")
    private Boolean su;

    @Column(name = "DI")
    private Boolean di;

    @Column(name = "TI")
    private Boolean ti;

    @Column(name = "GDCD")
    private Boolean gdcd;

    @Column(name = "KTPL")
    private Boolean ktpl;
    
    @Column(name = "CNCN")
    private Boolean cncn;

    @Column(name = "CNNN")
    private Boolean cnnn;

    @Column(name = "NK1")
    private Boolean nk1;

    @Column(name = "NK2")
    private Boolean nk2;

    @Column(name = "NK3")
    private Boolean nk3;

    @Column(name = "NK4")
    private Boolean nk4;

    @Column(name = "NK5")
    private Boolean nk5;

    @Column(name = "NK6")
    private Boolean nk6;

    @Column(name = "dolech", precision = 6, scale = 2)
    private BigDecimal dolech;

    public XtNganhToHop() {}

    // ==================== GETTER & SETTER ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getManganh() { return manganh; }
    public void setManganh(String manganh) { this.manganh = manganh; }

    public String getMatohop() { return matohop; }
    public void setMatohop(String matohop) { this.matohop = matohop; }

    public String getThMon1() { return thMon1; }
    public void setThMon1(String thMon1) { this.thMon1 = thMon1; }

    public Integer getHsMon1() { return hsMon1; }
    public void setHsMon1(Integer hsMon1) { this.hsMon1 = hsMon1; }

    public String getThMon2() { return thMon2; }
    public void setThMon2(String thMon2) { this.thMon2 = thMon2; }

    public Integer getHsMon2() { return hsMon2; }
    public void setHsMon2(Integer hsMon2) { this.hsMon2 = hsMon2; }

    public String getThMon3() { return thMon3; }
    public void setThMon3(String thMon3) { this.thMon3 = thMon3; }

    public Integer getHsMon3() { return hsMon3; }
    public void setHsMon3(Integer hsMon3) { this.hsMon3 = hsMon3; }

    public String getTbKeys() { return tbKeys; }
    public void setTbKeys(String tbKeys) { this.tbKeys = tbKeys; }

    public Boolean getN1() { return n1; }
    public void setN1(Boolean n1) { this.n1 = n1; }

    public Boolean getTo() { return to; }
    public void setTo(Boolean to) { this.to = to; }

    public Boolean getLi() { return li; }
    public void setLi(Boolean li) { this.li = li; }

    public Boolean getHo() { return ho; }
    public void setHo(Boolean ho) { this.ho = ho; }

    public Boolean getSi() { return si; }
    public void setSi(Boolean si) { this.si = si; }

    public Boolean getVa() { return va; }
    public void setVa(Boolean va) { this.va = va; }

    public Boolean getSu() { return su; }
    public void setSu(Boolean su) { this.su = su; }

    public Boolean getDi() { return di; }
    public void setDi(Boolean di) { this.di = di; }

    public Boolean getTi() { return ti; }
    public void setTi(Boolean ti) { this.ti = ti; }

    public Boolean getGdcd() { return gdcd; }
    public void setGdcd(Boolean gdcd) { this.gdcd = gdcd; }

    public Boolean getKtpl() { return ktpl; }
    public void setKtpl(Boolean ktpl) { this.ktpl = ktpl; }
    
    public Boolean getCncn() { return cncn; }
    public void setCncn(Boolean cncn) { this.cncn = cncn; }

    public Boolean getCnnn() { return cnnn; }
    public void setCnnn(Boolean cnnn) { this.cnnn = cnnn; }

    public Boolean getNk1() { return nk1; }
    public void setNk1(Boolean nk1) { this.nk1 = nk1; }

    public Boolean getNk2() { return nk2; }
    public void setNk2(Boolean nk2) { this.nk2 = nk2; }

    public Boolean getNk3() { return nk3; }
    public void setNk3(Boolean nk3) { this.nk3 = nk3; }

    public Boolean getNk4() { return nk4; }
    public void setNk4(Boolean nk4) { this.nk4 = nk4; }

    public Boolean getNk5() { return nk5; }
    public void setNk5(Boolean nk5) { this.nk5 = nk5; }

    public Boolean getNk6() { return nk6; }
    public void setNk6(Boolean nk6) { this.nk6 = nk6; }

    public BigDecimal getDolech() { return dolech; }
    public void setDolech(BigDecimal dolech) { this.dolech = dolech; }

    @Override
    public String toString() {
        return "XtNganhToHop{" +
                "id=" + id +
                ", manganh='" + manganh + '\'' +
                ", matohop='" + matohop + '\'' +
                ", thMon1='" + thMon1 + '\'' +
                ", hsMon1=" + hsMon1 +
                ", thMon2='" + thMon2 + '\'' +
                ", hsMon2=" + hsMon2 +
                ", thMon3='" + thMon3 + '\'' +
                ", hsMon3=" + hsMon3 +
                ", tbKeys='" + tbKeys + '\'' +
                ", n1=" + n1 +
                ", to=" + to +
                ", li=" + li +
                ", ho=" + ho +
                ", si=" + si +
                ", va=" + va +
                ", su=" + su +
                ", di=" + di +
                ", ti=" + ti +
                ", gdcd=" + gdcd +
                ", ktpl=" + ktpl +
                
                ", cncn=" + cncn +
                ", cnnn=" + cnnn +
                
                ", nk1=" + nk1 +
                ", nk2=" + nk2 +
                ", nk3=" + nk3 +
                ", nk4=" + nk4 +
                ", nk5=" + nk5 +
                ", nk6=" + nk6 +
                ", dolech=" + dolech +
                '}';
    }
}