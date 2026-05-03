package com.quanlytuyensinh.GUI.Panel;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.quanlytuyensinh.BUS.XtDiemThiXetTuyenBUS;
import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.GUI.Component.IntegratedSearch;
import com.quanlytuyensinh.GUI.Component.MainFunction;
import com.quanlytuyensinh.GUI.Component.PaginatedTable;
import com.quanlytuyensinh.GUI.Component.PanelBorderRadius;

public class DiemThiPanel extends JPanel implements ActionListener, ItemListener{
    private PanelBorderRadius pnlMain, functionBar;
    private Main mainFrame;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private MainFunction mainFunction;
    private IntegratedSearch search;
    private PaginatedTable thptTable, dnglTable, vsatTable;
    private JTabbedPane tabbedPane;

    private XtDiemThiXetTuyenBUS diemBUS;
    private List<XtDiemThiXetTuyen> listDiem;
    private Color BackgroundColor = new Color(240, 247, 250);

    public DiemThiPanel(Main main) {
        this.mainFrame = main;
        // listDiem = diemBUS.getList();
        initComponent();
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 14));
        
        String[] thptHeaders = {
            "#", "CCCD",
            "Toán", "Văn", "Lý", "Hóa", "Sinh", "Sử", "Địa",
            "GDCD", "Anh (thi)", "Anh (CC)",
            "CNCN", "CNNN", "Tin", "KTPL",
            "NK1", "NK2", "NK3", "NK4", "NK5", "NK6"
        };

        String[] dnglHeaders = {"#", "CCCD", "Điểm ĐGNL"};
        
        String[] vsatHeaders = {
            "#", "CCCD",
            "Toán", "Văn", "Lý", "Hóa", "Sinh", "Sử", "Địa", "Anh"
        };

        tabbedPane.addTab("THPT", initTabPanel(thptHeaders, thptTable, "THPT"));
        tabbedPane.addTab("DGNL", initTabPanel(dnglHeaders, dnglTable, "DGNL"));
        tabbedPane.addTab("VSAT", initTabPanel(vsatHeaders, vsatTable, "VSAT"));

        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);
        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);
        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);
        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
        
        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        functionBar.setBackground(Color.WHITE);

        String[] action = {"create", "update", "delete", "import"};
        mainFunction = new MainFunction(1, "nguoiDung", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        
        search = new IntegratedSearch(new String[]{"CCCD", "Số báo danh"});
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addActionListener(e -> performSearch());
        search.btnReset.addActionListener(e -> {
            search.txtSearchForm.setText("");
            search.cbxChoose.setSelectedIndex(0);
        });
        functionBar.add(search);
        
        contentCenter.add(functionBar, BorderLayout.NORTH);
        pnlMain = new PanelBorderRadius();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.add(tabbedPane, BorderLayout.CENTER);
        contentCenter.add(pnlMain, BorderLayout.CENTER);
    }

    private void performSearch() {

    }

    private JPanel initTabPanel(String[] header, PaginatedTable paginatedTable, String pt) {
        paginatedTable = new PaginatedTable(header);

        JTable table = paginatedTable.getTable();
        table.setFocusable(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel panel = new PanelBorderRadius();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(paginatedTable, BorderLayout.CENTER);
        return panel;
        // loadDataTable(paginatedTable, pt);
    }

    private void loadDataTable(PaginatedTable table, String phuongThuc) {
        List<Object[]> rows = new ArrayList<>();
        int stt = 1;
        for (XtDiemThiXetTuyen d : listDiem) {
            if (phuongThuc.equals("DGNL")) {
                rows.add(new Object[]{
                    stt++,
                    d.getCccd(),
                    convert(d.getNl1())
                });
            } else if (phuongThuc.equals("VSAT")) {
                rows.add(new Object[]{
                    stt++,
                    d.getCccd(),
                    convert(d.getTo()),
                    convert(d.getVa()),
                    convert(d.getLi()),
                    convert(d.getHo()),
                    convert(d.getSi()),
                    convert(d.getSu()),
                    convert(d.getDi()),
                    convert(d.getN1Thi())
                });
            } else {
                rows.add(new Object[]{
                    stt++,
                    d.getCccd(),
                    convert(d.getTo()),
                    convert(d.getVa()),
                    convert(d.getLi()),
                    convert(d.getHo()),
                    convert(d.getSi()),
                    convert(d.getSu()),
                    convert(d.getDi()),
                    convert(d.getGdcd()),
                    convert(d.getN1Thi()),
                    convert(d.getN1Cc()),
                    convert(d.getCncn()),
                    convert(d.getCnnn()),
                    convert(d.getTi()),
                    convert(d.getKtpl()),
                    convert(d.getNk1()),
                    convert(d.getNk2()),
                    convert(d.getNk3()),
                    convert(d.getNk4()),
                    convert(d.getNk5()),
                    convert(d.getNk6())
                });
            }
        }

        table.setData(rows);
    }

    private String convert(BigDecimal value) {
            return value != null && value.compareTo(BigDecimal.ZERO) != 0
                ? value.stripTrailingZeros().toPlainString()
                : "---";
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        return;
    }
}
