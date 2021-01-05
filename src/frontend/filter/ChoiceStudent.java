package frontend.filter;

import backend.Data;
import backend.student.Student;
import javax.swing.*;

public class ChoiceStudent extends AbstractChoice {
    Student student;

    public ChoiceStudent(Data data, Filter filter, JFrame main, boolean bool){
        super(filter,data ,0,main,bool);
        student = (Student) getItem();
    }

    public Student getStudent(){
        return (Student) this.item;
    }

}