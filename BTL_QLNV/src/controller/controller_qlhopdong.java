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
    public void ThemButtonClick(){
        
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
        view_qlhd.dispose();
    }
    
    public void filter() {
            String query = view_qlhd.get_txttimkiem().getText().toLowerCase();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) view_qlhd.tb_hopdong.getModel());
            view_qlhd.tb_hopdong.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }  
    
    public void ClickTable(){
        view_qlhd.get_txtmahd().setEnabled(false);
        view_qlhd.get_txtmanv().setEnabled(false);
        view_qlhd.get_txthoten().setEnabled(false);
        view_qlhd.get_ngaybatdau().setEnabled(false);
        view_qlhd.get_ngayketthuc().setEnabled(false);
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
