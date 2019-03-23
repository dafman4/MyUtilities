
package com.squedgy.utilities.reader;

import com.squedgy.utilities.interfaces.FileFormatter;
import com.squedgy.utilities.interfaces.Reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.StandardOpenOption.READ;

/**
 * A reader that reads a file at the input file location and decodes according to the input formatter style
 *
 * @author Squedgy
 */
public final class FileReader<WriteType> implements Reader<WriteType, String> {

    private FileFormatter<WriteType> formatter;

    public FileReader(FileFormatter<WriteType> formatStyle) {
        this.formatter = formatStyle;
    }

    @Override
    public WriteType read(String fileLocation) throws IOException {
        File f = new File(fileLocation);
        if (f.exists() && f.isFile()) {
            return formatter.decode(newInputStream(f.toPath(), READ));
        }
        return null;
    }

    public FileFormatter<WriteType> getFormatter() { return formatter; }

    public void setFormatter(FileFormatter<WriteType> formatter) { this.formatter = formatter; }
}
