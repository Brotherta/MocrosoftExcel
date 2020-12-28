package frontend.newPopUp;

import backend.xml.XmlReader;
import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
public class PanelChoixOptionsCours extends JPanel{
    private List<Course> courseList;
    private int numberCoursOptions;

    private List<PanelChoixCours> panelChoixCoursList;
    private List<Object> listChoixCoursSimple;
    private List<SimpleCourse> ListeCoursSimpleFinal;
    private int numeroOptionsUntiedToProgram;
    private JLabel identifiantOptions;
    private JLabel semestreOptions;
    private JButton addButtonCours;
    private JPanel panelContainerCours;
    private JLabel ECTS;
    private int numeroCoursIn;
    private JFormattedTextField nameOptions;
    public int getNumberCoursOptions() {
        return numberCoursOptions;
    }
    private void EctsCount(){
        int etcs=0;
        for(int i=0;i<ListeCoursSimpleFinal.size();i++)
        {
            etcs+=ListeCoursSimpleFinal.get(i).getCredits();

        }
       ECTS.setText(""+etcs);
    }
    private void GenerationId()
    {
        String idGene= new String("");
        for(int i=0;i<ListeCoursSimpleFinal.size();i++)
        {
            idGene+=ListeCoursSimpleFinal.get(i).getName().charAt(0);
            idGene+=ListeCoursSimpleFinal.get(i).getName().charAt(1);
        }
        identifiantOptions.setText((nameOptions.getText()+idGene).toUpperCase());
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
        GenerationId();
        return new OptionCourse(this.identifiantOptions.getText(),this.nameOptions.getText(),ListeCoursSimpleFinal);
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
        GenerationId();
        EctsCount();
        return new CompositeCourse(this.identifiantOptions.getText(),this.nameOptions.getText(),ListeCoursSimpleFinal);
    }
    PanelChoixOptionsCours(List<Course> courseList, int width, int numberCoursOptions,String optionsOuCompo)
    {
        super();
        this.numeroCoursIn=0;
        this.listChoixCoursSimple=new ArrayList<>();
        this.numberCoursOptions=numberCoursOptions;
        this.nameOptions= new JFormattedTextField("nom");
        nameOptions.setBorder(BorderFactory.createLineBorder(Color.black));
        this.ECTS=new JLabel("ECTS");
        ECTS.setBorder(BorderFactory.createLineBorder(Color.black));
        nameOptions.setMaximumSize(new Dimension(100,50));
        nameOptions.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if((nameOptions.getValue().toString()=="nom")||(nameOptions.getValue().toString()=="a remplir"))
                {
                    nameOptions.setValue("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }});
        setBorder(new TitledBorder(optionsOuCompo+(numberCoursOptions+1)));
        setSize(new Dimension(width,50));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        identifiantOptions = new JLabel("ID");
        identifiantOptions.setBorder(BorderFactory.createLineBorder(Color.black));
        semestreOptions = new JLabel("Semestre");
        semestreOptions.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel panelInfoOptions = new JPanel();
        panelInfoOptions.setLayout(new BoxLayout(panelInfoOptions,BoxLayout.X_AXIS));
        panelInfoOptions.setPreferredSize(new Dimension(width/3,50));
        panelInfoOptions.add(nameOptions);
        panelInfoOptions.add(identifiantOptions);
        panelInfoOptions.add(semestreOptions);
        panelInfoOptions.add(ECTS);


        panelContainerCours = new JPanel();
        panelContainerCours.setLayout(new BoxLayout(panelContainerCours,BoxLayout.Y_AXIS));
        ////////////// Les deux premieres options car le choix entre rien et rien c pas ouf \\\\\\\\\\
        PanelChoixCours panelChoixCours1=new PanelChoixCours(courseList,width,numeroCoursIn);
        numeroCoursIn++;
        listChoixCoursSimple.add("NULL");
        JPanel panelTmp1=new JPanel();
        panelTmp1.setSize(new Dimension((width/4)-30,70));
        panelTmp1.add(panelChoixCours1);
        panelContainerCours.add(panelTmp1);
        JPanel panelOptionsTmp1=new JPanel();
        panelOptionsTmp1.setLayout(new BoxLayout(panelOptionsTmp1,BoxLayout.Y_AXIS));
        JButton annuler1=new JButton("X");
        annuler1.addActionListener(e2->{
            listChoixCoursSimple.set(panelChoixCours1.getNumberCoursSimple(),"NULL");
            panelContainerCours.remove(panelTmp1);
            revalidate();
            repaint();
        });
        JButton Cree1=new JButton("V");
        Cree1.addActionListener(e3->{
            Course leCours=panelChoixCours1.getCourse();
            listChoixCoursSimple.set(panelChoixCours1.getNumberCoursSimple(),leCours);
        });
        panelOptionsTmp1.add(Cree1);
        panelOptionsTmp1.add(annuler1);
        panelTmp1.add(panelOptionsTmp1);

        PanelChoixCours panelChoixCours2=new PanelChoixCours(courseList,width,numeroCoursIn);
        numeroCoursIn++;
        listChoixCoursSimple.add("NULL");
        JPanel panelTmp2=new JPanel();
        panelTmp2.setSize(new Dimension((width/4)-30,70));
        panelTmp2.add(panelChoixCours2);
        panelContainerCours.add(panelTmp2);
        JPanel panelOptionsTmp2=new JPanel();
        panelOptionsTmp2.setLayout(new BoxLayout(panelOptionsTmp2,BoxLayout.Y_AXIS));
        JButton annuler2=new JButton("X");
        annuler2.addActionListener(e2->{
            listChoixCoursSimple.set(panelChoixCours2.getNumberCoursSimple(),"NULL");
            panelContainerCours.remove(panelTmp2);
            revalidate();
            repaint();
        });
        JButton Cree2=new JButton("V");
        Cree2.addActionListener(e3->{
            Course leCours=panelChoixCours2.getCourse();
            listChoixCoursSimple.set(panelChoixCours2.getNumberCoursSimple(),leCours);
        });
        panelOptionsTmp2.add(Cree2);
        panelOptionsTmp2.add(annuler2);
        panelTmp2.add(panelOptionsTmp2);
        panelContainerCours.add(panelTmp1);
        panelContainerCours.add(panelTmp2);
        ////////////// Les deux premieres options car le choix entre rien et rien c pas ouf \\\\\\\\\

        addButtonCours=new JButton("+");
        addButtonCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelChoixCours panelChoixCours=new PanelChoixCours(courseList,width,numeroCoursIn);
                numeroCoursIn++;
                listChoixCoursSimple.add("NULL");
                JPanel panelTmp=new JPanel();
                panelTmp.setSize(new Dimension((width/4)-30,70));
                panelTmp.add(panelChoixCours);
                panelContainerCours.add(panelTmp);
                JPanel panelOptionsTmp=new JPanel();
                panelOptionsTmp.setLayout(new BoxLayout(panelOptionsTmp,BoxLayout.Y_AXIS));
                JButton annuler=new JButton("X");
                annuler.addActionListener(e2->{
                    listChoixCoursSimple.set(panelChoixCours.getNumberCoursSimple(),"NULL");
                    panelContainerCours.remove(panelTmp);
                    revalidate();
                    repaint();

                });
                JButton Cree=new JButton("V");
                Cree.addActionListener(e3->{
                    Course leCours=panelChoixCours.getCourse();
                    listChoixCoursSimple.set(panelChoixCours.getNumberCoursSimple(),leCours);
                });
                panelOptionsTmp.add(Cree);
                panelOptionsTmp.add(annuler);
                panelTmp.add(panelOptionsTmp);
                revalidate();
                repaint();
            }
        });

        add(panelInfoOptions);
        add(panelContainerCours);
        add(addButtonCours);


    }

}
