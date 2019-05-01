
package com.squedgy.utilities.writer;

// Author Squedgy

import com.squedgy.utilities.interfaces.Writer;
import com.squedgy.utilities.interfaces.InputStreamFormatter;
import org.slf4j.Logger;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.*;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * A Simple file writer that uses any sent formatter to encode and write to a specified file
 * @author Squedgy
 */
public final class FileWriter <WriteType> implements Writer<WriteType, Path>{
	private boolean append;
	private String fileLocation;
	private InputStreamFormatter<WriteType> formatter;
	private static final Logger log = getLogger(FileWriter.class);
	
	public FileWriter(String fileLocation, InputStreamFormatter<WriteType> formatter, boolean append){
		setAppending(append);
		setFileLocation(fileLocation);
		this.formatter = formatter;
	}
	
	@Override
	public Path write(WriteType writable) throws IOException {
		InputStream stream = formatter.encode(writable);

		Path p = Paths.get(fileLocation).toAbsolutePath();
		if(!p.toFile().exists()) {
			if(!p.getParent().toFile().exists())
				Files.createDirectories(p.getParent());
			Files.createFile(p);
		}
		FileOutputStream output = new FileOutputStream(p.toFile());


		output.getChannel().transferFrom(Channels.newChannel(stream) , 0, stream.available());
		return p;
	}

	public String getFileLocation() { return fileLocation; }

	public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }

	public boolean isAppending() { return append; }

	public void setAppending(boolean append) { this.append = append; }

	public InputStreamFormatter<WriteType> getFormatter() { return formatter; }

	public void setFormatter(InputStreamFormatter<WriteType> formatter) { this.formatter = formatter; }
}
