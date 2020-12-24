package frontend.Excel;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class NewCourse extends JPanel {
    public JFormattedTextField textField;

    public NewCourse() {
        super();
        setMaximumSize(new Dimension(2000, 60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        JButton name = new JButton("Nom");
        JButton identifiant = new JButton("Identifiant");
        JButton ects = new JButton("ECTS");
        JButton semestre = new JButton("Semestre");
        textField = new JFormattedTextField();
        textField.setMaximumSize(new Dimension(200, 50));

        add(name, BorderLayout.WEST);
        add(identifiant, BorderLayout.WEST);
        add(ects, BorderLayout.WEST);
        add(semestre, BorderLayout.WEST);
        add(textField, BorderLayout.WEST);
        setBorder(new TitledBorder("Course 1"));
    }

}
