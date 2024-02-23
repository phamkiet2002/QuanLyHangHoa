package model;

import java.io.Serializable;

public class Nhacungcap implements Serializable {
    private int idncc;
    private String tenncc;

    public Nhacungcap() {
    }

    public Nhacungcap(String tenncc) {
        this.tenncc = tenncc;
    }

    public Nhacungcap(int idncc, String tenncc) {
        this.idncc = idncc;
        this.tenncc = tenncc;
    }

    public int getIdncc() {
        return idncc;
    }

    public void setIdncc(int idncc) {
        this.idncc = idncc;
    }

    public String getTenncc() {
        return tenncc;
    }

    public void setTenncc(String tenncc) {
        this.tenncc = tenncc;
    }

    @Override
    public String toString() {
        return  tenncc;
    }

    //    @Override
//    public String toString() {
//        return "idncc=" + idncc + ", tenncc='" + tenncc ;
//    }
}
