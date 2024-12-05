package dataStructures;

import java.io.Serial;

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

    @Serial
    private static final long serialVersionUID = 4911151549064567588L;

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
        while (zPos != null) { // traverse up the tree towards the root
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
            
            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
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

    // Mostly copy-paste BST remove, but taking into consideration the need for rebalancing
    // is at the actually deleted node.
    @Override
    public V remove(K key) {
        AVLNode<Entry<K, V>> node = (AVLNode<Entry<K, V>>) this.findNode(key);
        if (node == null || node.getElement().getKey().compareTo(key) != 0)
            return null;
        else {
            V oldValue = node.getElement().getValue();
            AVLNode<Entry<K, V>> rebalancingNode;

            if (node.getLeft() == null) {
                rebalancingNode = (AVLNode<Entry<K, V>>) node.getParent();
                this.linkSubtreeRemove(node.getRight(), node.getParent(), node);
            }
            else if (node.getRight() == null) {
                rebalancingNode = (AVLNode<Entry<K, V>>) node.getParent();
                this.linkSubtreeRemove(node.getLeft(), node.getParent(), node);
            }
            else {
                // This is going to be a leaf
                AVLNode<Entry<K, V>> minNode = (AVLNode<Entry<K, V>>) this.minNode(node.getRight());
                rebalancingNode = (AVLNode<Entry<K, V>>) minNode.getParent();
                node.setElement(minNode.getElement());
                this.linkSubtreeRemove(minNode.getRight(), minNode.getParent(), minNode);
            }
            rebalance(rebalancingNode);
            currentSize--;
            return oldValue;
        }
    }
}
