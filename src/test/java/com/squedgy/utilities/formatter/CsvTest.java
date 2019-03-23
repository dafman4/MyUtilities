package com.squedgy.utilities.formatter;

import com.squedgy.utilities.formatter.CsvFormatter;
import com.squedgy.utilities.interfaces.Formatters;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CsvTest {

	@Test
	public void normalQuotedHeaderedCsv() throws IOException {

		CsvFormatter formatter = Formatters.headeredCsv();
		formatter.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/non-quoted-headered.csv"));
	}

	@Test
	public void normalNonQuotedHeaderedCsv() throws IOException {

		CsvFormatter formatter2 = Formatters.headeredCsv().quotedBy('"');
		formatter2.encode(Thread.currentThread().getContextClassLoader().getResourceAsStream("csv/quoted-headered.csv"));
	}

}
