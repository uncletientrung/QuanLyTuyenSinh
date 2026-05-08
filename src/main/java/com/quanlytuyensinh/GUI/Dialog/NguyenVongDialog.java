/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.XtBangQuyDoiBUS;
import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.ENTITY.XtDiemCongXetTuyen;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.VerticalComboBoxForm;
import com.quanlytuyensinh.GUI.Component.VerticalInputForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dell
 */
public class NguyenVongDialog extends JDialog{
    private NguyenVongPanel parent;
    private XtNguyenVongXetTuyenBUS NVBUS;
    private List<XtNguyenVongXetTuyen> listNV;
    private XtNguyenVongXetTuyen currentNV;
    private String type; // "create" hoặc "edit"
    private Runnable onSuccess; // Lưu hàm chạy sau khi xong
    
    // Form fields
    private VerticalInputForm txtThuTu, txtDiemTHXT, txtDiemUT, txtDiemCong, txtDiemXetTuyen, txtDoLech, txtDanhSachTH, txtTHXet, txtNV_Key;
    private VerticalInputForm txtKetQua;
    private VerticalComboBoxForm cbbPhuongThuc, cbbCCCD, cbbMaNganh;
    private ButtonCustom btnLuu, btnHuy;
    private JPanel pnlMain, pnlButtons;
    
    // Tác vụ thêm để xử lý
    private XtNganhBUS NganhBUS;
    private List<XtNganh> listNganh;
    private XtNganhToHopBUS NganhTHBUS;
    private List<XtNganhToHop> listNganhTH;
    private XtDiemCongXetTuyenBUS DiemCongBUS;
    private List<XtDiemCongXetTuyen> listDiemCong;
    private XtThisinhXetTuyen25BUS TSBUS ;
    private List<XtThisinhXetTuyen25> listTS;
    private XtDiemThiXetTuyenBUS DTBUS;
     private List<XtDiemThiXetTuyen> listDT;
    private XtBangQuyDoiBUS BQDBUS = new XtBangQuyDoiBUS();
    private List<XtBangQuyDoi> listBQD;
    
    // Biến lưu tham số hiển thị
    private BigDecimal maxDiemXT = BigDecimal.ZERO;
    private String bestToHop = "";
    private BigDecimal bestDiemTH = BigDecimal.ZERO;
    private BigDecimal diemDoLech = BigDecimal.ZERO;
    private BigDecimal bestDiemCong = BigDecimal.ZERO;
    private BigDecimal bestDiemUT = BigDecimal.ZERO;
    private String bestPhuongThuc ="";
    
    
    public NguyenVongDialog(NguyenVongPanel parent, JFrame owner, String title, String type, boolean modal, Runnable onSuccess, XtNguyenVongXetTuyen nv){
         super(owner, title, modal);
        this.parent = parent;
        NVBUS = parent.getBUS();
        listNV = parent.getListNV();
        this.currentNV = nv;
        this.type = type;
        this.onSuccess = onSuccess;
        this.setTitle(title);
        
        this.NganhBUS = parent.getNganhBUS();
        this.NganhTHBUS = parent.getNganhTHBUS();
        this.DiemCongBUS = parent.getDiemCongBUS();
        this.TSBUS = parent.getTSBUS();
        this.DTBUS = parent.getDTBUS();
        
        this.listDiemCong = parent.getListDiemCong();
        this.listNganhTH = parent.getListNganhTH();
        this.listNganh = parent.getListNganh();
        this.listTS = parent.getListTS();
        this.listDT = parent.getListDT();
        this.listBQD = parent.getListBQD();

        initComponents();
    }
        private void initComponents() {
        this.setSize(800, 750);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initMainPanel();
        initButtonPanel();
        setFieldsDisable();
        if(!this.type.equals("create")){
            this.setNguyenVongData(currentNV);
        }
        

        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void initMainPanel() {
        pnlMain = new JPanel(new GridLayout(1, 2, 40, 0));
        pnlMain.setBorder(new EmptyBorder(25, 40, 25, 40));
        pnlMain.setBackground(Color.WHITE);

        JPanel left = new JPanel(new GridLayout(7, 1, 0, 15));
        JPanel right = new JPanel(new GridLayout(7, 1, 0, 15));
        left.setBackground(Color.WHITE);
        right.setBackground(Color.WHITE);
        // ==================== ĐỊNH NGHĨA CÁC THUỘC TÍNH ====================
        String[] listStrCCCD = new String[this.listTS.size()+1]; // Danh sách CCCD String
        listStrCCCD[0] = "-- Chọn thí sinh --";
        for (int i = 0; i < listTS.size(); i++) {
            listStrCCCD[i + 1] = listTS.get(i).getCccd() + " - " + listTS.get(i).getHo() + " " + listTS.get(i).getTen();
        }
        
         String[] listStrMaNganh = new String[this.listNganh.size()+1]; // Danh sách CCCD String
         listStrMaNganh[0] = "-- Chọn mã ngành --";
        for (int i = 0; i < listNganh.size(); i++) {
            listStrMaNganh[i+1] = listNganh.get(i).getManganh() + " - " + listNganh.get(i).getTennganh();
        }
        
        this.cbbCCCD = new VerticalComboBoxForm("CCCD", listStrCCCD);
        this.cbbMaNganh = new VerticalComboBoxForm("Mã Ngành", listStrMaNganh); 
        this.cbbPhuongThuc = new VerticalComboBoxForm("Phương thức xét tuyển", new String[]{"THPT", "DGNL", "VSAT"}); 
        this.txtThuTu = new VerticalInputForm("Thứ tự nguyện vọng");
        this.txtDanhSachTH =  new VerticalInputForm("Danh sách tổ hợp");
        this.txtTHXet = new VerticalInputForm("Tổ hợp xét tuyển (Cao nhất)"); 
        this.txtDiemTHXT = new VerticalInputForm("Điểm tổ hợp xét tuyển (Đã thêm độ lệch)");
        this.txtDiemUT =  new VerticalInputForm("Điểm ưu tiên");
        this.txtDiemCong = new VerticalInputForm("Điểm cộng"); 
        this.txtDiemXetTuyen = new VerticalInputForm("Điểm xét tuyển"); 
        this.txtDoLech =  new VerticalInputForm("Độ lệch");  
        this.txtNV_Key= new VerticalInputForm("Nguyện vọng Key"); 
        this.txtKetQua= new VerticalInputForm("Kết quả xét tuyển"); 
        Label lbHide = new Label();
        
        // ==================== LEFT COLUMN ====================
        left.add(txtThuTu);
        left.add(cbbCCCD);
        left.add(cbbMaNganh);
        left.add(cbbPhuongThuc);
        left.add(txtDanhSachTH);
        left.add(txtTHXet);
        left.add(lbHide);
        
        // ==================== RIGHT COLUMN ====================
        right.add(txtDiemTHXT);
        right.add(txtDoLech);
        right.add(txtDiemUT);
        right.add(txtDiemCong);
        right.add(txtDiemXetTuyen);
        right.add(txtNV_Key);
        if(this.type.equals("detail")){
            right.add(txtKetQua);
        }
        
        // Gắn listener SAU khi tất cả field đã tạo xong
        bindListeners();
        
        // =======================
        pnlMain.add(left);
        pnlMain.add(right);
    }

    private void initButtonPanel() {
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 0, 25, 0));

        String btnText = "create".equals(type) ? "Thêm nguyện vọng" : "Lưu chỉnh sửa";

        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                 saveNguyenVong();
            }
        });
        btnHuy = new ButtonCustom("Hủy bỏ", "danger", 15);

        btnLuu.setPreferredSize(new Dimension(160, 48));
        btnHuy.setPreferredSize(new Dimension(160, 48));
        btnHuy.addActionListener(e -> dispose());
        
        if(!this.type.equals("detail")){
            pnlButtons.add(btnLuu);
        }
        pnlButtons.add(btnHuy);

    }
    
    // Gắn sự kiện cho cbb và textfield
    private void bindListeners() { 
        this.cbbMaNganh.getComboBox().addActionListener(e -> {
                updateNVKey();
                upadateField();
            });

        this.cbbCCCD.getComboBox().addActionListener(e -> {
                updateNVKey();
                upadateField();
            });

        this.txtThuTu.addTextChangeListener(() -> {
                updateNVKey();
            }
        );
    }
    
    // Set các field là disable
    private void setFieldsDisable(){
        VerticalInputForm[] listInputCreate = {txtDanhSachTH, txtTHXet, txtDiemTHXT, txtDiemUT, txtDiemCong, txtDiemXetTuyen, txtDoLech,
                txtNV_Key};
        for (VerticalInputForm f : listInputCreate) {
            f.setDisable();
        }
        this.cbbPhuongThuc.setDisable();
        if(this.type.equals("detail")){
            this.txtThuTu.setDisable();
            this.cbbCCCD.setDisable();
            this.cbbMaNganh.setDisable();
            this.txtKetQua.setDisable();
        }
    }
    
    // Cập nhật Key
    private void updateNVKey(){
        String cccd = getSelectedCCCD();
        String maNganh = getSelectedMaNganh();
        String thuTuNV = this.txtThuTu.getText();
        if (maNganh == null || cccd == null || thuTuNV.equals("")) {
            this.txtNV_Key.setText("");
        } else {
            this.txtNV_Key.setText( cccd+ "_" + maNganh+ "_" +thuTuNV );
        }
    }
    
     // Hàm trung chuyển tính toán set các field
    private void upadateField(){
        maxDiemXT = BigDecimal.ZERO;
        bestToHop = "";
        bestDiemTH = BigDecimal.ZERO;
        diemDoLech = BigDecimal.ZERO;
        bestDiemCong = BigDecimal.ZERO;
        bestDiemUT = BigDecimal.ZERO;
        bestPhuongThuc ="";
        String cccd = getSelectedCCCD();
        String maNganh = getSelectedMaNganh();
        if (maNganh == null || cccd == null) {
            this.txtDanhSachTH.setText("");
            txtTHXet.setText("");
            txtDiemTHXT.setText("");
            txtDiemCong.setText("");
            txtDiemUT.setText("");
            txtDiemXetTuyen.setText("");
            txtDoLech.setText("");
            return;
        }
        this.txtDanhSachTH.setText(getDanhSachToHop(maNganh));
        if(!maNganh.startsWith("7140")){ // Nếu không phải ngành sư phạm thì chạy
            tinhDiemDGNL(maNganh, cccd);
            tinhDiemVSAT(maNganh, cccd);
        }
        tinhDiemTHPT(maNganh, cccd);
        
        txtTHXet.setText(bestToHop);
        txtDiemTHXT.setText(bestDiemTH.setScale(5).toString());
        txtDiemCong.setText(bestDiemCong.setScale(2).toString());
        txtDiemUT.setText(bestDiemUT.setScale(5).toString());
        txtDiemXetTuyen.setText(maxDiemXT.setScale(5).toString());
        txtDoLech.setText(diemDoLech.setScale(2).toString());   
        this.cbbPhuongThuc.setSelectedValue(this.bestPhuongThuc.toString());
        
        
    }
    
    // Danh sách tổ hợp, có Gốc
    private String getDanhSachToHop(String maNganh){ 
        this.listNganhTH = this.NganhTHBUS.getNTHByMaNganh(maNganh);
        String toHopGoc = this.NganhBUS.getNganhByMaNganh(maNganh).getNTohopgoc();
        if (listNganhTH == null || listNganhTH.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(toHopGoc).append(" (Gốc)"); // Cái gốc đứng đầu
        for (XtNganhToHop th : listNganhTH) {
            String maTH = th.getMatohop();
            if (!maTH.equals(toHopGoc)) {
                result.append(", ").append(maTH);
            }
        }
        return result.toString();
    } 
    
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
   
    // Tính điểm THPT Cao nhất
    private void tinhDiemTHPT(String maNganh, String cccd){
        
        List<XtNganhToHop> listTH = NganhTHBUS.getNTHByMaNganh(maNganh);
        XtDiemThiXetTuyen diemThiTHPT = DTBUS.getDiemThiTHPTByCCCD(cccd);
        if (listTH == null || diemThiTHPT == null) return;
        for (XtNganhToHop nth : listTH) {
            BigDecimal tong = BigDecimal.ZERO;
            // Môn 1
            BigDecimal m1 = getDiemByMon(nth.getThMon1(), diemThiTHPT);
            tong = tong.add(m1.multiply(BigDecimal.valueOf(nth.getHsMon1())));

            // Môn 2
            BigDecimal m2 = getDiemByMon(nth.getThMon2(), diemThiTHPT);
            tong = tong.add(m2.multiply(BigDecimal.valueOf(nth.getHsMon2())));

            // Môn 3
            BigDecimal m3 = getDiemByMon(nth.getThMon3(), diemThiTHPT);
            tong = tong.add(m3.multiply(BigDecimal.valueOf(nth.getHsMon3())));
            
            //Công thức đổi THPT sang THPT hệ 30,  Chia cho tổng hệ số rồi x3
            BigDecimal tongHeSo = BigDecimal.valueOf(
                    nth.getHsMon1()
                    + nth.getHsMon2()
                    + nth.getHsMon3()
            );
            tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE).multiply(new BigDecimal("3"));

            // Độ lệch
            BigDecimal doLech =nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();

            // Điểm cộng 
            XtDiemCongXetTuyen dc = DiemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = BigDecimal.ZERO;
            if (dc != null) {
                diemCong = dc.getDiemTong()== null ? BigDecimal.ZERO : dc.getDiemTong();
            }
            
            // Điểm ưu tiên
             BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = TSBUS.getDiemUuTienByCCCD(cccd, tong.subtract(doLech), diemCong); // Điểm ưu tiên đã if else 22.5 rồi

           // Điểm xét tuyển
            BigDecimal diemTH = tong.subtract(doLech);
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) {
                diemXT = new BigDecimal("30");
            }

            // Lấy Max
            if (diemXT.compareTo(maxDiemXT) > 0) {
                maxDiemXT = diemXT.setScale(2, RoundingMode.HALF_UP);
                bestToHop = nth.getMatohop();
                bestDiemTH = diemTH.setScale(5, RoundingMode.HALF_UP);
                bestDiemCong = diemCong.setScale(5, RoundingMode.HALF_UP);
                bestDiemUT = diemUT.setScale(5, RoundingMode.HALF_UP);
                diemDoLech = doLech;
                bestPhuongThuc = "THPT";
            }
        }
    } 
    // Tính điểm VSAT Cao nhất
    public final void tinhDiemVSAT(String maNganh, String cccd){
        List<XtNganhToHop> listTH = NganhTHBUS.getNTHByMaNganh(maNganh);
        XtDiemThiXetTuyen diemThiVSAT = DTBUS.getDiemThiVSATByCCCD(cccd); // Điểm VSAT chưa quy đổi

        if (listTH == null || diemThiVSAT == null) return;
        XtDiemThiXetTuyen diemThiVSATQuyDoi = this.BQDBUS.getDiemThiVSATQuyDoi(diemThiVSAT); // Điểm VSAT đã quy đổi
        for (XtNganhToHop nth : listTH) {
            BigDecimal tong = BigDecimal.ZERO;
            // Môn 1
            BigDecimal m1 = getDiemByMon(nth.getThMon1(), diemThiVSATQuyDoi);
            tong = tong.add(m1.multiply(BigDecimal.valueOf(nth.getHsMon1())));

            // Môn 2
            BigDecimal m2 = getDiemByMon(nth.getThMon2(), diemThiVSATQuyDoi);
            tong = tong.add(m2.multiply(BigDecimal.valueOf(nth.getHsMon2())));

            // Môn 3
            BigDecimal m3 = getDiemByMon(nth.getThMon3(), diemThiVSATQuyDoi);
            tong = tong.add(m3.multiply(BigDecimal.valueOf(nth.getHsMon3())));
            
            //Công thức đổi VSAT sang THPT hệ 30,  Chia cho tổng hệ số rồi x3
            BigDecimal tongHeSo = BigDecimal.valueOf(
                    nth.getHsMon1()
                    + nth.getHsMon2()
                    + nth.getHsMon3()
            );
            tong = tong.divide(tongHeSo != BigDecimal.ZERO ? tongHeSo : BigDecimal.ONE).multiply(new BigDecimal("3"));

            // Độ lệch
            BigDecimal doLech =nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();
        
            // Điểm cộng 
            XtDiemCongXetTuyen dc = DiemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = BigDecimal.ZERO;
            if (dc != null) {
                diemCong = dc.getDiemTong()== null ? BigDecimal.ZERO : dc.getDiemTong();
            }
            
            // Điểm ưu tiên
             BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = TSBUS.getDiemUuTienByCCCD(cccd, tong.subtract(doLech), diemCong);

           // Điểm xét tuyển
            BigDecimal diemTH = tong.subtract(doLech);
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) {
                diemXT = new BigDecimal("30");
            }
            
            // Lấy Max
            if (diemXT.compareTo(maxDiemXT) > 0) {
                maxDiemXT = diemXT.setScale(2, RoundingMode.HALF_UP);
                bestToHop = nth.getMatohop();
                bestDiemTH = diemTH.setScale(5, RoundingMode.HALF_UP);
                bestDiemCong = diemCong.setScale(5, RoundingMode.HALF_UP);
                bestDiemUT = diemUT.setScale(5, RoundingMode.HALF_UP);
                diemDoLech = doLech;
                bestPhuongThuc = "VSAT";
            }
        }
    } 
    // Tính điểm DGNL
    private void tinhDiemDGNL(String maNganh, String cccd){
        List<XtNganhToHop> listTH = NganhTHBUS.getNTHByMaNganh(maNganh);
        BigDecimal diemThiDGNL = this.DTBUS.getDiemThiDGNLByCCCD(cccd);
        if (listTH == null || diemThiDGNL == null) return;
        for (XtNganhToHop nth : listTH) {
             BigDecimal tong = BigDecimal.ZERO;

             // Điểm quy đổi DGNL
            BigDecimal diemThiDGNLQuyDoi = this.BQDBUS.getDiemThiDGNLQuyDoi(nth.getMatohop(), diemThiDGNL);

            // Độ lệch
            BigDecimal doLech =nth.getDolech() == null ? BigDecimal.ZERO : nth.getDolech();
        
            // Điểm cộng 
            XtDiemCongXetTuyen dc = DiemCongBUS.getDiemCongByKey(cccd, maNganh, nth.getMatohop());
            BigDecimal diemCong = BigDecimal.ZERO;
            if (dc != null) {
                diemCong = dc.getDiemTong()== null ? BigDecimal.ZERO : dc.getDiemTong();
            }
            
            // Điểm ưu tiên
             BigDecimal diemUT = BigDecimal.ZERO;
             diemUT = TSBUS.getDiemUuTienByCCCD(cccd, tong.subtract(doLech), diemCong);

           // Điểm xét tuyển
           
            BigDecimal diemTH = tong.subtract(doLech).add(diemThiDGNLQuyDoi);
            BigDecimal diemXT = diemTH.add(diemCong).add(diemUT);
            if (diemXT.compareTo(new BigDecimal("30")) >= 0) {
                diemXT = new BigDecimal("30");
            }
            
            // Lấy Max
            if (diemXT.compareTo(maxDiemXT) > 0) {
               maxDiemXT = diemXT.setScale(2, RoundingMode.HALF_UP);
                bestToHop = nth.getMatohop();
                bestDiemTH = diemTH.setScale(5, RoundingMode.HALF_UP);
                bestDiemCong = diemCong.setScale(5, RoundingMode.HALF_UP);
                bestDiemUT = diemUT.setScale(5, RoundingMode.HALF_UP);
                diemDoLech = doLech;
                bestPhuongThuc = "DGNL";
            }
        }
    } 
    
    
    private String getSelectedCCCD() {
        Object sel = this.cbbCCCD.getSelectedValue();
        if (sel == null || sel.toString().startsWith("--")) return null;
        String value = sel.toString();
        return value.split(" ")[0];
    }

    private String getSelectedMaNganh() {
         Object sel = this.cbbMaNganh.getSelectedValue();
        if (sel == null || sel.toString().startsWith("--")) return null;
        String value = sel.toString();
        return value.split(" ")[0];
    }
    
    // Kiểm tra điều kiện
    private boolean validateInput() {
        String cccd = getSelectedCCCD();
        String maNganh = getSelectedMaNganh();
        String thuTuStr = txtThuTu.getText();

        // Check rỗng
        if (cccd == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thí sinh");
            return false;
        }
        if (maNganh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã ngành");
            return false;
        }
        if (thuTuStr == null || thuTuStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thứ tự nguyện vọng");
            return false;
        }

        // Check  hợp lệ
        int thuTu;
        try {
            thuTu = Integer.parseInt(thuTuStr.trim());
            if (thuTu <= 0) {
                JOptionPane.showMessageDialog(this, "Thứ tự nguyện vọng phải > 0");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thứ tự nguyện vọng phải là số");
            return false;
        }

        // Check  dữ liệu
        List<XtNguyenVongXetTuyen> listNVCheckTrung = NVBUS.getListNVByCCCD(cccd);
        for (XtNguyenVongXetTuyen nv : listNVCheckTrung) {
            if ("update".equals(type)  && currentNV != null  && nv.getIdnv() == currentNV.getIdnv()) {
                continue;
            }
            if (nv.getNnCccd().equals(cccd) && nv.getNvManganh().equals(maNganh)) {
                JOptionPane.showMessageDialog(this, "Ngành này đã tồn tại trong nguyện vọng");
                return false;
            }
            if (nv.getNnCccd().equals(cccd) && nv.getNvTt()== thuTu) {
                JOptionPane.showMessageDialog(this, "Thứ tự nguyện vọng đã tồn tại");
                return false;
            }

        }
        return true;
    }

    // Hàm thực hiện khi ấn lưu
    private void saveNguyenVong(){
        XtNguyenVongXetTuyen nv = new XtNguyenVongXetTuyen();
        nv.setNnCccd(this.getSelectedCCCD());
        nv.setNvManganh(this.getSelectedMaNganh());
        int thuTu = Integer.parseInt(this.txtThuTu.getText().trim());
        nv.setNvTt(thuTu);
        BigDecimal diemTHXT = new BigDecimal(this.txtDiemTHXT.getText().trim());
        nv.setDiemThxt(diemTHXT);
        BigDecimal diemUT = new BigDecimal(this.txtDiemUT.getText().trim());
        nv.setDiemUtqd(diemUT);
        BigDecimal diemCong = new BigDecimal(this.txtDiemCong.getText().trim());
        nv.setDiemCong(diemCong);
        BigDecimal diemXT =new BigDecimal(this.txtDiemXetTuyen.getText().trim());
        nv.setDiemXettuyen(diemXT);
        nv.setNvKetqua("Đang xét");
        nv.setTtPhuongthuc(this.cbbPhuongThuc.getSelectedValue());
        nv.setNvKeys(this.txtNV_Key.getText().trim());
        nv.setTtThm(this.txtTHXet.getText().trim());
        
        try{
            if ("create".equals(type)) {
                if (NVBUS.insertNguyenVong(nv)) {
                    JOptionPane.showMessageDialog(this, "Thêm nguyện vọng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                    dispose();
                }
            }else if (this.type.equals("update")) {
                nv.setIdnv(this.currentNV.getIdnv()); // Gán id cho nv mới tạo là id sửa
                if(NVBUS.updateNguyenVong(nv)){
                    JOptionPane.showMessageDialog(this, "Sửa thí sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                        dispose();
                }
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
    }

    // Set dữ liệu nguyện nếu detail hoặc update
    private void setNguyenVongData(XtNguyenVongXetTuyen nv){
        String itemCbbCCCD = "-- Chọn thí sinh --";
        for(XtThisinhXetTuyen25 ts : listTS){
            if(ts.getCccd().equals(nv.getNnCccd())){
                itemCbbCCCD = ts.getCccd() +" - " + ts.getHo() + " " + ts.getTen();
                break;
            }
               
        }
        String itemCbbMaNganh =  "-- Chọn mã ngành --";
        for(XtNganh n : listNganh){
            if(n.getManganh().equals(nv.getNvManganh())){
                itemCbbMaNganh = n.getManganh()+" - " + n.getTennganh();
                break;
            }
                
        }
        this.txtThuTu.setText(String.valueOf(nv.getNvTt()));
        this.cbbCCCD.getComboBox().setSelectedItem(itemCbbCCCD);
        this.cbbMaNganh.getComboBox().setSelectedItem(itemCbbMaNganh);
        this.cbbPhuongThuc.getComboBox().setSelectedItem(nv.getTtPhuongthuc());
        this.txtKetQua.setText(nv.getNvKetqua());
        // Sau khi set 3 cái txThuTu với cbbMaNganh và cbbCCCD thì nó chạy hàm upadateField và upadateKeys đỡ viết
    }
    
}
