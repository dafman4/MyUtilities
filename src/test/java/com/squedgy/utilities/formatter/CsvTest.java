package com.squedgy.utilities.formatter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.squedgy.utilities.formatter.csv.CsvFormatter;
import com.squedgy.utilities.interfaces.Formatters;
import org.junit.jupiter.api.Test;

public class CsvTest {

	@Test
	public void normalNonQuotedHeaderedCsv() throws IOException {

		CsvFormatter<Map<String, List<String>>> formatter = Formatters.headeredCsv();
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/non-quoted-headered.csv"))
				.forEach(this::printList);
	}

	@Test
	public void normalQuotedHeaderedCsv() throws IOException {

		CsvFormatter<Map<String, List<String>>> formatter = Formatters.headeredCsv().quotedBy('"');
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/quoted-headered.csv"))
				.forEach(this::printList);
	}

	@Test
	public void normalNonQuotedCsv() throws IOException {

		CsvFormatter<List<List<String>>> formatter = Formatters.csv();
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/non-quoted.csv"))
				.forEach(System.out::println);
	}

	@Test
	public void normaQuotedCsv() throws IOException {

		CsvFormatter<List<List<String>>> formatter = Formatters.csv().quotedBy('"');
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/quoted.csv"))
				.forEach(System.out::println);
	}

	@Test
	public void testBigQuotedHeadered() throws IOException {

		long millis = System.currentTimeMillis();
		CsvFormatter<Map<String, List<String>>> formatter = Formatters.headeredCsv().quotedBy('"');
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/big-quoted-headered.csv"))
			.forEach(this::printBig);
		long afterMillis = System.currentTimeMillis();
		System.out.println("It took approximately " + (afterMillis - millis) + " milliseconds to finish the big test!");


		millis = System.currentTimeMillis();
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<Map<String,String>> it = mapper.readerFor(Map.class)
				.with(schema)
				.readValues(Thread.currentThread().getContextClassLoader().getResource("csv/big-quoted-headered.csv"));
		List<Map<String, ?>> list = new LinkedList<>();
		while(it.hasNext()){
			list.add(it.nextValue());
		}
		for(String key : list.get(0).keySet()) printBig(key, list);
		afterMillis = System.currentTimeMillis();
		System.out.println("It took approximately " + (afterMillis - millis) + " milliseconds for jackson to finish the big test!");

	}

	@Test
	public void testBigHeadered() throws IOException {
		CsvFormatter<Map<String, List<String>>> formatter = Formatters.headeredCsv().lineSeparator("\r");
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/big-headered.csv"))
				.forEach(this::printBig);

	}

	private void printList(String header, List list) {
		System.out.println(String.format("%25s: %s", header, list));
	}

	private void printBig(String header, List list) {
		System.out.println(String.format("%25s: %d", header, list.size()));
	}

}
