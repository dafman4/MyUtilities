
package com.davidafaris.utilities.interfaces;

import java.util.Map;

/**
 * This interface is designed to allow for a
 * simple and flexible writer system when combined with the Formatter interface
 * 
 * @author David A. Faris, dfaris@my.wctc.edu
 * @version 1.0
 * @param <Key> The type of the Key within the Map to be written
 * @param <Value> The type of the Value within the Map to be written
 * @since 1.0
 */
public interface Writer<Key, Value> {
	/**
	 * Writes to something specified by the implementation.
	 * 
	 * @param lines the map that will be used for 
	 * @throws Exception in order to allow for many different type of writing locations (files/console/gui/etc.)
	 */
	public void write(Map<Key, Value> lines) throws Exception;
}
