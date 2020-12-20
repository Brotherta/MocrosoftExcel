package backend.student;
import backend.course.*;
public class Grade {
    private double grade; // valeur modifiable dans l'avenir, aucune valeur par defaut, -1 pour indiquer ABI // -2 pour pas encore fait l'examen
    private final Course course; // une note est lié a une matiere, et ne peux pas etre changer de matiere

    public Grade(double grade, Course course) {
        this.grade = grade;
        this.course = course;
    }
    /*
    public Grade(Course course) {
        this.grade= -2.00;
        this.course = course;
    }*/
    public double getGrade() {
        return grade;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return ""+course.getId()+"="+grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
