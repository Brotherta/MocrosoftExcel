package backend.program;
import java.util.List;

import backend.Item;
import backend.course.*;

public class Program implements Item {
    private final String id;
    private final String name;
    List<SimpleCourse> simpleCourseList;
    List<OptionCourse> optionCourseList;
    List<CompositeCourse> compositeCoursesList;

    public Program(String id, String name, List<SimpleCourse> simpleCourseList, List<OptionCourse> optionCourseList, List<CompositeCourse> compositeCoursesList) {
        this.id = id;
        this.name = name;
        this.simpleCourseList = simpleCourseList;
        this.optionCourseList = optionCourseList;
        this.compositeCoursesList = compositeCoursesList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() { return null;}

    public List<SimpleCourse> getSimpleCourseList() {
        return simpleCourseList;
    }

    public List<OptionCourse> getOptionCourseList() {
        return optionCourseList;
    }

    public List<CompositeCourse> getCompositeCoursesList() {
        return compositeCoursesList;
    }
    /*
    public static void main(String[] args) {
        SimpleCourse course1=new SimpleCourse("AL","Automate et langages",6);
        SimpleCourse course2=new SimpleCourse("AL2","Automate et langages2",6);
        List<SimpleCourse> simpleCourseList=new ArrayList<SimpleCourse>();
        simpleCourseList.add(course1);
        simpleCourseList.add(course2);
        Course courseO1=new SimpleCourse("OP1","Option1",5);
        Course courseO2=new SimpleCourse("OP2","Option2",5);
        Course courseC1=new SimpleCourse("C1","Truc1",5);
        Course courseC2=new SimpleCourse("C2","Truc2",4);
        Course courseO3=new SimpleCourse("OP3","Option3",5);
        Course courseO4=new SimpleCourse("OP4","Option4",5);
        Course courseC3=new SimpleCourse("C3","Truc3",5);
        Course courseC4=new SimpleCourse("C4","Truc4",4);
        Course courseC5=new SimpleCourse("C5","Truc5",4);
        List<Course> coursOptions1=new ArrayList<Course>();
        List<Course> coursOptions2=new ArrayList<Course>();
        List<Course> coursComposite1=new ArrayList<Course>();
        List<Course> coursComposite2=new ArrayList<Course>();
        coursOptions1.add(courseO1);
        coursOptions1.add(courseO2);
        coursOptions2.add(courseO3);
        coursOptions2.add(courseO4);
        coursComposite1.add(courseC1);
        coursComposite1.add(courseC2);
        coursComposite2.add(courseC3);
        coursComposite2.add(courseC4);
        coursComposite2.add(courseC5);
        OptionCourse CoursOption1=new OptionCourse("OPT1","OP1 ou OP2",coursOptions1);
        OptionCourse CoursOption2=new OptionCourse("OPT2","OP3 ou OP4",coursOptions2);
        CompositeCourse CoursComposite1=new CompositeCourse("CT","Competence Transversales",coursComposite1);
        CompositeCourse CoursComposite2=new CompositeCourse("CT2","Competence Transversales Bis",coursComposite2);
        List<OptionCourse> optionCourseList=new ArrayList<OptionCourse>();
        List<CompositeCourse> compositeCourseList=new ArrayList<CompositeCourse>();
        optionCourseList.add(CoursOption1);
        optionCourseList.add(CoursOption2);
        compositeCourseList.add(CoursComposite1);
        compositeCourseList.add(CoursComposite2);

        Program programme=new Program("SL3","LicenseInfo3",simpleCourseList,optionCourseList,compositeCourseList);

        List<SimpleCourse> testc1=programme.getSimpleCourseList();
        System.out.println(testc1.get(1).getId());
    }*/
}
