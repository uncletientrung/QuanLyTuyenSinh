/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtBangQuyDoi;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import java.math.BigDecimal;
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

    public boolean update(XtBangQuyDoi qd) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(qd);
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

    public boolean delete(int idqd) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            XtBangQuyDoi qd = session.get(XtBangQuyDoi.class, idqd);
            if (qd != null) {
                session.remove(qd);
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
        public XtBangQuyDoi getBQDByPhuongThucVaMonVaDiemDAO(String phuongThuc, String mon, BigDecimal diem) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // 1. Tạo CriteriaBuilder
                var cb = session.getCriteriaBuilder();
                // 2. Tạo CriteriaQuery
                var cq = cb.createQuery(XtBangQuyDoi.class);
                // 3. Khai báo root
                var root = cq.from(XtBangQuyDoi.class);
                // 4. Tạo điều kiện
                var predicate = cb.and(
                        cb.equal(root.get("dPhuongthuc"), phuongThuc),
                        cb.equal(root.get("dMon"), mon),
                        cb.between(cb.literal(diem), root.get("dDiema"), root.get("dDiemb"))
                );
                // 5. Gán điều kiện vào query
                cq.select(root).where(predicate);
                // 6. Thực thi query
                return session.createQuery(cq)
                        .setMaxResults(1)
                        .uniqueResult();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
}
