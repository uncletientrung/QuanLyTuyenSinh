/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Hi
 */
public class XtNganhToHopDAO {
    public static final XtNganhToHopDAO INSTANCE = new XtNganhToHopDAO();
    private XtNganhToHopDAO(){};
    public static XtNganhToHopDAO getInstance(){
        return INSTANCE;
    }
    
    public List<XtNganhToHop> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM XtNganhToHop", XtNganhToHop.class).list();
        }catch(Exception e){
            e.printStackTrace();
            return List.of();//tra ve danh sach rong neu loi
        }
    }
    
    public boolean delete(int idNganhToHop){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            XtNganhToHop nganhTH = session.get(XtNganhToHop.class, idNganhToHop);
            if(nganhTH != null){
                session.remove(nganhTH);
            }
            transaction.commit();
            return true;
        }catch(Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    
    public XtNganhToHop getNganhTHById(int idNganhTH){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(XtNganhToHop.class, idNganhTH);
        }catch(Exception e){
        e.printStackTrace();
        return null;
    }
        
        
        
    }
    
    public boolean insert(XtNganhToHop nth){
        Transaction transaction = null;
        try(Session sesstion = HibernateUtil.getSessionFactory().openSession()){
            transaction = sesstion.beginTransaction();//bat dau giao dich
            sesstion.persist(nth);//giao dich
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
    
    public boolean update(XtNganhToHop nth){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(nth);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
