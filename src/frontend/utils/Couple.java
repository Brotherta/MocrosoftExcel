package frontend.utils;

public class Couple {
    private int row;
    private int column;

    public Couple(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
        Couple couple = (Couple) obj;
        return (this.row == couple.row && this.column == couple.column);
    }

}
