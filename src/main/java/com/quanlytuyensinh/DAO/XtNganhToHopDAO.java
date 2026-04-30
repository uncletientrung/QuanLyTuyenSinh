/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.DAO;

import com.quanlytuyensinh.ENTITY.XtNganhToHop;
import com.quanlytuyensinh.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

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
}
