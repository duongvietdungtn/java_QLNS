/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class ChucVu {
    private String macv,chucvu;
   
    public ChucVu(){
    }
    public ChucVu(String macv, String chucvu) {
        this.macv = macv;
        this.chucvu = chucvu;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    @Override
    public String toString() {
        return "ChucVu{" + "macv=" + macv + ", chucvu=" + chucvu + '}';
    }
   
}
