/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

/**
 *
 * @author DELL
 */
import javax.swing.*;
import java.awt.*;

public class testDialog extends JDialog {

    public testDialog(JFrame owner) {
        super(owner, "Test Dialog", true); // true = modal

        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Đây là Dialog Test", JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());

        add(btnClose, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}