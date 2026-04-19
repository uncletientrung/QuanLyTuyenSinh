package GUI.Component;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VerticalInputForm extends JPanel {

    private JLabel lblTitle;
    private JTextField txtForm;

    public VerticalInputForm(String title) {
        this.setLayout(new BorderLayout(0, 5)); 
        this.setBackground(Color.WHITE);    
        this.setBorder(new EmptyBorder(5, 5, 5, 5)); 

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 13)); 
        lblTitle.setForeground(new Color(60, 60, 60));

        txtForm = new JTextField();
        txtForm.setPreferredSize(new Dimension(300, 40));
        txtForm.setFont(new Font("SansSerif", Font.PLAIN, 15)); 
        
       
        txtForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15) 
        ));

        this.add(lblTitle, BorderLayout.NORTH);
        this.add(txtForm, BorderLayout.CENTER);
    }

    public String getText() { return txtForm.getText(); }
    public void setText(String text) { txtForm.setText(text); }
    public void setDisable() { txtForm.setEnabled(false); }
    public JTextField getTxtForm() { return txtForm; }
    public JLabel getLblTitle() { return lblTitle; }
    public void requestFocus() { txtForm.requestFocus(); }
}