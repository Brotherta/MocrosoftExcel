package frontend.utils;

import backend.xml.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;


public class StartFrame extends JDialog {
    private boolean status;

    public StartFrame(JFrame main, boolean bool) {
        super(main, bool);
        status = false;

        JPanel option = new JPanel();
        option.setLayout(new FlowLayout());
        JLabel title = new JLabel("Bienvenue dans le meilleur programe (c'est faux)");

        JButton newFile = new JButton("Nouveau fichier");
        JButton importFile = new JButton("Importer un fichier");

        option.add(title);
        option.add(newFile);
        option.add(importFile);

        JLabel erreurDirectory = new JLabel("Veuillez selectionner un répertoire.");
        erreurDirectory.setForeground(Color.RED);

        JLabel erreurType = new JLabel("Veuillez selectionner un fichier XML valide.");
        erreurType.setForeground(Color.RED);

        JLabel erreurNotExist = new JLabel("Le fichier selectionner est introuvable.");
        erreurNotExist.setForeground(Color.RED);

        newFile.addActionListener(e1 -> {
            FileChooser fc = new FileChooser(main, true, "data.xml", "Où enregistrer le fichier ?");
            String path = fc.getFilePath();
            File selectedFile = fc.getFileToSave();
            if (selectedFile != null  && selectedFile.exists()) {
                WarningFrame wf = new WarningFrame(main, true, "Un fichier existe déjà voulez-vous l'écraser ?");
                if (!wf.getStatus()) {
                    dispose();
                }
            }
            if (path != null) {
                if (!path.endsWith(".xml")) {
                    path += ".xml";
                }
                try {
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                    Document document = documentBuilder.newDocument();
                    Element root = document.createElement("data");
                    document.appendChild(root);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(new File(path));

                    transformer.transform(domSource, streamResult);

                    File file = new File("src/resources/dataPath.txt");
                    FileWriter writer = new FileWriter(file);
                    writer.write(path);
                    writer.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                status = true;
                dispose();

            } else {
                option.remove(erreurType);
                option.add(erreurDirectory);
                option.revalidate();
            }
        });

        importFile.addActionListener(e1 -> {
            FileChooser fc = new FileChooser(main, true, "data.xml", "Choisissez un fichier à importer");
            String path = fc.getFilePath();
            File selectedFile = fc.getFileToSave();
            if (selectedFile != null  && selectedFile.exists()) {
                if (path != null) {
                    if (path.endsWith(".xml") && verifyXml(path)) {
                        try {
                            File file = new File("src/resources/dataPath.txt");
                            FileWriter writer = new FileWriter(file);
                            writer.write(path);
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        status = true;
                        dispose();
                    }
                    else {
                        option.remove(erreurNotExist);
                        option.remove(erreurDirectory);
                        option.add(erreurType);
                        option.revalidate();
                    }
                } else {
                    option.remove(erreurNotExist);
                    option.remove(erreurType);
                    option.add(erreurDirectory);
                    option.revalidate();
                }
            }
            else {
                option.remove(erreurType);
                option.remove(erreurDirectory);
                option.add(erreurNotExist);
                option.revalidate();
            }

        });

        setResizable(false);
        setTitle("Nouveau Fichier");
        setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/3-this.getSize().width/3, dim.height/3-this.getSize().height/3);
        add(option, BorderLayout.CENTER);
        setSize(400, 120);
        setVisible(true);
    }

    public static boolean verifyXml(String path) {
        boolean bool = true;
        try {
            XmlReader reader = new XmlReader(path);
        } catch (Exception e) {
            bool = false;
        }
        return bool;
    }

    public boolean getStatus() {
        return status;
    }

}
