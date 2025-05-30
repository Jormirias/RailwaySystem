/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */
package dataStructures;

class BSTKeyOrderIterator<K,V> implements Iterator<Entry<K,V>> {

	
	protected BSTNode<Entry<K,V>> root;

	protected Stack<BSTNode<Entry<K,V>>> p;


	BSTKeyOrderIterator(BSTNode<Entry<K,V>> root){
		this.root=root;
		rewind();
	}
	
	private void pushPathToMinimum(BSTNode<Entry<K,V>> node) {
		BSTNode<Entry<K,V>> current = node;
		while(current.getLeft() != null) {
			p.push(current);
			current = current.getLeft();
		}
		p.push(current);
	}

	//O(1) para todos os casos
	public boolean hasNext(){
		 return !p.isEmpty();
	 }


    public Entry<K,V> next( ) throws NoSuchElementException {
    	if (!hasNext()) throw new NoSuchElementException();
    	else {
    		BSTNode<Entry<K,V>> node = p.pop();
			if(node.getRight() != null)
				pushPathToMinimum(node.getRight());
			return node.getElement();
    	}
    }

    public void rewind( ){
		p = new StackInList<BSTNode<Entry<K,V>>>();
		if(root != null) {
    		pushPathToMinimum(root);
		}
    }
}
