package frontend.newPopUp;


import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
public class CompositeDeprecated extends JPanel{
    private List<Course> courseList;

    CompositeDeprecated(List<Course> courseList, int width, int numberCoursComposite)
    {
        super();
        JLabel WIP=new JLabel("Work in Progress 2 ");
        add(WIP);
    }


}