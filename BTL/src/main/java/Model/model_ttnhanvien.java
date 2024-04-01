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
public class model_ttnhanvien {
    private String manv, hoten, diachi, mahd, bophan, mabaohiem, gioitinh, path, chucvu, sdt;
    private long cccd;
    private Date ngaysinh;

    public model_ttnhanvien(String manv, String hoten, String diachi, String mahd, String bophan, String mabaohiem, String gioitinh, String path, String chucvu, String sdt, long cccd, Date ngaysinh) {
        this.manv = manv;
        this.hoten = hoten;
        this.diachi = diachi;
        this.mahd = mahd;
        this.bophan = bophan;
        this.mabaohiem = mabaohiem;
        this.gioitinh = gioitinh;
        this.path = path;
        this.chucvu = chucvu;
        this.sdt = sdt;
        this.cccd = cccd;
        this.ngaysinh = ngaysinh;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getBophan() {
        return bophan;
    }

    public void setBophan(String bophan) {
        this.bophan = bophan;
    }

    public String getMabaohiem() {
        return mabaohiem;
    }

    public void setMabaohiem(String mabaohiem) {
        this.mabaohiem = mabaohiem;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getCccd() {
        return cccd;
    }

    public void setCccd(long cccd) {
        this.cccd = cccd;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
    public model_ttnhanvien() {
        
    }   
}