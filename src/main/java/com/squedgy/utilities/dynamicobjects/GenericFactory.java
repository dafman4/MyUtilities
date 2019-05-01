
package com.squedgy.utilities.dynamicobjects;

import com.squedgy.utilities.interfaces.ConfigInformationStrategy;
import com.squedgy.utilities.interfaces.DynamicClassFields;
import com.squedgy.utilities.error.SeriousException;
import java.lang.reflect.Constructor;

// Author Squedgy
@SuppressWarnings("unchecked")
public abstract class GenericFactory {
	
	/**
	 * This is a method to get an object that can dynamically update it's fields this if you just want an object look at getObject
	 * within this class.
	 * @param <DynamicClass> the type that you wish to receive back that implement/extends DynamicClassFields
	 * @param <PropertyKey> the type of the Keys that the ConfigInformationStrategy used uses
	 * @param config the config file that contains the path to the the object you wish to receive Ex. the property value would be "com.squedgy.utilities.examples.ExampleClass" and the key would be "example"
	 * @param propertyKey an object of type PropertyKey representing the Key that links to the class path within the project
	 * @return and object of type DynamicClass that has the ability to update it's fields dynamically while the program is running
	 */
	public static <DynamicClass extends DynamicClassFields, PropertyKey> DynamicClass getDynamicObject(ConfigInformationStrategy<PropertyKey> config, PropertyKey propertyKey){
		try{
			DynamicClass ret = getObject((config.getProperty(propertyKey)));
			ret.updateDynamicFields(config);
			return  ret;
		}catch(Exception ex){
			throw new SeriousException("There was an error loading the requested type of file in the config file key: " + propertyKey);
		}
	}
	
	/**
	 * This method returns an object with the fully qualified name className then attempts to cast that to
	 * Type <DynamicClass> returning the generated class,casted to DynamicClass, there needs to be a constructor with 0
	 * arguments for the generated class as well.
	 * @param <ObjectType> the type that you want the generated object to be
	 * @param className the fully qualified name of the object being generated
	 * @return class <className> of type <DynamicClass>
	 * @throws Exception if there is an issue with the fully qualified name, or casting to &ls;ObjectType>
	 */
	public static <ObjectType> ObjectType getObject(String className)throws Exception{
		Class<ObjectType> clazz = (Class<ObjectType>) Class.forName(className);
		if(clazz == null) throw new ClassNotFoundException(String.format("Class \"%s\" was not found", className));
		Constructor<ObjectType> cons = clazz.getDeclaredConstructor();
		boolean reset = false;
		if(!cons.isAccessible()){
			cons.setAccessible(true);
			reset = true;
		}
		ObjectType ret = cons.newInstance();
		if(reset) cons.setAccessible(false);
		return ret;
	}
}
