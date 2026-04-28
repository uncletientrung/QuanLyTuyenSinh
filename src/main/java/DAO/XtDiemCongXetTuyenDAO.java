/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.XtDiemCongXetTuyen;
import UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public boolean insert(XtDiemCongXetTuyen dc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(dc);
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

    public boolean update(XtDiemCongXetTuyen dc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(dc);
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

    public boolean delete(int iddiemcong) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            XtDiemCongXetTuyen dc = session.get(XtDiemCongXetTuyen.class, iddiemcong);
            if (dc != null) {
                session.remove(dc);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
