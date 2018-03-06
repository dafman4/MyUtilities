/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davidafaris.utilites;

import com.davidafaris.utilities.error.SeriousException;
import com.davidafaris.utilities.formatter.ConfigFileFormat;
import com.davidafaris.utilities.interfaces.ConfigInformationStrategy;
import com.davidafaris.utilities.interfaces.Formatter;
import com.davidafaris.utilities.reader.FileReader;
import java.util.Map;

public class ExampleConfig implements ConfigInformationStrategy<String> {
	private static ExampleConfig instance;
	private Map<String, String> properties;
	private final Formatter FORMAT;
	private final String FILE_LOCATION;
	
	private ExampleConfig(){
		FORMAT = new ConfigFileFormat();
		FILE_LOCATION = "src/app.properties";
		updateProperties();
	}
	
	public static ExampleConfig getInstance(){
		if(instance == null){
			instance = new ExampleConfig();
			
		}
		return instance;
	}

	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Map<String, String> newProps) {
		if(newProps != null)
			properties = newProps;
	}

	@Override
	public String getProperty(String key) {
		return properties.get(key);
	}

	@Override
	public void setProperty(String key, String value) {
		if(key != null && value != null)
			properties.put(key, value);
	}
	
	private void updateProperties() throws SeriousException{
		FileReader fr = new FileReader(FORMAT, FILE_LOCATION);
		try{
			properties = fr.read();
		}catch(Exception e){
			throw new SeriousException("There was an error updating properties!");
		}
	}
}
