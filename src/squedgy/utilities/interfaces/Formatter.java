
package com.davidafaris.utilities.interfaces;

// Author David

import java.util.List;
import java.util.Map;
/**
 * An interface to act as a formatting tool for a list of strings
 * @author David A. Faris, dfaris@my.wctc.edu
 * @param <Key> The type of the Key that you are expecting to use within your decoded Map
 * @param <Value> The value type of the key within your decoded Map
 * @param <List> The type you are expecting your encoded list to use
 */
public interface Formatter<Key, Value, List> {
	/**
	 * Encodes a list of type List in a way specified by the implementation
	 * @param lines the Map of type Key,Value to be encoded
	 * @return a list of type List encoded to the implementation's requirements
	 */
	public List<List> encode(Map<Key, Value> lines);
	/**
	 * Decodes a list of type List in a way that undoes the encoding done by the same implementation
	 * @param lines the list of type List to be decoded
	 * @return a Map of type Key,Value that represents lines after undoing the encoding done by this same class.
	 */
	public Map<Key, Value> decode(List<List> lines);
}
