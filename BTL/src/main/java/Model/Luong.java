
package Model;

public class Luong {
    private String maluong, manv;
    private int luongcung, thuong, phat, tongluong;

    public Luong() {
      
    }
    public Luong(String maluong, String manv, int luongcung, int thuong, int phat, int tongluong) {
        this.maluong = maluong;
        this.manv = manv;
        this.luongcung = luongcung;
        this.thuong = thuong;
        this.phat = phat;
        this.tongluong = tongluong;
    }

    public String getMaluong() {
        return maluong;
    }

    public void setMaluong(String maluong) {
        this.maluong = maluong;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public int getLuongcung() {
        return luongcung;
    }

    public void setLuongcung(int luongcung) {
        this.luongcung = luongcung;
    }

    public int getThuong() {
        return thuong;
    }

    public void setThuong(int thuong) {
        this.thuong = thuong;
    }

    public int getPhat() {
        return phat;
    }

    public void setPhat(int phat) {
        this.phat = phat;
    }

    public int getTongluong() {
        return tongluong;
    }

    public void setTongluong(int tongluong) {
        this.tongluong = tongluong;
    }
    
    
}
