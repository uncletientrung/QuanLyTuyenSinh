/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.XtBangQuyDoi;
import UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Windows
 */
public class XtBangQuyDoiDAO {

    public static XtBangQuyDoiDAO getInstance() {
        return new XtBangQuyDoiDAO();
    }

    public List<XtBangQuyDoi> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Session opened");

            List<XtBangQuyDoi> list = session.createQuery(
                    "from XtBangQuyDoi",
                    XtBangQuyDoi.class
            ).list();

            System.out.println("Data size: " + list.size());

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }
        
        return null;
    }

}
