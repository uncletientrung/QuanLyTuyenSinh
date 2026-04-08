/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog.ThiSinh;
import GUI.Panel.ThiSinhPanel;
import GUI.Main;
import javax.swing.*;

/**
 *
 * @author DELL
 */
public class ThemThiSinhDialog extends JDialog{
    private Main mainFrame;
    private ThiSinhPanel parent;
    private String currentType;
    public ThemThiSinhDialog(ThiSinhPanel parent, JFrame owner, String title, String type, boolean  modal){
        super(owner, title, modal);
        this.mainFrame = (Main) owner;
        this.parent = parent;
        this.currentType = type;
        
        
        
    }
}
