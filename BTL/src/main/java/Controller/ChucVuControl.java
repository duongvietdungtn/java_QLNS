/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import ConnectDB.ConnectDB;
import Model.ChucVu;
import View.ChucVuView;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ChucVuControl {
    private ConnectDB connectDB;
    private ChucVuView view;

    public ChucVuControl() {
        this.connectDB = new ConnectDB();
        this.view = new ChucVuView(this);
    }  
    public void xuLyThemChucVu(String macv, String chucvu) {
    try {
        if(macv.isEmpty() || chucvu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(isDuplicate(macv, chucvu)) {
            JOptionPane.showMessageDialog(null, "Mã chức vụ hoặc chức vụ đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChucVu cv = new ChucVu(macv, chucvu);
        themChucVu(cv);       
        JOptionPane.showMessageDialog(null, "Chức vụ được thêm thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm chức vụ", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    public void themChucVu(ChucVu cv) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "INSERT INTO `chucvu`(`macv`, `chucvu`) VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, cv.getMacv());
            pst.setString(2, cv.getChucvu());           
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }   
    public boolean isDuplicate(String macv, String chucvu) {
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT COUNT(*) FROM chucvu WHERE macv = ? OR chucvu = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, macv);
        pst.setString(2, chucvu);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
    return false;
}
    
    public void xuLyCapNhatChucVu(String macv, String chucvu) {    
    try {       
        if(isDuplicateForUpdate(macv, chucvu,  layDanhSachChucVu())) { 
            JOptionPane.showMessageDialog(null, "Thông tin nhập mới trùng với thông tin khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ChucVu cv = new ChucVu(macv, chucvu);
        capNhatChucVu(cv);
        view.updateTableData(layDanhSachChucVu());
        JOptionPane.showMessageDialog(null, "Cập nhật chức vụ thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật chức vụ", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    public void capNhatChucVu(ChucVu cv) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "UPDATE chucvu SET  chucvu = ? WHERE macv = ?";
            PreparedStatement statement = conn.prepareStatement(sql);          
            statement.setString(1, cv.getChucvu());
            statement.setString(2, cv.getMacv());
            statement.executeUpdate();          
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }
    public boolean isDuplicateForUpdate(String macv, String chucvu, List<ChucVu> danhSachChucVu) {
    for (ChucVu cv : danhSachChucVu) {
        if (!cv.getMacv().equals(macv)) {
            if (cv.getChucvu().equals(chucvu)) {
                return true; // Tìm thấy trùng lập
            }
        }
    }
    return false; // Không tìm thấy trùng lập
}
    public void xoaChucVu(String macv) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM chucvu WHERE macv = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, macv);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }   
    public void xuLyXoaChucVu(String macv) {
    int response = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá chức vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.YES_OPTION) {
        try {
            xoaChucVu(macv); 
            JOptionPane.showMessageDialog(null, "Xoá chức vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xoá chức vụ", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            XSSFSheet sheet = workbook.createSheet("Chức Vụ");
            // Tạo tiêu đề cho sheet
            Row headerRow = sheet.createRow(0);
            String[] columnNames = {"Mã chức vụ", "Chức vụ"};
            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames[i]);
            }
            // Lấy dữ liệu từ database
            List<ChucVu> danhSachChucVu = layDanhSachChucVu();
            int rowNum = 1;
            for (ChucVu cv : danhSachChucVu) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cv.getMacv());
                row.createCell(1).setCellValue(cv.getChucvu());
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
    public List<ChucVu> layDanhSachChucVu() {
    List<ChucVu> danhSachChucVu = new ArrayList<>();
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT * FROM chucvu";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ChucVu cv = new ChucVu(               
                resultSet.getString("macv"),
                resultSet.getString("chucvu")
            );
            danhSachChucVu.add(cv);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
    return danhSachChucVu;
}
    public static void main(String[] args) {
        ChucVuControl control1=new ChucVuControl();
        ChucVuView view1 = new ChucVuView(control1);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
            view1.setSize(800, 600);
            view1.setLocationRelativeTo(null);
            view1.setVisible(true);
        }             
    }


