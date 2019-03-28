package com.squedgy.utilities.interfaces;

import com.squedgy.utilities.formatter.ConfigFileFormat;
import com.squedgy.utilities.formatter.csv.NoHeaderCsvFormatter;
import com.squedgy.utilities.formatter.csv.WithHeaderCsvFormatter;

public interface Formatters {

	public static ConfigFileFormat config() { return  new ConfigFileFormat(); }

	public static WithHeaderCsvFormatter headeredCsv() { return new WithHeaderCsvFormatter(); }

	public static NoHeaderCsvFormatter csv() { return new NoHeaderCsvFormatter(); }

}
