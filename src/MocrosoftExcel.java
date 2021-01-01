import backend.Data;
import backend.course.Course;
import backend.program.Program;
import backend.student.Student;
import frontend.Excel.ExcelPanel;
import frontend.Excel.Filter;
import frontend.csv.ViewProgram;
import frontend.newPopUp.popUpNewPrograms;
import frontend.newPopUp.popUpNewStudent;
import frontend.utils.StartFrame;
import frontend.csv.PV;
import frontend.newPopUp.PopUpNewCourse;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MocrosoftExcel extends JFrame {
    int iterator;
    ExcelPanel excel;
    Data data;
    Filter filter;

    public MocrosoftExcel() {
        super();
        String dataPath = getDataPath();
        File tmpFile = new File(dataPath);
        if (tmpFile.exists() && StartFrame.verifyXml(dataPath)) {
            this.data = new Data(dataPath);
        }
        else {
            StartFrame sfs = new StartFrame(this, true);
            if (! sfs.getStatus()) {
                System.exit(1);
            }
            dataPath = getDataPath();
        }

        this.data = new Data(dataPath);
        excel = new ExcelPanel(data);

        filter = new Filter();

        JMenu menu = new JMenu("File");
        JMenuItem i1 = new JMenuItem("Ouvrir");
        JMenuItem i2 = new JMenuItem("Enregistrer");
        JMenuBar mb = new JMenuBar();

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
                activeFilter.setLayout(new BoxLayout(activeFilter, BoxLayout.X_AXIS));
                activeFilter.setBorder(new TitledBorder("Filtre actif"));

            filterPanel.add(new JScrollPane(activeFilter,
                            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),
                            BorderLayout.WEST);
            filterPanel.add(filterSelection, BorderLayout.EAST);

        menuPanel.add(optionPanel, BorderLayout.NORTH);
        menuPanel.add(filterPanel, BorderLayout.CENTER);
        menu.add(i1); menu.add(i2);
        mb.add(menu);

        filterStudent.setBackground(new Color(245, 128, 128));
        filterBloc.setBackground(new Color(78, 147, 255));
        filterProgram.setBackground(new Color(180, 226, 138));

        filterStudent.addActionListener(e1 -> {
            List<Student> students = data.getStudentList();
            Student student = students.get(iterator);
            iterator++;
            JButton newFilter = filter.addStudentFilter(student);
            activeFilter.add(newFilter, BorderLayout.WEST);
            newFilter.setBackground(new Color(245, 128, 128));

            addClose(activeFilter, filter, data, newFilter, student, null, null);
        });
        filterBloc.addActionListener(e1 -> {
            Course course = data.getCourseList().get(0);
            JButton newFilter = filter.addCourseFilter(course);
            activeFilter.add(newFilter, BorderLayout.WEST);
            newFilter.setBackground(new Color(78, 147, 255));

            addClose(activeFilter, filter, data, newFilter, null, course, null);
        });
        filterProgram.addActionListener(e1 -> {
            JButton newFilter = filter.addProgramFilter(data.getProgramList().get(0));
            activeFilter.add(newFilter, BorderLayout.WEST);
            newFilter.setBackground(new Color(180, 226, 138));

            addClose(activeFilter, filter, data, newFilter, null, null, data.getProgramList().get(0));
        });
        newCourseButton.addActionListener(e1 -> {
            new PopUpNewCourse(data, this, true);
            System.out.println(data.getCourseList().get(data.getCourseList().size()-1).getName());
            updateExcel(filter, data);
        });
        procesVerbalButton.addActionListener(e1 -> {
            PV pv = new PV(data.getProgramList().get(0), data);
            pv.makePV();
        });
        viewProgramButton.addActionListener(e1 -> {
            new ViewProgram(data.getProgramList().get(0));
        });
        newProgramButton.addActionListener(e1 -> {
            new popUpNewPrograms(data.getCourseList(), data, this, true);
            updateExcel(filter, data);
        });
        newStudentButton.addActionListener(e1 -> {
            new popUpNewStudent(data.getProgramList(), data.getCourseList(), data, this, true);
            updateExcel(filter, data);
        });
        i1.addActionListener(e1 -> {
            StartFrame sf = new StartFrame(this, true);
            String path = getDataPath();
            this.data = new Data(path);
            filter = new Filter();
            setTitle("Mocrosoft Excel Student - "+ path);
            updateExcel(filter, data);
        });
        i2.addActionListener(e1 -> {
            String path = getDataPath();
            data.doSave(path);
        });

        add(menuPanel, BorderLayout.NORTH);
        add(excel, BorderLayout.CENTER);
        setTitle("Mocrosoft Excel Student - "+ dataPath);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500,1000);
        setFont(new Font("Arial", Font.BOLD, 20));
        setJMenuBar(mb);
        setVisible(true);
    }

    private void updateExcel(Filter filter, Data data) {
        remove(excel);
        excel = new ExcelPanel(data);
        excel.updateData(filter.getStudentsFilter(), filter.getCoursesFilter(), filter.getProgramsFilter(), data);
        excel.revalidatePanel();
        add(excel);
        revalidate();
    }

    private void addClose(JPanel activeFilter, Filter filter, Data data, JButton newFilter, Student student, Course course, Program program) {
        JButton close = new JButton();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Image img = ImageIO.read(Objects.requireNonNull(classLoader.getResource("resources/close.png")));
            close.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
        }
        close.setMaximumSize(new Dimension(20,20));
        activeFilter.add(close);

        updateExcel(filter, data);
        close.addActionListener(e2 -> {
            activeFilter.remove(newFilter);
            activeFilter.remove(close);
            activeFilter.revalidate();
            activeFilter.repaint();
            if(student != null) {
                filter.removeStudentFilter(student);
            }
            else if (course != null) {
                filter.removeCourseFilter(course);
            } else filter.removeProgramFilter(program);
            updateExcel(filter, data);
        });
    }

    private String getDataPath() {
        String dataPath = "";
        try {
            File pathFile = new File("src/resources/dataPath.txt");
            Scanner scanner = new Scanner(pathFile);
            while(scanner.hasNextLine()) {
                dataPath = scanner.nextLine();
            }
            scanner.close();
            System.out.println("path: " + pathFile);
        } catch (FileNotFoundException e) {
            System.out.println("fichier introuvable");;
            e.printStackTrace();
        }
        return dataPath;
    }


    public static void main(String[] args) {
        new MocrosoftExcel();
    }
}
