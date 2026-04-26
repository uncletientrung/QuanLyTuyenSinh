/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.XtBangQuyDoiDAO;
import ENTITY.XtBangQuyDoi;
import java.util.*;

/**
 *
 * @author Windows
 */
public class XtBangQuyDoiBUS {
    private final XtBangQuyDoiDAO xtbangquydoiDAO = XtBangQuyDoiDAO.getInstance();
    private List<XtBangQuyDoi> listQuyDoi;
    
    public XtBangQuyDoiBUS() {
        listQuyDoi = xtbangquydoiDAO.getAll();
        if (listQuyDoi == null) listQuyDoi = new ArrayList<>();
    }
    
    public List<XtBangQuyDoi> getAllQuyDoi() {
        return listQuyDoi;
    }
}
