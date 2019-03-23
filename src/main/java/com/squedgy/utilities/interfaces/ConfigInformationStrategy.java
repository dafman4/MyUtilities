
package com.squedgy.utilities.interfaces;

// Author Squedgy

import java.util.Map;
/**
 * This should be a class that only allows for 1 instance
 * of itself to be in memory at any given time.
 * 
 * 
 * @author Squedgy
 * @param <Key> Key is the type of the key in the map used to store properties
 *             (this should match the type of the key of your formatter map, if applicable)
 */
public interface ConfigInformationStrategy <Key> {
	public Map<Key,String> getProperties();
	public void setProperties(Map<Key,String> newProps);
	public String getProperty(Key key);
	public void setProperty(Key key, String value);
}
