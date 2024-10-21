package dataStructures;

public class RailwayLineInDoubleList<E> implements RailwayLine<E> {

    protected DoubleList<E> line;
    private static final long serialVersionUID = 0L;

    //Constructor
    public RailwayLineInDoubleList() {
        this.line = new DoubleList<E>();
    }

    public int size() {
        return line.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

}
