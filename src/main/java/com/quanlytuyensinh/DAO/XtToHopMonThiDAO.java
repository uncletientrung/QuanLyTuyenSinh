/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtToHopMonThi;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ASUS
 */

public class XtToHopMonThiDAO {
    public static XtToHopMonThiDAO getInstance() {
        return new XtToHopMonThiDAO();
    }
    
    public List<XtToHopMonThi> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from XtToHopMonThi", XtToHopMonThi.class).list();
        }
    }
    
    public boolean existMaToHop(String ma)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(m) from XtToHopMonThi m where m.matohop = :ma";
            Long count = session.createQuery(hql, Long.class)
                                .setParameter("ma", ma)
                                .uniqueResult();
            return count != null && count > 0;
        }
    }

    public String existToHopMon(List<String> toHop) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select m.matohop from XtToHopMonThi m where m.mon1 IN (:toHop) AND  m.mon2 IN (:toHop) AND  m.mon3 IN (:toHop)";
            String res = session.createQuery(hql, String.class)
                                .setParameter("toHop", toHop)
                                .uniqueResult();
            if (res == null)
                return "";
            else
                return res;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public void importToDB(List<XtToHopMonThi> list)
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
