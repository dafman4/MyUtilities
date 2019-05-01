package com.squedgy.utilities.formatter;

import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

import static com.squedgy.utilities.EscapedChars.escapeString;
import static com.squedgy.utilities.EscapedChars.getEscapedChar;
import static org.slf4j.LoggerFactory.getLogger;

public class NonQuotedCsvFormat extends CsvInputStreamFormat<List<List<String>>> {

	private static final Logger log = getLogger(NonQuotedCsvFormat.class);

	@Override
	public InputStream encode(List<List<String>> toEncode) throws IOException {
		StringBuilder builder = new StringBuilder();
		Function<String,String> modifyValue = escapeChar == null ? Function.identity() : s -> escapeString(s, delimiter, null, escapeChar);

		toEncode.forEach((columns) -> {
			columns.forEach((value) -> {
				builder.append(modifyValue.apply(value)).append(delimiter);
			});
			builder.setLength(builder.length() - 1);
			builder.append(lineSeperator);

		});
		log.debug(builder.toString());
		return new ByteArrayInputStream(builder.toString().getBytes());
	}

	@Override
	public List<List<String>> decode(InputStream toDecode) throws IOException {
		StringBuilder value = new StringBuilder(), whitespace = new StringBuilder();
		int currentValue = safeRead(toDecode);
		List<List<String>> rows = new LinkedList<>();
		List<String> columns = new LinkedList<>();

		while(currentValue != -1) {

			if(Character.isWhitespace(currentValue)) {
				whitespace.append(Character.toChars( currentValue ));
				if(atNewLine(whitespace)) {
					columns.add(value.toString());
					rows.add(new ArrayList<>(columns));
					columns.clear();
					value.setLength(0);
				}
			} else if (isEscapeChar(currentValue)) {
				value.append(getEscapedChar(currentValue, delimiter, null, escapeChar));
			} else if (isDelimiter(currentValue)) {
				columns.add(value.toString());
				value.setLength(0);
			} else {
				value.append(Character.toChars(currentValue));
			}

			currentValue = safeRead(toDecode);
		}

		return rows;
	}

}
