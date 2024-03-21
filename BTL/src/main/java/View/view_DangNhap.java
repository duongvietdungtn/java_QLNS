package View;

import Controller.controller_DangNhap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class view_DangNhap extends JFrame {
    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnDangNhap;
    private controller_DangNhap controller;

    public view_DangNhap(controller_DangNhap controller) {
        setTitle("Đăng Nhập");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        this.controller = controller;

        // Tạo các thành phần giao diện
        JLabel lblTitle = new JLabel("Đăng Nhập");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(JLabel.CENTER); // Căn giữa theo chiều ngang

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnDangNhap = new JButton("Đăng Nhập");

        // Tạo panel để chứa các thành phần
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(txtUsername);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(txtPassword);

        // Sử dụng EmptyBorder để đặt khoảng cách trái và phải
        inputPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Sử dụng FlowLayout và căn giữa
        buttonPanel.add(btnDangNhap);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm mainPanel vào JFrame
        setLayout(new BorderLayout());
        add(mainPanel);
        
        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLoginButtonClick();
            }
        });
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnDangNhap() {
        return btnDangNhap;
    }
}
