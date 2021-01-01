package frontend.newPopUp;
import backend.program.Program;
import backend.Data;
import backend.course.*;
import backend.student.Grade;
import backend.student.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PopUpNewStudent extends JDialog {

private int width = 1200;
private int height = 720;
private JPanel panelGlobale;
    private JPanel panelInfoStudent;
        private JFormattedTextField nomStudent;
        private JFormattedTextField prenomStudent;
        private JLabel idStudent;
    private JPanel panelProgram;
        private JComboBox nameProgram;
        private JPanel panelCoursTiedToProgram;
            private List<SimpleCourse> Cours;
            private List<OptionCourse> CoursOptionnel;
            private List<CompositeCourse> CoursComposite;
            private JPanel semester1Panel;
            private JPanel semester2Panel;
                private int sizeOfchoixOptionsCours;
                private List<Course> choixOptionsCours;
            private JPanel panelCoursSupplementaire;
                private int numberCoursSimple;
                private List<Object> NewCourseListProgram;
                private List<SimpleCourse> NewCourseListProgramFinal;
                private JPanel panelCoursSimple;
    private JPanel panelFin;
        private JButton Terminer;

    private Data data;


    private final List<Program> programList;


public PopUpNewStudent(List<Program> programList, List<Course> courseList, Data data, JFrame main, boolean bool){
    super(main,bool);
    this.programList=programList;
    this.choixOptionsCours=new ArrayList<>();
    this.NewCourseListProgram=new ArrayList<>();
    this.NewCourseListProgramFinal=new ArrayList<>();
    this.data=data;
    setSize(new Dimension(width,height));
    panelGlobale = new JPanel();
        /////////////////// Info etudiant
        panelInfoStudent=new JPanel();
        panelInfoStudent.setLayout(new BoxLayout(panelInfoStudent,BoxLayout.X_AXIS));
            nomStudent=new JFormattedTextField();
            nomStudent.setBorder(new TitledBorder("Nom"));
            nomStudent.setMaximumSize(new Dimension(200,50));
            prenomStudent=new JFormattedTextField();
             prenomStudent.setBorder(new TitledBorder("Prenom"));
            prenomStudent.setMaximumSize(new Dimension(200,50));
            idStudent=new JLabel(generateIdStudent());
            idStudent.setBorder(new TitledBorder("Id etud"));
            idStudent.setMaximumSize(new Dimension(200,50));
        panelInfoStudent.setPreferredSize(new Dimension(width/2,height/20));
        panelInfoStudent.add(nomStudent);
        panelInfoStudent.add(prenomStudent);
        panelInfoStudent.add(idStudent);
        /////////////////////////////////////// Fin info etudiant

        ///////////////////////// panelProgram le plus relou
        panelProgram =  new JPanel();
        panelProgram.setLayout(new BorderLayout());
            JPanel containerNameProgram=new JPanel();
            nameProgram=new JComboBox();
            containerNameProgram.setLayout(new BorderLayout());
            //containerNameProgram.setLayout(new BoxLayout(containerNameProgram,BoxLayout.X_AXIS));
            containerNameProgram.setMaximumSize(new Dimension(width/4,height/20));
            for(int i=0;i<programList.size();i++)
            {
                Program current=programList.get(i);
                String nameCurrent= current.getName();
                String idCurrent=current.getId();
                nameProgram.addItem(nameCurrent+" "+idCurrent);
            }
            containerNameProgram.add(nameProgram,BorderLayout.WEST);

            panelCoursTiedToProgram = new JPanel();
            Cours = new ArrayList<>();
            CoursOptionnel = new ArrayList<>();
            CoursComposite = new ArrayList<>();
            semester1Panel=new JPanel();
            semester2Panel=new JPanel();
            panelCoursTiedToProgram.setLayout(new BoxLayout(panelCoursTiedToProgram,BoxLayout.Y_AXIS));
            panelCoursTiedToProgram.add(semester1Panel);
            panelCoursTiedToProgram.add(semester2Panel);
    panelCoursSimple = new JPanel();
            panelCoursTiedToProgram.add(panelCoursSimple);
            nameProgram.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(nameProgram.getSelectedIndex()!=-1)
                    {
                        panelCoursTiedToProgram.remove(panelCoursSimple);
                        panelCoursTiedToProgram.remove(semester1Panel);
                        panelCoursTiedToProgram.remove(semester2Panel);
                        Cours=programList.get(nameProgram.getSelectedIndex()).getSimpleCourseList();
                        CoursOptionnel=programList.get(nameProgram.getSelectedIndex()).getOptionCourseList();
                        CoursComposite=programList.get(nameProgram.getSelectedIndex()).getCompositeCoursesList();
                        // Répartition des Cours
                        List<SimpleCourse> simpleCourseList1 = new ArrayList<SimpleCourse>();
                        List<SimpleCourse> simpleCourseList2 = new ArrayList<SimpleCourse>();
                        for (SimpleCourse simpleCourse : Cours){
                            if (NumSemester(simpleCourse) == 1){
                                simpleCourseList1.add(simpleCourse);
                            }
                            else if (NumSemester(simpleCourse) == 2){
                                simpleCourseList2.add(simpleCourse);
                            }
                            else {
                                System.out.println("Problem");
                            }
                        }
                        List<CompositeCourse> compositeCourseList1 = new ArrayList<CompositeCourse>();
                        List<CompositeCourse> compositeCourseList2 = new ArrayList<CompositeCourse>();
                        for (CompositeCourse compositeCourse : CoursComposite){
                            if (NumSemester(compositeCourse) == 1){
                                compositeCourseList1.add(compositeCourse);
                            }
                            else if (NumSemester(compositeCourse) == 2){
                                compositeCourseList2.add(compositeCourse);
                            }
                            else {
                                System.out.println("Problem");
                            }
                        }
                        List<OptionCourse> optionCourseList1 = new ArrayList<OptionCourse>();
                        List<OptionCourse> optionCourseList2 = new ArrayList<OptionCourse>();
                        for (OptionCourse optionCourse : CoursOptionnel){
                            if (NumSemester(optionCourse) == 1){
                                optionCourseList1.add(optionCourse);
                            }
                            else if (NumSemester(optionCourse) == 2){
                                optionCourseList2.add(optionCourse);
                            }
                            else {
                                System.out.println("Problem");
                            }
                        }

                        //// Semester 1 Panel
                         semester1Panel = printSemester("Semestre 1",simpleCourseList1,optionCourseList1,compositeCourseList1);
                        ////// Semestre 2 Panel
                         semester2Panel = printSemester("Semestre 2",simpleCourseList2,optionCourseList2,compositeCourseList2);
                        panelCoursTiedToProgram.add(semester1Panel);
                        panelCoursTiedToProgram.add(semester2Panel);
                        panelCoursTiedToProgram.add(panelCoursSimple);
                        revalidate();
                        repaint();
                    }
                    }
            });

        panelCoursSupplementaire=new JPanel();
        //////////////////////////////////////////////////////
    //////////////PanelCoursSimple////

    panelCoursSimple.setSize(new Dimension(width/4,500));
    panelCoursSimple.setLayout(new BoxLayout(panelCoursSimple,BoxLayout.X_AXIS));
    JButton ajoutDeCours = new JButton("Ajouter un Cours");
    JPanel menuCoursSimple=new JPanel();
    menuCoursSimple.setLayout(new BoxLayout(menuCoursSimple,BoxLayout.X_AXIS));
    menuCoursSimple.add(ajoutDeCours);
    panelCoursSimple.add(menuCoursSimple);
    JPanel panelChoixCoursContainer = new JPanel();
    panelChoixCoursContainer.setBorder(new TitledBorder("Liste Cours"));
    panelChoixCoursContainer.setSize(new Dimension(width/4,500));
    panelChoixCoursContainer.setLayout(new BoxLayout(panelChoixCoursContainer,BoxLayout.X_AXIS));
    numberCoursSimple=0;
    ajoutDeCours.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CourseChoicePanel panelChoixCours= new CourseChoicePanel(courseList,width/4,numberCoursSimple);
            numberCoursSimple++;
            NewCourseListProgram.add("NULL");
            JButton annuler=new JButton("X");
            JPanel panelTmp=new JPanel();
            panelTmp.setSize(new Dimension((width/4)-30,70));
            panelChoixCoursContainer.add(panelTmp);
            annuler.addActionListener(e2->{
                NewCourseListProgram.set(panelChoixCours.getNumberCoursSimple(),"NULL");
                panelChoixCoursContainer.remove(panelTmp);
                NewCourseListProgramFinal=ValiderCours(NewCourseListProgram);
                revalidate();
                repaint();
            });
            JButton Cree=new JButton("V");
            Cree.addActionListener(e3->{
                Course leCours=panelChoixCours.getCourse();
                NewCourseListProgram.set(panelChoixCours.getNumberCoursSimple(),leCours);
                NewCourseListProgramFinal=ValiderCours(NewCourseListProgram);
                // System.out.println(NewCourseListProgram);
                // System.out.println(NewCourseListProgramFinal);
            });
            JPanel tmpBouton=new JPanel();
            tmpBouton.setLayout(new BoxLayout(tmpBouton,BoxLayout.Y_AXIS));
            panelTmp.add(panelChoixCours);
            tmpBouton.add(Cree);
            tmpBouton.add(annuler);
            panelTmp.add(tmpBouton);
            revalidate();
            // repaint();
        }
    });

    panelCoursSimple.add(panelChoixCoursContainer);
    JScrollPane scrollPaneCoursSimple = new JScrollPane(panelCoursSimple);
    scrollPaneCoursSimple.setPreferredSize(new Dimension(width/3,500));
    scrollPaneCoursSimple.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneCoursSimple.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ///////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
        //panelCoursTiedToProgram.add(scrollPaneCoursSimple);
        panelProgram.add(panelCoursTiedToProgram);
        panelProgram.add(containerNameProgram,BorderLayout.NORTH);
        panelProgram.add(panelCoursSupplementaire,BorderLayout.SOUTH);
        ///////////////// Fin panelProgram
    panelFin = new JPanel();
    panelFin.setLayout(new BorderLayout());
        Terminer=new JButton("Terminer");
        Terminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Grade> listGrade=new ArrayList<>();
                for(int i=0;i<Cours.size();i++)
                {
                    listGrade.add(new Grade(-2,Cours.get(i)));
                }
                for(int i=0;i<choixOptionsCours.size();i++)
                {
                    listGrade.add(new Grade(-2,choixOptionsCours.get(i)));
                }
                for(int i=0;i<CoursComposite.size();i++)
                {
                    List<SimpleCourse> listTmp=new ArrayList(CoursComposite.get(i).getCompositeList());
                    for(int j=0;j<listTmp.size();j++)
                    {
                        listGrade.add(new Grade(-2, listTmp.get(j)));
                    }
                }
                for(int i=0;i<NewCourseListProgramFinal.size();i++)
                {
                    listGrade.add(new Grade(-2,NewCourseListProgramFinal.get(i)));
                }
                Student student =  new Student(nomStudent.getText(),prenomStudent.getText(), idStudent.getText(),programList.get(nameProgram.getSelectedIndex()).getId(),listGrade);
                System.out.println("Nom : "+student.getName()+"Liste :"+student.getGradeList());
                data.addStudent(student);
                dispose();
                return;
            }
        });
    panelFin.add(Terminer,BorderLayout.EAST);
    panelGlobale.setPreferredSize(new Dimension(width,720));
    panelGlobale.setLayout(new BorderLayout());
    panelGlobale.add(panelFin,BorderLayout.SOUTH);
    panelGlobale.add(panelInfoStudent,BorderLayout.NORTH);
    panelGlobale.add(new JScrollPane(panelProgram),BorderLayout.CENTER);
    //pack();
    add(panelGlobale);
    setVisible(true);

}

    private String generateIdStudent() {
        int Year = Calendar.getInstance().get(Calendar.YEAR);
        return ""+Year+data.getCourseList().size();

    }

    //////////Tweak
private JPanel printOptionCourseTweak(OptionCourse course){
    JPanel optionCourses = printMultipleSimpleCoursesTweak(course.getOptionList());
    choixOptionsCours.add(null);
    this.sizeOfchoixOptionsCours++;
    optionCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects"));
    return optionCourses;
}   // Panel d'une optionCourse
    private JRadioButton printSimpleCourseTweak(Course course){
       // JPanel simpleCourse = new JPanel();
        int creationNumber=sizeOfchoixOptionsCours;
        JRadioButton simpleCourseInfo = new JRadioButton(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects");
        simpleCourseInfo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                choixOptionsCours.set(creationNumber,course);

            }
        });
      //  simpleCourse.add(simpleCourseInfo);
      //  simpleCourse.setMaximumSize(new Dimension(500,30));
      //  simpleCourse.setLayout(new FlowLayout(FlowLayout.LEFT));
        return simpleCourseInfo;
    }
    private JPanel printMultipleSimpleCoursesTweak(List<SimpleCourse> courseList){
        int number=0;
        JPanel allCoursesPanel = new JPanel();
        allCoursesPanel.setLayout(new BoxLayout(allCoursesPanel,BoxLayout.Y_AXIS));
        ButtonGroup group=new ButtonGroup();
        for (SimpleCourse course : courseList){
            JRadioButton truc=printSimpleCourseTweak(course);
            group.add(truc);
            allCoursesPanel.add(truc);
        }

        return allCoursesPanel;
    }   // Panel d'une liste de simpleCourse

    ////////////////////
    private List ValiderCours(List cours){
        List newFinal=new ArrayList();
        for(int i=0;i<cours.size();i++)
        {
            if(cours.get(i)!="NULL")
            {
                newFinal.add(cours.get(i));
            }
        }
        System.out.println(newFinal);
        return newFinal;
    }
    ///////////////////////


///////////////////////////////////////////// Code extrait de ViewProgram
private JPanel printOptionCourse(OptionCourse course){
    JPanel optionCourses = printMultipleSimpleCourses(course.getOptionList());
    optionCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects"));
    return optionCourses;
}   // Panel d'une optionCourse
    private JPanel printSimpleCourse(Course course){
        JPanel simpleCourse = new JPanel();
        JLabel simpleCourseInfo = new JLabel("∘ "+course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects");
        simpleCourse.add(simpleCourseInfo);
        simpleCourse.setMaximumSize(new Dimension(500,30));
        simpleCourse.setLayout(new FlowLayout(FlowLayout.LEFT));
        return simpleCourse;
    }
    private JPanel printMultipleSimpleCourses(List<SimpleCourse> courseList){
        JPanel allCoursesPanel = new JPanel();
        allCoursesPanel.setLayout(new BoxLayout(allCoursesPanel,BoxLayout.Y_AXIS));
        for (SimpleCourse course : courseList){
            JPanel coursePanel = printSimpleCourse(course);
            allCoursesPanel.add(coursePanel);
        }
        return allCoursesPanel;
    }   // Panel d'une liste de simpleCourse
    private JPanel printCompositeCourse(CompositeCourse course){
        JPanel compositeCourses = printMultipleSimpleCourses(course.getCompositeList());
        compositeCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()));
        return compositeCourses;
    }  // Panel d'une compositeCourse
    private JPanel printSemester(String semesterName,List<SimpleCourse> simpleCourseList, List<OptionCourse> optionCourseList, List<CompositeCourse> compositeCourseList){
        JPanel semesterPanel = new JPanel();
        semesterPanel.setLayout(new BoxLayout(semesterPanel,BoxLayout.X_AXIS));
        JPanel Noptional = new JPanel();
        Noptional.setLayout(new BoxLayout(Noptional,BoxLayout.Y_AXIS));
        semesterPanel.setBorder(new TitledBorder(semesterName));
        semesterPanel.add(Box.createVerticalStrut(10));

        // Cours Simple
        JPanel allSimpleCourses = printMultipleSimpleCourses(simpleCourseList);
        allSimpleCourses.setBorder(new TitledBorder("Simple Courses"));
        Noptional.add(allSimpleCourses);
        //semesterPanel.add(Box.createVerticalStrut(20));

        //// Cours Composites
        JPanel allCompositeCourses = new JPanel();
        allCompositeCourses.setLayout(new BoxLayout(allCompositeCourses,BoxLayout.Y_AXIS));
        for (CompositeCourse course : compositeCourseList){
            JPanel compositePanel = printCompositeCourse(course);
            allCompositeCourses.add(compositePanel);
        }
        Noptional.add(allCompositeCourses);
      //  semesterPanel.add(Box.createVerticalStrut(20));
        //// Cours Optionnels
        JPanel allOptionCourses = new JPanel();
        allOptionCourses.setLayout(new BoxLayout(allOptionCourses,BoxLayout.Y_AXIS));
        for (OptionCourse course : optionCourseList){
            JPanel optionPanel = printOptionCourseTweak(course);
            allOptionCourses.add(optionPanel);
        }
        semesterPanel.add(new JScrollPane(Noptional));
        semesterPanel.add(new JScrollPane(allOptionCourses));
        semesterPanel.add(Box.createVerticalStrut(20));

        return semesterPanel;
    }

    private int NumSemester (Course course){
        String courseId = course.getId();
        String[] courseIdList = courseId.split("");
        for (int i=0; i<courseIdList.length; i++) {
            try{
                int nb = Integer.parseInt(courseIdList[i]);
                if(nb % 2 == 0){nb = 2;}
                else {nb = nb%2;}
                return nb;
            }
            catch (IllegalArgumentException e){ }
        }
        return 300;
    }

///////////////////////////////////////////// Fin Code extrait de ViewProgram
  /*  public static void main(String[] args) {
        Data data=new Data();
        List listProgram=data.getProgramList();
        List listCourse=data.getCourseList();
        new popUpNewStudent(listProgram,listCourse,data);
    }*/
}
