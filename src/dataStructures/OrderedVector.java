package dataStructures;

public class OrderedVector<K extends Comparable<K>, V> implements OrderedDictionary<K, V> {
    private int capacity;
    private int size;

    private Entry<K, V>[] entries;

    @SuppressWarnings("unchecked")
    public OrderedVector(int capacity) {
        size = 0;
        this.capacity = capacity;
        entries = (Entry<K, V>[]) new Object[capacity];
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
    public V find(K key) {
        Integer position = findPosition(key);

        if(position != null) {
            return entries[position].getValue();
        }

        return null;
    }

    @Override
    public V insert(K key, V value) {
        if(size == (capacity - 1)) {
            resize();
        }

        
    }

    @Override
    public V remove(K key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Entry<K, V> minEntry() throws EmptyDictionaryException {
        if(size == 0) {
            throw new EmptyDictionaryException();
        }

        return entries[0];
    }

    @Override
    public Entry<K, V> maxEntry() throws EmptyDictionaryException {
        if(size == 0) {
            throw new EmptyDictionaryException();
        }

        return entries[size - 1];
    }
    
	/**
	 * Increases vector's capacity.
     * Copies all elements to a new array of new capacity = old capacity * 2.
	 */
    private void resize() {
        capacity * 2;
    }

    // As the vector is ordered, we can do a binary search.
    private Integer findPosition(K key) {
        int first = 0;
        int last = size;
        while (first != last) {
            int middle = (first + last) / 2;
            Entry<K, V> entry = entries[middle];
            K currentKey = entry.getKey();

            int comparsionResult = currentKey.compareTo(key);
            if (comparsionResult < 0) {
                last = middle - 1;
            } else if (comparsionResult > 0) {
                first = middle + 1;
            } else { // == 0
                return middle;
            }
        }

        return null;
    }

}
