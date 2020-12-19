package backend.course;

public class SimpleBloc extends AbstractBloc {
    Course course;

    SimpleBloc(Course course){
        this.course = course;
        this.name = course.getName();
        this.id = course.getId();
        this.credits = course.getCredits();
    }

    public Course getCourse() { return this.course; }
}
