
package com.squedgy.utilities.interfaces;

import java.io.IOException;

/**
 * This interface is designed to allow for a
 * simple and flexible writer system when combined with the Formatter interface
 * 
 * @author Squedgy
 * @version 1.0
 * @param <WriteType> The type the writer writes about
 * @since 1.0
 */
public interface Writer<WriteType, ReadType> {

	/**
	 * Writes to something specified by the implementation.
	 * 
	 * @param toWrite an object of type <WriteType> that the writer writes values from
	 * @return ReadType returns an instance of the ReadType
	 */
	public abstract ReadType write(WriteType toWrite) throws IOException;

}
