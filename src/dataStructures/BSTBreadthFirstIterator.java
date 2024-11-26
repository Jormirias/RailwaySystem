package dataStructures;

/*
// completa n n n 
class BSTBreadthFirstIterator<K,V> implements Iterator<Entry<K,V>> {
	
	protected BSTNode<K,V> root;
	// espacial 1 n n
	protected Queue<BSTNode<K,V>> q;
	
	// 1 1 1
	public  BSTBreadthFirstIterator(BSTNode<K,V> root){
		this.root=root;
		rewind();
		
	}
	
	// 1 1 1
	public boolean hasNext( ){
		return !q.isEmpty();
	}
	
	// 1 1 1
	private void enqueueNode(BSTNode<K,V> node){
		if (node!=null) q.enqueue(node);
	}
	
	// 1 1 1
    public Entry<K,V> next( ) throws NoSuchElementException{
    	if (!hasNext()) throw new NoSuchElementException();
    	BSTNode<K,V> node=q.dequeue();
    	enqueueNode(node.getLeft());
    	enqueueNode(node.getRight());
    	return node.getEntry();
    }

    // 1 1 1
    public void rewind( ){
    	q=new QueueInList<BSTNode<K,V>>();
    	enqueueNode(root);
    	
    }

}
*/