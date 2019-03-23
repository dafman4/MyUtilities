
package com.squedgy.utilities.writer;

// Author Squedgy

import com.squedgy.utilities.abstracts.Writer;
import com.squedgy.utilities.interfaces.FileFormatter;

import java.io.IOException;

/**
 * A Simple file writer that uses any sent formatter to encode and write to a specified file
 * @author Squedgy
 */
public final class FileWriter <WriteType> implements Writer<WriteType, Void>{
	private boolean append;
	private String fileLocation;
	private FileFormatter<WriteType> formatter;
	
	public FileWriter(String fileLocation, FileFormatter<WriteType> formatter, boolean append){
		setAppending(append);
		setFileLocation(fileLocation);
	}
	
	@Override
	public Void write(WriteType strings) throws IOException {
		formatter.setWorkingFile(fileLocation);
		formatter.encode(strings);
		return null;
	}

	public String getFileLocation() { return fileLocation; }

	public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }

	public boolean isAppending() { return append; }

	public void setAppending(boolean append) { this.append = append; }

	public FileFormatter<WriteType> getFormatter() { return formatter; }

	public void setFormatter(FileFormatter<WriteType> formatter) { this.formatter = formatter; }
}
