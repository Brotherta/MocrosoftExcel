package backend.course;

import java.util.List;

public class OptionCourse extends AbstractCourse {
    private List<SimpleCourse> optionList;

    OptionCourse(String id, String name, List<SimpleCourse> optionList){
        this.optionList = optionList;
        this.id = id;
        this.name = name;
        this.credits = optionList.get(0).getCredits();
    }

    public List<SimpleCourse> getOptionList() { return this.optionList; }
}
