package backend.course;

import java.util.List;


public class CompositeBloc extends AbstractBloc {
    private List<Course> compositeList;

    CompositeBloc(String id, String name, List<Course> compositeList){
        this.compositeList = compositeList;
        this.id = id;
        this.name = name;
        this.credits = 0;
        for ( Course c : compositeList) {
            this.credits += c.getCredits();
        }
    }

    public List<Course> getCompositeList() { return this.compositeList; }

}
