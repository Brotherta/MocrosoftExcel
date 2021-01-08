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
            private List<CompositeCourse> CoursComposite;
            viewBySemester ViewSemester;
            private JPanel semester1Panel;
            private JPanel semester2Panel;
                private List<Course> choixOptionsCours;
        private panelContainerCourse panelCoursSupplementaire;
                private List<Object> NewCourseListProgram;
                private List<Course> NewCourseListProgramFinal;
    private JPanel panelFin;
        private JButton Terminer;
    private Data data;
    private final List<Program> programList;
public PopUpNewStudent(List<Program> programList, List<Course> courseList, Data data, JFrame main, boolean bool){
    super(main,bool);
    this.programList=programList;
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
        ///////////////////////// panelProgram
        panelProgram =  new JPanel();
        panelProgram.setLayout(new BorderLayout());
            JPanel containerNameProgram=new JPanel();
            nameProgram=new JComboBox();
            containerNameProgram.setLayout(new BorderLayout());
            containerNameProgram.setMaximumSize(new Dimension(width/4,height/20));
            int i;
            for( i=0;i<programList.size();i++)
            {
                Program current=programList.get(i);
                String nameCurrent= current.getName();
                String idCurrent=current.getId();
                nameProgram.addItem(nameCurrent+" "+idCurrent);
            }
            nameProgram.addItem("Pas de programme selectionné");
            nameProgram.setSelectedIndex(i);
            containerNameProgram.add(nameProgram,BorderLayout.WEST);
            panelCoursTiedToProgram = new JPanel();
            Cours = new ArrayList<>();
            //CoursOptionnel = new ArrayList<>();
            CoursComposite = new ArrayList<>();
            semester1Panel=new JPanel();
            semester2Panel=new JPanel();
            panelCoursTiedToProgram.setLayout(new BoxLayout(panelCoursTiedToProgram,BoxLayout.Y_AXIS));
            panelCoursTiedToProgram.add(semester1Panel);
            panelCoursTiedToProgram.add(semester2Panel);
    panelCoursSupplementaire=new panelContainerCourse(width,courseList,BoxLayout.X_AXIS);
    JScrollPane scrollPaneCoursSupplementaire = new JScrollPane(panelCoursSupplementaire);
    scrollPaneCoursSupplementaire.setPreferredSize(new Dimension(width/3,100));
    scrollPaneCoursSupplementaire.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPaneCoursSupplementaire.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            nameProgram.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!(nameProgram.getSelectedItem().equals("Pas de programme selectionné")))
                    {
                        panelCoursTiedToProgram.remove(semester1Panel);
                        panelCoursTiedToProgram.remove(semester2Panel);
                        ViewSemester=new viewBySemester(programList.get(nameProgram.getSelectedIndex()),true);
                        //// Semester 1 Panel
                        Cours=ViewSemester.getCours();
                        CoursComposite=ViewSemester.getCoursComposite();
                        semester1Panel=ViewSemester.getPanelSemester1();
                        ////// Semestre 2 Panel
                        semester2Panel= ViewSemester.getPanelSemester2();
                        panelCoursTiedToProgram.add(semester1Panel);
                        panelCoursTiedToProgram.add(semester2Panel);
                        revalidate();
                        repaint();
                    }
                    else
                    {
                        panelCoursTiedToProgram.remove(semester1Panel);
                        panelCoursTiedToProgram.remove(semester2Panel);
                        revalidate();
                        repaint();
                    }
                    }
            });
        //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
        panelProgram.add(panelCoursTiedToProgram,BorderLayout.CENTER);
        panelProgram.add(containerNameProgram,BorderLayout.NORTH);
        panelProgram.add(scrollPaneCoursSupplementaire,BorderLayout.SOUTH);
        ///////////////// Fin panelProgram
    panelFin = new JPanel();
    panelFin.setLayout(new BorderLayout());
        Terminer=new JButton("Terminer");
        Terminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nomStudent.getText().length()!=0 && prenomStudent.getText().length()!=0)
                {
                    if (!(nameProgram.getSelectedItem().equals("Pas de programme selectionné"))) {
                        List<Grade> listGrade = new ArrayList<>();
                        for (int i = 0; i < Cours.size(); i++) {
                            listGrade.add(new Grade(-2, Cours.get(i)));
                        }
                        choixOptionsCours=ViewSemester.getChoixOptionsCours();
                        for (int i = 0; i < choixOptionsCours.size(); i++) {
                            if (choixOptionsCours.get(i) == null) {
                                JOptionPane.showMessageDialog(main, "Option "+(i+1)+" pas remplie");
                                return;
                            }
                            listGrade.add(new Grade(-2, choixOptionsCours.get(i)));
                        }
                        for (int i = 0; i < CoursComposite.size(); i++) {
                            List<SimpleCourse> listTmp = new ArrayList(CoursComposite.get(i).getCompositeList());
                            for (int j = 0; j < listTmp.size(); j++) {
                                listGrade.add(i, new Grade(-2, listTmp.get(j)));
                            }
                        }
                        NewCourseListProgramFinal=panelCoursSupplementaire.getNewCourseListProgramFinal();
                        for (int i = 0; i < NewCourseListProgramFinal.size(); i++) {
                            listGrade.add(new Grade(-2, NewCourseListProgramFinal.get(i)));
                        }
                        Student student = new Student(nomStudent.getText(), prenomStudent.getText(), idStudent.getText(), programList.get(nameProgram.getSelectedIndex()).getId(), listGrade);
                        data.addStudent(student);
                        dispose();
                        return;
                    }
                    else if(panelCoursSupplementaire.getNewCourseListProgramFinal().size()!=0){
                        List<Grade> listGrade = new ArrayList<>();
                        NewCourseListProgramFinal=panelCoursSupplementaire.getNewCourseListProgramFinal();
                        for (int i = 0; i < NewCourseListProgramFinal.size(); i++) {
                            listGrade.add(new Grade(-2, NewCourseListProgramFinal.get(i)));
                        }
                        Student student = new Student(nomStudent.getText(), prenomStudent.getText(), idStudent.getText(), "Aucun", listGrade);
                        data.addStudent(student);
                        dispose();
                        return;
                    }
                    else{
                        JOptionPane.showMessageDialog(main, "Il faut choisir un programme et/ou ajouter des cours");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(main, "Nom et prenom à remplir");
                return;
            }
        });
    panelFin.add(Terminer,BorderLayout.EAST);
    panelGlobale.setPreferredSize(new Dimension(width,720));
    panelGlobale.setLayout(new BorderLayout());
    panelGlobale.add(panelFin,BorderLayout.SOUTH);
    panelGlobale.add(panelInfoStudent,BorderLayout.NORTH);
    panelGlobale.add(panelProgram,BorderLayout.CENTER);
    add(panelGlobale);
    setVisible(true);
}
    private String generateIdStudent() {
        int Year = Calendar.getInstance().get(Calendar.YEAR);
        return ""+Year+data.getStudentList().size();

    }
}
