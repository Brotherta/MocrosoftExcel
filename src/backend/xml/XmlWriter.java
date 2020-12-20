package backend.xml;

import backend.course.Course;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlWriter {
    public static void writeInXml(Course course) {

    }

    public static void main(String[] args) throws Exception {
        XmlBny reader = new XmlBny("data/data.xml");
        Document doc = reader.getDoc();
        Element root = reader.getRoot();

        Element course = doc.createElement("course");
        Element identifier = doc.createElement("identifier");
        identifier.setTextContent("00000");
        Element name = doc.createElement("name");
        name.setTextContent("TEST");

        course.appendChild(identifier);
        course.appendChild(name);

        root.appendChild(course);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File("cdCatalog.xml"));
        transformer.transform(domSource, streamResult);

        DOMSource source = new DOMSource(doc);

    }
}
