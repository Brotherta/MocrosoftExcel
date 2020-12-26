package frontend.newPopUp;
import backend.xml.XmlReader;
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
public class PanelChoixCours extends JPanel {
    public JLabel ECTS;
    public JComboBox name;
    public JComboBox semestre;
    public JLabel identifiant;
    private int numberCoursSimple;


    private Course course;

    public PanelChoixCours(List<Course> courseList,int width,int numberCoursSimple) {
        super();
        this.numberCoursSimple=numberCoursSimple;
        setBorder(new TitledBorder("Cours"));
        setSize(new Dimension(width,50));
        setLayout(new FlowLayout());
        //xml= new XmlReader();
        ECTS = new JLabel("ECTS");
        semestre = new JComboBox();
        semestre.setPreferredSize(new Dimension(80,50));
        semestre.addItem("1er Semestre");
        semestre.addItem("2nd Semestre");

        identifiant=new JLabel("ID");
        identifiant.setSize(new Dimension(80,50));

        name=new JComboBox();
        name.setPreferredSize(new Dimension(width-130,50));
        List<String> courseListByName = new ArrayList<>();

        for(int i =0;i<courseList.size();i++)
        {
            courseListByName.add(courseList.get(i).getName());
            name.addItem(courseListByName.get(i));

        }
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(name.getSelectedIndex());
                if(name.getSelectedIndex()!=-1)
                {
                    int indexOf = courseListByName.indexOf(name.getSelectedItem());
                    course = courseList.get(indexOf);
                    identifiant.setText(course.getId());
                    ECTS.setText(course.getCredits() + "");
                }
            }
        });
        add(name);
        add(semestre);
        add(identifiant);
        add(ECTS);
    }
    public Course getCourse()
    {
        name.removeAllItems();
        name.addItem(course.getName());
        this.setBackground(Color.LIGHT_GRAY);
        return this.course;
    }

    public int getNumberCoursSimple() {
        return numberCoursSimple;
    }
}
