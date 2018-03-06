
package com.davidafaris.utilities.dynamicobjects;

import com.davidafaris.utilities.interfaces.ConfigInformationStrategy;
import com.davidafaris.utilities.interfaces.DynamicClassFields;
import com.davidafaris.utilities.error.SeriousException;
import java.lang.reflect.Constructor;

// Author David
public abstract class AbstractDynamicFactory {
	
	/**
	 * This is a method to get an object that can dynamically update it's fields this if you just want an object look at getInstance
	 * within this class.
	 * @param <T> the type that you wish to receive back that implement/extends DynamicClassFields
	 * @param <K> the type of the Keys that the ConfigInformationStrategy used uses
	 * @param config the config file that contains the path to the the object you wish to receive Ex. the property would be "com.davidafaris.utilities.ExampleClass" and the key would be "example"
	 * @param propertyKey an object of type K representing the Key that links to the class path within the project
	 * @return and object of type T that has the ability to update it's fields dynamically while the program is running
	 */
	public static <T extends DynamicClassFields, K> T getSpecifiedObject(ConfigInformationStrategy<K> config, K propertyKey){
		try{
			Object ret = ((T) getInstance((config.getProperty(propertyKey))));
			((T) ret).updateDynamicFields(config);
			return (T) ret;
		}catch(Exception ex){
			throw new SeriousException("There was an error loading the requested type of file: " + propertyKey);
		}
	}

	public static <T> T getInstance(String className)throws Exception{
		Class clazz = Class.forName(className);
		Constructor cons = clazz.getDeclaredConstructor(new Class[0]);
		if(!cons.isAccessible())
			cons.setAccessible(true);
		T ret = (T)cons.newInstance(new Object[0]);
		return ret;
	}
}
