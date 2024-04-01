/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import com.toedter.calendar.JDateChooser;
import Controller.controller_ttnhanvien;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class view_ttnhanvien extends JFrame {
    JTextField txt_manv, txt_hoten, txt_diachi, txt_mabaohiem, txt_cccd, txt_mahd, txt_path, txt_sdt, txt_timkiem;
    JComboBox cbb_giotinh, cbb_bophan, cbb_chucvu;
    JDateChooser ngaysinh_dc;
    JButton btn_sua, btn_xoa, btn_xuatexc, btn_luu, btn_thoat, btn_upload;
    JLabel jLabelImage;
    public JTable tb_thongtin;
    JPanel panel_north, panel_center, panel_south, panel_center1, panel_center2, panel_center3, panel_center31, panel_center32;
    private controller_ttnhanvien controller_ttnv;

    public view_ttnhanvien(controller_ttnhanvien controller_ttnv) {
        this.controller_ttnv = controller_ttnv;
        display();
    }

    public void view_Screen() {
        setTitle("Thông tin nhân viên");
        setSize(1250, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void display() {
        Container con = getContentPane();
        JPanel pnmain = new JPanel();
        pnmain.setLayout(new BorderLayout());

        // Panel North
        panel_north = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txt_timkiem = new JTextField(20);
        panel_north.add(new JLabel("Tìm kiếm: "));
        panel_north.add(txt_timkiem);

        panel_center = new JPanel();
        panel_center.setLayout(new BorderLayout());

        // Tạo panel_center1 và thêm các thành phần vào nó
        panel_center1 = new JPanel();
        panel_center1.setLayout(new FlowLayout());

        // Tạo panel_center2 và thêm các thành phần vào nó
        panel_center2 = new JPanel();
        panel_center2.setLayout(new GridBagLayout());
        
        panel_center3 = new JPanel();
        panel_center3 = new JPanel(new GridBagLayout());    
        
        // Thêm panel_center1 và panel_center2 vào panel_center
        panel_center.add(panel_center1, BorderLayout.NORTH);
        panel_center.add(panel_center2, BorderLayout.CENTER);        
        panel_center.add(panel_center3, BorderLayout.SOUTH);

        // TabbedPane for central content
        tb_thongtin = new JTable();
        JScrollPane scrollPane = new JScrollPane(tb_thongtin);
        tb_thongtin.getTableHeader().setReorderingAllowed(false);
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.fill = GridBagConstraints.BOTH;
        gbcTable.weightx = 1.0;
        gbcTable.weighty = 1.0;
        panel_center2.add(scrollPane, gbcTable); // Sử dụng JScrollPane để có thể cuộn khi có nhiều dòng
        
        ImageIcon icon_luu =  new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\save.png");
        ImageIcon icon_thoat =  new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\exit.png");
        ImageIcon icon_sua =  new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\edit.png");
        ImageIcon icon_excel =  new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\excel.png");
        ImageIcon icon_browse =  new ImageIcon("C:\\Users\\hieup\\Downloads\\BTL\\src\\main\\java\\Image\\browse.png");
        
        // Panel South
        panel_south = new JPanel();
        btn_luu = new JButton("Lưu", icon_luu);
        btn_thoat = new JButton("Thoát", icon_thoat);
        panel_south.add(btn_luu);
        panel_south.add(btn_thoat);
        btn_upload = new JButton("Chọn ảnh", icon_browse);

        // Khởi tạo các JButton
        btn_sua = new JButton("Sửa", icon_sua);
//        btn_xoa = new JButton("Xóa");
        btn_xuatexc = new JButton("Xuất Excel", icon_excel);
        
        
        // Thêm các thành phần của bạn vào panel_center1
        panel_center1.add(btn_sua);
//        panel_center1.add(btn_xoa);
        panel_center1.add(btn_xuatexc);
        
        // Chỉnh layout của panel_center1 để căn chỉnh về phía trái
        panel_center1.setLayout(new FlowLayout(FlowLayout.LEFT));        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 8, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_center3.add(new JLabel("Mã NV"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_center3.add(txt_manv = new JTextField(20), gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel_center3.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel_center3.add(txt_hoten = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel_center3.add(new JLabel("Giới tính"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel_center3.add(cbb_giotinh = new JComboBox<>(new String[]{"Nam", "Nữ"}), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel_center3.add(new JLabel("Ngày sinh"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel_center3.add(ngaysinh_dc = new JDateChooser(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_center3.add(new JLabel("Địa chỉ"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_center3.add(txt_diachi = new JTextField(20), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_center3.add(new JLabel("Bộ phận"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_center3.add(cbb_bophan = new JComboBox<>(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel_center3.add(new JLabel("Mã bảo hiểm"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel_center3.add(txt_mabaohiem = new JTextField(20), gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel_center3.add(new JLabel("Chức vụ"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        panel_center3.add(cbb_chucvu = new JComboBox<>(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel_center3.add(new JLabel("CCCD"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel_center3.add(txt_cccd = new JTextField(20), gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel_center3.add(new JLabel("Số điện thoại"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 4;
        panel_center3.add(txt_sdt = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel_center3.add(new JLabel("Mã hợp đồng"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel_center3.add(txt_mahd = new JTextField(20), gbc);  
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel_center3.add(new JLabel("Path"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        panel_center3.add(txt_path = new JTextField(20), gbc);  
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridheight = 5;        
        gbc.insets = new Insets (10,50,2,10);
        panel_center3.add(jLabelImage = new JLabel(), gbc);
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.insets = new Insets (2,50,8,10);
        panel_center3.add(btn_upload, gbc);
        
        btn_sua.setEnabled(false);
//        btn_xoa.setEnabled(false);
        btn_luu.setEnabled(false);
        setLock();
        
        // Thêm panel1 vào trung tâm, panelSouth vào phía dưới
        pnmain.add(panel_north, BorderLayout.NORTH);
        pnmain.add(panel_center, BorderLayout.CENTER);
        pnmain.add(panel_south, BorderLayout.SOUTH);
        con.add(pnmain);
        
        btn_sua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.SuaButtonClick();
                txt_diachi.setEnabled(true);
                txt_cccd.setEnabled(true);
                txt_sdt.setEnabled(true);
                cbb_giotinh.setEnabled(true);
                cbb_bophan.setEnabled(true);
                cbb_chucvu.setEnabled(true);
                ngaysinh_dc.setEnabled(true);     
                btn_upload.setEnabled(true);           
            }
        });
        
//        btn_xoa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                controller_ttnv.XoaButtonClick();
//            }
//        });
        
        btn_xuatexc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.XuatexcButtonClick();
            }
        });
        
        btn_luu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.LuuButtonClick();
            }
        });
        
        btn_upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.UploadButtonClick();
            }
        });
        
        btn_thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.ThoatButtonClick();
            }
        });
        
        tb_thongtin.addMouseListener(new MouseAdapter(){
           public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller_ttnv.ClickTable();
//                btn_sua.setEnabled(true);
//                btn_xoa.setEnabled(true);
            }
        });
        
        txt_timkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_ttnv.timkiem();
            }
        });
//        btn_thoat.setIcon(new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\back.png"));
//        btn_sua.setIcon(new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\edit.png"));
//        btn_xuatexc.setIcon(new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\excel.png"));
        view_Screen();
    }
    public void setLock(){
        txt_manv.setEnabled(false);
        txt_hoten.setEnabled(false);
        txt_diachi.setEnabled(false);
        txt_mabaohiem.setEnabled(false);
        txt_cccd.setEnabled(false);
        txt_sdt.setEnabled(false);
        txt_mahd.setEnabled(false);
        txt_path.setEnabled(false);
        cbb_giotinh.setEnabled(false);
        cbb_bophan.setEnabled(false);
        cbb_chucvu.setEnabled(false);
        ngaysinh_dc.setEnabled(false);
        btn_upload.setEnabled(false);
    }

    public JTextField get_txtmanv(){
        return txt_manv;
    }
    public JTextField get_txthoten(){
        return txt_hoten;
    }
    public JTextField get_txtdiachi(){
        return txt_diachi;
    }
    public JTextField get_txtcccd(){
        return txt_cccd;
    }
    public JTextField get_txtsdt(){
        return txt_sdt;     
    }
    public JTextField get_txtpath(){
        return txt_path;
    }
    public JTextField get_txtmabaohiem(){
        return txt_mabaohiem;
    }
    public JTextField get_txtmahd(){
        return txt_mahd;
    }
    public JLabel get_lbimage(){
        return jLabelImage;
    }
    public JButton get_btnupload(){
        return btn_upload;
    }
    
    public JTextField get_txttimkiem(){
        return txt_timkiem;
    }
    public JComboBox<String> get_cbbbophan(){
        return cbb_bophan;
    }
    public JComboBox<String> get_cbbchucvu(){
        return cbb_chucvu;
    }
    public JComboBox<String> get_cbbgioitinh(){
        return cbb_giotinh;
    }
    
    public JTable getTable() {
        return tb_thongtin;
    }
    
    public JDateChooser get_ngaysinh() {
        return ngaysinh_dc;
    }
    public JButton get_sua(){
        return btn_sua;
    }
    public JButton get_luu(){
        return btn_luu;
    }
    public JButton get_thoat(){
        return btn_thoat;
    }
    public JButton get_xuatexc(){
        return btn_xuatexc;
    }
}