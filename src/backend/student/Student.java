package backend.student;
import java.util.ArrayList;
import java.util.List;
import backend.course.*;
public class Student {
    private final String name;
    private final String surname;
    private final String idEtud;
    private final String programId;
    private List<Grade> gradeList; //List<String> strings = new ArrayList<String>();

    public Student(String name, String surname, String idEtud, String programId, List<Grade> gradeList) {
        this.name = name;
        this.surname = surname;
        this.idEtud = idEtud;
        this.programId = programId;
       // this.gradeList=new ArrayList<Grade>();
        this.gradeList = gradeList;
    }
    void setGrade(String idCourse,double grade)
    {
        int i=0;
        while(this.gradeList.get(i).getCourse().getId()!=idCourse)
        {
            i++;
            if(i==this.gradeList.size()){return ;}//impossible que cela arrive
        }
        this.gradeList.get(i).setGrade(grade);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getIdEtud() {
        return idEtud;
    }

    public String getProgramId() {
        return programId;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }
/*
    public static void main(String[] args) {
        Course course1=new SimpleCourse("AL","Automate et Langage",6);
        Course course2=new SimpleCourse("PF","Programmation Fonctionnel",6);
        Course course3=new SimpleCourse("PCOO","Programmation et conception objet",6);
        Grade grade1=new Grade(15.04,course1);
        Grade grade2=new Grade(-2.0,course2);
        Grade grade3=new Grade(-1,course3);
        List<Grade> gradeList=new ArrayList<Grade>();
        gradeList.add(grade1);
        gradeList.add(grade2);
        gradeList.add(grade3);
        Student student=new Student("antoine","vidal","SL3I","SL3I",gradeList);
        student.setGrade("PF",10);
        System.out.println(student.getGradeList());
        System.out.println(student.getGradeList().get(1).getCourse());
    }*/
}
