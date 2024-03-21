/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.controller_QLTaiKhoan;
import Controller.controller_DangNhap;
import View.view_TrangChu;

/**
 *
 * @author hieup
 */
public class controller_TrangChu {
    private view_TrangChu view;
    
    public controller_TrangChu() {
        view = new view_TrangChu(this);
        view.setVisible(true);
    }
    
    public void dangXuat() {
        controller_DangNhap a = new controller_DangNhap();
        view.dispose();
    }
    
    public void openQLTaiKhoan() {
        controller_QLTaiKhoan a = new controller_QLTaiKhoan();
        view.dispose();
    }
    
    public void openQLNghiPhep() {
        controller_QLNghiPhep a = new controller_QLNghiPhep();
        view.dispose();
    }
    public void openQLLuong() {
        controller_QLLuong a = new controller_QLLuong();
        view.dispose();
    }
}
