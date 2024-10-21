package dataStructures;

import java.lang.Comparable;
import java.io.Serializable;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedDictionary<K,V> {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    static class BSTNode<K,V> implements Serializable {
        // Entry stored in the node.
        private EntryClass<K, V> entry;
        // (Pointer to) the left child.
        private BSTNode<K, V> leftChild;
        // (Pointer to) the right child.
        private BSTNode<K, V> rightChild;

        public BSTNode( K key, V value, BSTNode<K,V> left,
                        BSTNode<K,V> right ){
            entry = new EntryClass<K,V>(key, value);
            leftChild = left;
            rightChild = right;
        }
        public BSTNode( K key, V value ){
            this(key, value, null, null);
        }

        public EntryClass<K,V> getEntry( ){
            return entry;
        }
        public K getKey( ){
            return entry.getKey();
        }
        public V getValue( ){
            return entry.getValue();
        }

        public BSTNode<K,V> getLeft( ){
            return leftChild;
        }
        public BSTNode<K,V> getRight( ){
            return rightChild;
        }
        public void setEntry( EntryClass<K,V> newEntry ){
            entry = newEntry;
        }

        public void setEntry( K newKey, V newValue ){
            entry.setKey(newKey);
            entry.setValue(newValue);
        }
        public void setKey( K newKey ){
            entry.setKey(newKey);
        }
        public void setValue( V newValue ){
            entry.setValue(newValue);
        }

        public void setLeft( BSTNode<K,V> newLeft ){
            leftChild = newLeft;
        }
        public void setRight( BSTNode<K,V> newRight ){
            rightChild = newRight;
        }
        // Returns true if the node is a leaf.
        public boolean isLeaf( ){
            return leftChild == null && rightChild == null;
        }
    }

    protected BSTNode<K, V> root;

    protected int currentSize;

    public BinarySearchTree( ){
        root = null;
        currentSize = 0;
    }
    public boolean isEmpty( ){
        return root == null;
    }
    public int size( ){
        return currentSize;
    }

    //TODO!!

    public EntryClass<K, V> minEntry( ){
        return null;
    }
    public EntryClass<K, V> maxEntry( ){
        return null;
    }
    public V find(K key ){
        return null;
    }
    public V insert(K key, V value ){
        return null;
    }
    public V remove(K key){
        return null;
    }
    //TODO!!
    public Iterator<Entry<K,V>> iterator() {
        return null;
        //return new DoubleListIterator(this.head, this.tail)
    }
}