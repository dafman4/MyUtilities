
package com.squedgy.utilities.abstracts;

import com.squedgy.utilities.interfaces.FileFormatter;
import com.squedgy.utilities.interfaces.Formatter;

/**
 * This interface is designed to allow for a
 * simple and flexible writer system when combined with the Formatter interface
 * 
 * @author Squedgy
 * @version 1.0
 * @param <WriteType> The type the writer writes about
 * @since 1.0
 */
public abstract class Writer<WriteType, ReadType> {

	protected Formatter<WriteType, ReadType> formatter;

	public Writer(Formatter<WriteType, ReadType> formatter){
		setFormatter(formatter);
	}


	/**
	 * Writes to something specified by the implementation.
	 * 
	 * @param lines an object of type <WriteType> that the writer writes values from
	 * @throws Exception in order to allow for many different type of writing locations (files/console/gui/etc.)
	 */
	public abstract void write(WriteType lines) throws Exception;

	public final Formatter<WriteType, ReadType> getFormatter() {
		return formatter;
	}

	public final void setFormatter(Formatter<WriteType, ReadType> formatter) {
		if(formatter == null) throw new IllegalArgumentException("Formatter must not be null!");
		this.formatter = formatter;
	}
}
