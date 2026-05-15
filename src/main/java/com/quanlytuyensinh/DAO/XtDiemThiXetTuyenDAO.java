package com.quanlytuyensinh.DAO;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quanlytuyensinh.ENTITY.XtDiemThiXetTuyen;
import com.quanlytuyensinh.UTIL.HibernateUtil;

public class XtDiemThiXetTuyenDAO {
    public static XtDiemThiXetTuyenDAO getInstance() {
        return new XtDiemThiXetTuyenDAO();
    }   

    public List<XtDiemThiXetTuyen> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Session opened");

            List<XtDiemThiXetTuyen> list = session.createQuery(
                    "from XtDiemThiXetTuyen",
                    XtDiemThiXetTuyen.class
            ).list();

            System.out.println("Data size: " + list.size());

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }
        return null;
    }

    public List<XtDiemThiXetTuyen> getAllTHPT() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Session opened");

            List<XtDiemThiXetTuyen> list = session.createQuery(
                    "from XtDiemThiXetTuyen d where d.dPhuongthuc = 'THPT'",
                    XtDiemThiXetTuyen.class
            ).list();

            System.out.println("Data size: " + list.size());

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }
        return null;
    }

    public List<XtDiemThiXetTuyen> getAllDGNL() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Session opened");

            List<XtDiemThiXetTuyen> list = session.createQuery(
                    "from XtDiemThiXetTuyen d where d.dPhuongthuc = 'DGNL'",
                    XtDiemThiXetTuyen.class
            ).list();

            System.out.println("Data size: " + list.size());

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }
        return null;
    }

    
    public List<XtDiemThiXetTuyen> getAllVSAT() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            System.out.println("Session opened");

            List<XtDiemThiXetTuyen> list = session.createQuery(
                    "from XtDiemThiXetTuyen d where d.dPhuongthuc = 'VSAT'",
                    XtDiemThiXetTuyen.class
            ).list();

            System.out.println("Data size: " + list.size());

            return list;

        } catch (Exception e) {

            System.out.println("Hibernate error:");
            e.printStackTrace();
        }
        return null;
    }

    public boolean existCCCD(String cccd, String cccd1, String pt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(m) from XtDiemThiXetTuyen m where m.cccd = :cccd and m.cccd != :cccd1 and m.dPhuongthuc = :pt";
            Long count = session.createQuery(hql, Long.class)
                                .setParameter("cccd", cccd)
                                .setParameter("cccd1", cccd1)
                                .setParameter("pt", pt)
                                .uniqueResult();
            return count != null && count > 0;
        }
    }

    public XtDiemThiXetTuyen findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select t from XtDiemThiXetTuyen t where t.iddiemthi = :id", XtDiemThiXetTuyen.class)
                        .setParameter("id", id)
                        .uniqueResult();
        }
    }

    public XtDiemThiXetTuyen findByCCCDAndPT(String cccd, String pt) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select t from XtDiemThiXetTuyen t where t.cccd = :cccd and t.dPhuongthuc = :pt", XtDiemThiXetTuyen.class)
                        .setParameter("cccd", cccd)
                        .setParameter("pt", pt)
                        .uniqueResult();
        }
    }

    public boolean delete(XtDiemThiXetTuyen t) {
         Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(t);
            transaction.commit();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , e.getMessage());
            transaction.rollback();
            return false;
        }
    }

    public boolean add(XtDiemThiXetTuyen t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , e.getMessage());
            transaction.rollback();
            return false;
        }
    }

    public boolean update(XtDiemThiXetTuyen t) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(t);
            transaction.commit();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , e.getMessage());
            transaction.rollback();
            return false;
        }
    }

    public void updateCert(String cccd, String diem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery("update XtDiemThiXetTuyen t set t.n1Cc = :diem where t.cccd = :cccd and t.dPhuongthuc = 'THPT'")
                        .setParameter("cccd", cccd)
                        .setParameter("diem", diem)
                        .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void importToDB(List<XtDiemThiXetTuyen> list) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            int batchSize = 50;
            for (int i = 0; i < list.size(); i++) {
                session.persist(list.get(i));

                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateToDB(List<XtDiemThiXetTuyen> list) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            int batchSize = 50;
            for (int i = 0; i < list.size(); i++) {
                session.merge(list.get(i));

                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    
    public List<XtDiemThiXetTuyen> findByCccd(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM XtDiemThiXetTuyen WHERE cccd = :cccd",
                    XtDiemThiXetTuyen.class)
                .setParameter("cccd", cccd)
                .list();
        }
    }
}