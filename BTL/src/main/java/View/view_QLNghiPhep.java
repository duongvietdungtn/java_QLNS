package View;

import Controller.controller_QLNghiPhep;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JDateChooser;

public class view_QLNghiPhep extends JFrame {
    JTextField manp_text, searchTextField;
    JButton back, add, edit, delete, clear, exportToExcel;
    JTable table;
    JComboBox<String> manvComboBox, chophepComboBox;
    JDateChooser ngaynghiChooser;
    private controller_QLNghiPhep controller;

    public view_QLNghiPhep(controller_QLNghiPhep controller) {
        manp_text = new JTextField(10);
        manvComboBox = new JComboBox<>();
        ngaynghiChooser = new JDateChooser();
        ngaynghiChooser.setDateFormatString("yyyy-MM-dd");
        chophepComboBox = new JComboBox<>(new String[]{"Có", "Không"});
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
        inputPanel.add(new JLabel("Mã Nghỉ Phép:"), gbc);
        gbc.gridx++;
        inputPanel.add(manp_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx++;
        inputPanel.add(manvComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Ngày Nghỉ:"), gbc);
        gbc.gridx++;
        inputPanel.add(ngaynghiChooser, gbc); 
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Cho Phép:"), gbc);
        gbc.gridx++;
        inputPanel.add(chophepComboBox, gbc);

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
        
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleSearchButtonClick();
            }
        });
        
        exportToExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleExcelButtonClick();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.displayData();
            }
        });
        
        back.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/back.png"));
        add.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/add.png"));
        edit.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/edit.png"));
        delete.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/delete.png"));
        clear.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/clear.png"));
        exportToExcel.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/excel.png"));

        setTitle("Quản lý Nghỉ Phép");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getManp_text() {
        return manp_text;
    }

    public JDateChooser getNgaynghiChooser() {
        return ngaynghiChooser;
    }

    public JComboBox<String> getChophepComboBox() {
        return chophepComboBox;
    }

    public JButton getBack() {
        return back;
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

    public JComboBox<String> getManvComboBox() {
        return manvComboBox;
    }
    
}
