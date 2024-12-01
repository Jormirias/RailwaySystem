package dataStructures;

public class ReverseIterator<E> implements Iterator<E> {

    TwoWayIterator<E> iterator;

    public ReverseIterator(TwoWayIterator<E> iterator) {
        this.iterator = iterator;
        this.iterator.fullForward();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasPrevious();
    }

    @Override
    public E next() throws NoSuchElementException {
        return  iterator.previous();
    }

    @Override
    public void rewind() {
        iterator.fullForward();
    }

}
