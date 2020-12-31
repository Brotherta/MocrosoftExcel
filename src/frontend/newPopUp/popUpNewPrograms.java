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
public class popUpNewPrograms extends JDialog {

    JPanel panelGlobale; // Contient PanelInfoProgramme (North) , PanelCours(Center), PanelFin(SOUTH)
    JPanel panelInfoProgramme; // Contient FormatedTextField nom, ComboBOx semestre, Label ID
    JPanel panelCours; // contient PanelCoursSimple , PanelCoursOptions, PanelCours Composite, X_AXIS
    private int width=1200;
    private int numberCoursSimple;
    private int numberCoursOptions;
    private int numberCoursComposite;
    JLabel identifiant;
    private List<Course> courseList;
    Data data;
    private List<Object> NewCourseListProgram;
    private List<Object> NewCourseOptionsListProgram;
    private List<Object> NewCourseCompositeListProgram;

    private List<SimpleCourse> NewCourseListProgramFinal;
    private List<OptionCourse> NewCourseOptionsListProgramFinal;
    private List<CompositeCourse> NewCourseCompositeListProgramFinal;

    public popUpNewPrograms(List<Course> courseList,Data data, JFrame main, boolean bool){
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
        semestre.setName("Annee scolaire");
        semestre.addItem("Annee 1");
        semestre.addItem("Annee 2");
        semestre.addItem("Annee 3");
        semestre.setMaximumSize(new Dimension(100,50));
        JButton buttonGenererId=new JButton("Generer Identifiant");
        buttonGenererId.addActionListener(e3->{
            if(nomProgramme.getText().length()>2) {
                identifiant.setText(("SL" + nomProgramme.getText().charAt(0) + nomProgramme.getText().charAt(1) + semestre.getSelectedItem().toString().charAt(6)).toUpperCase());
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
            JPanel panelCoursSimple = new JPanel();
            panelCoursSimple.setSize(new Dimension(width/4,500));
            panelCoursSimple.setLayout(new BoxLayout(panelCoursSimple,BoxLayout.Y_AXIS));
            JButton ajoutDeCours = new JButton("Ajouter un Cours");
            JPanel menuCoursSimple=new JPanel();
            menuCoursSimple.setLayout(new BoxLayout(menuCoursSimple,BoxLayout.X_AXIS));
            menuCoursSimple.add(ajoutDeCours);
            panelCoursSimple.add(menuCoursSimple);
            JPanel panelChoixCoursContainer = new JPanel();
            panelChoixCoursContainer.setBorder(new TitledBorder("Liste Cours"));
            panelChoixCoursContainer.setSize(new Dimension(width/4,500));
            panelChoixCoursContainer.setLayout(new BoxLayout(panelChoixCoursContainer,BoxLayout.Y_AXIS));
            numberCoursSimple=0;
            ajoutDeCours.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PanelChoixCours panelChoixCours= new PanelChoixCours(courseList,width/4,numberCoursSimple);
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
                    });
                    JPanel tmpBouton=new JPanel();
                    tmpBouton.setLayout(new BoxLayout(tmpBouton,BoxLayout.Y_AXIS));
                    panelTmp.add(panelChoixCours);
                    tmpBouton.add(Cree);
                    tmpBouton.add(annuler);
                    panelTmp.add(tmpBouton);
                    revalidate();
                }
            });

            panelCoursSimple.add(panelChoixCoursContainer);
            JScrollPane scrollPaneCoursSimple = new JScrollPane(panelCoursSimple);
            scrollPaneCoursSimple.setPreferredSize(new Dimension(width/3,500));
            scrollPaneCoursSimple.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPaneCoursSimple.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursSimple

            //////////////PanelCoursOptions////
            JPanel panelCoursOptions = new JPanel();
            panelCoursOptions.setSize(new Dimension(width/4,500));
            panelCoursOptions.setLayout(new BoxLayout(panelCoursOptions,BoxLayout.Y_AXIS));
            JButton ajoutDeCours2 = new JButton("Ajouter un Cours");
            JPanel menuCoursOption=new JPanel();
            menuCoursOption.setLayout(new BoxLayout(menuCoursOption,BoxLayout.X_AXIS));
            menuCoursOption.add(ajoutDeCours2);
            //menuCoursOption.add(ValiderCoursOptions);
            panelCoursOptions.add(menuCoursOption);
            JPanel panelChoixCoursOptionsContainer = new JPanel();
            panelChoixCoursOptionsContainer.setBorder(new TitledBorder("Liste Options"));
            panelChoixCoursOptionsContainer.setSize(new Dimension(width/4,500));
            panelChoixCoursOptionsContainer.setLayout(new BoxLayout(panelChoixCoursOptionsContainer,BoxLayout.Y_AXIS));
            numberCoursOptions=0;
            ajoutDeCours2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelChoixOptionsCours panelChoixOptionsCours= new PanelChoixOptionsCours(courseList,width/4,numberCoursOptions,"Options",identifiant.getText());
                numberCoursOptions++;
                NewCourseOptionsListProgram.add("NULL");
                JButton annuler=new JButton("Supprimer l'options");
                JPanel panelTmp=new JPanel();
                panelTmp.setLayout(new BoxLayout(panelTmp,BoxLayout.Y_AXIS));
                panelTmp.setSize(new Dimension((width/4)-30,70));
                panelChoixCoursOptionsContainer.add(panelTmp);
                annuler.addActionListener(e2->{
                    NewCourseOptionsListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(),"NULL");
                    NewCourseOptionsListProgramFinal=ValiderCours(NewCourseOptionsListProgram);
                    panelChoixCoursOptionsContainer.remove(panelTmp);
                    revalidate();
                    repaint();
                });
                JButton Cree=new JButton("Valider l'options");
                Cree.addActionListener(e3->{
                    OptionCourse lesCours=panelChoixOptionsCours.getlistChoixCoursSimple();
                    if(lesCours!=null) {
                        NewCourseOptionsListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(), lesCours);
                        NewCourseOptionsListProgramFinal = ValiderCours(NewCourseOptionsListProgram);
                        panelTmp.setBackground(Color.GREEN);
                    }
                    else
                    {
                        panelTmp.setBackground(Color.RED);
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
        });
        panelCoursOptions.add(panelChoixCoursOptionsContainer);
        JScrollPane scrollPanelCoursOptions = new JScrollPane(panelCoursOptions);
        scrollPanelCoursOptions.setPreferredSize(new Dimension(width/3,500));
        scrollPanelCoursOptions.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanelCoursOptions.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursOptions

            //////////////PanelCoursComposite////
        JPanel panelCoursComposite = new JPanel();
        panelCoursComposite.setSize(new Dimension(width/4,250));
        panelCoursComposite.setLayout(new BoxLayout(panelCoursComposite,BoxLayout.Y_AXIS));
        JButton ajoutDeCours3 = new JButton("Ajouter un Cours");
        JPanel menuCoursComposite=new JPanel();
        menuCoursComposite.setLayout(new BoxLayout(menuCoursComposite,BoxLayout.X_AXIS));
        menuCoursComposite.add(ajoutDeCours3);
        panelCoursComposite.add(menuCoursComposite);
        JPanel panelChoixCoursCompositeContainer = new JPanel();
        panelChoixCoursCompositeContainer.setBorder(new TitledBorder("Liste Composite"));
        panelChoixCoursCompositeContainer.setSize(new Dimension(width/4,500));
        panelChoixCoursCompositeContainer.setLayout(new BoxLayout(panelChoixCoursCompositeContainer,BoxLayout.Y_AXIS));
        numberCoursComposite=0;
        ajoutDeCours3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelChoixOptionsCours panelChoixOptionsCours= new PanelChoixOptionsCours(courseList,width/4,numberCoursComposite,"Composite",identifiant.getText());
                numberCoursComposite++;
                NewCourseCompositeListProgram.add("NULL");
                JButton annuler=new JButton("Supprimer");
                JPanel panelTmp=new JPanel();
                panelTmp.setLayout(new BoxLayout(panelTmp,BoxLayout.Y_AXIS));
                panelTmp.setSize(new Dimension((width/4)-30,70));
                panelChoixCoursCompositeContainer.add(panelTmp);
                annuler.addActionListener(e2->{
                    NewCourseCompositeListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(),"NULL");
                    NewCourseCompositeListProgramFinal=ValiderCours(NewCourseCompositeListProgram);
                    panelChoixCoursCompositeContainer.remove(panelTmp);
                    revalidate();
                    repaint();
                });
                JButton Cree=new JButton("Valider");
                Cree.addActionListener(e3->{
                    CompositeCourse lesCours=panelChoixOptionsCours.getlistChoixCoursSimpleAsComposite();
                    if(lesCours!=null) {
                        NewCourseCompositeListProgram.set(panelChoixOptionsCours.getNumberCoursOptions(), lesCours);
                        panelTmp.setBackground(Color.green);
                        NewCourseCompositeListProgramFinal=ValiderCours(NewCourseCompositeListProgram);
                    }
                    else {
                        panelTmp.setBackground(Color.RED);
                    }
                });
                JPanel tmpBouton=new JPanel();
                tmpBouton.setLayout(new BoxLayout(tmpBouton,BoxLayout.X_AXIS));
                panelTmp.add(panelChoixOptionsCours);
                tmpBouton.add(Cree);
                tmpBouton.add(annuler);
                panelTmp.add(tmpBouton);
                revalidate();
                // repaint();
            }
        });
        panelCoursComposite.add(panelChoixCoursCompositeContainer);
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
            if((NewCourseListProgramFinal.size()!=0)||(NewCourseOptionsListProgramFinal.size()!=0)||(NewCourseCompositeListProgramFinal.size()!=0)) {
                if((identifiant.getText().length()>2))
                {
                    Program program = new Program(identifiant.getText(), nomProgramme.getText(), NewCourseListProgramFinal, NewCourseOptionsListProgramFinal, NewCourseCompositeListProgramFinal);
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
