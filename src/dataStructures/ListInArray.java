package dataStructures;

public class ListInArray<E> implements List<E> {

    public static final int DEFAULT_CAPACITY = 100;

    /**
     * Array which will hold the elements of the List.
     */
    private E[] array;

    /**
     * How many elements the array actually holds.
     */
    private int size;

    /**
     * Constructor with capacity.
     * @param capacity - initial holding capacity of Array
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int capacity) 
    {
        size = 0;
        array = (E[]) new Object[capacity];
    }
    
    /**
     * Default constructor.
     */
    public ListInArray() 
    {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public E getFirst() throws EmptyListException {
        return array[0];
    }

    @Override
    public E getLast() throws EmptyListException {
        return array[size - 1];
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        return array[position];
    }

    @Override
    public int find(E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public void addFirst(E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFirst'");
    }

    @Override
    public void addLast(E element) {
        array[size] = element;
        size++;
    }

    @Override
    public void add(int position, E element) throws InvalidPositionException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public E removeFirst() throws EmptyListException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public E removeLast() throws EmptyListException {
        E last = array[size - 1];
        size--;
        return last;
    }

    @Override
    public E remove(int position) throws InvalidPositionException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean remove(E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    
}
