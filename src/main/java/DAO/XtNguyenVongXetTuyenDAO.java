package DAO;

import ENTITY.XtNguyenVongXetTuyen;
import UTIL.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class XtNguyenVongXetTuyenDAO {

    public static XtNguyenVongXetTuyenDAO getInstance() {
        return new XtNguyenVongXetTuyenDAO();
    }

    // ================= INSERT =================
    public boolean insert(XtNguyenVongXetTuyen nv) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(nv);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }

        return false;
    }

    // ================= UPDATE =================
    public boolean update(XtNguyenVongXetTuyen nv) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(nv);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    public boolean delete(int idnv) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            XtNguyenVongXetTuyen nv = session.get(XtNguyenVongXetTuyen.class, idnv);

            if (nv != null) {
                session.remove(nv);
            }

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }

        return false;
    }

    // ================= GET ALL =================
    public List<XtNguyenVongXetTuyen> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery("from XtNguyenVongXetTuyen", XtNguyenVongXetTuyen.class)
                    .list();
        }
    }

    // ================= FIND BY ID =================
    public XtNguyenVongXetTuyen findById(int idnv) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(XtNguyenVongXetTuyen.class, idnv);
        }
    }

    // ================= FIND BY CCCD =================
    public List<XtNguyenVongXetTuyen> findByCCCD(String cccd) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "from XtNguyenVongXetTuyen where nnCccd = :cccd",
                    XtNguyenVongXetTuyen.class
            )
                    .setParameter("cccd", cccd)
                    .list();
        }
    }
}