package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import ConnectDB.ConnectDB;
import Model.BoPhan;
import View.BPView;
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
public class BPControl {    
    private ConnectDB connectDB;
    private BPView view;

    public BPControl() {
        this.connectDB = new ConnectDB();
        this.view = new BPView(this);
    }  
    public void xuLyThemBoPhan(String mabp, String bophan, java.util.Date ngaythanhlap) {
    try {
        if (mabp.isEmpty() || bophan.isEmpty() || ngaythanhlap == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        if(isDuplicate(mabp, bophan)) {
            JOptionPane.showMessageDialog(null, "Mã bộ phận hoặc bộ phận đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(ngaythanhlap.getTime());
        BoPhan bp = new BoPhan(mabp, bophan, sqlDate);
        themBoPhan(bp);
        JOptionPane.showMessageDialog(null, "Bộ phận được thêm thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm Bộ phận", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    public void themBoPhan(BoPhan bp) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "INSERT INTO `bophan`(`mabp`, `bophan`,`ngaythanhlap`) VALUES (?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, bp.getMabp());
            pst.setString(2, bp.getBophan());
            pst.setDate(3, (java.sql.Date) bp.getNgaythanhlap());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    } 
    public boolean isDuplicate(String mabp, String bophan) {
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT COUNT(*) FROM bophan WHERE mabp = ? OR bophan = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, mabp);
        pst.setString(2, bophan);
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
    public void xuLyCapNhatBoPhan(String mabp, String bophan, Date ngaythanhlap) {    
    try {       
        if(isDuplicateForUpdate(mabp, bophan,  layDanhSachBoPhan())) { 
            JOptionPane.showMessageDialog(null, "Thông tin nhập mới trùng với thông tin khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BoPhan bp = new BoPhan(mabp, bophan,ngaythanhlap);
        capNhatBoPhan(bp);
        view.updateTableData(layDanhSachBoPhan()); 
        JOptionPane.showMessageDialog(null, "Cập nhật bộ phận thành công");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật bộ phận", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
     public void capNhatBoPhan(BoPhan bp) {
    Connection conn = ConnectDB.getConnection();
    try {       
        String sql = "UPDATE bophan SET bophan = ?, ngaythanhlap = ? WHERE mabp = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, bp.getBophan());
        java.sql.Date sqlDate = new java.sql.Date(bp.getNgaythanhlap().getTime());
        statement.setDate(2, sqlDate);
        statement.setString(3, bp.getMabp());
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
}
    public boolean isDuplicateForUpdate(String mabp, String bophan, List<BoPhan> danhSachBoPhan) {
    for (BoPhan bp : danhSachBoPhan) {
        if (!bp.getMabp().equals(mabp)) {
            if (bp.getBophan().equals(bophan)) {
                return true; // Tìm thấy trùng lập
            }
        }
    }
    return false; // Không tìm thấy trùng lập
}
    public void xoaBoPhan(String mabp) {
        Connection conn = ConnectDB.getConnection();
        try {
            String sql = "DELETE FROM bophan WHERE mabp = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, mabp);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }   
    public void xuLyXoaBoPhan(String mabp) {
    int response = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá bộ phận này?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.YES_OPTION) {
        try {
            xoaBoPhan(mabp); 
            JOptionPane.showMessageDialog(null, "Xoá bộ phận thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xoá bộ phận", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            XSSFSheet sheet = workbook.createSheet("Bộ phận");
            // Tạo tiêu đề cho sheet
            Row headerRow = sheet.createRow(0);
            String[] columnNames = {"Mã bộ phận", "Bộ phận","Ngày thành lập"};
            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames[i]);
            }
            // Lấy dữ liệu từ database
            List<BoPhan> danhSachBoPhan = layDanhSachBoPhan();
            int rowNum = 1;
            for (BoPhan bp : danhSachBoPhan) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(bp.getMabp());
                row.createCell(1).setCellValue(bp.getBophan());
                row.createCell(2).setCellValue(bp.getNgaythanhlap());
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
    public List<BoPhan> layDanhSachBoPhan() {
    List<BoPhan> danhSachBoPhan = new ArrayList<>();
    Connection conn = ConnectDB.getConnection();
    try {
        String sql = "SELECT * FROM bophan";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            BoPhan bp;
            bp = new BoPhan(               
                    resultSet.getString("mabp"),
                    resultSet.getString("bophan"),
                    resultSet.getDate("ngaythanhlap")
            );
            danhSachBoPhan.add(bp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(conn);
    }
    return danhSachBoPhan;
}
    
    public static void main(String[] args) {
        BPControl control1=new BPControl();
        BPView view1 = new BPView(control1);
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