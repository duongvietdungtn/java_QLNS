/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.TableRowSorter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import View.view_ttnhanvien;

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
        LoadDataBphanToCbb();
        LoadDataCvuToCbb();
    }
    public void SuaButtonClick(){  
        view_ttnv.get_luu().setEnabled(true);  
    }
    
//    public void XoaButtonClick(){
//        
//    }
    
    public void XuatexcButtonClick(){
    try {
        // Tạo workbook và sheet mới
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Thông tin nhân viên");

        // Tạo tiêu đề cho các cột
        XSSFRow headerRow = sheet.createRow(0);
        String[] columns = {"Mã NV", "Họ và tên", "Giới tính", "CCCD", "Ngày sinh", "Địa chỉ", "Số điện thoại", "Mã bảo hiểm", "Mã hợp đồng", "Bộ phận", "Chức vụ"};
        for (int i = 0; i < columns.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Lấy dữ liệu từ bảng hiện tại và điền vào sheet Excel
        DefaultTableModel model = (DefaultTableModel) view_ttnv.tb_thongtin.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < columns.length; j++) {
                Object value = model.getValueAt(i, j);
                row.createCell(j).setCellValue(value.toString());
            }
        }

        // Lưu workbook xuống file Excel
        String filePath = "thongtinnhanvien.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
        Desktop.getDesktop().open(new File(filePath)); // Mở file Excel sau khi đã xuất thành công
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Xuất Excel thất bại: " + e.getMessage());
    }
}
    
    public void LuuButtonClick(){            
        // Lấy nội dung của txt_mahd và txt_manv
        String manv = view_ttnv.get_txtmanv().getText();
        String hoten = view_ttnv.get_txthoten().getText();
        String mahd = view_ttnv.get_txtmahd().getText();
        String cccd = view_ttnv.get_txtcccd().getText();
        String diachi = view_ttnv.get_txtdiachi().getText();
        String mabaohiem = view_ttnv.get_txtmabaohiem().getText();
        String path = view_ttnv.get_txtpath().getText();  
        String sdt = view_ttnv.get_txtsdt().getText();  
        String gioitinh = (String) view_ttnv.get_cbbgioitinh().getSelectedItem();
        String chucvu = (String) view_ttnv.get_cbbchucvu().getSelectedItem();
        String bophan = (String) view_ttnv.get_cbbbophan().getSelectedItem();
        java.util.Date selectedDate1 = view_ttnv.get_ngaysinh().getDate();
        java.sql.Date sqlngaysinh = new java.sql.Date(selectedDate1.getTime());
        try{
            int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn cập nhật thông tin?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, cccd= ?, diachi= ?, mabaohiem= ?, path= ?, sdt= ?, gioitinh= ?, chucvu= ?, bophan= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query_ttnv);
                preparedStatement.setString(12, manv);
                preparedStatement.setString(1, hoten);
                preparedStatement.setString(2, cccd);
                preparedStatement.setString(3, diachi);
                preparedStatement.setString(4, mabaohiem);      
                preparedStatement.setString(5, path);
                preparedStatement.setString(6, sdt);
                preparedStatement.setString(7, gioitinh);
                preparedStatement.setString(8, chucvu);      
                preparedStatement.setString(9, bophan);
                preparedStatement.setDate(10, sqlngaysinh);  
                preparedStatement.setString(11, mahd);                    
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhân viên " + manv + "-" + hoten + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();

                view_ttnv.get_sua().setEnabled(false);      
                view_ttnv.get_luu().setEnabled(false);  
                view_ttnv.setLock();
                
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void UploadButtonClick(){
        JFileChooser fileimage = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        fileimage.addChoosableFileFilter(fnef);
        
        int showOpenDialog = fileimage.showOpenDialog(null);
        
        if(showOpenDialog == JFileChooser.APPROVE_OPTION){
            File selectedImageFile = fileimage.getSelectedFile();
            String selectedImagePath = selectedImageFile.getAbsolutePath();
//            JOptionPane.showMessageDialog(null, selectedImagePath);
            
            ImageIcon ii = new ImageIcon(selectedImagePath);
            Image image = ii.getImage().getScaledInstance(150, 190, 0);
            
            view_ttnv.get_txtpath().setText(selectedImagePath);
            view_ttnv.get_lbimage().setIcon(new ImageIcon(image));
        }
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
                // Lấy đường dẫn ảnh từ cơ sở dữ liệu
                String imagePath = resultSet.getString("path");ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(150, 190, Image.SCALE_SMOOTH);
                view_ttnv.get_lbimage().setIcon(new ImageIcon(image));
                    
                view_ttnv.get_txtmanv().setText(resultSet.getString("manv"));
                view_ttnv.get_txthoten().setText(resultSet.getString("hoten"));
                view_ttnv.get_cbbgioitinh().setSelectedItem(resultSet.getString("gioitinh"));
                view_ttnv.get_cbbbophan().setSelectedItem(resultSet.getString("bophan"));
                view_ttnv.get_cbbchucvu().setSelectedItem(resultSet.getString("chucvu"));
                view_ttnv.get_txtcccd().setText(resultSet.getString("cccd"));
                view_ttnv.get_ngaysinh().setDate(resultSet.getDate("ngaysinh"));
                view_ttnv.get_txtdiachi().setText(resultSet.getString("diachi"));
                view_ttnv.get_txtpath().setText(resultSet.getString("path"));
                view_ttnv.get_txtsdt().setText(resultSet.getString("sdt"));                   
                view_ttnv.get_txtmahd().setText(resultSet.getString("mahopdong"));
                view_ttnv.get_txtmabaohiem().setText(resultSet.getString("mabaohiem"));
                // Tương tự cho các trường còn lại

                // Enable các nút Sửa và Xóa khi có hàng được chọn
                view_ttnv.get_sua().setEnabled(true);
//              view_ttnv.get_xoa().setEnabled(true);

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
    public static void main(String[] args) {
        controller_ttnhanvien ttnv = new controller_ttnhanvien();
    }
}
