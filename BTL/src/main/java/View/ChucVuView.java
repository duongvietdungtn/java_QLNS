/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.ChucVuControl;
import Model.ChucVu;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChucVuView extends JFrame implements ActionListener{
    private JTextField txtmacv,txtcv;
    private JButton AddJButton,UpdaJButton,DeleJButton,ExJButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private ChucVuControl control;
    private int selectedRow = -1;

    public ChucVuView(ChucVuControl control){
        this.setTitle("Chức vụ");
        this.control=control;
        // Tạo và cấu hình panel cho các trường nhập liệu
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputFieldsPanel.add(new JLabel("Mã chức vụ"));
        txtmacv = new JTextField(31);
        inputFieldsPanel.add(txtmacv);
        inputFieldsPanel.add(new JLabel("Chức vụ"));
        txtcv = new JTextField(31);
        inputFieldsPanel.add(txtcv);
        // Tạo và cấu hình panel cho các nút
        JPanel inputButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon addIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\add.png");
        AddJButton = new JButton("Thêm", addIcon);       
        AddJButton.setPreferredSize(new Dimension(190, 20));
        buttonPanel.add(AddJButton);
        ImageIcon updateIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\edit.png");
        UpdaJButton = new JButton("Sửa",updateIcon);
        UpdaJButton.setPreferredSize(new Dimension(190, 20));
        buttonPanel.add(UpdaJButton);
        ImageIcon deleIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\delete.png");
        DeleJButton = new JButton("Xoá",deleIcon);
        DeleJButton.setPreferredSize(new Dimension(190, 20));
        buttonPanel.add(DeleJButton);
        ImageIcon exceIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\excel.png");
        ExJButton = new JButton("Xuất Excel",exceIcon);
        ExJButton.setPreferredSize(new Dimension(190, 20));
        buttonPanel.add(ExJButton);
        inputButtonsPanel.add(AddJButton);    
        inputButtonsPanel.add(UpdaJButton);
        inputButtonsPanel.add(DeleJButton);
        inputButtonsPanel.add(ExJButton);
        // Tạo panel phía trên bao gồm các trường nhập liệu và các nút
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(inputFieldsPanel, BorderLayout.NORTH);
        topPanel.add(inputButtonsPanel, BorderLayout.CENTER);
        // Tạo search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        searchPanel.add(new JLabel("Tìm kiếm"));
        JTextField searchField = new JTextField("Nhập nội dung tìm kiếm", 20);
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            if (searchField.getText().equals("Nhập nội dung tìm kiếm")) {
                searchField.setText("");
                searchField.setForeground(Color.BLACK);
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
                table.setRowSorter(tr);
                tr.setRowFilter(null); 
            }   
        }
        @Override
        public void focusLost(FocusEvent e) {
            if (searchField.getText().isEmpty()) {
                searchField.setForeground(Color.GRAY);
                searchField.setText("Nhập nội dung tìm kiếm");                
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
                table.setRowSorter(tr);
                tr.setRowFilter(null); 
            }
        }
        });
        searchPanel.add(searchField);
        // Tạo bảng và scroll pane cho nó
        String[] columnNames = {"Mã chức vụ", "Chức vụ"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không sửa trực tiếp trong bảng
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        // Đăng ký  sự kiện các nút
        AddJButton.addActionListener(this);
        UpdaJButton.addActionListener(this);
        DeleJButton.addActionListener(this);
        ExJButton.addActionListener(this);
        searchField.addActionListener(this);
        // Phương thức tìm kiếm:
        searchField.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
            filter();
        }
        public void removeUpdate(DocumentEvent e) {
            filter();
        }
        public void insertUpdate(DocumentEvent e) {
            filter();
        }
        public void filter() {
            String filter = searchField.getText();
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
            table.setRowSorter(tr);
            tr.setRowFilter(RowFilter.regexFilter("(?i)" + filter));
        }
        });   
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtmacv.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtcv.setText(tableModel.getValueAt(selectedRow, 1).toString());
                }                 
            }           
        });
        updateTableData(control.layDanhSachChucVu());
        // Tạo một DefaultTableCellRenderer mới
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Đặt căn giữa
        // Căn giữa cho tất cả các cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Tạo panel chính và thêm tất cả các thành phần vào panel này
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        // Thiết lập panel chính làm nội dung của cửa sổ và hiển thị
        setContentPane(mainPanel);
        pack();
        }
        @Override
    public void actionPerformed(ActionEvent e) {       
        if(e.getSource() == AddJButton) {
            String macv = txtmacv.getText();
            String chucvu = txtcv.getText();
            control.xuLyThemChucVu(macv, chucvu);
            updateTableData(control.layDanhSachChucVu());
            clearFields();
        }
        if (e.getSource() == UpdaJButton) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                String macv= txtmacv.getText();
                String chucvu= txtcv.getText();           
                control.xuLyCapNhatChucVu(macv, chucvu);
                clearFields();
                updateTableData(control.layDanhSachChucVu()); 
            }
        }
        if (e.getSource() == DeleJButton) {
            if (selectedRow == -1) {
                 JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                String macv = txtmacv.getText();
                control.xuLyXoaChucVu(macv);
                updateTableData(control.layDanhSachChucVu());
                clearFields();
            }
        }        
        if (e.getSource() == ExJButton) {
            control.xuLyXuatExcel();
            }
        }              
    private void clearFields(){
        txtmacv.setText("");
        txtcv.setText("");
        selectedRow = -1;       
    }
    public void updateTableData(List<ChucVu> danhSachChucVu) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (ChucVu cv : danhSachChucVu) {
        Object[] row = new Object[]{
            cv.getMacv(), 
            cv.getChucvu()
        };
        tableModel.addRow(row);
        }
    }
}
