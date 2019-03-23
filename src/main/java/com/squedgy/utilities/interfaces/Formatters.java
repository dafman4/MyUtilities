package com.squedgy.utilities.interfaces;

import com.squedgy.utilities.formatter.ConfigFileFormat;
import com.squedgy.utilities.formatter.HeaderedCsvFormatter;

public interface Formatters {

	public static ConfigFileFormat config() { return  new ConfigFileFormat(); }

	public static HeaderedCsvFormatter headeredCsv() { return new HeaderedCsvFormatter(); }

}
