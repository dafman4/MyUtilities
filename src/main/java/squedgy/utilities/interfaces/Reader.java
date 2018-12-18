
package squedgy.utilities.interfaces;


import java.util.Map;
/**
 * This interface is designed to allow for a
 * simple and flexible reader system when combined with the Formatter interface
 * 
 * @author Squedgy
 * @version 1.0
 * @param <Key> The Type of the key within the returned Map
 * @param <Value> The Type of the value within the returned Map
 * @since 1.0
 */
public interface Reader <Key, Value>{
	/**
	 *Returns a list of strings read from somewhere specified by the concrete class
	 * @return a list of strings read from somewhere or something
	 * @throws Exception in order to easily allow for many types of things to be read from (custom error messages recommended)
	 */
	public Map<Key, Value> read() throws Exception;
}
