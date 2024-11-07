package dataStructures;


public class OrderedDoubleList<K extends Comparable<K>, V> implements OrderedDictionary<K,V> {


    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    /**
     *  Node at the head of the list.
     */
    protected DoubleListNode<Entry<K,V>> head;

    /**
     * Node at the tail of the list.
     */
    protected DoubleListNode<Entry<K,V>> tail;

    /**
     * Number of elements in the list.
     */
    protected int currentSize;

    /**
     * Constructor of an empty ordered double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public OrderedDoubleList( )
    {
        head = null;
        tail = null;
        currentSize = 0;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public TwoWayIterator<Entry<K, V>> iterator() {
        return new DoubleListIterator<>(head, tail);
    }

    @Override
    public Entry<K, V> minEntry() throws EmptyDictionaryException {
        if ( isEmpty() )
            throw new EmptyDictionaryException();
        return head.getElement();
    }

    @Override
    public Entry<K, V> maxEntry() throws EmptyDictionaryException {
        if ( isEmpty() )
            throw new EmptyDictionaryException();
        return tail.getElement();
    }

    protected DoubleListNode<Entry<K, V>> findNode( K key ) {
        DoubleListNode<Entry<K, V>> currNode = head;

        while (currNode != null) {
            int keyCompare = key.compareTo(currNode.getElement().getKey());

            if (keyCompare == 0) {
                return currNode;
            }
            else if (keyCompare < 0) {
                break;
            }

            currNode = currNode.getNext();
        }
        return null;
    }

    @Override
    public V find(K key) throws EmptyListException, InvalidPositionException {
        if ( isEmpty() )
            throw new EmptyDictionaryException();

        //Verifica se a key dada é menor do que a da head
        else if ( key.compareTo(head.getElement().getKey()) < 0 )
            throw new InvalidPositionException();

        //Verifica se a key dada é maior do que a da tail
        else if ( key.compareTo(tail.getElement().getKey()) > 0 )
            throw new InvalidPositionException();

        //Verifica todos os nodes a partir do primeiro
        else {
            if (findNode(key) != null)
                return findNode(key).getElement().getValue();
            else
                throw new InvalidPositionException();
        }
    }

    protected V insertMiddle( K key, V value )
    {
        DoubleListNode<Entry<K, V>> currNode = head;

        while (key.compareTo(currNode.getElement().getKey()) > 0) {
            currNode = currNode.getNext();
        }


        if (key.compareTo(currNode.getElement().getKey()) == 0) {
            currNode.getElement().setValue(value);
            return value;
        }

        else {
            DoubleListNode<Entry<K, V>> nextNode = currNode;
            DoubleListNode<Entry<K, V>> prevNode = nextNode.getPrevious();
            DoubleListNode<Entry<K, V>> newNode = new DoubleListNode<>(new EntryClass<>(key,value), prevNode, nextNode);
            nextNode.setPrevious(newNode);
            prevNode.setNext(newNode);
            currentSize++;
            return value;
        }
    }

    @Override
    public V insert(K key, V value) {
        DoubleListNode<Entry<K,V>> node = new DoubleListNode<>( new EntryClass<>(key,value), null, null);
        if (isEmpty()){
            head = node;
            tail = node;
            currentSize++;
            return value;
        } else if (key.compareTo(head.getElement().getKey()) < 0 ) {
            head.setPrevious(node);
            node.setNext(head);
            head = node;
            currentSize++;
            return value;
        } else if (key.compareTo(tail.getElement().getKey()) > 0) {
            tail.setNext(node);
            node.setPrevious(tail);
            tail=node;
            currentSize++;
            return value;
        } else {
            return insertMiddle(key, value);
        }
    }

    protected void removeFirstNode( )
    {
        head = head.getNext();
        if ( head == null )
            tail = null;
        else
            head.setPrevious(null);
        currentSize--;
    }


    public V removeFirst( )
    {
        if ( this.isEmpty() )
            return null;
        Entry<K, V> element = head.getElement();
        this.removeFirstNode();
        return element.getValue();
    }


    protected void removeLastNode( )
    {
        tail = tail.getPrevious();
        if ( tail == null )
            head = null;
        else
            tail.setNext(null);
        currentSize--;
    }


    public V removeLast( )
    {
        if ( this.isEmpty() )
            return null;
        Entry<K, V> element = tail.getElement();
        this.removeLastNode();
        return element.getValue();
    }


    protected void removeMiddleNode( DoubleListNode<Entry<K, V>> node )
    {
        DoubleListNode<Entry<K, V>> prevNode = node.getPrevious();
        DoubleListNode<Entry<K, V>> nextNode = node.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrevious(prevNode);
        node.setNext(null);
        node.setPrevious(null);
        currentSize--;

    }

    public V removeMiddle( K key  )
    {
        if ( this.isEmpty() )
            return null;
        DoubleListNode<Entry<K, V>> node = findNode(key);
        this.removeMiddleNode(node);
        return node.getElement().getValue();
    }

    @Override
    public V remove(K key) {
        if ( isEmpty() )
            return null;
        else if (key.compareTo(head.getElement().getKey()) < 0)
            return null;
        else if (key.compareTo(tail.getElement().getKey()) > 0)
            return null;
        else if (key.compareTo(head.getElement().getKey()) == 0) {
            return removeFirst();
        }
        else if (key.compareTo(tail.getElement().getKey()) == 0) {
            return removeLast();
        }
        else {
            return removeMiddle(key);
        }
    }

}
