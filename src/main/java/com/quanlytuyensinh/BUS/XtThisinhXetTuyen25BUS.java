package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class XtThisinhXetTuyen25BUS {

    private final XtThisinhXetTuyen25DAO TSDAO = XtThisinhXetTuyen25DAO.getInstance();
    private List<XtThisinhXetTuyen25> listThiSinh;

    public XtThisinhXetTuyen25BUS() {
        listThiSinh = TSDAO.getAll();
        if (listThiSinh == null) {
            listThiSinh = new ArrayList<>();
        }

    }

    public List<XtThisinhXetTuyen25> getAllThiSinh() {
        return listThiSinh;
    }
    public boolean insertThiSinh(XtThisinhXetTuyen25 ts) { // Thêm thí sinh
        if (ts == null) return false;
        ts.setSobaodanh(generateSBD());
        XtThisinhXetTuyen25 TS_DaThem = TSDAO.insert(ts); // Trả về thí sinh có id auto 
        if (TS_DaThem != null) {
            listThiSinh.add(TS_DaThem);
            return true;
        }

        return false;
    }
    
    public HashMap<String, Integer> thongKeThiSinhKhuVuc(){
        HashMap<String, Integer> rs = new HashMap<>();
        for(XtThisinhXetTuyen25 ts : this.listThiSinh){
            String kv = ts.getKhuVuc();
            if(kv == null || kv.equals("")){
                kv = "Không có";
            }
            if(kv.equals("01") || kv.equals("1")){
                kv = "1";
            }
            if(kv.equals("02") || kv.equals("2")){
                kv = "2";
            }
            if(kv.equals("02NT") || kv.equals("2NT")){
                kv = "2NT";
            }
            if(kv.equals("03") || kv.equals("3")){
                kv = "3";
            }            
            rs.put(kv, rs.getOrDefault(kv, 0) + 1);
        }
        return rs;
    }
    public HashMap<String, Integer> thongKeThiSinhNamNu(){
        HashMap<String, Integer> rs = new HashMap<>();
        for(XtThisinhXetTuyen25 ts : this.listThiSinh){
            String gioiTinh = ts.getGioiTinh();
            rs.put(gioiTinh, rs.getOrDefault(gioiTinh, 0) + 1);
        }
        return rs;
    }
    public HashMap<String, Integer> thongKeThiSinhDoiTuong(){
        HashMap<String, Integer> rs = new HashMap<>();
        for(XtThisinhXetTuyen25 ts : this.listThiSinh){
            String doiTuong = ts.getDoiTuong();
            if(doiTuong == null || doiTuong.equals("")){
                doiTuong = "Không có";
            }
            if(doiTuong.startsWith("07")){
                doiTuong = "07";
            }
            if(doiTuong.startsWith("06")){
                doiTuong = "06";
            }
            if(doiTuong.startsWith("05")){
                doiTuong = "05";
            }
            if(doiTuong.startsWith("04")){
                doiTuong = "04";
            }
            if(doiTuong.startsWith("03")){
                doiTuong = "03";
            }
            if(doiTuong.startsWith("02")){
                doiTuong = "02";
            }
            if(doiTuong.startsWith("01")){
                doiTuong = "01";
            }
            rs.put(doiTuong, rs.getOrDefault(doiTuong, 0) + 1);
        }
        return rs;
    }
    
    public List<Map.Entry<String, Integer>> thongKeThiSinhTinhThanh(){
        HashMap<String, Integer> rs = new HashMap<>();
        for(XtThisinhXetTuyen25 ts : this.listThiSinh){
            String tinhThanh = ts.getNoiSinh();
            if(tinhThanh == null){
                tinhThanh = "Không rõ";
            }
            tinhThanh = tinhThanh.toUpperCase();
            rs.put(tinhThanh, rs.getOrDefault(tinhThanh, 0) + 1);
        }
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(rs.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list;
    }
        
    private String generateSBD() {
        int maxId = 0;
        for (XtThisinhXetTuyen25 ts : listThiSinh) {
            if (ts.getIdthisinh() > maxId) {
                maxId = ts.getIdthisinh();
            }
        }
        int newId = maxId + 1;
        return String.format("BD%03d", newId);
    }
    public boolean checkCCCD(String cccd, int idTS){
        for(XtThisinhXetTuyen25 ts : listThiSinh){
            if (ts.getCccd().equals(cccd) && ts.getIdthisinh() != idTS)  return false; // Đã tồn tại
        }
        return true;
    }
    public boolean updateThiSinh(XtThisinhXetTuyen25 tsUpdate){
        if (tsUpdate == null) return false;
        boolean rs = TSDAO.update(tsUpdate);
        if(rs){
            for (XtThisinhXetTuyen25 ts : listThiSinh) {
                if (tsUpdate.getIdthisinh() == ts.getIdthisinh()) {
                    ts.setCccd(tsUpdate.getCccd());
                    ts.setSobaodanh(tsUpdate.getSobaodanh());
                    ts.setHo(tsUpdate.getHo());
                    ts.setTen(tsUpdate.getTen());
                    ts.setNgaySinh(tsUpdate.getNgaySinh());
                    ts.setDienThoai(tsUpdate.getDienThoai());
                    ts.setPassword(tsUpdate.getPassword());
                    ts.setGioiTinh(tsUpdate.getGioiTinh());
                    ts.setEmail(tsUpdate.getEmail());
                    ts.setNoiSinh(tsUpdate.getNoiSinh());
                    ts.setUpdatedAt(tsUpdate.getUpdatedAt());
                    ts.setDoiTuong(tsUpdate.getDoiTuong());
                    ts.setKhuVuc(tsUpdate.getKhuVuc());
                    break;
                }
            }
            return true;
        }
        return false;
    }
    public boolean deleteThiSinh(int IdThiSinh){
        if(IdThiSinh <=0) return false;
        boolean rs = TSDAO.delete(IdThiSinh);
        if (rs) {
            listThiSinh.removeIf(ts -> ts.getIdthisinh() == IdThiSinh);
            return true;
        }
        return false;  
    }
   public List<XtThisinhXetTuyen25> searchThiSinh(String keyword, String searchType) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return listThiSinh;
        }
        String key = keyword.trim().toLowerCase();
        List<XtThisinhXetTuyen25> result = new ArrayList<>();

        for (XtThisinhXetTuyen25 ts : listThiSinh) {
            switch (searchType) {

                case "Mã":
                    if (String.valueOf(ts.getIdthisinh()).contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Căn cước CD":
                    if (ts.getCccd() != null && ts.getCccd().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "SBD":
                    if (ts.getSobaodanh() != null && ts.getSobaodanh().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Họ Tên":
                    String fullName = (ts.getHo() + " " + ts.getTen()).toLowerCase();
                    if (fullName.contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Số điện thoại":
                    if (ts.getDienThoai() != null && ts.getDienThoai().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Email":
                    if (ts.getEmail() != null && ts.getEmail().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Nơi sinh":
                    if (ts.getNoiSinh() != null && ts.getNoiSinh().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                case "Khu vực":
                    if (ts.getKhuVuc() != null && ts.getKhuVuc().toLowerCase().contains(key)) {
                        result.add(ts);
                    }
                    break;

                default: // Tất cả
                    if(ts.toString().toLowerCase().contains(key)){
                        result.add(ts);
                    }
                    break;
            }
        }

        return result;
    }
   
   public BigDecimal getMucDiemUuTienTheoQuyDinh(String cccd){ // Trả về mức điểm ưu tiên
        if (cccd == null || cccd.isEmpty()) {
            return BigDecimal.ZERO;
        }
        XtThisinhXetTuyen25 thiSinh = TSDAO.findByCCCD(cccd);
        if (thiSinh == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal diemUT = BigDecimal.ZERO;

        // Tính điểm đối tượng
        String dt = thiSinh.getDoiTuong();
        if (dt != null) {
            if (dt.equals("01") || dt.equals("02") || dt.equals("03") || dt.equals("04") || dt.equals("05")) { // UT1
                diemUT = diemUT.add(new BigDecimal("2.0"));
            } else if (dt.startsWith("06") || dt.startsWith("07") ) { // UT2
                diemUT = diemUT.add(new BigDecimal("1.0")); 
            }
        }
        // Tính điểm khu vực
        String kv = thiSinh.getKhuVuc();
        if (kv != null) {
            switch (kv) {
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

    public BigDecimal getDiemUuTienByCCCD( String cccd, BigDecimal diemTHXT, BigDecimal diemCong ) {
        if (cccd == null || cccd.isEmpty()) {
            return BigDecimal.ZERO;
        }
        if(diemTHXT.add(diemCong).compareTo(new BigDecimal("30")) >= 0) return  BigDecimal.ZERO; // Nếu điểm >= 30 thì điểm ưu tiên là 0
        
        BigDecimal MucDiemUuTien = this.getMucDiemUuTienTheoQuyDinh(cccd);

        // Tính tổng điểm ưu tiên
        BigDecimal tong = diemTHXT.add(diemCong);
        if (tong.compareTo(new BigDecimal("22.5")) < 0){ // Nếu bé hơn 22.5
            return MucDiemUuTien;
        }
        
        BigDecimal heSo = new BigDecimal("30")
                .subtract(tong)
                .divide(new BigDecimal("7.5"), 2, RoundingMode.HALF_UP);

        return heSo.multiply(MucDiemUuTien).setScale(2, RoundingMode.HALF_UP);
    }

}