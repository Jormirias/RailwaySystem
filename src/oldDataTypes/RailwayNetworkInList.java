package dataStructures;

public class RailwayNetworkInList<E> implements RailwayNetwork<E> {

    protected DoubleList<E> list;
    private static final long serialVersionUID = 0L;

    //Constructor
    public RailwayNetworkInList() {
        this.list = new DoubleList<E>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

}
