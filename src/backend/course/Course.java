package backend.course;

public class Course {

    private String id;
    private String name;
    private int credits;

    Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public int getCredits() { return this.credits; }
}
