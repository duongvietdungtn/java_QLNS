package Controller;

import View.view_DangNhap;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class controller_DangNhap {
    private view_DangNhap view;

    public controller_DangNhap() {
        view = new view_DangNhap(this);
        view.setVisible(true);
    }

    public void handleLoginButtonClick() {
        String username = view.getTxtUsername().getText();
        String password = new String(view.getTxtPassword().getPassword());

        if (kiemTraDangNhap(username, password)) {
            controller_TrangChu a = new controller_TrangChu();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Đăng nhập thất bại. Vui lòng kiểm tra lại!");
        }
    }

    public static boolean kiemTraDangNhap(String username, String password) {
        String query = "SELECT * FROM taikhoan WHERE username = ? AND password = ?";
        try (Connection conn = KetNoi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Nếu có dòng kết quả, tức là tài khoản hợp lệ
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
