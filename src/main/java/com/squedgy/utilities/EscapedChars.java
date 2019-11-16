package com.squedgy.utilities;

import java.util.Map;
import java.util.Map.Entry;

import static com.squedgy.utilities.maps.MapUtils.of;
import static java.util.stream.Collectors.toMap;

public abstract class EscapedChars {

	private static final Map<Character, Character> escapeReference = of(
		'\t', 't',
		'\n', 'n',
		'\b', 'b',
		'\r', 'r',
		'\f', 'f'
	);

	private static final Map<Character, Character> reverseReference = escapeReference
			.entrySet()
			.stream()
			.collect(toMap(Entry::getValue, Entry::getKey));

	public static char getEscapedChar(int value, char delimiter, Character quoteChar, char escapeChar) {
		char c = (char) value;
		if(c == delimiter) return delimiter;
		if(c == quoteChar) return quoteChar;
		if(c == escapeChar) return escapeChar;
		Character ref = reverseReference.get(c);
		if(ref != null) return ref;
		throw new IllegalArgumentException("Char '" + c + "' is not an escapable char!");
	}

	public static String escapeString(String toEscape, char delimiter, Character quoteChar, char escapeChar) {
		StringBuilder builder = new StringBuilder();
		Character escaped;

		for(byte c : toEscape.getBytes()) {
			if(c == delimiter) builder.append(escapeChar).append(delimiter);
			else if (quoteChar != null && c == quoteChar) builder.append(escapeChar).append(quoteChar);
			else if (c == escapeChar) builder.append(escapeChar).append(escapeChar);
			else if ((escaped = escapeReference.get(c)) != null) {
				builder.append(escapeChar).append(escaped);
			} else {
				builder.append((char)c);
			}

		}

		return builder.toString();
	}

}
