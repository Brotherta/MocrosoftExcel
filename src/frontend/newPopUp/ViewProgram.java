package frontend.newPopUp;
import backend.program.Program;
import javax.swing.*;
import java.awt.*;
public class ViewProgram extends JFrame {
    viewBySemester vueSemestre;

    public ViewProgram(Program program){
        super("View Program");

        ///// Header Panel
        JPanel headerPanel = new JPanel();
        JLabel programName = new JLabel(program.getName()+" - "+program.getId());
        headerPanel.add(programName);
        this.vueSemestre=new viewBySemester(program,false);
        /*
        // Répartition des Cours
        List<SimpleCourse> simpleCourseList1 = new ArrayList<>();
        List<SimpleCourse> simpleCourseList2 = new ArrayList<>();
        for (SimpleCourse simpleCourse : program.getSimpleCourseList()){
            if (numSemester(simpleCourse) == 1){
                simpleCourseList1.add(simpleCourse);
            }
            else if (numSemester(simpleCourse) == 2){
                simpleCourseList2.add(simpleCourse);
            }
            else {
                System.out.println("Problem");
            }
        }
        List<OptionCourse> optionCourseList1 = new ArrayList<OptionCourse>();
        List<OptionCourse> optionCourseList2 = new ArrayList<OptionCourse>();
        for (OptionCourse optionCourse : program.getOptionCourseList()){
            if (numSemester(optionCourse) == 1){
                optionCourseList1.add(optionCourse);
            }
            else if (numSemester(optionCourse) == 2){
                optionCourseList2.add(optionCourse);
            }
            else {
                System.out.println("Problem");
            }
        }
        List<CompositeCourse> compositeCourseList1 = new ArrayList<CompositeCourse>();
        List<CompositeCourse> compositeCourseList2 = new ArrayList<CompositeCourse>();
        for (CompositeCourse compositeCourse : program.getCompositeCoursesList()){
            if (numSemester(compositeCourse) == 1){
                compositeCourseList1.add(compositeCourse);
            }
            else if (numSemester(compositeCourse) == 2){
                compositeCourseList2.add(compositeCourse);
            }
            else {
                System.out.println("Problem");
            }
        }
    */

        //// Semester 1 Panel
        JPanel semester1Panel = vueSemestre.getPanelSemester1();//printSemester("Semestre 1",simpleCourseList1,optionCourseList1,compositeCourseList1);

        ////// Semestre 2 Panel
        JPanel semester2Panel = vueSemestre.getPanelSemester2();//printSemester("Semestre 2",simpleCourseList2,optionCourseList2,compositeCourseList2);


        ///// Info Panel
        JPanel infos = new JPanel();
        infos.setLayout(new GridLayout());
        infos.add(new JScrollPane(semester1Panel));
        infos.add(new JScrollPane(semester2Panel));


        ////// ViewProgram Frame

        setSize(900,600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        add(headerPanel, BorderLayout.NORTH);
        add(infos, BorderLayout.CENTER);



        setVisible(true);
    }
/*
    private JPanel printSimpleCourse(SimpleCourse course){
        JPanel simpleCourse = new JPanel();
        JLabel simpleCourseInfo = new JLabel("∘ "+course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects");
        simpleCourse.add(simpleCourseInfo);
        simpleCourse.setMaximumSize(new Dimension(500,30));
        simpleCourse.setLayout(new FlowLayout(FlowLayout.LEFT));
        return simpleCourse;
    }   // Panel d'une simpleCourse
    private JPanel printMultipleSimpleCourses(List<SimpleCourse> courseList){
        JPanel allCoursesPanel = new JPanel();
        allCoursesPanel.setLayout(new BoxLayout(allCoursesPanel,BoxLayout.Y_AXIS));
        for (SimpleCourse course : courseList){
            JPanel coursePanel = printSimpleCourse(course);
            allCoursesPanel.add(coursePanel);
        }
        return allCoursesPanel;
    }   // Panel d'une liste de simpleCourse

    private JPanel printOptionCourse(OptionCourse course){
        JPanel optionCourses = printMultipleSimpleCourses(course.getOptionList());
        optionCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()+" ects"));
        return optionCourses;
    }   // Panel d'une optionCourse
    private JPanel printCompositeCourse(CompositeCourse course){
        JPanel compositeCourses = printMultipleSimpleCourses(course.getCompositeList());
        compositeCourses.setBorder(new TitledBorder(course.getName()+" - "+course.getId()+" - "+course.getCredits()));
        return compositeCourses;
    }  // Panel d'une compositeCourse


    private JPanel printSemester(String semesterName,List<SimpleCourse> simpleCourseList, List<OptionCourse> optionCourseList, List<CompositeCourse> compositeCourseList){
        JPanel semesterPanel = new JPanel();
        semesterPanel.setLayout(new BoxLayout(semesterPanel,BoxLayout.Y_AXIS));
        semesterPanel.setBorder(new TitledBorder(semesterName));
        semesterPanel.add(Box.createVerticalStrut(10));


        // Cours Simple
        JPanel allSimpleCourses = printMultipleSimpleCourses(simpleCourseList);
        allSimpleCourses.setBorder(new TitledBorder("Simple Courses"));
        semesterPanel.add(allSimpleCourses);
        semesterPanel.add(Box.createVerticalStrut(20));

        //// Cours Optionnels
        JPanel allOptionCourses = new JPanel();
        allOptionCourses.setLayout(new BoxLayout(allOptionCourses,BoxLayout.Y_AXIS));
        for (OptionCourse course : optionCourseList){
            JPanel optionPanel = printOptionCourse(course);
            allOptionCourses.add(optionPanel);
        }
        semesterPanel.add(allOptionCourses);
        semesterPanel.add(Box.createVerticalStrut(20));

        //// Cours Composites
        JPanel allCompositeCourses = new JPanel();
        allCompositeCourses.setLayout(new BoxLayout(allCompositeCourses,BoxLayout.Y_AXIS));
        for (CompositeCourse course : compositeCourseList){
            JPanel compositePanel = printCompositeCourse(course);
            allCompositeCourses.add(compositePanel);
        }
        semesterPanel.add(allCompositeCourses);
        semesterPanel.add(Box.createVerticalStrut(20));

        return semesterPanel;
    }


    private int numSemester(Course course){
        String courseId = course.getId();
        String[] courseIdList = courseId.split("");
        for (int i=0; i<courseIdList.length; i++) {
            try{
               int nb = Integer.parseInt(courseIdList[i]);
               if(nb % 2 == 0){nb = 2;}
               else {nb = nb%2;}
               return nb;
            }
            catch (IllegalArgumentException e){ }
        }
        return 300;
    }




 */
//    public static void main(String[] args) {
//        Data data = new Data("data/data.xml");
//        Program program = data.getProgramList().get(0);
//        JFrame viewProgram = new ViewProgram(program);
//    }


}
