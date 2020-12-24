package frontend.Excel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;

public class NewCourseFrame extends JFrame {
    List<NewCourse> panels;

    public NewCourseFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,800);
        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        panels = new ArrayList<>();

        mainPanel.setBorder(new TitledBorder("main"));
        JButton button = new JButton("+");
        mainPanel.add(button);
        button.addActionListener(e1 -> {
            NewCourse newCourse = new NewCourse();
            mainPanel.add(newCourse);
            mainPanel.revalidate();
            panels.add(newCourse);
        });
        
        JButton validate = new JButton("Valider");
        mainPanel.add(validate);
        validate.addActionListener(e2 -> {
            System.out.println(panels.get(0).textField.getText());
        });

        getContentPane().add(new JScrollPane(mainPanel));
        setVisible(true);
    }


    public static void main(String[] args) {
        new NewCourseFrame();
    }
}


