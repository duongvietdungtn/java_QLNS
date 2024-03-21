package Controller;

import Controller.KetNoi;
import Controller.controller_TrangChu;
import View.view_QLNghiPhep;
import Model.NghiPhep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class controller_QLNghiPhep {
    private view_QLNghiPhep view;
    private Connection connection;

    public controller_QLNghiPhep() {
        view = new view_QLNghiPhep(this);
        connection = KetNoi.getConnection();
        view.setVisible(true);
        loadMaNhanVienToComboBox();
        loadDataToTable();
    }

    public void handleClearButtonClick() {
        clearFields();
    }

    public void handleBackButtonClick() {
        controller_TrangChu a = new controller_TrangChu();
        view.dispose();
    }

    public void handleAddButtonClick() {
        try {
            String manp = view.getManp_text().getText();
            String manv = view.getManvComboBox().getSelectedItem().toString();
            java.util.Date selectedDate = view.getNgaynghiChooser().getDate();
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
            String chophep = view.getChophepComboBox().getSelectedItem().toString();

            if (isDuplicate(manv, sqlDate.toString(), "")) {
                JOptionPane.showMessageDialog(null, "Mã Nhân Viên và Ngày Nghỉ đã tồn tại!");
            } else if (isManpExists(manp)) {
                JOptionPane.showMessageDialog(null, "Trùng Mã Nghỉ Phép, vui lòng kiểm tra lại!");
            } else {
                NghiPhep nghiPhep = new NghiPhep(manp, manv, sqlDate, chophep);
                insertNghiPhep(nghiPhep);
                loadDataToTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleEditButtonClick() {
        try {
            String manp = view.getManp_text().getText();
            String manv = view.getManvComboBox().getSelectedItem().toString();
            java.util.Date selectedDate = view.getNgaynghiChooser().getDate();
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
            String chophep = view.getChophepComboBox().getSelectedItem().toString();

            if (isDuplicate(manv, sqlDate.toString(), manp)) {
                JOptionPane.showMessageDialog(null, "Mã Nhân Viên và Ngày Nghỉ đã tồn tại!");
            } else if (!isManpExists(manp)) {
                JOptionPane.showMessageDialog(null, "Mã Nghỉ Phép không tồn tại, vui lòng kiểm tra lại!");
            } else {
                NghiPhep nghiPhep = new NghiPhep(manp, manv, sqlDate, chophep);
                updateNghiPhep(nghiPhep);
                loadDataToTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleDeleteButtonClick() {
        String manp = view.getManp_text().getText();

        try {
            deleteNghiPhep(manp);
            loadDataToTable();
            clearFields();
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLNghiPhep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayData() {
        int selectedRow = view.getTable().getSelectedRow();

        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            view.getManp_text().setText(model.getValueAt(selectedRow, 0).toString());
            view.getManvComboBox().setSelectedItem(model.getValueAt(selectedRow, 1).toString());
            java.util.Date selectedDate = (java.util.Date) model.getValueAt(selectedRow, 2);
            view.getNgaynghiChooser().setDate(selectedDate);

            // Sử dụng JComboBox để hiển thị lựa chọn "Có" hoặc "Không"
            String chophepValue = model.getValueAt(selectedRow, 3).toString();
            if (chophepValue.equals("Có")) {
                view.getChophepComboBox().setSelectedItem("Có");
            } else {
                view.getChophepComboBox().setSelectedItem("Không");
            }
        }
    }

    public void loadDataToTable() {
        try {
            String query = "SELECT manp, manv, ngaynghi, chophep FROM qlnghiphep";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<NghiPhep> dataList = new ArrayList<>();

            while (resultSet.next()) {
                String manp = resultSet.getString("manp");
                String manv = resultSet.getString("manv");
                Date ngaynghi = resultSet.getDate("ngaynghi");
                String chophep = resultSet.getString("chophep");

                NghiPhep nghiPhep = new NghiPhep(manp, manv, ngaynghi, chophep);
                dataList.add(nghiPhep);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã Nghỉ Phép");
            model.addColumn("Mã Nhân Viên");
            model.addColumn("Ngày Nghỉ");
            model.addColumn("Cho Phép");

            for (NghiPhep nghiPhep : dataList) {
                Object[] row = {nghiPhep.getManp(), nghiPhep.getManv(), nghiPhep.getNgaynghi(), nghiPhep.getChophep()};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLNghiPhep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleSearchButtonClick() {
        try {
            String searchText = view.getSearchTextField().getText();
            String query = "SELECT manp, manv, ngaynghi, chophep FROM qlnghiphep WHERE manp LIKE ? OR manv LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<NghiPhep> nghiPhepList = new ArrayList<>();

            while (resultSet.next()) {
                String manp = resultSet.getString("manp");
                String manv = resultSet.getString("manv");
                Date ngaynghi = resultSet.getDate("ngaynghi");
                String chophep = resultSet.getString("chophep");

                NghiPhep nghiPhep = new NghiPhep(manp, manv, ngaynghi, chophep);
                nghiPhepList.add(nghiPhep);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã Nghỉ Phép");
            model.addColumn("Mã Nhân Viên");
            model.addColumn("Ngày Nghỉ");
            model.addColumn("Cho Phép");

            for (NghiPhep nghiPhep : nghiPhepList) {
                Object[] row = {nghiPhep.getManp(), nghiPhep.getManv(), nghiPhep.getNgaynghi(), nghiPhep.getChophep()};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLNghiPhep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExcelButtonClick() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

            // Tạo Workbook và Sheet mới
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("NgayNghiData");

            // Tạo dòng đầu tiên cho tên cột
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            // Đổ dữ liệu từ bảng vào file Excel
            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1); // Bắt đầu từ dòng thứ 2

                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object cellValue = model.getValueAt(row, col);

                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        cell.setCellValue(""); // Tránh lỗi nếu giá trị là null
                    }
                }
            }

            // Lưu Workbook vào file Excel
            try (FileOutputStream fileOut = new FileOutputStream("NgayNghiData.xlsx")) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xuất Excel thất bại!");
            }

            // Đóng Workbook
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        view.getManp_text().setText("");
        view.getManvComboBox().setSelectedIndex(-1);
        view.getNgaynghiChooser().setDate(null);
        view.getChophepComboBox().setSelectedItem("Có");
    }

    private boolean isManpExists(String manp) throws SQLException {
        String query = "SELECT * FROM qlnghiphep WHERE manp=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, manp);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    private boolean isDuplicate(String manv, String ngaynghi, String excludeManp) throws SQLException {
        String query = "SELECT * FROM qlnghiphep WHERE manv=? AND ngaynghi=? AND manp<>?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, manv);
        preparedStatement.setString(2, ngaynghi);
        preparedStatement.setString(3, excludeManp);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    private void insertNghiPhep(NghiPhep nghiPhep) throws SQLException {
        String query = "INSERT INTO qlnghiphep (manp, manv, ngaynghi, chophep) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nghiPhep.getManp());
        preparedStatement.setString(2, nghiPhep.getManv());
        preparedStatement.setDate(3, nghiPhep.getNgaynghi());
        preparedStatement.setString(4, nghiPhep.getChophep());
        preparedStatement.executeUpdate();
    }

    private void updateNghiPhep(NghiPhep nghiPhep) throws SQLException {
        String query = "UPDATE qlnghiphep SET manv=?, ngaynghi=?, chophep=? WHERE manp=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nghiPhep.getManv());
        preparedStatement.setDate(2, nghiPhep.getNgaynghi());
        preparedStatement.setString(3, nghiPhep.getChophep());
        preparedStatement.setString(4, nghiPhep.getManp());
        preparedStatement.executeUpdate();
    }

    private void deleteNghiPhep(String manp) throws SQLException {
        String query = "DELETE FROM qlnghiphep WHERE manp=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, manp);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void loadMaNhanVienToComboBox() {
        try {
            String query = "SELECT manv FROM thongtinnhanvien";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                view.getManvComboBox().addItem(resultSet.getString("manv"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        controller_QLNghiPhep a = new controller_QLNghiPhep();
    }
}
