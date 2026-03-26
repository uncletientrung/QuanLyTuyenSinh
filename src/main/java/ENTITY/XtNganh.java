package ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh")
public class XtNganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnganh")
    private int idnganh;

    @Column(name = "manganh", nullable = false, length = 45)
    private String manganh;

    @Column(name = "tennganh", nullable = false, length = 100)
    private String tennganh;

    @Column(name = "n_tohopgoc", length = 3)
    private String nTohopgoc;

    @Column(name = "n_chitieu")
    private int nChitieu;

    @Column(name = "n_diemsan", precision = 10, scale = 2)
    private BigDecimal nDiemsan;

    @Column(name = "n_diemtrungtuyen", precision = 10, scale = 2)
    private BigDecimal nDiemtrungtuyen;

    @Column(name = "n_tuyenthang", length = 1)
    private String nTuyenthang;

    @Column(name = "n_dgnl", length = 1)
    private String nDgnl;

    @Column(name = "n_thpt", length = 1)
    private String nThpt;

    @Column(name = "n_vsat", length = 1)
    private String nVsat;

    @Column(name = "sl_xtt")
    private Integer slXtt;

    @Column(name = "sl_dgnl")
    private Integer slDgnl;

    @Column(name = "sl_vsat")
    private Integer slVsat;

    @Column(name = "sl_thpt", length = 45)
    private String slThpt;

    public XtNganh() {}

    // ==================== GETTER & SETTER ====================

    public int getIdnganh() { return idnganh; }
    public void setIdnganh(int idnganh) { this.idnganh = idnganh; }

    public String getManganh() { return manganh; }
    public void setManganh(String manganh) { this.manganh = manganh; }

    public String getTennganh() { return tennganh; }
    public void setTennganh(String tennganh) { this.tennganh = tennganh; }

    public String getNTohopgoc() { return nTohopgoc; }
    public void setNTohopgoc(String nTohopgoc) { this.nTohopgoc = nTohopgoc; }

    public int getNChitieu() { return nChitieu; }
    public void setNChitieu(int nChitieu) { this.nChitieu = nChitieu; }

    public BigDecimal getNDiemsan() { return nDiemsan; }
    public void setNDiemsan(BigDecimal nDiemsan) { this.nDiemsan = nDiemsan; }

    public BigDecimal getNDiemtrungtuyen() { return nDiemtrungtuyen; }
    public void setNDiemtrungtuyen(BigDecimal nDiemtrungtuyen) { this.nDiemtrungtuyen = nDiemtrungtuyen; }

    public String getNTuyenthang() { return nTuyenthang; }
    public void setNTuyenthang(String nTuyenthang) { this.nTuyenthang = nTuyenthang; }

    public String getNDgnl() { return nDgnl; }
    public void setNDgnl(String nDgnl) { this.nDgnl = nDgnl; }

    public String getNThpt() { return nThpt; }
    public void setNThpt(String nThpt) { this.nThpt = nThpt; }

    public String getNVsat() { return nVsat; }
    public void setNVsat(String nVsat) { this.nVsat = nVsat; }

    public Integer getSlXtt() { return slXtt; }
    public void setSlXtt(Integer slXtt) { this.slXtt = slXtt; }

    public Integer getSlDgnl() { return slDgnl; }
    public void setSlDgnl(Integer slDgnl) { this.slDgnl = slDgnl; }

    public Integer getSlVsat() { return slVsat; }
    public void setSlVsat(Integer slVsat) { this.slVsat = slVsat; }

    public String getSlThpt() { return slThpt; }
    public void setSlThpt(String slThpt) { this.slThpt = slThpt; }

    @Override
    public String toString() {
        return "XtNganh{" +
                "idnganh=" + idnganh +
                ", manganh='" + manganh + '\'' +
                ", tennganh='" + tennganh + '\'' +
                ", nTohopgoc='" + nTohopgoc + '\'' +
                ", nChitieu=" + nChitieu +
                ", nDiemsan=" + nDiemsan +
                ", nDiemtrungtuyen=" + nDiemtrungtuyen +
                ", nTuyenthang='" + nTuyenthang + '\'' +
                ", nDgnl='" + nDgnl + '\'' +
                ", nThpt='" + nThpt + '\'' +
                ", nVsat='" + nVsat + '\'' +
                '}';
    }
}