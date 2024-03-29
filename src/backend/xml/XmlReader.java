package backend.xml;

import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import frontend.utils.Utils;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.List;



public class XmlReader {
    private final Element root;


    public XmlReader(String path) {
        XmlBny reader = new XmlBny(path, false);
        this.root = reader.getRoot();
    }

    public List<Student> readStudent(List<Course> courses) {
        List<Student> students = new ArrayList<>();

        List<Element> studentList = XmlBny.getChildren(root, "student");
        for (Element xmlStudent : studentList) {
            String name = xmlStudent.getElementsByTagName("name").item(0).getTextContent();
            String id = xmlStudent.getElementsByTagName("identifier").item(0).getTextContent();
            String surname = xmlStudent.getElementsByTagName("surname").item(0).getTextContent();
            String programId = xmlStudent.getElementsByTagName("program").item(0).getTextContent();
            List<Grade> grades = new ArrayList<>();


            List<Element> gradeList = XmlBny.getChildren(xmlStudent, "grade");
            for (Element xmlGrade : gradeList) {
                String gradeId = xmlGrade.getElementsByTagName("item").item(0).getTextContent();
                String value = xmlGrade.getElementsByTagName("value").item(0).getTextContent();
                if (value.equals("ABI")) {
                    value = "-1";
                }

                Course course = Utils.getCourseById(gradeId, courses);
                Grade grade = new Grade(Double.parseDouble(value), course);
                grades.add(grade);
            }
            Student student = new Student(name, surname, id, programId, grades);
            students.add(student);
        }

        return students;
    }

    public List<Course> readCourse() {
        List<Course> courses = new ArrayList<>();

        List<Element> courseList = XmlBny.getChildren(root, "course");
        for (Object o : courseList) {
            Element course = (Element) o;

            String name = course.getElementsByTagName("name").item(0).getTextContent();
            String id = course.getElementsByTagName("identifier").item(0).getTextContent();
            String credits = course.getElementsByTagName("credits").item(0).getTextContent();

            Course courseObject = new SimpleCourse(id, name, Integer.parseInt(credits));
            courses.add(courseObject);
        }
        return courses;
    }


    public List<Program> readProgram(List<Course> courseList)  {
        List<Program> programs = new ArrayList<>();

        List<Element> xmlProgramList = XmlBny.getChildren(root, "program");
        for (Object o1 : xmlProgramList) {
            List<SimpleCourse> simples = new ArrayList<>();
            List<OptionCourse> options = new ArrayList<>();
            List<CompositeCourse> composites = new ArrayList<>();

            Element xmlProgram = (Element) o1;

            String ProgramName = xmlProgram.getElementsByTagName("name").item(0).getTextContent();
            String ProgramId = xmlProgram.getElementsByTagName("identifier").item(0).getTextContent();

            // SIMPLES COURSES

            addCoursesToList(xmlProgram, courseList, simples); // Ajoute les cours dans simples.

            // OPTIONS COURSES

            List<Element> xmlOptionList = XmlBny.getChildren(xmlProgram, "option");
            for (Object o2 : xmlOptionList) {
                Element xmlOption = (Element) o2;
                List<SimpleCourse> optionItems = new ArrayList<>();

                String optionName = xmlOption.getElementsByTagName("name").item(0).getTextContent();
                String optionId = xmlOption.getElementsByTagName("identifier").item(0).getTextContent();

                addCoursesToList(xmlOption, courseList, optionItems);

                OptionCourse option = new OptionCourse(optionId, optionName, optionItems);
                options.add(option);
            }

            // COMPOSITES COURSES

            List<Element> xmlCompositeList = XmlBny.getChildren(xmlProgram, "composite");
            for (Object o3 : xmlCompositeList) {
                Element xmlComposite = (Element) o3;
                List<SimpleCourse> compositeItems = new ArrayList<>();

                String compositeName = xmlComposite.getElementsByTagName("name").item(0).getTextContent();
                String compositeId = xmlComposite.getElementsByTagName("identifier").item(0).getTextContent();

                addCoursesToList(xmlComposite, courseList, compositeItems);

                CompositeCourse composite = new CompositeCourse(compositeId, compositeName, compositeItems);
                composites.add(composite);
            }

            Program program = new Program(ProgramId, ProgramName, simples, options, composites);
            programs.add(program);
        }
        return programs;
    }

    private static void addCoursesToList(Element xmlElement, List<Course> courseList, List<SimpleCourse> listToAdd) {
        List<Element> xmlCourseList = XmlBny.getChildren(xmlElement, "item");
        for (Object o : xmlCourseList) {
            Element xmlSimpleCourse = (Element) o;
            String simpleCourseId = xmlSimpleCourse.getTextContent();

            SimpleCourse simpleCourse = (SimpleCourse) Utils.getCourseById(simpleCourseId, courseList);
            listToAdd.add(simpleCourse);
        }
    }
}
