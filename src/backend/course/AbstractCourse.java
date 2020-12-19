package backend.course;

public abstract class AbstractCourse implements Course {

    protected String id;
    protected String name;
    protected int credits;

    @Override
    public String getId() { return this.id; }
    @Override
    public String getName() { return this.name; }
    @Override
    public int getCredits() { return this.credits; }

}
