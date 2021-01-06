package backend.student;
import backend.Item;

import java.util.List;

public class Student implements Item {
    private final String name;
    private final String surname;
    private final String studentId;
    private final String programId;
    private List<Grade> gradeList; //List<String> strings = new ArrayList<String>();

    public Student(String name, String surname, String studentId, String programId, List<Grade> gradeList) {
        this.name = name;
        this.surname = surname;
        this.studentId = studentId;
        this.programId = programId;
       // this.gradeList=new ArrayList<Grade>();
        this.gradeList = gradeList;
    }
    void setGrade(String courseId,double grade)
    {
        int i=0;
        while(this.gradeList.get(i).getCourse().getId()!=courseId)
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

    public String getStudentId() {
        return studentId;
    }

    public String getId(){ return getStudentId();}

    public String getProgramId() {
        return programId;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    @Override
    public boolean equals(Object obj) {
        Student student = (Student) obj;
        return student.getStudentId().equals(this.getStudentId());
    }

    public Grade getGradeById(String gradeIdPlusName) {
        for (Grade grade : getGradeList()) {
            if (gradeIdPlusName.contains(grade.getCourse().getId())) {
                return grade;
            }
        }
        return null;
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
