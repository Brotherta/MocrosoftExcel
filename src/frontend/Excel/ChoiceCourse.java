package frontend.Excel;

import backend.Data;
import backend.course.Course;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoiceCourse extends JFrame {
    List<String> infos;
    GetInfosPanel getInfosPanel;
    List<Course> resultSearchCourse;
    Course course;
    JPanel inChooseCoursePanel;

    public ChoiceCourse(Data data,Filter courseFilter){


        super("Chercher un cours à filtrer");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,500);
        setLayout(new BoxLayout(super.getContentPane(),BoxLayout.Y_AXIS));


        /// Create Elements
        JPanel textPanel = new JPanel();
        JLabel text = new JLabel("Entrez des mots clefs puis sélectionnez le cours voulu:");
        textPanel.add(text);

        getInfosPanel = new GetInfosPanel();
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Chercher");
        searchPanel.add(searchButton);
        JPanel checkPanel = new JPanel();
        JButton checkButton = new JButton("Valider");
        checkPanel.add(checkButton);

        // Set variables
        resultSearchCourse = data.getCourseList();
        resultSearchCourse.removeAll(courseFilter.getCoursesFilter());


        /// RadioButton ChooseStudent
        JPanel chooseCoursePanel = new JPanel();
        chooseCoursePanel.setLayout(new BorderLayout());
        chooseCoursePanel.setBorder(new LineBorder(Color.BLACK));

        buttonChooseCoursePanel();
        chooseCoursePanel.add(new JScrollPane(inChooseCoursePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        searchButton.addActionListener(e -> {
            infos = getInfosPanel.getInfos();
            List<Course> sortedCourse = sortCourse(data,infos.get(0),infos.get(1));
            if (!(sortedCourse.size() == data.getCourseList().size())){
                resultSearchCourse.clear();
                for (Course course: sortedCourse){
                    resultSearchCourse.add(course);
                }
            }
            else {
                resultSearchCourse = data.getCourseList();
                resultSearchCourse.removeAll(courseFilter.getCoursesFilter());
            }
            chooseCoursePanel.removeAll();
            buttonChooseCoursePanel();
            chooseCoursePanel.add(new JScrollPane(inChooseCoursePanel));
            chooseCoursePanel.revalidate();

        });

        // Fermer la fenêtre
        checkButton.addActionListener(e -> {
            System.exit(0);
        });



        // Ajout dans la JFrame
        add(textPanel);
        add(getInfosPanel);
        add(searchPanel);
        add(chooseCoursePanel);
        add(checkPanel);
        setVisible(true);
    }

    private void buttonChooseCoursePanel(){
        inChooseCoursePanel = new JPanel();
        inChooseCoursePanel.setLayout(new BoxLayout(inChooseCoursePanel,BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        for (int i=0; i<resultSearchCourse.size(); i++){
            Course course = resultSearchCourse.get(i);
            JRadioButton courseButton = new JRadioButton(course.getName()+" - "+course.getId());
            courseButton.setActionCommand(course.getId());
            group.add(courseButton);
            inChooseCoursePanel.add(courseButton);
            courseButton.addActionListener(e -> {
                this.course = getcourseById(this.resultSearchCourse,e.getActionCommand());
            });
        }


    }

    private Course getcourseById(List<Course> courseList, String id){
        for (Course course : courseList){
            if (course.getId().equals(id)){
                return course;
            }
        }
        return null;
    }

    private List<Course> sortCourse(Data data,String name, String id){
        List<Course> allCourse = data.getCourseList();
        List<Course> sortedCourse = new ArrayList<>();
        for (Course course : allCourse){
            if (course.getName().toLowerCase().contains(name.toLowerCase())
                    && course.getId().toLowerCase().contains(id.toLowerCase()))
            {
                sortedCourse.add(course);
            }
        }
        return sortedCourse;
    }



    private class GetInfosPanel extends JPanel{
        JTextField name;
        JTextField surname;
        JTextField id;

        public GetInfosPanel(){
            name = new JTextField();
            id = new JTextField();

            name.setBorder(new TitledBorder("Nom"));
            id.setBorder(new TitledBorder("Identifiant"));
            name.setMaximumSize(new Dimension(2000,50));
            id.setMaximumSize(new Dimension(2000,50));
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            add(name);
            add(id);
        }

        public List<String> getInfos(){
            List<String> infos = new ArrayList<String>();
            infos.add(name.getText());
            infos.add(id.getText());
            return infos;
        }

    }

    public Course getCourse() {
        return course;
    }

    //    public static void main(String[] args) {
//        Data data = new Data("data/data.xml");
//        Filter courseFilter = new Filter();
//        ChoiceCourse choiceCourse = new ChoiceCourse(data,courseFilter);
//    }


}
