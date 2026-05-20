package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtNguyenVongXetTuyen;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    public boolean approve(XtNguyenVongXetTuyen nvDuocChon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            XtNguyenVongXetTuyen nv = session.get(XtNguyenVongXetTuyen.class, nvDuocChon.getIdnv());
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

            if(nv.getTtPhuongthuc().equals("Tuyển thẳng")){
                nv.setNvKetqua("Trúng tuyển");
            }
            session.merge(nv);
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
                
                if(nv.getTtPhuongthuc().equals("Tuyển thẳng")){
                    nv.setNvKetqua("Trúng tuyển");
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
        public boolean undo(int idnv) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            XtNguyenVongXetTuyen nv =
                    session.get(XtNguyenVongXetTuyen.class, idnv);
            if (nv == null) {
                transaction.rollback();
                return false;
            }
            nv.setNvKetqua("Đang xét");
            session.merge(nv);
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
    
    
    public List<XtNguyenVongXetTuyen> findByCccdOrderByThuTu(String cccd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM XtNguyenVongXetTuyen WHERE nnCccd = :cccd ORDER BY nvTt ASC",
                    XtNguyenVongXetTuyen.class)
                    .setParameter("cccd", cccd)
                    .list();
        }
    }
    public boolean updateAll(List<XtNguyenVongXetTuyen> list) { // Update lại danh sách nguyện vọng ở DAO
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int batchSize = 50;
            for (int i = 0; i < list.size(); i++) {
                session.merge(list.get(i));
                if ((i + 1) % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            session.flush();
            session.clear();
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
    
    public List<XtNguyenVongXetTuyen> filterNguyenVong(String maNganh, String phuongThuc, String ketQua) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM XtNguyenVongXetTuyen n WHERE 1=1");

            
            boolean locKetQua = ketQua != null && !"Tất cả".equals(ketQua);
            if (!locKetQua) {
                hql.append(" AND n.nvKetqua = 'Trúng tuyển'");
            } else {
                hql.append(" AND n.nvKetqua = :ketQua");
            }

            if (maNganh != null && !"Tất cả".equals(maNganh)) {
                hql.append(" AND n.nvManganh = :maNganh");
            }
            if (phuongThuc != null && !"Tất cả".equals(phuongThuc)) {
                hql.append(" AND n.ttPhuongthuc = :phuongThuc");
            }

            hql.append(" ORDER BY n.nvManganh ASC, n.nvTt ASC");

            var query = session.createQuery(hql.toString(), XtNguyenVongXetTuyen.class);

            if (locKetQua) {
                query.setParameter("ketQua", ketQua);
            }
            if (maNganh != null && !"Tất cả".equals(maNganh)) {
                query.setParameter("maNganh", maNganh);
            }
            if (phuongThuc != null && !"Tất cả".equals(phuongThuc)) {
                query.setParameter("phuongThuc", phuongThuc);
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}