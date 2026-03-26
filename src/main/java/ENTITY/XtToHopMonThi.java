package ENTITY;

import jakarta.persistence.*;

@Entity
@Table(name = "xt_tohop_monthi")
public class XtToHopMonThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtohop")
    private int idtohop;

    @Column(name = "matohop", nullable = false, unique = true, length = 45)
    private String matohop;

    @Column(name = "mon1", nullable = false, length = 10)
    private String mon1;

    @Column(name = "mon2", nullable = false, length = 10)
    private String mon2;

    @Column(name = "mon3", nullable = false, length = 10)
    private String mon3;

    @Column(name = "tentohop", length = 100)
    private String tentohop;

    public XtToHopMonThi() {}

    // ==================== GETTER & SETTER ====================

    public int getIdtohop() { return idtohop; }
    public void setIdtohop(int idtohop) { this.idtohop = idtohop; }

    public String getMatohop() { return matohop; }
    public void setMatohop(String matohop) { this.matohop = matohop; }

    public String getMon1() { return mon1; }
    public void setMon1(String mon1) { this.mon1 = mon1; }

    public String getMon2() { return mon2; }
    public void setMon2(String mon2) { this.mon2 = mon2; }

    public String getMon3() { return mon3; }
    public void setMon3(String mon3) { this.mon3 = mon3; }

    public String getTentohop() { return tentohop; }
    public void setTentohop(String tentohop) { this.tentohop = tentohop; }

    @Override
    public String toString() {
        return "XtToHopMonThi{" +
                "idtohop=" + idtohop +
                ", matohop='" + matohop + '\'' +
                ", mon1='" + mon1 + '\'' +
                ", mon2='" + mon2 + '\'' +
                ", mon3='" + mon3 + '\'' +
                ", tentohop='" + tentohop + '\'' +
                '}';
    }
}