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
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dugdug
 */
public class view3 extends JFrame {
    JTextField txt_manv, txt_hoten, txt_diachi, txt_maphong, txt_mabaohiem, txt_cccd, txt_path, txt_sdt, txt_timkiem;
    JComboBox cbb_giotinh, cbb_phongban, cbb_chucvu;
    JDateChooser ngaysinh_dc;
    JButton btn_sua, btn_xoa, btn_timkiem, btn_xuatexc, btn_luu_btn, btn_huy;
    public JTable tb_thongtin;
    JPanel panel_north, panel_center, panel_south, panel_center1, panel_center2, panel_center3, panel_center31, panel_center32;
    

    public view3() {
        display();
    }

    public void view_Screen() {
        this.setTitle("Thông tin nhân viên");
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void display() {
        BorderLayout border = new BorderLayout();
        setLayout(border);
        
        Container con = getContentPane();
        JPanel pnmain = new JPanel();
        pnmain.setLayout(new BorderLayout());

        // Tạo FlowLayout cho từng nhóm thành phần
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();

    // Thiết lập cảnh dạng cho từng FlowLayout
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout3.setAlignment(FlowLayout.LEFT);
    flowLayout4.setAlignment(FlowLayout.LEFT);
    flowLayout5.setAlignment(FlowLayout.LEFT);
    flowLayout6.setAlignment(FlowLayout.LEFT);

    // Tạo JPanel cho mỗi nhóm thành phần
    JPanel panel_flow1 = new JPanel(flowLayout1);
    JPanel panel_flow2 = new JPanel(flowLayout2);
    JPanel panel_flow3 = new JPanel(flowLayout3);
    JPanel panel_flow4 = new JPanel(flowLayout4);
    JPanel panel_flow5 = new JPanel(flowLayout5);
    JPanel panel_flow6 = new JPanel(flowLayout6);

    // Thêm các thành phần vào từng JPanel tương ứng
    panel_flow1.add(new JLabel("Mã NV"));
    panel_flow1.add(txt_manv);
    panel_flow1.add(new JLabel("Họ tên"));
    panel_flow1.add(txt_hoten);

    panel_flow2.add(new JLabel("Giới tính"));
    panel_flow2.add(cbb_giotinh);
    panel_flow2.add(new JLabel("Ngày sinh"));
    panel_flow2.add(ngaysinh_dc);

    panel_flow3.add(new JLabel("Địa chỉ"));
    panel_flow3.add(txt_diachi);

    panel_flow4.add(new JLabel("Mã phòng"));
    panel_flow4.add(txt_maphong);
    panel_flow4.add(new JLabel("Mã bảo hiểm"));
    panel_flow4.add(txt_mabaohiem);

    panel_flow5.add(new JLabel("Chức vụ"));
    panel_flow5.add(cbb_chucvu);
    panel_flow5.add(new JLabel("CCCD"));
    panel_flow5.add(txt_cccd);

    panel_flow6.add(new JLabel("Số điện thoại"));
    panel_flow6.add(txt_sdt);
    panel_flow6.add(new JLabel("Path"));
    panel_flow6.add(txt_path);

    // Tạo GridLayout cho việc sắp xếp các FlowLayout
    GridLayout gridLayout = new GridLayout(6, 1);
    JPanel panel_center32 = new JPanel(gridLayout);

    // Thêm các JPanel vào GridLayout
    panel_center32.add(panel_flow1);
    panel_center32.add(panel_flow2);
    panel_center32.add(panel_flow3);
    panel_center32.add(panel_flow4);
    panel_center32.add(panel_flow5);
    panel_center32.add(panel_flow6);

    // Thêm panel_center32 vào panel_center
    panel_center.add(panel_center32, BorderLayout.CENTER);
        con.add(pnmain);
        
        view_Screen();
    }
    
    public static void main(String[] args) {
        view3 v3 = new view3();
    }
}