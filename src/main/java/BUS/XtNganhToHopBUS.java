/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtNganhToHopDAO;
import ENTITY.XtNganhToHop;
import java.util.List;

/**
 *
 * @author Hi
 */
public class XtNganhToHopBUS {
    private final XtNganhToHopDAO nganhToHopDAO;
    
    public XtNganhToHopBUS(){
        this.nganhToHopDAO= XtNganhToHopDAO.getInstance();
    }
    
    public List<XtNganhToHop> getAll(){
        List e = nganhToHopDAO.getAll();
        System.err.println(e);
        return nganhToHopDAO.getAll();
    }
}
