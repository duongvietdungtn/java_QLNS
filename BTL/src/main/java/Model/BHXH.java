/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class BHXH {
    private String mabaohiem;
    private String manv;
    private String loaibaohiem;
    private Date ngaybatdau;
    private Date ngayketthuc;
    
    public BHXH(){
        
    }

    public BHXH(String mabaohiem, String manv, String loaibaohiem, Date ngaybatdau, Date ngayketthuc) {
        this.mabaohiem = mabaohiem;
        this.manv = manv;
        this.loaibaohiem = loaibaohiem;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
    }

    public String getMabaohiem() {
        return mabaohiem;
    }

    public void setMabaohiem(String mabaohiem) {
        this.mabaohiem = mabaohiem;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getLoaibaohiem() {
        return loaibaohiem;
    }

    public void setLoaibaohiem(String loaibaohiem) {
        this.loaibaohiem = loaibaohiem;
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

    @Override
    public String toString() {
        return "BHXH{" + "mabaohiem=" + mabaohiem + ", manv=" + manv + ", loaibaohiem=" + loaibaohiem + ", ngaybatdau=" + ngaybatdau + ", ngayketthuc=" + ngayketthuc + '}';
    }

}
