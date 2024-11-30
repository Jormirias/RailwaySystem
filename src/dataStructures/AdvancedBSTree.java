package dataStructures;                                         

/**
 * Advanced BSTree Data Type implementation
 * @author AED team
 * @version 1.0
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value 
 */
public abstract class AdvancedBSTree<K extends Comparable<K>, V> extends BinarySearchTree<K,V>
{
    /**
     * Performs a single left rotation rooted at Y node.
     * Node X was a  right  child  of Y before the  rotation,
     * then Y becomes the left child of X after the rotation.
     * @param Y - root of the rotation
     * @pre: Y has a right child
     */
    protected void rotateLeft( BSTNode<Entry<K,V>> Y)
    {
        //  a single rotation modifies a constant number of parent-child relationships,
        //  it can be implemented in O(1) time
        BSTNode<Entry<K,V>> Z = Y.getParent();
        rotateParent(Y, Z);

        BSTNode<Entry<K,V>> YLeft = Y.getLeft();
        Z.setRight(YLeft);
        if(YLeft != null) {
            YLeft.setParent(Z);
        }
        Y.setLeft(Z);
    }


    /**
     * Performs a single right rotation rooted at Y node.
     * Node X was a  left  child  of Y before the  rotation,
     * then Y becomes the right child of X after the rotation.
     * @param Y - root of the rotation
     * @pre: Y has a left child
     */
    protected void rotateRight( BSTNode<Entry<K,V>> Y)
    {
        //  a single rotation modifies a constant number of parent-child relationships,
        //  it can be implemented in O(1) time
        BSTNode<Entry<K,V>> Z = Y.getParent();
        rotateParent(Y, Z);

        BSTNode<Entry<K,V>> YRight = Y.getRight();
        Z.setLeft(YRight);
        if(YRight != null) {
            YRight.setParent(Z);
        }
        Y.setRight(Z);
    }

    private void rotateParent(BSTNode<Entry<K,V>> Y, BSTNode<Entry<K,V>> Z) {
        BSTNode<Entry<K,V>> ZParent = Z.getParent();
        Y.setParent(ZParent);
        if(ZParent != null) {
            if(ZParent.getRight() == Z) {
                ZParent.setRight(Y);
            } else {
                ZParent.setLeft(Y);
            }
        } else { // we're at root
            root = Y;
        }
        Z.setParent(Y);
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param X - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *         /  \      /  \       /  \        /  \
     *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *      /  \      /  \           /  \         /  \
     *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *   /  \          /  \       /  \             /  \
     *  t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BSTNode<Entry<K,V>> restructure(BSTNode<Entry<K,V>> X) {
        // the modification of a tree T caused by a trinode restructuring operation
        // can be implemented through case analysis either as a single rotation or as a double rotation.
        // The double rotation arises when position X has the middle of the three relevant keys
        // and is first rotated above its parent Y, and then above what was originally its grandparent Z.
        // In any of the cases, the trinode restructuring is completed with O(1)running time

        BSTNode<Entry<K,V>> newSubtreeRoot = null;
        BSTNode<Entry<K,V>> Y = X.getParent();
        BSTNode<Entry<K,V>> Z = Y.getParent();

        if(Y == Z.getLeft()) {
            if(X == Y.getLeft()) { // Left Left
                rotateRight(Y);
                newSubtreeRoot = Y;
            } else { // X == Y.getRight(), // Left Right
                rotateLeft(X);
                rotateRight(X);
                newSubtreeRoot = X;
            }
        } else { // Y == Z.getRight()
            if(X == Y.getRight()) { // Right Right
                rotateLeft(Y);
                newSubtreeRoot = Y;
            } else { // X == Y.getLeft(), Right Left
                rotateRight(X);
                rotateLeft(X);
                newSubtreeRoot = X;
            }
        }

        return newSubtreeRoot;
    }

}

