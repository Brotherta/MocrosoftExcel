package frontend.newPopUp;
import backend.program.Program;
import backend.Data;
import backend.course.*;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
public class PopUpNewProgram extends JDialog {

    JPanel panelGlobale; // Contient PanelInfoProgramme (North) , PanelCours(Center), PanelFin(SOUTH)
    JPanel panelInfoProgramme; // Contient FormatedTextField nom, ComboBOx semestre, Label ID
    JPanel panelCours; // contient PanelCoursSimple , PanelCoursOptions, PanelCours Composite, X_AXIS
    private int width=1200;
    private int numberCoursSimple;
    private int numberCoursOptions;
    private int numberCoursComposite;
    private JLabel identifiant;
    private List<Course> courseList;
    private Data data;
    private List<Object> NewCourseListProgram;
    private List<Object> NewCourseOptionsListProgram;
    private List<Object> NewCourseCompositeListProgram;

    private List<SimpleCourse> NewCourseListProgramFinal;
    private List<OptionCourse> NewCourseOptionsListProgramFinal;
    private List<CompositeCourse> NewCourseCompositeListProgramFinal;

    private panelContainerCoursSimple panelCoursSimple;
    private panelContainerCoursSimple panelCoursOptions;
    private panelContainerCoursSimple panelCoursComposite;
    public PopUpNewProgram(List<Course> courseList, Data data, JFrame main, boolean bool){
        super(main,bool);
        //Data data=new Data();
        this.data=data;
        this.courseList=courseList;
        this.NewCourseListProgram=new ArrayList<>();
        this.NewCourseOptionsListProgram=new ArrayList<>();
        this.NewCourseCompositeListProgram=new ArrayList<>();
        this.NewCourseListProgramFinal=new ArrayList<>();
        this.NewCourseOptionsListProgramFinal=new ArrayList<>();
        this.NewCourseCompositeListProgramFinal=new ArrayList<>();
       // this.NewCourseListProgramFinal=new ArrayList<>();           //Reinitialiser a chaque
       // this.NewCourseOptionsListProgramFinal=new ArrayList<>();    //revalidation de cours
       // this.NewCourseCompositeListProgramFinal=new ArrayList<>();  //pour évité un stack de faux
        //courseList= data.getCourseList();
        ////
        setMinimumSize(new Dimension(width,720));
        setPreferredSize(new Dimension(width,720));
        panelGlobale=new JPanel();
        panelGlobale.setPreferredSize(new Dimension(width,720));
        panelGlobale.setLayout(new BorderLayout());
        //////////////////PanelInfoPrograms///////////////////////////////////////
        panelInfoProgramme=new JPanel();
        panelInfoProgramme.setLayout(new BoxLayout(panelInfoProgramme,BoxLayout.X_AXIS));
        JFormattedTextField nomProgramme=new JFormattedTextField();
        nomProgramme.setBorder(new TitledBorder("Nom"));
        nomProgramme.setMaximumSize(new Dimension(200,50));
        nomProgramme.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nomProgramme.getText().equals("Le nom doit faire +de 2 caractères"))
                {
                    nomProgramme.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        this.identifiant = new JLabel();
        identifiant.setBorder(new TitledBorder("Identifiant"));
        identifiant.setMaximumSize(new Dimension(200,50));
        JComboBox semestre = new JComboBox();
        semestre.setName("Année scolaire");
        semestre.addItem("Année");
        semestre.addItem("Année 1");
        semestre.addItem("Année 2");
        semestre.addItem("Année 3");
        semestre.setMaximumSize(new Dimension(100,50));
        JButton buttonGenererId=new JButton("Generer Identifiant");
        buttonGenererId.addActionListener(e3->{
            if(nomProgramme.getText().length()>2) {
                if(semestre.getSelectedIndex()!=0) {
                    identifiant.setText(("SL" + nomProgramme.getText().charAt(0) + nomProgramme.getText().charAt(1) + semestre.getSelectedItem().toString().charAt(6)).toUpperCase());
                    semestre.setEnabled(false);
                    panelCoursOptions.setSemestre(semestre.getSelectedIndex());
                    panelCoursOptions.setId("SLO");
                    panelCoursComposite.setSemestre(semestre.getSelectedIndex());
                    panelCoursComposite.setId("SLC");
                    nomProgramme.setEditable(false);
                    semestre.setEditable(false);
                }
                else {
                    JOptionPane.showMessageDialog(main, "Veuillez choisir l'année de ce programme pour generer l'identifiant");
                    return;
                }
            }
            else{
                nomProgramme.setText("Le nom doit faire +de 2 caractères");
            }
        });
        panelInfoProgramme.add(buttonGenererId,BoxLayout.X_AXIS);
        panelInfoProgramme.add(semestre, BoxLayout.X_AXIS);
        panelInfoProgramme.add(identifiant, BoxLayout.X_AXIS);
        panelInfoProgramme.add(nomProgramme, BoxLayout.X_AXIS);
        //////////////////PanelInfoPrograms///////////////////////////////////////

        //////////////////PanelCours///////////////////////////////////////
        panelCours=new JPanel();
        panelCours.setBorder(new TitledBorder("Cours"));
        panelCours.setLayout(new BoxLayout(panelCours,BoxLayout.X_AXIS));

            //////////////PanelCoursSimple////
            this.panelCoursSimple=new panelContainerCoursSimple(width,courseList,BoxLayout.Y_AXIS);
            JScrollPane scrollPaneCoursSimple = new JScrollPane(panelCoursSimple);
            scrollPaneCoursSimple.setPreferredSize(new Dimension(width/3,500));
            scrollPaneCoursSimple.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPaneCoursSimple.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursSimple

            //////////////PanelCoursOptions////

        this.panelCoursOptions = new panelContainerCoursSimple(this.width,courseList,BoxLayout.Y_AXIS,semestre.getSelectedIndex(),"SLO");
        JScrollPane scrollPanelCoursOptions = new JScrollPane(panelCoursOptions);
        scrollPanelCoursOptions.setPreferredSize(new Dimension(width/3,500));
        scrollPanelCoursOptions.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelCoursOptions.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursOptions

            //////////////PanelCoursComposite////
        this.panelCoursComposite = new panelContainerCoursSimple(this.width,courseList,BoxLayout.Y_AXIS,semestre.getSelectedIndex(),"SLC");
        JScrollPane scrollPanelCoursComposite = new JScrollPane(panelCoursComposite);
        scrollPanelCoursOptions.setPreferredSize(new Dimension(width/3,500));
        scrollPanelCoursOptions.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelCoursOptions.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursComposite
        panelCours.add(scrollPanelCoursComposite,BoxLayout.X_AXIS);
        panelCours.add(scrollPanelCoursOptions,BoxLayout.X_AXIS);
        panelCours.add(scrollPaneCoursSimple,BoxLayout.X_AXIS);
        //////////////////PanelCours///////////////////////////////////////
        //////////////////PanelGlobal///////////////////////////////////////
        JPanel PanelFin=new JPanel();
        PanelFin.setLayout(new BorderLayout());
        JButton Terminer=new JButton("Terminer");
        Terminer.addActionListener(terminerPage -> {
            if((panelCoursSimple.getNewCourseListProgramFinal().size()!=0)||(panelCoursOptions.getNewCourseListProgramFinal().size()!=0)||(panelCoursComposite.getNewCourseListProgramFinal().size()!=0)) {
                if((identifiant.getText().length()>2))
                {
                    Program program = new Program(identifiant.getText(), nomProgramme.getText(),  (List) panelCoursSimple.getNewCourseListProgramFinal(),  (List) panelCoursOptions.getNewCourseListProgramFinal(), (List) panelCoursComposite.getNewCourseListProgramFinal());
                    data.addProgram(program);
                    dispose();
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(this, "l'identifiant doit etre generé");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Au moins un bloc de cours doit etre validé pour créé un programme valide");
        });
        PanelFin.add(Terminer,BorderLayout.EAST);
        panelGlobale.add(PanelFin,BorderLayout.SOUTH);
        panelGlobale.add(panelCours,BorderLayout.CENTER);
        panelGlobale.add(panelInfoProgramme,BorderLayout.NORTH);
        add(panelGlobale);
        //////////////////PanelGlobal///////////////////////////////////////
        setVisible(true);
    }
    private List ValiderCours(List cours){
        List newFinal=new ArrayList();
        for(int i=0;i<cours.size();i++)
        {
            if(cours.get(i)!="NULL")
            {
                newFinal.add(cours.get(i));
            }
        }
        return newFinal;
    }
/*
    public static void main(String[] args) {
        Data data=new Data();
        List list=data.getCourseList();
        list.add(new SimpleCourse("test","TEST",8));
        new popUpNewPrograms(list,data);
    }*/
}
