package backend.xml;

import frontend.Excel.ExcelPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlBny {
    private Document doc;
    private File file;
    private Element root;

    XmlBny(String path, boolean newFile)  {
        try {
            file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            if (newFile) {
                try {

                    doc = dBuilder.parse(file); // ouverture et lecture du fichier XML
                    doc.getDocumentElement().normalize(); // normalise le contenu du fichier, opération très conseillée
                    root = doc.getDocumentElement(); // la racine de l'arbre XML
                } catch (FileNotFoundException e) {
                    doc = dBuilder.newDocument();
                    root = doc.getDocumentElement();
                }
            }
            else {
                doc = dBuilder.parse(file); // ouverture et lecture du fichier XML
                doc.getDocumentElement().normalize(); // normalise le contenu du fichier, opération très conseillée
                root = doc.getDocumentElement(); // la racine de l'arbre XML
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Document getDoc() {
        return doc;
    }

    public File getFile() {
        return file;
    }

    public Element getRoot() {
        return root;
    }

    // Extrait la liste des fils de l'élément item dont le tag est name
    public static List<Element> getChildren(Element item, String name) {
        NodeList nodeList = item.getChildNodes();
        List<Element> children = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i); // cas particulier pour nous où tous les noeuds sont des éléments
                if (element.getTagName().equals(name)) {
                    children.add(element);
                }
            }
        }
        return children;
    }
}
