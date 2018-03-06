
package com.davidafaris.utilities.writer;

// Author David

import com.davidafaris.utilities.interfaces.Formatter;
import com.davidafaris.utilities.interfaces.Writer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
		try(PrintWriter out = new PrintWriter(new java.io.FileWriter(file))) {
			for(String s : formatStyle.encode(strings))
				out.println(s);
		}catch(IOException e){
			throw new Exception("Error openening file",e);
		}
	}

	public Formatter getFormatStyle() {
		return formatStyle;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public void setFormatStyle(Formatter formatStyle) {
		if(formatStyle == null)
			throw new IllegalArgumentException("Format Style cannot be null!");
		this.formatStyle = formatStyle;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

}
