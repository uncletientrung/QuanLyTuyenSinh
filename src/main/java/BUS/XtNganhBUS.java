/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.XtNganhDAO;
import ENTITY.XtNganh;
import java.util.List;
/**
 *
 * @author Hi
 */
public class XtNganhBUS {

    private final XtNganhDAO nganhDAO;

    // Constructor
    public XtNganhBUS() {
        this.nganhDAO = XtNganhDAO.getInstance();
    }


    public List<XtNganh> getAllNganh() {
        return nganhDAO.getAll();
    }
}
