/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.view_qlhopdong;

/**
 *
 * @author Dugdug
 */
public class controller_qlhopdong {
    public view_qlhopdong view_qlhd;
    private Connection connection;
    
    public controller_qlhopdong() {
        view_qlhd = new view_qlhopdong(this);
        connection = KetNoi.getConnection(); // Lấy kết nối từ lớp KetNoi
        view_qlhd.setVisible(true);
        loadDataToTable();
    }    
    public void XoaButtonClick(){
        
    }
    
    public void XuatexcButtonClick(){
        
    }
    
    public void ThemmoiButtonClick(){
        // Lấy nội dung của txt_mahd và txt_manv
        String mahd = view_qlhd.get_txtmahd().getText();
        String manv = view_qlhd.get_txtmanv().getText();
        String hoten = view_qlhd.get_txthoten().getText();
        Date ngaybatdau = view_qlhd.get_ngaybatdau().getDate();
        Date ngayketthuc = view_qlhd.get_ngayketthuc().getDate();

        // Kiểm tra nếu mahd hoặc manv rỗng thì hiển thị thông báo lỗi
        if (mahd.isEmpty() || manv.isEmpty() || hoten.isEmpty() || ngaybatdau == null || ngayketthuc == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }else if(ngaybatdau.compareTo(ngayketthuc) >= 0){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại, ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }else {          
            view_qlhd.get_themmoi().setEnabled(false);
            view_qlhd.setLock();
            view_qlhd.setNull();
        }
    }
    
    public void CapnhatButtonClick(){
         // Lấy nội dung của txt_mahd và txt_manv
        String mahd = view_qlhd.get_txtmahd().getText();
        String manv = view_qlhd.get_txtmanv().getText();
        String hoten = view_qlhd.get_txthoten().getText();
        Date ngaybatdau = view_qlhd.get_ngaybatdau().getDate();
        Date ngayketthuc = view_qlhd.get_ngayketthuc().getDate();

        // Kiểm tra nếu mahd hoặc manv rỗng thì hiển thị thông báo lỗi
        if (mahd.isEmpty() || manv.isEmpty() || hoten.isEmpty() || ngaybatdau == null || ngayketthuc == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }else if(ngaybatdau.compareTo(ngayketthuc) > 0){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại, ngày kết thúc phải sau ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }else {
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn cập nhật thông tin?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                view_qlhd.get_capnhat().setEnabled(false);
                view_qlhd.get_sua().setEnabled(false);             
                view_qlhd.setLock();
            }
        }
    }
    
    public void ThoatButtonClick(){
        view_qlhd.dispose();
    }
    
    public void filter() {
            String query = view_qlhd.get_txttimkiem().getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) view_qlhd.tb_hopdong.getModel());
            view_qlhd.tb_hopdong.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }  
    
    public void ClickTable(){
       view_qlhd.setLock();
       int selectedRow = view_qlhd.tb_hopdong.getSelectedRow();
       if (selectedRow != -1) { // Kiểm tra xem có hàng được chọn không
           // Lấy mã nhân viên từ hàng được chọn
           String selectedManv = (String) view_qlhd.tb_hopdong.getValueAt(selectedRow, 0);

           try {
               // Tạo truy vấn để lấy thông tin của nhân viên từ mã nhân viên được chọn
               String query = "SELECT * FROM qlhopdong WHERE mahopdong = ?";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               preparedStatement.setString(1, selectedManv);
               ResultSet resultSet = preparedStatement.executeQuery();

               // Nếu có kết quả trả về, điền thông tin vào các trường JTextField và JComboBox tương ứng
               if (resultSet.next()) {
                   view_qlhd.get_txtmahd().setText(resultSet.getString("mahopdong"));
                   view_qlhd.get_txtmanv().setText(resultSet.getString("manv"));
                   view_qlhd.get_txthoten().setText(resultSet.getString("hoten"));
                   view_qlhd.get_ngaybatdau().setDate(resultSet.getDate("ngaybatdau"));
                   view_qlhd.get_ngayketthuc().setDate(resultSet.getDate("ngayketthuc"));

                   // Enable các nút Sửa và Xóa khi có hàng được chọn
                   view_qlhd.get_sua().setEnabled(true);
                   view_qlhd.get_xoa().setEnabled(true);

               }
           } catch (SQLException ex) {
               Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
    public void loadDataToTable() {
        try {
            String query = "SELECT * FROM qlhopdong";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            DefaultTableModel model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }      
            };  
            model.addColumn("Mã hợp đồng");          
            model.addColumn("Mã nhân viên");
            model.addColumn("Họ tên");
            model.addColumn("Ngày bắt đầu");
            model.addColumn("Ngày kết thúc");

            // Đổ dữ liệu từ ResultSet vào model
            while (resultSet.next()) {
                Object[] row = new Object[5]; // Số cột trong kết quả truy vấn
                for (int i = 1; i <= 5; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            // Đặt model cho JTable
            view_qlhd.tb_hopdong.setModel(model);
            
        } catch (SQLException ex) {
            Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        controller_qlhopdong hd = new controller_qlhopdong();
    }
}
