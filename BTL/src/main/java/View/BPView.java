/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.BPControl;
import Model.BoPhan;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Admin
 */
public class BPView extends JFrame implements ActionListener{
    private JTextField txtmabp,txtbp;
    private JDateChooser dateCv;
    private JButton AddJButton,UpdaJButton,DeleJButton,ExJButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private BPControl control;
    private int selectedRow = -1;

    public BPView(BPControl control){
        this.setTitle("Bộ Phận");
        this.control=control;
        // Tạo và cấu hình panel cho các trường nhập liệu
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputFieldsPanel.add(new JLabel("Mã bộ phận"));
        txtmabp = new JTextField(15);
        inputFieldsPanel.add(txtmabp);
        inputFieldsPanel.add(new JLabel("Bộ phận"));
        txtbp = new JTextField(15);
        inputFieldsPanel.add(txtbp);
        inputFieldsPanel.add(new JLabel("Ngày thành lập"));
        dateCv = new JDateChooser();
        dateCv.setPreferredSize(new Dimension(120, 20));
        inputFieldsPanel.add(dateCv);
        // Tạo và cấu hình panel cho các nút
        JPanel inputButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon addIcon = new ImageIcon("C:\\Users\\Admin\\Documents\\Zalo Received Files\\QuanLyNhanSu\\src\\main\\java\\image\\add.png");
        AddJButton = new JButton("Thêm",addIcon);
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
                tr.setRowFilter(null); // Xóa bộ lọc hiện có
            }
        }
    });
        searchPanel.add(searchField);
        // Tạo bảng và scroll pane cho nó
        String[] columnNames = {"Mã  bộ phận", "Bộ phận","Ngày thành lập"};
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
                txtmabp.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtbp.setText(tableModel.getValueAt(selectedRow, 1).toString());                        
                String dateString = tableModel.getValueAt(selectedRow, 2).toString();
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                dateCv.setDate(date);
            } catch (ParseException ex) {
                ex.printStackTrace();               
            }
        }
    }
});
        updateTableData(control.layDanhSachBoPhan());
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
            String mabp = txtmabp.getText();
            String bophan = txtbp.getText();
            java.util.Date ngaythanhlap = dateCv.getDate();        
            control.xuLyThemBoPhan(mabp, bophan, ngaythanhlap);
            updateTableData(control.layDanhSachBoPhan());
            clearFields();
        }
        if (e.getSource() == UpdaJButton) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bộ phận để cập nhật", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                String mabp = txtmabp.getText();
                String bophan = txtbp.getText();
                java.util.Date ngaythanhlap = dateCv.getDate();
                control.xuLyCapNhatBoPhan(mabp, bophan,ngaythanhlap);
                clearFields();
                updateTableData(control.layDanhSachBoPhan()); 
            }
        }
        if (e.getSource() == DeleJButton) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bộ phận để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } 
            else {
                String mabp = txtmabp.getText();
                control.xuLyXoaBoPhan(mabp);
                updateTableData(control.layDanhSachBoPhan());
                clearFields();
            }
        }        
        if (e.getSource() == ExJButton) {
            control.xuLyXuatExcel();
        }
    }              
    private void clearFields(){
        txtmabp.setText("");
        txtbp.setText("");
        dateCv.setDate(null);
        selectedRow = -1;        
    }
    public void updateTableData(List<BoPhan> danhSachBoPhan) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);        
        for (BoPhan bp : danhSachBoPhan) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         String ngayThanhLapFormatted = dateFormat.format(bp.getNgaythanhlap());
    
    Object[] row = new Object[]{
        bp.getMabp(), 
        bp.getBophan(),
        ngayThanhLapFormatted 
    };
    tableModel.addRow(row);
}
}
}
