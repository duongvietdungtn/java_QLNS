/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import ConnectDB.ConnectDB;
import Model.BHXH;
import View.BHXHView;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Admin
 */
public class BHXHControl {
    private ConnectDB connectDB;
    private BHXHView view;

    public BHXHControl() {
        this.connectDB = new ConnectDB();
        this.view = new BHXHView(this);
    }  
    public void xuLyThemBHXH(String mabaohiem, String manv,String loaibaohiem, java.util.Date ngaybatdau,java.util.Date ngayketthuc) {
    try {
        if (mabaohiem.isEmpty() || manv.isEmpty() || loaibaohiem.isEmpty() || ngaybatdau == null || ngayketthuc ==null) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        if(isDuplicate(mabaohiem, manv)) {
            JOptionPane.showMessageDialog(null, "Mã bảo hiểm hoặc mã nhân viên đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.sql.Date sqlDate1 = new java.sql.Date(ngaybatdau.getTime());
        java.sql.Date sqlDate2 = new java.sql.Date(ngayketthuc.getTime());
        BHXH bh = new BHXH(mabaohiem, manv, loaibaohiem,sqlDate1,sqlDate2);
        themBHXH(bh);
        JOptionPane.showMessageDialog(null, "Mã bảo hiểm được thêm thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm Mã bảo hiểm", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    public void themBHXH(BHXH bh) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql1 = "INSERT INTO `baohiemxh`(`mabaohiem`, `manv`,`loaibaohiem`, `ngaybatdau`, `ngayketthuc`) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setString(1, bh.getMabaohiem());
            pst.setString(2, bh.getManv());
            pst.setString(3, bh.getLoaibaohiem());           
            pst.setDate(4, (java.sql.Date) bh.getNgaybatdau());
            pst.setDate(5, (java.sql.Date) bh.getNgayketthuc());
            pst.executeUpdate();
            
            String sql2="UPDATE thongtinnhanvien SET mabaohiem=? where manv=?";
            PreparedStatement pst2=conn.prepareCall(sql2);
            pst2.setString(1, bh.getMabaohiem());
            pst2.setString(2, bh.getManv());
            pst2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }      
    
    public boolean isDuplicate(String mabaohiem, String manv) {
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT COUNT(*) FROM baohiemxh WHERE mabaohiem = ? OR manv = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, mabaohiem);
        pst.setString(2, manv);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Trả về true nếu có ít nhất một bản ghi trùng khớp
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
    return false;
}    
    public void xuLyCapNhatBHXH(String mabaohiem, String manv,String loaibaohiem, Date ngaybatdau,Date ngayketthuc) {    
    try {       
        if(isDuplicateForUpdate(mabaohiem, manv,  layDanhSachBHXH())) { 
            JOptionPane.showMessageDialog(null, "Thông tin nhập mới trùng với thông tin khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BHXH bh = new BHXH(mabaohiem, manv,loaibaohiem,ngaybatdau,ngayketthuc);
        capNhatBHXH(bh);
        view.updateTableData(layDanhSachBHXH()); // Đảm bảo rằng dòng này thực sự được gọi
        JOptionPane.showMessageDialog(null, "Cập nhật mã bảo hiểm thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật mã bảo hiểm", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
     public void capNhatBHXH(BHXH bh) {
    Connection conn = ConnectDB.getConnection();
    try {       
        String sql = "UPDATE baohiemxh SET manv = ?, loaibaohiem = ?, ngaybatdau = ?, ngayketthuc = ?  WHERE mabaohiem = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, bh.getManv());
        statement.setString(2, bh.getLoaibaohiem());
        java.sql.Date sqlDate1 = new java.sql.Date(bh.getNgaybatdau().getTime());
        java.sql.Date sqlDate2 = new java.sql.Date(bh.getNgayketthuc().getTime());
        statement.setDate(3, sqlDate1);
        statement.setDate(4, sqlDate2);
        statement.setString(5, bh.getMabaohiem());
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
}
    public boolean isDuplicateForUpdate(String mabaohiem, String manv, List<BHXH> danhSachBHXH) {
    for (BHXH bh : danhSachBHXH) {
        if (!bh.getMabaohiem().equals(mabaohiem)) {
            if (bh.getManv().equals(manv)) {
                return true; // Tìm thấy trùng lập
            }
        }
    }
    return false; // Không tìm thấy trùng lập
}
    public void xoaBHXH(String mabaohiem) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM baohiemxh WHERE mabaohiem = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mabaohiem);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }   
    public void xuLyXoaBHXH(String mabaohiem) {
        int response = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá mã bảo hiểm này?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try {
                xoaBHXH(mabaohiem); 
                JOptionPane.showMessageDialog(null, "Xoá mã bảo hiểm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xoá mã bảo hiểm", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    public void xuLyXuatExcel() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");
    int userSelection = fileChooser.showSaveDialog(view);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
    }
        try {
            // Tạo một workbook mới
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Bảo hiểm");
            // Tạo tiêu đề cho sheet
            Row headerRow = sheet.createRow(0);
            String[] columnNames = {"Mã  bảo hiểm", "Mã nhân viên","Loại bảo hiểm","Ngày bắt đầu","Ngày kết thúc"};
            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames[i]);
            }
            // Lấy dữ liệu từ database
            List<BHXH> danhSachBHXH = layDanhSachBHXH();
            int rowNum = 1;
            for (BHXH bh : danhSachBHXH) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(bh.getMabaohiem());
                row.createCell(1).setCellValue(bh.getManv());
                row.createCell(2).setCellValue(bh.getLoaibaohiem());
                row.createCell(3).setCellValue(bh.getNgaybatdau());
                row.createCell(4).setCellValue(bh.getNgayketthuc());
            }
            // Ghi dữ liệu vào file
            FileOutputStream fileOut = new FileOutputStream(fileToSave);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    public List<BHXH> layDanhSachBHXH() {
    List<BHXH> danhSachBHXH = new ArrayList<>();
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT * FROM baohiemxh";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            BHXH bh;
            bh = new BHXH(               
                    resultSet.getString("mabaohiem"),
                    resultSet.getString("manv"),
                    resultSet.getString("loaibaohiem"),
                    resultSet.getDate("ngaybatdau"),
                    resultSet.getDate("ngayketthuc")
            );
            danhSachBHXH.add(bh);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
    return danhSachBHXH;
}

    public static void main(String[] args) {
        BHXHControl control1=new BHXHControl();
        BHXHView view1 = new BHXHView(control1);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        view1.setSize(1200, 800);
        view1.setLocationRelativeTo(null);
        view1.setVisible(true);
    }             
}
