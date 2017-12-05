
package com.davidafaris.interfaces;

// Author David

import java.util.List;
/**
 * An interface to act as a formatting tool for a list of strings
 * @author David A. Faris, dfaris@my.wctc.edu
 */
public interface Formatter {
	/**
	 * Encodes a list of String in a way specified by the implementation
	 * @param lines the list of Strings to be encoded
	 * @return a list of Strings encoded to the implementation's requirements
	 */
	public List<String> encode(List<String> lines);
	/**
	 * Decodes a list of Strings in a way that undoes the encoding done by the same implementation
	 * @param lines the list of Strings to be decoded
	 * @return a list of Strings decoded in the opposite way of the encoder
	 */
	public List<String> decode(List<String> lines);
}
