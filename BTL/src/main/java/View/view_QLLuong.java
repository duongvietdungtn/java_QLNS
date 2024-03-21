package View;

import Controller.controller_QLLuong;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class view_QLLuong extends JFrame {
    JTextField mal_text, luongCung_text, thuong_text, phat_text, tongLuong_text, searchTextField;
    JButton back, add, edit, delete, clear, exportToExcel;
    JTable table;
    JComboBox<String> manvComboBox;
    private controller_QLLuong controller;

    public view_QLLuong(controller_QLLuong controller) {
        mal_text = new JTextField(10);
        luongCung_text = new JTextField(10);
        thuong_text = new JTextField(10);
        phat_text = new JTextField(10);
        tongLuong_text = new JTextField(10);
        manvComboBox = new JComboBox<>();
        searchTextField = new JTextField(10);
        
        tongLuong_text.setEditable(false);

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
        inputPanel.add(new JLabel("Mã Lương:"), gbc);
        gbc.gridx++;
        inputPanel.add(mal_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx++;
        inputPanel.add(manvComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Lương cứng:"), gbc);
        gbc.gridx++;
        inputPanel.add(luongCung_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Thưởng:"), gbc);
        gbc.gridx++;
        inputPanel.add(thuong_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Phạt:"), gbc);
        gbc.gridx++;
        inputPanel.add(phat_text, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Tổng lương:"), gbc);
        gbc.gridx++;
        inputPanel.add(tongLuong_text, gbc);
        
        back.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/back.png"));
        add.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/add.png"));
        edit.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/edit.png"));
        delete.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/delete.png"));
        clear.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/clear.png"));
        exportToExcel.setIcon(new ImageIcon("C:/Users/hieup/Downloads/BTL/src/main/java/Image/excel.png"));

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

        setTitle("Quản lý Lương");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getMal_text() {
        return mal_text;
    }

    public JTextField getLuongCung_text() {
        return luongCung_text;
    }

    public JTextField getThuong_text() {
        return thuong_text;
    }

    public JTextField getPhat_text() {
        return phat_text;
    }

    public JTextField getTongLuong_text() {
        return tongLuong_text;
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
