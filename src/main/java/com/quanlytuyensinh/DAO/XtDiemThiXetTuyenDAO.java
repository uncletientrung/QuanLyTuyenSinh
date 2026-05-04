package com.quanlytuyensinh.DAO;

import java.util.List;

import org.hibernate.Session;

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

    public XtDiemThiXetTuyen findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select t from XtDiemThiXetTuyen t where t.iddiemthi = :id", XtDiemThiXetTuyen.class)
                        .setParameter("id", id)
                        .uniqueResult();
        }
    }
}