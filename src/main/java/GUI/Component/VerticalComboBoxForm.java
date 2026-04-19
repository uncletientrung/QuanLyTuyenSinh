package GUI.Component;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VerticalComboBoxForm extends JPanel {

    private JLabel lblTitle;
    private JComboBox<String> cbbForm;

    public VerticalComboBoxForm(String title) {
        this.setLayout(new BorderLayout(0, 5));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitle.setForeground(new Color(60, 60, 60));

        String[] options = {"-- Chọn --", "Có", "Không"};
        cbbForm = new JComboBox<>(options);
        cbbForm.setPreferredSize(new Dimension(300, 40));
        cbbForm.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbbForm.setBackground(Color.WHITE);

        this.add(lblTitle, BorderLayout.NORTH);
        this.add(cbbForm, BorderLayout.CENTER);
    }


    public String getSelectedValue() {
        String selected = (String) cbbForm.getSelectedItem();
        if ("Có".equals(selected)) return "1";
        if ("Không".equals(selected)) return "0";
        return ""; 
    }

    public void setSelectedValue(String dbValue) {
        if ("1".equals(dbValue)) {
            cbbForm.setSelectedItem("Có");
        } else if ("0".equals(dbValue)) {
            cbbForm.setSelectedItem("Không");
        } else {
            cbbForm.setSelectedIndex(0); 
        }
    }

    public void setDisable() {
        cbbForm.setEnabled(false);
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }
}