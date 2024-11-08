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

    protected boolean isInverted = false;

    /**
     * ListArrayIterator constructor
     */
    public ListInArrayIterator( E[] listArray, int currSize)
    {
        array = listArray;
        currIndex=0;
        size=currSize;
    }      

    public void invert() {
        isInverted = !isInverted;
    }

    @Override
    public void rewind( )
    {
        if (!isInverted)
            currIndex=0;
        else
            currIndex=size;
    }


    @Override
    public void fullForward( )    {
        if (!isInverted)
            currIndex=size;
        else
            currIndex=0;
    }


    @Override
    public boolean hasNext( )
    {
        if (!isInverted)
            return currIndex < size;
        else
            return currIndex > 0;
    }


    @Override
    public boolean hasPrevious( )
    {
        if (!isInverted)
            return currIndex > 0;
        else
            return currIndex < size;
    }


    @Override
    public E next( ) throws NoSuchElementException
    {
        if ( !this.hasNext() )
            throw new NoSuchElementException();

        if (!isInverted)
            return array[currIndex++];
        else
            return array[--currIndex];

    }


    @Override
    public E previous( ) throws NoSuchElementException
    {
        if ( !this.hasPrevious() )
            throw new NoSuchElementException();

        if (!isInverted)
            return array[--currIndex];
        else
            return array[currIndex++];
    }


}
