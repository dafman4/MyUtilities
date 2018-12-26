
package com.squedgy.utilities.reader;
import com.squedgy.utilities.interfaces.FileFormatter;
import com.squedgy.utilities.abstracts.Reader;

import java.io.File;

/**
 * A reader that reads a file at the input file location and decodes according to the input formatter style
 * @author Squedgy
 */
public final class FileReader <WriteType> extends Reader<WriteType,Void>{
	private String fileLocation;
	
	public FileReader(String fileLocation, FileFormatter<WriteType> formatStyle){
		super(formatStyle);
		setFileLocation(fileLocation);
	}

	@Override
	public WriteType read(){
		((FileFormatter) formatter).setWorkingFile(fileLocation);
		return formatter.decode(null);
	}
	/**
	 * returns the location of the file as a string
	 * @return string representing the file location
	 */
	public String getFileLocation() { return fileLocation; }
	/**
	 * sets the location of the file
	 * @param fileLocation the new location of a file to be read
	 */
	public void setFileLocation(String fileLocation) {
		if(fileLocation == null || fileLocation.isEmpty())
			throw new IllegalArgumentException("File Location cannot be null or empty!");
		this.fileLocation = fileLocation;
	}

}
