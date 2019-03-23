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

package com.squedgy.utilities.formatter;

import com.squedgy.utilities.interfaces.FileFormatter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Config file Format is used for a simple config file format option
 * It reads and encodes files that have individual lines based on the
 * key value pair Key/ +=+ +/Value
 * Each line is it's own pair
 * this also provides the ability to comment areas in your properties file
 * using the # sign as the first character on the line
 *
 * @author David
 */
public class ConfigFileFormat implements FileFormatter<Map<String, String>> {

	private String fileLocation;

	private File getFile() throws IOException {
		File ret = new File(fileLocation);
		if(ret.exists()){
			return ret;
		}else{
			throw new FileNotFoundException("File " + ret.getAbsolutePath() + " doesn't exist!");
		}
	}

	@Override
	public Void encode(Map<String,String> lines) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()))){
			for (Map.Entry<String, String> e : lines.entrySet()) {
				writer.write(e.getKey() + "=" + e.getValue());
				writer.newLine();
			}
		}catch (FileNotFoundException e){
			try {
				if(shouldCreateFiles() && new File(fileLocation).createNewFile()){
					return encode(lines);
				}
			} catch (IOException e1) {
				throw new RuntimeException("File " + fileLocation + " was not found, and could not be created!");
			}
		}catch (IOException e) {

			throw new RuntimeException("There was and issue writing to " + fileLocation);
		}
		
		return null;
	}

	@Override
	public Map<String, String> decode(Void lines) {
		try(BufferedReader reader = new BufferedReader(new FileReader(getFile()))){
			return decode(reader.lines().collect(Collectors.toList()));
		}catch(IOException e){
			throw new RuntimeException("There was an issue reading the file located at " + fileLocation);
		}
	}

	public Map<String,String> decode(List<String> lines) {
		Map<String,String> ret = new HashMap<>();
		for(String s: lines){
			if(s.startsWith("#")) continue;
			if(s.startsWith("\\")) s = s.substring(1);
			String[] parts = s.split(" *=+ *[\"\\\\]");
			ret.put(parts[0], parts[1]);
		}
		return ret;
	}

	@Override
	public String toString() { return "ConfigFileFormat instance"; }

    @Override
	public boolean shouldCreateFiles() { return true; }

	@Override
	public void setWorkingFile(String fileLocation) { this.fileLocation = new File(fileLocation).getAbsolutePath(); }

	@Override
	public boolean isAppending() { return false; }
}
