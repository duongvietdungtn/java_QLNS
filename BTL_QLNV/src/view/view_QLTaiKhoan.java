    package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class view_QLTaiKhoan extends JFrame {
    JTextField id_text, username_text, password_text, role_text;
    JButton back, add, edit, delete, clear, sort1, sort2;
    JTable table;
//    private controller_QLTaiKhoan controller;

    public view_QLTaiKhoan() {
        // Initialize components
        id_text = new JTextField(10);
        username_text = new JTextField(10);
        password_text = new JTextField(10);
        role_text = new JTextField(10);

        back = new JButton("Back");
        add = new JButton("Add");
        edit = new JButton("Edit");
        delete = new JButton("Delete");
        clear = new JButton("Clear");

//        this.controller = controller;

        table = new JTable();

        // Set layout and add components
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 2, 6, 8);

        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        inputPanel.add(id_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx++;
        inputPanel.add(username_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        inputPanel.add(password_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx++;
        inputPanel.add(role_text, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(back);
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(clear);

        // Add JSplitPane to the center
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, new JScrollPane(table));
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set events for buttons
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.handleClearButtonClick();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.handleBackButtonClick();
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.handleAddButtonClick();
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.handleEditButtonClick();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                controller.handleDeleteButtonClick();
            }
        });

        // Display data when a row in the table is clicked
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                controller.displayData();
            }
        });

        setTitle("Account Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args){
        view_QLTaiKhoan nhanvien = new view_QLTaiKhoan();
    }
    
}
