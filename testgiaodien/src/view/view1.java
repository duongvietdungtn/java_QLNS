package view;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class view1 extends JFrame {
    JFrame frame;

    public view1() {
        frame = new JFrame();

        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
        JButton b6 = new JButton("6");

        // Tạo 3 FlowLayout cho 3 hàng và đặt giữa
        FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout flowLayout2 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout flowLayout3 = new FlowLayout(FlowLayout.CENTER);

        // Gán layout cho frame
        frame.setLayout(new FlowLayout());

        // Thêm các button vào các hàng
        frame.setLayout(flowLayout1);
        frame.add(b1);
        frame.add(b2);

        frame.setLayout(flowLayout2);
        frame.add(b3);
        frame.add(b4);

        frame.setLayout(flowLayout3);
        frame.add(b5);
        frame.add(b6);

        frame.setSize(400, 250);
        frame.setVisible(true);
        frame.setTitle("Ví dụ FlowLayout trong Java Swing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        view1 v = new view1();
    }
}
