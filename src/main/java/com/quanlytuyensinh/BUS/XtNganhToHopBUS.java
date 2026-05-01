/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtNganhToHopDAO;
import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import java.util.List;

/**
 *
 * @author Hi
 */
public class XtNganhToHopBUS {
    private final XtNganhToHopDAO nganhToHopDAO;
    private List<XtNganhToHop> listNTH;
    public XtNganhToHopBUS(){  
        this.nganhToHopDAO= XtNganhToHopDAO.getInstance();
        this.listNTH = nganhToHopDAO.getAll();
    }
    
    public List<XtNganhToHop> getAll(){
        listNTH = nganhToHopDAO.getAll(); 
        return listNTH;
    }
    
    public boolean addNTH(XtNganhToHop nth) {
        if (nganhToHopDAO.insert(nth)) {
            listNTH.add(nth);
            return true;
        }
        return false;
    }
    
    public boolean updateNTH(XtNganhToHop nth) {

        if (nganhToHopDAO.update(nth)) {
            for (int i = 0; i <= listNTH.size(); i++) {
                if (listNTH.get(i).getId()== nth.getId()) {
                    listNTH.set(i, nth);
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean deleteNTH(int id){
        if(nganhToHopDAO.delete(id)){
            listNTH.removeIf(nth -> nth.getId()== id);
            return true;
        }
        return false;
    }
    
    public List<XtNganhToHop> searchNTH(String keyword, String searchType){
        List<XtNganhToHop> all = nganhToHopDAO.getAll();
        if(keyword == null || keyword.trim().isEmpty()){
            return  all;
        }
        
        String kw = keyword.trim().toLowerCase();
        return all.stream()
                .filter(nth -> switch (searchType) {
            case "Mã ngành" -> nth.getManganh()!= null && nth.getManganh().toLowerCase().contains(kw);
            case "Mã tổ hợp" -> nth.getMatohop() !=null && nth.getMatohop().toLowerCase().contains(kw);
            case "Môn 1" -> nth.getThMon1() !=null && nth.getThMon1().toLowerCase().contains(kw);
            case "Môn 2" -> nth.getThMon2() !=null && nth.getThMon2().toLowerCase().contains(kw);
            case "Môn 3" -> nth.getThMon3() !=null && nth.getThMon3().toLowerCase().contains(kw);
            default ->
                (nth.getManganh()!= null && nth.getManganh().toLowerCase().contains(kw)) ||
                (nth.getMatohop() !=null && nth.getMatohop().toLowerCase().contains(kw)) ||
                (nth.getThMon1() !=null && nth.getThMon1().toLowerCase().contains(kw)) ||
                (nth.getThMon2() !=null && nth.getThMon2().toLowerCase().contains(kw)) ||
                (nth.getThMon3() !=null && nth.getThMon3().toLowerCase().contains(kw));
             
        }).toList();
                
    }
}
