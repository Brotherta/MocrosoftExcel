package frontend.Excel;

import backend.Data;
import backend.student.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.renderable.ParameterBlock;
import java.util.Collections;
import java.util.List;

public class JTableBasiqueAvecScrollPane extends JFrame {
    int iterator;
    ExcelPanel excel;

    public JTableBasiqueAvecScrollPane() {
        super();
        excel = new ExcelPanel();
        add(excel, BorderLayout.CENTER);

        JPanel toremove = new JPanel();
        toremove.setLayout(new BorderLayout());
        toremove.add(new JButton("Kill me please"));
        add(toremove, BorderLayout.SOUTH);


        setTitle("JTable basique dans un JScrollPane");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500,1000);

        Data data = new Data();
        int size = data.getStudentList().size();
        List<Student> students = data.getStudentList();

        Collections.sort(students, (a, b) -> a.getStudentId().compareToIgnoreCase(b.getStudentId()));
        System.out.println(size);

        JButton readButton = new JButton("Read");
        JButton writeButton = new JButton("Write");

        JButton viewProgramButton = new JButton("View Program");
        JButton newCourseButton = new JButton("New Course");
        JButton newProgramButton = new JButton("New Program");
        JButton newStudentButton = new JButton("New Student");
        JButton procesVerbalButton = new JButton("Procès Verbal");

        JButton filterStudent = new JButton("Student");
        JButton filterProgram = new JButton("Program");
        JButton filterBloc = new JButton("Bloc");


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
            JPanel optionPanel = new JPanel();
            optionPanel.setLayout(new BorderLayout());
                JPanel leftOptionPanel = new JPanel();
                leftOptionPanel.setLayout(new FlowLayout());
                leftOptionPanel.add(readButton, BorderLayout.WEST);
                leftOptionPanel.add(writeButton, BorderLayout.WEST);

                JPanel rightOptionPanel = new JPanel();
                rightOptionPanel.setLayout(new FlowLayout());
                rightOptionPanel.add(viewProgramButton, BorderLayout.EAST);
                rightOptionPanel.add(newCourseButton, BorderLayout.EAST);
                rightOptionPanel.add(newProgramButton, BorderLayout.EAST);
                rightOptionPanel.add(newStudentButton, BorderLayout.EAST);
                rightOptionPanel.add(procesVerbalButton, BorderLayout.EAST);

            optionPanel.add(leftOptionPanel, BorderLayout.WEST);
            optionPanel.add(rightOptionPanel, BorderLayout.EAST);

            JPanel filterPanel = new JPanel();
            filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
            filterPanel.setPreferredSize(new Dimension(2000, 75));
                JPanel filterSelection = new JPanel();
                filterSelection.setLayout(new FlowLayout());
                filterSelection.setBorder(new TitledBorder("Selection de filtre"));
                filterSelection.setMaximumSize(new Dimension(400,150));
                    filterSelection.add(filterStudent, BorderLayout.EAST);
                    filterSelection.add(filterBloc, BorderLayout.EAST);
                    filterSelection.add(filterProgram, BorderLayout.EAST);

                JPanel activeFilter = new JPanel();
                activeFilter.setLayout(new FlowLayout());
                activeFilter.setBorder(new TitledBorder("Filtre actif"));

                filterStudent.addActionListener(e1 -> {
                    NewFilter newFilter = new NewFilter(students.get(iterator).getStudentId(), students.get(iterator));
                    activeFilter.add(newFilter);
                    List<Student> localStudents = excel.getStudents();
                    remove(excel);
                    excel = new ExcelPanel();
                    excel.setStudentListFilter(localStudents);
                    excel.addStudent(students.get(iterator));
                    iterator++;
                    excel.myUpdate();
                    add(excel);
                    revalidate();
                });


            filterPanel.add(new JScrollPane(activeFilter,
                            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),
                            BorderLayout.WEST);
            filterPanel.add(filterSelection, BorderLayout.EAST);

        menuPanel.add(optionPanel, BorderLayout.NORTH);
        menuPanel.add(filterPanel, BorderLayout.CENTER);
        // tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);


        add(menuPanel, BorderLayout.NORTH);


        JMenu menu = new JMenu("File");
        JMenuItem i1 = new JMenuItem("Save");
        JMenuItem i2 = new JMenuItem("Import csv");
        JMenuItem i3 = new JMenuItem("Export csv");
        JMenuBar mb = new JMenuBar();


        menu.add(i1); menu.add(i2); menu.add(i3);
        mb.add(menu);
        setJMenuBar(mb);
        setFont(new Font("Arial", Font.BOLD, 20));
        setVisible(true);
    }

    public static void main(String[] args) {
        new JTableBasiqueAvecScrollPane();
    }
}
