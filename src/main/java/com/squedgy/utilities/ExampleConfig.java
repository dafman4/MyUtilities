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

package com.squedgy.utilities;

import com.squedgy.utilities.interfaces.FileFormatter;
import com.squedgy.utilities.reader.FileReader;
import com.squedgy.utilities.error.SeriousException;
import com.squedgy.utilities.formatter.ConfigFileFormat;
import com.squedgy.utilities.interfaces.ConfigInformationStrategy;
import com.squedgy.utilities.interfaces.Formatter;

import java.util.Map;

public class ExampleConfig implements ConfigInformationStrategy<String> {
	/**
	 * Keeps the config class down to 1 instance throughout any program.
	 */
	private static ExampleConfig instance;
	/**
	 * Holds the properties contained within the properties class (the key is the same type as is passed into the interface)
	 */
	private Map<String, String> properties;
	/**
	 * This simply contains the formatting style for whatever file you decide to use with the file reader
	 */
	private final FileFormatter<Map<String,String>> FORMAT;
	/**
	 * Marks file location for the file reader used later
	 */
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
		if(key != null && value != null) properties.put(key, value);
	}
	
	private void updateProperties() throws SeriousException{
		FileReader<Map<String,String>> fr = new FileReader<>(FILE_LOCATION, FORMAT);
		try{
			properties = fr.read(null);
		}catch(Exception e){
			throw new SeriousException("There was an error updating properties!\nPossibly with the file. Has it been tampered with, or was it edited while this was running?");
		}
	}
}
