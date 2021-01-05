package frontend.filter;

import backend.Data;
import backend.course.Course;
import javax.swing.*;

public class ChoiceCourse extends AbstractChoice {
    Course course;

    public ChoiceCourse(Data data, Filter filter, JFrame main, boolean bool){
        super(filter,data ,1,main,bool);
        course = (Course) getItem();
    }

    public Course getCourse(){ return course;}
}
