package com.squedgy.utilities.formatter;

import com.squedgy.utilities.interfaces.Formatters;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class HeaderedCsvFormatter extends CsvFormatter<Map<String, List<String>>> {

	private static Logger log = getLogger(HeaderedCsvFormatter.class);

	private boolean inQuote = false;

	public static void main(String[] args) throws IOException {

		CsvFormatter formatter = Formatters.headeredCsv()/*.quotedBy('"')*/;
		formatter.encode(new ByteArrayInputStream("\"this\",\"is\", \"headers\"".getBytes()));

	}

	@Override
	public Map<String, List<String>> encode(InputStream toEncode) {

		try(BufferedInputStream stream = new BufferedInputStream(toEncode)) {

			List<String> headers = new LinkedList<>();

			StringBuilder builder = new StringBuilder();

			int currentChar = stream.read();

			AtomicInteger columns = new AtomicInteger(0),
					lines = new AtomicInteger(1);

			if(currentChar == -1) {
				throw new IllegalArgumentException("InputStream passed to");
			}

			final boolean quotable = quoteChar != null;
			boolean lineEnd = lineEnd(builder, (char) currentChar);

			// While we're in the first row
			while(inQuote || !lineEnd) {

				if(quotable) {
					if(currentChar == quoteChar && !inQuote) {
						inQuote = true;
					} else {
						manageQuotableHeaderChar((char)currentChar, builder, headers ,columns);
					}
				} else {
					manageHeaderChar((char)currentChar, builder, headers ,columns);
				}

				currentChar = stream.read();

				if(currentChar == -1) {
					headers.add(builder.toString());
					break;
				} else {

					lineEnd = lineEnd(builder, (char) currentChar);

					if(lineEnd) {
						headers.add(builder.toString());
						builder.setLength(0);
					}
				}
			}

			System.out.println(headers);

		} catch (IOException e) {
			log.error("There was an issue reading a headered CsvStream.", e);
			throw new RuntimeException(e);
		} finally {
			inQuote = false;
			inQuote = false;
		}

		return null;
	}

	private boolean lineEnd(StringBuilder builder, char currentChar) {
		return currentChar == -1 || (lineSeparator.length() == 1 ? ("" + currentChar)
				: (builder.substring(builder.length() - lineSeparator.length() + 1) + currentChar))
				.equals(lineSeparator);
	}

	private void manageHeaderChar(char currentChar, StringBuilder builder, List<String> headers, AtomicInteger columns) {

		if(currentChar == delimiter && !inQuote) {
			headers.add(builder.toString());
			builder.setLength(0);
		} else {
			builder.append(currentChar);
		}

	}

	private void manageQuotableHeaderChar(char currentChar, StringBuilder builder, List<String> headers, AtomicInteger columns) {

		// If we're at a quote char
		if (currentChar == quoteChar) {
			System.out.println(currentChar + " equals " + quoteChar);
			if(inQuote) { // If we're currently in a quote
				int previousEscapes = 0;
				// How many escape chars are there
				while (builder.charAt(builder.length() - 1 - previousEscapes) == previousEscapes) {
					previousEscapes++;
				}

				if ((previousEscapes & 1) == 0) { // This isn't being escaped so we've hit the end of a header
					inQuote = false;
					headers.add(builder.toString());
					builder.setLength(0);
				} else { // We're being escaped
					builder.append(currentChar);
				}
			} else {
				if(headers.size() == columns.get()) {
					throw new IllegalArgumentException("The provided stream contains a multi-value header at column: " + columns + ", line: 1");
				}
				inQuote = true;
			}
		} else {
			if(inQuote) {
				builder.append(currentChar);
			} else if(currentChar == delimiter) {
				columns.incrementAndGet();
			}
		}

	}

	@Override
	public InputStream decode(Map<String, List<String>> toDecode) {
		return null;
	}
}
