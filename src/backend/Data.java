package backend;

import backend.course.Course;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import backend.xml.XmlReader;
import backend.xml.XmlWriter;

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
        save.addToAddBuffer(student);
    }

    public void addCourse (Course course) {
        courseList.add(course);
        save.addToAddBuffer(course);
    }

    public void addProgram(Program program) {
        programList.add(program);
        save.addToAddBuffer(program);
    }

    public void deleteStudent(Student student) {
        studentList.remove(student);
        save.addToDeleteBuffer(student.getStudentId());
    }

    public void modifyGrade(Student student, Grade grade) {
        studentList.set(getIndexStudent(student), student);
        save.addToModifyBuffer(student, grade);
    }

    private int getIndexStudent(Student student) {
        return studentList.indexOf(student);
    }

    public void doSave(String path) {
        save.doSave(path);
        this.path = path;
    }

    private static class Save {
        protected List<Object> addBuffer;
        protected List<String> deleteBuffer;
        protected List<Object> modifyBuffer;

        public Save() {
            addBuffer = new ArrayList<>();
            deleteBuffer = new ArrayList<>();
            modifyBuffer = new ArrayList<>();
        }

        public void addToAddBuffer(Object object) {
            addBuffer.add(object);
        }

        public void addToDeleteBuffer(String id) {
            deleteBuffer.add(id);
        }

        public void addToModifyBuffer(Student student, Grade grade) {
            modifyBuffer.add(student);
            modifyBuffer.add(grade);
        }

        // Dans l'optique d'un bouton Undo, on aurait pu récupérer le buffer.
        public List<Object> getAddBuffer(){
            return addBuffer;
        }

        public void doSave(String path)  {
            XmlWriter writer = new XmlWriter(path);
            writer.writeInXml(addBuffer);
            addBuffer.clear();
            writer.removeStudent(deleteBuffer);
            deleteBuffer.clear();
            for(int i = 0; i< modifyBuffer.size(); i+=2) {
                Student student = (Student) modifyBuffer.get(i);
                Grade grade = (Grade) modifyBuffer.get(i+1);
                writer.modifyGrade(student.getStudentId(), grade.getCourse().getId(), String.valueOf(grade.getGrade()));
            }
            writer.updateXml();
        }

    }


}
