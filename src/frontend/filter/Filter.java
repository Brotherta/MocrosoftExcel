package frontend.filter;

import backend.Data;
import backend.course.Course;
import backend.program.Program;
import backend.student.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Filter extends JPanel{

    private final List<Student> studentsFilter;
    private final List<Program> programsFilter;
    private final List<Course> coursesFilter;


    public Filter() {
        super();

        studentsFilter = new ArrayList<>();
        programsFilter = new ArrayList<>();
        coursesFilter = new ArrayList<>();

    }
    public JButton addStudentFilter(Student student) {
        JButton newFilter = new JButton(student.getName()+" "+student.getStudentId());
        studentsFilter.add(student);
        return newFilter;
    }

    public JButton addCourseFilter(Course course) {
        JButton newFilter = new JButton(course.getName()+ " "+course.getId());
        coursesFilter.add(course);
        return newFilter;
    }

    public JButton addProgramFilter(Program program) {
        JButton newFilter = new JButton(program.getName()+ " "+program.getId());
        programsFilter.add(program);
        return newFilter;
    }

    public void removeStudentFilter(Student student) {
        studentsFilter.remove(student);
    }

    public void removeCourseFilter(Course course) {
        coursesFilter.remove(course);
    }

    public void removeProgramFilter(Program program) {
        programsFilter.remove(program);
    }

    public List<Student> getStudentsFilter() {
        return studentsFilter;
    }

    public List<Program> getProgramsFilter() {
        return programsFilter;
    }

    public List<Course> getCoursesFilter() {
        return coursesFilter;
    }

}
