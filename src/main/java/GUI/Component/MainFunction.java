/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author DELL
 */
public final class MainFunction extends JToolBar {

    public HashMap<String, ButtonToolBar> btn = new HashMap<>();

    public MainFunction(int manhomquyen, String chucnang, String[] listBtn) {
        initComponent(listBtn);
//        NhomQuyenBUS nhomquyenBus = new NhomQuyenBUS();
//        for (String s : listBtn) {
//            if (btn.containsKey(s)) {
//                String permission = btn.get(s).getPermisson();
//                if (!nhomquyenBus.checkPermisson(manhomquyen, chucnang, permission)) {
//                    btn.get(s).setEnabled(false);
//                }
//            }
//        }
    }

    private void initComponent(String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        this.setFloatable(false);

        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("delete", new ButtonToolBar("XÓA", "delete.svg", "delete"));
        btn.put("update", new ButtonToolBar("SỬA", "edit.svg", "update"));
        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
        btn.put("import", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));

        for (String s : listBtn) {
            if (btn.containsKey(s)) {
                this.add(btn.get(s)); // Nếu có thì add vào ToolBar
            }
        }
    }
}
