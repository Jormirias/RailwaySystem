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
    extends AdvancedBSTree<K,V> implements OrderedDictionary<K,V>
{                                                                   

    protected AVLTree(AVLNode<Entry<K,V>> node) {
        root = node;
    }

    public AVLTree() {
        this(null);
    }

    /**
     * Rebalance method called by insert and remove.  Traverses the path from
     * zPos to the root. For each node encountered, we recompute its height
     * and perform a trinode restructuring if it's unbalanced.
     * the rebalance is completed with O(log n) running time
     */
    void rebalance(AVLNode<Entry<K,V>> zPos) {
        if(zPos.isInternal())
            zPos.setHeight();
        // Improve if possible...
        while (zPos!=null) {  // traverse up the tree towards the root
            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
            zPos.setHeight();
            if (!zPos.isBalanced()) {
                // perform a trinode restructuring at zPos's tallest grandchild
                //If yPos (zPos.tallerChild()) denote the child of zPos with greater height.
                //Finally, let xPos be the child of yPos with greater height
                AVLNode<Entry<K,V>> xPos = zPos.tallerChild().tallerChild();

                zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
                ((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight();  // recompute heights
                ((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
                zPos.setHeight();
            }
        }
    }


    @Override
    public V insert( K key, V value )
    {
        //TODO
        V valueToReturn=null;
        AVLNode<Entry<K,V>> newNode=null; // node where the new entry is being inserted (if find(key)==null)
        // insert the new Entry (if find(key)==null)
        // or set the new value (if find(key)!=null)
        if(newNode != null) //(if find(key)==null)
            rebalance(newNode); // rebalance up from the insertion node
        return valueToReturn;

    }







        

               

   @Override
    public V remove( K key )
     {
         // TODO
         V valueToReturn=null;
         AVLNode<Entry<K,V>> node=null; // father of node where the key was
         // removeNode is the BST remove(key)
         if(node != null) //(if find(key)==null)
             rebalance(node); // rebalance up from the node
         return valueToReturn;
    }














}
