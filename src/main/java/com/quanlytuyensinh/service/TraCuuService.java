package com.quanlytuyensinh.service;

import com.quanlytuyensinh.DAO.XtNganhDAO;
import com.quanlytuyensinh.DAO.XtNguyenVongXetTuyenDAO;
import com.quanlytuyensinh.DAO.XtThisinhXetTuyen25DAO;
import com.quanlytuyensinh.ENTITY.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraCuuService {

    private final XtThisinhXetTuyen25DAO thisinhDAO = XtThisinhXetTuyen25DAO.getInstance();
    private final XtNguyenVongXetTuyenDAO nguyenVongDAO = XtNguyenVongXetTuyenDAO.getInstance();
    private final XtNganhDAO nganhDAO = XtNganhDAO.getInstance();

    public List<KetQuaTraCuuDTO> traCuu(String cccd, String password) {
        List<KetQuaTraCuuDTO> result = new ArrayList<>();

        // buoc1 tim thi sinh
        XtThisinhXetTuyen25 ts = thisinhDAO.findByCccdAndPassword(cccd, password);
        if (ts == null) {
            return result; // Sai thông tin
        }

        // b2 lay ds nguyen vong
        List<XtNguyenVongXetTuyen> dsNV = nguyenVongDAO.findByCccdOrderByThuTu(cccd);

        //map sang DTO
        for (XtNguyenVongXetTuyen nv : dsNV) {

            
            String tenNganh = nv.getNvManganh(); 
            XtNganh nganh = nganhDAO.getNganhByMaNganh(nv.getNvManganh());
            if (nganh != null) {
                tenNganh = nganh.getTennganh();
            }

            KetQuaTraCuuDTO dto = new KetQuaTraCuuDTO(
                ts.getCccd(),
                ts.getHo(),
                ts.getTen(),
                ts.getNgaySinh(),
                ts.getDoiTuong(),
                ts.getKhuVuc(),
                nv.getNvManganh(),
                tenNganh,
                nv.getNvTt(),
                nv.getTtThm(),        
                nv.getNvKetqua(),
                nv.getTtPhuongthuc(),
                nv.getDiemXettuyen()
            );

            result.add(dto);
        }

        return result;
    }
}