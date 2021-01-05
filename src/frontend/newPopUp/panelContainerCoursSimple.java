package frontend.newPopUp;

import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class panelContainerCoursSimple extends JPanel {

    private List<Course> NewCourseListProgram;
    private List<Course> NewCourseListProgramFinal;
    private List<Course> courseList;

    private int semestre;
    private int numberCours;
    private int width;

    private String id;

    private JPanel panelChoixCoursContainer;

    panelContainerCoursSimple(int width, List<Course> courseList, int axis) {
        super();
        this.NewCourseListProgram=new ArrayList<>();
        this.NewCourseListProgramFinal=new ArrayList<>();
        //this.semestre=semestre;
        this.numberCours=0;
        this.width=width;
        this.courseList=courseList;
        setSize(new Dimension(width/4,500));
        setLayout(new BoxLayout(this,axis));
        JButton ajoutDeCours = new JButton("Ajouter un Cours");
        JPanel menuCoursSimple=new JPanel();
        menuCoursSimple.setLayout(new BoxLayout(menuCoursSimple,axis));
        menuCoursSimple.add(ajoutDeCours);
        add(menuCoursSimple);
        this.panelChoixCoursContainer = new JPanel();
        panelChoixCoursContainer.setBorder(new TitledBorder("Liste Cours"));
        panelChoixCoursContainer.setSize(new Dimension(width,500));
        panelChoixCoursContainer.setLayout(new BoxLayout(panelChoixCoursContainer,axis));;
        ajoutDeCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajoutCours();
            }});
        add(panelChoixCoursContainer);
    }
    panelContainerCoursSimple(int width,List<Course> courseList,int axis,int semestre,String id) //Pour options
    {
        super();
        this.NewCourseListProgram=new ArrayList<>();
        this.NewCourseListProgramFinal=new ArrayList<>();
        this.id =id;
        System.out.println(id);
        this.semestre=semestre;
        this.courseList=courseList;
        this.width=width;
        setSize(new Dimension(width/4,500));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JButton ajoutDeCours2 = new JButton("Ajouter un Cours");
        JPanel menuCoursOption=new JPanel();
        menuCoursOption.setLayout(new BoxLayout(menuCoursOption,BoxLayout.X_AXIS));
        menuCoursOption.add(ajoutDeCours2);
        add(menuCoursOption);
        this.panelChoixCoursContainer = new JPanel();
        panelChoixCoursContainer.setBorder(new TitledBorder("Liste de cours"));
        panelChoixCoursContainer.setSize(new Dimension(width/4,500));
        panelChoixCoursContainer.setLayout(new BoxLayout(panelChoixCoursContainer,BoxLayout.Y_AXIS));
        numberCours=0;
        ajoutDeCours2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajoutCoursMultiple();
            }
        });
        add(panelChoixCoursContainer);
    }

    private List<Course> ValiderCours(List cours){
        List newFinal=new ArrayList();
        for(int i=0;i<cours.size();i++)
        {
            if(cours.get(i)!=null)
            {
                newFinal.add(cours.get(i));
            }
        }
        return newFinal;
    }
    public List<Course> getNewCourseListProgramFinal() {
        return NewCourseListProgramFinal;
    }

    public void ajoutCours() { // Cours Simple
        CourseChoicePanel panelChoixCours= new CourseChoicePanel(courseList,width/4, numberCours);
        numberCours++;
        NewCourseListProgram.add(null);
        JButton annuler=new JButton("X");
        JPanel panelTmp=new JPanel();
        panelTmp.setSize(new Dimension((width/4)-30,70));
        panelChoixCoursContainer.add(panelTmp);
        annuler.addActionListener(e2->{
            NewCourseListProgram.set(panelChoixCours.getNumberCoursSimple(),null);
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
        });
        JPanel tmpBouton=new JPanel();
        tmpBouton.setLayout(new BoxLayout(tmpBouton,BoxLayout.Y_AXIS));
        panelTmp.add(panelChoixCours);
        tmpBouton.add(Cree);
        tmpBouton.add(annuler);
        panelTmp.add(tmpBouton);
        revalidate();
        repaint();
    }
    public void ajoutCoursMultiple(){
        if(semestre==0)
        {
            JOptionPane.showMessageDialog(getParent(), "Veuillez choisir l'année et générez un identifiant");
            return;  // l'année est envoyé quand on génére l'identifiant, de toute façon on a besoin des deux.
        }
        MultipleChoiceCoursePanel panelChoixOptionsCours= new MultipleChoiceCoursePanel(courseList,width/4,numberCours,"Options",id,semestre);
        numberCours++;
        NewCourseListProgram.add(null);
        JButton annuler=new JButton("Supprimer");
        JPanel panelTmp=new JPanel();
        panelTmp.setLayout(new BoxLayout(panelTmp,BoxLayout.Y_AXIS));
        panelTmp.setSize(new Dimension((width/4)-30,70));
        this.panelChoixCoursContainer.add(panelTmp);
        annuler.addActionListener(e2->{
            NewCourseListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(),null);
            NewCourseListProgramFinal=ValiderCours(NewCourseListProgram);
            this.panelChoixCoursContainer.remove(panelTmp);
            revalidate();
            repaint();
        });
        JButton Cree=new JButton("Valider");
        Cree.addActionListener(e3->{
            Course lesCours;
            if(id.equals("SLO")) {
                System.out.println("As options");
                 lesCours = panelChoixOptionsCours.getlistChoixCoursSimple();
            }else{
                 lesCours=panelChoixOptionsCours.getlistChoixCoursSimpleAsComposite();
            }
            if(lesCours!=null) {
                NewCourseListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(), lesCours);
                NewCourseListProgramFinal = ValiderCours(NewCourseListProgram);
                panelTmp.setBackground(new Color(101, 193, 96));
            }
            else
            {
                panelTmp.setBackground(new Color(0xD41E39));
            }
        });
        JPanel tmpBouton=new JPanel();
        tmpBouton.setLayout(new BoxLayout(tmpBouton,BoxLayout.X_AXIS));
        panelTmp.add(panelChoixOptionsCours);
        tmpBouton.add(Cree);
        tmpBouton.add(annuler);
        panelTmp.add(tmpBouton);
        revalidate();
    }


    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setId(String id) {
        this.id = id;
    }
}
