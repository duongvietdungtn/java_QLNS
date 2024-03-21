package Controller;

import Model.TaiKhoan;
import View.view_QLTaiKhoan;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class controller_QLTaiKhoan {
    private view_QLTaiKhoan view;
    private Connection connection;

    public controller_QLTaiKhoan() {
        view = new view_QLTaiKhoan(this);
        connection = KetNoi.getConnection(); // Lấy kết nối từ lớp KetNoi
        view.setVisible(true);
        loadDataToTable();
    }

    public void handleBackButtonClick() {
        controller_TrangChu a = new controller_TrangChu();
        view.dispose();
    }

    
    public void handleAddButtonClick() {
        try {
            int id = Integer.parseInt(view.getId_text().getText());
            String username = view.getUsername_text().getText();
            String password = view.getPassword_text().getText();
            String role = view.getSelectedRole();

            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setId(id);
            taiKhoan.setUsername(username);
            taiKhoan.setPassword(password);
            taiKhoan.setRole(role);

            addTaiKhoan(taiKhoan);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void addTaiKhoan(TaiKhoan taiKhoan) {
        try {
            if (isIdExists(taiKhoan.getId())) {
                JOptionPane.showMessageDialog(null, "Trùng ID, vui lòng kiểm tra lại!");
            } else {
                String query = "INSERT INTO taikhoan (id, username, password, role) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, taiKhoan.getId());
                preparedStatement.setString(2, taiKhoan.getUsername());
                preparedStatement.setString(3, taiKhoan.getPassword());
                preparedStatement.setString(4, taiKhoan.getRole());
                preparedStatement.executeUpdate();

                // Sau khi thêm xong, cập nhật lại bảng
                loadDataToTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleEditButtonClick() {
        try {
            int id = Integer.parseInt(view.getId_text().getText());
            String username = view.getUsername_text().getText();
            String password = view.getPassword_text().getText();
            String role = view.getSelectedRole();

            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setId(id);
            taiKhoan.setUsername(username);
            taiKhoan.setPassword(password);
            taiKhoan.setRole(role);

            // Gọi hàm để chỉnh sửa thông tin tài khoản trong cơ sở dữ liệu
            editTaiKhoan(taiKhoan);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void editTaiKhoan(TaiKhoan taiKhoan) {
        try {
            // Kiểm tra xem ID đã tồn tại chưa
            if (isIdExists(taiKhoan.getId())) {
                String query = "UPDATE taikhoan SET username=?, password=?, role=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, taiKhoan.getUsername());
                preparedStatement.setString(2, taiKhoan.getPassword());
                preparedStatement.setString(3, taiKhoan.getRole());
                preparedStatement.setInt(4, taiKhoan.getId());
                preparedStatement.executeUpdate();

                // Sau khi chỉnh sửa xong, cập nhật lại bảng
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(null, "ID không tồn tại, vui lòng kiểm tra lại!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleDeleteButtonClick() {
        int id = Integer.parseInt(view.getId_text().getText());

        try {
            // Gọi hàm để xóa tài khoản từ cơ sở dữ liệu
            deleteTaiKhoan(id);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteTaiKhoan(int id) throws SQLException {
        String query = "DELETE FROM taikhoan WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
        preparedStatement.close();

        loadDataToTable();
        clearFields();
    }

    public void handleClearButtonClick() {
        clearFields();
    }

    public void handleExcelButtonClick() {
        try {
            exportToExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportToExcel() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TaiKhoanData");

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1); 

                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object cellValue = model.getValueAt(row, col);

                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue(""); 
                    }
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream("TaiKhoanData.xlsx")) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xuất Excel thất bại!");
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSearchButtonClick() {
        try {
            String searchText = view.getSearchTextField().getText();
            String query = "SELECT id, username, password, role FROM taikhoan WHERE id LIKE ? OR username LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tên người dùng");
            model.addColumn("Mật khẩu");
            model.addColumn("Quyền");

            ArrayList<TaiKhoan> taiKhoanList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                TaiKhoan taiKhoan = new TaiKhoan(id, username, password, role);
                taiKhoanList.add(taiKhoan);

                Object[] row = {id, username, password, role};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayData() {
    int selectedRow = view.getTable().getSelectedRow();

    if (selectedRow >= 0) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        view.getId_text().setText(model.getValueAt(selectedRow, 0).toString());
        view.getUsername_text().setText(model.getValueAt(selectedRow, 1).toString());
        view.getPassword_text().setText(model.getValueAt(selectedRow, 2).toString());
        String selectedRole = model.getValueAt(selectedRow, 3).toString();
        view.getRoleComboBox().setSelectedItem(selectedRole);
    }
}

    
    public void loadDataToTable() {
        try {
            String query = "SELECT id, username, password, role FROM taikhoan";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<TaiKhoan> taiKhoanList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                TaiKhoan taiKhoan = new TaiKhoan(id, username, password, role);
                taiKhoanList.add(taiKhoan);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Tên người dùng");
            model.addColumn("Mật khẩu");
            model.addColumn("Quyền");

            for (TaiKhoan taiKhoan : taiKhoanList) {
                Object[] row = {taiKhoan.getId(), taiKhoan.getUsername(), taiKhoan.getPassword(), taiKhoan.getRole()};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLTaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void clearFields() {
        view.getId_text().setText("");
        view.getUsername_text().setText("");
        view.getPassword_text().setText("");
        view.getRoleComboBox().setSelectedIndex(-1);
    }

    private boolean isIdExists(int id) throws SQLException {
        String query = "SELECT * FROM taikhoan WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static void main(String[] args) {
        controller_QLTaiKhoan a = new controller_QLTaiKhoan();
    }
}
