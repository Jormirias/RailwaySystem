package dataStructures;

public class ListInArray<E> implements List<E> {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    /**
     *  Default capacity of the stack.
     */
    public static final int DEFAULT_CAPACITY = 1000;
    /**
     * Array which will hold the elements of the List.
     */
    protected E[] array;

    /**
     * How many elements the array actually holds.
     */
    private int size;

    private boolean isInverted;

    /**
     * Constructor with capacity.
     * @param capacity - initial holding capacity of Array
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int capacity) 
    {
        size = 0;
        array = (E[]) new Object[capacity];
        isInverted = false;
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
    public TwoWayIterator<E> iterator() {
        ListInArrayIterator<E> newIterator = new ListInArrayIterator<>(array, size);
        if(isInverted) newIterator.invert();
        return newIterator;
    }

    @Override
    public E getFirst() throws EmptyListException {
        if (!isInverted)
            return array[0];
        else
            return array[size - 1];
    }

    @Override
    public E getLast() throws EmptyListException {
        if (!isInverted)
            return array[size - 1];
        else
            return array[0];
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        if (!isInverted)
            return array[position];
        else
            return array[size-position-1];
    }

    @Override
    public int find(E element) throws NoSuchElementException{
        Iterator<E> it = iterator();
        int index = 0;
        E currElement = getFirst();
        while (it.hasNext() && !element.equals(currElement)) {
            currElement = it.next();
            index++;
        }
        return index;
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

    /**
     * Invert the order of the array (artificially)
     */
    public void invert() {
        isInverted = !isInverted;
    }
    
}
