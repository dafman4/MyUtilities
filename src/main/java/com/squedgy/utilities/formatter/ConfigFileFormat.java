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
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

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

	private final static Logger log = getLogger(ConfigFileFormat.class);

	@Override
	public InputStream encode(Map<String,String> lines) {
		String output = lines.entrySet().stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(joining("\n"));

		return new ByteArrayInputStream(output.getBytes(Charset.forName("UTF-8")));
	}

	@Override
	public Map<String, String> decode(InputStream stream) {
		List<String> lines = new LinkedList<>();

		StringBuilder builder = new StringBuilder();
		int c;
		while(true) {
			// Get the next char/byte/etc.
			try {
				c = stream.read();
			} catch(IOException e) {
				log.error("This could potentially by an acceptable error.", e);
				break;
			}
			// If there wasn't a next one we're done reading
			if(c == -1) {
				lines.add(builder.toString());
				break;
			}

			char ch = (char) c;
			// If new line
			if(ch == '\n') {
				lines.add(builder.toString());
				builder.setLength(0);
			} else {
				builder.append((char)c);
			}
		}

		return decode(lines);
	}

	private Map<String,String> decode(List<String> lines) {
		Map<String,String> ret = new HashMap<>();
		log.debug("lines: " + lines);
		for(String s: lines){
			if(s.startsWith("#")) continue;
			if(s.startsWith("\\")) s = s.substring(1);
			String[] parts = s.split(" *=+ *");
			ret.put(parts[0], parts[1]);
		}
		return ret;
	}

	@Override
	public String toString() { return "ConfigFileFormat instance"; }

}
