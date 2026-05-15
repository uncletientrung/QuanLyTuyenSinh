package com.quanlytuyensinh.service;

import com.quanlytuyensinh.DAO.XtDiemThiXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtNganhDAO;
import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.ENTITY.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraCuuService {

    private final XtThisinhXetTuyen25DAO  thisinhDAO   = XtThisinhXetTuyen25DAO.getInstance();
    private final XtNguyenVongXetTuyenDAO nguyenVongDAO= XtNguyenVongXetTuyenDAO.getInstance();
    private final XtNganhDAO              nganhDAO     = XtNganhDAO.getInstance();
    private final XtDiemThiXetTuyenDAO    diemDAO      = XtDiemThiXetTuyenDAO.getInstance(); 

    public TraCuuResultWrapper traCuu(String cccd) {  

        XtThisinhXetTuyen25 ts = thisinhDAO.findByCccdAndPassword(cccd);
        if (ts == null) return new TraCuuResultWrapper(new ArrayList<>(), new ArrayList<>());

        // Lấy danh sách nguyện vọng → map DTO 
        List<XtNguyenVongXetTuyen> dsNV = nguyenVongDAO.findByCccdOrderByThuTu(cccd);
        List<KetQuaTraCuuDTO> dsKetQua = new ArrayList<>();
        for (XtNguyenVongXetTuyen nv : dsNV) {
            String tenNganh = nv.getNvManganh();
            XtNganh nganh = nganhDAO.getNganhByMaNganh(nv.getNvManganh());
            if (nganh != null) tenNganh = nganh.getTennganh();

            dsKetQua.add(new KetQuaTraCuuDTO(
                ts.getCccd(), ts.getHo(), ts.getTen(), ts.getNgaySinh(),
                ts.getDoiTuong(), ts.getKhuVuc(),
                nv.getNvManganh(), tenNganh, nv.getNvTt(),
                nv.getTtThm(), nv.getNvKetqua(), nv.getTtPhuongthuc(),
                nv.getDiemThxt(),
                    nv.getDiemUtqd(),nv.getDiemCong(),
                nv.getDiemXettuyen()
            ));
        }

        // lấy tất cả dòng điểm của thí sinh 
        List<XtDiemThiXetTuyen> dsDiem = diemDAO.findByCccd(cccd);

        return new TraCuuResultWrapper(dsKetQua, dsDiem);
    }
}