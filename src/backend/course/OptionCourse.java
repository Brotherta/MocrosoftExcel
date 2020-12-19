package backend.course;

import java.util.List;

public class OptionCourse extends AbstractCourse {
    private List<Course> optionList;

    public OptionCourse(String id, String name, List<Course> optionList){
        this.optionList = optionList;
        this.id = id;
        this.name = name;
        this.credits = optionList.get(0).getCredits();
    }

    public List<Course> getOptionList() { return this.optionList; }
}
