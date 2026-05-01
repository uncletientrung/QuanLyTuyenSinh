/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.BUS;

import com.quanlytuyensinh.DAO.MonDAO;
import com.quanlytuyensinh.ENTITY.Mon;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ASUS
 */
public class MonBUS {
    private final MonDAO monDAO = MonDAO.getInstance();
    private List<Mon> listMon;
    
    public MonBUS() {
        listMon = monDAO.getAll();
    }
    
    public List<Mon> getList() {
        return listMon;
    }
    
    public List<Mon> refreshList() {
        return monDAO.getAll();
    }
    
    public List<Mon> search(String type, String keyword) {
         if (keyword == null || keyword.trim().isEmpty()) {
            return listMon;
        }

        String lowerKeyword = keyword.trim().toLowerCase();

        switch (type) {
            case "Mã":
                return listMon.stream()
                        .filter(qd -> String.valueOf(qd.getMaToHop()).contains(lowerKeyword))
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
                        .filter(qd -> qd.getTenToHop() != null
                        && qd.getTenToHop().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
            case "Tất cả":
            default:
                return listMon.stream()
                        .filter(qd
                                -> (qd.getMaToHop() != null && qd.getMaToHop().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon1() != null && qd.getMon1().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon2() != null && qd.getMon2().toLowerCase().contains(lowerKeyword))
                        || (qd.getMon3() != null && qd.getMon3().toLowerCase().contains(lowerKeyword))
                        || (qd.getTenToHop() != null && qd.getTenToHop().toLowerCase().contains(lowerKeyword))
                            || String.valueOf(qd.getId()).contains(lowerKeyword)
                        )
                        .collect(Collectors.toList());
        }
    }
    
    public boolean existMaToHop(String ma) {
        return monDAO.existMaToHop(ma);
    }
    
    public void importToDB(List<Mon> list) {
        monDAO.importToDB(list);
        return;
    }
}
