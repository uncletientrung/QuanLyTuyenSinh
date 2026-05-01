/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlytuyensinh.ENTITY;

import jakarta.persistence.*;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "xt_tohop_monthi")
public class Mon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtohop")
    private Integer idtohop;
    
    @Column(name = "matohop")
    private String matohop;
    
    @Column(name = "mon1")
    private String mon1;
    
    @Column(name = "mon2")
    private String mon2;
    
    @Column(name = "mon3")
    private String mon3;
    
    @Column(name = "tentohop")
    private String tentohop;
    
    public Mon() {};
    public Mon(String matohop, String mon1, String mon2, String mon3, String tentohop) {
        this.matohop = matohop;
        this.mon1 = mon1;
        this.mon2 = mon2;
        this.mon3 = mon3;
        this.tentohop = tentohop;
    };
    
    public int getId() { return idtohop;};
    public String getMaToHop() {return matohop;};
    public String getMon1() {return mon1;};
    public String getMon2() {return mon2;};
    public String getMon3() {return mon3;};
    public String getTenToHop() {return tentohop;};
}
