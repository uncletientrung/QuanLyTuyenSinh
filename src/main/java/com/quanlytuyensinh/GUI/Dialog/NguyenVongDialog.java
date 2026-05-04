/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.GUI.Dialog;

import com.quanlytuyensinh.BUS.XtDiemCongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtNganhBUS;
import com.quanlytuyensinh.BUS.XtNganhToHopBUS;
import com.quanlytuyensinh.GUI.Panel.NguyenVongPanel;
import com.quanlytuyensinh.ENTITY.XtThisinhXetTuyen25;
import com.quanlytuyensinh.BUS.XtNguyenVongXetTuyenBUS;
import com.quanlytuyensinh.BUS.XtThisinhXetTuyen25BUS;
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
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dell
 */
public class NguyenVongDialog extends JDialog{
    private NguyenVongPanel parent;
    private XtNguyenVongXetTuyenBUS NVBUS;
    private String type; // "create" hoặc "edit"
    private Runnable onSuccess; // Lưu hàm chạy sau khi xong
    
    // Form fields
    private VerticalInputForm txtThuTu, txtDiemTHXT, txtDiemUT, txtDiemCong, txtDiemXetTuyen, txtKetQua, txtDanhSachTH, txtTHXet, txtNV_Key;
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
    
    public NguyenVongDialog(NguyenVongPanel parent, JFrame owner, String title, String type, boolean modal, Runnable onSuccess, XtNguyenVongXetTuyen nv){
         super(owner, title, modal);
        this.parent = parent;
        NVBUS = parent.getBUS();
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

        initComponents();
    }
        private void initComponents() {
        this.setSize(800, 750);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);

        initMainPanel();
        initButtonPanel();
        
        if(this.type.equals("create")){
            setFieldsDisable();
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
        this.txtDiemTHXT = new VerticalInputForm("Điểm tổ hợp xét tuyển");
        this.txtDiemUT =  new VerticalInputForm("Điểm ưu tiên");
        this.txtDiemCong = new VerticalInputForm("Điểm cộng"); 
        this.txtDiemXetTuyen = new VerticalInputForm("Điểm xét tuyển"); 
        this.txtKetQua =  new VerticalInputForm("Kết quả");  
        this.txtNV_Key= new VerticalInputForm("Nguyện vọng Key"); 
        
        // ==================== LEFT COLUMN ====================
        left.add(txtThuTu);
        left.add(cbbCCCD);
        left.add(cbbMaNganh);
        left.add(cbbPhuongThuc);
        left.add(txtDanhSachTH);
        left.add(txtTHXet);
        
        // ==================== RIGHT COLUMN ====================
        right.add(txtDiemTHXT);
        right.add(txtDiemUT);
        right.add(txtDiemCong);
        right.add(txtDiemXetTuyen);
        right.add(txtKetQua);
        right.add(txtNV_Key);
        
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

        String btnText = "create".equals(type) ? "Thêm thí sinh" : "Lưu chỉnh sửa";

        btnLuu = new ButtonCustom(btnText, "success", 15);
        btnHuy = new ButtonCustom("Hủy bỏ", "danger", 15);

        btnLuu.setPreferredSize(new Dimension(160, 48));
        btnHuy.setPreferredSize(new Dimension(160, 48));
        btnHuy.addActionListener(e -> dispose());
        
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
    }
        private void bindListeners() {
            this.cbbMaNganh.getComboBox().addActionListener(e -> updateNVKey());
            
            this.cbbCCCD.getComboBox().addActionListener(e -> updateNVKey());
            
            this.txtThuTu.addTextChangeListener(() -> updateNVKey());
        }
    
    
    private void setFieldsDisable(){
        VerticalInputForm[] listInputCreate = {txtDanhSachTH, txtTHXet, txtDiemTHXT, txtDiemUT, txtDiemCong, txtDiemXetTuyen, txtKetQua,
                txtNV_Key};
        for (VerticalInputForm f : listInputCreate) {
            f.setDisable();
        }
    }
    
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
    
}
