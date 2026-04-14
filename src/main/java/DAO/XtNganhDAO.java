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
    
    public  List<XtNganh> getAll(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM XtNganh", XtNganh.class).list();
        }catch(Exception e){
            e.printStackTrace();
            return List.of();//tra ve danh sach rong neu loi
        }
    }
}
