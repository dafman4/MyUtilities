package com.squedgy.utilities.formatter;

import com.squedgy.utilities.reader.FileReader;
import com.squedgy.utilities.writer.FileWriter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

public class NonQuotedCsv {

	private static final Logger log = getLogger(NonQuotedCsv.class);

	@Test
	public void reads() throws IOException {
		NonQuotedCsvFormat format = new NonQuotedCsvFormat();
		long millies = System.currentTimeMillis();
		List<List<String>> values = format.decode(getClass().getResourceAsStream("test.csv"));
		long diff = System.currentTimeMillis() - millies;
		log.debug("Took " + diff + " millis to parse test.csv");

		StringBuilder builder = new StringBuilder();
		AtomicInteger i = new AtomicInteger();
		values.forEach((columns) -> {
			if(i.getAndIncrement() > 80000)
			builder.append(i.get()).append(": ")
					.append(String.join(", ", columns))
					.append('\n');
		});

		log.debug(builder.toString());
	}

	@Test
	public void writes() throws IOException, URISyntaxException {
		NonQuotedCsvFormat format = new NonQuotedCsvFormat();
		List<List<String>> toWrite = Arrays.asList(
				Arrays.asList("123", "123", "123", "123", "1234"),
				Arrays.asList("456", "456", "456", "45" , "645")
		);

		FileReader<List<List<String>>> reader = new FileReader<>(format);

		log.debug(reader.read(getClass().getResource("exampleWrite.csv")).toString());

		FileWriter<List<List<String>>> writer = new FileWriter<>("test.csv", format, false);

		writer.write(toWrite);

		assertEquals(toWrite, reader.read(Paths.get("test.csv").toAbsolutePath()));

	}

}
