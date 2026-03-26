/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import ENTITY.PhanQuyen;
import UTIL.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PhanQuyenDAO {

    public static PhanQuyenDAO getInstance() {
        return new PhanQuyenDAO();
    }
    // Lấy tất cả quyền
    public List<PhanQuyen> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM PhanQuyen", PhanQuyen.class).list();
        }
    }

    // Lấy quyền theo ID
    public PhanQuyen getById(int maPhanQuyen) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(PhanQuyen.class, maPhanQuyen);
        }
    }

    // Thêm mới quyền
    public boolean add(PhanQuyen pq) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pq);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật quyền
    public boolean update(PhanQuyen pq) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(pq);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Xóa quyền
    public boolean delete(PhanQuyen pq) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(pq);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
