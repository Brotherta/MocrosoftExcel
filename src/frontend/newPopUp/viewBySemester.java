package frontend.newPopUp;

import backend.course.*;
import backend.program.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class viewBySemester {

    private JPanel panelSemester1;
    private JPanel panelSemester2;
    private boolean fromViewOrFromChoice;

    List<SimpleCourse> Cours;
    List<OptionCourse> CoursOptionnel;
    List<CompositeCourse> CoursComposite;
    private List<Course> choixOptionsCours;


    private int nbrOptions;

    viewBySemester(Program programmeOnScope, boolean fromViewOrFromChoice) {
        this.choixOptionsCours=new ArrayList<>();
        this.fromViewOrFromChoice=fromViewOrFromChoice; // false = viewProgram , true = NewStudent
        this.Cours= programmeOnScope.getSimpleCourseList();
        this.CoursOptionnel = programmeOnScope.getOptionCourseList();
        this.CoursComposite = programmeOnScope.getCompositeCoursesList();
        // Répartition des Cours
        List<SimpleCourse> simpleCourseList1 = new ArrayList<SimpleCourse>();
        List<SimpleCourse> simpleCourseList2 = new ArrayList<SimpleCourse>();
        for (SimpleCourse simpleCourse : Cours) {
            if (NumSemester(simpleCourse) == 1) {
                simpleCourseList1.add(simpleCourse);
            } else if (NumSemester(simpleCourse) == 2) {
                simpleCourseList2.add(simpleCourse);
            } else {
                System.out.println("Problem");
            }
        }
        List<CompositeCourse> compositeCourseList1 = new ArrayList<CompositeCourse>();
        List<CompositeCourse> compositeCourseList2 = new ArrayList<CompositeCourse>();
        for (CompositeCourse compositeCourse : CoursComposite) {
            if (NumSemester(compositeCourse) == 1) {
                compositeCourseList1.add(compositeCourse);
            } else if (NumSemester(compositeCourse) == 2) {
                compositeCourseList2.add(compositeCourse);
            } else {
                System.out.println("Problem");
            }
        }
        List<OptionCourse> optionCourseList1 = new ArrayList<OptionCourse>();
        List<OptionCourse> optionCourseList2 = new ArrayList<OptionCourse>();
        for (OptionCourse optionCourse : CoursOptionnel) {
            if (NumSemester(optionCourse) == 1) {
                optionCourseList1.add(optionCourse);
            } else if (NumSemester(optionCourse) == 2) {
                optionCourseList2.add(optionCourse);
            } else {
                System.out.println("Problem");
            }
        }
        //// Semester 1 Panel
        this.panelSemester1 = printSemester("Semestre 1",simpleCourseList1,optionCourseList1,compositeCourseList1);
        ////// Semestre 2 Panel
        this.panelSemester2 = printSemester("Semestre 2",simpleCourseList2,optionCourseList2,compositeCourseList2);
    }








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

    private JPanel printCompositeCourse(CompositeCourse course){
        JPanel compositeCourses = printMultipleSimpleCourses(course.getCompositeList());
        compositeCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()));
        return compositeCourses;
    }  // Panel d'une compositeCourse
    private JPanel printSemester(String semesterName,List<SimpleCourse> simpleCourseList, List<OptionCourse> optionCourseList, List<CompositeCourse> compositeCourseList){
        JPanel semesterPanel = new JPanel();
        if(fromViewOrFromChoice) {
            semesterPanel.setLayout(new BoxLayout(semesterPanel, BoxLayout.X_AXIS));
        }
        else{
            semesterPanel.setLayout(new BoxLayout(semesterPanel, BoxLayout.Y_AXIS));
        }
        JPanel Noptional = new JPanel();
        if(fromViewOrFromChoice) {
            Noptional.setLayout(new BoxLayout(Noptional,BoxLayout.Y_AXIS));
        }

        semesterPanel.setBorder(new TitledBorder(semesterName));
        semesterPanel.add(Box.createVerticalStrut(10));

        // Cours Simple
        JPanel allSimpleCourses = printMultipleSimpleCourses(simpleCourseList);
        allSimpleCourses.setBorder(new TitledBorder("Simple Courses"));
        if(fromViewOrFromChoice) {
            Noptional.add(allSimpleCourses);
        }
        else{
            semesterPanel.add(allSimpleCourses);
        }
        //semesterPanel.add(Box.createVerticalStrut(20));

        //// Cours Composites
        JPanel allCompositeCourses = new JPanel();
        allCompositeCourses.setLayout(new BoxLayout(allCompositeCourses,BoxLayout.Y_AXIS));
        for (CompositeCourse course : compositeCourseList){
            JPanel compositePanel = printCompositeCourse(course);
            allCompositeCourses.add(compositePanel);
        }
        if(fromViewOrFromChoice) {
            Noptional.add(allCompositeCourses);
        }
        else{
            semesterPanel.add(allSimpleCourses);
        }
        //// Cours Optionnels
        JPanel allOptionCourses = new JPanel();
        allOptionCourses.setLayout(new BoxLayout(allOptionCourses,BoxLayout.Y_AXIS));
        for (OptionCourse course : optionCourseList){
            JPanel optionPanel = printOptionCourseRadioButton(course,fromViewOrFromChoice);  // Creation du panel options avec en parametre le cours optionnel // course contient une option cours
            allOptionCourses.add(optionPanel);
        }
        if(fromViewOrFromChoice) {
            semesterPanel.add(new JScrollPane(Noptional));
        }
        else{
            semesterPanel.add(allOptionCourses);
        }
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
    //////////Tweak
    private JPanel printOptionCourseRadioButton(OptionCourse course,boolean radioButtonOnOrOff){
        choixOptionsCours.add(null);
        JPanel optionCourses = printMultipleSimpleCourses(course.getOptionList(),radioButtonOnOrOff);   // Je créé le panel qui contient les options
        optionCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects"));
        this.nbrOptions++;
        return optionCourses;
    }   // Panel d'une optionCourse
    private JRadioButton printSimpleCourse(Course course, int numeroDeCreation){
        JRadioButton simpleCourseInfo = new JRadioButton(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects");
        simpleCourseInfo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                choixOptionsCours.set(numeroDeCreation,course);
                // System.out.println("Choix :"+choixOptionsCours+"\n Size :"+nbrOptions+"\n Vrai size"+choixOptionsCours.size());
            }
        });
        return simpleCourseInfo;
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
    private JPanel printMultipleSimpleCourses(List<SimpleCourse> courseList,boolean presentOrNo){
        JPanel allCoursesPanel = new JPanel();
        allCoursesPanel.setLayout(new BoxLayout(allCoursesPanel,BoxLayout.Y_AXIS));
        ButtonGroup group=new ButtonGroup();
        for (SimpleCourse course : courseList){
            JRadioButton truc=printSimpleCourse(course,this.nbrOptions);
            group.add(truc);
            allCoursesPanel.add(truc);
            truc.setEnabled(presentOrNo);
        }
        return allCoursesPanel;
    }   // Panel d'une liste de simpleCourse

    public JPanel getPanelSemester1() {
        return panelSemester1;
    }

    public JPanel getPanelSemester2() {
        return panelSemester2;
    }

    public List<SimpleCourse> getCours() {
        return Cours;
    }

    public List<OptionCourse> getCoursOptionnel() {
        return CoursOptionnel;
    }

    public List<CompositeCourse> getCoursComposite() {
        return CoursComposite;
    }

    public List<Course> getChoixOptionsCours() {
        return choixOptionsCours;
    }

    }


