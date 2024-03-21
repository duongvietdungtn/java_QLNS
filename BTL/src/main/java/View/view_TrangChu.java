package View;

import Controller.controller_TrangChu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class view_TrangChu extends JFrame {
    private controller_TrangChu controller;
    public view_TrangChu(controller_TrangChu controller) {
        this.setSize(500, 600);
        this.setTitle("Trang chủ");
        
        this.controller = controller;

        // Tạo MenuBar
        JMenuBar menuBar = new JMenuBar();

        // Tạo menu Đăng xuất
        JMenu menuDangXuat = new JMenu("Đăng xuất");
        JMenuItem menuItemDangXuat = new JMenuItem("Đăng xuất");
        menuItemDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.dangXuat();
            }
        });
        menuDangXuat.add(menuItemDangXuat);

        // Tạo menu Quản lý tài khoản
        JMenu menuQuanLyTaiKhoan = new JMenu("Quản lý tài khoản");
        JMenu menuQuanLyNghiPhep = new JMenu("Quản lý nghỉ phép");
        JMenu menuQuanLyLuong = new JMenu("Quản lý lương");
        JMenuItem menuItemQuanLyTaiKhoan = new JMenuItem("Quản lý tài khoản");
        JMenuItem menuItemQuanLyNghiPhep = new JMenuItem("Quản lý nghỉ phép");
        JMenuItem menuItemQuanLyLuong = new JMenuItem("Quản lý lương");

        menuItemQuanLyTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openQLTaiKhoan();
            }
        });
        menuItemQuanLyNghiPhep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openQLNghiPhep();
            }
        });
        menuItemQuanLyLuong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openQLLuong();
            }
        });
        menuQuanLyTaiKhoan.add(menuItemQuanLyTaiKhoan);
        menuQuanLyNghiPhep.add(menuItemQuanLyNghiPhep);
        menuQuanLyLuong.add(menuItemQuanLyLuong);

        menuBar.add(menuDangXuat);
        menuBar.add(menuQuanLyTaiKhoan);
        menuBar.add(menuQuanLyNghiPhep);
        menuBar.add(menuQuanLyLuong);

        this.setJMenuBar(menuBar);
    }

}
