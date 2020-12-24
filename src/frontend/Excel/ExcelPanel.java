package frontend.Excel;

import backend.Data;
import backend.student.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExcelPanel extends JPanel {
    TableFrame table;
    TableData tableData;

    public ExcelPanel() {
        setLayout(new BorderLayout());
        Data data = new Data();
        tableData = new TableData(data);
        table = new TableFrame(tableData.getTableData(), tableData.getHeaders());
        add(new JScrollPane(table));
    }

    public void myUpdate(){
        remove(table);
        revalidate();
        table = new TableFrame(tableData.getTableData(), tableData.getHeaders());
        add(new JScrollPane(table));
        revalidate();
    }

    public void addStudent(Student student) {
        tableData.addStudent(student);
    }

    public List<Student> getStudents() {
        return tableData.getStudentFilterList();
    }

    public void setStudentListFilter(List<Student> students) {
        tableData.setStudentFilterList(students);
    }
}
