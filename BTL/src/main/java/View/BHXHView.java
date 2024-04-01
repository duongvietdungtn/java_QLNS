/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Controller.BHXHControl;
import Model.BHXH;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.List;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class BHXHView extends JFrame implements ActionListener{
    private JTextField txtmabh,txtmanv;
    private JComboBox txtloaibh;
    private JDateChooser dateBG,dateED;
    private JButton AddJButton,UpdaJButton,DeleJButton,ExJButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private BHXHControl control;
    private int selectedRow = -1;

    public BHXHView(BHXHControl control){
        setTitle("Bảo hiểm");
        this.control=control;
        // Tạo và cấu hình panel cho các trường nhập liệu
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputFieldsPanel.add(new JLabel("Mã bảo hiểm"));
        txtmabh = new JTextField(15);
        inputFieldsPanel.add(txtmabh);
        inputFieldsPanel.add(new JLabel("Mã nhân viên"));
        txtmanv = new JTextField(15);
        inputFieldsPanel.add(txtmanv);
        txtloaibh = new JComboBox<>();
        txtloaibh.addItem("Y tế");
        txtloaibh.addItem("Nhân thọ");
        txtloaibh.setPreferredSize(new Dimension(120, 20));
        inputFieldsPanel.add(new JLabel("Loại bảo hiểm"));
        inputFieldsPanel.add(txtloaibh);
        inputFieldsPanel.add(txtloaibh);
        inputFieldsPanel.add(new JLabel("Ngày bắt đầu"));
        dateBG = new JDateChooser();
        dateBG.setPreferredSize(new Dimension(120, 20));
        inputFieldsPanel.add(dateBG);
        inputFieldsPanel.add(new JLabel("Ngày kết thúc"));
        dateED = new JDateChooser();
        dateED.setPreferredSize(new Dimension(120, 20));
        inputFieldsPanel.add(dateED);
        // Tạo và cấu hình panel cho các nút
        JPanel inputButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon addIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\add.png");
        AddJButton = new JButton("Thêm",addIcon);
        AddJButton.setPreferredSize(new Dimension(290, 20));
        buttonPanel.add(AddJButton);
        ImageIcon updateIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\edit.png");
        UpdaJButton = new JButton("Sửa",updateIcon);
        UpdaJButton.setPreferredSize(new Dimension(290, 20));
        buttonPanel.add(UpdaJButton);
        ImageIcon deleIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\delete.png");
        DeleJButton = new JButton("Xoá",deleIcon);
        DeleJButton.setPreferredSize(new Dimension(290, 20));
        buttonPanel.add(DeleJButton);
        ImageIcon exceIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\excel.png");
        ExJButton = new JButton("Xuất Excel",exceIcon);
        ExJButton.setPreferredSize(new Dimension(290, 20));
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
        String[] columnNames = {"Mã  bảo hiểm", "Mã nhân viên","Loại bảo hiểm","Ngày bắt đầu","Ngày kết thúc"};
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
                    txtmabh.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtmanv.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    String selectedLoaiBH = (String) tableModel.getValueAt(selectedRow, 2);
                    txtloaibh.setSelectedItem(selectedLoaiBH);              
                    String dateString = tableModel.getValueAt(selectedRow, 3).toString();
                    try {
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                    dateBG.setDate(date);
                    } catch (ParseException ex) {
                    ex.printStackTrace();               
                    }
                    String dateStrings = tableModel.getValueAt(selectedRow, 4).toString();
                    try {
                    Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(dateStrings);
                    dateED.setDate(date2);
                    } catch (ParseException ex) {
                    ex.printStackTrace();               
            }
            }                 
        }           
    });
        updateTableData(control.layDanhSachBHXH());
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
        String mabaohiem = txtmabh.getText();
        String manv= txtmanv.getText();
        String loaibaohiem = (String) txtloaibh.getSelectedItem();
        java.util.Date ngaybatdau = dateBG.getDate();
        java.util.Date ngayketthuc = dateED.getDate(); 
        if (ngaybatdau != null && ngayketthuc != null && !ngayketthuc.after(ngaybatdau)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng thực thi phương thức
        }
        control.xuLyThemBHXH(mabaohiem, manv, loaibaohiem,ngaybatdau,ngayketthuc);
        updateTableData(control.layDanhSachBHXH());
        clearFields();
    }
    if (e.getSource() == UpdaJButton) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã bảo hiểm để cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE);
    } else {
        String mabaohiem = txtmabh.getText();
        String manv= txtmanv.getText();
        String loaibaohiem = (String) txtloaibh.getSelectedItem();
        java.util.Date ngaybatdau = dateBG.getDate();
        java.util.Date ngayketthuc = dateED.getDate(); 
         if (ngaybatdau != null && ngayketthuc != null && !ngayketthuc.after(ngaybatdau)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng thực thi phương thức
        }
        control.xuLyCapNhatBHXH(mabaohiem, manv, loaibaohiem,ngaybatdau,ngayketthuc);
        clearFields();
        updateTableData(control.layDanhSachBHXH()); 
    }
}

    if (e.getSource() == DeleJButton) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã bảo hiểm để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            String mabaohiem = txtmabh.getText();
            control.xuLyXoaBHXH(mabaohiem);
            updateTableData(control.layDanhSachBHXH());
            clearFields();
        }
    }        
    if (e.getSource() == ExJButton) {
        control.xuLyXuatExcel();
    }
}              
    private void clearFields(){
        txtmabh.setText("");
        txtmanv.setText("");
        txtloaibh.setSelectedIndex(0);
        dateBG.setDate(null);
        dateED.setDate(null);
        selectedRow = -1;
        
    }
    public void updateTableData(List<BHXH> danhSachBHXH) {
    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
    tableModel.setRowCount(0);
    for (BHXH bh : danhSachBHXH) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String ngayBatDauFormatted = dateFormat.format(bh.getNgaybatdau());
            String ngayKetThucFormatted = dateFormat.format(bh.getNgayketthuc());
            Object[] row = new Object[]{          
                bh.getMabaohiem(),
                bh.getManv(),
                bh.getLoaibaohiem(),
                ngayBatDauFormatted,
                ngayKetThucFormatted
            
        };
    tableModel.addRow(row);
    }
}
    
}
