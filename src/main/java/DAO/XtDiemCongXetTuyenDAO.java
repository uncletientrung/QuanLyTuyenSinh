/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.XtDiemCongXetTuyen;
import UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Windows
 */
public class XtDiemCongXetTuyenDAO {
     public static XtDiemCongXetTuyenDAO getInstance() {
        return new XtDiemCongXetTuyenDAO();
    }

    public List<XtDiemCongXetTuyen> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<XtDiemCongXetTuyen> list = session.createQuery(
                    "from XtDiemCongXetTuyen",
                    XtDiemCongXetTuyen.class
            ).list();

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }

        return null;
    }

}
