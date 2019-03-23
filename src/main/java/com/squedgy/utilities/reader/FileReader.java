
package com.squedgy.utilities.reader;

import com.squedgy.utilities.interfaces.FileFormatter;
import com.squedgy.utilities.abstracts.Reader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A reader that reads a file at the input file location and decodes according to the input formatter style
 *
 * @author Squedgy
 */
public final class FileReader<WriteType> implements Reader<WriteType, Void> {
    private String fileLocation;
    private FileFormatter<WriteType> formatter;

    public FileReader(String fileLocation, FileFormatter<WriteType> formatStyle) {
        this.formatter = formatStyle;
        setFileLocation(fileLocation);
    }

    @Override
    public WriteType read(Void ignored) {
        File f = new File(fileLocation);
        if (f.exists() && f.isFile()) {
            return formatter.decode(null);
        }
        return null;
    }

    public String getFileLocation() { return fileLocation; }

    public void setFileLocation(String fileLocation) {
        if (fileLocation == null || fileLocation.isEmpty())
            throw new IllegalArgumentException("File Location cannot be null or empty!");
        this.fileLocation = fileLocation;
    }

    public FileFormatter<WriteType> getFormatter() { return formatter; }

    public void setFormatter(FileFormatter<WriteType> formatter) { this.formatter = formatter; }
}
