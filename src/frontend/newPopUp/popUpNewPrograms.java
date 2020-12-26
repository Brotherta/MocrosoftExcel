package frontend.newPopUp;
import backend.program.Program;
import backend.Data;
import backend.course.*;
import backend.student.Student;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
public class popUpNewPrograms extends JFrame {

    JPanel panelGlobale; // Contient PanelInfoProgramme (North) , PanelCours(Center), PanelFin(SOUTH)
    JPanel panelInfoProgramme; // Contient FormatedTextField nom, ComboBOx semestre, Label ID
    JPanel panelCours; // contient PanelCoursSimple , PanelCoursOptions, PanelCours Composite, X_AXIS
    private int width=1200;
    private int numberCoursSimple;
    private List<Course> courseList;

    private List<Object> NewCourseListProgram;

    popUpNewPrograms(){
        super("new Program");
        Data data=new Data();
        this.NewCourseListProgram=new ArrayList<>();
        courseList= data.getCourseList();
        ////
        setMinimumSize(new Dimension(width,720));
        setPreferredSize(new Dimension(width,720));
        panelGlobale=new JPanel();
        panelGlobale.setPreferredSize(new Dimension(width,720));
        panelGlobale.setLayout(new BorderLayout());
        //////////////////PanelInfoPrograms///////////////////////////////////////
        panelInfoProgramme=new JPanel();
        panelInfoProgramme.setLayout(new BoxLayout(panelInfoProgramme,BoxLayout.X_AXIS));
        JFormattedTextField nomProgramme=new JFormattedTextField("Nom");
        nomProgramme.setMaximumSize(new Dimension(200,50));
        nomProgramme.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nomProgramme.getValue().toString()=="Nom" || (nomProgramme.getValue().toString()==("Nom non remplie"))) {
                    nomProgramme.setValue("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        JLabel identifiant = new JLabel("Identifiant");
        identifiant.setMaximumSize(new Dimension(100,50));
        JComboBox semestre = new JComboBox();
        semestre.setName("Annee scolaire");
        semestre.addItem("Annee 1");
        semestre.addItem("Annee 2");
        semestre.addItem("Annee 3");
        semestre.setMaximumSize(new Dimension(100,50));

        JButton buttonGenererId=new JButton("Generer Identifiant");
        buttonGenererId.addActionListener(e3->{
            identifiant.setText("" + nomProgramme.getValue().toString().charAt(0) + nomProgramme.getValue().toString().charAt(1) +" "+ semestre.getSelectedItem().toString().charAt(6));
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
            JButton ValiderCoursSimple = new JButton("Valider les cours");
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
                        revalidate();
                        repaint();
                    });
                    JButton Cree=new JButton("V");
                    Cree.addActionListener(e3->{
                        Course leCours=panelChoixCours.getCourse();
                        NewCourseListProgram.set(panelChoixCours.getNumberCoursSimple(),leCours);
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
            //scrollPane.setMinimumSize(new Dimension(720,500));
            scrollPaneCoursSimple.setPreferredSize(new Dimension(width/3,500));
            scrollPaneCoursSimple.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPaneCoursSimple.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursSimple

            //////////////PanelCoursOptions////
            JPanel panelCoursOptions = new JPanel();
            panelCoursOptions.setLayout(new BoxLayout(panelCoursSimple,BoxLayout.Y_AXIS));
           // PanelChoixCours panelChoixCours= new PanelChoixCours(courseList);
           // panelCoursSimple.add(panelChoixCours);
            JScrollPane scrollPanelCoursOptions = new JScrollPane(panelCoursSimple);
            //scrollPane.setMinimumSize(new Dimension(720,500));
            scrollPanelCoursOptions.setPreferredSize(new Dimension(width/3,500));
            scrollPanelCoursOptions.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPanelCoursOptions.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursSimple

            //////////////PanelCoursComposite////
            JPanel panelCoursComposite = new JPanel();
            panelCoursOptions.setLayout(new BoxLayout(panelCoursSimple,BoxLayout.Y_AXIS));
            // PanelChoixCours panelChoixCours= new PanelChoixCours(courseList);
            // panelCoursSimple.add(panelChoixCours);
            JScrollPane scrollPanelCoursComposite = new JScrollPane(panelCoursSimple);
            //scrollPane.setMinimumSize(new Dimension(720,500));
            scrollPanelCoursComposite.setPreferredSize(new Dimension(width/3,500));
            scrollPanelCoursComposite.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPanelCoursComposite.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            //////////PanelCoursSimple


        panelCours.add(scrollPaneCoursSimple,BoxLayout.X_AXIS);
        panelCours.add(scrollPanelCoursOptions,BoxLayout.X_AXIS);
        panelCours.add(scrollPanelCoursComposite,BoxLayout.X_AXIS);
        //////////////////PanelCours///////////////////////////////////////
        //////////////////PanelGlobal///////////////////////////////////////
        JPanel PanelFin=new JPanel();
        PanelFin.setLayout(new BorderLayout());
        JButton Terminer=new JButton("Terminer");
        Terminer.addActionListener(terminerPage -> {
            System.out.println(NewCourseListProgram);
            dispose();
        });
        PanelFin.add(Terminer,BorderLayout.EAST);
        panelGlobale.add(PanelFin,BorderLayout.SOUTH);
        panelGlobale.add(panelCours,BorderLayout.CENTER);
        panelGlobale.add(panelInfoProgramme,BorderLayout.NORTH);
        add(panelGlobale);
        //////////////////PanelGlobal///////////////////////////////////////
        setVisible(true);

    }

    public static void main(String[] args) {
        new popUpNewPrograms();
    }
}
