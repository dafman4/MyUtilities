package com.squedgy.utilities.formatter;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class HeaderedCsvFormatter extends CsvFormatter<Map<String, List<String>>> {

	private static Logger log = getLogger(HeaderedCsvFormatter.class);

	private boolean inQuote = false;

	@Override
	public Map<String, List<String>> encode(InputStream stream) {
		inQuote = false;
		try {

			List<String> headers = new LinkedList<>();

			StringBuilder builder = new StringBuilder();

			int currentChar = stream.read();

			AtomicInteger columns = new AtomicInteger(1);

			final boolean quotable = quoteChar != null;
			boolean lineEnd = lineEnd(builder, currentChar);

			// While we're in the first row
			while(inQuote || !lineEnd) {

				if(quotable) {
					manageQuotableChar((char)currentChar, builder, headers ,columns, stream);
				} else {
					manageChar((char)currentChar, builder, headers ,columns, stream);
				}

				currentChar = stream.read();

				if(currentChar == -1) {
					headers.add(builder.toString());
					break;
				} else {
					lineEnd = lineEnd(builder, currentChar);

					if(lineEnd && !quotable) {
						headers.add(builder.toString());
						builder.setLength(0);
					}
				}
			}

			Map<String, List<String>> values = new HashMap<>();
			for(String header : headers) values.put(header, new LinkedList<>());
			String lastHeader = headers.get(headers.size()-1);
			AtomicInteger lines = new AtomicInteger(1);
			int selectableRows = maxSelection + skipRows;

			while( maxSelection <= 0 || lines.get() < selectableRows) {
				if(lines.get() <= skipRows) {

				} else {

				}
			}

			log.debug(headers.toString());

		} catch (IOException e) {
			log.error("There was an issue reading a headered CsvStream.", e);
			throw new RuntimeException(e);
		}

		return null;
	}

	private boolean lineEnd(StringBuilder builder, int currentChar) {
		return currentChar == -1 || (lineSeparator.length() == 1 ? ("" + ((char)currentChar))
				: (builder.substring(builder.length() - lineSeparator.length() + 1) + ((char)currentChar)))
				.equals(lineSeparator);
	}

	private void manageChar(char currentChar, StringBuilder builder, List<String> headers, AtomicInteger columns, InputStream stream) throws IOException {

		if (currentChar == escapeChar) {
			builder.append((char)stream.read());
		} else if(currentChar == delimiter && !inQuote) {
			columns.incrementAndGet();
			headers.add(builder.toString());
			builder.setLength(0);
		} else {
			builder.append(currentChar);
		}

	}

	private void manageQuotableChar(char currentChar, StringBuilder builder, List<String> headers, AtomicInteger columns, InputStream stream) throws IOException {

		if(currentChar == escapeChar) {
			// If we care about it
			if(inQuote){
				builder.append((char) stream.read());
			}

		} else if (currentChar == quoteChar) { // If we're at a quote char
			if(inQuote) { // If we're currently in a quote
				inQuote = false;
				headers.add(builder.toString());
				builder.setLength(0);
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
			} else {

			}
		}

	}

	private int getPreviousEscapes(StringBuilder builder) {
		int previousEscape = 0;

		while(builder.charAt(builder.length() - 1 - previousEscape) == escapeChar) previousEscape++;

		return previousEscape;
	}

	@Override
	public InputStream decode(Map<String, List<String>> toDecode) {
		return null;
	}
}
