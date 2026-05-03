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
            return session.createQuery("from XtDiemThiXetTuyen", XtDiemThiXetTuyen.class).list();
        }
    }

    public XtDiemThiXetTuyen findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select t from XtDiemThiXetTuyen t where t.iddiemthi = :id", XtDiemThiXetTuyen.class)
                        .setParameter("id", id)
                        .uniqueResult();
        }
    }
}