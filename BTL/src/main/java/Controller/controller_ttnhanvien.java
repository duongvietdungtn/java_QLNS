/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.TaiKhoan;
import Model.model_ttnhanvien;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import javax.swing.table.TableRowSorter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import View.view_ttnhanvien;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JTextField;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

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
        chinhapso_sdt();
        chinhapso_cccd();
        chinhapchu_diachi();
        loadDataToTable();
        LoadDataBphanToCbb();
        LoadDataCvuToCbb();
        Anhmacdinh();
    }
    
    public void LuuButtonClick() {            
        // Lấy nội dung của các trường
        String manv = view_ttnv.get_txtmanv().getText();
        String hoten = view_ttnv.get_txthoten().getText();
        String mahd = view_ttnv.get_txtmahd().getText();
        String cccd = view_ttnv.get_txtcccd().getText();
        String diachi = view_ttnv.get_txtdiachi().getText().toUpperCase();
        String mabaohiem = view_ttnv.get_txtmabaohiem().getText();
        String path = view_ttnv.get_txtpath().getText();  
        String sdt = view_ttnv.get_txtsdt().getText();  
        String gioitinh = (String) view_ttnv.get_cbbgioitinh().getSelectedItem();
        String chucvu = (String) view_ttnv.get_cbbchucvu().getSelectedItem();
        String bophan = (String) view_ttnv.get_cbbbophan().getSelectedItem();
        java.util.Date selectedDate1 = view_ttnv.get_ngaysinh().getDate();
        if (selectedDate1 == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Return early to avoid NullPointerException
        }
        java.sql.Date sqlngaysinh = new java.sql.Date(selectedDate1.getTime());
      
        model_ttnhanvien mdtt = new model_ttnhanvien();
        mdtt.setManv(manv);
        mdtt.setHoten(hoten);
        mdtt.setMahd(mahd);
        mdtt.setCccd(cccd);
        mdtt.setSdt(sdt);
        mdtt.setDiachi(diachi);
        mdtt.setGioitinh(gioitinh);
        mdtt.setChucvu(chucvu);
        mdtt.setBophan(bophan);
        mdtt.setMabaohiem(mabaohiem);
        mdtt.setPath(path);
        mdtt.setNgaysinh(sqlngaysinh);

        try {
            if (mdtt.getManv().isEmpty() || mdtt.getHoten().isEmpty() || mdtt.getMahd().isEmpty() || mdtt.getCccd().isEmpty() || mdtt.getDiachi().isEmpty() || mdtt.getPath().isEmpty() || mdtt.getSdt().isEmpty() || mdtt.getGioitinh() == null || mdtt.getChucvu() == null || mdtt.getBophan() == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại và điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                int option = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn cập nhật thông tin?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (mdtt.getSdt().length() != 10 || !mdtt.getSdt().startsWith("0")) {
                    JOptionPane.showMessageDialog(null, "Số điện thoại gồm 10 chữ số và bắt đầu bằng số 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else if (mdtt.getCccd().length() != 12) {
                    JOptionPane.showMessageDialog(null, "Số CCCD gồm 12 chữ số", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (option == JOptionPane.YES_OPTION) {
                        String query = "SELECT cccd, sdt, bophan, chucvu FROM thongtinnhanvien WHERE manv = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, mdtt.getManv());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String cccdDB = resultSet.getString("cccd");
                            String sdtDB = resultSet.getString("sdt"); 
                            String bophanDB = resultSet.getString("bophan");
                            String chucvuDB = resultSet.getString("chucvu");                    

                            // So sánh giá trị được chọn với dữ liệu từ cơ sở dữ liệu
                            if (mdtt.getCccd().equals(cccdDB) && mdtt.getSdt().equals(sdtDB) && mdtt.getBophan().equals(bophanDB) && mdtt.getChucvu().equals(chucvuDB)) {
                                // Thực hiện cập nhật thông tin nhân viên
                                String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                preparedStatement1.setString(7, mdtt.getManv());
                                preparedStatement1.setString(1, mdtt.getHoten());
                                preparedStatement1.setString(2, mdtt.getDiachi());
                                preparedStatement1.setString(3, mdtt.getPath()); 
                                preparedStatement1.setString(4, mdtt.getGioitinh());  
                                preparedStatement1.setDate(5, (java.sql.Date) mdtt.getNgaysinh());
                                preparedStatement1.setString(6, mdtt.getMahd());                     
                                preparedStatement1.executeUpdate();
                            }else if (mdtt.getCccd().equals(cccdDB) && mdtt.getBophan().equals(bophanDB) && mdtt.getChucvu().equals(chucvuDB)) {                                
                                if (kiemTraTrungSDT(sdt)) {
                                    JOptionPane.showMessageDialog(null, "Số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, sdt= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(8, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getSdt());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath()); 
                                    preparedStatement1.setString(5, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(6, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(7, mdtt.getMahd());                     
                                    preparedStatement1.executeUpdate();
                                }    
                            }else if (mdtt.getSdt().equals(sdtDB) && mdtt.getBophan().equals(bophanDB) && mdtt.getChucvu().equals(chucvuDB)) {                                
                                if (kiemTraTrungCCCD(cccd)) {
                                    JOptionPane.showMessageDialog(null, "CCCD đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, cccd= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(8, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getCccd());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath()); 
                                    preparedStatement1.setString(5, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(6, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(7, mdtt.getMahd());                     
                                    preparedStatement1.executeUpdate();
                                }
                            }else if (mdtt.getBophan().equals(bophanDB) && mdtt.getChucvu().equals(chucvuDB)) {
                                if (kiemTraTrungSDT(sdt)) {
                                    JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (kiemTraTrungCCCD(cccd)) {
                                    JOptionPane.showMessageDialog(null, "CCCD đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, cccd= ?, diachi= ?, path= ?, sdt= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(9, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getCccd());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath());      
                                    preparedStatement1.setString(5, mdtt.getSdt());
                                    preparedStatement1.setString(6, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(7, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(8, mdtt.getMahd());                     
                                    preparedStatement1.executeUpdate();
                                }
                            }else if (mdtt.getCccd().equals(cccdDB) && mdtt.getSdt().equals(sdtDB)) {                                
                                if (Tontaichucvu_Truongphong(bophan) && mdtt.getChucvu().equals("Trưởng phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Trưởng phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Phophong(bophan) && mdtt.getChucvu().equals("Phó phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Phó phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ?, chucvu= ?, bophan= ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(9, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getDiachi());
                                    preparedStatement1.setString(3, mdtt.getPath()); 
                                    preparedStatement1.setString(4, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(5, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(6, mdtt.getMahd());                                         
                                    preparedStatement1.setString(7, mdtt.getChucvu());        
                                    preparedStatement1.setString(8, mdtt.getBophan());   
                                    preparedStatement1.executeUpdate();
                                }
                            }else if (mdtt.getCccd().equals(cccdDB)) {                                
                                if (kiemTraTrungSDT(sdt)) {
                                    JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Truongphong(bophan) && mdtt.getChucvu().equals("Trưởng phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Trưởng phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Phophong(bophan) && mdtt.getChucvu().equals("Phó phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Phó phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, sdt= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ?, chucvu= ?, bophan= ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(10, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getSdt());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath()); 
                                    preparedStatement1.setString(5, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(6, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(7, mdtt.getMahd());       
                                    preparedStatement1.setString(8, mdtt.getChucvu());        
                                    preparedStatement1.setString(9, mdtt.getBophan());                      
                                    preparedStatement1.executeUpdate();
                                }
                            }else if (mdtt.getSdt().equals(sdtDB)){                                
                                if (kiemTraTrungCCCD(cccd)) {
                                    JOptionPane.showMessageDialog(null, "CCCD đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Truongphong(bophan) && mdtt.getChucvu().equals("Trưởng phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Trưởng phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Phophong(bophan) && chucvu.equals("Phó phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Phó phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv = "UPDATE thongtinnhanvien SET hoten= ?, cccd= ?, diachi= ?, path= ?, gioitinh= ?, ngaysinh= ?, mahopdong = ?, chucvu= ?, bophan= ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv);
                                    preparedStatement1.setString(10, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getCccd());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath()); 
                                    preparedStatement1.setString(5, mdtt.getGioitinh());  
                                    preparedStatement1.setDate(6, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(7, mdtt.getMahd());        
                                    preparedStatement1.setString(8, mdtt.getChucvu());        
                                    preparedStatement1.setString(9, mdtt.getBophan());               
                                    preparedStatement1.executeUpdate();
                                }
                            }else{
                                if (kiemTraTrungSDT(sdt)) {
                                    JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (kiemTraTrungCCCD(cccd)) {
                                    JOptionPane.showMessageDialog(null, "CCCD đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Truongphong(bophan) && mdtt.getChucvu().equals("Trưởng phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Trưởng phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (Tontaichucvu_Phophong(bophan) && mdtt.getChucvu().equals("Phó phòng")) {
                                    JOptionPane.showMessageDialog(null, "Bộ phận " + mdtt.getBophan() + " đã có Phó phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else {
                                    // Thực hiện cập nhật thông tin nhân viên
                                    String query_ttnv1 = "UPDATE thongtinnhanvien SET hoten= ?, cccd= ?, diachi= ?, path= ?, sdt= ?, gioitinh= ?, chucvu= ?, bophan= ?, ngaysinh= ?, mahopdong = ? WHERE manv = ?";
                                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_ttnv1);
                                    preparedStatement1.setString(11, mdtt.getManv());
                                    preparedStatement1.setString(1, mdtt.getHoten());
                                    preparedStatement1.setString(2, mdtt.getCccd());
                                    preparedStatement1.setString(3, mdtt.getDiachi());
                                    preparedStatement1.setString(4, mdtt.getPath());      
                                    preparedStatement1.setString(5, mdtt.getSdt());
                                    preparedStatement1.setString(6, mdtt.getGioitinh());
                                    preparedStatement1.setString(7, mdtt.getChucvu());
                                    preparedStatement1.setString(8, mdtt.getBophan());      
                                    preparedStatement1.setDate(9, (java.sql.Date) mdtt.getNgaysinh());
                                    preparedStatement1.setString(10, mdtt.getMahd());                     
                                    preparedStatement1.executeUpdate();
                                }
                            }
                                // Hiển thị thông báo và cập nhật bảng dữ liệu
                                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhân viên " + mdtt.getManv() + "-" + mdtt.getHoten() + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                loadDataToTable();

                                // Tắt các nút Sửa và Lưu và khóa trường nhập liệu
                                view_ttnv.get_sua().setEnabled(false);      
                                view_ttnv.get_luu().setEnabled(false);  
                                view_ttnv.setLock();
                            } else {
                                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin nhân viên có mã nhân viên: " + mdtt.getManv(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }catch (SQLException ex) {
            ex.printStackTrace();
        }        
    }
    
    public void SuaButtonClick(){  
        view_ttnv.get_luu().setEnabled(true);         
    }
    
    public void XuatexcButtonClick() {
        try {
            // Yêu cầu người dùng nhập tên cho file Excel
            String fileName = JOptionPane.showInputDialog(null, "Nhập tên cho file Excel:", "Tên tệp Excel", JOptionPane.PLAIN_MESSAGE);
            if (fileName == null || fileName.trim().isEmpty() || fileName.matches(".*[\\\\/:*?\"<>|].*")) {
                JOptionPane.showMessageDialog(null, "Tên file không hợp lệ!");
                return;
            }

            // Tạo workbook và sheet mới
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Thông tin nhân viên");

            // Tạo tiêu đề cho các cột và thiết lập border
            XSSFRow headerRow = sheet.createRow(0);
            String[] columns = {"Mã NV", "Họ và tên", "Giới tính", "CCCD", "Ngày sinh", "Địa chỉ", "Số điện thoại", "Mã bảo hiểm", "Mã hợp đồng", "Bộ phận", "Chức vụ"};
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            for (int i = 0; i < columns.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Lấy dữ liệu từ bảng hiện tại và điền vào sheet Excel
            DefaultTableModel model = (DefaultTableModel) view_ttnv.tb_thongtin.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < columns.length; j++) {
                    Object value = model.getValueAt(i, j);
                    // Kiểm tra nếu giá trị là null trước khi gọi phương thức toString()
                    String cellValue = (value != null) ? value.toString() : "";
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(cellValue);

                    // Thiết lập border cho ô
                    XSSFCellStyle style = workbook.createCellStyle();
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderTop(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    style.setBorderLeft(BorderStyle.THIN);
                    cell.setCellStyle(style);
                }
            }
            // Auto size cột sau khi đã điền dữ liệu
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Lưu workbook xuống file Excel với tên đã được chỉ định
            String filePath = fileName + ".xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            Desktop.getDesktop().open(new File(filePath)); // Mở file Excel sau khi đã xuất thành công
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xuất Excel thất bại: " + e.getMessage());
        }
    }  
    
    public void ThoatButtonClick(){
        controller_TrangChu a = new controller_TrangChu();
        view_ttnv.dispose();
    }
    
    public void timkiem() {
            try {
                String searchText = view_ttnv.get_txttimkiem().getText();
                String query = "SELECT manv, hoten, gioitinh, cccd, ngaysinh, diachi, sdt, mabaohiem, mahopdong, bophan, chucvu FROM thongtinnhanvien WHERE manv LIKE ? OR hoten LIKE ? OR mahopdong LIKE ? OR mabaohiem LIKE ? OR sdt LIKE ? OR cccd LIKE ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchText + "%");
                preparedStatement.setString(2, "%" + searchText + "%");
                preparedStatement.setString(3, "%" + searchText + "%");
                preparedStatement.setString(4, "%" + searchText + "%");
                preparedStatement.setString(5, "%" + searchText + "%");
                preparedStatement.setString(6, "%" + searchText + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<model_ttnhanvien> dataList = new ArrayList<>();

                while (resultSet.next()) {
                    String manv = resultSet.getString("manv");
                    String hoten = resultSet.getString("hoten");
                    Date ngaysinh = resultSet.getDate("ngaysinh");
                    String gioitinh = resultSet.getString("gioitinh");
                    String cccd = resultSet.getString("cccd");
                    String diachi = resultSet.getString("diachi");
                    String sdt = resultSet.getString("sdt");
                    String mabaohiem = resultSet.getString("mabaohiem");                
                    String mahopdong = resultSet.getString("mahopdong");
                    String bophan = resultSet.getString("bophan");                
                    String chucvu = resultSet.getString("chucvu");

                    model_ttnhanvien md_ttnv = new model_ttnhanvien(manv, hoten, diachi, mahopdong, bophan, mabaohiem, gioitinh, sdt, chucvu, sdt, cccd, ngaysinh);
                    dataList.add(md_ttnv);
                }

                DefaultTableModel model = new DefaultTableModel();
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

                for (model_ttnhanvien md_ttnv : dataList) {
                    Object[] row = {md_ttnv.getManv(), md_ttnv.getHoten(), md_ttnv.getGioitinh(), md_ttnv.getCccd(), md_ttnv.getNgaysinh(), md_ttnv.getDiachi(), md_ttnv.getSdt(), md_ttnv.getMabaohiem(),md_ttnv.getMahd(),md_ttnv.getBophan(),md_ttnv.getChucvu()};
                    model.addRow(row);
                }

                view_ttnv.getTable().setModel(model);
            } catch (SQLException ex) {
                Logger.getLogger(controller_QLTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
   
    public void Anhmacdinh(){            
        ImageIcon ii = new ImageIcon("T:\\Code\\java_QLNS\\BTL\\src\\main\\java\\Image\\anhmacdinh.png");
        Image image = ii.getImage().getScaledInstance(150, 190, Image.SCALE_SMOOTH);            
        view_ttnv.get_lbimage().setIcon(new ImageIcon(image));
    }
       
    public void UploadButtonClick(){
        JFileChooser fileimage = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        fileimage.addChoosableFileFilter(fnef);
        
        int showOpenDialog = fileimage.showOpenDialog(null);
        
        if(showOpenDialog == JFileChooser.APPROVE_OPTION){
            File selectedImageFile = fileimage.getSelectedFile();
            String selectedImagePath = selectedImageFile.getAbsolutePath();
            
            ImageIcon ii = new ImageIcon(selectedImagePath);
            Image image = ii.getImage().getScaledInstance(150, 190, Image.SCALE_SMOOTH);
            
            view_ttnv.get_txtpath().setText(selectedImagePath);
            view_ttnv.get_lbimage().setIcon(new ImageIcon(image));
        }
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
        view_ttnv.setLock();
        int selectedRow = view_ttnv.tb_thongtin.getSelectedRow();
        if (selectedRow >= 0) { // Kiểm tra xem có hàng được chọn không
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
                     String imagePath = resultSet.getString("path");
                     if (imagePath != null && !imagePath.isEmpty()) {
                         ImageIcon imageIcon = new ImageIcon(imagePath);
                         Image image = imageIcon.getImage().getScaledInstance(150, 190, Image.SCALE_SMOOTH);
                         view_ttnv.get_lbimage().setIcon(new ImageIcon(image));
                     } else {
                         Anhmacdinh();
                     }

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

                     // Enable các nút Sửa và Xóa khi có hàng được chọn
                     view_ttnv.get_sua().setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void chinhapso_sdt() {
        JTextField txtSdt = view_ttnv.get_txtsdt();
        txtSdt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                // Kiểm tra nếu ký tự không phải là số
                if (Character.isDigit(character)|| Character.isISOControl(character)) {
                    txtSdt.setEditable(true);
                }else{
                    txtSdt.setEditable(false);
                }
            }
        });
    }    
    public void chinhapso_cccd() {
        JTextField txtCccd = view_ttnv.get_txtcccd();
        txtCccd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                // Kiểm tra nếu ký tự không phải là số
                if (Character.isDigit(character)|| Character.isISOControl(character)) {
                    txtCccd.setEditable(true);
                }else{
                    txtCccd.setEditable(false);
                }
            }
        });
    }
    public void chinhapchu_diachi() {
        JTextField txtDiachi = view_ttnv.get_txtdiachi();
        txtDiachi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                // Kiểm tra nếu ký tự không phải là số
                if (Character.isLetter(character) || Character.isWhitespace(character) ||Character.isISOControl(character)) {
                    txtDiachi.setEditable(true);
                }else{
                    txtDiachi.setEditable(false);
                }
            }
        });
    }
    
    public boolean Tontaichucvu_Truongphong(String bophan) {
        boolean tonTai = false;
        try {
            // Tạo câu truy vấn SQL để kiểm tra xem bộ phận đã có Trưởng phòng hoặc Phó phòng chưa
            String query = "SELECT * FROM thongtinnhanvien WHERE bophan = ? AND chucvu = 'Trưởng phòng'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bophan);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Nếu có kết quả trả về từ câu truy vấn, tồn tại Trưởng phòng hoặc Phó phòng trong bộ phận
            if (resultSet.next()) {
                tonTai = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tonTai;
    }
    public boolean Tontaichucvu_Phophong(String bophan) {
        boolean tonTai = false;
        try {
            // Tạo câu truy vấn SQL để kiểm tra xem bộ phận đã có Trưởng phòng hoặc Phó phòng chưa
            String query = "SELECT * FROM thongtinnhanvien WHERE bophan = ? AND chucvu = 'Phó phòng'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bophan);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Nếu có kết quả trả về từ câu truy vấn, tồn tại Trưởng phòng hoặc Phó phòng trong bộ phận
            if (resultSet.next()) {
                tonTai = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tonTai;
    }
    
    public boolean kiemTraTrungCCCD(String cccd) {
        try {
            String query = "SELECT COUNT(*) AS count FROM thongtinnhanvien WHERE cccd = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cccd);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Trả về true nếu đã tồn tại CCCD, ngược lại trả về false
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public boolean kiemTraTrungSDT(String sdt) {
        try {
            String query = "SELECT COUNT(*) AS count FROM thongtinnhanvien WHERE sdt = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sdt);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Trả về true nếu đã tồn tại SDT, ngược lại trả về false
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

    public void loadDataToTable() {
        try {
            String query = "SELECT manv, hoten, gioitinh, cccd, ngaysinh, diachi, sdt, mabaohiem, mahopdong, bophan, chucvu FROM thongtinnhanvien";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<model_ttnhanvien> dataList = new ArrayList<>();

            while (resultSet.next()) {
                String manv = resultSet.getString("manv");
                String hoten = resultSet.getString("hoten");
                Date ngaysinh = resultSet.getDate("ngaysinh");
                String gioitinh = resultSet.getString("gioitinh");
                String cccd = resultSet.getString("cccd");
                String diachi = resultSet.getString("diachi");
                String sdt = resultSet.getString("sdt");
                String mabaohiem = resultSet.getString("mabaohiem");                
                String mahopdong = resultSet.getString("mahopdong");
                String bophan = resultSet.getString("bophan");                
                String chucvu = resultSet.getString("chucvu");

                model_ttnhanvien md_ttnv = new model_ttnhanvien(manv, hoten, diachi, mahopdong, bophan, mabaohiem, gioitinh, sdt, chucvu, sdt, cccd, ngaysinh);
                dataList.add(md_ttnv);
            }

            DefaultTableModel model = new DefaultTableModel();
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

            for (model_ttnhanvien md_ttnv : dataList) {
                Object[] row = {md_ttnv.getManv(), md_ttnv.getHoten(), md_ttnv.getGioitinh(), md_ttnv.getCccd(), md_ttnv.getNgaysinh(), md_ttnv.getDiachi(), md_ttnv.getSdt(), md_ttnv.getMabaohiem(),md_ttnv.getMahd(),md_ttnv.getBophan(),md_ttnv.getChucvu()};
                model.addRow(row);
            }

            view_ttnv.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_ttnhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public static void main(String[] args) {
        controller_ttnhanvien ttnv = new controller_ttnhanvien();
    }
}
