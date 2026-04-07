/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import BUS.TaiKhoanBUS;
import BUS.XtThisinhXetTuyen25BUS;
import java.util.List;
import ENTITY.TaiKhoan;
import ENTITY.XtThisinhXetTuyen25;

/**
 *
 * @author DELL
 */
public class testGUI {
    public static void main(String[] args) {

        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        XtThisinhXetTuyen25BUS TSBUS =  new XtThisinhXetTuyen25BUS();
        List<XtThisinhXetTuyen25> list = TSBUS.getAllThiSinh();
        System.out.println("===== DANH SACH TAI KHOAN =====");

        for (XtThisinhXetTuyen25 tk : list) {

            System.out.println(
                    tk.toString()
            );
        }

    }
}
