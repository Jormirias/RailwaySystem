package dataStructures;

public class BSTPreorderIterator<K,V> implements Iterator<Entry<K,V>> {

	
	protected BSTNode<Entry<K,V>> root;
	
	protected Stack<BSTNode<Entry<K,V>>> st;

	BSTPreorderIterator(BSTNode<Entry<K,V>> root){
		this.root=root;
		rewind();
	}
	
	public boolean hasNext(){
			return !st.isEmpty();
		}

	public Entry<K,V> next( ) throws NoSuchElementException{
		if (!hasNext()) throw new NoSuchElementException();
		else {
			BSTNode<Entry<K,V>> node=st.pop();
			if (node.getRight()!=null) st.push(node.getRight());
			if (node.getLeft()!=null) st.push(node.getLeft());
			return node.getElement();
		}
	}

	public void rewind( ){
		st=new StackInList<BSTNode<Entry<K,V>>>();
		if (this.root!= null) st.push(this.root);
	}

}
