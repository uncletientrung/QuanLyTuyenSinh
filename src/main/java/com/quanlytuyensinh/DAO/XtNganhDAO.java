/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtNganh;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Hi
 */
public class XtNganhDAO {
    public static final XtNganhDAO INSTANCE = new XtNganhDAO();
    private XtNganhDAO() {} 
    
    public static XtNganhDAO getInstance() {
        return INSTANCE;
    }
    
    public boolean insert(XtNganh nganh){
        Transaction transaction = null;
        try(Session sesstion = HibernateUtil.getSessionFactory().openSession()){
            transaction = sesstion.beginTransaction();//bat dau giao dich
            sesstion.persist(nganh);//giao dich
            transaction.commit();//ket thuc giao dicjh
            return true;
        }catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(XtNganh nganh){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(nganh);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
public boolean delete(int idnganh) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            XtNganh nganh = session.get(XtNganh.class, idnganh);
            if (nganh != null) {
                session.remove(nganh);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    public  List<XtNganh> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM XtNganh", XtNganh.class).list();
        }catch(Exception e){
            e.printStackTrace();
            return List.of();//tra ve danh sach rong neu loi
        }
    }
    
   public XtNganh getNganhById(int id){
    try(Session session = HibernateUtil.getSessionFactory().openSession()){
        return session.get(XtNganh.class, id);
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }
}
   
   public XtNganh getNganhByMaNganh(String manganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM XtNganh WHERE manganh = :ma", XtNganh.class)
                .setParameter("ma", manganh)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
   

    
    
    public boolean checkTrungMaNganh(String manganh, int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "FROM XtNganh WHERE manganh = :manganh AND idnganh != :id";
            return !session.createQuery(hql, XtNganh.class)
                    .setParameter("manganh", manganh)
                    .setParameter("id", id)
                    .list()
                    .isEmpty();
        }
    }
    public BigDecimal getDiemTTByMaNganh(String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
            Root<XtNganh> root = cq.from(XtNganh.class);
            cq.select(root.get("nDiemtrungtuyen"))
              .where(cb.equal(root.get("manganh"), maNganh));
            BigDecimal diem = session.createQuery(cq).uniqueResult();
            return diem != null ? diem : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        public BigDecimal getDiemSanByMaNganh(String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
            Root<XtNganh> root = cq.from(XtNganh.class);
            cq.select(root.get("nDiemsan"))
              .where(cb.equal(root.get("manganh"), maNganh));
            BigDecimal diem = session.createQuery(cq).uniqueResult();
            return diem != null ? diem : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        
    public boolean TangSoLuongPhuongThuc(String phuongThuc, String maNganh) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            XtNganh nganh = session.createQuery(
                "FROM XtNganh WHERE manganh = :ma", XtNganh.class)
                .setParameter("ma", maNganh)
                .uniqueResult();

            if (nganh == null) {
                transaction.rollback();
                return false;
            }

            switch (phuongThuc.toUpperCase()) {
                case "THPT" -> {
                    nganh.setSlThpt(nganh.getSlThpt() == null || nganh.getSlThpt() == 0 ? 1 : nganh.getSlThpt() + 1);
                    if (nganh.getNThpt() == null || "0".equals(nganh.getNThpt())) 
                        nganh.setNThpt("1");
                }
                case "VSAT" -> {
                    nganh.setSlVsat(nganh.getSlVsat() == null || nganh.getSlVsat() == 0 ? 1 : nganh.getSlVsat() + 1);
                    if (nganh.getNVsat() == null || "0".equals(nganh.getNVsat())) 
                        nganh.setNVsat("1");
                }
                case "DGNL" -> {
                    nganh.setSlDgnl(nganh.getSlDgnl() == null || nganh.getSlDgnl() == 0 ? 1 : nganh.getSlDgnl() + 1);
                    if (nganh.getNDgnl() == null || "0".equals(nganh.getNDgnl())) 
                        nganh.setNDgnl("1");
                }
                case "Tuyển thẳng" -> {
                    nganh.setSlXtt(nganh.getSlXtt() == null || nganh.getSlXtt() ==  0 ? 1 : nganh.getSlXtt() + 1);
                    if (nganh.getNTuyenthang() == null || "0".equals(nganh.getNTuyenthang())) 
                        nganh.setNTuyenthang("1");
                }
                default -> {
                    transaction.rollback();
                    return false;
                }
            }

            session.merge(nganh);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean resetAllSoLuongPhuongThuc() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = """
                UPDATE XtNganh 
                SET slXtt = 0,
                    slDgnl = 0,
                    slVsat = 0,
                    slThpt = 0,
                    nTuyenthang = NULL,
                    nDgnl = NULL,
                    nThpt = NULL,
                    nVsat = NULL
                """;
            int affectedRows = session.createMutationQuery(hql).executeUpdate();
            transaction.commit();
            return affectedRows > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
