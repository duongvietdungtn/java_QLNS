/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Dugdug
 */
public class view_ttnhanvien extends JFrame {
    JTextField txt_manv, txt_hoten, txt_diachi, txt_maphong, txt_mabaohiem, txt_cccd, txt_path, txt_sdt, txt_timkiem;
    JComboBox cbb_giotinh, cbb_phongban;
    JDateChooser ngaysinh_dc;
    JButton btn_sua_btn, btn_xoa, btn_timkiem, btn_xuatexc, btn_luu_btn, btn_huy;
    JTable tb_thongtin;
    JPanel panel_north, panel_center, panel_south;

    public view_ttnhanvien() {
        display();
    }

    public void view_Screen() {
        this.setTitle("Thông tin nhân viên");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void display() {
        Container con = getContentPane();
        JPanel pnmain = new JPanel();
        pnmain.setLayout(new BorderLayout());

        // Panel North
        panel_north = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txt_timkiem = new JTextField(20);
        btn_timkiem = new JButton("Tìm kiếm");
        panel_north.add(txt_timkiem);
        panel_north.add(btn_timkiem);

        // Panel Center
        panel_center = new JPanel();
        panel_center.setLayout(new BorderLayout());

        // TabbedPane for central content
        tb_thongtin = new JTable();
        // Set preferred size for the table
//        tb_thongtin.setPreferredScrollableViewportSize(new Dimension(800, 400));
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout());

        // Add your components to tabPanel
        txt_manv = new JTextField(10);
        txt_hoten = new JTextField(10);
        // Add other components...
        cbb_giotinh = new JComboBox();
        cbb_phongban = new JComboBox();
        ngaysinh_dc = new JDateChooser();
        btn_sua_btn = new JButton("Sửa");
        btn_xoa = new JButton("Xóa");
        btn_timkiem = new JButton("Tìm kiếm");
        btn_xuatexc = new JButton("Xuất Excel");

        tabPanel.add(new JLabel("Mã NV:"));
        tabPanel.add(txt_manv);
        tabPanel.add(new JLabel("Họ Tên:"));
        tabPanel.add(txt_hoten);
        // Add other components...

        tabPanel.add(new JLabel("Giới tính:"));
        tabPanel.add(cbb_giotinh);
        tabPanel.add(new JLabel("Phòng ban:"));
        tabPanel.add(cbb_phongban);
        tabPanel.add(new JLabel("Ngày sinh:"));
        tabPanel.add(ngaysinh_dc);

        tabPanel.add(btn_sua_btn);
        tabPanel.add(btn_xoa);
        tabPanel.add(btn_timkiem);
        tabPanel.add(btn_xuatexc);

        tb_thongtin.add("Thông tin", tabPanel);

        // Add tb_thongtin to the center panel
        panel_center.add(tb_thongtin, BorderLayout.CENTER);

        // Panel South
        panel_south = new JPanel();
        btn_luu_btn = new JButton("Lưu");
        btn_huy = new JButton("Hủy");
        panel_south.add(btn_luu_btn);
        panel_south.add(btn_huy);

        pnmain.add(panel_north, BorderLayout.NORTH);
        pnmain.add(panel_center, BorderLayout.CENTER);
        pnmain.add(panel_south, BorderLayout.SOUTH);
        con.add(pnmain);
        
        view_Screen();
    }
    public static void main(String[] args) {
        view_ttnhanvien nhanvien = new view_ttnhanvien();
    }
}