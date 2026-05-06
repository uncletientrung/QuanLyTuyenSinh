/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.XtToHopMonThiDAO;
import com.quanlytuyensinh.ENTITY.XtToHopMonThi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ASUS
 */
public class XtToHopMonThiBUS {
    private final XtToHopMonThiDAO tohopDAO = XtToHopMonThiDAO.getInstance();
    private List<XtToHopMonThi> listMon;
    
    public XtToHopMonThiBUS() {
        listMon = tohopDAO.getAll();
    }
    
    public List<XtToHopMonThi> getList() {
        return listMon;
    }
    
    public List<XtToHopMonThi> refreshList() {
        return tohopDAO.getAll();
    }
    
    public List<XtToHopMonThi> search(String type, String keyword) {
         if (keyword == null || keyword.trim().isEmpty()) {
            return listMon;
        }

        String lowerKeyword = keyword.trim().toLowerCase();

        switch (type) {
            case "Mã":
                return listMon.stream()
                        .filter(qd -> String.valueOf(qd.getMatohop()).contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Môn 1":
                return listMon.stream()
                        .filter(qd -> qd.getMon1() != null
                        && qd.getMon1().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Môn 2":
                return listMon.stream()
                        .filter(qd -> qd.getMon2() != null
                        && qd.getMon2().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Môn 3":
                return listMon.stream()
                        .filter(qd -> qd.getMon3() != null
                        && qd.getMon3().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tên tổ hợp":
                return listMon.stream()
                        .filter(qd -> qd.getTentohop() != null
                        && qd.getTentohop().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tất cả":
            default:
                return listMon.stream()
                        .filter(qd
                                -> (qd.getMatohop() != null && qd.getMatohop().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon1() != null && qd.getMon1().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon2() != null && qd.getMon2().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon3() != null && qd.getMon3().toLowerCase().contains(lowerKeyword))
                        || (qd.getTentohop() != null && qd.getTentohop().toLowerCase().contains(lowerKeyword))
                        || String.valueOf(qd.getIdtohop()).contains(lowerKeyword)
                        )
                        .collect(Collectors.toList());
        }
    }
    
    public boolean existMaToHop(String ma) {
        return tohopDAO.existMaToHop(ma);
    }
    
    public String existToHopMon(String mon1, String mon2, String mon3) {
        List<String> toHop = Arrays.asList(mon1, mon2, mon3);
        return tohopDAO.existToHopMon(toHop);
    }

    public XtToHopMonThi findByMa(String ma) {
        return tohopDAO.findByMa(ma);
    }

    public boolean addNewToHop(XtToHopMonThi t) {
        return tohopDAO.addNewToHop(t);
    }

    public XtToHopMonThi findById(int id) {
        return tohopDAO.findById(id);
    }

    public boolean updateToHop(XtToHopMonThi t) {
        return tohopDAO.updateToHop(t);
    }

    public boolean deleteToHop(XtToHopMonThi t) {
        return tohopDAO.deleteToHop(t);
    }
    
    public void importToDB(List<XtToHopMonThi> list) {
        tohopDAO.importToDB(list);
    }
}
