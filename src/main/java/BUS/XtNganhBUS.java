/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DAO.XtNganhDAO;
import ENTITY.XtNganh;
import java.math.BigDecimal;
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

 public String insertNganh(XtNganh nganh) {
        
        if (nganh == null) {
            return "Dữ liệu ngành không được để trống!";
        }
        
        if (nganh.getManganh() == null || nganh.getManganh().trim().isEmpty()) {
            return "Mã ngành không được để trống!";
        }
        if (nganh.getManganh().length() > 45) {
            return "Mã ngành không được vượt quá 45 ký tự!";
        }
        if (nganhDAO.checkTrungMaNganh(nganh.getManganh(), nganh.getIdnganh())) {
            return "Mã ngành '" + nganh.getManganh() + "' đã tồn tại!";
        }
        
        if (nganh.getTennganh() == null || nganh.getTennganh().trim().isEmpty()) {
            return "Tên ngành không được để trống!";
        }
        if (nganh.getTennganh().length() > 100) {
            return "Tên ngành không được vượt quá 100 ký tự!";
        }
        
        if (nganh.getNTohopgoc() != null && nganh.getNTohopgoc().length() > 3) {
            return "Tổ hợp gốc không được vượt quá 3 ký tự (VD: A00, D01...)!";
        }
        
        if (nganh.getNChitieu() <= 0) {
            return "Chỉ tiêu phải lớn hơn 0!";
        }

        if (nganh.getNDiemsan() != null && nganh.getNDiemsan().compareTo(BigDecimal.ZERO) < 0) {
            return "Điểm sàn không được là số âm!";
        }
        if (nganh.getNDiemtrungtuyen() != null && nganh.getNDiemtrungtuyen().compareTo(BigDecimal.ZERO) < 0) {
            return "Điểm trúng tuyển không được là số âm!";
        }

        if (nganh.getNDiemsan() != null && nganh.getNDiemtrungtuyen() != null) {
            if (nganh.getNDiemtrungtuyen().compareTo(nganh.getNDiemsan()) < 0) {
                return "Điểm trúng tuyển không được nhỏ hơn điểm sàn!";
            }
        }

        if (!isValidFlag(nganh.getNTuyenthang())) {
            return "Vui lòng chọn trạng thái 'Tuyển thẳng' (Có/Không)!";
        }
        if (!isValidFlag(nganh.getNDgnl())) {
            return "Vui lòng chọn trạng thái 'ĐGNL' (Có/Không)!";
        }
        if (!isValidFlag(nganh.getNThpt())) {
            return "Vui lòng chọn trạng thái 'THPT' (Có/Không)!";
        }
        if (!isValidFlag(nganh.getNVsat())) {
            return "Vui lòng chọn trạng thái 'VSAT' (Có/Không)!";
        }

        if (nganh.getSlXtt() != null && nganh.getSlXtt() < 0) {
            return "Số lượng Xét tuyển thẳng phải >= 0!";
        }
        if (nganh.getSlDgnl() != null && nganh.getSlDgnl() < 0) {
            return "Số lượng ĐGNL phải >= 0!";
        }
        if (nganh.getSlVsat() != null && nganh.getSlVsat() < 0) {
            return "Số lượng VSAT phải >= 0!";
        }
        
        if (nganh.getSlThpt() != null && nganh.getSlThpt().length() > 45) {
            return "Số lượng THPT không được vượt quá 45 ký tự!";
        }

        boolean result = nganhDAO.insert(nganh);
        
        if (result) {
            return "Thêm ngành thành công!";
        } else {
            return "Thêm ngành thất bại! Vui lòng thử lại.";
        }
    }


    private boolean isValidFlag(String flag) {
        
        if (flag == null || flag.trim().isEmpty()) {
            return false; 
        }
        flag = flag.trim();
        return flag.equals("1") || flag.equals("0");
    }
    public List<XtNganh> getAllNganh() {
        return nganhDAO.getAll();
    }
    
    public boolean updateNganh(XtNganh nganh) {

    if (nganh == null || nganh.getIdnganh() <= 0) {
        throw new RuntimeException("Dữ liệu ngành không hợp lệ!");
    }

    if (nganh.getManganh() == null || nganh.getManganh().trim().isEmpty()) {
        throw new RuntimeException("Mã ngành không được để trống!");
    }
    if (nganh.getManganh().length() > 45) {
        throw new RuntimeException("Mã ngành không được vượt quá 45 ký tự!");
    }

    if (nganhDAO.checkTrungMaNganh(nganh.getManganh(), nganh.getIdnganh())) {
        throw new RuntimeException("Mã ngành '" + nganh.getManganh() + "' đã tồn tại!");
    }

    if (nganh.getTennganh() == null || nganh.getTennganh().trim().isEmpty()) {
        throw new RuntimeException("Tên ngành không được để trống!");
    }
    if (nganh.getTennganh().length() > 100) {
        throw new RuntimeException("Tên ngành không được vượt quá 100 ký tự!");
    }

    if (nganh.getNTohopgoc() != null && nganh.getNTohopgoc().length() > 3) {
        throw new RuntimeException("Tổ hợp gốc không hợp lệ (VD: A00, D01...)!");
    }

    if (nganh.getNChitieu() <= 0) {
        throw new RuntimeException("Chỉ tiêu phải lớn hơn 0!");
    }

    if (nganh.getNDiemsan() != null && nganh.getNDiemsan().compareTo(BigDecimal.ZERO) < 0) {
        throw new RuntimeException("Điểm sàn không được âm!");
    }
    if (nganh.getNDiemtrungtuyen() != null && nganh.getNDiemtrungtuyen().compareTo(BigDecimal.ZERO) < 0) {
        throw new RuntimeException("Điểm trúng tuyển không được âm!");
    }
    if (nganh.getNDiemsan() != null && nganh.getNDiemtrungtuyen() != null &&
        nganh.getNDiemtrungtuyen().compareTo(nganh.getNDiemsan()) < 0) {
        throw new RuntimeException("Điểm trúng tuyển không được nhỏ hơn điểm sàn!");
    }

    if (!isValidFlag(nganh.getNTuyenthang())) {
        throw new RuntimeException("Chọn Tuyển thẳng (Có/Không)!");
    }
    if (!isValidFlag(nganh.getNDgnl())) {
        throw new RuntimeException("Chọn ĐGNL (Có/Không)!");
    }
    if (!isValidFlag(nganh.getNThpt())) {
        throw new RuntimeException("Chọn THPT (Có/Không)!");
    }
    if (!isValidFlag(nganh.getNVsat())) {
        throw new RuntimeException("Chọn VSAT (Có/Không)!");
    }

    if (nganh.getSlXtt() != null && nganh.getSlXtt() < 0) {
        throw new RuntimeException("SL Tuyển thẳng phải >= 0!");
    }
    if (nganh.getSlDgnl() != null && nganh.getSlDgnl() < 0) {
        throw new RuntimeException("SL ĐGNL phải >= 0!");
    }
    if (nganh.getSlVsat() != null && nganh.getSlVsat() < 0) {
        throw new RuntimeException("SL VSAT phải >= 0!");
    }

    if (nganh.getSlThpt() != null && nganh.getSlThpt().length() > 45) {
        throw new RuntimeException("SL THPT không hợp lệ!");
    }

    boolean result = nganhDAO.update(nganh);

    if (!result) {
        throw new RuntimeException("Cập nhật thất bại!");
    }

    return true;
}
    
    public XtNganh getNganhById(int id){
        return nganhDAO.getNganhById(id);
    }
    
    public String deleteNganh(int idnganh) {
        if (idnganh <= 0) {
            return "ID ngành không hợp lệ!";
        }

   

        boolean result = nganhDAO.delete(idnganh);
        
        if (result) {
            return "Xóa ngành thành công!";
        } else {
            return "Xóa ngành thất bại! Ngành có thể đang được sử dụng.";
        }
    }
}
