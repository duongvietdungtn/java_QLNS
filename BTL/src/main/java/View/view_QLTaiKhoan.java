package View;

import Controller.controller_QLTaiKhoan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class view_QLTaiKhoan extends JFrame {
    JTextField id_text, username_text, password_text, searchTextField;
    JButton back, add, edit, delete, clear, exportToExcel;
    JTable table;
    JComboBox<String> roleComboBox;
    private controller_QLTaiKhoan controller;

    public view_QLTaiKhoan(controller_QLTaiKhoan controller) {
        id_text = new JTextField(10);
        username_text = new JTextField(10);
        password_text = new JTextField(10);
        roleComboBox = new JComboBox<>(new String[]{"User", "Admin"});
        searchTextField = new JTextField(10);
        
        back = new JButton("Back");
        add = new JButton("Add");
        edit = new JButton("Edit");
        delete = new JButton("Delete");
        clear = new JButton("Clear");
        exportToExcel = new JButton("Excel");

        this.controller = controller;

        table = new JTable();

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tìm kiếm:"), gbc);
        gbc.gridx++;
        inputPanel.add(searchTextField, gbc);       
        gbc.gridx = 0;
        gbc.gridy++;
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
        inputPanel.add(roleComboBox, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(back);
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(clear);
        buttonPanel.add(exportToExcel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, new JScrollPane(table));
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleClearButtonClick();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleBackButtonClick();
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleAddButtonClick();
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleEditButtonClick();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleDeleteButtonClick();
            }
        });
        
        exportToExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleExcelButtonClick();
            }
        });
        
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleSearchButtonClick();
            }
        });
        
        back.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/back.png"));
        add.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/add.png"));
        edit.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/edit.png"));
        delete.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/delete.png"));
        clear.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/clear.png"));
        exportToExcel.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/excel.png"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.displayData();
            }
        });

        setTitle("Account Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getId_text() {
        return id_text;
    }

    public JTextField getUsername_text() {
        return username_text;
    }

    public JTextField getPassword_text() {
        return password_text;
    }

    public String getSelectedRole() {
        return roleComboBox.getSelectedItem().toString();
   }

    public JButton getBack() {
        return back;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getEdit() {
        return edit;
    }

    public JButton getDelete() {
        return delete;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JButton getClear() {
        return clear;
    }

    public JTable getTable() {
        return table;
    }
    
}
