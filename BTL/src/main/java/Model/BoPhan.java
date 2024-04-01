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
public class BoPhan {
    private String mabp;
    private String bophan;
    private Date ngaythanhlap;
    
    public BoPhan(){
        
    }
    
    public BoPhan(String mabp, String bophan, Date ngaythanhlap) {
        this.mabp = mabp;
        this.bophan = bophan;
        this.ngaythanhlap = ngaythanhlap;
    }

    public String getMabp() {
        return mabp;
    }

    public void setMabp(String mabp) {
        this.mabp = mabp;
    }

    public String getBophan() {
        return bophan;
    }

    public void setBophan(String bophan) {
        this.bophan = bophan;
    }

    public Date getNgaythanhlap() {
        return ngaythanhlap;
    }

    public void setNgaythanhlap(Date ngaythanhlap) {
        this.ngaythanhlap = ngaythanhlap;
    }

    @Override
    public String toString() {
        return "BoPhan{" + "mabp=" + mabp + ", bophan=" + bophan + ", ngaythanhlap=" + ngaythanhlap + '}';
    }

    
}
