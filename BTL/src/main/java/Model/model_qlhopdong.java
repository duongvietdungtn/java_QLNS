/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Dugdug
 */
public class model_qlhopdong {
    private String mahd, manv, hoten;
    private Date ngaybatdau, ngayketthuc;

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Date getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(Date ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public Date getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(Date ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public model_qlhopdong(String mahd, String manv, String hoten, Date ngaybatdau, Date ngayketthuc) {
        this.mahd = mahd;
        this.manv = manv;
        this.hoten = hoten;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
    }
    
    public model_qlhopdong(){
        
    }
}
