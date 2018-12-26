
package com.squedgy.utilities.writer;

// Author Squedgy

import com.squedgy.utilities.abstracts.Writer;
import com.squedgy.utilities.interfaces.FileFormatter;

/**
 * A Simple file writer that uses any sent formatter to encode and write to a specified file
 * @author Squedgy
 */
public final class FileWriter <WriteType> extends Writer<WriteType, Void>{
	private boolean append;
	private String fileLocation;
	
	public FileWriter(String fileLocation, FileFormatter<WriteType> f, boolean append){
		super(f);
		setAppending(append);
		setFileLocation(fileLocation);
	}
	
	@Override
	public void write(WriteType strings) {
		((FileFormatter)formatter).setWorkingFile(fileLocation);
		formatter.encode(strings);
	}
	/**
	 * returns the file location as a string
	 * @return the file location
	 */
	public String getFileLocation() { return fileLocation; }
	/**
	 * sets the file location
	 * @param fileLocation the location of the file 
	 */
	public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }
	/**
	 * returns a boolean that tells whether or not you are currently appending to a file
	 * @return true = appending, false = not appending
	 */
	public boolean isAppending() { return append; }
	/**
	 * Sets whether you are appending to a file or not
	 * @param append true = append, false = don't append
	 */
	public void setAppending(boolean append) { this.append = append; }

}
