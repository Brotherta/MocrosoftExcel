package backend.course;

import backend.Item;

public interface Course extends Item {

    String getId();
    String getName();
    int getCredits();
}

