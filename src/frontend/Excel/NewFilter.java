package frontend.Excel;

import backend.student.Student;

import javax.swing.*;

public class NewFilter extends JPanel{
    public JButton filterPanel;
    public Student student;

    public NewFilter(String filter, Student student) {
        super();
        filterPanel = new JButton(filter);
        this.student = student;
        add(filterPanel);
    }
}
