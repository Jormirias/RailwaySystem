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

public static void main(String[] args){
	

	BinarySearchTree<Integer,Integer> bst=new BinarySearchTree<>();
	bst.insert(12, 12);
	bst.insert(3, 3);
	bst.insert(21,21);
	bst.insert(1, 1);
	bst.insert(4, 4);
	bst.insert(18, 18);
	Iterator<Entry<Integer,Integer>> it = new BSTPreorderIterator<>(bst.root);
	while (it.hasNext()) {
		System.out.println(it.next().getKey());
	}
}
}
