package frontend.Excel;

import backend.Data;
import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExcelPanel extends JPanel {
    TablePanel table;
    TableData tableData;

    public ExcelPanel(Data data) {
        setLayout(new BorderLayout());
        tableData = new TableData(data);
        table = new TablePanel(tableData.getTableData(), tableData.getHeaders());
        add(new JScrollPane(table));
    }

    public void revalidatePanel(){
        remove(table);
        revalidate();
        table = new TablePanel(tableData.getTableData(), tableData.getHeaders());
        add(new JScrollPane(table));
        revalidate();
        repaint();
    }

    public void updateData(List<Student> students, List<Course> courses, List<Program> programs, Data data) {
        tableData.updateData(students, courses, programs, data);
    }


    private static class TableData {
        private Object[][] tableData;
        private String[] headers;
        private List<Course> courseFilterList;
        public List<Student> studentFilterList;

        public TableData(Data data) {
            studentFilterList = new ArrayList<>();
            courseFilterList = new ArrayList<>();
            applyFilter(data);
        }

        private void applyFilter(Data data) {
            if (studentFilterList.isEmpty() && courseFilterList.isEmpty())
            {
                setTableData(data.getStudentList(), data.getCourseList());
            }
            else if (courseFilterList.isEmpty())
            {
                setTableData(studentFilterList, data.getCourseList());
            }
            else if (studentFilterList.isEmpty()) {
                setTableData(data.getStudentList(), courseFilterList);
            }
            else setTableData(studentFilterList, courseFilterList);
        }

        private void setTableData(List<Student> students, List<Course> courses) {
            int headersSize = 4 + courses.size();
            headers = new String[headersSize];
            headers[0] = "Nom";
            headers[1] = "Prénom";
            headers[2] = "Identifiant";
            headers[3] = "Programme";
            for (int i = 4; i < headersSize; i++) {
                headers[i] = courses.get(i - 4).getId()+" "+courses.get(i - 4).getName();
            }

            tableData = new Object[students.size()][headersSize];
            for (int i = 0; i < students.size(); i++) {
                tableData[i][0] = students.get(i).getName();
                tableData[i][1] = students.get(i).getSurname();
                tableData[i][2] = students.get(i).getStudentId();
                tableData[i][3] = students.get(i).getProgramId();

                List<Grade> grades = students.get(i).getGradeList();
                for (Course course : courses) {
                    for (Grade grade : grades) {
                        if (course.getId().equals(grade.getCourse().getId())) {
                            int index = courses.indexOf(course) + 4;
                            if (grade.getGrade() == -1) {
                                tableData[i][index] = "ABI";
                            } else tableData[i][index] = grade.getGrade();
                        }
                    }
                }
            }
        }

        public Object[][] getTableData() {
            return tableData;
        }

        public String[] getHeaders() {
            return headers;
        }

        public void updateData(List<Student> students, List<Course> courses, List<Program> programs, Data data) {
            setStudentFilterList(students, data);
            setProgramFilterList(programs, data);
            setCourseFilterList(courses, data);

            removeDuplicatesCourses();
            applyFilter(data);
        }

        private void setStudentFilterList(List<Student> students, Data data) {
            studentFilterList = students;
        }

        private void setProgramFilterList(List<Program> programs, Data data) {
            for (Program program : programs) {
                this.courseFilterList.addAll(program.getSimpleCourseList());
                for (OptionCourse option : program.getOptionCourseList()) {
                    this.courseFilterList.addAll(option.getOptionList());
                }
                for (CompositeCourse composite : program.getCompositeCoursesList()) {
                    this.courseFilterList.addAll(composite.getCompositeList());
                }
            }
        }

        private void setCourseFilterList(List<Course> courses, Data data) {
            this.courseFilterList.addAll(courses);
        }

        private void removeDuplicatesCourses() {
            List<Course> newList = new ArrayList<>();
            for (Course course : courseFilterList) {
                if (! newList.contains(course)) {
                    newList.add(course);
                }
            }
            courseFilterList = newList;
        }
    }




    private static class TablePanel extends JTable {
        private Object[][] data;
        private String[] headers;
        private JTable table;

        public TablePanel(Object[][] data, String[] headers) {
            super(data, headers);
            this.data = data;
            this.headers = headers;
            this.table = this;

            getTableHeader().setReorderingAllowed(false);
            getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
            setRowSelectionAllowed(false);
            setFont(new Font("Arial", Font.PLAIN, 15));
            setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            autoResizeColumn();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column < 4) {
                return false;
            }
            else {
                return true;
            }
        }

        private void autoResizeColumn() {
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            for (int i = 4; i < headers.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(250);
            }
        }

    }

}
