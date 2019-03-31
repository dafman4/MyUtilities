
package com.squedgy.utilities.interfaces;

import java.io.IOException;

/**
 * An interface to act as a formatting tool
 * @author Squedgy
 * @param <FormattedType> The type being input for formatting
 * @param <EncodedType> The type being produced after formatting
 */
public interface Formatter<FormattedType, EncodedType> {

	/**
	 * @param toEncode
	 * @return an EncodedType instance
	 */
	public EncodedType encode(FormattedType toEncode) throws IOException;

	/**
	 * @param toDecode the EncodedType to decode into an FormattedType
	 * @return FormattedType instance
	 */
	public FormattedType decode(EncodedType toDecode) throws IOException;
}
