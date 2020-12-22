package backend.course;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.List;

/*
Cours composite
base de cours + liste des cours qui le compose
constructeur + getCompositeList

 */


public class CompositeCourse extends AbstractCourse {
    private List<SimpleCourse> compositeList;

    public CompositeCourse(String id, String name, List<SimpleCourse> compositeList){
        this.compositeList = compositeList;
        this.id = id;
        this.name = name;
        this.credits = 0;
        for ( Course c : compositeList) {
            this.credits += c.getCredits();
        }
    }

    public List<SimpleCourse> getCompositeList() { return this.compositeList; }

}
