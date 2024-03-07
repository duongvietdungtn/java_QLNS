package view;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class test1 extends JFrame {
    JTextField manv_tf, hoten_tf, diachi_tf, mahopdong_tf, mabaohiem_tf, cccd_tf, path_tf, sdt_tf;
    JComboBox<String> giotinh_cbb, phongban_cbb;
    JDateChooser ngaysinh_dc;
    JButton sua_btn, xoa_btn, timkiem_btn, xuatexc_btn, luu_btn, huy_btn;
    JTabbedPane thongtin_tb;

    public test1() {
        // Khởi tạo các thành phần giao diện
        manv_tf = new JTextField();
        hoten_tf = new JTextField();
        diachi_tf = new JTextField();
        mahopdong_tf = new JTextField();
        mabaohiem_tf = new JTextField();
        cccd_tf = new JTextField();
        path_tf = new JTextField();
        sdt_tf = new JTextField();

        giotinh_cbb = new JComboBox<>(new String[]{"Nam", "Nữ"});
        phongban_cbb = new JComboBox<>(new String[]{"Phòng A", "Phòng B", "Phòng C"});

        ngaysinh_dc = new JDateChooser();

        sua_btn = new JButton("Sửa");
        xoa_btn = new JButton("Xóa");
        timkiem_btn = new JButton("Tìm Kiếm");
        xuatexc_btn = new JButton("Xuất Excel");
        luu_btn = new JButton("Lưu");
        huy_btn = new JButton("Hủy");

        // Tạo JPanel để chứa các thành phần
        JPanel panelThongTin = new JPanel();
        panelThongTin.setLayout(null);  // Sử dụng layout tuyến tính để tùy chỉnh vị trí của các thành phần

        // Thêm JLabel và JTextField
        addLabelAndTextField(panelThongTin, "Mã NV:", manv_tf, 20, 20);
        addLabelAndTextField(panelThongTin, "Họ Tên:", hoten_tf, 20, 60);
        addLabelAndTextField(panelThongTin, "Địa Chỉ:", diachi_tf, 20, 100);
        addLabelAndTextField(panelThongTin, "Mã Hợp Đồng:", mahopdong_tf, 20, 140);
        addLabelAndTextField(panelThongTin, "Mã Bảo Hiểm:", mabaohiem_tf, 20, 180);
        addLabelAndTextField(panelThongTin, "CCCD:", cccd_tf, 20, 220);
        addLabelAndTextField(panelThongTin, "Path:", path_tf, 20, 260);
        addLabelAndTextField(panelThongTin, "Số Điện Thoại:", sdt_tf, 20, 300);

        // Thêm JLabel và JComboBox
        addLabelAndComboBox(panelThongTin, "Giới Tính:", giotinh_cbb, 300, 20);
        addLabelAndComboBox(panelThongTin, "Phòng Ban:", phongban_cbb, 300, 60);

        // Thêm JLabel và JDateChooser
        addLabelAndDateChooser(panelThongTin, "Ngày Sinh:", ngaysinh_dc, 300, 100);

        // Thêm JButton
        addButton(panelThongTin, sua_btn, 20, 350);
        addButton(panelThongTin, xoa_btn, 120, 350);
        addButton(panelThongTin, timkiem_btn, 220, 350);
        addButton(panelThongTin, xuatexc_btn, 320, 350);
        addButton(panelThongTin, luu_btn, 420, 350);
        addButton(panelThongTin, huy_btn, 520, 350);

        // Tạo JTabbedPane để chứa thông tin nhân viên
        thongtin_tb = new JTabbedPane();
        thongtin_tb.addTab("Thông Tin Nhân Viên", panelThongTin);

        // Thêm JTabbedPane vào frame
        add(thongtin_tb);

        // Cấu hình frame
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }

    private void addLabelAndTextField(JPanel panel, String labelText, JTextField textField, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, 25);
        panel.add(label);

        textField.setBounds(x + 110, y, 150, 25);
        panel.add(textField);
    }

    private void addLabelAndComboBox(JPanel panel, String labelText, JComboBox<String> comboBox, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, 25);
        panel.add(label);

        comboBox.setBounds(x + 110, y, 150, 25);
        panel.add(comboBox);
    }

    private void addLabelAndDateChooser(JPanel panel, String labelText, JDateChooser dateChooser, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, 25);
        panel.add(label);

        dateChooser.setBounds(x + 110, y, 150, 25);
        panel.add(dateChooser);
    }

    private void addButton(JPanel panel, JButton button, int x, int y) {
        button.setBounds(x, y, 100, 25);
        panel.add(button);
    }

    public static void main(String[] args) {
        test1 r = new test1();
    }
}
