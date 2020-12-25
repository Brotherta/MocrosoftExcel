package frontend.newPopUp;
import backend.Data;
import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class PopUpNewCourse extends JFrame {
    List<Object> ContenuPanels;
    private int number=0;
    Data data;
    public PopUpNewCourse() {
        super("NewCourse");
        data=new Data();
        JPanel panelGlob= new JPanel();
        panelGlob.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500,250));
        JPanel mainPanel = new JPanel();
        JPanel panelMenu=new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setMaximumSize(new Dimension(2000,50));
        mainPanel.add(panelMenu);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        ContenuPanels = new ArrayList<>();
       // ContenuPanels.add("");
        mainPanel.setBorder(new TitledBorder("Cours"));
        JButton button = new JButton("Ajouter un cours");
        button.setMaximumSize(new Dimension(50,50));
        panelMenu.add(button, BorderLayout.EAST);
        button.addActionListener(e1 -> {
            ////////////////////////////////////////////////////////////////////////////////////////////////
            ContenuPanels.add("NULL");
            JPanel paneltmp=new JPanel();
            paneltmp.setLayout(new BoxLayout(paneltmp,BoxLayout.X_AXIS));
            NewCourse newCourse = new NewCourse(number+1);
            this.number++;
            paneltmp.add(newCourse);
            JButton annuler=new JButton("Annuler");
            paneltmp.add(annuler);
            annuler.addActionListener(e2->{
                ContenuPanels.set(newCourse.getNumber(),"NULL");
                mainPanel.remove(paneltmp);
                revalidate();
                repaint();
                    });
            JButton Creer=new JButton("Creer");
            paneltmp.add(Creer);
            Creer.addActionListener(e3->{

                ContenuPanels.set(newCourse.getNumber(),newCourse.getCourse());
            });
            mainPanel.revalidate();
            mainPanel.add(paneltmp);
            revalidate();
            ////////////////////////////////////////////////////////////////////////////////////////////////
        });
        JButton validate = new JButton("Valider");
        validate.addActionListener(e3 -> {
            System.out.println(ContenuPanels);
            for(int i=0;i<ContenuPanels.size();i++)
            {
                if(!(ContenuPanels.get(i).equals("NULL")))
                {
                    System.out.println("Creation de cours");
                    List ContenuPanelsTmp= (List) ContenuPanels.get(i);
                    data.addCourse(new SimpleCourse((String)ContenuPanelsTmp.get(1),(String)ContenuPanelsTmp.get(0),Integer.parseInt((String)ContenuPanelsTmp.get(2))));
                }
            }
            System.out.println(ContenuPanels);
        });
        validate.setMaximumSize(new Dimension(50,50));
        JPanel panelFin=new JPanel();
        panelFin.setLayout(new BorderLayout());
        panelFin.setMaximumSize(new Dimension(2000,50));
        panelFin.add(validate,BorderLayout.EAST);
        ////////////////////////////////////////////////////////////////////////////////////////////////
        ContenuPanels.add("NULL");
        JPanel paneltmp=new JPanel();
        paneltmp.setLayout(new BoxLayout(paneltmp,BoxLayout.X_AXIS));
        NewCourse newCourse = new NewCourse(number+1);
        this.number++;
        paneltmp.add(newCourse);
        JButton annuler=new JButton("Annuler");
        paneltmp.add(annuler);
        annuler.addActionListener(e2->{
            ContenuPanels.set(newCourse.getNumber(),"NULL");
            mainPanel.remove(paneltmp);
            revalidate();
            repaint();
        });
        JButton Creer=new JButton("Creer");
        paneltmp.add(Creer);
        Creer.addActionListener(e3->{
            ContenuPanels.set(newCourse.getNumber(),newCourse.getCourse());
        });
        mainPanel.revalidate();
        mainPanel.add(paneltmp);
        revalidate();
        ////////////////////////////////////////////////////////////////////////////////////////////////
        getContentPane().add(new JScrollPane(mainPanel));
        panelGlob.add(panelFin,BorderLayout.SOUTH);
        validate.addActionListener(e2 -> {
        });

        panelGlob.add(mainPanel,BorderLayout.CENTER);
        add(panelGlob);
        setVisible(true);
    }/*
    public static void main(String[] args) {
        new PopUpNewCourse();
    }*/
}
