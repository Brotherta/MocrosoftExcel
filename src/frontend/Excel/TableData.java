package frontend.Excel;

import backend.Data;
import backend.course.Course;
import backend.student.Grade;
import backend.student.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableData {
    private Object[][] tableData;
    private String[] headers;
    protected List<String> gradeIdList;
    public List<NewFilter> filters;
    public List<Student> studentFilterList;

    public TableData(Data data) {
        List<Student> students = data.getStudentList();
        filters = new ArrayList<>();
        gradeIdList = new ArrayList<>();
        studentFilterList = new ArrayList<>();

        for (Student student : students) {
            List<Grade> grades = student.getGradeList();
            for(Grade grade: grades) {
                String gradeId = grade.getCourse().getId();
                if (!gradeIdList.contains(gradeId)) {
                    gradeIdList.add(gradeId);
                }
            }
        }

        int headersSize = 4+gradeIdList.size();
        headers = new String[headersSize];
        headers[0] = "Nom"; headers[1] = "Prénom"; headers[2] = "Identifiant"; headers[3] = "Programme";
        for (int i = 4; i < headersSize; i++) {
            headers[i] = gradeIdList.get(i-4);
        }

        tableData = new Object[students.size()][headersSize];
        for (int i = 0; i <students.size(); i++) {
            tableData[i][0] = students.get(i).getName();
            tableData[i][1] = students.get(i).getSurname();
            tableData[i][2] = students.get(i).getStudentId();
            tableData[i][3] = students.get(i).getProgramId();

            List<Grade> grades = students.get(i).getGradeList();
            for (Grade grade : grades) {
                String gradeId = grade.getCourse().getId();
                int index = gradeIdList.indexOf(gradeId);
                tableData[i][index+4] = grade.getGrade();
            }
        }
    }

    public void addFilters(NewFilter filter) {
        filters.add(filter);
    }

    public void addStudent(Student student) {
        studentFilterList.add(student);
        applyFilter();
    }

    private void applyFilter() {
        gradeIdList = new ArrayList<>();

        for (Student student : studentFilterList) {
            List<Grade> grades = student.getGradeList();
            for(Grade grade: grades) {
                String gradeId = grade.getCourse().getId();
                if (!gradeIdList.contains(gradeId)) {
                    gradeIdList.add(gradeId);
                }
            }
        }

        int headersSize = 4+gradeIdList.size();
        headers = new String[headersSize];
        headers[0] = "Nom"; headers[1] = "Prénom"; headers[2] = "Identifiant"; headers[3] = "Programme";
        for (int i = 4; i < headersSize; i++) {
            headers[i] = gradeIdList.get(i-4);
        }

        tableData = new Object[studentFilterList.size()][headersSize];
        for (int i = 0; i < studentFilterList.size(); i++) {
            System.out.println(studentFilterList.get(i).getName());
            tableData[i][0] = studentFilterList.get(i).getName();
            tableData[i][1] = studentFilterList.get(i).getSurname();
            tableData[i][2] = studentFilterList.get(i).getStudentId();
            tableData[i][3] = studentFilterList.get(i).getProgramId();

            List<Grade> grades = studentFilterList.get(i).getGradeList();
            for (Grade grade : grades) {
                String gradeId = grade.getCourse().getId();
                int index = gradeIdList.indexOf(gradeId);
                tableData[i][index+4] = grade.getGrade();
            }
        }

    }

    public Object[][] getTableData() {
        return tableData;
    }

    public List<String> getGradeIdList() {
        return gradeIdList;
    }

    public List<Student> getStudentFilterList() {
        return new ArrayList<>(studentFilterList);
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setStudentFilterList(List<Student> students) {
        studentFilterList = students;
    }
}
