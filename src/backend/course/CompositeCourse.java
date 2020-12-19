package backend.course;

import java.util.List;


public class CompositeCourse extends AbstractCourse {
    private List<SimpleCourse> compositeList;

    CompositeCourse(String id, String name, List<SimpleCourse> compositeList){
        this.compositeList = compositeList;
        this.id = id;
        this.name = name;
        this.credits = 0;
        for ( SimpleCourse c : compositeList) {
            this.credits += c.getCredits();
        }
    }

    public List<SimpleCourse> getCompositeList() { return this.compositeList; }

}
