/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import com.toedter.calendar.JDateChooser;
import Controller.controller_qlhopdong;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class view_qlhopdong extends JFrame {
    JTextField txt_mahd, txt_manv, txt_hoten, txt_timkiem;
    JDateChooser ngaybatdau_dc, ngayketthuc_dc;
    JButton btn_them, btn_sua, btn_xoa, btn_xuatexc, btn_luu, btn_thoat;
    public JTable tb_hopdong;
    JPanel panel_north, panel_center, panel_south, panel_center1, panel_center2, panel_center3, panel_center31, panel_center32;
    private controller_qlhopdong controller_qlhd;

    public view_qlhopdong(controller_qlhopdong controller_qlhd) {
        this.controller_qlhd = controller_qlhd;
        display();
    }

    public void view_Screen() {
        setTitle("Quản lý hợp đồng");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void display() {
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
        tb_hopdong = new JTable();
        JScrollPane scrollPane = new JScrollPane(tb_hopdong);
        tb_hopdong.getTableHeader().setReorderingAllowed(false);
        GridBagConstraints gbcTable = new GridBagConstraints();
        gbcTable.fill = GridBagConstraints.BOTH;
        gbcTable.weightx = 1.0;
        gbcTable.weighty = 1.0;
        panel_center2.add(scrollPane, gbcTable); // Sử dụng JScrollPane để có thể cuộn khi có nhiều dòng
        
        ImageIcon icon_them =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\add.png");
        ImageIcon icon_xoa =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\delete.png");
        ImageIcon icon_luu =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\save.png");
        ImageIcon icon_thoat =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\exit.png");
        ImageIcon icon_sua =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\edit.png");
        ImageIcon icon_excel =  new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\excel.png");
        
        // Panel South
        panel_south = new JPanel();   
        btn_luu = new JButton("Lưu", icon_luu);
        btn_thoat = new JButton("Thoát", icon_thoat);
        panel_south.add(btn_luu);
        panel_south.add(btn_thoat);        

        // Khởi tạo các JButton
        btn_them = new JButton(" Thêm", icon_them);
        btn_sua = new JButton("Sửa", icon_sua);
        btn_xoa = new JButton("Xóa", icon_xoa);
        btn_xuatexc = new JButton("Xuất Excel", icon_excel);
        
        
        // Thêm các thành phần của bạn vào panel_center1
        panel_center1.add(btn_them);
        panel_center1.add(btn_sua);
        panel_center1.add(btn_xoa);
        panel_center1.add(btn_xuatexc);
        
        // Chỉnh layout của panel_center1 để căn chỉnh về phía trái
        panel_center1.setLayout(new FlowLayout(FlowLayout.LEFT));        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 8, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_center3.add(new JLabel("Mã hợp đồng"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel_center3.add(txt_mahd = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel_center3.add(new JLabel("Mã nhân viên"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel_center3.add(txt_manv = new JTextField(20), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel_center3.add(new JLabel("Họ tên"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel_center3.add(txt_hoten = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel_center3.add(new JLabel("Ngày bắt đầu"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel_center3.add(ngaybatdau_dc = new JDateChooser(), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel_center3.add(new JLabel("Ngày kết thúc"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel_center3.add(ngayketthuc_dc = new JDateChooser(), gbc);
        
        btn_luu.setEnabled(false);
        btn_sua.setEnabled(false);
        btn_xoa.setEnabled(false);        
        setLock();
        
        // Thêm panel_center1 và panel_center2 vào panel_center
        panel_center.add(panel_center1, BorderLayout.NORTH);
        panel_center.add(panel_center2, BorderLayout.CENTER);        
        panel_center.add(panel_center3, BorderLayout.SOUTH);

        // Thêm panel1 vào trung tâm, panelSouth vào phía dưới
        pnmain.add(panel_north, BorderLayout.NORTH);
        pnmain.add(panel_center, BorderLayout.CENTER);
        pnmain.add(panel_south, BorderLayout.SOUTH);
        add(pnmain);
        
        btn_them.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.ThemButtonClick();
                btn_sua.setEnabled(false);
                btn_xoa.setEnabled(false);
                setOpen();
                setNull();
            }
        });
        
        btn_sua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.SuaButtonClick();
                txt_hoten.setEnabled(true);
                ngaybatdau_dc.setEnabled(true);
                ngayketthuc_dc.setEnabled(true);
            }
        });
        
        btn_xoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.XoaButtonClick();
            }
        });
        
        btn_xuatexc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.XuatexcButtonClick();
            }
        });
        btn_luu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                controller_qlhd.LuuButtonClick();
            }
        });
        btn_thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.ThoatButtonClick();
            }
        });
        
        tb_hopdong.addMouseListener(new MouseAdapter(){
           public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller_qlhd.ClickTable();
//                btn_sua.setEnabled(true);
//                btn_xoa.setEnabled(true);
            }
        });
        
        txt_timkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller_qlhd.timkiem();
            }
        });
        view_Screen();
    }
    public void setOpen(){
        txt_mahd.setEnabled(true);
        txt_manv.setEnabled(true);
        txt_hoten.setEnabled(true);
        ngaybatdau_dc.setEnabled(true);
        ngayketthuc_dc.setEnabled(true);
    }
    public void setLock(){
        txt_mahd.setEnabled(false);
        txt_manv.setEnabled(false);
        txt_hoten.setEnabled(false);
        ngaybatdau_dc.setEnabled(false);
        ngayketthuc_dc.setEnabled(false);
    }
    
    public void setNull(){        
        txt_mahd.setText(null);
        txt_manv.setText(null);
        txt_hoten.setText(null);
        ngaybatdau_dc.setDate(null);
        ngayketthuc_dc.setDate(null);
    }
    public JTextField get_txtmahd(){
        return txt_mahd;
    }
    public JTextField get_txtmanv(){
        return txt_manv;
    }
    public JTextField get_txthoten(){
        return txt_hoten;
    }
    public JTextField get_txttimkiem(){
        return txt_timkiem;
    }    
    public JDateChooser get_ngaybatdau() {
        return ngaybatdau_dc;
    }
    
    public JTable getTable() {
        return tb_hopdong;
    }
    
    public JDateChooser get_ngayketthuc() {
        return ngayketthuc_dc;
    }
    public JButton get_sua(){
        return btn_sua;
    }
    public JButton get_xoa(){
        return btn_xoa;
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