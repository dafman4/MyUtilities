
package com.davidafaris.interfaces;


import java.util.List;
/**
 * This interface is designed to allow for a
 * simple and flexible reader system
 * 
 * @author David A. Faris, dfaris@my.wctc.edu
 * @version 1.0
 * @since 1.0
 */
public interface Reader {
	/**
	 *Returns a list of strings read from somewhere specified by the concrete class
	 * @return a list of strings read from somewhere or something
	 * @throws exception in order to easily allow for many types of things to be read from (custom error messages recommended)
	 */
	public List<String> read() throws Exception;
}
