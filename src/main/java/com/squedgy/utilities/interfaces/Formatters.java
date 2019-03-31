package com.squedgy.utilities.interfaces;

import com.squedgy.utilities.formatter.ConfigFileFormat;

public interface Formatters {

	public static ConfigFileFormat config() { return  new ConfigFileFormat(); }

}
