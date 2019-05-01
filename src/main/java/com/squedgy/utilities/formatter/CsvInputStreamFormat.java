package com.squedgy.utilities.formatter;

import com.squedgy.utilities.interfaces.InputStreamFormatter;

import java.io.IOException;
import java.io.InputStream;

public abstract class CsvInputStreamFormat <ReadAs> implements InputStreamFormatter<ReadAs> {

	protected Character escapeChar = '\\';
	protected char delimiter = ',';
	protected String lineSeperator = System.lineSeparator();

	protected static int safeRead(InputStream i) {
		try{
			return i.read();
		} catch (IOException e) {
			return -1;
		}
	}

	protected boolean atNewLine(StringBuilder builder) {
		return builder.length() >= lineSeperator.length() &&
				builder.substring(builder.length() - lineSeperator.length()).equals(lineSeperator);
	}

	protected boolean isEscapeChar(int value) {
		return escapeChar != null && value == escapeChar;
	}

	protected boolean isDelimiter(int value) {
		return delimiter == value;
	}

}
