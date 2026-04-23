/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.XtNganh;
import UTIL.HibernateUtil;
import java.util.List;
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
    
    public boolean delete(int id){
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            // lấy ngành
            XtNganh nganh = session.get(XtNganh.class, id);
            if (nganh == null) return false;

            String maNganh = nganh.getManganh();

            Long count1 = session.createQuery(
                "SELECT COUNT(dc) FROM XtDiemCongXetTuyen dc WHERE dc.maNganh = :ma",
                Long.class
            )
            .setParameter("ma", maNganh)
            .getSingleResult();

            Long count2 = session.createQuery(
                "SELECT COUNT(nt) FROM XtNganhToHop nt WHERE nt.manganh = :ma",
                Long.class
            )
            .setParameter("ma", maNganh)
            .getSingleResult();

            if (count1 > 0 || count2 > 0) {
                return false;
            }

            session.remove(nganh);

            transaction.commit();
            return true;

        }catch (Exception e) {
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
}
