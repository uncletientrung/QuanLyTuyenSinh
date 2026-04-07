package DAO;

import ENTITY.XtThisinhXetTuyen25;
import UTIL.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class XtThisinhXetTuyen25DAO {

    public static XtThisinhXetTuyen25DAO getInstance() {
        return new XtThisinhXetTuyen25DAO();
    }

    public boolean insert(XtThisinhXetTuyen25 ts) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(ts);

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

    public boolean update(XtThisinhXetTuyen25 ts) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(ts);

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

    public boolean delete(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            XtThisinhXetTuyen25 ts =
                    session.get(XtThisinhXetTuyen25.class, id);

            if (ts != null) {
                session.remove(ts);
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

    public List<XtThisinhXetTuyen25> getAll() {

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {

        System.out.println("Session opened");

        List<XtThisinhXetTuyen25> list = session.createQuery(
                "from XtThisinhXetTuyen25",
                XtThisinhXetTuyen25.class
        ).list();

        System.out.println("Data size: " + list.size());

        return list;

    } catch (Exception e) {

        System.out.println("Hibernate error:");
        e.printStackTrace();
    }

    return null;
}
    public XtThisinhXetTuyen25 findById(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(
                    XtThisinhXetTuyen25.class,
                    id
            );
        }
    }

    public XtThisinhXetTuyen25 findByCCCD(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "from XtThisinhXetTuyen25 where cccd = :cccd",
                    XtThisinhXetTuyen25.class
            )
                    .setParameter("cccd", cccd)
                    .uniqueResult();
        }
    }
}