/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import GUI.Component.ButtonCustom;
import GUI.Panel.XtDiemCongXetTuyenPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Windows
 */
public class XtDiemCongXetTuyenExcelDialog extends JDialog implements ActionListener {

    private XtDiemCongXetTuyenPanel parentPanel;
    private JFrame mainFrame;
    
    private ButtonCustom btnThiSinh;
    private ButtonCustom btnDiemChungChi;
    private ButtonCustom btnDiemUuTien;

    public XtDiemCongXetTuyenExcelDialog(XtDiemCongXetTuyenPanel parent, JFrame mainFrame) {
        super(mainFrame, "Chọn loại dữ liệu import", true);
        this.parentPanel = parent;
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        this.setSize(new Dimension(600, 150));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 15, 10, 15);
        
        // Nút Thí sinh
        btnThiSinh = new ButtonCustom("Thí sinh", "success", 16, 160, 60);
        btnThiSinh.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(btnThiSinh, gbc);

        // Nút Điểm chứng chỉ
        btnDiemChungChi = new ButtonCustom("Điểm chứng chỉ", "warning", 16, 160, 60);
        btnDiemChungChi.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(btnDiemChungChi, gbc);

        // Nút Điểm ưu tiên xét tuyển
        btnDiemUuTien = new ButtonCustom("Điểm ưu tiên xét tuyển", "excel", 15, 180, 60);
        btnDiemUuTien.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(btnDiemUuTien, gbc);

        this.add(mainPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThiSinh) {
            dispose();
            // TODO: Gọi hàm import thí sinh
        } else if (e.getSource() == btnDiemChungChi) {
            dispose();
            // TODO: Gọi hàm import điểm chứng chỉ
        } else if (e.getSource() == btnDiemUuTien) {
            dispose();
            // TODO: Gọi hàm import điểm ưu tiên xét tuyển
        }
    }
}