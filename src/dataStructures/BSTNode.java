/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */
package dataStructures;

import java.io.Serial;
import java.io.Serializable;

/**
 * BST node implementation
 * 
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Value
 */
public class BSTNode<E> implements Serializable {

    @Serial
    private static final long serialVersionUID = -4575661114698067129L;
    /**
     * Element stored in the node.
     */
    protected E element;

    /**
     * (Pointer to) the left child.
     * 
     */
    protected BSTNode<E> left;

    /**
     * (Pointer to) the right child.
     * 
     */
    protected BSTNode<E> right;
    /**
     * (Pointer to) the parent node.
     *
     */
    protected BSTNode<E> parent;

    /**
     * Constructor for BST nodes
     * 
     */
    public BSTNode(E elem, BSTNode<E> parent, BSTNode<E> left, BSTNode<E> right) {
        this.element = elem;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public BSTNode(E elem) {
        this(elem, null, null, null);
    }

    /**
     * Returns the element of the current node.
     * 
     * @return
     */
    E getElement() {
        return element;
    }

    /**
     * Returns the left child node of the current node.
     * 
     * @return
     */
    BSTNode<E> getLeft() {
        return left;
    }

    /**
     * Returns the right child node of the current node.
     * 
     * @return
     */
    BSTNode<E> getRight() {
        return right;
    }

    /**
     * Returns the parent node of the current node.
     *
     * @return
     */
    BSTNode<E> getParent() {
        return parent;
    }

    // A node is internal when it is not the root nor a leaf.
    boolean isInternal() {
        return (parent != null && (left != null || right != null));
    }

    public void setLeft(BSTNode<E> left) {
        this.left = left;
    }

    public void setRight(BSTNode<E> right) {
        this.right = right;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public void setParent(BSTNode<E> parent) {
        this.parent = parent;
    }
}
