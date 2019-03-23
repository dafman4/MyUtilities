
package com.squedgy.utilities.writer;

// Author Squedgy

import com.squedgy.utilities.interfaces.Writer;
import com.squedgy.utilities.interfaces.FileFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.LinkedList;

import static java.nio.file.StandardOpenOption.*;

/**
 * A Simple file writer that uses any sent formatter to encode and write to a specified file
 * @author Squedgy
 */
public final class FileWriter <WriteType> implements Writer<WriteType, Path>{
	private boolean append;
	private String fileLocation;
	private FileFormatter<WriteType> formatter;
	
	public FileWriter(String fileLocation, FileFormatter<WriteType> formatter, boolean append){
		setAppending(append);
		setFileLocation(fileLocation);
		this.formatter = formatter;
	}
	
	@Override
	public Path write(WriteType writable) throws IOException {
		InputStream stream = formatter.encode(writable);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		Path p = Paths.get(fileLocation);
		if(!p.toFile().exists()) {
			if(!p.getParent().toFile().exists())
				Files.createDirectories(p.getParent());
			Files.createFile(p);
		}
		return Files.write(Paths.get(fileLocation), bytes, WRITE, append ? APPEND : TRUNCATE_EXISTING);
	}

	public String getFileLocation() { return fileLocation; }

	public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }

	public boolean isAppending() { return append; }

	public void setAppending(boolean append) { this.append = append; }

	public FileFormatter<WriteType> getFormatter() { return formatter; }

	public void setFormatter(FileFormatter<WriteType> formatter) { this.formatter = formatter; }
}
