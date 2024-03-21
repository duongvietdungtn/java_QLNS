package Controller;

import Model.Luong;
import View.view_QLLuong;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class controller_QLLuong {
    private view_QLLuong view;
    private Connection connection;

    public controller_QLLuong() {
        view = new view_QLLuong(this);
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
            Luong luong = new Luong();
            luong.setMaluong(view.getMal_text().getText());
            luong.setManv(view.getManvComboBox().getSelectedItem().toString());
            luong.setLuongcung((int) Float.parseFloat(view.getLuongCung_text().getText()));
            luong.setThuong((int) Float.parseFloat(view.getThuong_text().getText()));
            luong.setPhat((int) Float.parseFloat(view.getPhat_text().getText()));
            luong.setTongluong(luong.getLuongcung() + luong.getThuong() - luong.getPhat()); 

            if (isDuplicate(luong.getMaluong(), luong.getManv())) {
                JOptionPane.showMessageDialog(null, "Mã Lương và Mã Nhân Viên đã tồn tại!");
            } else {
                String query = "INSERT INTO qlluong (maluong, manv, luongcung, thuong, phat, tongluong) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, luong.getMaluong());
                preparedStatement.setString(2, luong.getManv());
                preparedStatement.setFloat(3, luong.getLuongcung());
                preparedStatement.setFloat(4, luong.getThuong());
                preparedStatement.setFloat(5, luong.getPhat());
                preparedStatement.setFloat(6, luong.getTongluong());
                preparedStatement.executeUpdate();

                loadDataToTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleEditButtonClick() {
        try {
            Luong luong = new Luong();
            luong.setMaluong(view.getMal_text().getText());
            luong.setManv(view.getManvComboBox().getSelectedItem().toString());
            luong.setLuongcung((int) Float.parseFloat(view.getLuongCung_text().getText()));
            luong.setThuong((int) Float.parseFloat(view.getThuong_text().getText()));
            luong.setPhat((int) Float.parseFloat(view.getPhat_text().getText()));
            luong.setTongluong(luong.getLuongcung() + luong.getThuong() - luong.getPhat()); // Tính toán tổng lương

            if (!isDuplicate(luong.getMaluong(), luong.getManv())) {
                JOptionPane.showMessageDialog(null, "Mã Lương không tồn tại!");
            } else {
                String query = "UPDATE qlluong SET manv=?, luongcung=?, thuong=?, phat=?, tongluong=? WHERE maluong=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, luong.getManv());
                preparedStatement.setFloat(2, luong.getLuongcung());
                preparedStatement.setFloat(3, luong.getThuong());
                preparedStatement.setFloat(4, luong.getPhat());
                preparedStatement.setFloat(5, luong.getTongluong());
                preparedStatement.setString(6, luong.getMaluong());
                preparedStatement.executeUpdate();

                loadDataToTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void handleDeleteButtonClick() {
        String maluong = view.getMal_text().getText();

        try {
            String query = "DELETE FROM qlluong WHERE maluong=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maluong);
            preparedStatement.executeUpdate();
            preparedStatement.close();

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
            view.getMal_text().setText(model.getValueAt(selectedRow, 0).toString());
            view.getManvComboBox().setSelectedItem(model.getValueAt(selectedRow, 1).toString());
            view.getLuongCung_text().setText(model.getValueAt(selectedRow, 2).toString());
            view.getThuong_text().setText(model.getValueAt(selectedRow, 3).toString());
            view.getPhat_text().setText(model.getValueAt(selectedRow, 4).toString());
            view.getTongLuong_text().setText(model.getValueAt(selectedRow, 5).toString());
        }
    }

    public void loadDataToTable() {
        try {
            String query = "SELECT maluong, manv, luongcung, thuong, phat, tongluong FROM qlluong";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Luong> luongList = new ArrayList<>();

            while (resultSet.next()) {
                String maluong = resultSet.getString("maluong");
                String manv = resultSet.getString("manv");
                int luongcung = resultSet.getInt("luongcung");
                int thuong = resultSet.getInt("thuong");
                int phat = resultSet.getInt("phat");
                int tongluong = resultSet.getInt("tongluong");

                Luong luong = new Luong(maluong, manv, luongcung, thuong, phat, tongluong);
                luongList.add(luong);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã Lương");
            model.addColumn("Mã Nhân Viên");
            model.addColumn("Lương Cứng");
            model.addColumn("Thưởng");
            model.addColumn("Phạt");
            model.addColumn("Tổng Lương");

            for (Luong luong : luongList) {
                Object[] row = {luong.getMaluong(), luong.getManv(), luong.getLuongcung(), luong.getThuong(), luong.getPhat(), luong.getTongluong()};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLLuong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleExcelButtonClick() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

            // Tạo Workbook và Sheet mới
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("LuongData");

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

            try (FileOutputStream fileOut = new FileOutputStream("LuongData.xlsx")) {
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
            String query = "SELECT maluong, manv, luongcung, thuong, phat, tongluong FROM qlluong WHERE maluong LIKE ? OR manv LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Luong> luongList = new ArrayList<>();

            while (resultSet.next()) {
                String maluong = resultSet.getString("maluong");
                String manv = resultSet.getString("manv");
                int luongcung = resultSet.getInt("luongcung");
                int thuong = resultSet.getInt("thuong");
                int phat = resultSet.getInt("phat");
                int tongluong = resultSet.getInt("tongluong");

                Luong luong = new Luong(maluong, manv, luongcung, thuong, phat, tongluong);
                luongList.add(luong);
            }

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã Lương");
            model.addColumn("Mã Nhân Viên");
            model.addColumn("Lương Cứng");
            model.addColumn("Thưởng");
            model.addColumn("Phạt");
            model.addColumn("Tổng Lương");

            for (Luong luong : luongList) {
                Object[] row = {luong.getMaluong(), luong.getManv(), luong.getLuongcung(), luong.getThuong(), luong.getPhat(), luong.getTongluong()};
                model.addRow(row);
            }

            view.getTable().setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(controller_QLNghiPhep.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void clearFields() {
        view.getMal_text().setText("");
        view.getManvComboBox().setSelectedIndex(-1);
        view.getLuongCung_text().setText("");
        view.getThuong_text().setText("");
        view.getPhat_text().setText("");
        view.getTongLuong_text().setText("");
    }

    private boolean isDuplicate(String maluong, String manv) throws SQLException {
        String query = "SELECT * FROM qlluong WHERE maluong=? AND manv=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, maluong);
        preparedStatement.setString(2, manv);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static void main(String[] args) {
        controller_QLLuong a = new controller_QLLuong();
    }
}
