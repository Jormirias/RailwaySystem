/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */
package dataStructures;

import java.io.Serial;

/**
 * @author AED
 *
 */
public class EntryClass<K, V> implements Entry<K, V>{


	@Serial
	private static final long serialVersionUID = 5527641447887287619L;
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
