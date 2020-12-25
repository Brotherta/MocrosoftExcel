package frontend.newPopUp;
import backend.Data;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestPopUp implements  ActionListener{
    JButton addButton;
    JFrame frame =new JFrame("JPP");
    public int jpp=0;
    JPanel Papa;
    TestPopUp(){
       // frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addButton=new JButton("+");
        addButton.addActionListener(this);
        Papa=new JPanel();
        JPanel Fils=new JPanel();;
        addComponentsToPane(Fils,jpp);
        Papa.add(Fils);
        Papa.setLayout(new BoxLayout(Papa, BoxLayout.Y_AXIS));
        frame.add(this.Papa,BorderLayout.SOUTH);
        frame.add(addButton);
        frame.pack();
        frame.setVisible(true);
    }
    public static void addComponentsToPane(Container pane,int number) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        addAButton("Button 1", pane);
        addAButton("Button 2", pane);
        addAButton("Button 3", pane);
        addAButton("Long-Named Button 4", pane);
        addAButton(number+"", pane);
    }
    private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        container.add(button);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jpp++;
        JPanel Fils=new JPanel();
        addComponentsToPane(Fils,jpp);
        this.Papa.add(Fils);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String args[])
    {
        new TestPopUp();
    }


}
