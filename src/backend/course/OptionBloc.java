package backend.course;

import java.util.List;

public class OptionBloc extends AbstractBloc {
    private List<Course> optionList;

    OptionBloc(String id, String name, List<Course> optionList){
        this.optionList = optionList;
        this.id = id;
        this.name = name;
        this.credits = optionList.get(0).getCredits();
    }

    public List<Course> getOptionList() { return this.optionList; }
}
