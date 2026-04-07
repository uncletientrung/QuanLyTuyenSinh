/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BUS.XtThisinhXetTuyen25BUS;
import ENTITY.XtThisinhXetTuyen25;
import GUI.Main;
import javax.swing.JPanel;
import javax.swing.*;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author DELL
 */
public class testPanel extends JPanel{
    Main mainFrame;
    XtThisinhXetTuyen25BUS tsBUS;
    List<XtThisinhXetTuyen25> listTS;
    
    public testPanel(Main mainF){
            this.mainFrame = mainF;
            tsBUS = new XtThisinhXetTuyen25BUS();
            listTS = tsBUS.getAllThiSinh();
            for (XtThisinhXetTuyen25 ts : listTS) {
                System.out.println(ts);
            }
            
            initComponent();
        }
        private void initComponent() {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("QUẢN LÝ THÍ SINH");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(new EmptyBorder(20,0,20,0));

        this.add(title, BorderLayout.NORTH);

        // Table
        String[] column = {
            "Mã thí sinh",
            "CCCD",
            "SBD",
            "Họ",
            "Tên",
            "Giới tính",
            "Ngày sinh",
            "Khu vực",
            "Đối tượng"
        };

        DefaultTableModel model = new DefaultTableModel(column,0);

        JTable table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        this.add(scroll, BorderLayout.CENTER);

        // Fake data
        model.addRow(new Object[]{
            "TS-1",
            "0123456789",
            "SBD001",
            "Nguyễn",
            "An",
            "Nam",
            "15/03/2005",
            "KV1",
            "01"
        });

        model.addRow(new Object[]{
            "TS-2",
            "0987654321",
            "SBD002",
            "Trần",
            "Bình",
            "Nữ",
            "20/05/2005",
            "KV2",
            "02"
        });
    }
}
