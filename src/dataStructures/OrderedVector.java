package dataStructures;

public class OrderedVector<K extends Comparable<K>, V> implements OrderedDictionary<K, V> {
    private int capacity;
    private int size;

    // Entry<K, V>[]
    private Object[] entries;

    /**
     * Avoids implementing two different methods which are variations of findPosition,
     * allows for a search to always return a valid result and
     * gives meaning to what position was reached.
     * For internal use only!
     */
    class SearchResult {
        public static final boolean FOUND = true;
        public static final boolean NOT_FOUND = false;

        int position;
        boolean found;

        public SearchResult(int position, boolean found) {
            this.position = position;
            this.found = found;
        }

        public int getPosition() {
            return position;
        }

        public boolean wasFound() {
            return found;
        }
    }

    public OrderedVector(int capacity) {
        size = 0;
        this.capacity = capacity;
        entries = new Object[capacity];
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
        V value = null;
        SearchResult result = findPosition(key);

        if (result.wasFound()) {
            value = getEntry(result.getPosition()).getValue();
        }

        return value;
    }

    @Override
    public V insert(K key, V value) {
        if (size == (capacity - 1)) {
            resize();
        }

        if (size == 0) {
            setEntry(0, new EntryClass<K, V>(key, value));
            size += 1;
            return value;
        } else if (key.compareTo(getEntry(0).getKey()) < 0) {
            insertBeginning(key, value);
        } else if (key.compareTo(getEntry(size - 1).getKey()) > 0) {
            insertEnd(key, value);
        } else {
            insertMiddle(key, value);
        }

        return value;
    }

    @Override
    public V remove(K key) {
        V value = null;

        SearchResult result = findPosition(key);
        if (result.wasFound()) {
            int position = result.getPosition();
            value = getEntry(position).getValue();
            shiftInto(position);
            size -= 1;
        }

        return value;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<K, V> minEntry() throws EmptyDictionaryException {
        if (size == 0) {
            throw new EmptyDictionaryException();
        }

        return (Entry<K, V>) entries[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Entry<K, V> maxEntry() throws EmptyDictionaryException {
        if (size == 0) {
            throw new EmptyDictionaryException();
        }

        return (Entry<K, V>) entries[size - 1];
    }

    /**
     * Increases vector's capacity.
     * Copies all elements to a new array of new capacity = old capacity * 2.
     */
    private void resize() {
        capacity *= 2;

        Object[] newArray = new Object[capacity];

        for (int i = 0; i < size; ++i) {
            newArray[i] = entries[i];
        }

        entries = newArray;
    }

    // As the vector is ordered, we can do a binary search.
    private SearchResult findPosition(K key) {
        int first = 0;
        int last = size - 1;
        int middle = first;
        while (first <= last) {
            middle = (first + last) / 2;
            Entry<K, V> entry = getEntry(middle);
            K currentKey = entry.getKey();

            int comparsionResult = key.compareTo(currentKey);
            if (comparsionResult < 0) {
                last = middle - 1;
            } else if (comparsionResult > 0) {
                first = middle + 1;
            } else { // == 0
                return new SearchResult(middle, SearchResult.FOUND);
            }
        }

        return new SearchResult(middle, SearchResult.NOT_FOUND);
    }

    private void insertBeginning(K key, V value) {
        shiftFrom(0);
        setEntry(0, new EntryClass<K, V>(key, value));
        size += 1;
    }

    private void insertEnd(K key, V value) {
        setEntry(size, new EntryClass<K, V>(key, value));
        size += 1;
    }

    private void insertMiddle(K key, V value) {
        SearchResult result = findPosition(key);
        if (result.wasFound()) {
            // update current value
            getEntry(result.getPosition()).setValue(value);
        } else {
            int entryPosition = result.getPosition();
            Entry<K, V> entry = getEntry(entryPosition);

            if (key.compareTo(entry.getKey()) < 0) {
                shiftFrom(entryPosition);
            } else if (key.compareTo(entry.getKey()) > 0) {
                entryPosition += 1;
                shiftFrom(entryPosition);
            } else {
                // blow up, as this should be made impossible by the external if condition
            }

            setEntry(entryPosition, new EntryClass<K, V>(key, value));
            size += 1;
        }
    }

    private void shiftFrom(int position) {
        for (int i = size; i > position; --i) {
            entries[i] = entries[i - 1];
        }
    }

    private void shiftInto(int position) {
        for (int i = position; i < size; ++i) {
            entries[i] = entries[i + 1];
        }
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> getEntry(int position) {
        return (Entry<K, V>) entries[position];
    }

    private void setEntry(int position, Entry<K, V> entry) {
        entries[position] = entry;
    }

}
