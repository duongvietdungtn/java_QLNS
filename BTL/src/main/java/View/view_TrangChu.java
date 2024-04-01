package View;

import Controller.controller_TrangChu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class view_TrangChu extends JFrame {
    private controller_TrangChu controller;
    private JPanel contentPane;

    public view_TrangChu (controller_TrangChu controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Trang chủ");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo MenuBar và các menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuDangXuat = new JMenu("Đăng xuất");
        JMenuItem menuItemDangXuat = new JMenuItem("Đăng xuất");
        menuItemDangXuat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.dangXuat();
            }
        });
        menuDangXuat.add(menuItemDangXuat);

        JMenu menuQuanLyTaiKhoan = new JMenu("Quản lý tài khoản");
        JMenu menuQuanLyNhanVien = new JMenu("Quản lý nhân viên");
        JMenuItem menuItemQuanLyTaiKhoan = new JMenuItem("Quản lý tài khoản");
        JMenuItem menuItemQuanLyNghiPhep = new JMenuItem("Quản lý nghỉ phép");
        JMenuItem menuItemQuanLyLuong = new JMenuItem("Quản lý lương");
        JMenuItem menuItemQuanLyNhanVien = new JMenuItem("Quản lý nhân viên");
        JMenuItem menuItemQuanLyHopDong = new JMenuItem("Quản lý hợp đồng");
        JMenuItem menuItemQuanLyBaoHiem = new JMenuItem("Quản lý bảo hiểm");
        JMenuItem menuItemQuanLyBoPhan = new JMenuItem("Quản lý bộ phận");
        JMenuItem menuItemQuanLyChucVu = new JMenuItem("Quản lý chức vụ");

        menuItemQuanLyTaiKhoan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLTaiKhoan();
            }
        });
        menuItemQuanLyNghiPhep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLNghiPhep();
            }
        });
        menuItemQuanLyLuong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLLuong();
            }
        });
        menuItemQuanLyNhanVien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLNhanVien();
            }
        });
        menuItemQuanLyHopDong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLHopDong();
            }
        });
        menuItemQuanLyBaoHiem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLBaoHiem();
            }
        });
        menuItemQuanLyBoPhan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLBoPhan();
            }
        });
        menuItemQuanLyChucVu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openQLChucVu();
            }
        });

        menuQuanLyTaiKhoan.add(menuItemQuanLyTaiKhoan);
        menuQuanLyNhanVien.add(menuItemQuanLyNhanVien);
        menuQuanLyNhanVien.add(menuItemQuanLyHopDong);
        menuQuanLyNhanVien.add(menuItemQuanLyBaoHiem);
        menuQuanLyNhanVien.add(menuItemQuanLyBoPhan);
        menuQuanLyNhanVien.add(menuItemQuanLyChucVu);
        menuQuanLyNhanVien.add(menuItemQuanLyNghiPhep);
        menuQuanLyNhanVien.add(menuItemQuanLyLuong);

        menuBar.add(menuDangXuat);
        menuBar.add(menuQuanLyTaiKhoan);
        menuBar.add(menuQuanLyNhanVien);

        setJMenuBar(menuBar);

        // Tạo JPanel chứa hình ảnh
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\anhbia.png").getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        controller_TrangChu controller = new controller_TrangChu();
    }
}
