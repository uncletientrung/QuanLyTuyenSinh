/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.Mon;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */

public class MonDAO {
    public static MonDAO getInstance() {
        return new MonDAO();
    }
    
    public List<Mon> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Mon", Mon.class).list();
        }
    }
    
    public boolean existMaToHop(String ma)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(m) from Mon m where m.matohop = :ma";
            Long count = session.createQuery(hql, Long.class)
                                .setParameter("ma", ma)
                                .uniqueResult();
            return count != null && count > 0;
        }
    }
    
    public void importToDB(List<Mon> list)
    {
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
}
