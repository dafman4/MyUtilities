
package squedgy.utilities.dynamicobjects;

import squedgy.utilities.interfaces.ConfigInformationStrategy;
import squedgy.utilities.interfaces.DynamicClassFields;
import squedgy.utilities.error.SeriousException;
import java.lang.reflect.Constructor;

// Author David
public abstract class GenericFactory {
	
	/**
	 * This is a method to get an object that can dynamically update it's fields this if you just want an object look at getObject
	 * within this class.
	 * @param <T> the type that you wish to receive back that implement/extends DynamicClassFields
	 * @param <K> the type of the Keys that the ConfigInformationStrategy used uses
	 * @param config the config file that contains the path to the the object you wish to receive Ex. the property would be "squedgy.utilities.ExampleClass" and the key would be "example"
	 * @param propertyKey an object of type K representing the Key that links to the class path within the project
	 * @return and object of type T that has the ability to update it's fields dynamically while the program is running
	 */
	public static <T extends DynamicClassFields, K> T getDynamicObject(ConfigInformationStrategy<K> config, K propertyKey){
		try{
			Object ret = ((T) getObject((config.getProperty(propertyKey))));
			((T) ret).updateDynamicFields(config);
			return (T) ret;
		}catch(Exception ex){
			throw new SeriousException("There was an error loading the requested type of file in the config file key: " + propertyKey);
		}
	}
	
	/**
	 * This method returns an object with the fully qualified name className then attempts to cast that to
	 * Type <T> returning the generated class,casted to T, there needs to be a constructor with 0
	 * arguments for the generated class as well.
	 * @param <T> the type that you want the generated object to be
	 * @param className the fully qualified name of the object being generated
	 * @return class <className> of type <T>
	 * @throws Exception if there is an issue with the fully qualified name, or casting
	 */
	public static <T> T getObject(String className)throws Exception{
		Class clazz = Class.forName(className);
		Constructor cons = clazz.getDeclaredConstructor(new Class[0]);
		if(!cons.isAccessible())
			cons.setAccessible(true);
		T ret = (T)cons.newInstance(new Object[0]);
		return ret;
	}
}
