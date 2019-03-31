
package com.squedgy.utilities.reader;

import com.squedgy.utilities.interfaces.InputStreamFormatter;
import com.squedgy.utilities.interfaces.Reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.StandardOpenOption.READ;

/**
 * A reader that reads a file at the input file location and decodes according to the input formatter style
 *
 * @author Squedgy
 */
public final class FileReader<WriteType> implements Reader<WriteType, Path> {

    private InputStreamFormatter<WriteType> formatter;

    public FileReader(InputStreamFormatter<WriteType> formatStyle) {
        this.formatter = formatStyle;
    }

    public WriteType read(String file) throws IOException {
        return read(Paths.get(file));
    }

    public WriteType read(URL file) throws IOException, URISyntaxException {
        return read(Paths.get(file.toURI()));
    }

    @Override
    public WriteType read(Path pathToFile) throws IOException {
        return formatter.decode(newInputStream(pathToFile, READ));
    }

    public InputStreamFormatter<WriteType> getFormatter() { return formatter; }

    public void setFormatter(InputStreamFormatter<WriteType> formatter) { this.formatter = formatter; }
}
