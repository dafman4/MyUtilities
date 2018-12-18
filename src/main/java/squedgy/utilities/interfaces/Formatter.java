
package squedgy.utilities.interfaces;

// Author David

import java.util.List;
import java.util.Map;
/**
 * An interface to act as a formatting tool for a list of strings
 * @author David A. Faris, dfaris@my.wctc.edu
 * @param <InputType> The type being input for formatting
 * @param <OutputType> The type being output
 */
public interface Formatter<InputType, OutputType> {
	/**
	 * Encodes a list of type ListType in a way specified by the implementation
	 * @param lines the Map of type KeyType,ValueType to be encoded
	 * @return a list of type ListType encoded to the implementation's requirements
	 */
	public OutputType encode(InputType lines);

	/**
	 * Decodes a list of type ListType in a way that undoes the encoding done by the same implementation
	 * @param lines the list of type ListType to be decoded
	 * @return a Map of type KeyType,ValueType that represents lines after undoing the encoding done by this same class.
	 */
	public InputType decode(OutputType lines);
}
