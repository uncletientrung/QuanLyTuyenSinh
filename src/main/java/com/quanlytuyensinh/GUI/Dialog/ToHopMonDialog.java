package com.quanlytuyensinh.GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.quanlytuyensinh.BUS.XtToHopMonThiBUS;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import com.quanlytuyensinh.GUI.Component.ButtonCustom;
import com.quanlytuyensinh.GUI.Component.InputForm;
import com.quanlytuyensinh.GUI.Component.SelectForm;
import com.quanlytuyensinh.GUI.Panel.ToHopMonPanel;

public class ToHopMonDialog extends JDialog implements ActionListener {
    private ToHopMonPanel parentPanel;
    private XtToHopMonThiBUS bus;
    private JFrame main;
    private JPanel pmain, pbottom;
    private ButtonCustom btnConfirm, btnCancel;
    private InputForm maToHop, tenToHop;
    private SelectForm mon1, mon2, mon3;
    private XtToHopMonThi selectedToHop;
    private XtToHopMonThi newToHop;

    public ToHopMonDialog() {};
    public ToHopMonDialog(ToHopMonPanel parentPanel, XtToHopMonThiBUS bus, XtToHopMonThi toHop, JFrame main, String title) {
        super(main, title, true);
        this.main = main;
        this.bus = bus;
        this.parentPanel = parentPanel;
        selectedToHop = toHop;
        initComponents();
    }

    private void initComponents() {
        this.setSize(new Dimension(400, 700));
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(main);
        
        pmain = new JPanel(new GridLayout(5, 1, 0, 0));
        pmain.setBackground(Color.WHITE);
        pmain.setBorder(new EmptyBorder(20 , 20, 20, 20));
        
        ArrayList<String> list = new ArrayList<>();
        list.add("Chọn tổ hợp môn");
        list.addAll(ToHopMonPanel.tenMap.keySet());
        
        String[] monArr = list.toArray(new String[0]);
        maToHop = new InputForm("Mã tổ hợp");
        mon1 = new SelectForm("Môn 1", monArr);
        mon2 = new SelectForm("Môn 2", monArr);
        mon3 = new SelectForm("Môn 3", monArr);
        tenToHop = new InputForm("Tên tổ hợp");
        
        btnConfirm = new ButtonCustom("Xác nhận", "success", 20);
        btnCancel = new ButtonCustom("Huỷ bỏ", "danger", 20);

        tenToHop.setDisable();
        btnConfirm.setEnabled(false);
    
        mon1.getCbb().addActionListener(e -> {
            String tenMon1 = mon1.getValue();
            String tenMon2 = mon2.getValue();
            String tenMon3 = mon3.getValue();
            if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && !(tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3))) {
                tenToHop.setText(ToHopMonPanel.tenMap.get(tenMon1) + ", " + ToHopMonPanel.tenMap.get(tenMon2) + ", " + ToHopMonPanel.tenMap.get(tenMon3));
                btnConfirm.setEnabled(true);
            } else {
                if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && (tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3)))
                    JOptionPane.showMessageDialog(this, "Có tổ hợp trùng nhau", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                btnConfirm.setEnabled(false);
            }
        });

        mon2.getCbb().addActionListener(e -> {
            String tenMon1 = mon1.getValue();
            String tenMon2 = mon2.getValue();
            String tenMon3 = mon3.getValue();
            if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && !(tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3))) {
                tenToHop.setText(ToHopMonPanel.tenMap.get(tenMon1) + ", " + ToHopMonPanel.tenMap.get(tenMon2) + ", " + ToHopMonPanel.tenMap.get(tenMon3));
                btnConfirm.setEnabled(true);
            } else {
                if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && (tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3)))
                    JOptionPane.showMessageDialog(this, "Có tổ hợp trùng nhau", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                btnConfirm.setEnabled(false);
            }
        });
        
        mon3.getCbb().addActionListener(e -> {
            String tenMon1 = mon1.getValue();
            String tenMon2 = mon2.getValue();
            String tenMon3 = mon3.getValue();
            if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && !(tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3))) {
                tenToHop.setText(ToHopMonPanel.tenMap.get(tenMon1) + ", " + ToHopMonPanel.tenMap.get(tenMon2) + ", " + ToHopMonPanel.tenMap.get(tenMon3));
                btnConfirm.setEnabled(true);
            } else {
                if ((mon1.getSelectedIndex() != 0 && mon2.getSelectedIndex() != 0 && mon3.getSelectedIndex() != 0)
                && (tenMon1.equals(tenMon2) || tenMon1.equals(tenMon3) || tenMon2.equals(tenMon3)))
                    JOptionPane.showMessageDialog(this, "Có tổ hợp trùng nhau", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
                btnConfirm.setEnabled(false);
            }
        });

        pmain.add(maToHop);
        pmain.add(mon1);
        pmain.add(mon2);
        pmain.add(mon3);
        pmain.add(tenToHop);

        pbottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pbottom.setBackground(Color.WHITE);
        pbottom.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        pbottom.add(btnConfirm);
        pbottom.add(btnCancel);

        this.add(pmain, BorderLayout.CENTER);
        this.add(pbottom, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            dispose();
        } else if (e.getSource() == btnConfirm) {
            if (selectedToHop == newToHop)
                dispose();
            validate(newToHop);
            if (selectedToHop == null) return;
                //Create new Entity
            else
                return;
                //Edit Entity
        }
    }

    private boolean validate(XtToHopMonThi toHop) {
        String ma = toHop.getMatohop();
        String mon1 = toHop.getMon1();
        String mon2 = toHop.getMon2();
        String mon3 = toHop.getMon3();

        String existIn;
        if (bus.existMaToHop(ma)) {
            JOptionPane.showMessageDialog(main, "Mã tổ hợp " + ma + " đã tồn tại!", "Sai thông tin tổ hợp", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ((existIn = bus.existToHopMon(mon1, mon2, mon3)) != "") {
            JOptionPane.showMessageDialog(main, "Tổ hợp đã tồn tại với mã " + existIn, "Sai thông tin tổ hợp", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
