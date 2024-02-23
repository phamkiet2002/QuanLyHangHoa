package model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    private int id;
    private String ten;
    private String mota;
    private String ncc;
    private int gia;
    private byte[] hinh;

    public Sanpham() {
    }

    public Sanpham(int id, String ten, String mota) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
    }

    public Sanpham(String ten, String mota, String ncc, int gia, byte[] hinh) {
        this.ten = ten;
        this.mota = mota;
        this.ncc = ncc;
        this.gia = gia;
        this.hinh = hinh;
    }

    public Sanpham(int id, String ten, String mota, String ncc, int gia, byte[] hinh) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.ncc = ncc;
        this.gia = gia;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNcc() {
        return ncc;
    }

    public void setNcc(String ncc) {
        this.ncc = ncc;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    @Override
    public String toString() {
        return "sanpham{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", ncc='" + ncc + '\'' +
                '}';
    }
}
