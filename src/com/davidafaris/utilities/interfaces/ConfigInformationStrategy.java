
package com.davidafaris.utilities.interfaces;

// Author David

import java.util.Map;
/**
 * This should be a class that only allows for 1 instance
 * of itself to be in memory at any given time.
 * 
 * 
 * @author David
 * @param <K> K is the type of the key in the map used to store properties(this should match the type of the key of your formatter map, if applicable)
 */
public interface ConfigInformationStrategy <K> {
	public Map<K,String> getProperties();
	public void setProperties(Map<K,String> newProps);
	public String getProperty(K key);
	public void setProperty(K key, String value);
}
