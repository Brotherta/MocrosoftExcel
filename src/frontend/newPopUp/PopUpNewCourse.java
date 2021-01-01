package frontend.newPopUp;
import backend.Data;
import backend.course.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class PopUpNewCourse extends JDialog {
    List<Object> ContenuPanels;
    private int number=0;
    Data data;
    public PopUpNewCourse(Data data, JFrame main, boolean bool) {
        super(main, bool);
        this.data=data;
        ContenuPanels = new ArrayList<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800,500));
        setPreferredSize(new Dimension(800,500));

        JPanel panelGlob= new JPanel();
       // panelGlob.setBorder(new TitledBorder("Globale"));
        panelGlob.setLayout(new BorderLayout());
        panelGlob.setPreferredSize(new Dimension(800,500));


        JPanel mainPanel = new JPanel();
        JPanel panelMenu=new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setMaximumSize(new Dimension(800,100));
        panelGlob.add(panelMenu,BorderLayout.NORTH);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
       // mainPanel.setPreferredSize(new Dimension(720,500));
        mainPanel.setBorder(new TitledBorder("Cours"));


        JButton button = new JButton("Ajouter un cours");
        panelMenu.add(button, BorderLayout.EAST);

        button.addActionListener(e1 -> {
            ////////////////////////////////////////////////////////////////////////////////////////////////
            ContenuPanels.add("NULL");
            JPanel paneltmp=new JPanel();
            paneltmp.setLayout(new BoxLayout(paneltmp,BoxLayout.X_AXIS));
            NewCoursePanel newCourse = new NewCoursePanel(number+1);
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
                newCourse.setBackground(Color.GREEN);
            });
            mainPanel.revalidate();
            mainPanel.add(paneltmp);
            revalidate();
            ////////////////////////////////////////////////////////////////////////////////////////////////
        });
        JButton validate = new JButton("Terminer");
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
            dispose();
            System.out.println(ContenuPanels);
        });

        JPanel panelFin=new JPanel();
        panelFin.setLayout(new BorderLayout());
       // panelFin.setMaximumSize(new Dimension(1080,50));
        panelFin.add(validate,BorderLayout.EAST);
        ////////////////////////////////////////////////////////////////////////////////////////////////
        ContenuPanels.add("NULL");
        JPanel paneltmp=new JPanel();
        paneltmp.setLayout(new BoxLayout(paneltmp,BoxLayout.X_AXIS));
        NewCoursePanel newCourse = new NewCoursePanel(number+1);
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
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        //scrollPane.setMinimumSize(new Dimension(720,500));
        scrollPane.setPreferredSize(new Dimension(800,500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelGlob.add(scrollPane);

        panelGlob.add(panelFin,BorderLayout.SOUTH);
       // panelGlob.add(mainPanel,BorderLayout.CENTER);
        add(panelGlob);
        setVisible(true);
    }
/*
    public static void main(String[] args) {
        Data data = new Data();
        new PopUpNewCourse(data);
    }*/
}
