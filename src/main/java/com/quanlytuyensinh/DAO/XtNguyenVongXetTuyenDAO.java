package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class XtNguyenVongXetTuyenDAO {
    private  XtNganhDAO nganhDAO = XtNganhDAO.getInstance();
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
    // SỬA TRẠNG THÁI KẾT QUẢ
    public boolean approve(int idnv, String ketQua) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            XtNguyenVongXetTuyen nv = session.get(XtNguyenVongXetTuyen.class, idnv);
            if (nv == null) return false;
            nv.setNvKetqua(ketQua);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }
    // SỬA TRẠNG THÁI TẤT CẢ NGUYỆN VỌNG
    public boolean approveAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<XtNguyenVongXetTuyen> list =this.getAll();
            
            for (XtNguyenVongXetTuyen nv : list) {
                BigDecimal diemTT = nganhDAO.getDiemTTByMaNganh(nv.getNvManganh()); 
                BigDecimal diemSan = nganhDAO.getDiemSanByMaNganh(nv.getNvManganh()); 
                if (nv.getDiemXettuyen() == null) {
                    nv.setNvKetqua("Chưa có điểm");
                } else if (diemSan != null  && nv.getDiemXettuyen().compareTo(diemSan) < 0) {
                    nv.setNvKetqua("Rớt điểm sàn");
                } else if (diemTT == null) {
                    nv.setNvKetqua("Đang xét");
                } else if (nv.getDiemXettuyen().compareTo(diemTT) >= 0) {
                    nv.setNvKetqua("Trúng tuyển");
                } else {
                    nv.setNvKetqua("Không trúng tuyển");
                }
                    session.merge(nv);
                }
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

    public boolean undoAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<XtNguyenVongXetTuyen> list =this.getAll();
            
            for (XtNguyenVongXetTuyen nv : list) {
                    nv.setNvKetqua("Đang xét");
                session.merge(nv);
            }
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
    
    public boolean kiemTraNVTonTai(String cccd, String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            Root<XtNguyenVongXetTuyen> root =
                    cq.from(XtNguyenVongXetTuyen.class);

            cq.select(cb.count(root));

            cq.where(
                    cb.and(
                            cb.equal(root.get("nnCccd"), cccd),
                            cb.equal(root.get("nvManganh"), maNganh)
                    )
            );

            Long count = session.createQuery(cq).getSingleResult();

            return count != null && count > 0;
        }
}
}