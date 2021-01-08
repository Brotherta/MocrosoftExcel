package frontend.newPopUp;
import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class CourseChoicePanel extends JPanel {
    public JLabel ECTS;
    public JComboBox name;
    public JLabel semestre;
    public JLabel identifiant;
    private int numberCoursSimple;


    private Course course;

    public CourseChoicePanel(List<Course> courseList, int width, int numberCoursSimple) {
        super();
        this.numberCoursSimple=numberCoursSimple;
        setBorder(new TitledBorder("Cours"));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(new Dimension(width,50));
        setLayout(new FlowLayout());
        ECTS = new JLabel("ECTS");
        semestre = new JLabel("Semestre");
        semestre.setPreferredSize(new Dimension(70,50));
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
        name.setSelectedIndex(0); // Il faut un cours par défaut si
        course=courseList.get(0); // l'utilisateur veux selectionner le cours d'index 0 sans utiliser le comboBox
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getSelectedIndex()!=-1)
                {
                    int indexOf = courseListByName.indexOf(name.getSelectedItem());
                    course = courseList.get(indexOf);
                    String id = course.getId();
                    identifiant.setText(id);
                    ECTS.setText(course.getCredits() + "");
                    for(int i = 0;i<id.length();i++)
                    {
                        if(Character.isDigit(id.charAt(i)))
                        {
                            semestre.setText("Semestre"+" "+id.charAt(i));
                           break;
                        }
                    }

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
        this.setBackground(new Color(101, 193, 96));
        return this.course;
    }

    public int getNumberCoursSimple() {
        return numberCoursSimple;
    }
}
