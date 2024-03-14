/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import view.view_ttnhanvien;

/**
 *
 * @author Dugdug
 */
public class controller_ttnhanvien {
    public view_ttnhanvien view_ttnv;
    private Connection connection;
    
    public controller_ttnhanvien() {
        view_ttnv = new view_ttnhanvien(this);
        connection = KetNoi.getConnection(); // Lấy kết nối từ lớp KetNoi
        view_ttnv.setVisible(true);
        loadDataToTable();
//        LoadDataBphanToCbb();
//        LoadDataCvuToCbb();
    }
    public void SuaButtonClick(){
        
    }
    
    public void XoaButtonClick(){
        
    }
    
    public void XuatexcButtonClick(){
        
    }
    
    public void LuuButtonClick(){
        
    }
    
    public void ThoatButtonClick(){
        view_ttnv.dispose();
    }
    
    public void filter() {
            String query = view_ttnv.get_txttimkiem().getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) view_ttnv.tb_thongtin.getModel());
            view_ttnv.tb_thongtin.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }    
    
    public void LoadDataGtinhToCbb(){
    }
    
    public void LoadDataBphanToCbb(){
        try {
            String query = "SELECT bophan FROM bophan";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                view_ttnv.get_cbbbophan().addItem(resultSet.getString("bophan"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void LoadDataCvuToCbb(){
        try {
            String query = "SELECT chucvu FROM chucvu";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                view_ttnv.get_cbbchucvu().addItem(resultSet.getString("chucvu"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
    
    public void ClickTable(){
       int selectedRow = view_ttnv.tb_thongtin.getSelectedRow();
       if (selectedRow != -1) { // Kiểm tra xem có hàng được chọn không
           // Lấy mã nhân viên từ hàng được chọn
           String selectedManv = (String) view_ttnv.tb_thongtin.getValueAt(selectedRow, 0);

           try {
               // Tạo truy vấn để lấy thông tin của nhân viên từ mã nhân viên được chọn
               String query = "SELECT * FROM thongtinnhanvien WHERE manv = ?";
               PreparedStatement preparedStatement = connection.prepareStatement(query);
               preparedStatement.setString(1, selectedManv);
               ResultSet resultSet = preparedStatement.executeQuery();

               // Nếu có kết quả trả về, điền thông tin vào các trường JTextField và JComboBox tương ứng
               if (resultSet.next()) {
                   view_ttnv.get_txtmanv().setText(resultSet.getString("manv"));
                   view_ttnv.get_txthoten().setText(resultSet.getString("hoten"));
                   view_ttnv.get_cbbgioitinh().setSelectedItem(resultSet.getString("gioitinh"));
                   view_ttnv.get_cbbbophan().addItem(resultSet.getString("bophan"));
                   view_ttnv.get_cbbchucvu().addItem(resultSet.getString("chucvu"));
                   view_ttnv.get_txtcccd().setText(resultSet.getString("cccd"));
                   view_ttnv.get_ngaysinh().setDate(resultSet.getDate("ngaysinh"));
                   view_ttnv.get_txtdiachi().setText(resultSet.getString("diachi"));
                   view_ttnv.get_txtsdt().setText(resultSet.getString("sdt"));
                   view_ttnv.get_txtmabaohiem().setText(resultSet.getString("mabaohiem"));
                   // Tương tự cho các trường còn lại

                   // Enable các nút Sửa và Xóa khi có hàng được chọn
                   view_ttnv.get_sua().setEnabled(true);
                   view_ttnv.get_xoa().setEnabled(true);

               }
           } catch (SQLException ex) {
               Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
    public void loadDataToTable() {
        try {
            String query = "SELECT manv, hoten, gioitinh, cccd, ngaysinh, diachi, sdt, mabaohiem, mahopdong, bophan, chucvu FROM thongtinnhanvien";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            DefaultTableModel model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                }      
            };            
            model.addColumn("Mã nhân viên");
            model.addColumn("Họ tên");
            model.addColumn("Giới tính");
            model.addColumn("Số CCCD");
            model.addColumn("Ngày sinh");
            model.addColumn("Địa chỉ");
            model.addColumn("SĐT");
            model.addColumn("Mã bảo hiểm");
            model.addColumn("Mã hợp đồng");
            model.addColumn("Bộ phận");
            model.addColumn("Chức vụ");

            // Đổ dữ liệu từ ResultSet vào model
            while (resultSet.next()) {
                Object[] row = new Object[11]; // Số cột trong kết quả truy vấn
                for (int i = 1; i <= 11; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            // Đặt model cho JTable
            view_ttnv.tb_thongtin.setModel(model);
            
        } catch (SQLException ex) {
            Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
