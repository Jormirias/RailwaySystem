/**
 * 
 */
package dataStructures;

/**
 * @author fernanda
 *
 */
public interface Entry<K, V> {
	// Returns the key in the entry.
	K getKey( );
	 
	// Returns the value in the entry.
	V getValue( );
}
