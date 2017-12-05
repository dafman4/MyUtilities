
package com.davidafaris.interfaces;

import java.util.List;

/**
 * This interface is designed to allow for a
 * simple and flexible writer system
 * 
 * @author David A. Faris, dfaris@my.wctc.edu
 * @version 1.0
 * @since 1.0
 */
public interface Writer {
	/**
	 * Writes to something specified by the implementation.
	 * 
	 * @param string a string to be implemented in whatever way the implementation sees fit
	 * @throws Exception in order to allow for many different type of writing locations (files/console/gui/etc.)
	 */
	public void write(List<String> strings) throws Exception;
}
