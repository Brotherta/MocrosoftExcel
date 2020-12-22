package backend;

import backend.course.Course;
import backend.program.Program;
import backend.student.Student;
import backend.xml.XmlReader;
import backend.xml.XmlWriter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class Data {
    protected List<Student> studentList;
    protected List<Course> courseList;
    protected List<Program> programList;
    protected Save save;

    public Data() {
        courseList = XmlReader.readCourse();
        studentList = XmlReader.readStudent(courseList);
        programList = XmlReader.readProgram(courseList);
        save = new Save();
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentList);
    }

    public List<Course> getCourseList() {
        return new ArrayList<>(courseList);
    }

    public List<Program> getProgramList() {
        return new ArrayList<>(programList);
    }

    public void addStudent (Student student) {
        studentList.add(student);
        save.addBuffer(student);
    }

    public void addCourse (Course course) {
        courseList.add(course);
        save.addBuffer(course);
    }

    public void addProgram(Program program) {
        programList.add(program);
        save.addBuffer(program);
    }



    public void doSave() {
        save.doSave();
    }

    private static class Save {
        protected List<Object> buffer;

        public Save() {
            buffer = new ArrayList<>();
        }

        public void addBuffer(Object object) {
            buffer.add(object);
        }

        public List<Object> getBuffer(){
            return buffer;
        }

        public void doSave() {
            XmlWriter.writeInXml(buffer);
            buffer.clear();
        }

    }


}
