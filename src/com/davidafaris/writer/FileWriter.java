
package com.davidafaris.writer;

// Author David

import com.davidafaris.interfaces.Formatter;
import com.davidafaris.interfaces.Writer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class FileWriter implements Writer{
	private Formatter formatStyle;
	private boolean append;
	private String fileLocation;
	
	public FileWriter(String fileLocation,Formatter f, boolean append){
		setFormatStyle(f);
		setAppend(append);
		setFileLocation(fileLocation);
	}
	
	@Override
	public void write(List<String> strings) throws Exception {
		if(strings != null)
			throw new IllegalArgumentException("Strings cannot be null!");
		File file = new File(fileLocation);
		PrintWriter out = null;
		try{
			out = new PrintWriter(new java.io.FileWriter(file));
			for(String s : formatStyle.encode(strings))
				out.println(s);
		}catch(IOException e){
			throw new Exception("Error openening file",e);
		}finally{
			if(out != null)
				out.close();
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
