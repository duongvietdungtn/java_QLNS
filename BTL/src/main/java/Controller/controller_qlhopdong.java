/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

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
import View.view_qlhopdong;

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
        // Thực hiện chức năng khi nhấn nút "Delete"
        String mahd = view_qlhd.get_txtmahd().getText();
        String manv = view_qlhd.get_txtmanv().getText();

        try {
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa hợp đồng " + mahd + " ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {     
                String query_mahd = "DELETE FROM qlhopdong WHERE mahopdong=?";
                PreparedStatement preparedStatement_mahd = connection.prepareStatement(query_mahd);
                preparedStatement_mahd.setString(1, mahd);

                preparedStatement_mahd.executeUpdate();
                preparedStatement_mahd.close();

                String query_manv = "DELETE FROM thongtinnhanvien WHERE manv=?";
                PreparedStatement preparedStatement_manv = connection.prepareStatement(query_manv);
                preparedStatement_manv.setString(1, manv);

                preparedStatement_manv.executeUpdate();
                preparedStatement_manv.close();

                JOptionPane.showMessageDialog(null, "Xóa hợp đồng " + mahd + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                loadDataToTable();
                view_qlhd.setNull();
            }       
        } catch (SQLException ex) {
            Logger.getLogger(controller_qlhopdong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void XuatexcButtonClick(){
        
    }
    public void LuuButtonClick(){
        // Lấy nội dung của txt_mahd và txt_manv
        String mahd = view_qlhd.get_txtmahd().getText();
        String manv = view_qlhd.get_txtmanv().getText();
        String hoten = view_qlhd.get_txthoten().getText();
        java.util.Date selectedDate1 = view_qlhd.get_ngaybatdau().getDate();
        java.sql.Date sqlngaybatdau = new java.sql.Date(selectedDate1.getTime());
        java.util.Date selectedDate2 = view_qlhd.get_ngayketthuc().getDate();
        java.sql.Date sqlngayketthuc = new java.sql.Date(selectedDate2.getTime());
        try{
            if (!view_qlhd.get_sua().isEnabled()){

                // Kiểm tra nếu mahd hoặc manv rỗng thì hiển thị thông báo lỗi
                if (mahd.isEmpty() || manv.isEmpty() || hoten.isEmpty() || selectedDate1 == null || selectedDate2 == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }else if(sqlngaybatdau.compareTo(selectedDate2) >= 0){
                    JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại, ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }else {
                    TrungResult result_mahd = checktrung_mahd();
                    TrungResult result_manv = checktrung_manv();

                    if (result_mahd.getCount_mahd() > 0 && result_manv.getCount_manv() > 0) {
                        JOptionPane.showMessageDialog(null, "Mã hợp đồng và mã nhân viên đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else if (result_mahd.getCount_mahd() > 0) {
                        JOptionPane.showMessageDialog(null, "Mã hợp đồng đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else if (result_manv.getCount_manv() > 0) {
                        JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String query_qlhd = "INSERT INTO qlhopdong (mahopdong, manv, hoten, ngaybatdau, ngayketthuc) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(query_qlhd);
                        preparedStatement1.setString(1, mahd);
                        preparedStatement1.setString(2, manv);
                        preparedStatement1.setString(3, hoten);                        
                        preparedStatement1.setDate(4, sqlngaybatdau);
                        preparedStatement1.setDate(5, sqlngayketthuc);
                        preparedStatement1.executeUpdate();
                        
                        String query_ttnv = "INSERT INTO thongtinnhanvien ( manv, hoten, mahopdong) VALUES (?, ?, ?)";
                        PreparedStatement preparedStatement2 = connection.prepareStatement(query_ttnv);
                        preparedStatement2.setString(1, manv);
                        preparedStatement2.setString(2, hoten);
                        preparedStatement2.setString(3, mahd);        
                        preparedStatement2.executeUpdate();
                        
                        JOptionPane.showMessageDialog(null, "Thêm thành công mã hợp đồng: " + mahd, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadDataToTable();
                        // Tiếp tục xử lý khi không có trùng mã hợp đồng và mã nhân viên
                        view_qlhd.setLock();
                        view_qlhd.setNull();
                    }
                }
            } else if(view_qlhd.get_sua().isEnabled()){     

                view_qlhd.get_txtmahd().setEnabled(false);
                view_qlhd.get_txtmanv().setEnabled(false);

                // Kiểm tra nếu mahd hoặc manv rỗng thì hiển thị thông báo lỗi
                if (mahd.isEmpty() || manv.isEmpty() || hoten.isEmpty() || selectedDate1 == null || selectedDate2 == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }else if(sqlngaybatdau.compareTo(selectedDate2) > 0){
                    JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại, ngày kết thúc phải sau ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }else {
                    int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn cập nhật thông tin?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
//                        TrungResult result_mahd = checktrung_mahd();
//                        TrungResult result_manv = checktrung_manv();
//
//                        if (result_mahd.getCount_mahd() > 0 && result_manv.getCount_manv() > 0) {
//                            JOptionPane.showMessageDialog(null, "Mã hợp đồng và mã nhân viên đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        } else if (result_mahd.getCount_mahd() > 0) {
//                            JOptionPane.showMessageDialog(null, "Mã hợp đồng đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        } else if (result_manv.getCount_manv() > 0) {
//                            JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        } else {
                            String query_qlhd_update = "UPDATE qlhopdong SET manv = ?, hoten= ?, ngaybatdau = ?, ngayketthuc = ? WHERE mahopdong = ?";
                            PreparedStatement preparedStatement1 = connection.prepareStatement(query_qlhd_update);
                            preparedStatement1.setString(5, mahd);
                            preparedStatement1.setString(1, manv);
                            preparedStatement1.setString(2, hoten);                        
                            preparedStatement1.setDate(3, sqlngaybatdau);
                            preparedStatement1.setDate(4, sqlngayketthuc);
                            preparedStatement1.executeUpdate();

                            String query_ttnv_update = "UPDATE thongtinnhanvien SET hoten= ?,mahopdong = ? WHERE manv = ?";
                            PreparedStatement preparedStatement4 = connection.prepareStatement(query_ttnv_update);
                            preparedStatement4.setString(3, manv);
                            preparedStatement4.setString(1, hoten);
                            preparedStatement4.setString(2, mahd);        
                            preparedStatement4.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Cập nhật thông tin mã hợp đồng " + mahd + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            loadDataToTable();

                            view_qlhd.get_sua().setEnabled(false);             
                            view_qlhd.setLock();
//                        }
                    }
                } 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void ThemButtonClick(){  
        view_qlhd.get_luu().setEnabled(true);
    }
    
    public void SuaButtonClick(){                
        view_qlhd.get_luu().setEnabled(true);
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
       view_qlhd.get_luu().setEnabled(false);
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
    
    public class TrungResult {
        private int count_mahd;
        private int count_manv;

        public TrungResult(int count_mahd, int count_manv) {
            this.count_mahd = count_mahd;
            this.count_manv = count_manv;
        }

        public int getCount_mahd() {
            return count_mahd;
        }

        public int getCount_manv() {
            return count_manv;
        }
    }

    public TrungResult checktrung_mahd() throws SQLException {
        String mahd = view_qlhd.get_txtmahd().getText();
        String query = "SELECT COUNT(*) FROM qlhopdong WHERE mahopdong = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mahd);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        int count_mahd = resultSet.getInt(1);
        return new TrungResult(count_mahd, 0);
    }

    public TrungResult checktrung_manv() throws SQLException {
        String manv = view_qlhd.get_txtmanv().getText();
        String query = "SELECT COUNT(*) FROM qlhopdong WHERE manv = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, manv);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        int count_manv = resultSet.getInt(1);
        return new TrungResult(0, count_manv);
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
