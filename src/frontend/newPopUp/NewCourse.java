package frontend.newPopUp;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
public class NewCourse extends JPanel{
    public JFormattedTextField ECTS;
    public JFormattedTextField name;
    public JComboBox semestre;
    public JLabel identifiant;
    public int number;
    public int getNumber() {
        return number-1;
    }
    public NewCourse(int number) {
        super();
        this.number=number;
        setMaximumSize(new Dimension(2000, 60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        name  = new JFormattedTextField("name");
        name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(name.getValue().toString()=="name" || (name.getValue().toString()==("Nom non remplie"))) {
                    name.setValue("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        name.setMaximumSize(new Dimension(200, 50));
        identifiant = new JLabel("Identifiant");
        identifiant.setMaximumSize(new Dimension(200, 50));
        semestre = new JComboBox();
        semestre.setName("semestre");
        semestre.addItem("Semestre 1");
        semestre.addItem("Semestre 2");
        semestre.addItem("Semestre 3");
        semestre.addItem("Semestre 4");
        semestre.addItem("Semestre 5");
        semestre.addItem("Semestre 6");
        semestre.setMaximumSize(new Dimension(200, 50));
        ECTS = new JFormattedTextField("ECTS");
        ECTS.setMaximumSize(new Dimension(200, 50));
        ECTS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if((ECTS.getValue().toString()=="ECTS")||(ECTS.getValue().toString()=="ECTS DOIT ETRE UN NOMBRE"))
                {
                    ECTS.setValue("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
            }});
      // JButton
        add(name, BorderLayout.WEST);
        add(identifiant, BorderLayout.WEST);
        add(semestre, BorderLayout.WEST);
        add(ECTS, BorderLayout.WEST);
        setBorder(new TitledBorder("Cours"+number));
    }
    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
    public Object getCourse(){
        if(isStringInt(ECTS.getValue().toString())) {
            if (!((name.getValue().equals("")) || Character.isSpaceChar(name.getValue().toString().charAt(0)))) {
                identifiant.setText("" + name.getValue().toString().charAt(0) + name.getValue().toString().charAt(1) +" "+ semestre.getSelectedItem().toString().charAt(9));
                List<String> contenu = new ArrayList();
                contenu.add(name.getValue().toString());
                contenu.add(identifiant.getText());
                contenu.add(ECTS.getValue().toString());
                System.out.println(contenu);
                return contenu;
            } else {
                name.setValue("Nom non remplie");
                return "NULL";
            }
        }
        else{
            ECTS.setValue("ECTS DOIT ETRE UN NOMBRE");
            return "NULL";
        }
    }

}
