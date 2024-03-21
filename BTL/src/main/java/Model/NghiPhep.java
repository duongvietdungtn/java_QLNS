package Model;

import java.sql.Date;

public class NghiPhep {
    private String manp;
    private String manv;
    private Date ngaynghi;
    private String chophep;

    public NghiPhep() {
    }

    public NghiPhep(String manp, String manv, Date ngaynghi, String chophep) {
        this.manp = manp;
        this.manv = manv;
        this.ngaynghi = ngaynghi;
        this.chophep = chophep;
    }

    public String getManp() {
        return manp;
    }

    public void setManp(String manp) {
        this.manp = manp;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public Date getNgaynghi() {
        return ngaynghi;
    }

    public void setNgaynghi(Date ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public String getChophep() {
        return chophep;
    }

    public void setChophep(String chophep) {
        this.chophep = chophep;
    }
}
