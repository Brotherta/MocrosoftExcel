package frontend.newPopUp;

import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class MultipleChoiceCoursePanel extends JPanel{
    private final String prefixeId;
    private List<Course> courseList;
    private int numberCoursOptions;
    private List<Object> listChoixCoursSimple;
    private List<SimpleCourse> ListeCoursSimpleFinal;
    private JTextField identifiantOptions;
    private JTextField semestreOptions;
    private JButton addButtonCours;
    private JPanel panelContainerCours;
    private JTextField ECTS;
    private int numeroCoursIn;
    private int anneeProgramLie;
    private int width;
    public int getNumberCoursOptions() {
        return numberCoursOptions;
    }
    private boolean setSemestre(){
        String id=ListeCoursSimpleFinal.get(0).getId();
        int j=0;
        int numeroSemestrePremierCours=0;
        for ( j = 0; j < id.length(); j++) {
            if (Character.isDigit(id.charAt(j))) {
                numeroSemestrePremierCours=Character.getNumericValue(id.charAt(j));
                System.out.println("Numero du premier cours"+numeroSemestrePremierCours);
                if(numeroSemestrePremierCours % 2 == 0){numeroSemestrePremierCours = 2;}
                else {numeroSemestrePremierCours = numeroSemestrePremierCours%2;}
                break;
            }
        }
        for(int i =1;i<ListeCoursSimpleFinal.size();i++) {
            String id2=ListeCoursSimpleFinal.get(i).getId();
            int numeroSemestreAutreCours;
            int w = 0;
            for (w=0; j < id.length(); w++) {
                if (Character.isDigit(id2.charAt(w))) {
                    numeroSemestreAutreCours = Character.getNumericValue(id2.charAt(w));
                    System.out.println("Numero du Second cours"+numeroSemestreAutreCours);
                    if(numeroSemestreAutreCours % 2 == 0){numeroSemestreAutreCours = 2;}
                    else {numeroSemestreAutreCours = numeroSemestreAutreCours%2;}
                    if(numeroSemestrePremierCours!=numeroSemestreAutreCours) {
                        JOptionPane.showMessageDialog(this, "Les cours d'un meme bloc doit etre au meme semestres(1/3/5 ou 2/4/6), merci de modifier les cours");
                        return false;
                    }
                    break;
                }
            }
        }

        semestreOptions.setText(""+(numeroSemestrePremierCours+(anneeProgramLie-1)*2));
        return true;
    }
    private int EctsCount(){
        int ects=0;
        for(int i=0;i<ListeCoursSimpleFinal.size();i++)
        {
            ects+=ListeCoursSimpleFinal.get(i).getCredits();
        }
       //ECTS.setText(""+etcs);
        return ects;
    }
    private void GenerationId()
    {
        String idGene= new String("");
        for(int i=0;i<ListeCoursSimpleFinal.size();i++)
        {
            idGene+=ListeCoursSimpleFinal.get(i).getName().charAt(0);
            idGene+=ListeCoursSimpleFinal.get(i).getName().charAt(1);
        }
        identifiantOptions.setText(prefixeId+idGene.toUpperCase()+semestreOptions.getText()+(numberCoursOptions+1));
    }
    public OptionCourse getlistChoixCoursSimple(){
        ListeCoursSimpleFinal=new ArrayList<>();
        for(int i=0;i<listChoixCoursSimple.size();i++)
        {
            if(listChoixCoursSimple.get(i)!="NULL")
            {
                ListeCoursSimpleFinal.add((SimpleCourse) listChoixCoursSimple.get(i));
            }
        }
        if(ListeCoursSimpleFinal.size()==0){ /*panelContainerCours.setBackground(Color.RED);*/return null ;}
        if(setSemestre()) {
            int ects = EctsCount();
            if (ects % ListeCoursSimpleFinal.get(0).getCredits() != 0) {
                JOptionPane.showMessageDialog(this, "Toute les options doivent avoir le meme nombre d'ects.\n Merci de modifier les cours");
                return null;
            }
            ECTS.setText("" + ListeCoursSimpleFinal.get(0).getCredits());
            GenerationId();
            return new OptionCourse(this.identifiantOptions.getText(), "OPTION"+(numberCoursOptions+1)+" S"+semestreOptions.getText()+" "+identifiantOptions.getText(), ListeCoursSimpleFinal);
        }
        return null;
    }
    public CompositeCourse getlistChoixCoursSimpleAsComposite(){
        ListeCoursSimpleFinal=new ArrayList<>();
        for(int i=0;i<listChoixCoursSimple.size();i++)
        {
            if(listChoixCoursSimple.get(i)!="NULL")
            {
                ListeCoursSimpleFinal.add((SimpleCourse) listChoixCoursSimple.get(i));
            }
        }
        if(ListeCoursSimpleFinal.size()==0){ /*panelContainerCours.setBackground(Color.RED);*/return null ;}
        if(setSemestre()) {
            GenerationId();
            int ects = EctsCount();
            ECTS.setText("" + ects);
            return new CompositeCourse(this.identifiantOptions.getText(), "Composite"+(numberCoursOptions+1)+identifiantOptions.getText(), ListeCoursSimpleFinal);
        }
        return null;
    }
    MultipleChoiceCoursePanel(List<Course> courseList, int width, int numberCoursOptions, String optionsOuCompo, String prefixeId, int anneeProgramLie)
    {
        super();
        setOpaque(false);
        this.numeroCoursIn=0;
        this.courseList = courseList;
        this.listChoixCoursSimple=new ArrayList<>();
        this.numberCoursOptions=numberCoursOptions;
        this.width=width;
        this.prefixeId="SL";   // on commence tout les id de programme par SL.
        this.anneeProgramLie=anneeProgramLie;
        setBorder(new TitledBorder(optionsOuCompo+(numberCoursOptions+1)));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BorderLayout());

        JPanel containerInfoOptions=new JPanel();
        containerInfoOptions.setLayout(new BoxLayout(containerInfoOptions,BoxLayout.X_AXIS));
        this.ECTS=new JTextField("",12);
        ECTS.setEditable(false);
        this.ECTS.setBorder(new TitledBorder("ECTS"));
        identifiantOptions = new JTextField("",12);
        identifiantOptions.setEditable(false);
        identifiantOptions.setBorder(new TitledBorder("Identifiant"));
        identifiantOptions.setSize(new Dimension(width,70));
        semestreOptions = new JTextField("",12);
        semestreOptions.setEditable(false);
        semestreOptions.setMaximumSize(new Dimension(width,70));
        semestreOptions.setBorder(new TitledBorder("Semestre"));

        JPanel panelInfoOptions = new JPanel();
       // panelInfoOptions.setBorder(new TitledBorder("TEST"));
        panelInfoOptions.setOpaque(false);
        panelInfoOptions.setLayout(new BorderLayout());
        panelInfoOptions.setPreferredSize(new Dimension(width,70));


        //containerInfoOptions.setPreferredSize(new Dimension(width,50));
        containerInfoOptions.add(identifiantOptions);
        containerInfoOptions.add(semestreOptions);
        containerInfoOptions.add(ECTS);
        panelInfoOptions.add(containerInfoOptions,BorderLayout.WEST);
        this.panelContainerCours = new JPanel();
        panelContainerCours.setOpaque(false);
        panelContainerCours.setOpaque(false);
        panelContainerCours.setLayout(new BoxLayout(panelContainerCours,BoxLayout.Y_AXIS));
        ////////////// Les deux premieres options car le choix entre rien et rien c pas ouf \\\\\\\\\\
        actionAjout();
        ///////////////////////////////////
        actionAjout();
        addButtonCours=new JButton("+");
        addButtonCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionAjout();
            }
        });
        add(panelInfoOptions,BorderLayout.NORTH);
        add(panelContainerCours,BorderLayout.CENTER);
        add(addButtonCours,BorderLayout.SOUTH);
    }
    private void actionAjout(){
        CourseChoicePanel panelChoixCours=new CourseChoicePanel(this.courseList,this.width,numeroCoursIn);
        this.numeroCoursIn++;
        listChoixCoursSimple.add("NULL");
        JPanel panelTmp=new JPanel();
        panelTmp.setOpaque(false);
        panelTmp.setSize(new Dimension((width/4)-30,70));
        panelTmp.add(panelChoixCours);
        this.panelContainerCours.add(panelTmp);
        JPanel panelOptionsTmp=new JPanel();
        panelOptionsTmp.setOpaque(false);
        panelOptionsTmp.setLayout(new BoxLayout(panelOptionsTmp,BoxLayout.Y_AXIS));
        JButton annuler=new JButton("X");
        annuler.addActionListener(e2->{
            this.listChoixCoursSimple.set(panelChoixCours.getNumberCoursSimple(),"NULL");
            this.panelContainerCours.remove(panelTmp);
            this.revalidate();
            this.repaint();
        });
        JButton Cree=new JButton("V");
        Cree.addActionListener(e3->{
            Course leCours=panelChoixCours.getCourse();
            this.listChoixCoursSimple.set(panelChoixCours.getNumberCoursSimple(),leCours);
        });
        panelOptionsTmp.add(Cree);
        panelOptionsTmp.add(annuler);
        panelTmp.add(panelOptionsTmp);
        this.panelContainerCours.add(panelTmp);
        revalidate();
        repaint();
    }


}
