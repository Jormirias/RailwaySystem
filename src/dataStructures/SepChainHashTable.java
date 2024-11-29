package dataStructures;  

/**
 * Separate Chaining Hash table implementation
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value 
 */

public class SepChainHashTable<K extends Comparable<K>, V> 
    extends HashTable<K,V> 
{ 
	/**
	 * Serial Version UID of the Class.
	 */
    static final long serialVersionUID = 0L;

	/**
	 * The array of dictionaries.
	 */
    protected Dictionary<K,V>[] table;


    /**
     * Constructor of an empty separate chaining hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity )
    {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        // Compiler gives a warning.
        table = (Dictionary<K,V>[]) new Dictionary[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new OrderedDoubleList<K,V>();
        maxSize = capacity;
        currentSize = 0;
    }                                      


    public SepChainHashTable( )
    {
        this(DEFAULT_CAPACITY);
    }                                                                

    /**
     * Returns the hash value of the specified key.
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash( K key )
    {
        return Math.abs( key.hashCode() ) % table.length;
    }

    @Override
    public V find( K key )
    {
        return table[ this.hash(key) ].find(key);
    }

    @Override
    public V insert( K key, V value )
    {
        if ( this.isFull() ) {
            this.rehash();
        }

        int position = this.hash(key);
        this.table[position].insert(key, value);
        ++currentSize;
        return value;
    }

    @Override
    public V remove( K key )
    {
        int position = this.hash(key);
        V value = this.table[position].remove(key);
        if(value != null) {
            --currentSize;
        }
        return value;
    }

    @Override
    public Iterator<Entry<K,V>> iterator()
    {
        // TODO: redo this with a class that iterates over  the lists
        ListInArray<Entry<K,V>> list = new ListInArray<>(this.maxSize);

        for(int i = 0; i < this.maxSize; ++i) {
            Dictionary<K, V> dictionary = this.table[i];
            if(!dictionary.isEmpty()) {
                Iterator<Entry<K, V>> it = dictionary.iterator();
                while(it.hasNext()) {
                    list.addLast(it.next());
                }
            }
        }

        // The garbage collector won't kill the array so long as this iterator points to it.
        return list.iterator();
    } 

    /**
     * Allocates new table array and reinserts existing elements.
     * Same use-case as resize in other collections.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        Dictionary<K, V>[] oldTable = this.table;
        int oldMaxSize = this.maxSize;

        // Java doesn't allow placement new...
        int arraySize = HashTable.nextPrime((int) (1.1 * (maxSize * 2)));
        this.table = (Dictionary<K,V>[]) new Dictionary[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new OrderedDoubleList<K,V>();
        this.maxSize = arraySize;
        this.currentSize = 0;
        
        for(int i = 0; i < oldMaxSize; ++i) {
            Dictionary<K, V> dictionary = oldTable[i];
            if(!dictionary.isEmpty()) {
                Iterator<Entry<K, V>> it = dictionary.iterator();
                while(it.hasNext()) {
                    Entry<K, V> next = it.next();
                    this.insert(next.getKey(), next.getValue());
                }
            }
        } 
    }
}
































