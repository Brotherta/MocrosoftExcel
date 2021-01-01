package backend;

import backend.course.Course;
import backend.program.Program;
import backend.student.Student;
import backend.xml.XmlReader;
import backend.xml.XmlWriter;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    protected List<Student> studentList;
    protected List<Course> courseList;
    protected List<Program> programList;
    protected Save save;
    protected String path;

    public Data(String path) {
        this.path = path;
        try {
            XmlReader reader = new XmlReader(path);
            courseList = reader.readCourse();
            studentList = reader.readStudent(courseList);
            programList = reader.readProgram(courseList);
        } catch (Exception e) {
            courseList = new ArrayList<>();
            studentList = new ArrayList<>();
            programList = new ArrayList<>();
        }
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



    public void doSave(String path) {
        save.doSave(path);
        this.path = path;
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

        public void doSave(String path)  {
            XmlWriter writer = new XmlWriter(path);
            writer.writeInXml(buffer);
            buffer.clear();
        }

    }


}
