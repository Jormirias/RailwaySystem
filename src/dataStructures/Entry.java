/**
 * 
 */
package dataStructures;

import java.io.Serializable;

/**
 * @author fernanda
 *
 */
public interface Entry<K, V>  extends Serializable {
	// Returns the key in the entry.
	K getKey( );
	 
	// Returns the value in the entry.
	V getValue( );

	void setKey(K key);
	
	void setValue(V value);
}
