package backend.xml;

import backend.course.*;
import backend.program.Program;
import backend.student.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.naming.Name;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlWriter {

    public static void writeInXml(List<Object> buffer) {
        XmlBny writer = new XmlBny("data/data.xml");
        Document doc = writer.getDoc();
        Element root = writer.getRoot();

        for (Object o: buffer) {
            if (o instanceof Course) {
                Course course = (Course) o;
                addCoursesToXml((Course) o, doc, root);
            }
            else if (o instanceof Student) {
                addStudentsToXml((Student) o, doc, root);
            }
            else if (o instanceof Program) {
                addProgramsToXml((Program) o, doc, root);
            }
        }
        updateXml(doc);
    }

    private static void writeCourseInXml(List<SimpleCourse> courses, Element Node, Document doc) {
        for (Course course : courses) {
            Element item =  doc.createElement("item");
            item.setTextContent(course.getId());
            Node.appendChild(item);
        }
    }
    private static void updateXml(Document doc) {
        try {
            Source xmlInput = new DOMSource(doc);
            StreamResult xmlOutput = new StreamResult(new File("datav2.xml"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();


            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void addCoursesToXml(Course course, Document doc, Element root) {
        Element courseNode = doc.createElement("course");
        Element identifierNode = doc.createElement("identifier");
        Element nameNode = doc.createElement("name");
        Element creditsNode = doc.createElement("credits");

        identifierNode.setTextContent(course.getId());
        nameNode.setTextContent(course.getName());
        creditsNode.setTextContent(String.valueOf(course.getCredits()));

        courseNode.appendChild(identifierNode);
        courseNode.appendChild(nameNode);
        courseNode.appendChild(creditsNode);
        root.appendChild(courseNode);
    }
    private static void addStudentsToXml(Student student, Document doc, Element root) {
        List<Grade> grades = student.getGradeList();

        Element studentNode = doc.createElement("student");
        Element identifierNode = doc.createElement("identifier");
        Element nameNode = doc.createElement("name");
        Element surnameNode = doc.createElement("surname");
        Element programNode = doc.createElement("program");

        identifierNode.setTextContent(student.getStudentId());
        nameNode.setTextContent(student.getName());
        surnameNode.setTextContent(student.getSurname());
        programNode.setTextContent(student.getProgramId());

        studentNode.appendChild(identifierNode);
        studentNode.appendChild(nameNode);
        studentNode.appendChild(surnameNode);
        studentNode.appendChild(programNode);

        for (Grade grade : grades) {
            Element gradeNode = doc.createElement("grade");
            Element item = doc.createElement("item");
            Element value = doc.createElement("value");

            item.setTextContent(grade.getCourse().getId());
            value.setTextContent(String.valueOf(grade.getGrade()));

            gradeNode.appendChild(item);
            gradeNode.appendChild(value);
            studentNode.appendChild(gradeNode);
        }

        root.appendChild(studentNode);
    }

    private static void addProgramsToXml(Program program, Document doc, Element root) {
        List<SimpleCourse> simples = program.getSimpleCourseList();
        List<OptionCourse> options = program.getOptionCourseList();
        List<CompositeCourse> composites = program.getCompositeCoursesList();

        Element programNode = doc.createElement("program");
        Element identifierNode = doc.createElement("identifier");
        Element nameNode = doc.createElement("name");

        identifierNode.setTextContent(program.getId());
        nameNode.setTextContent(program.getName());

        programNode.appendChild(identifierNode);
        programNode.appendChild(nameNode);

        writeCourseInXml(simples, programNode, doc);

        for (OptionCourse option : options) {
            Element optionNode = doc.createElement("option");
            Element optionIdentifierNode = doc.createElement("identifier");
            Element optionNameNode = doc.createElement("name");

            optionIdentifierNode.setTextContent(option.getId());
            optionNameNode.setTextContent(option.getName());

            optionNode.appendChild(optionIdentifierNode);
            optionNode.appendChild(optionNameNode);

            writeCourseInXml(option.getOptionList(), optionNode, doc);
            programNode.appendChild(optionNode);
        }

        for (CompositeCourse composite : composites) {
            Element compositeNode = doc.createElement("composite");
            Element compositeIdentifierNode = doc.createElement("identifier");
            Element compositeNameNode = doc.createElement("name");

            compositeIdentifierNode.setTextContent(composite.getId());
            compositeNameNode.setTextContent(composite.getName());

            compositeNode.appendChild(compositeIdentifierNode);
            compositeNode.appendChild(compositeNameNode);

            writeCourseInXml(composite.getCompositeList(), compositeNode, doc);
            programNode.appendChild(compositeNode);
        }
        root.appendChild(programNode);
    }


}
