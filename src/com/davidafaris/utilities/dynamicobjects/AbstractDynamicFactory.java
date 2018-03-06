
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
	 * @param config the config file that contains the path to the the object you wish to receive ex the property would be "com.davidafaris.utilities.ExampleClass"
	 * @param propertiesFileString
	 * @return 
	 */
	public static <T extends DynamicClassFields> T getSpecifiedObject(ConfigInformationStrategy config, String propertiesFileString){
		try{
			Object ret = ((T) getInstance((config.getProperty(propertiesFileString))));
			((T) ret).updateDynamicFields(config);
			return (T) ret;
		}catch(Exception ex){
			throw new SeriousException("There was an error loading the requested type of file: " + propertiesFileString);
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
