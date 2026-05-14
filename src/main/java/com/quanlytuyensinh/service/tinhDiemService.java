/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.service;

import com.quanlytuyensinh.BUS.XtBangQuyDoiBUS;
import com.quanlytuyensinh.DAO.XtBangQuyDoiDAO;
import com.quanlytuyensinh.DAO.XtDiemCongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtDiemThiXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtNganhDAO;
import com.quanlytuyensinh.DAO.XtNganhToHopDAO;
import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.DAO.XtToHopMonThiDAO;
import com.quanlytuyensinh.ENTITY.KetQuaTraCuuDTO;
import com.quanlytuyensinh.ENTITY.KetQuaTraCuuVSATDTO;
import com.quanlytuyensinh.ENTITY.TinhDiemDGNL;
import com.quanlytuyensinh.ENTITY.TinhDiemTHPTDTO;
import com.quanlytuyensinh.ENTITY.TinhDiemVSAT;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@Service
public class tinhDiemService {
    private final XtDiemThiXetTuyenDAO diemDAO = XtDiemThiXetTuyenDAO.getInstance(); // Điểm thi
    private List<XtDiemThiXetTuyen> listDiem;
    private final XtNganhDAO nganhDAO = XtNganhDAO.getInstance(); // Ngành
    private List<XtNganh> listNganh;
    private final XtNguyenVongXetTuyenDAO nvDAO = XtNguyenVongXetTuyenDAO.getInstance(); // Nguyện vọng
    private List<XtNguyenVongXetTuyen> listNV;
    private final XtNganhToHopDAO nganhToHopDAO = XtNganhToHopDAO.getInstance();  // Ngành tổ hợp
    private List<XtNganhToHop> listNTH;
    private final XtThisinhXetTuyen25DAO TSDAO = XtThisinhXetTuyen25DAO.getInstance(); // Thí sinh
    private List<XtThisinhXetTuyen25> listThiSinh;
    private final XtDiemCongXetTuyenDAO diemCongDAO = XtDiemCongXetTuyenDAO.getInstance(); // Điểm cộng
    private List<XtDiemCongXetTuyen> listDiemCong;
    private final XtBangQuyDoiDAO xtbangquydoiDAO = XtBangQuyDoiDAO.getInstance(); // Bảng quy đổi
    private List<XtBangQuyDoi> listQuyDoi;
        private final XtToHopMonThiDAO tohopDAO = XtToHopMonThiDAO.getInstance(); // Tổ hợp môn thi
//    private List<XtToHopMonThi> listMon;
    
    public tinhDiemService(){
        this.listDiem = this.diemDAO.getAll();
        this.listDiemCong = this.diemCongDAO.getAll();
        this.listNganh = this.nganhDAO.getAll();
        this.listNTH = this.nganhToHopDAO.getAll();
        this.listQuyDoi = this.xtbangquydoiDAO.getAll();
        this.listNV = this.nvDAO.getAll();
        this.listThiSinh = this.TSDAO.getAll();
    }
    
    public List<XtNganh> getListNganh(){
        return listNganh;
    }
    
    public List<XtNganhToHop> getNTHByMaNganh(String maNganh){
        List<XtNganhToHop> rs = new ArrayList<>();
        for(XtNganhToHop nth: this.listNTH){
            if(nth.getManganh().equals(maNganh)){
                rs.add(nth);
            }
        }
        return rs;
    }
    
    public String getTenNganhByMaNganh(String maNganh){
        return this.nganhDAO.getNganhByMaNganh(maNganh).getTennganh();
    }
    
    public BigDecimal getDiemSan(String maNganh){
        return this.nganhDAO.getDiemSanByMaNganh(maNganh);
    }
    
    public BigDecimal DiemUuTienDoiTuong(String dt){
        if (dt == null || dt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal diemUT = BigDecimal.ZERO;
        // Tính điểm đối tượng
        if (dt != null) {
            if (dt.equals("01") || dt.equals("02") || dt.equals("03") || dt.equals("04") || dt.equals("05")) { // UT1
                diemUT = diemUT.add(new BigDecimal("2.0"));
            } else if (dt.startsWith("06") || dt.startsWith("07") ) { // UT2
                diemUT = diemUT.add(new BigDecimal("1.0")); 
            }
        }
        return diemUT;
    }
    public BigDecimal DiemUuTienKhuVuc(String KhuVuc){
        if (KhuVuc == null || KhuVuc.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal diemUT = BigDecimal.ZERO;
        // Tính điểm khu vực
        if (KhuVuc != null) {
            switch (KhuVuc) {
                case "1":
                    diemUT = diemUT.add(new BigDecimal("0.75"));
                    break;
                case "2NT":
                    diemUT = diemUT.add(new BigDecimal("0.50"));
                    break;
                case "2":
                    diemUT = diemUT.add(new BigDecimal("0.25"));
                    break;
            }
        }
        return diemUT;
    }
    
    public BigDecimal getMucDiemUuTienTheoQuyDinh(String kv, String dt){ // Trả về mức điểm ưu tiên
        BigDecimal diemUT = BigDecimal.ZERO;
        diemUT = diemUT.add(this.DiemUuTienDoiTuong(dt));
        diemUT = diemUT.add(this.DiemUuTienKhuVuc(kv));
        return diemUT;
   }
    
    public BigDecimal getDiemUuTien( String kv, String dt, BigDecimal diemTHXT, BigDecimal diemCong ) { // Tính điểm ưu tiên
        if (kv == null || kv.isEmpty() || dt == null || dt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        if(diemTHXT.add(diemCong).compareTo(new BigDecimal("30")) >= 0) return  BigDecimal.ZERO; // Nếu điểm >= 30 thì điểm ưu tiên là 0
        BigDecimal MucDiemUuTien = this.getMucDiemUuTienTheoQuyDinh(kv, dt);

        // Tính tổng điểm ưu tiên
        BigDecimal tong = diemTHXT.add(diemCong);
        if (tong.compareTo(new BigDecimal("22.5")) < 0){ // Nếu bé hơn 22.5
            return MucDiemUuTien;
        }

        BigDecimal heSo = new BigDecimal("30")
                .subtract(tong)
                .divide(new BigDecimal("7.5"), 5, RoundingMode.HALF_UP);
        return heSo.multiply(MucDiemUuTien).setScale(5, RoundingMode.HALF_UP);
    }
    
//    // Tính điểm xét ngưỡng
//    public BigDecimal getDiemTHXT(XtDiemThiXetTuyen tsGiaLap){
//        
//    }
    private BigDecimal getDiemSafe(BigDecimal val) {
        return val == null ? BigDecimal.ZERO : val;
    }
    
    private BigDecimal getDiemByMon(String monHoc, XtDiemThiXetTuyen d) {
        if (monHoc == null) return BigDecimal.ZERO;
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
            case "N1":
                if (d.getN1Cc() != null && d.getN1Thi() != null)
                    return d.getN1Cc().compareTo(d.getN1Thi()) > 0 ? d.getN1Cc() : d.getN1Thi();
                return getDiemSafe(d.getN1Thi());
        }
        
        return BigDecimal.ZERO;
    }
    
    public XtDiemThiXetTuyen getDiemThiGiaLap(BigDecimal toan, BigDecimal nguVan, BigDecimal vatLy, BigDecimal hoaHoc, 
            BigDecimal sinhHoc, BigDecimal tiengAnh, BigDecimal lichSu, BigDecimal diaLy, BigDecimal tinHoc, BigDecimal ktpl, BigDecimal cnCongNghiep, 
            BigDecimal cnNongNghiep){
        XtDiemThiXetTuyen diemThiGiaLap = new XtDiemThiXetTuyen();
        diemThiGiaLap.setCccd(null);
        diemThiGiaLap.setDPhuongthuc("THPT");
        diemThiGiaLap.setTo(toan);
        diemThiGiaLap.setVa(nguVan);
        diemThiGiaLap.setLi(vatLy);
        diemThiGiaLap.setHo(hoaHoc);
        diemThiGiaLap.setSi(sinhHoc);
        diemThiGiaLap.setN1Thi(tiengAnh);
        diemThiGiaLap.setSu(lichSu);
        diemThiGiaLap.setDi(diaLy);
        diemThiGiaLap.setTi(tinHoc);
        diemThiGiaLap.setKtpl(ktpl);
        diemThiGiaLap.setCncn(cnCongNghiep);
        diemThiGiaLap.setCnnn(cnNongNghiep);
        // Không dùng
        diemThiGiaLap.setN1Cc(null);
        diemThiGiaLap.setNl1(null);
        diemThiGiaLap.setNk1(null);
        diemThiGiaLap.setNk2(null);
        diemThiGiaLap.setNk3(null);
        diemThiGiaLap.setNk4(null);
        diemThiGiaLap.setNk5(null);
        diemThiGiaLap.setNk6(null);
        
        return diemThiGiaLap;
    }
    public XtDiemThiXetTuyen getDiemThiGiaLapVSAT(BigDecimal toan, BigDecimal nguVan, BigDecimal vatLy, BigDecimal hoaHoc, 
            BigDecimal sinhHoc, BigDecimal tiengAnh, BigDecimal lichSu, BigDecimal diaLy){
        XtDiemThiXetTuyen diemThiGiaLap = new XtDiemThiXetTuyen();
        diemThiGiaLap.setCccd(null);
        diemThiGiaLap.setDPhuongthuc("THPT");
        diemThiGiaLap.setTo(toan);
        diemThiGiaLap.setVa(nguVan);
        diemThiGiaLap.setLi(vatLy);
        diemThiGiaLap.setHo(hoaHoc);
        diemThiGiaLap.setSi(sinhHoc);
        diemThiGiaLap.setN1Thi(tiengAnh);
        diemThiGiaLap.setSu(lichSu);
        diemThiGiaLap.setDi(diaLy);
      
        // Không dùng
        diemThiGiaLap.setN1Cc(null);
        diemThiGiaLap.setNl1(null);
        diemThiGiaLap.setNk1(null);
        diemThiGiaLap.setNk2(null);
        diemThiGiaLap.setNk3(null);
        diemThiGiaLap.setNk4(null);
        diemThiGiaLap.setNk5(null);
        diemThiGiaLap.setNk6(null);
        
        return diemThiGiaLap;
    }
    private String getTenMonHoc(String maMon) {
        if (maMon == null) return "";
            switch (maMon) {
                case "TO": return "Toán";
                case "VA": return "Ngữ văn";
                case "LI": return "Vật lý";
                case "HO": return "Hóa học";
                case "SI": return "Sinh học";
                case "SU": return "Lịch sử";
                case "DI": return "Địa lý";
                case "TI": return "Tin học";
                case "N1": return "Tiếng anh";
                case "GDCD": return "GD Kinh tế Pháp luật";
                case "CNCN": return "CN Công nghiệp";
                case "CNNN": return "CN Nông nghiệp";
                case "KTPL": return "GD Kinh tế Pháp luật";
                default: return maMon;
            }
    }
    
    public String getTHGoc(String maNganh){ // Lẩy tổ hợp gốc
        return this.nganhDAO.getNganhByMaNganh(maNganh).getNTohopgoc();
    }
       
    public  List<TinhDiemTHPTDTO> tinhDiemTHPTTatCaToHop(String maNganh,  BigDecimal toan, BigDecimal nguVan, BigDecimal vatLy, BigDecimal hoaHoc, 
         BigDecimal sinhHoc, BigDecimal tiengAnh, BigDecimal lichSu, BigDecimal diaLy, BigDecimal tinHoc, BigDecimal ktpl, BigDecimal cnCongNghiep, 
         BigDecimal cnNongNghiep, String khuVuc, String doiTuong, BigDecimal diemCong) {
         List<TinhDiemTHPTDTO> listKQTHPT = new ArrayList<>();
         List<XtNganhToHop> listTH =this.getNTHByMaNganh(maNganh);
         String THGoc = getTHGoc(maNganh);
         // Tạo điểm thi Entity giả lập
         XtDiemThiXetTuyen diemThiGiaLap = this.getDiemThiGiaLap( toan, nguVan, vatLy, hoaHoc, sinhHoc, tiengAnh, lichSu, diaLy,
                 tinHoc, ktpl, cnCongNghiep, cnNongNghiep);
         
        for (XtNganhToHop nth : listTH) {
            // Tên tổ hợp môn thi
            String TenTHMT = this.tohopDAO.findByMa(nth.getMatohop()).getTentohop();
            
             BigDecimal tong = BigDecimal.ZERO;
             // Môn 1
             BigDecimal m1 = getDiemByMon(nth.getThMon1(), diemThiGiaLap);
             tong = tong.add(m1.multiply(BigDecimal.valueOf(nth.getHsMon1())));

             // Môn 2
             BigDecimal m2 = getDiemByMon(nth.getThMon2(), diemThiGiaLap);
             tong = tong.add(m2.multiply(BigDecimal.valueOf(nth.getHsMon2())));

             // Môn 3
             BigDecimal m3 = getDiemByMon(nth.getThMon3(), diemThiGiaLap);
             tong = tong.add(m3.multiply(BigDecimal.valueOf(nth.getHsMon3())));

             //Công thức đổi THPT sang THPT hệ 30,  Chia cho tổng hệ số rồi x3
             BigDecimal tongHeSo = BigDecimal.valueOf(  nth.getHsMon1() + nth.getHsMon2() + nth.getHsMon3());
             tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE, 5, RoundingMode.HALF_UP).multiply(new BigDecimal("3"));
             
             // Độ lệch
            BigDecimal doLech =nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();

            // Điểm cộng 
            BigDecimal diemCongXT= diemCong != null ? diemCong : BigDecimal.ZERO;
            
            // Điểm ưu tiên
             BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = this.getDiemUuTien(khuVuc, doiTuong, tong, diemCongXT); // Điểm ưu tiên đã if else 22.5 rồi
             
            // Điểm xét tuyển
            BigDecimal diemTH = tong;
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT).subtract(doLech).setScale(2, RoundingMode.HALF_UP);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) {
                diemXT = new BigDecimal("30");
            }
            
            // Tạo Tính điểm Tổ hợp DTO
            TinhDiemTHPTDTO diemTHDTO = new TinhDiemTHPTDTO();

            // Thông tin tổ hợp
            diemTHDTO.setMaToHop(nth.getMatohop());
            diemTHDTO.setTenToHop(TenTHMT);
            diemTHDTO.setCongThuc(
                "((" + nth.getThMon1() + " * " + nth.getHsMon1() + " + "
                      + nth.getThMon2() + " * " + nth.getHsMon2() + " + "
                      + nth.getThMon3() + " * " + nth.getHsMon3()
                + ") / " + tongHeSo + ") * 3"
            );
            List<String> dsMon = new ArrayList<>();
            List<String> dsTenMon = new ArrayList<>();
            if (nth.getThMon1() != null && !nth.getThMon1().isEmpty()) {
                dsMon.add(nth.getThMon1());
                dsTenMon.add(getTenMonHoc(nth.getThMon1()));
            }
            if (nth.getThMon2() != null && !nth.getThMon2().isEmpty()) {
                dsMon.add(nth.getThMon2());
                dsTenMon.add(getTenMonHoc(nth.getThMon2()));
            }
            if (nth.getThMon3() != null && !nth.getThMon3().isEmpty()) {
                dsMon.add(nth.getThMon3());
                dsTenMon.add(getTenMonHoc(nth.getThMon3()));
            }
            diemTHDTO.setDsMonThi(dsMon);
            diemTHDTO.setDsTenMonThi(dsTenMon);
            // Điểm các môn
            diemTHDTO.setDiemToan(toan.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemVan(nguVan.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemLy(vatLy.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemHoa(hoaHoc.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemSinh(sinhHoc.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemAnh(tiengAnh.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemSu(lichSu.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemDia(diaLy.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemTin(tinHoc.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemKTPL(ktpl.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemCnCongNghiep(cnCongNghiep.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemCnNongNghiep(cnNongNghiep.setScale(2, RoundingMode.HALF_UP));
            // Điểm xét tuyển
            diemTHDTO.setDiemTHXT(diemTH.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemUuTien(diemUT.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemCong(diemCongXT.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDoLech(doLech.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setDiemXT(diemXT.setScale(2, RoundingMode.HALF_UP));
            diemTHDTO.setToHopGoc(nth.getMatohop().equals(THGoc));

            listKQTHPT.add(diemTHDTO);
    }
        listKQTHPT.sort((a, b) -> b.getDiemXT().compareTo(a.getDiemXT()));
        return listKQTHPT;
    }
    public List<TinhDiemVSAT> tinhdiemxettuyenVSAT(String maNganh,  BigDecimal toan, BigDecimal nguVan, BigDecimal vatLy, BigDecimal hoaHoc, 
         BigDecimal sinhHoc, BigDecimal tiengAnh, BigDecimal lichSu, BigDecimal diaLy, 
          String khuVuc, String doiTuong, BigDecimal diemCong)
    {   
        List<XtNganhToHop> listTHXT = this.getNTHByMaNganh(maNganh);
        XtDiemThiXetTuyen diemThiGiaLap = this.getDiemThiGiaLapVSAT(toan, nguVan,vatLy,hoaHoc,sinhHoc,tiengAnh,lichSu,diaLy);
        XtBangQuyDoiBUS qdbus= new XtBangQuyDoiBUS();
           
        
        XtDiemThiXetTuyen diemquydoi = qdbus.getDiemThiVSATQuyDoi(diemThiGiaLap);
        List<TinhDiemVSAT> listkq = new ArrayList<>();
        String THGoc = getTHGoc(maNganh);
        for(XtNganhToHop thxt : listTHXT)
        {
            String maToHop = thxt.getMatohop();
            String TenTHMT = this.tohopDAO.findByMa(thxt.getMatohop()).getTentohop();
            BigDecimal tong = BigDecimal.ZERO;
             // Môn 1
             BigDecimal m1 = getDiemByMon(thxt.getThMon1(), diemquydoi);
             tong = tong.add(m1.multiply(BigDecimal.valueOf(thxt.getHsMon1())));

             // Môn 2
             BigDecimal m2 = getDiemByMon(thxt.getThMon2(), diemquydoi);
             tong = tong.add(m2.multiply(BigDecimal.valueOf(thxt.getHsMon2())));

             // Môn 3
             BigDecimal m3 = getDiemByMon(thxt.getThMon3(), diemquydoi);
             tong = tong.add(m3.multiply(BigDecimal.valueOf(thxt.getHsMon3())));

             //Công thức đổi THPT sang THPT hệ 30,  Chia cho tổng hệ số rồi x3
             BigDecimal tongHeSo = BigDecimal.valueOf(  thxt.getHsMon1() + thxt.getHsMon2() + thxt.getHsMon3());
             tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE, 5, RoundingMode.HALF_UP).multiply(new BigDecimal("3"));
             
           
              // Độ lệch
            BigDecimal doLech =thxt.getDolech() == null ? BigDecimal.ZERO : thxt.getDolech();
                
            // Điểm cộng 
            BigDecimal diemCongXT= diemCong != null ? diemCong : BigDecimal.ZERO;
            BigDecimal diemUT = BigDecimal.ZERO;
          
           
          diemUT = this.getDiemUuTien(khuVuc, doiTuong, tong, diemCongXT); // Điểm ưu tiên đã if else 22.5 rồi
           BigDecimal diemTH = tong;
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT).subtract(doLech).setScale(2, RoundingMode.HALF_UP);
            
          TinhDiemVSAT dto = new TinhDiemVSAT();
           dto.setMaToHop(thxt.getMatohop());
            dto.setTenToHop(TenTHMT);
            
            dto.setCongThuc(
                "((" + thxt.getThMon1() + " * " + thxt.getHsMon1() + " + "
                      + thxt.getThMon2() + " * " + thxt.getHsMon2() + " + "
                      + thxt.getThMon3() + " * " + thxt.getHsMon3()
                + ") / " + tongHeSo + ") * 3"
            );
           List<String> dsMon = new ArrayList<>();
           List<String> dsTenMon = new ArrayList<>();
            if (thxt.getThMon1() != null && !thxt.getThMon1().isEmpty()) {
                dsMon.add(thxt.getThMon1());
                dsTenMon.add(getTenMonHoc(thxt.getThMon1()));
            }
            if (thxt.getThMon2() != null && !thxt.getThMon2().isEmpty()) {
                dsMon.add(thxt.getThMon2());
                dsTenMon.add(getTenMonHoc(thxt.getThMon2()));
            }
            if (thxt.getThMon3() != null && !thxt.getThMon3().isEmpty()) {
                dsMon.add(thxt.getThMon3());
                dsTenMon.add(getTenMonHoc(thxt.getThMon3()));
            }
          dto.setDsMonThi(dsMon);
            dto.setDsTenMonThi(dsTenMon);
            // Điểm các môn
            dto.setDiemToan(diemquydoi.getTo().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemVan(diemquydoi.getVa().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemLy(diemquydoi.getLi().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemHoa(diemquydoi.getHo().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemSinh(diemquydoi.getSi().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemAnh(diemquydoi.getN1Thi().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemSu(diemquydoi.getSu().setScale(2, RoundingMode.HALF_UP));
            dto.setDiemDia(diemquydoi.getDi().setScale(2, RoundingMode.HALF_UP));
            
         dto.setDiemTHXT(diemTH.setScale(2, RoundingMode.HALF_UP));
            dto.setDiemUuTien(diemUT.setScale(2, RoundingMode.HALF_UP));
            dto.setDiemCong(diemCongXT);
            dto.setDoLech(doLech);
            dto.setDiemXT(diemXT);
            dto.setToHopGoc(thxt.getMatohop().equals(THGoc));
          
          listkq.add(dto);
        }
        listkq.sort((a, b) -> b.getDiemXT().compareTo(a.getDiemXT()));
        return listkq;
        
    }

    public TinhDiemDGNL tinhDiemDGNL(String nganh, String diemCong, String khuVuc, String doiTuong, String diemThi) {
        XtNganh nganhXet = listNganh.stream().filter(n -> nganh.equals(n.getManganh()))
                                            .findAny()
                                            .orElse(null);
        
        if (nganhXet == null) {
            System.out.println("Không tìm thấy mã ngành" + nganh);
            return null;
        } 
        String tenNganh = nganhXet.getTennganh();
        String toHopGoc = nganhXet.getNTohopgoc();

        BigDecimal diemThiDGNL = new BigDecimal(diemThi);

        XtBangQuyDoi quyDoi = listQuyDoi.stream().filter(q -> (q.getDDiema().compareTo(diemThiDGNL) < 0 && q.getDDiemb().compareTo(diemThiDGNL) > 0))
                                                .findAny()
                                                .orElse(null);
        if (quyDoi == null) {
            System.out.println("Không tìm thấy phân vị quy đổi");
            return null;
        }

        String ctQuyDoi = quyDoi.getDDiemc() + " + (" + diemThiDGNL + " - " + quyDoi.getDDiema() + ") / (" + quyDoi.getDDiemb() + " - " + quyDoi.getDDiema() + ") * (" + quyDoi.getDDiemd() + " - " + quyDoi.getDDiemc() + ")";
        BigDecimal diemThiQuyDoi = quyDoi.getDDiemc().add(diemThiDGNL.subtract(quyDoi.getDDiema()).divide(quyDoi.getDDiemb().subtract(quyDoi.getDDiema()), MathContext.DECIMAL128).multiply(quyDoi.getDDiemd().subtract(quyDoi.getDDiemc())));    
        diemThiQuyDoi = diemThiQuyDoi.setScale(2, RoundingMode.HALF_UP);
        BigDecimal diemCongVal = new BigDecimal(diemCong);
        BigDecimal diemUuTien = DiemUuTienKhuVuc(khuVuc).add(DiemUuTienDoiTuong(doiTuong));
        BigDecimal diemXetTuyen = diemThiQuyDoi.add(diemCongVal).add(diemUuTien);
        diemXetTuyen = diemXetTuyen.setScale(2, RoundingMode.HALF_UP);

        return new TinhDiemDGNL(tenNganh, toHopGoc, diemThiDGNL, ctQuyDoi, diemThiQuyDoi, diemCongVal, diemUuTien, diemXetTuyen);
    }
}
