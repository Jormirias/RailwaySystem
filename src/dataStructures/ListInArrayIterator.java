package dataStructures;

/**
 * Implementation of ListInArray Iterator for AED
 * 
 */
class ListInArrayIterator<E> implements TwoWayIterator<E>
{

	/**
	 * Serial Version UID of the Class
	 */
    static final long serialVersionUID = 0L;


    /** 
     * Node with the first element in the iteration.
     */
    protected E[] array;

    /**
     * Node with the last element in the iteration.
     */
    protected int currIndex;

    protected int size;

    /**
     * ListArrayIterator constructor
     */
    public ListInArrayIterator( E[] listArray, int currSize )
    {
        array = listArray;
        currIndex=0;
        size=currSize;
    }      


    @Override
    public void rewind( )
    {
        currIndex=0;
    }


    @Override
    public void fullForward( )
    {
        currIndex=size;
    }


    @Override
    public boolean hasNext( )
    {
        return currIndex < size;
    }


    @Override
    public boolean hasPrevious( )
    {
        return currIndex > 0;
    }


    @Override
    public E next( ) throws NoSuchElementException
    {
        if ( !this.hasNext() )
            throw new NoSuchElementException();

        return array[currIndex++];
    }


    @Override
    public E previous( ) throws NoSuchElementException
    {
        if ( !this.hasPrevious() )
            throw new NoSuchElementException();

        return array[--currIndex];
    }


}
