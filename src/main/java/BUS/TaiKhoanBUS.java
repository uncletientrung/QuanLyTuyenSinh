package BUS;

import DAO.TaiKhoanDAO;
import ENTITY.TaiKhoan;
import java.util.ArrayList;

import java.util.List;

public class TaiKhoanBUS {

    private final TaiKhoanDAO taiKhoanDAO = TaiKhoanDAO.getInstance();
    private List<TaiKhoan> listTaiKhoan;

    public TaiKhoanBUS() {
        listTaiKhoan =taiKhoanDAO.getAll();
    }

    // ===================== Lấy danh sách =====================

    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanDAO.getAll();
    }

    // ===================== Thêm tài khoản =====================

    public boolean addTaiKhoan(String tendangnhap,
                              String matkhau,
                              int maphanquyen,
                              int trangthai) {

        // kiểm tra rỗng
        if (tendangnhap == null || tendangnhap.trim().isEmpty()) {
            System.out.println("Tên đăng nhập không được rỗng");
            return false;
        }

        if (matkhau == null || matkhau.trim().isEmpty()) {
            System.out.println("Mật khẩu không được rỗng");
            return false;
        }

        // tạo object
        TaiKhoan tk = new TaiKhoan(
                tendangnhap,
                matkhau,
                maphanquyen,
                trangthai
        );

        return taiKhoanDAO.insert(tk);
    }

    // ===================== Cập nhật =====================

    public boolean updateTaiKhoan(int matk,
                                  String tendangnhap,
                                  String matkhau,
                                  int maphanquyen,
                                  int trangthai) {

        TaiKhoan tk = taiKhoanDAO.findById(matk);

        if (tk == null) {
            System.out.println("Không tìm thấy tài khoản");
            return false;
        }

        tk.setTendangnhap(tendangnhap);
        tk.setMatkhau(matkhau);
        tk.setMaphanquyen(maphanquyen);
        tk.setTrangthai(trangthai);

        return taiKhoanDAO.update(tk);
    }

    // ===================== Xóa =====================

    public boolean deleteTaiKhoan(int matk) {

        TaiKhoan tk = taiKhoanDAO.findById(matk);

        if (tk == null) {
            System.out.println("Không tồn tại tài khoản");
            return false;
        }

        return taiKhoanDAO.delete(tk.getMatk());
    }

    // ===================== Tìm theo ID =====================

    public TaiKhoan findById(int matk) {
        return taiKhoanDAO.findById(matk);
    }

    // ===================== Login =====================

    public TaiKhoan login(String username, String password) {

        if (username == null || username.isEmpty()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        return taiKhoanDAO.login(username, password);
    }
}