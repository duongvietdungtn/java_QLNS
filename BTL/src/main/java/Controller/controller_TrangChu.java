/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Controller.controller_QLTaiKhoan;
import Controller.controller_DangNhap;
import View.BHXHView;
import View.BPView;
import View.ChucVuView;
import View.view_TrangChu;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

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
    public void openQLNhanVien() {
        controller_ttnhanvien a = new controller_ttnhanvien();
        view.dispose();
    }
    public void openQLHopDong() {
        controller_qlhopdong a = new controller_qlhopdong();
        view.dispose();
    }
    public void openQLBaoHiem() {
        BHXHControl control1=new BHXHControl();
        BHXHView view1 = new BHXHView(control1);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        view1.setSize(1200, 800);
        view1.setLocationRelativeTo(null);
        view1.setVisible(true);
        view.dispose();
    }
    public void openQLBoPhan() {
        BPControl control1=new BPControl();
        BPView view1 = new BPView(control1);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        view1.setSize(800, 600);
        view1.setLocationRelativeTo(null);
        view1.setVisible(true);
        view.dispose();
    }
    public void openQLChucVu() {
        ChucVuControl control1=new ChucVuControl();
        ChucVuView view1 = new ChucVuView(control1);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        view1.setSize(800, 600);
        view1.setLocationRelativeTo(null);
        view1.setVisible(true);   
        view.dispose();
    }
}
