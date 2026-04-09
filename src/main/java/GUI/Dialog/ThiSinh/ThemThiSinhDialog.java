/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog.ThiSinh;

import GUI.Panel.ThiSinhPanel;
import GUI.Component.ButtonCustom;
import GUI.Component.InputForm;
import GUI.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author DELL
 */
public class ThemThiSinhDialog extends JDialog {

    private Main mainFrame;
    private ThiSinhPanel parent;
    private String currentType;
    private String title;

    // Input fields
    private InputForm inputCCCD;
    private InputForm inputSBD;
    private InputForm inputHo;
    private InputForm inputTen;
    private InputForm inputSDT;
    private InputForm inputEmail;
    private InputForm inputNoiSinh;
    private InputForm inputNgaySinh;

    // ComboBox fields
    private JComboBox<String> cboGioiTinh;
    private JComboBox<String> cboKhuVuc;
    private JComboBox<String> cboDuoiTuong;

    // Buttons
    private ButtonCustom btnLuu;
    private ButtonCustom btnDong;

    public ThemThiSinhDialog(ThiSinhPanel parent, JFrame owner, String title, String type, boolean modal) {
        super(owner, title, modal);
        this.mainFrame = (Main) owner;
        this.parent = parent;
        this.currentType = type;
        this.title = title;
        init();
        this.setVisible(true);
    }

    private void init() {
        this.setSize(900, 500);
        this.setLocationRelativeTo(getOwner());
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 160, 220));
        headerPanel.setPreferredSize(new Dimension(700, 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel(title, SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.add(lblHeader, BorderLayout.CENTER);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridLayout(6,2 , 10, 10));

        // --- Row 1: CCCD | SBD ---
        inputCCCD = new InputForm("CCCD:", 280, 65);
        inputSBD = new InputForm("SBD:", 280, 65);

        formPanel.add(inputCCCD, formPanel);
        formPanel.add(inputSBD, formPanel);

        // --- Row 2: Ho | Ten ---
        inputHo = new InputForm("Họ:", 280, 65);
        inputTen = new InputForm("Tên:", 280, 65);

        formPanel.add(inputHo, formPanel);
        formPanel.add(inputTen, formPanel);

        // --- Row 3: Gioi tinh (ComboBox) | Ngay sinh ---
        JPanel pnlGioiTinh = createLabelComboPanel("Giới tính:", new String[]{"Nam", "Nữ"});
        cboGioiTinh = (JComboBox<String>) ((JPanel) pnlGioiTinh.getComponent(1)).getComponent(0);

        inputNgaySinh = new InputForm("Ngày sinh:", 280, 65);

        formPanel.add(pnlGioiTinh, formPanel);
        formPanel.add(inputNgaySinh, formPanel);

        // --- Row 4: SDT | Email ---
        inputSDT = new InputForm("SĐT:", 280, 65);
        inputEmail = new InputForm("Email:", 280, 65);

        formPanel.add(inputSDT, formPanel);
        formPanel.add(inputEmail, formPanel);

        // --- Row 5: Noi sinh | Khu vuc (ComboBox) ---
        inputNoiSinh = new InputForm("Nơi sinh:", 280, 65);

        JPanel pnlKhuVuc = createLabelComboPanel("Khu vực:", new String[]{"Khu vực 1", "Khu vực 2", "Khu vực 3"});
        cboKhuVuc = (JComboBox<String>) ((JPanel) pnlKhuVuc.getComponent(1)).getComponent(0);

        formPanel.add(inputNoiSinh, formPanel);
        formPanel.add(pnlKhuVuc, formPanel);

        // --- Row 6: Doi tuong (ComboBox) ---
        JPanel pnlDoiTuong = createLabelComboPanel("Đối tượng:", new String[]{"Không ưu tiên", "Ưu tiên 1", "Ưu tiên 2"});
        cboDuoiTuong = (JComboBox<String>) ((JPanel) pnlDoiTuong.getComponent(1)).getComponent(0);

        formPanel.add(pnlDoiTuong, formPanel);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 15, 0));

        btnLuu = new ButtonCustom("Lưu thông tin", "excel", 14, 160, 42);
        btnDong = new ButtonCustom("Đóng", "success", 14, 120, 42);

        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnDong);

        // ===== ASSEMBLE =====
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);

        // Pre-fill data if editing
        if ("edit".equals(currentType)) {
            loadData();
        }
    }

    /**
     * Helper: tạo panel gồm JLabel + JComboBox (giống style InputForm)
     */
    private JPanel createLabelComboPanel(String labelText, String[] items) {
        JPanel wrapper = new JPanel(new GridLayout(2, 1));
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(0, 10, 5, 10));
        wrapper.setPreferredSize(new Dimension(280, 65));

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel comboWrapper = new JPanel(new BorderLayout());
        comboWrapper.setBackground(Color.WHITE);
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBackground(Color.WHITE);
        comboWrapper.add(combo, BorderLayout.CENTER);

        wrapper.add(lbl);
        wrapper.add(comboWrapper);
        return wrapper;
    }

    private void handleSave() {
        String cccd = inputCCCD.getText().trim();
        String sbd = inputSBD.getText().trim();
        String ho = inputHo.getText().trim();
        String ten = inputTen.getText().trim();
        String sdt = inputSDT.getText().trim();
        String email = inputEmail.getText().trim();
        String noiSinh = inputNoiSinh.getText().trim();
        String ngaySinh = inputNgaySinh.getText().trim();
        String gioiTinh = (String) cboGioiTinh.getSelectedItem();
        String khuVuc = (String) cboKhuVuc.getSelectedItem();
        String doiTuong = (String) cboDuoiTuong.getSelectedItem();

        // Basic validation
        if (cccd.isEmpty() || ho.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng điền đầy đủ thông tin bắt buộc (CCCD, Họ, Tên)!",
                "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO: Gọi DAO/Service để lưu vào database
        // Ví dụ: ThiSinhDAO.save(new ThiSinh(cccd, sbd, ho, ten, ...));

        JOptionPane.showMessageDialog(this,
            "Lưu thông tin thành công!",
            "Thành công", JOptionPane.INFORMATION_MESSAGE);


        dispose();
    }


    private void loadData() {
        // TODO: Load dữ liệu thí sinh từ DAO theo ID
        // Ví dụ:
        // ThiSinh ts = ThiSinhDAO.findById(selectedId);
        // inputCCCD.setText(ts.getCccd());
        // inputSBD.setText(ts.getSbd());
        // ...
    }

    // ===== GETTERS =====
    public String getCCCD()     { return inputCCCD.getText(); }
    public String getSBD()      { return inputSBD.getText(); }
    public String getHo()       { return inputHo.getText(); }
    public String getTen()      { return inputTen.getText(); }
    public String getSDT()      { return inputSDT.getText(); }
    public String getEmail()    { return inputEmail.getText(); }
    public String getNoiSinh()  { return inputNoiSinh.getText(); }
    public String getNgaySinh() { return inputNgaySinh.getText(); }
    public String getGioiTinh() { return (String) cboGioiTinh.getSelectedItem(); }
    public String getKhuVuc()   { return (String) cboKhuVuc.getSelectedItem(); }
    public String getDoiTuong() { return (String) cboDuoiTuong.getSelectedItem(); }
}