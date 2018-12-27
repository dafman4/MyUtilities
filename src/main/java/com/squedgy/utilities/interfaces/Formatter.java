
package com.squedgy.utilities.interfaces;

/**
 * An interface to act as a formatting tool
 * @author Squedgy
 * @param <InputType> The type being input for formatting
 * @param <OutputType> The type being produced after formatting
 */
public interface Formatter<InputType, OutputType> {

	/**
	 * @param toEncode
	 * @return an OutputType instance
	 */
	public OutputType encode(InputType toEncode);

	/**
	 * @param toDecode the OutputType to decode into an InputType
	 * @return InputType instance
	 */
	public InputType decode(OutputType toDecode);
}
