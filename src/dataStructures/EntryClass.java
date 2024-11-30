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

	@Override
	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}
	
	

}
