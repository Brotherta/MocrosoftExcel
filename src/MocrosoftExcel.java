import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import backend.xml.XmlReader;


import javax.swing.text.html.Option;
import java.util.List;

public class MocrosoftExcel {
    public static void main(String[] args) {
        List<Course> courses = XmlReader.readCourse();
        Course course = courses.get(0);

        List<Student> students = XmlReader.readStudent(courses);
        Student student = students.get(0);
        List<Grade> grades = student.getGradeList();
        Grade grade = grades.get(0);
        // System.out.println(grade.getCourse().getId());
        // System.out.println(grade.getGrade());

        List<Program> programs = XmlReader.readProgram(courses);
        Program program = programs.get(0);
        System.out.println(program.getName());

        List<OptionCourse> courseOptionList = program.getOptionCourseList();
        OptionCourse option = courseOptionList.get(0);
        System.out.println(option.getName());

        List<SimpleCourse> optionNum = option.getOptionList();
        SimpleCourse option1 = optionNum.get(0);
        System.out.println(option1.getId());



    }
}
