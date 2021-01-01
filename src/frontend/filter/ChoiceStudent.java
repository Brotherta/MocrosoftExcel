package frontend.filter;

import backend.Data;
import backend.student.Student;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoiceStudent extends JDialog {
    List<String> infos;
    GetInfosPanel getInfosPanel;
    List<Student> resultSearchStudent;
    Student student;
    JPanel inChooseStudentPanel;

    public ChoiceStudent(Data data, Filter studentFilter, JFrame main, boolean bool){


        super(main, bool);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,500);
        setLayout(new BoxLayout(super.getContentPane(),BoxLayout.Y_AXIS));


        /// Create Elements
        JPanel textPanel = new JPanel();
        JLabel text = new JLabel("Entrez des mots clefs puis sélectionnez l'élève voulu:");
        textPanel.add(text);

        getInfosPanel = new GetInfosPanel();
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Chercher");
        searchPanel.add(searchButton);
        JPanel checkPanel = new JPanel();
        JButton checkButton = new JButton("Valider");
        checkPanel.add(checkButton);

        // Set variables
        resultSearchStudent = data.getStudentList();
        resultSearchStudent.removeAll(studentFilter.getStudentsFilter());


        /// RadioButton ChooseStudent
        JPanel chooseStudentPanel = new JPanel();
        chooseStudentPanel.setLayout(new BorderLayout());
        chooseStudentPanel.setBorder(new LineBorder(Color.BLACK));

        buttonChooseStudentPanel();
        chooseStudentPanel.add(new JScrollPane(inChooseStudentPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        searchButton.addActionListener(e -> {
            infos = getInfosPanel.getInfos();
            List<Student> sortedStudent = sortStudent(data,infos.get(0),infos.get(1),infos.get(2));
            if (!(sortedStudent.size() == data.getStudentList().size())){
                resultSearchStudent.clear();
                for (Student student: sortedStudent){
                    resultSearchStudent.add(student);
                }
            }
            else {
                resultSearchStudent = data.getStudentList();
                resultSearchStudent.removeAll(studentFilter.getStudentsFilter());
            }
            chooseStudentPanel.removeAll();
            buttonChooseStudentPanel();
            chooseStudentPanel.add(new JScrollPane(inChooseStudentPanel));
            chooseStudentPanel.revalidate();

        });

        // Fermer la fenêtre
        checkButton.addActionListener(e -> {
            dispose();
        });



        // Ajout dans la JFrame
        add(textPanel);
        add(getInfosPanel);
        add(searchPanel);
        add(chooseStudentPanel);
        add(checkPanel);
        setVisible(true);
    }

    private void buttonChooseStudentPanel(){
        inChooseStudentPanel = new JPanel();
        inChooseStudentPanel.setLayout(new BoxLayout(inChooseStudentPanel,BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        for (int i=0; i<resultSearchStudent.size(); i++){
            Student student = resultSearchStudent.get(i);
            JRadioButton studentButton = new JRadioButton(student.getName()+" - "+student.getSurname()+" - "+student.getStudentId());
            studentButton.setActionCommand(student.getStudentId());
            group.add(studentButton);
            inChooseStudentPanel.add(studentButton);
            studentButton.addActionListener(e -> {
                this.student = getStudentById(this.resultSearchStudent,e.getActionCommand());
            });
        }


    }

    private Student getStudentById(List<Student> studentList, String id){
        for (Student student : studentList){
            if (student.getStudentId().equals(id)){
                return student;
            }
        }
        return null;
    }

    private List<Student> sortStudent(Data data,String name, String surname, String id){
        List<Student> allStudent = data.getStudentList();
        List<Student> sortedStudent = new ArrayList<>();
        for (Student student : allStudent){
            if (student.getName().toLowerCase().contains(name.toLowerCase())
                    && student.getSurname().toLowerCase().contains(surname.toLowerCase())
                    && student.getStudentId().toLowerCase().contains(id.toLowerCase()))
            {
                sortedStudent.add(student);
            }
        }
        return sortedStudent;
    }



    private class GetInfosPanel extends JPanel{
        JTextField name;
        JTextField surname;
        JTextField id;

        public GetInfosPanel(){
            name = new JTextField();
            surname = new JTextField();
            id = new JTextField();

            name.setBorder(new TitledBorder("Nom"));
            surname.setBorder(new TitledBorder("Prénom"));
            id.setBorder(new TitledBorder("Identifiant"));
            name.setMaximumSize(new Dimension(2000,50));
            surname.setMaximumSize(new Dimension(2000,50));
            id.setMaximumSize(new Dimension(2000,50));
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            add(name);
            add(surname);
            add(id);
        }

        public List<String> getInfos(){
            List<String> infos = new ArrayList<String>();
            infos.add(name.getText());
            infos.add(surname.getText());
            infos.add(id.getText());
            return infos;
        }

    }

    public Student getStudent() {
        return student;
    }

    //    public static void main(String[] args) {
//        Data data = new Data("data/data.xml");
//        Filter studentFilter = new Filter();
//        ChoiceStudent choiceStudent = new ChoiceStudent(data,studentFilter);
//    }


}
