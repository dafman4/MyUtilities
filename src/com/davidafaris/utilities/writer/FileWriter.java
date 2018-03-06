
package com.davidafaris.utilities.writer;

// Author David

import com.davidafaris.utilities.interfaces.Formatter;
import com.davidafaris.utilities.interfaces.Writer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
/**
 * A Simple file writer that uses any sent formatter to encode and write to a specified file
 * @author David
 */
public final class FileWriter implements Writer<String,String>{
	private Formatter<String,String,String> formatStyle;
	private boolean append;
	private String fileLocation;
	
	public FileWriter(String fileLocation,Formatter f, boolean append){
		setFormatStyle(f);
		setAppend(append);
		setFileLocation(fileLocation);
	}
	
	@Override
	public void write(Map<String,String> strings) throws Exception {
		if(strings == null)
			throw new IllegalArgumentException("Strings cannot be null!");
		File file = new File(fileLocation);
		try(PrintWriter fileWriter = new PrintWriter(new java.io.FileWriter(file))) {
			formatStyle.encode(strings).forEach((s) -> {
				fileWriter.println(s);
			});
		}catch(IOException e){
			throw new Exception("Error openening file",e);
		}
	}
	/**
	 * Returns the Formatter object
	 * @return an object representing the formatting for the selected file
	 */
	public Formatter getFormatStyle() {
		return formatStyle;
	}
	/**
	 * returns the file location as a string
	 * @return the file location
	 */
	public String getFileLocation() {
		return fileLocation;
	}
	/**
	 * sets the file location
	 * @param fileLocation the location of the file 
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	/**
	 * sets the format style of the file you've selected
	 * @param formatStyle and object representing how to encode/decode a file
	 */
	public void setFormatStyle(Formatter formatStyle) {
		if(formatStyle == null)
			throw new IllegalArgumentException("Format Style cannot be null!");
		this.formatStyle = formatStyle;
	}
	/**
	 * returns a boolean that tells whether or not you are currently appending to a file
	 * @return true = appending, false = not appending
	 */
	public boolean isAppend() {
		return append;
	}
	/**
	 * Sets whether you are appending to a file or not
	 * @param append true = append, false = don't append
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

}
