package com.squedgy.utilities.formatter.csv;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.squedgy.utilities.formatter.csv.CsvFormatter.EscapableCharacters.escapeString;
import static com.squedgy.utilities.formatter.csv.CsvFormatter.EscapableCharacters.getEscapeChar;
import static com.squedgy.utilities.interfaces.Formatters.csv;
import static java.util.stream.Collectors.toMap;
import static org.slf4j.LoggerFactory.getLogger;

public class WithHeaderCsvFormatter extends CsvFormatter<Map<String, List<String>>> {

	private static Logger log = getLogger(WithHeaderCsvFormatter.class);

	@Override
	public Map<String, List<String>> encode(InputStream stream) {
		inQuote = false;
		CsvFormatter<List<List<String>>> bodyParser = csv()
				.delimitedBy(delimiter)
				.escapedBy(escapeChar)
				.ignoreLast(ignoreLast)
				.skipRows(skipRows)
				.lineSeparator(lineSeparator)
				.quotedBy(quoteChar)
				.selectUpTo(maxSelection);
		try {

			List<String> headers;

			StringBuilder builder = new StringBuilder();

			final boolean quotable = quoteChar != null;
			log.debug("quotable stream: " + quotable);
			log.debug("line separator: " + escapeString(lineSeparator, '\\'));
			// While we're in the first row

			if(quotable) {
				log.debug("quoted");
				headers = parseQuotedHeaders(builder, stream);
			} else {
				log.debug("unquoted");
				headers = parseQuotelessHeaders(builder, stream);
			}


			log.debug(headers.size() + " headers");

			AtomicInteger column = new AtomicInteger(0);
			Map<String, List<String>> values;

			List<List<String>> body = bodyParser.encode(stream);
			if(body.size() > 0) values = body.stream()
						.collect(toMap(list -> headers.get(column.getAndIncrement()), Function.identity()));
			else values = headers.stream().collect(toMap(Function.identity(), e -> new LinkedList<>()));
			return values;
		} catch (IOException e) {
			log.error("There was an issue reading a headered CsvStream.", e);
			throw new RuntimeException(e);
		}
	}

	private List<String> parseQuotelessHeaders(StringBuilder builder, InputStream stream){

		int currentChar;
		List<String> headers = new LinkedList<>();

		while(true) {

			try{
				currentChar = stream.read();
				if(currentChar == -1) {
					headers.add(builder.toString());
					break;
				}
			} catch (IOException e) {
				log.error("Error reading a stream EOS?", e);
				headers.add(builder.toString());
				break;
			}

			if(lineEnd(builder, currentChar)) {
				headers.add(builder.toString());
				break;
			} else if(currentChar == escapeChar) {
				currentChar = readChar(stream);
				if(currentChar == delimiter) builder.append(delimiter);
				else builder.append((char)getEscapeChar(currentChar, stream));
			} else if (currentChar == delimiter) {
				headers.add(builder.toString());
				builder.setLength(0);
			} else {
				builder.append((char) currentChar);
			}

		}

		return headers;

	}

	private List<String> parseQuotedHeaders(StringBuilder builder, InputStream stream) {

		int currentChar;
		List<String> headers = new LinkedList<>();
		inQuote = false;

		while(true) {
			try{
				currentChar = stream.read();
			} catch (IOException e) {
				log.error("Error while reading from stream, EOS?", e);
				headers.add(builder.toString());
				break;
			}
			if(lineEnd(builder, currentChar)){
				headers.add(builder.toString());
				break;
			} else if(inQuote) {
				if(currentChar == escapeChar) {
					builder.append((char)getEscapeChar(currentChar, stream));
				} else if (currentChar == quoteChar) {
					inQuote = false;
				} else {
					builder.append((char)currentChar);
				}
			} else {
				if(currentChar == quoteChar) {
					if(builder.length() != 0) throw new IllegalStateException("Column " + headers.size() + " in the headers has multiple values in the same column!");
					inQuote = true;
				} else if ( currentChar == delimiter) {
					headers.add(builder.toString());
					builder.setLength(0);
				}
			}

		}

		return headers;
	}

	@Override
	public InputStream decode(Map<String, List<String>> toDecode) {
		return null;
	}
}
