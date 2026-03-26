/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import BUS.TaiKhoanBUS;
import ENTITY.TaiKhoan;
import java.util.List;

/**
 *
 * @author DELL
 */
public class testGUI {
    public static void main(String[] args) {

        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

        List<TaiKhoan> list = taiKhoanBUS.getAllTaiKhoan();

        System.out.println("===== DANH SACH TAI KHOAN =====");

        for (TaiKhoan tk : list) {

            System.out.println(
                    "MaTK: " + tk.getMatk()
                    + " | Username: " + tk.getTendangnhap()
                    + " | Password: " + tk.getMatkhau()
                    + " | MaPhanQuyen: " + tk.getMaphanquyen()
                    + " | TrangThai: " + tk.getTrangthai()
            );
        }

    }
}
