
package com.davidafaris.utilities.interfaces;

// Author David

import java.util.List;
import java.util.Map;
/**
 * An interface to act as a formatting tool for a list of strings
 * @author David A. Faris, dfaris@my.wctc.edu
 * @param <K> The type of the Key that you are expecting to use within your decoded Map
 * @param <V> The value type of the key within your decoded Map
 * @param <L> The type you are expecting your encoded list to use 
 */
public interface Formatter<K,V,L> {
	/**
	 * Encodes a list of type L in a way specified by the implementation
	 * @param lines the Map of type K,V to be encoded
	 * @return a list of type L encoded to the implementation's requirements
	 */
	public List<L> encode(Map<K,V> lines);
	/**
	 * Decodes a list of type L in a way that undoes the encoding done by the same implementation
	 * @param lines the list of type L to be decoded
	 * @return a Map of type K,V that represents lines after undoing the encoding done by this same class.
	 */
	public Map<K,V> decode(List<L> lines);
}
