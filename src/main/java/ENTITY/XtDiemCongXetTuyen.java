package ENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemcongxetuyen")
public class XtDiemCongXetTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemcong")
    private int idDiemCong;

    @Column(name = "ts_cccd", nullable = false)
    private String tsCccd;  // CCCD của thí sinh

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "matohop")
    private String maToHop;

    @Column(name = "phuongthuc")
    private String phuongThuc;

    @Column(name = "diemCC", precision = 6, scale = 2)
    private BigDecimal diemCC;  // Điểm cộng từ chứng chỉ

    @Column(name = "diemUtxt", precision = 6, scale = 2)
    private BigDecimal diemUtxt;  // Điểm ưu tiên xét tuyển

    @Column(name = "diemTong", precision = 6, scale = 2)
    private BigDecimal diemTong;  // Tổng điểm

    @Column(name = "ghichu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "dc_keys", nullable = false)
    private String dcKeys;  // Khóa ghép định danh duy nhất

    public XtDiemCongXetTuyen() {}

    // Getter & Setter
    public int getIdDiemCong() {
        return idDiemCong;
    }

    public void setIdDiemCong(int idDiemCong) {
        this.idDiemCong = idDiemCong;
    }

    public String getTsCccd() {
        return tsCccd;
    }

    public void setTsCccd(String tsCccd) {
        this.tsCccd = tsCccd;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getMaToHop() {
        return maToHop;
    }

    public void setMaToHop(String maToHop) {
        this.maToHop = maToHop;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public BigDecimal getDiemCC() {
        return diemCC;
    }

    public void setDiemCC(BigDecimal diemCC) {
        this.diemCC = diemCC;
    }

    public BigDecimal getDiemUtxt() {
        return diemUtxt;
    }

    public void setDiemUtxt(BigDecimal diemUtxt) {
        this.diemUtxt = diemUtxt;
    }

    public BigDecimal getDiemTong() {
        return diemTong;
    }

    public void setDiemTong(BigDecimal diemTong) {
        this.diemTong = diemTong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDcKeys() {
        return dcKeys;
    }

    public void setDcKeys(String dcKeys) {
        this.dcKeys = dcKeys;
    }

    // toString
    @Override
    public String toString() {
        return "XtDiemCongXetTuyen{" +
                "idDiemCong=" + idDiemCong +
                ", tsCccd='" + tsCccd + '\'' +
                ", maNganh='" + maNganh + '\'' +
                ", maToHop='" + maToHop + '\'' +
                ", phuongThuc='" + phuongThuc + '\'' +
                ", diemCC=" + diemCC +
                ", diemUtxt=" + diemUtxt +
                ", diemTong=" + diemTong +
                ", ghiChu='" + ghiChu + '\'' +
                ", dcKeys='" + dcKeys + '\'' +
                '}';
    }
}