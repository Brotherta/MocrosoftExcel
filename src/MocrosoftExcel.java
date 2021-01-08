import backend.Data;
import backend.course.Course;
import backend.program.Program;
import backend.student.Student;
import frontend.excel.*;
import frontend.filter.*;
import frontend.newPopUp.*;
import frontend.utils.*;
import frontend.csv.PV;
import frontend.newPopUp.PopUpNewCourse;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.util.Objects;


public class MocrosoftExcel extends JFrame {
    ExcelPanel excel;
    Data data;
    Filter filter;
    boolean editable;

    public MocrosoftExcel() {
        super();
        editable = false;  // Mode Lecture à l'ouverture.

        // on récupère le chemin du fichier précédent s'il existe, sinon on lance la fenêtre StartFrame.
        String dataPath = Utils.getDataPath();
        File tmpFile = new File(dataPath);
        if (tmpFile.exists() && StartFrame.verifyXml(dataPath)) {
            this.data = new Data(dataPath);
        }
        else {
            StartFrame sf = new StartFrame(this, true);
            if (! sf.getStatus()) {
                System.exit(1);
            }
            dataPath = Utils.getDataPath();
        }

        // Initialisation des données à partir du fichier XML, on itinialise le tableau et les filtres.
        this.data = new Data(dataPath);
        excel = new ExcelPanel(data, editable);
        filter = new Filter();


        //////////// Création de tous les éléments /////////////

        JMenu menu = new JMenu("Fichier");
        JMenuItem openMenu = new JMenuItem("Ouvrir");
        JMenuItem saveMenu = new JMenuItem("Enregistrer");
        JMenuBar mb = new JMenuBar();

        JButton readButton = new JButton("Mode lecture seule");
        readButton.setBackground(new Color(168, 168, 168));
        JButton writeButton = new JButton("Mode édition");

        JButton viewProgramButton = new JButton("Vue Programme");
        JButton newCourseButton = new JButton("Nouveau Cours");
        JButton newProgramButton = new JButton("Nouveau Programme");
        JButton newStudentButton = new JButton("Nouveau Étudiant");
        JButton procesVerbalButton = new JButton("Procès Verbal");
        JButton deleteStudentButton = new JButton("Supprimer Étudiant");

        JButton filterStudent = new JButton("Étudiant");
        JButton filterProgram = new JButton("Programme");
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
                rightOptionPanel.add(deleteStudentButton, BorderLayout.EAST);
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
        menu.add(openMenu); menu.add(saveMenu);
        mb.add(menu);

        filterStudent.setBackground(new Color(245, 128, 128));
        filterBloc.setBackground(new Color(78, 147, 255));
        filterProgram.setBackground(new Color(180, 226, 138));


        /////////////// Ajout pour chaque bouton d'un ActionListener /////////////////

        filterStudent.addActionListener(e1 -> {
            ChoiceStudent cs = new ChoiceStudent(data, filter, this, true);
            Student student = cs.getStudent();
            if (student != null) {
                JButton newFilter = filter.addStudentFilter(student);
                activeFilter.add(newFilter, BorderLayout.WEST);
                newFilter.setBackground(new Color(245, 128, 128));

                // Ajoute un bouton pour fermer le filtre à côté du nouveau filtre.
                addClose(activeFilter, filter, data, newFilter, student, null, null);
            }
        });
        filterBloc.addActionListener(e1 -> {
            ChoiceCourse cc = new ChoiceCourse(data, filter, this, true);
            Course course = cc.getCourse();
            if (course != null) {
                JButton newFilter = filter.addCourseFilter(course);
                activeFilter.add(newFilter, BorderLayout.WEST);
                newFilter.setBackground(new Color(78, 147, 255));

                addClose(activeFilter, filter, data, newFilter, null, course, null);
            }
        });
        filterProgram.addActionListener(e1 -> {
            ChoiceProgram cp = new ChoiceProgram(data, filter, this, true);
            Program program = cp.getProgram();
            if (program != null) {
                JButton newFilter = filter.addProgramFilter(program);
                activeFilter.add(newFilter, BorderLayout.WEST);
                newFilter.setBackground(new Color(180, 226, 138));

                addClose(activeFilter, filter, data, newFilter, null, null, program);
            }
        });
        procesVerbalButton.addActionListener(e1 -> {
            ChoiceProgram cp = new ChoiceProgram(data, filter, this, true);
            Program program = cp.getProgram();
            if (program != null) {
                FileChooser fc = new FileChooser(this,
                        true,
                        program.getName()+".csv",
                        "Où enregistrer "+program.getName()+".csv"+" ?");
                String path = fc.getFilePath();
                File selectedFile = fc.getFileToSave();
                if (selectedFile != null  && selectedFile.exists()) {
                    WarningFrame wf = new WarningFrame(this, true, "Un fichier existe déjà voulez-vous l'écraser ?");
                    if (!wf.getStatus()) {
                        dispose();
                    }
                }
                if (path != null) {
                    if (!path.endsWith(".csv")) {
                        path += ".csv";
                    }
                    PV pv = new PV(program, data, path);
                    pv.makePV();
                }
            }
        });
        viewProgramButton.addActionListener(e1 -> {
            ChoiceProgram cp = new ChoiceProgram(data, new Filter(), this, true);
            Program program = cp.getProgram();
            if (program != null) {
                new ViewProgram(program);
            }
        });
        newCourseButton.addActionListener(e1 -> {
            new PopUpNewCourse(data, this, true);
            updateExcel(filter, data);
        });
        newProgramButton.addActionListener(e1 -> {
            new PopUpNewProgram(data.getCourseList(), data, this, true);
            updateExcel(filter, data);
        });
        newStudentButton.addActionListener(e1 -> {
            new PopUpNewStudent(data.getProgramList(), data.getCourseList(), data, this, true);
            updateExcel(filter, data);
        });
        deleteStudentButton.addActionListener(e1 -> {
            ChoiceStudent cs = new ChoiceStudent(data, new Filter(), this, true);
            Student student = cs.getStudent();
            if (student != null) {
                data.deleteStudent(student);
                if (filter.getStudentsFilter().contains(student)) {
                    filter.removeStudentFilter(student);
                    activeFilter.removeAll();
                    activeFilter.repaint();
                }
                updateExcel(filter, data);
            }
        });
        openMenu.addActionListener(e1 -> {
            new StartFrame(this, true);
            String path = Utils.getDataPath();
            this.data = new Data(path);
            filter = new Filter();
            setTitle("Mocrosoft Excel Étudiant - "+ path);
            updateExcel(filter, data);
        });
        saveMenu.addActionListener(e1 -> {
            String path = Utils.getDataPath();
            data.doSave(path);
        });
        writeButton.addActionListener(e1 -> {
            if (!editable) {
                editable = true;
                writeButton.setBackground(new Color(168, 168, 168));
                readButton.setBackground(null);
                updateExcel(filter, data);
            }
        });
        readButton.addActionListener(e1 -> {
            if (editable) {
                editable = false;
                readButton.setBackground(new Color(168, 168, 168));
                writeButton.setBackground(null);
                updateExcel(filter, data);
            }
        });


        add(menuPanel, BorderLayout.NORTH);
        add(excel, BorderLayout.CENTER);
        setTitle("Mocrosoft Excel Étudiant - "+ dataPath);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500,1000);
        setFont(new Font("Arial", Font.BOLD, 20));
        setJMenuBar(mb);
        setVisible(true);
    }

    // permet de mettre à jour le tableau et de recalculer les données à afficher
    private void updateExcel(Filter filter, Data data) {
        remove(excel);
        excel = new ExcelPanel(data, editable);
        excel.updateData(filter.getStudentsFilter(), filter.getCoursesFilter(), filter.getProgramsFilter(), data);
        excel.revalidatePanel(data, editable);
        add(excel);
        revalidate();
    }

    // Ajoute un bouton pour fermer le filtre à côté du nouveau filtre.
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
            } else  {
                filter.removeProgramFilter(program);
                List<Student> studentList = Utils.getStudentListByProgramId(data, program);
                for (Student s : studentList) {
                    filter.removeStudentFilter(s);
                }

            }
            updateExcel(filter, data);
        });
    }


    public static void main(String[] args) {
        new MocrosoftExcel();
    }
}
