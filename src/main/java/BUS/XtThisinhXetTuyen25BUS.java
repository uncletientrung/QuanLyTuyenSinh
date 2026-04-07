package BUS;

import DAO.XtThisinhXetTuyen25DAO;
import ENTITY.XtThisinhXetTuyen25;
import java.util.ArrayList;

import java.util.List;

public class XtThisinhXetTuyen25BUS {

    private final XtThisinhXetTuyen25DAO TSDAO = XtThisinhXetTuyen25DAO.getInstance();
    private List<XtThisinhXetTuyen25> listThiSinh;

    public XtThisinhXetTuyen25BUS() {
        listThiSinh = TSDAO.getAll();
        if (listThiSinh == null) {
            listThiSinh = new ArrayList<>();
        }

    }

    public List<XtThisinhXetTuyen25> getAllThiSinh() {
        return listThiSinh;

    }

}