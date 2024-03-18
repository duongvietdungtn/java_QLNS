/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;

/**
 *
 * @author Dugdug
 */
public class model_ttnhanvien {
    private String manv, hoten, diachi, mahd, phongban, mabaohiem, gioitinh, path;
    private int cccd, sdt;
    private Date ngaysinh;
    
    public model_ttnhanvien(){
        
    }
    
    public model_ttnhanvien(int cccd, int sdt, String manv, String hoten, String diachi, String mahd, String phongban, String mabaohiem, String gioitinh, String path){
        this.cccd = cccd;
        this.manv = manv;
        this.hoten = hoten;
        this.diachi = diachi;
        this.sdt = sdt;
        this.mahd = mahd;
        this.phongban = phongban;
        this.mabaohiem = mabaohiem;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.path = path;
    }

    public int getSdt() {
        return sdt;
    }
    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
    
    public int getCCCD(){
        return cccd;
    }
    public void setCCCD(){
        this.cccd = cccd;
    }
    
    public String getManv(){
        return manv;
    }
    public void setManv(String manv){
        this.manv = manv;
    }

    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    // Getter và setter cho diachi
    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    // Getter và setter cho mahopdong
    public String getMahd() {
        return mahd;
    }
    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    // Getter và setter cho phongban
    public String getPhongban() {
        return phongban;
    }
    public void setPhongban(String phongban) {
        this.phongban = phongban;
    }

    // Getter và setter cho mabaohiem
    public String getMabaohiem() {
        return mabaohiem;
    }
    public void setMabaohiem(String mabaohiem) {
        this.mabaohiem = mabaohiem;
    }

    // Getter và setter cho gioitinh
    public String getGioitinh() {
        return gioitinh;
    }
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    // Getter và setter cho ngaysinh
    public Date getNgaysinh() {
        return ngaysinh;
    }
    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    // Getter và setter cho path
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
