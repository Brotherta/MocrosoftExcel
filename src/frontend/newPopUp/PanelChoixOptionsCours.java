package frontend.newPopUp;

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
public class PanelChoixOptionsCours extends JPanel{
    private List<Course> courseList;
    private int numberCoursOptions;
    private List<Object> listChoixCoursSimple;
    private List<SimpleCourse> ListeCoursSimpleFinal;
    private JLabel identifiantOptions;
    private JLabel semestreOptions;
    private JButton addButtonCours;
    private JPanel panelContainerCours;
    private JLabel ECTS;
    private int numeroCoursIn;
    private JFormattedTextField nameOptions;
    private int width;
    public int getNumberCoursOptions() {
        return numberCoursOptions;
    }
    private boolean setSemestre(){
        String id=ListeCoursSimpleFinal.get(0).getId();
        int j=0;
        for ( j = 0; j < id.length(); j++) {
            if (Character.isDigit(id.charAt(j))) {
                break;
            }
        }
        char idToSemestre=id.charAt(j);

        for(int i =1;i<ListeCoursSimpleFinal.size();i++) {
            String id2=ListeCoursSimpleFinal.get(i).getId();
            char idToSemestre2;
            int w = 0;
            for (w=0; j < id.length(); w++) {
                if (Character.isDigit(id2.charAt(w))) {
                    break;
                }
            }
            idToSemestre2=id2.charAt(w);
            if(idToSemestre!=idToSemestre2)
            {
                JOptionPane.showMessageDialog(this, "Les cours d'un meme bloc doit etre au meme semestres, merci de modifier les cours");
                return false;
            }
        }
        semestreOptions.setText("S "+idToSemestre);
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
        if(ListeCoursSimpleFinal.size()==0){ panelContainerCours.setBackground(Color.RED);return null ;}
        if(setSemestre()) {
            int ects = EctsCount();
            if (ects % ListeCoursSimpleFinal.get(0).getCredits() != 0) {
                JOptionPane.showMessageDialog(this, "Toute les options doivent avoir le meme nombre d'ects.\n Merci de modifier les cours");
                return null;
            }
            ECTS.setText("" + ListeCoursSimpleFinal.get(0).getCredits());
            GenerationId();
            return new OptionCourse(this.identifiantOptions.getText(), this.nameOptions.getText(), ListeCoursSimpleFinal);
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
        if(ListeCoursSimpleFinal.size()==0){ return null ;}
        if(setSemestre()) {
            GenerationId();
            int ects = EctsCount();
            ECTS.setText("" + ects);
            return new CompositeCourse(this.identifiantOptions.getText(), this.nameOptions.getText(), ListeCoursSimpleFinal);
        }
        return null;
    }
    PanelChoixOptionsCours(List<Course> courseList, int width, int numberCoursOptions,String optionsOuCompo)
    {
        super();
        setOpaque(false);
        this.numeroCoursIn=0;
        this.courseList = courseList;
        this.listChoixCoursSimple=new ArrayList<>();
        this.numberCoursOptions=numberCoursOptions;
        this.width=width;
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
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(new Dimension(width,50));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        identifiantOptions = new JLabel("ID");
        identifiantOptions.setBorder(BorderFactory.createLineBorder(Color.black));
        semestreOptions = new JLabel("Semestre");
        semestreOptions.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel panelInfoOptions = new JPanel();
        panelInfoOptions.setOpaque(false);
        panelInfoOptions.setLayout(new BoxLayout(panelInfoOptions,BoxLayout.X_AXIS));
        panelInfoOptions.setPreferredSize(new Dimension(width/3,50));
        panelInfoOptions.add(nameOptions);
        panelInfoOptions.add(identifiantOptions);
        panelInfoOptions.add(semestreOptions);
        panelInfoOptions.add(ECTS);
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
        add(panelInfoOptions);
        add(panelContainerCours);
        add(addButtonCours);
    }
    private void actionAjout(){
        PanelChoixCours panelChoixCours=new PanelChoixCours(this.courseList,this.width,numeroCoursIn);

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
