/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows
 */
public class PaginatedTable extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel lblPageInfo;
    private final JButton btnPrev, btnNext;
    private final JComboBox<Integer> cboPage, cboPageSize;

    private List<Object[]> allData = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 20; // Số dòng mặc định
    private int totalPages = 1;
    private boolean updatingControls = false;

    public PaginatedTable(String[] columns) {
        setLayout(new BorderLayout(0, 10));

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Hiện bản ghi
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        leftPanel.setOpaque(false);
        JLabel lblPageSize = new JLabel("Dòng/trang");
        lblPageSize.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboPageSize = new JComboBox<>(new Integer[]{10, 20, 50, 100});
        cboPageSize.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboPageSize.setSelectedItem(pageSize);
        cboPageSize.addActionListener(e -> {
            if (updatingControls) {
                return;
            }
            Integer selected = (Integer) cboPageSize.getSelectedItem();
            if (selected != null) {
                setPageSize(selected);
            }
        });
        leftPanel.add(lblPageSize);
        leftPanel.add(cboPageSize);

        JPanel paginationPanel = new JPanel(new BorderLayout());
        paginationPanel.setOpaque(false);
        paginationPanel.add(leftPanel, BorderLayout.WEST);

        // Thanh điều hướng
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12));

        btnPrev = new JButton();
        btnNext = new JButton();
        for (JButton btn : new JButton[]{btnPrev, btnNext}) {
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setOpaque(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        try {
            btnPrev.setIcon(new FlatSVGIcon("icons/previous.svg", 18, 18));
        } catch (Exception e) {
            btnPrev.setText("< Trước");
        }
        try {
            btnNext.setIcon(new FlatSVGIcon("icons/next.svg", 18, 18));
        } catch (Exception e) {
            btnNext.setText("Tiếp >");
        }

        lblPageInfo = new JLabel("Trang 1/1");
        lblPageInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboPage = new JComboBox<>();
        cboPage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboPage.setPreferredSize(new Dimension(90, 28));

        btnPrev.addActionListener(e -> goToPage(currentPage - 1));
        btnNext.addActionListener(e -> goToPage(currentPage + 1));
        cboPage.addActionListener(e -> {
            if (!updatingControls) {
                goToPage((Integer) cboPage.getSelectedItem());
            }
        });

        rightPanel.add(btnPrev);
        rightPanel.add(lblPageInfo);
        JLabel lblJump = new JLabel("Tới trang:");
        lblJump.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rightPanel.add(lblJump);
        rightPanel.add(cboPage);
        rightPanel.add(btnNext);
        paginationPanel.add(rightPanel, BorderLayout.EAST);
        add(paginationPanel, BorderLayout.SOUTH);
    }

    // Đổ data
    public void setData(List<Object[]> data) {
        this.allData = (data != null) ? data : new ArrayList<>();
        refreshPageCount();
        goToPage(1);
    }

    public void setPageSize(int newPageSize) {
        if (newPageSize <= 0) {
            return;
        }
        int firstRowIndex = (currentPage - 1) * pageSize;
        this.pageSize = newPageSize;
        refreshPageCount();
        int nextPage = allData.isEmpty() ? 1 : (firstRowIndex / pageSize) + 1;
        goToPage(nextPage);
    }

    // Số trang = tổng dòng / số dòng mỗi trang
    private void refreshPageCount() {
        totalPages = Math.max(1, (int) Math.ceil((double) allData.size() / pageSize));
    }

    public void goToPage(int page) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        currentPage = page;

        tableModel.setRowCount(0); // Xóa dữ liệu cũ trên giao diện

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, allData.size());

        for (int i = start; i < end; i++) {
            tableModel.addRow(allData.get(i));
        }

        updateUIControls();
    }

    private void updateUIControls() {
        updatingControls = true; // Chặn loop sự kiện ComboBox

        lblPageInfo.setText(String.format("Trang %d/%d (%d dòng)", currentPage, totalPages, allData.size()));
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);

        cboPage.removeAllItems();
        for (int i = 1; i <= totalPages; i++) {
            cboPage.addItem(i);
        }
        cboPage.setSelectedItem(currentPage);

        updatingControls = false;
    }

    // Lấy index
    public int getRealIndex(int visualRow) {
        if (visualRow < 0) {
            return -1;
        }
        return (currentPage - 1) * pageSize + visualRow;
    }

    public JTable getTable() {
        return table;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}
