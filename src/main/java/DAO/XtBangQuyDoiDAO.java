/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.XtBangQuyDoi;
import UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            List<XtBangQuyDoi> list = session.createQuery(
                    "from XtBangQuyDoi",
                    XtBangQuyDoi.class
            ).list();

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(XtBangQuyDoi qd) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(qd);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
