package backend.xml;

import backend.Data;
import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.html.Option;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestXML {
    public static void main(String[] args) {
//        SimpleCourse c1 = new SimpleCourse("66666", "salut", 9);
//        SimpleCourse c2 = new SimpleCourse("99999", "ça va ?", 10);
//        List<SimpleCourse> l = new ArrayList<>();
//        l.add(c1);
//        l.add(c2);
//
//        Data save = new Data();
//
//        save.addCourse(c1);
//        save.addCourse(c2);
//
//        List<Grade> grades = new ArrayList<>();
//        Grade g1 = new Grade(0.0, c1);
//        Grade g2 = new Grade(19.15, c2);
//        grades.add(g1);
//        grades.add(g2);
//
//        Student student = new Student("antoine", "Vidal", "21702760", "pas de prog", grades);
//        student.getGradeList().get(0).setGrade(0.0);
//        save.addStudent(student);
//
//        List<OptionCourse> options = new ArrayList<>();
//        OptionCourse option1 = new OptionCourse("012345", "incroyable 1", l);
//        OptionCourse option2 = new OptionCourse("123456z", "incroyable 2", l);
//        options.add(option1);
//        options.add(option2);
//
//        List<CompositeCourse> composites = new ArrayList<>();
//
//        Program program = new Program("un super prog", "vraimenty top", l, options, composites);
//
//        save.addProgram(program);
//
//
//        save.doSave();
        XmlWriter.removeStudent("21232189");
        XmlWriter.modifyGrade("21123983", "SLUIN501", "20.0");

    }
}
