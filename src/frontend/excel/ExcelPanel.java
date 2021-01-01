package frontend.excel;

import backend.Data;
import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import frontend.utils.ColorCellRenderer;
import frontend.utils.Couple;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelPanel extends JPanel {
    TablePanel table;
    TableData tableData;

    public ExcelPanel(Data data, boolean editable) {
        setLayout(new BorderLayout());
        tableData = new TableData(data);
        table = new TablePanel(tableData.getTableData(), tableData.getHeaders(), tableData.getNotEditableCell(), editable, data);
        add(new JScrollPane(table));
    }

    public void revalidatePanel(Data data, boolean editable){
        remove(table);
        revalidate();
        table = new TablePanel(tableData.getTableData(), tableData.getHeaders(), tableData.getNotEditableCell(), editable, data);
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
        private List<Couple> notEditableCell;
        private List<Course> courseFilterList;
        public List<Student> studentFilterList;

        public TableData(Data data) {
            studentFilterList = new ArrayList<>();
            courseFilterList = new ArrayList<>();
            notEditableCell = new ArrayList<>();
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
            notEditableCell = new ArrayList<>();

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
                Student currentStudent = students.get(i);

                tableData[i][0] = currentStudent.getName();
                tableData[i][1] = currentStudent.getSurname();
                tableData[i][2] = currentStudent.getStudentId();
                tableData[i][3] = currentStudent.getProgramId();

                List<Grade> grades = currentStudent.getGradeList();
                for (Course course : courses) {
                    boolean modified = false;
                    int index = courses.indexOf(course) + 4;
                    for (Grade grade : grades) {
                        if (course.getId().equals(grade.getCourse().getId())) {

                            if (grade.getGrade() == -1) {
                                tableData[i][index] = "ABI";
                            }
                            else if (grade.getGrade() == -2) {
                                tableData[i][index] = "";
                            }
                            else tableData[i][index] = grade.getGrade();
                            modified = true;
                        }
                    }
                    if(! modified) {
                        notEditableCell.add(new Couple(i,index));
                        tableData[i][index] = " ";//new Color(47, 47, 47);
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

        public List<Couple> getNotEditableCell() {
            return notEditableCell;
        }

        public void updateData(List<Student> students, List<Course> courses, List<Program> programs, Data data) {
            setStudentFilterList(students);
            setProgramFilterList(programs);
            setCourseFilterList(courses);

            removeDuplicatesCourses();
            applyFilter(data);
        }

        private void setStudentFilterList(List<Student> students) {
            studentFilterList = students;
        }

        private void setProgramFilterList(List<Program> programs) {
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

        private void setCourseFilterList(List<Course> courses) {
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




    private static class TablePanel extends JTable implements TableModelListener {
        private final String[] headers;
        private final JTable table;
        private final Data data;
        private final List<Couple> notEditableCellList;
        private final boolean editable;

        public TablePanel(Object[][] tableData, String[] headers, List<Couple> notEditableCellList, boolean editable, Data data) {
            super(tableData, headers);
            this.headers = headers;
            this.table = this;
            this.data = data;
            this.notEditableCellList = notEditableCellList;
            this.editable = editable;

            getTableHeader().setReorderingAllowed(false);
            getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
            setRowSelectionAllowed(true);
            setFont(new Font("Arial", Font.PLAIN, 15));
            setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            autoResizeColumn();

            setDefaultRenderer(Object.class, new ColorCellRenderer());


            getModel().addTableModelListener(e -> {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);


                Object studentId = model.getValueAt(row, 2);
                Object cellData = model.getValueAt(row, column);
                Student student = getStudentById((String) studentId);
                assert student != null;
                Grade grade = student.getGradeById(columnName);

                if (verifyCellEntry(cellData)) {
                    for(Grade gra : student.getGradeList()) {
                        if(gra.getCourse().getId().equals(grade.getCourse().getId())) {
                            double res;
                            if (cellData.toString().isEmpty()) {
                                res = -2;
                            }
                            else if (cellData.toString().equals("ABI")) {
                                res = -1;
                            } else res = Double.parseDouble(cellData.toString());
                            gra.setGrade(res);
                        }
                    }
                    this.data.modifyGrade(student, grade);
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez rentrer une note valide !");
                    for(Grade gra : student.getGradeList()) {
                        if(gra.getCourse().getId().equals(grade.getCourse().getId())) {
                            double d = gra.getGrade();
                            String res;
                            if(d == -2) {
                                res = "";
                            } else if (d == -1) {
                                res = "ABI";
                            } else res = String.valueOf(d);
                            model.setValueAt(res ,row, column);
                            break;
                        }
                    }
                }
            });
        }

        private boolean verifyCellEntry(Object cellData) {
            String data = cellData.toString();
            if (data.equals("ABI") || data.isEmpty()) {
                return true;
            } else if(data.contains("d") || data.contains("f")) {
                return false;
            }
            else {
                try {
                    double d = Double.parseDouble(data);
                    return !(d < 0) && !(d > 20);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        private Student getStudentById(String studentId) {
            List<Student> students = data.getStudentList();
            for (Student student : students) {
                if (student.getStudentId().equals(studentId)) {
                    return student;
                }
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (editable) {
                Couple c = new Couple(row, column);
                if (column < 4 || notEditableCellList.contains(c)) {
                    System.out.println(c);
                    return false;
                }
                else {
                    return true;
                }
            } else return false;

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
