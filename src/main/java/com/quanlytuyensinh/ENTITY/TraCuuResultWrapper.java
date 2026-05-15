/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.ENTITY;
import java.util.List;
/**
 *
 * @author Hi
 */
public class TraCuuResultWrapper {
    private List<KetQuaTraCuuDTO>      dsNguyenVong;   // 1 phần tử / NV
    private List<XtDiemThiXetTuyen>   dsDiem;         // tối đa 3 dòng (THPT/VSAT/DGNL)

    public TraCuuResultWrapper(List<KetQuaTraCuuDTO> dsNguyenVong,
                               List<XtDiemThiXetTuyen> dsDiem) {
        this.dsNguyenVong = dsNguyenVong;
        this.dsDiem = dsDiem;
    }

    //lấy dòng điểm theo phương thức (THPT / VSAT / DGNL)
    public XtDiemThiXetTuyen getDiemTheo(String phuongThuc) {
        if (dsDiem == null) return null;
        return dsDiem.stream()
            .filter(d -> phuongThuc.equalsIgnoreCase(d.getDPhuongthuc()))
            .findFirst().orElse(null);
    }

    public List<KetQuaTraCuuDTO> getDsNguyenVong() { return dsNguyenVong; }
    public List<XtDiemThiXetTuyen> getDsDiem()     { return dsDiem; }
    public XtDiemThiXetTuyen getDiemTHPT() { return getDiemTheo("THPT"); }
    public XtDiemThiXetTuyen getDiemVSAT() { return getDiemTheo("VSAT"); }
    public XtDiemThiXetTuyen getDiemDGNL() { return getDiemTheo("DGNL"); }
}