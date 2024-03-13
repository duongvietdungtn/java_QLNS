/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class view2 extends JFrame {
    JTextField txt_manv, txt_hoten, txt_diachi, txt_maphong, txt_mabaohiem, txt_cccd, txt_path, txt_sdt, txt_timkiem;
    JComboBox<String> cbb_giotinh, cbb_phongban, cbb_chucvu;
    JDateChooser ngaysinh_dc;
    JButton btn_sua, btn_xoa, btn_timkiem, btn_xuatexc, btn_luu_btn, btn_huy;
    public JTable tb_thongtin;
    JPanel panel_north, panel_center, panel_south, panel_center1, panel_center2, panel_center3;

    public view2() {
        display();
    }

    public void view_Screen() {
        this.setTitle("Test");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void display() {
        BorderLayout border = new BorderLayout();
        setLayout(border);

        // Panel chứa các thành phần trung tâm
        JPanel panel1 = new JPanel(new GridLayout(6, 2, 5, 5));

        // Khởi tạo các JTextField
        txt_manv = new JTextField();
        txt_hoten = new JTextField();
        txt_diachi = new JTextField();
        txt_maphong = new JTextField();
        txt_mabaohiem = new JTextField();
        txt_cccd = new JTextField();
        txt_path = new JTextField();
        txt_sdt = new JTextField();
        txt_timkiem = new JTextField();

        // Khởi tạo các JComboBox
        cbb_giotinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbb_phongban = new JComboBox<>(new String[]{"Phòng A", "Phòng B", "Phòng C"});
        cbb_chucvu = new JComboBox<>(new String[]{"Giám Đốc", "Nhân Viên", "Trưởng Phòng"});

        // Khởi tạo các JButton
        btn_sua = new JButton("Sửa");
        btn_xoa = new JButton("Xóa");
        btn_timkiem = new JButton("Tìm kiếm");
        btn_xuatexc = new JButton("Xuất Excel");
        btn_luu_btn = new JButton("Lưu");
        btn_huy = new JButton("Hủy");

        // Khởi tạo JDateChooser
        ngaysinh_dc = new JDateChooser();

        // Thêm các thành phần vào panel1
        panel1.add(new JLabel("Mã NV"));
        panel1.add(txt_manv);
        panel1.add(new JLabel("Họ tên"));
        panel1.add(txt_hoten);
        panel1.add(new JLabel("Giới tính"));
        panel1.add(cbb_giotinh);
        panel1.add(new JLabel("Ngày sinh"));
        panel1.add(ngaysinh_dc);
        panel1.add(new JLabel("Địa chỉ"));
        panel1.add(txt_diachi);
        panel1.add(new JLabel("Mã phòng"));
        panel1.add(txt_maphong);
        panel1.add(new JLabel("Mã bảo hiểm"));
        panel1.add(txt_mabaohiem);
        panel1.add(new JLabel("Chức vụ"));
        panel1.add(cbb_chucvu);
        panel1.add(new JLabel("CCCD"));
        panel1.add(txt_cccd);
        panel1.add(new JLabel("Số điện thoại"));
        panel1.add(txt_sdt);
        panel1.add(new JLabel("Path"));
        panel1.add(txt_path);

        // Panel chứa các nút chức năng
        JPanel panelSouth = new JPanel();
        panelSouth.add(btn_sua);
        panelSouth.add(btn_xoa);
        panelSouth.add(btn_timkiem);
        panelSouth.add(btn_xuatexc);
        panelSouth.add(btn_luu_btn);
        panelSouth.add(btn_huy);

        // Thêm panel1 vào trung tâm, panelSouth vào phía dưới
        add(panel1, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        view_Screen();
    }

    public static void main(String[] args) {
        new view2();
    }
}
