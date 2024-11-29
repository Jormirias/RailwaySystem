/**
 * 
 */
package dataStructures;

/**
 * @author AED
 *
 */
public class EntryClass<K, V> implements Entry<K, V>{
	protected K key;
	protected V value;
	
	public EntryClass(K k, V v){
		key=k;
		value=v;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public void setValue(V value2) {
		value=value2;
	}
	
	

}
