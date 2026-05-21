package com.quanlytuyensinh.UTIL;

import com.quanlytuyensinh.BUS.*;
import com.quanlytuyensinh.ENTITY.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class NguyenVongImportHelper {

    private final String cccd, maNganh;
    private final XtNganhBUS nganhBUS;
    private final XtNganhToHopBUS nganhTHBUS;
    private final XtDiemCongXetTuyenBUS diemCongBUS;
    private final XtThisinhXetTuyen25BUS tsBUS;
    private final XtDiemThiXetTuyenBUS dtBUS;
    private final XtBangQuyDoiBUS bqdBUS;
    private final List<XtNganhToHop> listNganhTH;
    private final List<XtDiemCongXetTuyen> listDiemCong;
    private final List<XtDiemThiXetTuyen> listDT;
    private final List<XtBangQuyDoi> listBQD;

    // Kết quả sau tinhDiem()
    private BigDecimal maxDiemXT    = BigDecimal.ZERO;
    private BigDecimal bestDiemTH   = BigDecimal.ZERO;
    private BigDecimal bestDiemCong = BigDecimal.ZERO;
    private BigDecimal bestDiemUT   = BigDecimal.ZERO;
    private BigDecimal diemDoLech   = BigDecimal.ZERO;
    private String bestToHop        = "";
    private String bestPhuongThuc   = "";

    public NguyenVongImportHelper(
            String cccd, String maNganh,
            XtNganhBUS nganhBUS, XtNganhToHopBUS nganhTHBUS,
            XtDiemCongXetTuyenBUS diemCongBUS, XtThisinhXetTuyen25BUS tsBUS,
            XtDiemThiXetTuyenBUS dtBUS, XtBangQuyDoiBUS bqdBUS,
            List<XtNganhToHop> listNganhTH, List<XtDiemCongXetTuyen> listDiemCong,
            List<XtDiemThiXetTuyen> listDT, List<XtBangQuyDoi> listBQD) {
        this.cccd = cccd; this.maNganh = maNganh;
        this.nganhBUS = nganhBUS; this.nganhTHBUS = nganhTHBUS;
        this.diemCongBUS = diemCongBUS; this.tsBUS = tsBUS;
        this.dtBUS = dtBUS; this.bqdBUS = bqdBUS;
        this.listNganhTH = listNganhTH; this.listDiemCong = listDiemCong;
        this.listDT = listDT; this.listBQD = listBQD;
    }

    /** Chạy tính điểm — giống upadateField() trong Dialog */
    public void tinhDiem() {
        if (!maNganh.startsWith("7140") && !maNganh.equals("7140114")) {
            tinhDiemDGNL();
            tinhDiemVSAT();
        }
        tinhDiemTHPT();
    }

    // -------- Copy nguyên từ NguyenVongDialog, chỉ đổi field thành local --------

    private BigDecimal getDiemSafe(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val;
    }

    private BigDecimal getDiemByMon(String monHoc, XtDiemThiXetTuyen d) {
        if (monHoc == null || d == null) return BigDecimal.ZERO;
        switch (monHoc) {
            case "TO": return getDiemSafe(d.getTo());
            case "LI": return getDiemSafe(d.getLi());
            case "HO": return getDiemSafe(d.getHo());
            case "SI": return getDiemSafe(d.getSi());
            case "VA": return getDiemSafe(d.getVa());
            case "SU": return getDiemSafe(d.getSu());
            case "DI": return getDiemSafe(d.getDi());
            case "TI": return getDiemSafe(d.getTi());
            case "GDCD": return getDiemSafe(d.getGdcd());
            case "KTPL": return getDiemSafe(d.getKtpl());
            case "CNCN": return getDiemSafe(d.getCncn());
            case "CNNN": return getDiemSafe(d.getCnnn());
            case "NK1": return getDiemSafe(d.getNk1());
            case "NK2": return getDiemSafe(d.getNk2());
            case "NK3": return getDiemSafe(d.getNk3());
            case "NK4": return getDiemSafe(d.getNk4());
            case "NK5": return getDiemSafe(d.getNk5());
            case "NK6": return getDiemSafe(d.getNk6());
            case "N1":
                if (d.getN1Cc() != null && d.getN1Thi() != null)
                    return d.getN1Cc().compareTo(d.getN1Thi()) > 0 ? d.getN1Cc() : d.getN1Thi();
                return getDiemSafe(d.getN1Thi());
        }
        return BigDecimal.ZERO;
    }

    private void tinhDiemTHPT() {
        List<XtNganhToHop> listTH = nganhTHBUS.getNTHByMaNganh(maNganh);
        XtDiemThiXetTuyen diemTHPT = dtBUS.getDiemThiTHPTByCCCD(cccd);
        if (listTH == null || diemTHPT == null) return;
        for (XtNganhToHop nth : listTH) {
            BigDecimal tong = BigDecimal.ZERO;
            tong = tong.add(getDiemByMon(nth.getThMon1(), diemTHPT).multiply(BigDecimal.valueOf(nth.getHsMon1())));
            tong = tong.add(getDiemByMon(nth.getThMon2(), diemTHPT).multiply(BigDecimal.valueOf(nth.getHsMon2())));
            tong = tong.add(getDiemByMon(nth.getThMon3(), diemTHPT).multiply(BigDecimal.valueOf(nth.getHsMon3())));
            BigDecimal tongHeSo = BigDecimal.valueOf(nth.getHsMon1() + nth.getHsMon2() + nth.getHsMon3());
            tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE,5, RoundingMode.HALF_UP).multiply(new BigDecimal("3"));
            
            BigDecimal doLech = nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();
            
            XtDiemCongXetTuyen dc = diemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = dc != null && dc.getDiemTong() != null ? dc.getDiemTong() : BigDecimal.ZERO;
            
            BigDecimal diemUT = BigDecimal.ZERO;
            diemUT = tsBUS.getDiemUuTienByCCCD(cccd, tong, diemCong); // Điểm ưu tiên đã if else 22.5 rồi

            BigDecimal diemTH   = tong;
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT).subtract(doLech).setScale(2, RoundingMode.HALF_UP);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) diemXT = new BigDecimal("30");
             updateBest(diemXT, nth.getMatohop(), diemTH, diemCong, diemUT, doLech, "THPT");
        }
    }

    private void tinhDiemVSAT() {
        List<XtNganhToHop> listTH = nganhTHBUS.getNTHByMaNganh(maNganh);
        XtDiemThiXetTuyen diemVSAT = dtBUS.getDiemThiVSATByCCCD(cccd);
        if (listTH == null || diemVSAT == null) return;
        XtDiemThiXetTuyen diemVSATQD = bqdBUS.getDiemThiVSATQuyDoi(diemVSAT);
        for (XtNganhToHop nth : listTH) {
            BigDecimal tong = BigDecimal.ZERO;
            tong = tong.add(getDiemByMon(nth.getThMon1(), diemVSATQD).multiply(BigDecimal.valueOf(nth.getHsMon1())));
            tong = tong.add(getDiemByMon(nth.getThMon2(), diemVSATQD).multiply(BigDecimal.valueOf(nth.getHsMon2())));
            tong = tong.add(getDiemByMon(nth.getThMon3(), diemVSATQD).multiply(BigDecimal.valueOf(nth.getHsMon3())));
            BigDecimal tongHeSo = BigDecimal.valueOf(nth.getHsMon1() + nth.getHsMon2() + nth.getHsMon3());
            tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE, 5, RoundingMode.HALF_UP).multiply(new BigDecimal("3"));
            
            BigDecimal doLech = nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();
            
            XtDiemCongXetTuyen dc = diemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = dc != null && dc.getDiemTong() != null ? dc.getDiemTong() : BigDecimal.ZERO;
            
            BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = tsBUS.getDiemUuTienByCCCD(cccd, tong, diemCong);
             
            BigDecimal diemTH = tong;
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT).subtract(doLech).setScale(2, RoundingMode.HALF_UP);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) diemXT = new BigDecimal("30");
            updateBest(diemXT, nth.getMatohop(), diemTH, diemCong, diemUT, doLech, "VSAT");
        }
    }

    private void tinhDiemDGNL() {
        List<XtNganhToHop> listTH = nganhTHBUS.getNTHByMaNganh(maNganh);
        BigDecimal diemDGNL = dtBUS.getDiemThiDGNLByCCCD(cccd);
        if (listTH == null || diemDGNL == null) return;
        for (XtNganhToHop nth : listTH) {
            BigDecimal diemQD = bqdBUS.getDiemThiDGNLQuyDoi(nth.getMatohop(), diemDGNL);
            
            BigDecimal doLech = nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();
            
            XtDiemCongXetTuyen dc = diemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = dc != null && dc.getDiemTong() != null ? dc.getDiemTong() : BigDecimal.ZERO;
            
            BigDecimal tong = BigDecimal.ZERO;
            
            BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = tsBUS.getDiemUuTienByCCCD(cccd, tong, diemCong);
             
            BigDecimal diemTH = tong.add(diemQD);
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT).subtract(doLech).setScale(2, RoundingMode.HALF_UP);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) diemXT = new BigDecimal("30");
            updateBest(diemXT, nth.getMatohop(), diemTH, diemCong, diemUT, doLech, "DGNL");
        }
    }

    private void updateBest(BigDecimal diemXT, String toHop, BigDecimal diemTH,
                             BigDecimal diemCong, BigDecimal diemUT,
                             BigDecimal doLech, String phuongThuc) {
        if (diemXT.compareTo(maxDiemXT) > 0) {
            maxDiemXT    = diemXT.setScale(2, RoundingMode.HALF_UP);
            bestToHop    = toHop;
            bestDiemTH   = diemTH.setScale(5, RoundingMode.HALF_UP);
            bestDiemCong = diemCong.setScale(5, RoundingMode.HALF_UP);
            bestDiemUT   = diemUT.setScale(5, RoundingMode.HALF_UP);
            diemDoLech   = doLech;
            bestPhuongThuc = phuongThuc;
        }
    }

    // Getters
    public BigDecimal getMaxDiemXT()    { return maxDiemXT; }
    public BigDecimal getBestDiemTH()   { return bestDiemTH; }
    public BigDecimal getBestDiemCong() { return bestDiemCong; }
    public BigDecimal getBestDiemUT()   { return bestDiemUT; }
    public String getBestToHop()        { return bestToHop; }
    public String getBestPhuongThuc()   { return bestPhuongThuc; }
}