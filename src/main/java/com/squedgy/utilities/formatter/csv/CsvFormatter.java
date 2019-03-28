package com.squedgy.utilities.formatter.csv;

import com.squedgy.utilities.interfaces.Formatter;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class CsvFormatter<OutputType> implements Formatter<InputStream,OutputType> {

	private static final Logger log = getLogger(CsvFormatter.class);

	protected int skipRows = 0;
	protected int ignoreLast = 0;
	protected int maxSelection = 0;
	protected char delimiter = ',';
	protected char escapeChar = '\\';
	protected Character quoteChar = null;
	protected String lineSeparator = System.lineSeparator();
	protected boolean inQuote;

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

	protected boolean lineEnd(StringBuilder builder, int currentChar) {
		boolean ret;
		if(lineSeparator.length() == 1) {
			ret = lineSeparator.charAt(0) == (char) currentChar;
		} else {
			String comparison = builder.substring(builder.length() - (lineSeparator.length()-1)) + ((char) currentChar);
			ret = comparison.equals(lineSeparator);
		}
		return ret;
	}

	protected int readChar(InputStream stream) {
		try { return stream.read(); }
		catch (IOException e) { throw new RuntimeException(e); }
	}

	public interface EscapableCharacters {
		int T = '\t';
		int B = '\b';
		int N = '\n';
		int R = '\r';
		int F = '\f';

		public static int getEscapeChar(int c, InputStream stream){
			if(c == 't') return T;
			if(c == 'b') return B;
			if(c == 'n') return N;
			if(c == 'r') return R;
			if(c == 'f') return F;
			if(c == 'u') {
				char charVal = 0;
				int currentChar;
				for(int i = 0; i < 4; i++) {
					try {
						currentChar = stream.read();
					} catch (IOException e) {
						log.error("An error occurred while trying to parse an escaped unicode: ", e);
						throw new RuntimeException(e);
					}
					if((currentChar >= 'a' && currentChar <= 'f') || (currentChar >= 'A' && currentChar <= 'F') || (currentChar >= '0' && currentChar <= '9')){
						charVal += Integer.parseInt("" + ((char)currentChar), 16);
					}
				}
				return charVal;
			}
			if(c == '\'' || c == '"' || c == '\\') return c;

			throw new IllegalArgumentException(String.format("The escaped char '%s' is not an escapable character!", c));
		}

		public static String escapeString(String escape, char escapeChar) {

			return escape.replace("" + ((char)T), escapeChar + "t")
					.replace("" + ((char)B), escapeChar + "b")
					.replace("" + ((char)N), escapeChar + "n")
					.replace("" + ((char)R), escapeChar + "r")
					.replace("" + ((char)F), escapeChar + "f");

		}

		public static String escapeChar(char escapedBy, char toEscape) {

			if(toEscape == T) return escapedBy + "t";
			if(toEscape == B) return escapedBy + "b";
			if(toEscape == N) return escapedBy + "n";
			if(toEscape == R) return escapedBy + "r";
			if(toEscape == F) return escapedBy + "f";
			return "";
		}

	}


}
