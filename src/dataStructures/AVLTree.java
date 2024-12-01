package dataStructures;

/**
 * AVL tree implementation
 *
 * @author AED team
 * @version 1.0
 *
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value
 */
public class AVLTree<K extends Comparable<K>, V>
        extends AdvancedBSTree<K, V> implements OrderedDictionary<K, V> {

    AVLTree(AVLNode<Entry<K, V>> node) {
        root = node;
    }

    public AVLTree() {
        this(null);
    }

    /**
     * Rebalance method called by insert and remove. Traverses the path from
     * zPos to the root. For each node encountered, we recompute its height
     * and perform a trinode restructuring if it's unbalanced.
     * the rebalance is completed with O(log n) running time
     */
    void rebalance(AVLNode<Entry<K, V>> zPos) {
        if (zPos.isInternal())
            zPos.setHeight();
        while (zPos != null) { // traverse up the tree towards the root
            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
            if (zPos == null) // reached the root, stop.
                break;

            zPos.setHeight();
            if (!zPos.isBalanced()) {
                // perform a trinode restructuring at zPos's tallest grandchild
                // If yPos (zPos.tallerChild()) denote the child of zPos with greater height.
                // Finally, let xPos be the child of yPos with greater height
                AVLNode<Entry<K, V>> xPos = zPos.tallerChild().tallerChild();

                zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
                ((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight(); // recompute heights
                ((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
                zPos.setHeight();
            }
        }
    }

    @Override
    public V insert(K key, V value) {
        V valueToReturn = null;
        if (isEmpty()) {
            root = new AVLNode<Entry<K, V>>(new EntryClass<K, V>(key, value));
            ++currentSize;
            return valueToReturn;
        }

        AVLNode<Entry<K, V>> newNode = (AVLNode<Entry<K, V>>) findNode(key);
        int keyComparsionResult = key.compareTo(newNode.getElement().getKey());
        if (keyComparsionResult != 0) { // add new node
            AVLNode<Entry<K, V>> parentNode = newNode;
            newNode = new AVLNode<Entry<K, V>>(new EntryClass<K, V>(key, value));
            newNode.setParent(parentNode);

            if (keyComparsionResult < 0) {
                parentNode.setLeft(newNode);
            } else {
                parentNode.setRight(newNode);
            }

            rebalance(newNode); // rebalance up from the insertion node, also updates parent height
            ++currentSize;
        } else { // update the node's element
            valueToReturn = newNode.getElement().getValue();
            newNode.setElement(new EntryClass<K, V>(key, value));
        }

        return valueToReturn;
    }

    @Override
    public V remove(K key) {
        V valueToReturn = super.remove(key); // will decrement size if key exists.
        if(!isEmpty() && valueToReturn != null) {
            AVLNode<Entry<K, V>> node = (AVLNode<Entry<K, V>>) findNode(key);
            node.setHeight();
            rebalance(node);
        }
        return valueToReturn;
    }



    public static void main(String[] args) {
        AVLTree<Integer, Integer> tree = new AVLTree<>();
        tree.insert(50, 0);
        tree.insert(25, 0);
        tree.insert(75, 0);
        tree.insert(15, 0);
        tree.insert(35, 0);
        tree.insert(60, 0);
        tree.insert(120, 0);
        tree.insert(10, 0);
        tree.insert(68, 0);
        tree.insert(90, 0);
        tree.insert(125, 0);
        tree.insert(83, 0);
        tree.insert(100, 0);

        tree.remove(120);
        tree.remove(10);
    }
}
