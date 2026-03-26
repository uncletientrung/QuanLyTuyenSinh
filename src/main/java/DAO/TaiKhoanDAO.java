/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author DELL
 */
import ENTITY.TaiKhoan;
import UTIL.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaiKhoanDAO {

    public static TaiKhoanDAO getInstance() {
        return new TaiKhoanDAO();
    }
    // thêm tài khoản
    public boolean insert(TaiKhoan tk) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(tk);
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

    // cập nhật
    public boolean update(TaiKhoan tk) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(tk);

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

    // xóa
    public boolean delete(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            TaiKhoan tk = session.get(TaiKhoan.class, id);

            if (tk != null) {
                session.remove(tk);
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

    // lấy tất cả
    public List<TaiKhoan> getAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("from TaiKhoan", TaiKhoan.class).list();
        }
    }

    // tìm theo id
    public TaiKhoan findById(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(TaiKhoan.class, id);
        }
    }

    // login
    public TaiKhoan login(String username, String password) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from TaiKhoan where tendangnhap = :user and matkhau = :pass",
                    TaiKhoan.class
            )
                    .setParameter("user", username)
                    .setParameter("pass", password)
                    .uniqueResult();
        }
    }
}
