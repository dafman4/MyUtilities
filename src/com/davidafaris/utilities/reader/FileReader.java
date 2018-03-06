
package com.davidafaris.utilities.reader;
import com.davidafaris.utilities.interfaces.Formatter;
import com.davidafaris.utilities.interfaces.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * A reader that reads a file at the input file location according to the input formatter style
 * @author David
 */
public final class FileReader implements Reader<String,String>{
	private Formatter<String,String,String> formatStyle;
	private String fileLocation;
	
	public FileReader(Formatter<String,String,String> formatStyle, String fileLocation){
		setFormatStyle(formatStyle);
		setFileLocation(fileLocation);
	}

	@Override
	public Map<String,String> read() throws Exception{
		File file = new File(fileLocation);
		BufferedReader in = null;
		try{
			in = new BufferedReader(new java.io.FileReader(file));
			List<String> lines = new ArrayList<>();
			String line = in.readLine();
			while(line != null){
				lines.add(line);
				line = in.readLine();
			}
			return formatStyle.decode(lines);
		}catch(IOException e){
			throw new Exception("Error while opening or reading file!",e);
		}finally{
			if(in != null){
				try{
					in.close();
				}catch(IOException e){
					throw new Exception("Error closing file", e);
				}
			}
		}
	}
	
	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		if(fileLocation == null || fileLocation.isEmpty())
			throw new IllegalArgumentException("File Location cannot be null or empty!");
		this.fileLocation = fileLocation;
	}

	public void setFormatStyle(Formatter f){
		if(f == null)
			throw new IllegalArgumentException("F cannot be null!");
		formatStyle = f;
	}
	
	public Formatter getFormatStyle(Formatter f){
		return formatStyle;
	}
}
