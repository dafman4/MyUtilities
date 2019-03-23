package com.squedgy.utilities.formatter;

import com.squedgy.utilities.interfaces.Formatter;

import java.io.InputStream;

public abstract class CsvFormatter<OutputType> implements Formatter<InputStream,OutputType> {

	protected int skipRows = 0;
	protected int ignoreLast = 0;
	protected int maxSelection = 0;
	protected char delimiter = ',';
	protected char escapeChar = '\\';
	protected Character quoteChar = null;
	protected String lineSeparator = System.lineSeparator();

	/**
	 * Sets the character rows are delimited by defaults to null (no delimiter)
	 * @param quote - null represents an unquoted file
	 * @return this
	 */
	public CsvFormatter<OutputType> quotedBy(Character quote) {
		this.quoteChar = quote;
		return this;
	}

	/**
	 * Sets the delimiter character defaults to ','
	 * @param delimiter - the character the stream's values are delimited by
	 * @return this
	 */
	public CsvFormatter<OutputType> delimitedBy(char delimiter) {
		this.delimiter = delimiter;
		return this;
	}

	/**
	 * Sets the line seperator string default is {@link System#lineSeparator()} )}
	 * @param separator - the new line separator string
	 * @throws IllegalArgumentException if separator is null
	 * @return this
	 */
	public CsvFormatter<OutputType> lineSeparator(String separator) {
		if(separator == null) throw new IllegalArgumentException("Seperator cannot be null!");
		this.lineSeparator = separator;
		return this;
	}

	/**
	 * How many rows you want to skip.
	 * NOTE: This doesn't include the header row if present as that's automatically skipped
	 * @param rowsToSkip - the rows to skip at the beginning of the stream.
	 * @return this
	 */
	public CsvFormatter<OutputType> skipRows(int rowsToSkip) {
		this.skipRows = rowsToSkip;
		return this;
	}

	/**
	 * Escape character
	 * @param escapeChar - the new escape character
	 * @return this
	 */
	public CsvFormatter<OutputType> escapedBy(Character escapeChar) {
		this.escapeChar = escapeChar;
		return this;
	}

	/**
	 * Skip the last give amount of rows
	 * @param rowsToSkip - the rows to skip at the end of the file
	 * @return this
	 */
	public CsvFormatter<OutputType> ignoreLast(int rowsToSkip) {
		this.ignoreLast = rowsToSkip;
		return this;
	}

	/**
	 * Max amount of rows selected after skipping at the beginning and end of the stream
	 * @param maxSelection - the max selectable rows
	 * @return this
	 */
	public CsvFormatter<OutputType> selectUpTo(int maxSelection) {
		this.maxSelection = maxSelection;
		return this;
	}

}
