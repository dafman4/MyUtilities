
package com.squedgy.utilities.abstracts;


import com.squedgy.utilities.interfaces.Formatter;

/**
 * This interface is designed to allow for a
 * simple and flexible reader system when combined with the Formatter interface
 * 
 * @author Squedgy
 * @version 1.0
 * @param <ReadType>
 * @param <WriteType>
 * @since 1.0
 */
public abstract class Reader <WriteType, ReadType>{

	protected Formatter<WriteType, ReadType> formatter;

	public Reader(Formatter<WriteType, ReadType> formatter){
		setFormatter(formatter);
	}

	/**
	 *Returns a list of strings read from somewhere specified by the concrete class
	 * @return a list of strings read from somewhere or something
	 */
	public abstract WriteType read();

	public final void setFormatter(Formatter<WriteType, ReadType> formatter){
		if(formatter == null) throw new IllegalArgumentException("formatter cannot be null!");
		this.formatter = formatter;
	}

	public final Formatter<WriteType, ReadType> getFormatter(){
		return formatter;
	}
}
